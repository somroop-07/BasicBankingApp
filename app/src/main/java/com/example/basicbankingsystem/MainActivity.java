package com.example.basicbankingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button button_users;
    private Button button_transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getting ID of the buttons
        button_users=findViewById(R.id.Users_Button);
        button_transaction=findViewById(R.id.Transaction_Button);
        //making the buttons work upon clicking
        setUpButtons();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    /**
     * Intents to go the desired page
     */

    public void setUpButtons()
    {
        button_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,Users.class);
                startActivity(intent);
            }
        });
        button_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,Transactions.class);
                startActivity(intent);
            }
        });
    }
}