
package com.algonquincollege.nana0006.lab1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
//import androidx.support.annotation.Nullable;
//import androidx.*;


//import com.algonquincollege.nana0006.lab1.R;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> implements ListAdapter {
//public class MessageAdapter extends BaseAdapter implements ListAdapter {

    private Activity activity;
    private List<Message> messages;//111= DatabaseHelper.getAllMessages();
    DatabaseHelper help;
    public MessageAdapter(Activity context, int resource, List<Message> objects) {
        super(context, resource, objects);

        Database database = new Database(context);

        this.activity = context;
        this.messages = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // View newView = inflater.inflate(R.layout.left, parent, false);
        //  LayoutInflater inflater = getLayoutInflater();

        help = new DatabaseHelper(getContext());

        Message chatData = getItem(position);
        //    Message chatData = messages.getItemId(position);
        //  Message chatData ;//= new Message();
        // messages = help.getAllMessages();

        //   int viewType = getItemViewType(position);

        int layoutResource = 0; // determined by view type

        if (chatData.getIsMine() == 1) {
            layoutResource = R.layout.left;
        } else {
            layoutResource = R.layout.right;
        }
        // if (convertView == null) {
        //    holder = (ViewHolder) convertView.getTag();
        // } else {

            convertView = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        // }
        //set message content
        holder.msg.setText(chatData.getContent());
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        // return the total number of view types. this value should never change
        // at runtime
        return 2;
    }


    @Override
    public int getCount() {
        return super.getCount();
        //return messages.size();
    }

    //@androidx.annotation.Nullable
    @Override
    public Message getItem(int position) {
        //return super.getItem(position);
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        //  return super.getItemId(position);
        return (long) position;
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        return position % 2;
    }

    private class ViewHolder {
        private TextView msg;

        public ViewHolder(View v) {
            msg = (TextView) v.findViewById(R.id.txt_msg);
        }
    }
}