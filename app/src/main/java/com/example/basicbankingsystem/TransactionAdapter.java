package com.example.basicbankingsystem;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.basicbankingsystem.DataTransaction.TransactionContract.TransactionEntry;

import java.text.NumberFormat;
import java.util.Locale;

public class TransactionAdapter extends CursorAdapter {
    public TransactionAdapter(Context context, Cursor c) {
        super(context, c,0);
    }

    /**
     * Getting blank view
     */

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.transaction_list,parent,false);
    }

    /**
     * Binding elements to the view
     */

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView sender=view.findViewById(R.id.sender_name);
        TextView receiver=view.findViewById(R.id.receiver_name);
        TextView amount=view.findViewById(R.id.transaction_amount);
        TextView status=view.findViewById(R.id.transaction_status);

        int senderColumn=cursor.getColumnIndex(TransactionEntry.Column_Sender_name);
        int receiverColumn=cursor.getColumnIndex(TransactionEntry.Column_Receiver_name);
        int amountColumn=cursor.getColumnIndex(TransactionEntry.Column_Amount);
        int statusColumn=cursor.getColumnIndex(TransactionEntry.Column_Status);

        String sender_name= cursor.getString(senderColumn);
        String receiver_name=cursor.getString(receiverColumn);
        String amount_money=cursor.getString(amountColumn);
        Long amount_money_temp=Long.parseLong(amount_money);
        String amount_money_fin= NumberFormat.getNumberInstance(Locale.UK).format(amount_money_temp).toString();
        String final_amount="₹ "+amount_money_fin;
        String status_transaction=cursor.getString(statusColumn);

        sender.setText(sender_name);
        receiver.setText(receiver_name);
        amount.setText(final_amount);
        status.setText(status_transaction);
        if(status_transaction.equals("Failed"))
            status.setTextColor(Color.parseColor("#CA2020"));
        else
            status.setTextColor(Color.parseColor("#20CA27"));



    }
}
