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
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class BottomAddContactSheet extends BottomSheetDialogFragment {
    private Button createContact;
    private String currentUserName;
    private String additionUserName;
    private String additionPhoneNumber;
    private EditText newUserName;
    private EditText newPhoneNumber;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.bottom_sheet_add_contact,container,false);
        createContact = (Button) v.findViewById(R.id.create_contact_btn);
        newUserName = v.findViewById(R.id.new_contact_username);
        newPhoneNumber = v.findViewById(R.id.new_contact_phoneNumber);
        assert getArguments() != null;
        currentUserName = getArguments().getString("currentUserName");
        createContact.setOnClickListener(v1 -> {
            additionUserName = newUserName.getText().toString();
            additionPhoneNumber = newPhoneNumber.getText().toString();
            //TODO check the userName in database.

            //add contact to database if user exist.
            FirebaseFirestore.getInstance().collection("User")
                    .document(currentUserName)
                    .update("Contacts", FieldValue.arrayUnion());
        });
        return v;
    }
}
