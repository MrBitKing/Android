package com.algonquincollege.nana0006.lab1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String mypreference = "mypref";
    public static final String Name = "nameKey";
    public static final String Email = "emailKey";
    EditText txtUsername, txtPassword, txtEmail;
    Button buttonReg2;

   // String Password ;
    //String email ;

    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

   // String name = txtUsername.getText().toString();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_lab3);

        txtPassword = (EditText) findViewById(R.id.password);
        txtEmail = (EditText) findViewById(R.id.email);
        buttonReg2 = (Button) findViewById(R.id.button);

        buttonReg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(MainActivity.this,ProfileActivity.class);
                intent.putExtra("key1",  txtEmail.getText().toString());
                intent.putExtra("key2", txtPassword.getText().toString());
                MainActivity.this.startActivity(intent);
            }
        });

        sharedPreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedPreferences.contains(Name)) {
            txtEmail.setText(sharedPreferences.getString(Name, ""));
        }
        if (sharedPreferences.contains(Name)) {
            txtPassword.setText(sharedPreferences.getString(Email, ""));

       }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        String pass = txtPassword.getText().toString();
        String mail = txtEmail.getText().toString();

       // final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Name, mail);
        editor.putString(Email, pass);
        editor.commit();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
