package com.example.mychatapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.FirebaseFirestore;

public class BottomSetPhoneNumber extends BottomSheetDialogFragment {
    private Button done;
    private Button cancel;
    private EditText phoneNumberChanged;
    private String currentUserName;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_set_phonenumber,container,false);
        done = (Button) v.findViewById(R.id.done_set_phone_btn);
        cancel = (Button) v.findViewById(R.id.cancel_set_phone_btn);
        currentUserName = getArguments().getString("username");
        phoneNumberChanged = (EditText) v.findViewById(R.id.changed_phoneNumber);

        cancel.setOnClickListener(click ->{
            dismiss();
        });
        done.setOnClickListener(clickToChange ->{
            FirebaseFirestore.getInstance().document("User/"+currentUserName).update("Phone number",phoneNumberChanged.getText().toString());
        });
        return v;
    }
}
