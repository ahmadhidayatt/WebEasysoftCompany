package com.example.janolaskar.aCU.sqlite;

import android.content.Context;
import android.database.Cursor;

import com.example.janolaskar.aCU.holder.GroupChatHolder;

import java.util.ArrayList;

/**
 * Created by ahmad on 11/09/17.
 */

public class GroupChatDB extends DB{
    public static final String TABLE_NAME = "CHAT_GROUP_DB";
    public static final String[] TABLE_FIELDS = new String[] {
            GroupChatHolder.FIELD__ID,
            GroupChatHolder.FIELD_PERSON,
            GroupChatHolder.FIELD_FRIEND,
            GroupChatHolder.FIELD_MESSAGE,
            GroupChatHolder.FIELD_WHEN,
            GroupChatHolder.FIELD_DELIVERED,
            GroupChatHolder.FIELD_READ,
//            ConversationHolder.FIELD_USER_ACCESS,
//            ConversationHolder.FIELD_USER_CODE,
//            ConversationHolder.FIELD_THUMB_ID,
//            ConversationHolder.FIELD_QUOTE,
//            ConversationHolder.FIELD_CONNECTED,
//            ConversationHolder.FIELD_LAST_UPDATE,
//            ConversationHolder.FIELD_NAME,
//            ConversationHolder.FIELD_TIMEZONE,
//            ConversationHolder.FIELD_EX_BUDDY_STATUS,
//            ConversationHolder.FIELD_EX_BUDDY_BLOCK,
//            ConversationHolder.FIELD_POSITION_ID,
//            ConversationHolder.FIELD_POSITION_DESC,
//            ConversationHolder.FIELD_MEDIA,
//            ConversationHolder.FIELD_CONNECTION_ID,
//            ConversationHolder.FIELD_EMAIL,
//            ConversationHolder.FIELD_PHONE,
    };
    public static final String ALTER_QUERY = "";
    public static final String CREATE_QUERY = "CREATE TABLE \""+TABLE_NAME+"\" " +
            "(\""+ GroupChatHolder.FIELD__ID+"\" INTEGER PRIMARY KEY  NOT NULL " +
            ",\""+ GroupChatHolder.FIELD_PERSON+"\" INTEGER " +
            ",\""+ GroupChatHolder.FIELD_FRIEND+"\" INTEGER " +
            ",\""+ GroupChatHolder.FIELD_MESSAGE+"\" TEXT" +
            ",\""+ GroupChatHolder.FIELD_WHEN+"\" TEXT" +
            ",\""+ GroupChatHolder.FIELD_DELIVERED+"\" TEXT" +
            ",\""+ GroupChatHolder.FIELD_READ+"\"TEXT)";


//            ",\""+ConversationHolder.FIELD_USER_ACCESS+"\" TEXT UNIQUE" +
//            ",\""+ConversationHolder.FIELD_USER_CODE+"\" TEXT" +
//            ",\""+ConversationHolder.FIELD_THUMB_ID+"\" TEXT" +
//            ",\""+ConversationHolder.FIELD_QUOTE+"\" TEXT" +
//            ",\""+ConversationHolder.FIELD_CONNECTED+"\" TEXT" +
//            ",\""+ConversationHolder.FIELD_LAST_UPDATE+"\" TEXT" +
//            ",\""+ConversationHolder.FIELD_NAME+"\" TEXT" +
//            ",\""+ConversationHolder.FIELD_TIMEZONE+"\" TEXT" +
//            ",\""+ConversationHolder.FIELD_EX_BUDDY_STATUS+"\" TEXT" +
//            ",\""+ConversationHolder.FIELD_EX_BUDDY_BLOCK+"\" TEXT" +
//            ",\""+ConversationHolder.FIELD_POSITION_ID+"\" TEXT" +
//            ",\""+ConversationHolder.FIELD_POSITION_DESC+"\" TEXT" +
//            ",\""+ConversationHolder.FIELD_MEDIA+"\" TEXT" +
//            ",\""+ConversationHolder.FIELD_CONNECTION_ID+"\" TEXT" +
//            ",\""+ConversationHolder.FIELD_EMAIL+"\" TEXT" +
//            ",\""+ConversationHolder.FIELD_PHONE+"\" TEXT)";

    public GroupChatDB(Context p_context) {
        super(p_context, TABLE_NAME);
    }

    public synchronized GroupChatHolder getFirstRecord(String p_where) {
        GroupChatHolder holder = null;
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, null, "1");
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    holder = new GroupChatHolder(cursor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
        return holder;
    }

    public synchronized GroupChatHolder getLastRecord(String p_where) {
        GroupChatHolder holder = null;
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, TABLE_FIELDS[0] + " DESC", "1");
            if (cursor != null) {
                if (cursor.moveToLast()) {
                    holder = new GroupChatHolder(cursor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
        return holder;
    }

    public synchronized ArrayList<GroupChatHolder> getRecords(String p_where) {
        return getRecords(p_where, null);
    }

    public synchronized ArrayList<GroupChatHolder> getRecords(String p_where, String p_order) {
        ArrayList<GroupChatHolder> holders = new ArrayList<GroupChatHolder>();
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, p_order);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    holders.add(new GroupChatHolder(cursor));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
        return holders;
    }


    public void getRecords(String p_where, String p_order, GroupChatDB.FetchListener p_fetch) {
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, p_order);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    p_fetch.onFetch(new GroupChatHolder(cursor));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
    }

    public interface FetchListener {
        public void onFetch(GroupChatHolder person);
    }

    public static String getCreateTableQuery() {
        return GroupChatDB.CREATE_QUERY;
    }

    public synchronized GroupChatHolder getRecord(String p_where) {
        GroupChatHolder holder = null;
        Cursor cursor = null;

        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, null, null, "1");
            if (cursor != null) {
                if(cursor.moveToFirst()) {
                    holder = new GroupChatHolder(cursor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }

        return holder;
    }
}
