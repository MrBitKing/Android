package com.algonquincollege.nana0006.lab1;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MessageDetails extends AppCompatActivity {

    public static final String ITEM_SELECTED = "ITEM";
    public static final String ITEM_POSITION = "POSITION";
    public static final String ITEM_ID = "ID";
    public static final int EMPTY_ACTIVITY = 345;

    private Button btnDelete;
    static DatabaseHelper helper;
    static List<Message> chatDatas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_details);

        btnDelete = (Button) findViewById(R.id.deleteButton);
        helper = new DatabaseHelper(getApplicationContext());
        helper.open();
        chatDatas = helper.getAllMessages();//new ArrayList<>();


        final Bundle dataToPass = getIntent().getExtras(); //get the data that was passed from FragmentExample

        //This is copied directly from FragmentExample.java lines 47-54
        MessageFragmentB dFragment = new MessageFragmentB();
        dFragment.setArguments(dataToPass); //pass data to the the fragment
        dFragment.setTablet(false); //tell the Fragment that it's on a phone.
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentLocation, dFragment)
                //.addToBackStack("AnyName")
                .commit();


        btnDelete.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //Intent intent = new Intent(MessageDetails.this, WeatherForecast.class);
                //  MessageDetails.this.startActivity(intent);
                // dataToPass.
                Bundle bundle = getIntent().getExtras();
                helper.deleteMessage(chatDatas.get(bundle.getInt(ITEM_POSITION)));
                // helper.close();

            }
        });
    }


}


/*
package com.algonquincollege.nana0006.lab1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class MessageDetails extends Fragment {

    private boolean isTablet;
    private Bundle dataFromActivity;
    private long id;

    public void setTablet(boolean tablet) {
        isTablet = tablet;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataFromActivity = getArguments();
        id = dataFromActivity.getLong(ChatRoomActivity.ITEM_ID);

        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.message_details, container, false);

        //show the message
        TextView message = (TextView) result.findViewById(R.id.message);
        message.setText(dataFromActivity.getString(ChatRoomActivity.ITEM_SELECTED));

        //show the id:
        TextView idView = (TextView) result.findViewById(R.id.idText);
        idView.setText("ID=" + id);

        // get the delete button, and add a click listener:
        Button deleteButton = (Button) result.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clk) {

                if (isTablet) { //both the list and details are on the screen:

                    ChatRoomActivity parent = (ChatRoomActivity) getActivity();
                    // parent.deleteMessageId((int)id); //this deletes the item and updates the list


                    //now remove the fragment since you deleted it from the database:
                    // this is the object to be removed, so remove(this):
                    parent.getSupportFragmentManager().beginTransaction().remove(MessageDetails.this).commit();
                }
                //for Phone:
                else //You are only looking at the details, you need to go back to the previous list page
                {
                    ChatRoomActivity parent = (ChatRoomActivity) getActivity();
                    Intent backToFragmentExample = new Intent();
                    backToFragmentExample.putExtra(ChatRoomActivity.ITEM_ID, dataFromActivity.getLong(ChatRoomActivity.ITEM_ID));

                    parent.setResult(Activity.RESULT_OK, backToFragmentExample); //send data back to FragmentExample in onActivityResult()
                    parent.finish(); //go back
                }
            }
        });
        return result;
    }
}*/
