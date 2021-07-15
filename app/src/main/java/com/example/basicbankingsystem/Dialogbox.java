package com.example.basicbankingsystem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class Dialogbox extends DialogFragment  {

    private final String present_balance;
    public Dialogbox(String balance)
    {
        present_balance=balance;
    }

    private EditText amount;
    private DialogboxListener listener;
    private AlertDialog dialog;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener=(DialogboxListener)context;
    }

    /**
     *  Creating the dialogbox
     */

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.dialogbox, null);
        builder.setView(view).setTitle("Enter amount (₹)");
        amount=view.findViewById(R.id.Amount);
             builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog,int id) {

                 }

    })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         dialog.dismiss();
                         CancelTransaction();
                    }
                });

             dialog=builder.create();
             dialog.show();

        /**
         *  Setting up roles of the positive button
         */

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     String transfer_amount = amount.getText().toString().trim();
                     if (transfer_amount.isEmpty()){
                         amount.setError("Enter Amount");}
                     else {
                         long transfer = Long.parseLong(transfer_amount);
                         long current_balance = Long.parseLong(present_balance);
                         if (transfer > current_balance)
                             amount.setError("Account Balance exceeded");
                         else if(transfer<=0)
                             amount.setError("Enter valid amount");
                         else
                             listener.applyText(transfer_amount);
                     }
                 }
             });
        /**
         *  OnBackPressed Function defined
         */

       dialog.setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                dialog.dismiss();
                CancelTransaction();
                return true;
            }
            return false;
        });
        return dialog;
    }

    /**
     *   If user wants to cancel transaction
     */

    public void CancelTransaction()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder((getActivity()));
        builder.setTitle("Do you want to cancel the transaction?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        listener.applyText("-1");

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Interface to send info to DetailsScreen.class
     */

    public interface DialogboxListener{
        void applyText(String balance);
    }

}
