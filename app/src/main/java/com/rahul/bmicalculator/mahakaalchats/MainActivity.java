package com.rahul.bmicalculator.mahakaalchats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    ImageButton searchbtn;
    BottomNavigationView bottomNavigationView;
    chatfragment chatfrag;

    profilefragment profilefrag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchbtn = findViewById(R.id.searchBtn);
        bottomNavigationView = findViewById(R.id.bottonNavigationBar);
        chatfrag = new chatfragment();
        profilefrag = new profilefragment();



        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,searchuseract.class);
                startActivity(intent);
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId()==R.id.chatsmenu){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,chatfrag).commit();
                }
                if(item.getItemId()==R.id.profilemenu){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,profilefrag).commit();
                }


                return true;
            }

        });


        bottomNavigationView.setSelectedItemId(R.id.chatsmenu);


    }
}