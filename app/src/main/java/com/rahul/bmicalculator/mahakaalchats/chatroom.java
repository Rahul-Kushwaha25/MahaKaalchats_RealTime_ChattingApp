package com.rahul.bmicalculator.mahakaalchats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.rahul.bmicalculator.mahakaalchats.adaptors.chat_msg_rec_adaptor;
import com.rahul.bmicalculator.mahakaalchats.adaptors.result_recview_adaptor;
import com.rahul.bmicalculator.mahakaalchats.models.ChatMessageModel;
import com.rahul.bmicalculator.mahakaalchats.models.ChatroomModel;
import com.rahul.bmicalculator.mahakaalchats.models.usernamemodel;
import com.rahul.bmicalculator.mahakaalchats.utils.androidUtil;
import com.rahul.bmicalculator.mahakaalchats.utils.firebaseutil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class chatroom extends AppCompatActivity {

    ChatroomModel chatroomModel;
    ImageView imageView;

    chat_msg_rec_adaptor adaptor;
    String chatroomid;
    usernamemodel usermodeldata;
    ImageButton back_btn,send_btn;
    TextView usernamearea;
    EditText msg_input;
    RecyclerView recview_chatroom;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        usermodeldata = androidUtil.getuserdatafromIntent(getIntent());
        chatroomid = firebaseutil.getchatroomid(firebaseutil.currentUserId(),usermodeldata.getUserid());

        back_btn = findViewById(R.id.backBtn);
        send_btn = findViewById(R.id.sendmsg_btn);
        usernamearea = findViewById(R.id.usernametop);
        msg_input = findViewById(R.id.msg_edt_chatroom);
        recview_chatroom = findViewById(R.id.recViewchatroom);
        imageView = findViewById(R.id.profilePerson);

        Context icontext = this;
        firebaseutil.getOtherProfilePicReff(usermodeldata.getUserid()).getDownloadUrl()
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            Uri uri = task.getResult();
                            androidUtil.setprofilepic(icontext,uri,imageView);
                        }
                    }
                });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });




        usernamearea.setText(usermodeldata.getUsername());

        getOrMakeChatRoom();

        set_chatmsg_rec_adaptor();

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String message = msg_input.getText().toString().trim();
                if(message.isEmpty()){
                    return;
                }
                send_message(message);
            }
        });
    }

    void set_chatmsg_rec_adaptor(){
        Query queryy = firebaseutil.getchatmessagereff(chatroomid).orderBy("timestamp", Query.Direction.DESCENDING);


        FirestoreRecyclerOptions<ChatMessageModel> options = new FirestoreRecyclerOptions.Builder<ChatMessageModel>()
                .setQuery(queryy,ChatMessageModel.class).build();

        adaptor = new chat_msg_rec_adaptor(options,getApplicationContext());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        recview_chatroom.setLayoutManager(manager);
        recview_chatroom.setAdapter(adaptor);
        adaptor.startListening();
        adaptor.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recview_chatroom.smoothScrollToPosition(0);
            }
        });

    }

    void getOrMakeChatRoom(){
        firebaseutil.getchatroomidreff(chatroomid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    chatroomModel = task.getResult().toObject(ChatroomModel.class);

                if(chatroomModel==null) {
                    //creation of chatroom
                    ChatroomModel newchatroommodel = new ChatroomModel(
                            chatroomid,
                            Arrays.asList(firebaseutil.currentUserId(), usermodeldata.getUserid()),
                            Timestamp.now(),
                            ""

                    );
                    firebaseutil.getchatroomidreff(chatroomid).set(newchatroommodel);
                }

                }

            }
        });

    }

    void send_message( String message){
        chatroomModel.setLastsender_userid(firebaseutil.currentUserId());
        chatroomModel.setLastmsg_time(Timestamp.now());
        chatroomModel.setLast_message(message);
        firebaseutil.getchatroomidreff(chatroomid).set(chatroomModel);

        ChatMessageModel chatMessageModel = new ChatMessageModel(message,firebaseutil.currentUserId(),Timestamp.now());

        firebaseutil.getchatmessagereff(chatroomid).add(chatMessageModel).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()){
                    msg_input.setText("");
                }
            }
        });
    }
}