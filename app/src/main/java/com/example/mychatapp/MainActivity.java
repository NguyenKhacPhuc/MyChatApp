package com.example.mychatapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigtion_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content,
                    new contact_fragment()).commit();
        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = menuItem -> {
        Fragment chosenFrag = null;
        switch (menuItem.getItemId()){
            case R.id.contact:
                chosenFrag = new contact_fragment();
                break;
            case R.id.chat:
                intent = getIntent();
                Bundle currentBunUsername = intent.getBundleExtra("bunUserName");
                String currentUserName = currentBunUsername.getString("currentUserName");
                Bundle bundle = new Bundle();
                bundle.putString("currentUserName",currentUserName);
                chosenFrag = new chat_fragment();
                chosenFrag.setArguments(bundle);
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
