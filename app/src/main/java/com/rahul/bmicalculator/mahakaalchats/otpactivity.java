package com.rahul.bmicalculator.mahakaalchats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class otpactivity extends AppCompatActivity {
    String phone;
    long timeoutSecond = 60L;
    String verificationcode;
    PhoneAuthProvider.ForceResendingToken resendingToken;
    EditText inputotp;
    Button nextbtn;
    TextView resendbtn;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        inputotp = findViewById(R.id.login_otp_edt);
        nextbtn = findViewById(R.id.login_otp_next_btn);
        resendbtn = findViewById(R.id.otp_resend_btn);

        phone = getIntent().getExtras().getString("inputnamekey");

        sendotp(phone,false);

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredotp = inputotp.getText().toString();
                PhoneAuthCredential credentials = PhoneAuthProvider.getCredential(verificationcode,enteredotp);
                signup(credentials);
            }
        });
         resendbtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 sendotp(phone,true);
             }
         });





    }

    void sendotp(String phonenumber, boolean isresend){
        startresendtimer();
        PhoneAuthOptions.Builder builder = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phonenumber)
                .setActivity(this)
                .setTimeout(timeoutSecond, TimeUnit.SECONDS)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        signup(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(otpactivity.this, "OTP verification Failed", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        verificationcode = s;
                        resendingToken = forceResendingToken;
                        Toast.makeText(otpactivity.this, "OTP sent successfully", Toast.LENGTH_LONG).show();
                    }
                });
        if(isresend){
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        }else{
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }


    }
    void signup(PhoneAuthCredential phoneAuthCredential){
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(otpactivity.this,loginuseractivity.class);
                    intent.putExtra("phonekey",phone);
                    startActivity(intent);
                }else{
                    Toast.makeText(otpactivity.this, "invalid OTP", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    void startresendtimer(){
        resendbtn.setEnabled(false);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
              timeoutSecond--;
              resendbtn.setText("Resend OTP in "+timeoutSecond+" second");
              if(timeoutSecond<=0){
                  timeoutSecond=60L;
                  timer.cancel();
                  runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          resendbtn.setEnabled(true);
                      }
                  });
              }

            }
        },0,1000);

    }
}