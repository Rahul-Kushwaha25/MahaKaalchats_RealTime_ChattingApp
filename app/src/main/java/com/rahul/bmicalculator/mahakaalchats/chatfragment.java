package com.rahul.bmicalculator.mahakaalchats;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.rahul.bmicalculator.mahakaalchats.adaptors.recent_chat_rec_adaptor;
import com.rahul.bmicalculator.mahakaalchats.adaptors.result_recview_adaptor;
import com.rahul.bmicalculator.mahakaalchats.models.ChatroomModel;
import com.rahul.bmicalculator.mahakaalchats.models.usernamemodel;
import com.rahul.bmicalculator.mahakaalchats.utils.firebaseutil;


public class chatfragment extends Fragment {


    RecyclerView chat_frag_recView;
    recent_chat_rec_adaptor adaptor;
    public chatfragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chatfragment, container, false);

        chat_frag_recView = view.findViewById(R.id.chat_frag_rec_view);

        setupRecview();

        return view;
    }


    void setupRecview(){

        Query queryy = firebaseutil.getAllchatroomCollectionReff()
                .whereArrayContains("userids", firebaseutil.currentUserId())
                .orderBy("lastmsg_time", Query.Direction.DESCENDING);






        FirestoreRecyclerOptions<ChatroomModel> options = new FirestoreRecyclerOptions.Builder<ChatroomModel>()
                .setQuery(queryy,ChatroomModel.class).build();




        Log.d("setuprecChecking", "setupRecview: yes i m in setuprec ");
        adaptor = new recent_chat_rec_adaptor(options,getContext());
        chat_frag_recView.setLayoutManager(new LinearLayoutManager(getContext()));
        chat_frag_recView.setAdapter(adaptor);
        adaptor.startListening();


    }

    @Override
    public void onStart() {
        super.onStart();
        if(adaptor!=null){
            adaptor.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(adaptor!=null){
            adaptor.stopListening();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adaptor!=null){
            adaptor.notifyDataSetChanged();
        }
    }
}