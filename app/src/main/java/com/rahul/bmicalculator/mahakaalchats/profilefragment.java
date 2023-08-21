package com.rahul.bmicalculator.mahakaalchats;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.UploadTask;
import com.rahul.bmicalculator.mahakaalchats.models.usernamemodel;
import com.rahul.bmicalculator.mahakaalchats.utils.androidUtil;
import com.rahul.bmicalculator.mahakaalchats.utils.firebaseutil;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class profilefragment extends Fragment {

    ImageView profile_pic_view;
    TextView username_view,phone_view;
    Button update_username_btn;
    TextView logout_btn;
    usernamemodel newusermodel;
    ActivityResultLauncher<Intent> imagePickerLauncher;
    Uri selectedimgUri;

    public profilefragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagePickerLauncher = registerForActivityResult( new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if(result.getResultCode()== Activity.RESULT_OK){
                Intent data = result.getData();
                if(data!=null && data.getData()!=null){
                    selectedimgUri = data.getData();
                    androidUtil.setprofilepic(getContext(),selectedimgUri,profile_pic_view);
                }
            }
        }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profilefragment, container, false);

        profile_pic_view =  view.findViewById(R.id.update_profile_picView);
        username_view= view.findViewById(R.id.username_edt);
        phone_view = view.findViewById(R.id.phone_edt);
        update_username_btn = view.findViewById(R.id.update_user_btn);
        logout_btn= view.findViewById(R.id.logout_btn);

        getdata();

        update_username_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUsername();
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseutil.logout();
                Intent intent = new Intent(getContext(),splash.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        profile_pic_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with((Activity) getContext()).cropSquare().compress(512).maxResultSize(512,512)
                        .createIntent(new Function1<Intent, Unit>() {
                            @Override
                            public Unit invoke(Intent intent) {
                                imagePickerLauncher.launch(intent);
                                return null;
                            }
                        });
            }
        });




        return view;
    }



    void  updateUsername(){
        String newusername = username_view.getText().toString();
        if(newusername.isEmpty() || newusername.length()<3){
            username_view.setError("username length atleast greater than 3");
            return;
        }
        newusermodel.setUsername(newusername);
        if(selectedimgUri!=null) {
            firebaseutil.getCurrentProfilePicReff().putFile(selectedimgUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    updateUsernameFirestore();
                }
            });
        }else{
            updateUsernameFirestore();
        }



    }

    void updateUsernameFirestore(){
        firebaseutil.currentUserDetails().set(newusermodel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getContext(), "updated successfully", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(), "update failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    void getdata(){

        firebaseutil.getCurrentProfilePicReff().getDownloadUrl()
                        .addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()){
                                    Uri uri = task.getResult();
                                    androidUtil.setprofilepic(getContext(),uri,profile_pic_view);
                                }
                            }
                        });
        firebaseutil.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                newusermodel = task.getResult().toObject(usernamemodel.class);
                username_view.setText(newusermodel.getUsername());
                phone_view.setText(newusermodel.getPhone());

            }
        });
    }

}