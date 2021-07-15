package com.example.basicbankingsystem.DataTransaction;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.basicbankingsystem.DataTransaction.TransactionContract.TransactionEntry;


public class Transactionsdb extends SQLiteOpenHelper {

    /**
     * Initialising database_version and data_base name
     */

    private static final int database_version=1;
    private static final String database_name="transactioninfo.db";


    public Transactionsdb(Context context){
        super(context, database_name, null,database_version);
    }

    /**
     * Creating the database table
     */

    @Override
    public void onCreate(SQLiteDatabase db) {



        String CREATE_TRANSACTIONSTABLE= " CREATE TABLE "+ TransactionEntry.TABLE_NAME +"("
                + TransactionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TransactionEntry.Column_Sender_name + " TEXT NOT NULL, "
                + TransactionEntry.Column_Receiver_name + " TEXT NOT NULL, "
                + TransactionEntry.Column_Amount + " TEXT, "
                + TransactionEntry.Column_Status + " TEXT);";

        db.execSQL(CREATE_TRANSACTIONSTABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
