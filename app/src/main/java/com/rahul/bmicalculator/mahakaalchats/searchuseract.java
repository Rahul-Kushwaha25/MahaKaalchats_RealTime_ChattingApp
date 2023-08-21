package com.rahul.bmicalculator.mahakaalchats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.rahul.bmicalculator.mahakaalchats.adaptors.result_recview_adaptor;
import com.rahul.bmicalculator.mahakaalchats.models.usernamemodel;
import com.rahul.bmicalculator.mahakaalchats.utils.firebaseutil;

public class searchuseract extends AppCompatActivity {
    ImageButton backbtn,searchbtn;
    EditText searchuserInput;
    RecyclerView resultrecview;
    result_recview_adaptor adaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchuseract);

        backbtn = findViewById(R.id.backBtn);
        searchuserInput = findViewById(R.id.searchuser_input);
        searchbtn = findViewById(R.id.searchBtn);
        resultrecview = findViewById(R.id.resultRecview);

        searchuserInput.requestFocus();

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String entername = searchuserInput.getText().toString();
                if(entername.isEmpty() || entername.length()<2){
                    searchuserInput.setError("invalid");
                    return;
                }
                getresult(entername);
            }
        });






    }

    void getresult(String enteredname){

        Query queryy = firebaseutil.alluserCollectionReff()
                .whereGreaterThanOrEqualTo("username",enteredname)
                        .whereLessThanOrEqualTo("username",enteredname+'\uf8ff');

        queryy.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().isEmpty()) {
                    Log.d("getresult", "No matching documents found.");
                    // Show a message or handle empty results
                }
            } else {
                Log.d("getresult", "Query failed with: " + task.getException());
            }
        });

        FirestoreRecyclerOptions<usernamemodel> options = new FirestoreRecyclerOptions.Builder<usernamemodel>()
                .setQuery(queryy,usernamemodel.class).build();

        Log.d("getresult", "Query: " + queryy.toString());



        adaptor = new result_recview_adaptor(options,getApplicationContext());
        resultrecview.setLayoutManager(new LinearLayoutManager(this));
        resultrecview.setAdapter(adaptor);
        adaptor.startListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adaptor!=null){
            adaptor.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adaptor!=null){
            adaptor.stopListening();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adaptor!=null){
            adaptor.startListening();
        }
    }
}