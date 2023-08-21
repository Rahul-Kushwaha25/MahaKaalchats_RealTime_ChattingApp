package com.rahul.bmicalculator.mahakaalchats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.rahul.bmicalculator.mahakaalchats.utils.firebaseutil;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(firebaseutil.isloggedin()){
                    Intent intent = new Intent(splash.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(splash.this,Login_page.class);
                    startActivity(intent);
                }
                finish();

            }
        },3000);
    }
}