package com.rahul.bmicalculator.mahakaalchats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.rahul.bmicalculator.mahakaalchats.models.usernamemodel;
import com.rahul.bmicalculator.mahakaalchats.utils.firebaseutil;

public class loginuseractivity extends AppCompatActivity {

    EditText usernameEdt;
    usernamemodel usernamemodelobj;
    Button unextbtn;
    String phonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginuseractivity);

        usernameEdt = findViewById(R.id.username_edt);
        unextbtn = findViewById(R.id.username_next_btn);

        phonenumber = getIntent().getExtras().getString("phonekey");


        unextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setusername();
            }
        });


    }
    void setusername(){
       String username = usernameEdt.getText().toString();
       if(username.isEmpty() || username.length()<3){
           usernameEdt.setError("username length atleast greater than 3");
           return;
       }
       if(usernamemodelobj!=null){
           usernamemodelobj.setUsername(username);
       }else{
           usernamemodelobj = new usernamemodel(phonenumber,username, Timestamp.now(),firebaseutil.currentUserId());
       }
       firebaseutil.currentUserDetails().set(usernamemodelobj).addOnCompleteListener(new OnCompleteListener<Void>() {
           @Override
           public void onComplete(@NonNull Task<Void> task) {
               if(task.isSuccessful()){
                   Intent intent = new Intent(loginuseractivity.this,MainActivity.class);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   startActivity(intent);
               }
           }
       });


    }
    void getusername(){
        firebaseutil.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    usernamemodelobj = task.getResult().toObject(usernamemodel.class);
                    if(usernamemodelobj!=null){
                        usernameEdt.setText(usernamemodelobj.getUsername());
                    }
                }
            }
        });
    }
}