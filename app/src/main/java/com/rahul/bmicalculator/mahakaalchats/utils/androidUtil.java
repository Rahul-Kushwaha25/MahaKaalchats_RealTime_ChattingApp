package com.rahul.bmicalculator.mahakaalchats.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rahul.bmicalculator.mahakaalchats.models.usernamemodel;

public class androidUtil {

    public static void userpassfromIntent(Intent intent , usernamemodel usernamemodelobj){

        intent.putExtra("username",usernamemodelobj.getUsername());
        intent.putExtra("phonenumber",usernamemodelobj.getPhone());
        intent.putExtra("userid",usernamemodelobj.getUserid());

    }

    public static usernamemodel getuserdatafromIntent(Intent intent){
        usernamemodel umodel = new usernamemodel();
        umodel.setUsername(intent.getStringExtra("username"));
        umodel.setPhone(intent.getStringExtra("phonenumber"));
        umodel.setUserid(intent.getStringExtra("userid"));
        return umodel;
    }

    public static void setprofilepic(Context context , Uri imageuri , ImageView imageView){
        Glide.with(context).load(imageuri).apply(RequestOptions.circleCropTransform()).into(imageView);
    }
}
