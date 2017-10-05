package com.example.janolaskar.aCU.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.janolaskar.aCU.R;
import com.example.janolaskar.aCU.holder.ConversationHolder;
import com.example.janolaskar.aCU.lib.CircularTextView;
import com.pkmmte.view.CircularImageView;

import java.util.List;

/**
 * Created by janolaskar on 9/6/17.
 */
public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.MyViewHolder> {
    private static final String TAG = "ConversationAdapter";

    private List<ConversationHolder> conversationList;

    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CircularImageView thumbnail;
        public TextView name;
        public TextView last_message;
        public TextView date;
        public CircularTextView unread_counter;


        public MyViewHolder(View view) {
            super(view);
            thumbnail = view.findViewById(R.id.img);
            name = (TextView) view.findViewById(R.id.title);
            last_message = (TextView) view.findViewById(R.id.message);
            date = (TextView) view.findViewById(R.id.date);
            unread_counter = (CircularTextView)view.findViewById(R.id.circularTextView);
        }
    }


    public ConversationAdapter(Context context, List<ConversationHolder> conversationList) {
        mContext = context;
        this.conversationList = conversationList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.conversation_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ConversationHolder conversation = conversationList.get(position);

        holder.name.setText(conversation.getTitle());
        holder.last_message.setText(conversation.getLastMessage());
        holder.date.setText(conversation.getDate());

        if (conversation.getUnreadCounter() == 0) {
            holder.unread_counter.setVisibility(View.GONE);
        } else {
            holder.unread_counter.setText(conversation.getUnreadCounter()+"");
        }


        Log.e(TAG, conversation.toCommValues()+"");

        if (conversation.flag == 0) {
            holder.thumbnail.setImageResource(R.drawable.friends_profile);
        } else {
            holder.thumbnail.setImageResource(R.drawable.group_profile);
        }

//        Glide.with(mContext).load(R.drawable.group_profile)
//                .thumbnail(0.5f)
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return conversationList.size();
    }
}
