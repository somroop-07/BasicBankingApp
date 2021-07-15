package com.example.basicbankingsystem;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.basicbankingsystem.DataTransaction.TransactionContract.TransactionEntry;
import com.example.basicbankingsystem.Data_Users.UserContract;

public class SendMoney extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor> {
    private ListView send_money;
    private UserAdapter adapter;
    private String id_user;
    private String transfer_amount;
    private String current_balance;
    private String sender_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);
        send_money=findViewById(R.id.list_send_to_user);
        Intent intent=getIntent();
        //id of Sender
        id_user=intent.getStringExtra("User_Id");
        //Balance of sender
        current_balance=intent.getStringExtra("Current Balance");
        //Amount to be transferred
        transfer_amount=intent.getStringExtra("Transfer_amount");
        //Name of sender
        sender_name=intent.getStringExtra("Sender_name");
        //Initiating cursor loader
        getLoaderManager().initLoader(0,null,this);
        setUpListView();
    }

    /**
     *  Cancel transaction action performed on onBackPressed
     */

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Do you want to cancel the transaction")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        InsertTransaction("Not selected","Failed");
                        Toast.makeText(SendMoney.this,"Transaction cancelled",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(SendMoney.this,Users.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", null);
         AlertDialog exit=builder.create();
         exit.show();


    }
    public void InsertTransaction(String receiver_name,String status)
    {
       Uri transaction_uri= TransactionEntry.Content_Uri;
        ContentValues values=new ContentValues();
        values.put(TransactionEntry.Column_Sender_name,sender_name);
        values.put(TransactionEntry.Column_Receiver_name,receiver_name);
        values.put(TransactionEntry.Column_Amount,transfer_amount);
        values.put(TransactionEntry.Column_Status,status);

        Uri getUri=getContentResolver().insert(transaction_uri,values);

    }

    private void setUpListView() {
        adapter = new UserAdapter(this, null);
        send_money.setAdapter(adapter);

        send_money.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Columns of which we need information
                String[] projection={UserContract.UserEntry.COLUMN_BALANCE, UserContract.UserEntry.COLUMN_NAME};
                String receiver_balance=null;
                String receiver_name=null;
                ContentValues values=new ContentValues();
                //uri to access the query method of sender id
                Uri current_uri = ContentUris.withAppendedId(UserContract.UserEntry.Content_URI,id);
                //we get the necessary details of the columns in cursor c
                Cursor c=getContentResolver().query(current_uri,projection,null,null,null);
                //getting values from the cursor
                if(c.moveToFirst()){
                    int column_name=c.getColumnIndex(UserContract.UserEntry.COLUMN_BALANCE);
                    int column_name_user=c.getColumnIndex(UserContract.UserEntry.COLUMN_NAME);
                    receiver_balance=c.getString(column_name);
                    receiver_name=c.getString(column_name_user);}
                c.close();
                Long receiver=Long.parseLong(receiver_balance);
                Long transfer=Long.parseLong(transfer_amount);
                Long new_balance_receiver=receiver+transfer;
                //entering the values into contentvalues
                values.put(UserContract.UserEntry.COLUMN_BALANCE,new_balance_receiver.toString());
                //updating the database using contentresolver
                int rows_1=getContentResolver().update(current_uri,values,null,null);

                ContentValues values_1=new ContentValues();
                //uri to access the query method of reciever id
                Uri current_uri_1 = ContentUris.withAppendedId(UserContract.UserEntry.Content_URI, Long.parseLong(id_user));
                Long sender=Long.parseLong(current_balance);
                Long new_balance_sender=sender-transfer;
                values_1.put(UserContract.UserEntry.COLUMN_BALANCE,new_balance_sender.toString());
                //updating database
                int rows_2=getContentResolver().update(current_uri_1,values_1,null,null);

                Intent intent_return=new Intent(SendMoney.this,Users.class);
                startActivity(intent_return);

                /**
                 *   Calling InsertTransaction to insert into the transaction database the transactions just performed
                 */

                if(rows_1!=0 && rows_2!=0){
                    Toast.makeText(SendMoney.this,"Transfer successful",Toast.LENGTH_LONG).show();
                    InsertTransaction(receiver_name,"Success");}
                else{
                    Toast.makeText(SendMoney.this,"Transfer failed",Toast.LENGTH_LONG).show();
                    InsertTransaction(receiver_name,"Failed");}
            }
        });
    }

    /**
     * Defining information we need for cursor loader to query
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] Projections={
                UserContract.UserEntry._ID,
                UserContract.UserEntry.COLUMN_NAME,
                UserContract.UserEntry.COLUMN_BALANCE

        };
       String selection= UserContract.UserEntry._ID + "!=?";
       String[] selectionArgs = new String[]{id_user};
        return new android.content.CursorLoader(this, UserContract.UserEntry.Content_URI,Projections,selection,selectionArgs,null);

    }

    /**
     * adapter showing the info
     */

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
          adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}