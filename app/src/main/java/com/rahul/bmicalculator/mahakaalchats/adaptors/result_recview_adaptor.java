package com.rahul.bmicalculator.mahakaalchats.adaptors;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rahul.bmicalculator.mahakaalchats.R;
import com.rahul.bmicalculator.mahakaalchats.chatroom;
import com.rahul.bmicalculator.mahakaalchats.models.usernamemodel;
import com.rahul.bmicalculator.mahakaalchats.utils.androidUtil;
import com.rahul.bmicalculator.mahakaalchats.utils.firebaseutil;

public class result_recview_adaptor  extends FirestoreRecyclerAdapter< usernamemodel ,result_recview_adaptor.userModelViewHolder>{

  Context context;
    public result_recview_adaptor(@NonNull FirestoreRecyclerOptions<usernamemodel> options , Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull userModelViewHolder holder, int position, @NonNull usernamemodel model) {
        Log.d("onBindViewHolder", "onBindViewHolder: onBindViewHolder");
        holder.usernametext.setText(model.getUsername());
        holder.phonenumber.setText(model.getPhone());
        if (model.getUserid().equals(firebaseutil.currentUserId())){
            holder.usernametext.setText(model.getUsername() + "(me)");
        }

        firebaseutil.getOtherProfilePicReff(model.getUserid()).getDownloadUrl()
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            Uri uri = task.getResult();
                            androidUtil.setprofilepic(context,uri,holder.imageprofile);
                        }
                    }
                });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(context, chatroom.class);
                androidUtil.userpassfromIntent(intent,model);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public userModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("onCreateViewHolder", "onCreateViewHolder:onCreateViewHolder ");
        View view = LayoutInflater.from(context).inflate(R.layout.result_user_recview_row,parent,false);
        return new userModelViewHolder(view);
    }

    class   userModelViewHolder extends RecyclerView.ViewHolder {

        ImageView imageprofile;
        TextView usernametext;
        TextView phonenumber;

        public userModelViewHolder(@NonNull View itemView) {
            super(itemView);

            imageprofile = itemView.findViewById(R.id.profilePerson);
            usernametext =  itemView.findViewById(R.id.username_text);
            phonenumber = itemView.findViewById(R.id.phone_text);
        }
    }
}
