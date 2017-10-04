package com.example.janolaskar.aCU.list;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.janolaskar.aCU.R;
import com.example.janolaskar.aCU.activity.DividerItemDecoration;
import com.example.janolaskar.aCU.activity.RecyclerTouchListener;
import com.example.janolaskar.aCU.adapter.ConversationAdapter;
import com.example.janolaskar.aCU.global.DataGlobal;
import com.example.janolaskar.aCU.holder.GroupChatHolder;
import com.example.janolaskar.aCU.holder.PrivateChatHolder;
import com.example.janolaskar.aCU.holder.ConversationHolder;
import com.example.janolaskar.aCU.holder.GroupHolder;
import com.example.janolaskar.aCU.holder.PersonHolder;
import com.example.janolaskar.aCU.sqlite.PrivateChatDB;
import com.example.janolaskar.aCU.sqlite.GroupChatDB;
import com.example.janolaskar.aCU.sqlite.ConversationDB;
import com.example.janolaskar.aCU.sqlite.GroupDB;
import com.example.janolaskar.aCU.sqlite.PersonDB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by janolaskar on 9/6/17.
 */

public class ConversationList extends Fragment {

    private static final String TAG = "ConversationList";

    private List<ConversationHolder> conversationList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ConversationAdapter conversation_adapter;

    public ConversationList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRootView =  inflater.inflate(R.layout.conversation_list_fragment, container, false);
        recyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view);

        conversationList = new ArrayList<>();
        conversation_adapter = new ConversationAdapter(getContext(),conversationList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(conversation_adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ConversationHolder chat_position = conversationList.get(position);

                if (chat_position.getUnreadCounter() != 0) {
                    Log.e(TAG, "-------> masuk");
                    ContentValues cvalues = new ContentValues();
                    cvalues.put(ConversationHolder.FIELD_UNREAD_COUNTER, 0);
                    new ConversationDB(getContext()).updateRecord(cvalues, ConversationHolder.FIELD__ID + " = " +chat_position._id);
                    chat_position.unread_counter = 0;
                    conversationList.set(position, chat_position);
                    conversation_adapter.notifyItemChanged(position);
                }

                Toast.makeText(getActivity(), chat_position.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();

                if (chat_position.flag == DataGlobal.PRIVATE_MESSAGE) {
                    Intent intent = new Intent(getActivity(), PrivateChatList.class);
                    intent.putExtra("chat_group_message", chat_position._id);
                    intent.putExtra("name", chat_position.getTitle());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                } else if (chat_position.flag == DataGlobal.GROUP_MESSAGE) {
                    Intent intent = new Intent(getActivity(), GroupChatList.class);
                    intent.putExtra("chat_group_message", chat_position._id);
                    intent.putExtra("name", chat_position.getTitle());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                ConversationHolder chat_position = conversationList.get(position);
                Toast.makeText(getActivity(), chat_position.getTitle()+" on longclick!", Toast.LENGTH_SHORT).show();
            }
        }));
        prepareConversationData();
        return mRootView;
    }

    private void prepareConversationData() {
        ArrayList<ConversationHolder> arCh = new ConversationDB(getContext()).getRecords(null);
        for (ConversationHolder convHolder : arCh) {
            if (convHolder.flag == DataGlobal.PRIVATE_MESSAGE) {
                PrivateChatHolder privateChatHolder =  new PrivateChatDB(getContext()).getRecord(PrivateChatHolder.FIELD__ID + "=" + convHolder.chat_group_message);
                convHolder.setLastMessage(privateChatHolder.messages);
                convHolder.setDate(privateChatHolder.delivered);


                PersonHolder personHolder =  new PersonDB(getContext()).getRecord(PersonHolder.FIELD__ID + "=" + privateChatHolder.friend);
                convHolder.setTitle(personHolder.name);

            } else if (convHolder.flag == DataGlobal.GROUP_MESSAGE) {
                GroupChatHolder groupChatHolder = new GroupChatDB(getContext()).getRecord(PrivateChatHolder.FIELD__ID + "=" + convHolder.chat_group_message);
                Log.e(TAG, groupChatHolder.toCommValues() + "");
                convHolder.setLastMessage(groupChatHolder.messages);
                convHolder.setDate(groupChatHolder.delivered);
//
                GroupHolder groupHolder = new GroupDB(getContext()).getRecord(PrivateChatHolder.FIELD__ID + "=" + convHolder.chat_group_message);
                convHolder.setTitle(groupHolder.name);
            }

            conversationList.add(convHolder);
        }

//        ConversationHolder objConv = new ConversationHolder("Mad Max: Fury Road", "Action & Adventure", "20.15");
//        conversationList.add(objConv);
//
//        objConv = new ConversationHolder("Inside Out", "Animation, Kids & Family", "20.15");
//        conversationList.add(objConv);
//
//        objConv = new ConversationHolder("Star Wars: Episode VII - The Force Awakens", "Action", "20.15");
//        conversationList.add(objConv);
//
//        objConv = new ConversationHolder("Shaun the Sheep", "Animation", "20.15");
//        conversationList.add(objConv);
//
//        objConv = new ConversationHolder("The Martian", "Science Fiction & Fantasy", "20.15");
//        conversationList.add(objConv);
//
//        objConv = new ConversationHolder("Mission: Impossible Rogue Nation", "Action", "20.15");
//        conversationList.add(objConv);
//
//        objConv = new ConversationHolder("Up", "Animation", "20.09");
//        conversationList.add(objConv);
//
//        objConv = new ConversationHolder("Star Trek", "Science Fiction", "20.09");
//        conversationList.add(objConv);
//
//        objConv = new ConversationHolder("The LEGO Movie", "Animation", "20.14");
//        conversationList.add(objConv);
//
//        objConv = new ConversationHolder("Iron Man", "Action & Adventure", "20.08");
//        conversationList.add(objConv);
//
//        objConv = new ConversationHolder("Aliens", "Science Fiction", "19.86");
//        conversationList.add(objConv);
//
//        objConv = new ConversationHolder("Chicken Run", "Animation", "20.00");
//        conversationList.add(objConv);
//
//        objConv = new ConversationHolder("Back to the Future", "Science Fiction", "19.85");
//        conversationList.add(objConv);
//
//        objConv = new ConversationHolder("Raiders of the Lost Ark", "Action & Adventure", "19.81");
//        conversationList.add(objConv);
//
//        objConv = new ConversationHolder("Goldfinger", "Action & Adventure", "19.65");
//        conversationList.add(objConv);
//
//        objConv = new ConversationHolder("Guardians of the Galaxy", "Science Fiction & Fantasy", "20.14");
//        conversationList.add(objConv);

        conversation_adapter.notifyDataSetChanged();
    }
}
