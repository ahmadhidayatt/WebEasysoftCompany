package com.example.janolaskar.aCU.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.janolaskar.aCU.CommonMethods;
import com.example.janolaskar.aCU.R;
import com.example.janolaskar.aCU.adapter.GroupChatAdapter;
import com.example.janolaskar.aCU.holder.GroupChatHolder;
import com.example.janolaskar.aCU.sqlite.GroupChatDB;
import com.pkmmte.view.CircularImageView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ahmad on 06/09/17.
 */

public class GroupChatList extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "PrivateChatList";
    private EditText msg_edittext;
    private Random random;
    private CircularImageView avatar;
    private TextView title;
    private ImageView image_back;
    public static ArrayList<GroupChatHolder> chatlist;
    public static GroupChatAdapter chatAdapter;
    ListView msgListView;
    private Toolbar toolbar;

    public GroupChatList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String name;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                name = null;
            } else {
                name = extras.getString("name");
            }
        } else {
            name = (String) savedInstanceState.getSerializable("name");
        }
        setContentView(R.layout.fragment_chat_group);

        random = new Random();

//        getSupportActionBar().setTitle(
//                Html.fromHtml("<font color=\"black\">" + name +
//                        "</font>"));

        msg_edittext = (EditText) findViewById(R.id.messageEditText);
        msgListView = (ListView) findViewById(R.id.msgListView);
        ImageButton sendButton = (ImageButton) findViewById(R.id.sendMessageButton);
        sendButton.setOnClickListener(this);

        // ----Set autoscroll of listview when a new message arrives----//
        msgListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        msgListView.setStackFromBottom(true);

        chatlist = new ArrayList<GroupChatHolder>();
        chatAdapter = new GroupChatAdapter(this, chatlist);
        msgListView.setAdapter(chatAdapter);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        avatar = (CircularImageView) toolbar.findViewById(R.id.avatar);

        title = (TextView) toolbar.findViewById(R.id.title);
        title.setText(name);
        prepareChatData();

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    public void sendTextMessage(View v) {
        String message = msg_edittext.getEditableText().toString();
        if (!message.equalsIgnoreCase("")) {
            final GroupChatHolder chatMessage = new GroupChatHolder(2002, 300,
                    message,true,getResources().getDrawable(R.drawable.friends_profile));
            chatMessage.image = getResources().getDrawable(R.drawable.friends_profile);
            chatMessage.message = message;
            chatMessage.Date = CommonMethods.getCurrentDate();
            chatMessage.Time = CommonMethods.getCurrentTime();
            msg_edittext.setText("");
            chatAdapter.add(chatMessage);
            chatAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendMessageButton:
                sendTextMessage(v);

        }
    }

    public void change_actionbar(String Title) {
        this.getSupportActionBar().setTitle(Title);
    }

    private void prepareChatData() {
        ArrayList<GroupChatHolder> arC = new GroupChatDB(getApplicationContext()).getRecords(null);
        Log.e(TAG, arC.toString());
        for (GroupChatHolder cholder : arC) {
            cholder.setImage(getResources().getDrawable(R.drawable.friends_profile));
            cholder.setMine(false);
//            ConversationHolder objConv = new ConversationHolder(cholder._id+"", cholder.person_group+"", cholder.unread_counter+"");
            chatAdapter.add(cholder);
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

        chatAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
