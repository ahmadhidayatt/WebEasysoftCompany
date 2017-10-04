package com.example.janolaskar.aCU.list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.janolaskar.aCU.R;
import com.example.janolaskar.aCU.activity.DividerItemDecoration;
import com.example.janolaskar.aCU.adapter.ConversationAdapter_backup;
import com.example.janolaskar.aCU.holder.ConversationHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by janolaskar on 9/6/17.
 */

public class ConversationList_backup extends Fragment {

    private static final String TAG = "ConversationList";
    private boolean inBed = false;
    private List<ConversationHolder> conversationList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ConversationAdapter_backup conversation_adapter;
    private static OnMultipleLongClick mOnMultipleLongClickListener;
    public ConversationList_backup() {
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
        conversation_adapter = new ConversationAdapter_backup(conversationList);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(conversation_adapter);

//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                ConversationHolder chat_position = conversationList.get(position);
//                Toast.makeText(getActivity(), chat_position.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getActivity(), PrivateChatList.class);
//                intent .putExtra("name",chat_position.getTitle());
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//                Toast.makeText(getActivity(), " is selected!", Toast.LENGTH_SHORT).show();
//            }
//        }));

        // ubah menu jika yang dipilih lebih dari 1 list / multiple item
        conversation_adapter.setOnMultipleLongClick(new ConversationAdapter_backup.OnMultipleLongClick() {
            @Override
            public void OnMultipleLongClickItem(int a, int pos, String scope) {
                try {
                    ArrayList<ConversationHolder> checkedConversations = conversation_adapter.getCheckedList();
                    String muteState = "-1";
                    mOnMultipleLongClickListener.OnMultipleLongClickItem(a,pos,scope);
                    Log.e(TAG, checkedConversations.get(pos).getTitle());
                    boolean sameMuteState = true;
//                    for (ConversationHolder conversationHolder : checkedConversations) {
//                        if (conversationHolder.message_scope.equals(Scope.GROUP)) {
//                            String group_id = conversationHolder.f_pin;
//                            GroupHolder groupHolder = GroupDB.get(group_id);
//                            if (muteState == "-1") {
//                                muteState = groupHolder.is_muted;
//                            } else if (!groupHolder.is_muted.equals(muteState)) {
//                                sameMuteState = false;
//                                break;
//                            }
//                        } else {
//                            String user_id = conversationHolder.f_pin;
//                            PersonHolder personHolder = PersonDB.get(user_id);
//                            if (muteState == "-1") {
//                                muteState = personHolder.is_muted;
//                            } else if (!personHolder.is_muted.equals(muteState)) {
//                                sameMuteState = false;
//                                break;
//                            }
//                        }
//                    }

//                    if (!sameMuteState) {
//                        updateMenu(R.menu.menu_uc_multiplelongclick);
//                    } else if (muteState.equals(GroupHolder.MUTE) || muteState.equals(PersonHolder.MUTE)) {
//                        updateMenu(R.menu.menu_uc_multiplelongclick_unmute);
//                    } else if (muteState.equals(GroupHolder.NOT_MUTE) || muteState.equals(PersonHolder.NOT_MUTE)) {
//                        updateMenu(R.menu.menu_uc_multiplelongclick_mute);
//                    }

//                    Snackbar.make(ConversationList.this, a + " conversation Selected", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            //
//                        }
//
//                    }).setActionTextColor(0xFF2cb058).show();
//                    position.add(pos);
                } catch (Exception e) {
                    Log.e(TAG, "", e);
                }
            }
        });
        prepareConversationData();
        return mRootView;
    }

    private void prepareConversationData() {
        ConversationHolder objConv = new ConversationHolder("Mad Max: Fury Road", "Action & Adventure", "20.15");
        conversationList.add(objConv);

        objConv = new ConversationHolder("Inside Out", "Animation, Kids & Family", "20.15");
        conversationList.add(objConv);

        objConv = new ConversationHolder("Star Wars: Episode VII - The Force Awakens", "Action", "20.15");
        conversationList.add(objConv);

        objConv = new ConversationHolder("Shaun the Sheep", "Animation", "20.15");
        conversationList.add(objConv);

        objConv = new ConversationHolder("The Martian", "Science Fiction & Fantasy", "20.15");
        conversationList.add(objConv);

        objConv = new ConversationHolder("Mission: Impossible Rogue Nation", "Action", "20.15");
        conversationList.add(objConv);

        objConv = new ConversationHolder("Up", "Animation", "20.09");
        conversationList.add(objConv);

        objConv = new ConversationHolder("Star Trek", "Science Fiction", "20.09");
        conversationList.add(objConv);

        objConv = new ConversationHolder("The LEGO Movie", "Animation", "20.14");
        conversationList.add(objConv);

        objConv = new ConversationHolder("Iron Man", "Action & Adventure", "20.08");
        conversationList.add(objConv);

        objConv = new ConversationHolder("Aliens", "Science Fiction", "19.86");
        conversationList.add(objConv);

        objConv = new ConversationHolder("Chicken Run", "Animation", "20.00");
        conversationList.add(objConv);

        objConv = new ConversationHolder("Back to the Future", "Science Fiction", "19.85");
        conversationList.add(objConv);

        objConv = new ConversationHolder("Raiders of the Lost Ark", "Action & Adventure", "19.81");
        conversationList.add(objConv);

        objConv = new ConversationHolder("Goldfinger", "Action & Adventure", "19.65");
        conversationList.add(objConv);

        objConv = new ConversationHolder("Guardians of the Galaxy", "Science Fiction & Fantasy", "20.14");
        conversationList.add(objConv);

        conversation_adapter.notifyDataSetChanged();
    }
    public void setOnMultipleLongClick(OnMultipleLongClick p_listener) {
        mOnMultipleLongClickListener = p_listener;
    }

    public interface OnMultipleLongClick {
        void OnMultipleLongClickItem(int a, int pos, String Scope);
    }

}
