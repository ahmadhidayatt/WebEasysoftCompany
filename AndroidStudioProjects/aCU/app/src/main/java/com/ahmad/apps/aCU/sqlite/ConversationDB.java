package com.example.janolaskar.aCU.sqlite;

import android.content.Context;
import android.database.Cursor;

import com.example.janolaskar.aCU.holder.ConversationHolder;

import java.util.ArrayList;

/**
 * Created by janolaskar on 9/10/17.
 */

public class ConversationDB extends DB {
    public static final String TABLE_NAME = "CONVERSATION_LIST";
    public static final String[] TABLE_FIELDS = new String[] {
            ConversationHolder.FIELD__ID,
            ConversationHolder.FIELD_PERSON_GROUP,
            ConversationHolder.FIELD_CHAT_GROUP_MESSAGE,
            ConversationHolder.FIELD_FLAG,
            ConversationHolder.FIELD_UNREAD_COUNTER,
    };
    public static final String ALTER_QUERY = "";
    public static final String CREATE_QUERY = "CREATE TABLE \""+TABLE_NAME+"\" " +
            "(\"_id\" integer PRIMARY KEY  NOT NULL " +
            ",\""+ConversationHolder.FIELD_PERSON_GROUP+"\" integer UNIQUE" +
            ",\""+ConversationHolder.FIELD_CHAT_GROUP_MESSAGE+"\" integer UNIQUE" +
            ",\""+ConversationHolder.FIELD_FLAG+"\" integer" +
            ",\""+ConversationHolder.FIELD_UNREAD_COUNTER+"\" integer)";


    public ConversationDB(Context p_context) {
        super(p_context, TABLE_NAME);
    }

    public synchronized ConversationHolder getFirstRecord(String p_where) {
        ConversationHolder holder = null;
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, null, "1");
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    holder = new ConversationHolder(cursor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
        return holder;
    }

    public synchronized ConversationHolder getLastRecord(String p_where) {
        ConversationHolder holder = null;
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, TABLE_FIELDS[0] + " DESC", "1");
            if (cursor != null) {
                if (cursor.moveToLast()) {
                    holder = new ConversationHolder(cursor);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
        return holder;
    }

    public synchronized ArrayList<ConversationHolder> getRecords(String p_where) {
        return getRecords(p_where, null);
    }

    public synchronized ArrayList<ConversationHolder> getRecords(String p_where, String p_order) {
        ArrayList<ConversationHolder> holders = new ArrayList<ConversationHolder>();
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, p_order);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    holders.add(new ConversationHolder(cursor));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
        return holders;
    }


    public void getRecords(String p_where, String p_order, FetchListener p_fetch) {
        Cursor cursor = null;
        try {
            open();
            cursor = fetch(TABLE_FIELDS, p_where, p_order);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    p_fetch.onFetch(new ConversationHolder(cursor));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) { cursor.close(); }
        }
    }

    public interface FetchListener {
        public void onFetch(ConversationHolder person);
    }

    public static String getCreateTableQuery() {
        return ConversationDB.CREATE_QUERY;
    }
}
