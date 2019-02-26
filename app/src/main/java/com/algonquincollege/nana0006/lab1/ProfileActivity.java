package com.algonquincollege.nana0006.lab1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
//import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;


public class ProfileActivity extends AppCompatActivity{

  //  private static final String ACTIVITY_NAME = "ProfileActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView mImageButton;
    private Button goToChatButton;
    EditText mail ;
    EditText pass ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_lab3);
        mail = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        mImageButton = (ImageButton) findViewById(R.id.button);
        goToChatButton = (Button) findViewById(R.id.goToChat);

        mail.setText(getIntent().getStringExtra("key1"));
        pass.setText(getIntent().getStringExtra("key2"));

        mImageButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                //Toast.makeText(this, "Button Clicked", Toast.LENGTH_LONG).show();
            dispatchTakePictureIntent();
            }
        });

        goToChatButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                //Toast.makeText(this, "Button Clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ProfileActivity.this, ChatRoomActivity.class);
                ProfileActivity.this.startActivity(intent);
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }

    }
        @Override
    protected void onStart() {
            super.onStart();
            Log.e("ProfileActivity", "In function: " + "onStart");
        }

    @Override
    protected void onResume() {
        super.onResume();
                Log.e("ProfileActivity", "In function: " + "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("ProfileActivity", "In function: " + "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("ProfileActivity", "In function: " + "onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("ProfileActivity", "In function: " + "onDestroy");

    }

 //   @Override
 //   protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
 //       super.onActivityResult(requestCode, resultCode, data);
 //   }
}
