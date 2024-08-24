package com.aditya.paymentcollection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    EditText etUsername,etPassword;
    ImageView btn_login;
    TextView txtForgetPassword;
    int fingerprint_lock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = getSharedPreferences( "PaymentCollection", Context.MODE_PRIVATE );
        editor = pref.edit();

        Intent intent=getIntent();
        int verify_done= intent.getIntExtra("verify_done",0);

        if (verify_done==0){
            fingerprint_lock=pref.getInt("fingerprint_lock", 0);

            if (fingerprint_lock==1){
                Intent intent1=new Intent(this,FingeprintActivity.class);
                startActivity(intent1);
                finish();
            }
        }



        etUsername=findViewById(R.id.etUsername);
        etPassword=findViewById(R.id.etPassword);
        btn_login=findViewById(R.id.btn_login);
        txtForgetPassword=findViewById(R.id.txtForgetPassword);

        btn_login.setOnClickListener(this);
        txtForgetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                Intent intent=new Intent(LoginActivity.this,BottomNavigationActivity.class);
                startActivity(intent);
                break;

            case R.id.txtForgetPassword:
                break;
        }
    }
}