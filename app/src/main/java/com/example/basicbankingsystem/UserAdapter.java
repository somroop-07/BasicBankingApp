package com.example.basicbankingsystem;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.basicbankingsystem.Data_Users.UserContract;

import java.text.NumberFormat;
import java.util.Locale;

public class UserAdapter extends CursorAdapter {
    public UserAdapter(Context context, Cursor c) {
        super(context, c,0);
    }

    /**
     * Getting blank view
     */

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.users_list,parent,false);
    }

    /**
     * Binding elements to the view
     */

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView name= view.findViewById(R.id.user_name);
        TextView balance= view.findViewById(R.id.user_balance);

        int nameColumn = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_NAME);
        int balanceColumn = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_BALANCE);

        String user_name=cursor.getString(nameColumn);
        String user_balance=cursor.getString(balanceColumn);
        Long user_balance_temp=Long.parseLong(user_balance);
        String user_balance_fin=NumberFormat.getNumberInstance(Locale.UK).format(user_balance_temp).toString();


        name.setText(user_name);
        String balance_final="₹ "+user_balance_fin;
        balance.setText(balance_final);
    }
}
