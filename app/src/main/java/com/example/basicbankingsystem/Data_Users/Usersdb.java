package com.example.basicbankingsystem.Data_Users;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.basicbankingsystem.Data_Users.UserContract.UserEntry;

public class Usersdb extends SQLiteOpenHelper {

    /**
     * Initialising database_version and data_base name
     */

    private static final int database_version=1;
    private static final String database_name="userinfo.db";


     public Usersdb(Context context)
     {
         super(context,database_name,null,database_version);

     }

    /**
     * Creating the database table
     */

    @Override
    public void onCreate(SQLiteDatabase db) {



        String CREATE_USERSTABLE= " CREATE TABLE "+ UserEntry.TABLE_NAME +"("
                + UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UserEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + UserEntry.COLUMN_ACCOUNT + " TEXT, "
                + UserEntry.COLUMN_BALANCE + " TEXT, "
                + UserEntry.COLUMN_PHONE +" TEXT, "
                + UserEntry.COLUMN_EMAIL + " TEXT);";
          db.execSQL(CREATE_USERSTABLE);
        InsertUserData(db,"Rohit Roy", "021000021", "110000", "rohit123@gmail.com", "9876567893");
        InsertUserData(db,"Akansha Sharma", "011401533", "20000", "akanshalite@gmail.com", "8576839475");
        InsertUserData(db,"Sandy Sen", "091000019", "100000", "sandysen2002@gmail.com", "3658365830");
        InsertUserData(db,"Rita Agarwal", "0210006666", "420000", "rita2002@hotmail.com", "9375836573");
        InsertUserData(db,"Rahul Raj", "021000021", "1000000", "rahulgamer@gmail.com", "5849375738");
        InsertUserData(db,"Rohan Singh", "0210004234", "200000", "rohan1266@ggmail.com", "9192847568");
        InsertUserData(db,"Aahana Das", "0210003456", "125000", "aahanadas12@gmail.com", "3546789876");
        InsertUserData(db,"Sachin Sahoo", "0210007979", "120000", "sachinsahoo@gmail.com", "9876567876");
        InsertUserData(db,"Ananya Singh", "021000987", "900000", "ananyanew@hotmail.com", "6777876656");
        InsertUserData(db,"Sourin Malhotra", "0910004444", "8000", "sourin1212@gmail.com", "8876667565");


    }

    /**
     * Inserting values inside the database table
     */

    private void InsertUserData(SQLiteDatabase db,String Name,String Account,String Balance,String Email,String Phone)
    {

        ContentValues values=new ContentValues();
        values.put(UserEntry.COLUMN_NAME,Name);
        values.put(UserEntry.COLUMN_ACCOUNT,Account);
        values.put(UserEntry.COLUMN_BALANCE,Balance);
        values.put(UserEntry.COLUMN_EMAIL,Email);
        values.put(UserEntry.COLUMN_PHONE,Phone);

        db.insert(UserEntry.TABLE_NAME,null,values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
