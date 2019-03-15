package com.algonquincollege.nana0006.lab1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.CoordinatorLayout;


public class TestToolbar extends AppCompatActivity {

    CoordinatorLayout coordinatorLayout;
    String toaster;//= new String();
    String initial = "This is the initial message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        // getActionBar();
        // getSupportActionBar();

        //     coordinatorLayout = (CoordinatorLayout) findViewById(R.id
        //           .coordinatorLayout);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Toolbar tb = (Toolbar) findViewById(R.id.my_toolbar);
        tb.inflateMenu(R.menu.toobar);
        tb.setOnMenuItemClickListener(

                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });

        //   Toast.makeText(this.getBaseContext(),"You clicked on the overflow menu",
        //         Toast.LENGTH_SHORT).show();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.choice1:
                Toast.makeText(this.getApplicationContext(), initial,
                        Toast.LENGTH_SHORT).show();
                return true;

            case R.id.choice2:

                final LayoutInflater inflater = this.getLayoutInflater();
                View v = inflater.inflate(R.layout.dialogbox_message, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setCancelable(true);
                builder.setTitle("Lol");
                builder.setMessage("Message");
                builder.setView(v);

                final EditText name = (EditText) v.findViewById(R.id.text_dialog);


                // builder.setMessage("haha");
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //  EditText edited = (EditText) findViewById(R.id.text_dialog);
                        toaster = name.getText().toString();
                        initial = toaster;
                        dialog.dismiss();

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });

                builder.show(); //.cerete.show
                return true;

            case R.id.choice3:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...

              /*  Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Go Back ?", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "went back !", Snackbar.LENGTH_SHORT);
                                snackbar1.show();
                                //finishActivity(1);
                            }
                        });*/

                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.coordinatorLayout),
                        "Go back ?", Snackbar.LENGTH_INDEFINITE);
                mySnackbar.setAction("Finish", new MyUndoListener());
                mySnackbar.show();
                return true;

            case R.id.action_overflow:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...

                Toast.makeText(this.getBaseContext(), "You clicked on the overflow menu",
                        Toast.LENGTH_SHORT).show();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public class MyUndoListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            finish();
        }
    }

    public class MyDialogListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // finish();
        }
    }

    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}


