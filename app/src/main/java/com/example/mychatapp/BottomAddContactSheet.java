package com.example.mychatapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.bottom_sheet_add_contact,container,false);
        createContact = (Button) v.findViewById(R.id.create_contact_btn);
        assert getArguments() != null;
        currentUserName = getArguments().getString("currentUserName");
        createContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseFirestore.getInstance().collection("User")
                        .document(currentUserName)
                        .update("Contacts", FieldValue.arrayUnion());
            }
        });
        return v;
    }
}
