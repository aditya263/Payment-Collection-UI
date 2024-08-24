package com.aditya.paymentcollection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.aditya.paymentcollection.Fragment.CollectionFragment;
import com.aditya.paymentcollection.Fragment.HomeFragment;
import com.aditya.paymentcollection.Fragment.ProfileFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class BottomNavigationActivity extends AppCompatActivity {

    Fragment fragment;
    FragmentTransaction fragmentTransaction;

    private AnimatedBottomBar.OnTabSelectListener mOnNavigationItemSelectedListener=new AnimatedBottomBar.OnTabSelectListener(){

        @Override
        public void onTabSelected(int i, @Nullable AnimatedBottomBar.Tab tab, int i1, @NotNull AnimatedBottomBar.Tab tab1) {
            Log.d("YourResponse","position--"+i1);
            Log.d("YourResponse","pos--"+i);
            switch (i1) {
                case 0:
                    fragment=new HomeFragment();
                    switchFragment(fragment);
                    break;

                case 1:
                    fragment=new CollectionFragment();
                    switchFragment(fragment);
                    break;

                case 2:
                    fragment=new ProfileFragment();
                    switchFragment(fragment);
                    break;

            }
        }

        @Override
        public void onTabReselected(int i, @NotNull AnimatedBottomBar.Tab tab) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        AnimatedBottomBar navigation = (AnimatedBottomBar) findViewById( R.id.navigation );
        navigation.setOnTabSelectListener(mOnNavigationItemSelectedListener);

        fragment=new HomeFragment();
        switchFragment(fragment);

    }

    private void switchFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frames, fragment);
        fragmentTransaction.commit();
    }

}