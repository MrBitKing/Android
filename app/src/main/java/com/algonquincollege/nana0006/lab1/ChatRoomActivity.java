package com.algonquincollege.nana0006.lab1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;
import android.support.v4.widget.SwipeRefreshLayout;
import java.util.ArrayList;
import java.util.List;


public class ChatRoomActivity extends AppCompatActivity {
    private ListView listView;
    private View btnSend;
    private View btnReceived;

    private EditText editText;
    boolean isMine = false;
    private List<Message> chatDatas;
    private ArrayAdapter<Message> adapter;
    private ListAdapter adapt;
    private SwipeRefreshLayout scrollRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chat_room_lab4);
        chatDatas = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list_msg);
        btnSend = findViewById(R.id.btn_chat_send);
        btnReceived = findViewById(R.id.btn_chat_receive);
        editText = (EditText) findViewById(R.id.msg_type);
        scrollRefresh = findViewById(R.id.swipeRefresh);

        // adapter = new ArrayAdapter<>(this, R.layout.left, chatDatas);
        adapt = new MessageAdapter(this, R.layout.left, chatDatas);

        listView.setAdapter(adapt);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMine = true;

                if (editText.getText().toString().trim().equals("")) {
                    Toast.makeText(ChatRoomActivity.this, "please input text..", Toast.LENGTH_SHORT).show();
                } else {
                    Message chatData = new Message(editText.getText().toString(), isMine);
                    chatDatas.add(chatData);
                    ((BaseAdapter) adapt).notifyDataSetChanged();
                    editText.setText("");

                }
            }
        });

        btnReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isMine = false;

                if (editText.getText().toString().trim().equals("")) {
                    Toast.makeText(ChatRoomActivity.this, "please input text..", Toast.LENGTH_SHORT).show();
                } else {
                    Message chatData = new Message(editText.getText().toString(), isMine);
                    chatDatas.add(chatData);
                    ((BaseAdapter) adapt).notifyDataSetChanged();
                    editText.setText("");
                    //    if (isMine) {
                    //        isMine = false;
                    //     } else {
//                        isMine = true;

                    //  }
                }
            }
        });


        scrollRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //write your code here.
                //
                scrollRefresh.setRefreshing(false);
            }
        });

    }


}


/*public class ChatRoomActivity extends AppCompatActivity {

    public static final String mypreference = "mypref";
    public static final String Name = "nameKey";
    public static final String Email = "emailKey";
    EditText txtUsername, txtPassword, txtEmail;
    Button buttonReg2;

    // String Password ;
    //String email ;

    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    // String name = txtUsername.getText().toString();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_room_lab4);


    }

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
        super.onActivityResult(requestCode, resultCode, data);
    }
}*/
