package com.rahul.bmicalculator.mahakaalchats.utils;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.List;

public class firebaseutil{

    public static String currentUserId(){
       return  FirebaseAuth.getInstance().getUid();
    }
    public static boolean isloggedin(){
        if(currentUserId()!=null){
            return true;
        }
        return  false;
    }

    public  static DocumentReference currentUserDetails(){
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId());
    }
    public static CollectionReference alluserCollectionReff(){
        return FirebaseFirestore.getInstance().collection("users");
    }

    public static DocumentReference getchatroomidreff( String chatroomid){
        return FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomid);
    }
    public  static  CollectionReference getchatmessagereff(String chatroomid){
        return getchatroomidreff(chatroomid).collection("chats");
    }


    public static String  getchatroomid(String userid1 , String userid2){
        if(userid1.hashCode()<userid2.hashCode()){
            return  userid1 + "_" + userid2;
        }
        else{
            return  userid2 + "_" + userid1;
        }
    }

    public  static CollectionReference getAllchatroomCollectionReff(){
        return FirebaseFirestore.getInstance().collection("chatrooms");
    }

    public  static  DocumentReference getOtheruserFromchatroom(List<String> userids){
        if (userids.get(0).equals(firebaseutil.currentUserId())){
            return alluserCollectionReff().document(userids.get(1));
        }else{
            return alluserCollectionReff().document(userids.get(0));
        }
    }

    public  static  String timeStamptoString(Timestamp timestamp){
        return new SimpleDateFormat("HH:MM").format(timestamp.toDate());
    }

    public static void logout(){
        FirebaseAuth.getInstance().signOut();
    }

    public  static StorageReference getCurrentProfilePicReff(){
        return FirebaseStorage.getInstance().getReference().child("profilepicture")
                .child(firebaseutil.currentUserId());
    }

    public  static StorageReference getOtherProfilePicReff(String otheruserid){
        return FirebaseStorage.getInstance().getReference().child("profilepicture")
                .child(otheruserid);
    }

}
