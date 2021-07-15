package com.example.basicbankingsystem;

import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.basicbankingsystem.DataTransaction.TransactionContract.TransactionEntry;


public class Transactions extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor>{
    private ListView transaction_list;
    private  TransactionAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        transaction_list=findViewById(R.id.list_transactions);
        LinearLayout Empty_view=findViewById(R.id.Empty_view);
        transaction_list.setEmptyView(Empty_view);
        getLoaderManager().initLoader(0, null, this);
        setupListView();
    }

    /**
     * Adapter setting up the List
     */

    public void setupListView()
    {

        adapter=new TransactionAdapter(this,null);
        transaction_list.setAdapter(adapter);
    }

    /**
     * Setting up the menu option
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete,menu);
        return true;
    }

    /**
     * Deleting from transaction database upon clicking menu option
     */

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int rows=getContentResolver().delete(TransactionEntry.Content_Uri,null,null);
        return true;
    }

    /**
     * Defining information we need for cursor loader to query
     */

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
       String[] Projections={
               TransactionEntry._ID,
               TransactionEntry.Column_Sender_name,
               TransactionEntry.Column_Receiver_name,
               TransactionEntry.Column_Amount,
               TransactionEntry.Column_Status
       };
        return new android.content.CursorLoader(this, TransactionEntry.Content_Uri,Projections,null,null,null);
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