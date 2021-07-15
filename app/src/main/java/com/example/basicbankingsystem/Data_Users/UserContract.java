package com.example.basicbankingsystem.Data_Users;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class UserContract {
    private UserContract(){}

    /**
     * Initialising the URI components for contacting to the content provider
     */

    public static final String CONTENT_AUTHORITY="com.example.basicbankingsystem";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_USERS="Users";


    public final static class UserEntry implements BaseColumns
    {
        /**
         *   Declaring the content URI
         */

        public static final Uri Content_URI= Uri.withAppendedPath(BASE_CONTENT_URI,PATH_USERS);

        /**
         * The MIME type  for the user clicked
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USERS;
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USERS;


        /**
         * Naming the users database table and its components
         */

        public static String TABLE_NAME= "Users";
        public static String _ID= BaseColumns._ID;
        public static String COLUMN_NAME="Name";
        public static String COLUMN_ACCOUNT="Account";
        public static String COLUMN_BALANCE="Balance";
        public static String COLUMN_EMAIL="Email";
        public static String COLUMN_PHONE="Phone";


    }

}
