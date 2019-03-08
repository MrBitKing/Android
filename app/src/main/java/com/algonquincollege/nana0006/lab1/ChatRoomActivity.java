package com.algonquincollege.nana0006.lab1;

import android.content.Context;
import android.content.Intent;
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

    DatabaseHelper helper;
    Database database;

    private EditText editText;
    int isMine = 0;
    private List<Message> chatDatas;
    private ArrayAdapter<Message> adapter;
    private ListAdapter adapt;
    private SwipeRefreshLayout scrollRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chat_room_lab4);
        helper = new DatabaseHelper(getApplicationContext());
        // database = Database.(getApplicationContext()); //////  same

        helper.open();
        chatDatas = helper.getAllMessages();//new ArrayList<>();
        listView = (ListView) findViewById(R.id.list_msg);
        btnSend = findViewById(R.id.btn_chat_send);
        btnReceived = findViewById(R.id.btn_chat_receive);
        editText = (EditText) findViewById(R.id.msg_type);
        scrollRefresh = findViewById(R.id.swipeRefresh);

        // adapter = new ArrayAdapter<>(this, R.layout.left, chatDatas);
        adapt = new MessageAdapter(this, R.layout.chat_room_lab4, chatDatas);  //////////chtdatas ???

        listView.setAdapter(adapt);

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
                }
            }

        });

        btnReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMine = 0;

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
                }
            }
        });

        scrollRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                scrollRefresh.setRefreshing(false);
            }
        });
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
}
