package com.example.mychatapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class setting_fragment extends Fragment {
    private TextView setProfilePhoto;
    private TextView setUserName;
    private TextView setPhoneNumber;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.setting_fragment,container,false);
        setProfilePhoto = (TextView) v.findViewById(R.id.set_profile_photo);
        setUserName = (TextView) v.findViewById(R.id.set_user_name);
        setPhoneNumber = (TextView) v.findViewById(R.id.set_phoneNumber);
        setProfilePhoto.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.chevron_right,0);
        setUserName.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.chevron_right,0);
        setPhoneNumber.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.chevron_right,0);
        //event user click set username
        setUserName.setOnClickListener(v12 -> {
            setUserName.setBackgroundColor(Color.GRAY);
            BottomSetUserName bottomSetUserName = new BottomSetUserName();
            assert getFragmentManager() != null;
            bottomSetUserName.show(getFragmentManager(),"Edit username");
        });
        //event user click set phone number
        setPhoneNumber.setOnClickListener(v1 -> {
            setPhoneNumber.setBackgroundColor(Color.GRAY);
            BottomSetPhoneNumber bottomSetPhoneNumber = new BottomSetPhoneNumber();
            assert getFragmentManager() != null;
            bottomSetPhoneNumber.show(getFragmentManager(),"change phone number");
        });
        //event user click set profile photo
        setProfilePhoto.setOnClickListener(vProfile ->{
            setProfilePhoto.setBackgroundColor(Color.GRAY);
            //TODO
        });
        return v;
    }
}
