package com.rahul.bmicalculator.mahakaalchats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;

public class Login_page extends AppCompatActivity {
    EditText inputphone_number;
    CountryCodePicker countryCodePicker;
    Button sendOtpbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

         countryCodePicker = findViewById(R.id.country_code);
         sendOtpbtn = findViewById(R.id.login_otp_sent_btn);
         inputphone_number = findViewById(R.id.login_phone_number);

         countryCodePicker.registerCarrierNumberEditText(inputphone_number);

         sendOtpbtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(!countryCodePicker.isValidFullNumber()){
                     inputphone_number.setError("invalid phone number");
                     return;
                 }
                 else{
                     Intent intent = new Intent(Login_page.this,otpactivity.class);
                     intent.putExtra("inputnamekey",countryCodePicker.getFullNumberWithPlus());
                     startActivity(intent);
                 }
             }
         });


    }
}