package com.example.basicbankingsystem;

import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.basicbankingsystem.Data_Users.UserContract;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailsScreen extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor>, Dialogbox.DialogboxListener {
   private TextView User_name;
    private TextView account_number;
    private TextView phone_number;
    private TextView email;
    private TextView balance;
    private  Button transfer;
    private Uri uri;
    private String Balance;
    private String Name;
    private String id;
    private Dialogbox dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_screen);
        setupViews();
        moneyTransfer();
    }

    /**
     * Getting view ids from the layout file
     * Getting intent along with information from Users screen about the particular user clicked
     */
    private void setupViews()
    {
        User_name= findViewById(R.id.name_details);
        account_number=findViewById(R.id.account_details);
        phone_number=findViewById(R.id.phone_details);
        email=findViewById(R.id.email_details);
        balance=findViewById(R.id.Balance_details);
        transfer=findViewById(R.id.transfer_button);
        Intent intent=getIntent();
       uri= intent.getData();
        getLoaderManager().initLoader(0, null, this);


    }

    /**
     *   Opening a dialog box to transfer money
     */

    private void moneyTransfer()
    {
        transfer.setOnClickListener(v -> {
              OpenDialog();


        });
    }


    private void OpenDialog()
    {
        dialog=new Dialogbox(Balance);
        dialog.show(getSupportFragmentManager(),"Enter amount");

    }


    /**
     *  Getting the parameters that we need from the cursor loader
     */

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] Projection={
                UserContract.UserEntry._ID,
                UserContract.UserEntry.COLUMN_NAME,
                UserContract.UserEntry.COLUMN_BALANCE,
                UserContract.UserEntry.COLUMN_PHONE,
                UserContract.UserEntry.COLUMN_EMAIL,
                UserContract.UserEntry.COLUMN_ACCOUNT
        };
        return new android.content.CursorLoader(this, uri,Projection,null,null,null);
    }

    /**
     *  Extracting and displaying the values on the screen
     */

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
      if(data.moveToFirst())
      {
          int Column_id=data.getColumnIndex(UserContract.UserEntry._ID);
          int Column_name=data.getColumnIndex(UserContract.UserEntry.COLUMN_NAME);
          int Column_balance=data.getColumnIndex(UserContract.UserEntry.COLUMN_BALANCE);
          int Column_phone=data.getColumnIndex(UserContract.UserEntry.COLUMN_PHONE);
          int Column_email=data.getColumnIndex(UserContract.UserEntry.COLUMN_EMAIL);
          int Column_account=data.getColumnIndex(UserContract.UserEntry.COLUMN_ACCOUNT);

          id= data.getString(Column_id);
          Name=data.getString(Column_name);
          Balance=data.getString(Column_balance);
          String Phone=data.getString(Column_phone);
          String Email=data.getString(Column_email);
          String Account=data.getString(Column_account);
          String mask = Account.replaceAll("\\w(?=\\w{4})", "X");

          Long user_balance_temp=Long.parseLong(Balance);
          String user_balance_fin= NumberFormat.getNumberInstance(Locale.UK).format(user_balance_temp).toString();
          String final_balance="₹ "+user_balance_fin;

          User_name.setText(Name);
          account_number.setText(mask);
          phone_number.setText(Phone);
          email.setText(Email);
          balance.setText(final_balance);



      }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
          finish();
    }

    /**
     *  Information we get from the dialog box about the money to be transferred
     *  Setting up intent to open up the pick user page and passing necessary info
     */

    @Override
    public void applyText(String balance) {
        if(balance.equals("-1"))
            OpenDialog();
        else if(!balance.isEmpty()) {
            Intent intent = new Intent(DetailsScreen.this,SendMoney.class);
            intent.putExtra("User_Id",id);
            intent.putExtra("Transfer_amount",balance);
            intent.putExtra("Current Balance",Balance);
            intent.putExtra("Sender_name",Name);
            startActivity(intent);
        }
    }
}