package com.algonquincollege.nana0006.lab1;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;
import android.support.v4.widget.SwipeRefreshLayout;


import java.util.ArrayList;
import java.util.List;


public class ChatRoomActivity extends AppCompatActivity {

    public static final String ITEM_SELECTED = "ITEM";
    public static final String ITEM_POSITION = "POSITION";
    public static final String ITEM_ID = "ID";
    public static final int EMPTY_ACTIVITY = 345;
    public static int selected;


    private ListView listView;
    private Button btnSend;
    private Button btnReceived;

    DatabaseHelper helper;

    private EditText editText;
    int isMine = 0;
    public static List<Message> chatDatas;
    private ArrayAdapter<Message> adapter;
    private MessageAdapter adapt;
    private SwipeRefreshLayout scrollRefresh;

    private boolean mIsDualPane = false;
    private boolean mTwoPane;

    //  private boolean isTablet = false;
    private Bundle dataFromActivity;
    private int id;


    // public void setTablet(boolean tablet) { isTablet = tablet; }


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.chat_room_lab4);

        //   val fragmentBView = findViewById<View>(R.id.fragmentb)

   /*     if (savedInstanceState == null) {
            // Let's first dynamically add a fragment into a frame container
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.activity_chat_window, new MessageFragmentA(), "SOMETAG").
                    commit();
            // Now later we can lookup the fragment by tag
            MessageFragmentA fragmentDemo = (MessageFragmentA)
                    getSupportFragmentManager().findFragmentByTag("SOMETAG");
        }*/

        helper = new DatabaseHelper(getApplicationContext());
        // database = Database.(getApplicationContext()); //////  same

        helper.open();
        chatDatas = helper.getAllMessages();//new ArrayList<>();
        listView = (ListView) findViewById(R.id.list_msg);
        btnSend = (Button) findViewById(R.id.btn_chat_send);
//        btnReceived = (Button) findViewById(R.id.);
        editText = (EditText) findViewById(R.id.msg_type);
        scrollRefresh = findViewById(R.id.swipeRefresh);

        final boolean isTablet = findViewById(R.id.message_detail_container) != null; //check if the FrameLayout is loaded

        // adapter = new MessageAdapter<>(this, R.layout.left, chatDatas);
        // adapt = new ArrayAdapter(this, R.layout.chat_room_lab4, chatDatas);  //////////chtdatas ???
        adapt = new MessageAdapter(this, android.R.layout.simple_list_item_1, chatDatas);  //////////chtdatas ???


        listView.setAdapter(adapt);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> list, View item, int position, long id) {

                Bundle dataToPass = new Bundle();
                dataToPass.putString(ITEM_SELECTED, chatDatas.get(position).getContent());
                dataToPass.putInt(ITEM_POSITION, position);
                dataToPass.putLong(ITEM_ID, id);
                selected = position;
                if (isTablet) {
                    MessageFragmentB dFragment = new MessageFragmentB(); //add a DetailFragment
                    dFragment.setArguments(dataToPass); //pass it a bundle for information
                    dFragment.setTablet(true);  //tell the fragment if it's running on a tablet or not
                    ChatRoomActivity.this.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.message_detail_container, dFragment) //Add the fragment in FrameLayout
                            //.addToBackStack("AnyName") //make the back button undo the transaction
                            .commit(); //actually load the fragment.
                } else //isPhone
                {
                    Intent nextActivity = new Intent(ChatRoomActivity.this, MessageDetails.class);
                    nextActivity.putExtras(dataToPass); //send data to next activity
                    ChatRoomActivity.this.startActivityForResult(nextActivity, EMPTY_ACTIVITY); //make the transition
                    //ChatRoomActivity.this.startActivity(nextActivity);

                }
            }
        });

     /*   listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message item = (Message) adapt.getItem(position);

                Intent intent = new Intent(view.getContext(), MessageFragmentB.class);
                //based on item add info to intent
                startActivity(intent);
            }

        });*/

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMine = 1;

                if (editText.getText().toString().trim().equals("")) {
                    Toast.makeText(ChatRoomActivity.this, "please input text..", Toast.LENGTH_SHORT).show();
                } else {
                    Message chatData = new Message();
                    chatData.setContent(editText.getText().toString());
                    chatData.setIsMine(isMine);
                    chatDatas.add(chatData);

                    helper = new DatabaseHelper(ChatRoomActivity.this);
                    helper.write();
                    helper.createMessage(chatData.getContent(), chatData.getIsMine());
                    helper.close();

                    ((BaseAdapter) adapt).notifyDataSetChanged();
                    editText.setText("");
/*
                    if (isTablet) {  //twopane?
                        Bundle arguments = new Bundle();
                        //arguments.putString(MessageFragmentA.class, holder.mItem.id);
                        MessageFragmentB fragment = new MessageFragmentB();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentLocation, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, MessageDetails.class);
                        // intent.putExtra(MessageDetails.ARG_ITEM_ID, helper.mItem.id);

                        context.startActivity(intent);
                    }*/
                }
            }

        });

/*        btnReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMessageId(id);
            }
        });*/

        scrollRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                scrollRefresh.setRefreshing(false);
            }
        });

        if (findViewById(R.id.fragmentLocation) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }


    public void deleteMessageId(int id) {
        Log.i("Delete this message:", " id=" + id);
        chatDatas.remove(id);
        adapt.notifyDataSetChanged();
    }
/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.chat_room_lab4, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }*/

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //  super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EMPTY_ACTIVITY) {
            if (resultCode == RESULT_OK) //if you hit the delete button instead of back button
            {
                long id = data.getLongExtra(ITEM_ID, 0);
                deleteMessageId((int) id);
            }
        }
    }
}
