package com.aditya.paymentcollection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class UserDetailActivity extends AppCompatActivity {

    LinearLayout layout_payment;
    ToggleButton toggleVisitClient,toggleReceivePayment;
    RelativeLayout layoutReceivedPayment;
    LinearLayout layoutNotReceivedPayment;
    EditText etOtherIssues,etAmount;
    RadioGroup radio_group,rg_paymentMode;
    RadioButton cash,cheque;
    TextView txtEmployeeName,txtEmployeePhone,txtEmployeeAddress,txtEmployeeAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        String employeeName = getIntent().getStringExtra("employeeName");
        String amount = getIntent().getStringExtra("amount");
        String phoneNum = getIntent().getStringExtra("phoneNum");
        String fullAddress = getIntent().getStringExtra("fullAddress");

        initViews();

        txtEmployeeName.setText(employeeName);
        txtEmployeePhone.setText(phoneNum);
        txtEmployeeAmount.setText(amount);
        txtEmployeeAddress.setText(fullAddress);

        layoutReceivedPayment.setVisibility(View.VISIBLE);
        layoutNotReceivedPayment.setVisibility(View.GONE);
        layout_payment.setVisibility(View.VISIBLE);


        onCheckedChangeListner();
    }

    private void initViews(){
        rg_paymentMode = findViewById( R.id.rg_paymentMode );
        etOtherIssues = findViewById( R.id.etOtherIssues );
        radio_group = findViewById( R.id.radio_group );
        layoutNotReceivedPayment = findViewById( R.id.layoutNotReceivedPayment );
        layoutReceivedPayment = findViewById( R.id.layoutReceivedPayment );
        toggleReceivePayment = findViewById( R.id.toggleReceivePayment );
        toggleVisitClient = findViewById( R.id.toggleVisitClient );
        layout_payment = findViewById( R.id.layout_payment );
        txtEmployeeName = findViewById( R.id.txtEmployeeName );
        txtEmployeePhone = findViewById( R.id.txtEmployeePhone );
        txtEmployeeAddress = findViewById( R.id.txtEmployeeAddress );
        txtEmployeeAmount = findViewById( R.id.txtEmployeeAmount );
        cash = findViewById( R.id.cash );
        cheque = findViewById( R.id.cheque );
        etAmount = findViewById( R.id.etAmount );
    }

    private void onCheckedChangeListner(){
        toggleVisitClient.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked){
                    layoutReceivedPayment.setVisibility(View.VISIBLE);
                    layoutNotReceivedPayment.setVisibility(View.GONE);
                    toggleReceivePayment.setChecked(true);
                    layout_payment.setVisibility(View.VISIBLE);
                    radio_group.clearCheck();
                    etOtherIssues.setVisibility(View.GONE);
                    etOtherIssues.setText("");
                    etAmount.setText("");
                }else {
                    layoutReceivedPayment.setVisibility(View.GONE);
                    layout_payment.setVisibility(View.GONE);
                    layoutNotReceivedPayment.setVisibility(View.VISIBLE);
                    etAmount.setText("");
                }

            }
        });

        toggleReceivePayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    layout_payment.setVisibility(View.VISIBLE);
                    layoutNotReceivedPayment.setVisibility(View.GONE);
                    radio_group.clearCheck();
                    etOtherIssues.setVisibility(View.GONE);
                    etOtherIssues.setText("");
                    etAmount.setText("");
                }else {
                    layout_payment.setVisibility(View.GONE);
                    layoutNotReceivedPayment.setVisibility(View.VISIBLE);
                    etAmount.setText("");
                }
            }
        });

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Log.d("YourResponse","positionRadioButton--"+i);
                int selectedId=radio_group.getCheckedRadioButtonId();
                try {
                    RadioButton radioSexButton = (RadioButton) findViewById(selectedId);
                    String noPayment = radioSexButton.getText().toString();

                    if (noPayment.equals("Other issues")){
                        etOtherIssues.setVisibility(View.VISIBLE);
                    }else {
                        etOtherIssues.setVisibility(View.GONE);
                        etOtherIssues.setText("");
                    }

                }catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        rg_paymentMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId=rg_paymentMode.getCheckedRadioButtonId();
                try {
                    RadioButton radioSexButton=(RadioButton)findViewById(selectedId);
                    String paymentMode = radioSexButton.getText().toString();

                    if (paymentMode.equals("Cash")){
                        cash.setBackgroundColor(getResources().getColor(R.color.dark_red));
                        cash.setTextColor(getResources().getColor(R.color.white));
                        cheque.setTextColor(getResources().getColor(R.color.black));
                        cheque.setBackgroundColor(getResources().getColor(R.color.white));
                    }else if (paymentMode.equals("Cheque")){
                        cheque.setBackgroundColor(getResources().getColor(R.color.dark_red));
                        cheque.setTextColor(getResources().getColor(R.color.white));
                        cash.setTextColor(getResources().getColor(R.color.black));
                        cash.setBackgroundColor(getResources().getColor(R.color.white));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}