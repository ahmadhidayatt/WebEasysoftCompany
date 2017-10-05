    package com.example.janolaskar.aCU.activity;

    import android.graphics.Color;
    import android.os.Bundle;
    import android.support.design.widget.TabLayout;
    import android.support.v4.app.Fragment;
    import android.support.v4.app.FragmentManager;
    import android.support.v4.app.FragmentPagerAdapter;
    import android.support.v4.view.ViewPager;
    import android.support.v7.app.AppCompatActivity;
    import android.support.v7.widget.Toolbar;
    import android.util.Log;
    import android.view.Menu;

    import com.example.janolaskar.aCU.R;
    import com.example.janolaskar.aCU.holder.ConversationHolder;
    import com.example.janolaskar.aCU.holder.FriendHolder;
    import com.example.janolaskar.aCU.holder.GroupChatHolder;
    import com.example.janolaskar.aCU.holder.GroupHolder;
    import com.example.janolaskar.aCU.holder.GroupMemberHolder;
    import com.example.janolaskar.aCU.holder.PersonHolder;
    import com.example.janolaskar.aCU.holder.PrivateChatHolder;
    import com.example.janolaskar.aCU.list.ConversationList;
    import com.example.janolaskar.aCU.list.GroupList;
    import com.example.janolaskar.aCU.list.PersonList;
    import com.example.janolaskar.aCU.sqlite.ConversationDB;
    import com.example.janolaskar.aCU.sqlite.DBOpenHelper;
    import com.example.janolaskar.aCU.sqlite.FriendDB;
    import com.example.janolaskar.aCU.sqlite.GroupChatDB;
    import com.example.janolaskar.aCU.sqlite.GroupDB;
    import com.example.janolaskar.aCU.sqlite.GroupMemberDB;
    import com.example.janolaskar.aCU.sqlite.PersonDB;
    import com.example.janolaskar.aCU.sqlite.PrivateChatDB;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;

    import java.util.ArrayList;
    import java.util.List;


    /**
     * Created by janolaskar on 9/6/17.
     */
    public class MainActivity extends AppCompatActivity {

        public static final String TAG = "EasysoftChat";

        private Toolbar toolbar;
        private TabLayout tabLayout;
        private ViewPager viewPager;
        private boolean is_in_mode_action = false;
        DatabaseReference hobiku;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitleTextColor(Color.BLACK);
            setSupportActionBar(toolbar);

            viewPager = (ViewPager) findViewById(R.id.viewpager);
            setupViewPager(viewPager);

            tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);
            getSupportActionBar().setElevation(0);


            DBOpenHelper.getInstance(getApplicationContext());
            hobiku = FirebaseDatabase.getInstance().getReference().child("hobiku");
            dummyData();

            hobiku.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String hobi_saya = dataSnapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        private void setupViewPager(ViewPager viewPager) {
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new ConversationList(), "Chats");
            adapter.addFragment(new PersonList(), "Friends");
            adapter.addFragment(new GroupList(), "Groups");
            viewPager.setAdapter(adapter);
        }

        class ViewPagerAdapter extends FragmentPagerAdapter {
            private final List<Fragment> mFragmentList = new ArrayList<>();
            private final List<String> mFragmentTitleList = new ArrayList<>();

            public ViewPagerAdapter(FragmentManager manager) {
                super(manager);
            }

            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            public void addFragment(Fragment fragment, String title) {
                Log.i(TAG, "Title :" +title);
                mFragmentList.add(fragment);
                mFragmentTitleList.add(title);

                Log.i(TAG, mFragmentTitleList.toString());
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentTitleList.get(position);
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.activity_main_menu, menu);
            return true;
        }

        private void dummyData() {
            /**
             * Dummy Data
             */

            long insert = 0;
            ConversationHolder cholder = new ConversationHolder();
            cholder.set_Id(1);
            cholder.setPersonGroup(100);
            cholder.setChatGroupMessage(200);
            cholder.setFlag(0);
            cholder.setUnreadCounter(4);
            ConversationDB dbs = new ConversationDB(getApplicationContext());
            insert = dbs.insertRecord(cholder.toCommValues(), true);

            cholder.set_Id(2);
            cholder.setPersonGroup(200);
            cholder.setChatGroupMessage(300);
            cholder.setFlag(1);
            cholder.setUnreadCounter(5);
            insert = dbs.insertRecord(cholder.toCommValues(), true);

            long insert1 = 0;
            PersonHolder pholder = new PersonHolder();
            pholder.set_Id(20001);
            pholder.setName("Jano Laskar");
            pholder.setOnline(1);
            pholder.setUpdated(0);
            PersonDB pdb = new PersonDB(getApplicationContext());
            insert1 = pdb.insertRecord(pholder.toCommValues(), true);

            pholder.set_Id(20002);
            pholder.setName("Ahmad Hidayat");
            pholder.setOnline(0);
            pholder.setUpdated(1);
            insert1 = pdb.insertRecord(pholder.toCommValues(), true);

            long insert2 = 0;
            FriendHolder fholder = new FriendHolder();
            fholder.setPerson(1);
            fholder.setFriend(2);
            FriendDB fdb = new FriendDB(getApplicationContext());
            insert2 = fdb.insertRecord(fholder.toCommValues(), true);

            long insert3 = 0;
            GroupHolder gholder = new GroupHolder();
            gholder.set_Id(300);
            gholder.setName("EasySoft");
            GroupDB gdb = new GroupDB(getApplicationContext());
            insert3 = gdb.insertRecord(gholder.toCommValues(), true);

            long insert4 = 0;
            GroupMemberHolder gmholder = new GroupMemberHolder();
            gmholder.setGroup(300);
            gmholder.setPerson(2);
            GroupMemberDB gmdb = new GroupMemberDB(getApplicationContext());
            insert4 = gmdb.insertRecord(gmholder.toCommValues(), true);

            long insert5 = 0;
            PrivateChatHolder holders = new PrivateChatHolder();
            holders.setId(200);
            holders.setPerson(20001);
            holders.setFriend(20002);
            holders.setMessage("hai");
            holders.setWhen("13/Sep/2017 11:18:18");
            holders.setDelivered("13/Sep/2017");
            holders.setRead("13/Sep/2017");
            holders.setMine(false);
            PrivateChatDB dbss = new PrivateChatDB(getApplicationContext());
            insert5 = dbss.insertRecord(holders.toCommValues(), true);

            long insert6 = 0;
            GroupChatHolder holderss = new GroupChatHolder();
            holderss.setId(300);
            holderss.setPerson(20001);
            holderss.setFriend(20002);
            holderss.setMessage("Raisa ngaji yuuk...");
            holderss.setWhen("13/Sep/2017 07:20:18");
            holderss.setDelivered("13/Sep/2017");
            holderss.setRead("13/Sep/2017");
            holderss.setMine(false);
            GroupChatDB dbsss = new GroupChatDB(getApplicationContext());
            insert6 = dbsss.insertRecord(holderss.toCommValues(), true);




        }

    }
