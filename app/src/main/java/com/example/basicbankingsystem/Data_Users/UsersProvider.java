package com.example.basicbankingsystem.Data_Users;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.basicbankingsystem.DataTransaction.TransactionContract;
import com.example.basicbankingsystem.DataTransaction.Transactionsdb;
import com.example.basicbankingsystem.Data_Users.UserContract.UserEntry;

public class UsersProvider extends ContentProvider {

    public static final String LOG_TAG = UsersProvider.class.getSimpleName();

    /**
     *  Initialising values to different uri possible
     */

    private static final int USER_ID = 100;
    private static final int Transaction_ALL=102;
    private static final int USER_ALL=101;

    /**
     *   Creating a uri matcher and hence specifying the possible uri values we can get
     */

    private static final UriMatcher urimatch=new UriMatcher(UriMatcher.NO_MATCH);
    static {
        urimatch.addURI(UserContract.CONTENT_AUTHORITY,UserContract.PATH_USERS +"/#", USER_ID);
        urimatch.addURI(UserContract.CONTENT_AUTHORITY,UserContract.PATH_USERS , USER_ALL);
        urimatch.addURI(TransactionContract.CONTENT_AUTHORITY,TransactionContract.PATH_TRANSACTIONS,Transaction_ALL);
    }

    /**
     * Variables to access the databases created
     */
    private Usersdb mDbHelper;
    private Transactionsdb mDbHelper_Transaction;

    /**
     * Creating database objects
     */

    @Override
    public boolean onCreate() {
        mDbHelper=new Usersdb(getContext());
        mDbHelper_Transaction=new Transactionsdb(getContext());
        return true;
    }

    /**
     * Query method to read the database
     * Gets uri and matches with the uri matcher
     * If we get a desired uri, we match it and perform accordingly
     * Returns a cursor to read data from database
     */

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        if(urimatch.match(uri)==USER_ALL){
            SQLiteDatabase db=mDbHelper.getReadableDatabase();
            cursor=db.query(UserEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);}
        else if(urimatch.match(uri)==USER_ID)
        {   SQLiteDatabase db=mDbHelper.getReadableDatabase();
            selection = UserEntry._ID + "=?";
            selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
            cursor=db.query(UserEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
        }
        else if(urimatch.match(uri)==Transaction_ALL)
        {
            SQLiteDatabase db=mDbHelper_Transaction.getReadableDatabase();
            cursor=db.query(TransactionContract.TransactionEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
        }
        else
            throw new IllegalArgumentException("Cannot query unknown URI" + uri);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;

    }

    /**
     * Gets the MIME type and matches with urimatcher
     */

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        if(urimatch.match(uri)==USER_ALL)
            return UserEntry.CONTENT_LIST_TYPE;
        else if(urimatch.match(uri)==USER_ID)
            return UserEntry.CONTENT_ITEM_TYPE;
        else if(urimatch.match(uri)==Transaction_ALL)
            return TransactionContract.TransactionEntry.CONTENT_LIST_TYPE;
        else
            throw new IllegalArgumentException("Cannot getType of unknown URI" + uri);
    }

    /**
     * Insert method to insert into the database
     * Gets uri and matches with the appropriate database table
     * Returns uri
     */

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if(urimatch.match(uri)==USER_ALL)
            return insertUser(uri,values);
        else if(urimatch.match(uri)==Transaction_ALL)
            return insertTransaction(uri,values);
        else
            throw new IllegalArgumentException("Cannot insert unknown URI" + uri);

    }

    /**
     * getWritableDatabase() method to insert into the Transaction database
     */

    public Uri insertTransaction(Uri uri,ContentValues values) {
        SQLiteDatabase db = mDbHelper_Transaction.getWritableDatabase();
        long id = db.insert(TransactionContract.TransactionEntry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    /**
     * getWritableDatabase() method to insert into the Users database
     */

    public Uri insertUser(Uri uri,ContentValues Values)
    {
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        long id=db.insert(UserEntry.TABLE_NAME,null,Values);
        if(id==-1){
            Log.e(LOG_TAG,"Failed to insert row for " + uri);
        return null;}
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri,id);

    }

    /**
     * Update method to update values into the database
     * Gets uri and matches with the appropriate database table
     */

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        if(urimatch.match(uri)==USER_ID) {
            selection = UserEntry._ID + "=?";
            selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
            return updateUser(uri, values, selection, selectionArgs);
        }
        else
            throw new IllegalArgumentException("Cannot update unknown URI" + uri);

    }

    /**
     * getWritableDatabase() method to update values of database
     */

    public int updateUser(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs)
    {
        SQLiteDatabase db=mDbHelper.getWritableDatabase();
        int row=db.update(UserEntry.TABLE_NAME,values,selection,selectionArgs);
        if (row!= 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return row;

    }

    /**
     * Delete method to delete all values of the database
     */

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        if(urimatch.match(uri)==Transaction_ALL)
            return deleteTransaction(uri,selection,selectionArgs);
        else
            throw new IllegalArgumentException("Cannot insert unknown URI" + uri);

    }

    /**
     * getWritableDatabase() method to delete values of database
     */

    public int deleteTransaction(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs)
    {
        SQLiteDatabase db=mDbHelper_Transaction.getWritableDatabase();
        int rows=db.delete(TransactionContract.TransactionEntry.TABLE_NAME,selection,selectionArgs);
        if (rows == -1) {
            Log.e(LOG_TAG, "Failed to delete row for " + uri);
            return 0;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rows;
    }
    }

