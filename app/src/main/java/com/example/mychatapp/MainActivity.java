package com.example.mychatapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Intent intent ;
    private Bundle currentBunUsername ;
    private  String currentUserName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigtion_bottom);
        intent = getIntent();
        currentBunUsername = intent.getBundleExtra("bunUserName");
        assert currentBunUsername != null;
        currentUserName = currentBunUsername.getString("currentUserName");

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.content,
//                    new contact_fragment()).commit();
//        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = menuItem -> {

        Fragment chosenFrag = null;

        switch (menuItem.getItemId()){
            case R.id.contact:
                Bundle bundle = new Bundle();
                bundle.putString("currentUserName",currentUserName);
                chosenFrag = new contact_fragment();
                chosenFrag.setArguments(bundle);
                break;
            case R.id.chat:
                Bundle bundleChat = new Bundle();
                bundleChat.putString("currentUserName",currentUserName);
                chosenFrag = new chat_fragment();
                chosenFrag.setArguments(bundleChat);
                break;
            case R.id.call:
                chosenFrag = new call_fragment();
                break;
            case R.id.setting:
                chosenFrag = new setting_fragment();
                break;
            default:
                break;

        }
        assert chosenFrag != null;
        getSupportFragmentManager().beginTransaction().replace(R.id.content,chosenFrag).commit();
        return true;
    };
}
