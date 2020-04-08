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

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class setting_fragment extends Fragment {
    private TextView setProfilePhoto;
    private TextView setUserName;
    private TextView setPhoneNumber;
    private String username;
    private TextView settingName;
    private CircleImageView userAvatar;
    private TextView showPN;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.setting_fragment,container,false);
        setProfilePhoto = (TextView) v.findViewById(R.id.set_profile_photo);
        setUserName = (TextView) v.findViewById(R.id.set_user_name);
        setPhoneNumber = (TextView) v.findViewById(R.id.set_phoneNumber);
        userAvatar = (CircleImageView) v.findViewById(R.id.setting_avatar);
        showPN = (TextView) v.findViewById(R.id.show_phone_number) ;
        settingName = (TextView) v.findViewById(R.id.setting_name);
        assert getArguments() != null;
        username = getArguments().getString("currentUserName");
        setProfilePhoto.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.chevron_right,0);
        setUserName.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.chevron_right,0);
        setPhoneNumber.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.chevron_right,0);
        //event user click set username
        setUserName.setOnClickListener(v12 -> {
            setUserName.setBackgroundColor(Color.GRAY);
            BottomSetName bottomSetName = new BottomSetName();

            assert getFragmentManager() != null;
            bottomSetName.show(getFragmentManager(),"Edit username");
        });
        //event user click set phone number
        setPhoneNumber.setOnClickListener(v1 -> {
            setPhoneNumber.setBackgroundColor(Color.GRAY);
            BottomSetPhoneNumber bottomSetPhoneNumber = new BottomSetPhoneNumber();
            Bundle bundel = new Bundle();
            bundel.putString("username",username);
            bottomSetPhoneNumber.setArguments(bundel);
            assert getFragmentManager() != null;
            bottomSetPhoneNumber.show(getFragmentManager(),"change phone number");
        });
        //event user click set profile photo
        setProfilePhoto.setOnClickListener(vProfile ->{
            setProfilePhoto.setBackgroundColor(Color.GRAY);
            //TODO
        });
        FirebaseFirestore.getInstance().document("User/"+username).get().addOnCompleteListener(task -> {
            DocumentSnapshot documentSnapshot = task.getResult();
            assert documentSnapshot != null;
            String avatar = documentSnapshot.getString("Avatar");
            String phoneNumber = documentSnapshot.getString("Phone number");
            String name = documentSnapshot.getString("Username");
            Picasso.with(getContext()).load(avatar).into(userAvatar);
            showPN.setText(phoneNumber);
            settingName.setText(name);
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
