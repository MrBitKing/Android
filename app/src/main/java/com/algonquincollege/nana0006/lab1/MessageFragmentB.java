package com.algonquincollege.nana0006.lab1;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.algonquincollege.nana0006.lab1.ChatRoomActivity;
import com.algonquincollege.nana0006.lab1.MessageFragmentA;
import com.algonquincollege.nana0006.lab1.R;

import java.util.List;

import static com.algonquincollege.nana0006.lab1.ChatRoomActivity.ITEM_POSITION;
import static com.algonquincollege.nana0006.lab1.ChatRoomActivity.selected;


public class MessageFragmentB extends Fragment {

    private boolean isTablet;
    private Bundle dataFromActivity;
    private long id;
    //private Button btnDelete;
    DatabaseHelper helper;
    private List<Message> chatDatas;


    public void setTablet(boolean tablet) {
        isTablet = tablet;
    }

    public MessageFragmentB() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        //    helper.open();
        //    helper = new DatabaseHelper(getContext());

        chatDatas = ChatRoomActivity.chatDatas;
        dataFromActivity = getArguments();
        id = dataFromActivity.getLong(ChatRoomActivity.ITEM_ID);

        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.message_details, container, false);
        //show the message
        TextView message = (TextView) result.findViewById(R.id.message);
        message.setText(dataFromActivity.getString(ChatRoomActivity.ITEM_SELECTED));

        //show the id:
        View idView = (View) result.findViewById(R.id.fragmentB);
        //  idView.setText("ID=" + id);

        // get the delete button, and add a click listener:
        Button deleteButton = (Button) result.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clk) {

                if (isTablet) { //both the list and details are on the screen:

                    //      MessageDetails.helper.deleteMessage(MessageDetails.chatDatas.get(selected));
                    //helper.deleteMessage(chatDatas.get(selected));

                    // helper.deleteMessage(dataFromActivity.getString(ITEM_POSITION));

                    //  dataFromActivity.getBundle(ITEM_POSITION);

                    Toast.makeText(getContext(), "Delete Button Clicked", Toast.LENGTH_LONG).show();



                }
                //for Phone:
                else //You are only looking at the details, you need to go back to the previous list page
                {
                    ChatRoomActivity parent = (ChatRoomActivity) MessageFragmentB.this.getActivity();
                    Intent backToFragmentExample = new Intent();
                    backToFragmentExample.putExtra(ChatRoomActivity.ITEM_ID, dataFromActivity.getLong(ChatRoomActivity.ITEM_ID));

                    parent.setResult(Activity.RESULT_OK, backToFragmentExample); //send data back to FragmentExample in onActivityResult()
                    parent.finish(); //go back
                }
            }
        });
        return result;
    }
}