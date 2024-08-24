package com.aditya.paymentcollection.Fragment;

import android.Manifest;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.aditya.paymentcollection.R;

import static android.content.Context.FINGERPRINT_SERVICE;
import static android.content.Context.KEYGUARD_SERVICE;

public class ProfileFragment extends Fragment {

    ProgressDialog progressDialog;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    ToggleButton toggleFingerprintLock;
    int fingerprint_lock;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v= inflater.inflate(R.layout.fragment_profile, container, false);

        pref = getContext().getSharedPreferences( "PaymentCollection", Context.MODE_PRIVATE );
        editor = pref.edit();

        fingerprint_lock=pref.getInt("fingerprint_lock", 0);

        toggleFingerprintLock=v.findViewById(R.id.toggleFingerprintLock);

        toggleFingerprintLock.setChecked(fingerprint_lock == 1);


        toggleFingerprintLock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                toggleFingerprintLock.setChecked(!isChecked);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    KeyguardManager keyguardManager = (KeyguardManager)getContext().getSystemService(KEYGUARD_SERVICE);
                    FingerprintManager fingerprintManager = null;

                    fingerprintManager = (FingerprintManager) getContext().getSystemService(FINGERPRINT_SERVICE);


                    // Check whether the device has a Fingerprint sensor.
                    if(!fingerprintManager.isHardwareDetected()){
                        /**
                         * An error message will be displayed if the device does not contain the fingerprint hardware.
                         * However if you plan to implement a default authentication method,
                         * you can redirect the user to a default authentication activity from here.
                         * Example:
                         * Intent intent = new Intent(this, DefaultAuthenticationActivity.class);
                         * startActivity(intent);
                         */
                        Toast.makeText(getContext(), "Your Device does not have a Fingerprint Sensor", Toast.LENGTH_SHORT).show();
                    }else {
                        // Checks whether fingerprint permission is set on manifest
                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getContext(), "Fingerprint authentication permission not enabled", Toast.LENGTH_SHORT).show();
                        }else{
                            // Check whether at least one fingerprint is registered
                            if (!fingerprintManager.hasEnrolledFingerprints()) {
                                Toast.makeText(getContext(), "Register at least one fingerprint in Settings", Toast.LENGTH_SHORT).show();
                            }else{
                                // Checks whether lock screen security is enabled or not
                                if (!keyguardManager.isKeyguardSecure()) {
                                    Toast.makeText(getContext(), "Lock screen security not enabled in Settings", Toast.LENGTH_SHORT).show();
                                }else{
                                    toggleFingerprintLock.setChecked(isChecked);
                                    if (isChecked){
                                        editor.putInt( "fingerprint_lock",1 ).apply();
                                    }else {
                                        editor.putInt( "fingerprint_lock",0 ).apply();
                                    }

                                }
                            }
                        }
                    }
                }


            }
        });



        return v;
    }
}