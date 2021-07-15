package com.example.basicbankingsystem;

import android.content.ContentUris;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.basicbankingsystem.Data_Users.UserContract.UserEntry;

public class Users extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor> {
    ListView user_list;
    private static final int USER_LOADER = 0;
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        user_list = findViewById(R.id.list_users);
        getLoaderManager().initLoader(USER_LOADER, null, this);
        SetupListView();
    }

    /**
     * Setting up the menu option
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_users,menu);
        return true;
    }

    /**
     * Going to transaction page upon selecting the menu option
     */

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent= new Intent(Users.this,Transactions.class);
        startActivity(intent);
        return true;
    }

    /**
     * Adapter setting up the List
     * Intent to go to user_details screen and passing the uri
     */


    private void SetupListView() {
        adapter = new UserAdapter(this, null);
        user_list.setAdapter(adapter);

        user_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(Users.this, DetailsScreen.class);
                Uri current_uri = ContentUris.withAppendedId(UserEntry.Content_URI,id);
                intent.setData(current_uri);
                startActivity(intent);

            }
        });

    }

    /**
     * Defining information we need for cursor loader to query
     */

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] Projection={
                UserEntry._ID,
                UserEntry.COLUMN_NAME,
                UserEntry.COLUMN_BALANCE
        };
        return new android.content.CursorLoader(this,UserEntry.Content_URI,Projection,null,null,null);
    }

    /**
     * adapter showing the info
     */


    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Users.this,MainActivity.class);
        startActivity(intent);
    }
}