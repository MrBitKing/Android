package com.algonquincollege.nana0006.lab1;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.algonquincollege.nana0006.lab1.ChatRoomActivity;
import com.algonquincollege.nana0006.lab1.MessageFragmentA;
import com.algonquincollege.nana0006.lab1.R;


public class MessageFragmentB extends Fragment {

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
        View result = inflater.inflate(R.layout.fragmentb, container, false);

        //show the message
        TextView message = (TextView) result.findViewById(R.id.message);
        message.setText(dataFromActivity.getString(ChatRoomActivity.ITEM_SELECTED));

        //show the id:
        View idView = (View) result.findViewById(R.id.fragmentB);
        // idView.setText("ID=" + id);

        // get the delete button, and add a click listener:
        Button deleteButton = (Button) result.findViewById(R.id.btn_chat_send);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clk) {

                if (isTablet) { //both the list and details are on the screen:

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