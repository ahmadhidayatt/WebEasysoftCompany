package com.example.janolaskar.aCU.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.janolaskar.aCU.R;
import com.example.janolaskar.aCU.holder.ConversationHolder;
import com.example.janolaskar.aCU.holder.Holder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by janolaskar on 9/6/17.
 */
public class ConversationAdapter_backup extends RecyclerView.Adapter<ConversationAdapter_backup.MyViewHolder> {

    private static final String TAG = "ConversationAdapter";

    private List<ConversationHolder> conversationList;

    private static OnZeroLongClick mOnZeroLongClick;
    private static OnSingleLongClick mOnSingleLongClickListener;
    private static OnMultipleLongClick mOnMultipleLongClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, message, date;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            message = (TextView) view.findViewById(R.id.message);
            date = (TextView) view.findViewById(R.id.date);
        }
    }


    public ConversationAdapter_backup(List<ConversationHolder> conversationList) {
        this.conversationList = conversationList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.conversation_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ConversationHolder conversation = conversationList.get(position);
        holder.title.setText(conversation.getTitle());
        holder.message.setText(conversation.getLastMessage());
        holder.date.setText(conversation.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ConversationHolder ch = conversationList.get(position);
//                boolean checked = ch.isChecked();
//                if(checked) {
//                    setChecked(ch, false);
//                    if (checking() == 1) {
//                        mOnSingleLongClickListener.OnSingleLongClickItem(position, "1");
//                    } else if (checking() > 1) {
                        mOnMultipleLongClickListener.OnMultipleLongClickItem(checking(), position, "1");
//                    } else {
//                        mOnZeroLongClick.OnZeroLongClickItem();
//                    }
                    Log.e(TAG, "onClick: " + String.valueOf(checking()));
                    notifyItemChanged(position);
//                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return conversationList.size();
    }

    private void setChecked(Holder p_holder, boolean p_checked) {
        p_holder.setChecked(p_checked);
        if (p_checked) {
            setDeleteEnable(true);
        } else {
            setDeleteEnable(anyChecked());
        }
    }

    private boolean mDeleteEnable = false;

    public void setDeleteEnable(boolean p_enable) {
        if (p_enable == mDeleteEnable) {
            return;
        }
        mDeleteEnable = p_enable;
    }

    public boolean anyChecked() {
        boolean checked = false;
        for (int i = 0; i < conversationList.size(); i++) {
            if (conversationList.get(i).isChecked()) {
                checked = true;
                break;
            }
        }
        return checked;
    }

    int check;
    public int checking() {
        check = 0;
        for (int i = 0; i < conversationList.size(); i++) {
            if (conversationList.get(i).isChecked()) {
                check++;
            }
        }
        return check;
    }

    public interface OnZeroLongClick {
        void OnZeroLongClickItem();
    }

    // interface for changing menu when there is one item
    public void setOnSingleLongClick(OnSingleLongClick p_listener) {
        mOnSingleLongClickListener = p_listener;
    }

    public interface OnSingleLongClick {
        void OnSingleLongClickItem(int pos, String scope);
    }

    // interface for changing menu when there are multiple item
    public void setOnMultipleLongClick(OnMultipleLongClick p_listener) {
        mOnMultipleLongClickListener = p_listener;
    }

    public interface OnMultipleLongClick {
        void OnMultipleLongClickItem(int a, int pos, String Scope);
    }

    public ArrayList<ConversationHolder> getCheckedList() {
        ArrayList<ConversationHolder> checkedConversations = new ArrayList<>();
        for (int i = 0; i < conversationList.size(); i++) {
            if (conversationList.get(i).isChecked()) {
                checkedConversations.add(conversationList.get(i));
            }
        }
        return checkedConversations;
    }
}
