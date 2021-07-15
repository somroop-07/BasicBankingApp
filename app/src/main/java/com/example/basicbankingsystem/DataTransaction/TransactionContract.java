package com.example.basicbankingsystem.DataTransaction;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class TransactionContract {

    private TransactionContract() {
    }



    /**
     * Initialising the URI components for contacting to the content provider
     */

    public static final String CONTENT_AUTHORITY = "com.example.basicbankingsystem";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_TRANSACTIONS = "Transactions";

    public final static class TransactionEntry implements BaseColumns{

            /**
             *   Declaring the content URI
             */

        public static final Uri Content_Uri = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TRANSACTIONS);

        /**
         * The MIME type  for the user clicked
         */

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRANSACTIONS;

        /**
         * Naming the users database table and its components
         */
        public static String TABLE_NAME = "Transactions";
        public static String _ID = BaseColumns._ID;
        public static String Column_Sender_name = "Sender";
        public static String Column_Receiver_name = "Receiver";
        public static String Column_Amount = "Amount";
        public static String Column_Status = "Transaction_Status";
    }
}
