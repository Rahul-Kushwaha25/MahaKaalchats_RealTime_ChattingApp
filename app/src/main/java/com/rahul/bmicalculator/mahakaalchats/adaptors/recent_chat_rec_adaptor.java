package com.rahul.bmicalculator.mahakaalchats.adaptors;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rahul.bmicalculator.mahakaalchats.R;
import com.rahul.bmicalculator.mahakaalchats.chatroom;
import com.rahul.bmicalculator.mahakaalchats.models.ChatroomModel;
import com.rahul.bmicalculator.mahakaalchats.models.usernamemodel;
import com.rahul.bmicalculator.mahakaalchats.utils.androidUtil;
import com.rahul.bmicalculator.mahakaalchats.utils.firebaseutil;

public class recent_chat_rec_adaptor  extends FirestoreRecyclerAdapter< ChatroomModel ,recent_chat_rec_adaptor.chatRoomviewholder>{

    Context context;
    public recent_chat_rec_adaptor(@NonNull FirestoreRecyclerOptions<ChatroomModel> options , Context context) {
        super(options);
        this.context = context;
        
    }

    @Override
    protected void onBindViewHolder(@NonNull chatRoomviewholder holder, int position, @NonNull ChatroomModel model) {
       
       firebaseutil.getOtheruserFromchatroom(model.getUserids())
               .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {

               if(task.isSuccessful()){
                   usernamemodel otherusermodel = task.getResult().toObject(usernamemodel.class);

                   firebaseutil.getOtherProfilePicReff(otherusermodel.getUserid()).getDownloadUrl()
                           .addOnCompleteListener(new OnCompleteListener<Uri>() {
                               @Override
                               public void onComplete(@NonNull Task<Uri> task) {
                                   if (task.isSuccessful()){
                                       Uri uri = task.getResult();
                                       androidUtil.setprofilepic(context,uri,holder.profile);
                                   }
                               }
                           });

                   holder.username_text.setText(otherusermodel.getUsername());
                   Log.d("datachecking", "onComplete: username "+otherusermodel.getUsername());
                   holder.last_msg_text.setText(model.getLast_message());
                   holder.last_msg_time.setText(firebaseutil.timeStamptoString(model.getLastmsg_time()));
                   holder.itemView.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           Intent intent =  new Intent(context, chatroom.class);
                           androidUtil.userpassfromIntent(intent,otherusermodel);
                           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                           context.startActivity(intent);
                       }
                   });
               }
           }
       });


    }

    @NonNull
    @Override
    public chatRoomviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("onCreateViewHolder", "onCreateViewHolder:onCreateViewHolder ");
        View view = LayoutInflater.from(context).inflate(R.layout.recent_chat_rec_row,parent,false);
        return new chatRoomviewholder(view);
    }

    class   chatRoomviewholder extends RecyclerView.ViewHolder {

        TextView username_text,last_msg_text,last_msg_time;
        ImageView profile;



        public chatRoomviewholder(@NonNull View itemView) {
            super(itemView);

        username_text=itemView.findViewById(R.id.username_text);
        last_msg_text=itemView.findViewById(R.id.last_msg_text);
        last_msg_time=itemView.findViewById(R.id.last_mst_time);
        profile=itemView.findViewById(R.id.profilePerson);



        }
    }
}

