package com.rahul.bmicalculator.mahakaalchats.adaptors;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rahul.bmicalculator.mahakaalchats.R;
import com.rahul.bmicalculator.mahakaalchats.chatroom;
import com.rahul.bmicalculator.mahakaalchats.models.ChatMessageModel;
import com.rahul.bmicalculator.mahakaalchats.utils.androidUtil;
import com.rahul.bmicalculator.mahakaalchats.utils.firebaseutil;

public class chat_msg_rec_adaptor  extends FirestoreRecyclerAdapter< ChatMessageModel ,chat_msg_rec_adaptor.chatroomviewholder>{

    Context context;
    public chat_msg_rec_adaptor(@NonNull FirestoreRecyclerOptions<ChatMessageModel> options , Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull chatroomviewholder holder, int position, @NonNull ChatMessageModel model) {
        if(model.getSender_userid().equals(firebaseutil.currentUserId())){
            holder.right_msg_layout.setVisibility(View.VISIBLE);
            holder.left_msg_layout.setVisibility(View.GONE);
            holder.right_msg_text.setText(model.getMessage());
        }
        else{
            holder.left_msg_layout.setVisibility(View.VISIBLE);
            holder.right_msg_layout.setVisibility(View.GONE);
            holder.left_msg_text.setText(model.getMessage());
        }

    }

    @NonNull
    @Override
    public chatroomviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("onCreateViewHolder", "onCreateViewHolder:onCreateViewHolder ");
        View view = LayoutInflater.from(context).inflate(R.layout.chat_msg_row,parent,false);
        return new chatroomviewholder(view);
    }

    class   chatroomviewholder extends RecyclerView.ViewHolder {


        LinearLayout right_msg_layout,left_msg_layout;
        TextView right_msg_text,left_msg_text;
        public chatroomviewholder(@NonNull View itemView) {
            super(itemView);
            right_msg_layout = itemView.findViewById(R.id.right_msg);
            left_msg_layout = itemView.findViewById(R.id.left_msg);
            right_msg_text = itemView.findViewById(R.id.right_msg_text);
            left_msg_text = itemView.findViewById(R.id.left_msg_text);



        }
    }
}

