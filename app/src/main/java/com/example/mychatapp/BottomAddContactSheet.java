package com.example.mychatapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class BottomAddContactSheet extends BottomSheetDialogFragment {
    private Button createContact;
    private Button cancel;
    private String currentUserName;
    private String additionUserName;
    private String additionPhoneNumber;
    private EditText newUserName;
    private EditText newPhoneNumber;
    private ArrayList<String> userNamelst;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.bottom_sheet_add_contact,container,false);
        createContact = (Button) v.findViewById(R.id.create_contact_btn);
        newUserName = v.findViewById(R.id.new_contact_username);
        newPhoneNumber = v.findViewById(R.id.new_contact_phoneNumber);
        cancel = v.findViewById(R.id.cancel_add_contact_btn);
         userNamelst = new ArrayList<>();
        assert getArguments() != null;
        currentUserName = getArguments().getString("currentUserName");
        createContact.setOnClickListener(v1 -> {
            additionUserName = newUserName.getText().toString();
            additionPhoneNumber = newPhoneNumber.getText().toString();
            //TODO check the userName in database.
            FirebaseFirestore.getInstance().collection("UserNameList")
                    .document("UList").get()
                    .addOnCompleteListener(task -> {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        assert documentSnapshot != null;
                        Map<String,Object> map = documentSnapshot.getData();
                        assert map != null;
                        for(Map.Entry<String, Object> entry : map.entrySet()){
                            if(entry.getKey().equals("ListOfUserName")){
                                ArrayList<String> userNameList = (ArrayList<String>) entry.getValue();
                                userNamelst.addAll(userNameList);
                                Log.d("username", userNamelst.toString());
                                handleUserNameConditiontoAdd(userNameList);
                            }
                        }
                    });
        });
        cancel.setOnClickListener(clickCancel ->{
            dismiss();
        });
        return v;
    }

    private void handleUserNameConditiontoAdd(ArrayList<String> userNameList) {
        //add contact to database if user exist.
        boolean check = false;
        for (String s : userNameList){
            if(additionUserName.equals(s)){
                FirebaseFirestore.getInstance().document("User/"+additionUserName).get().addOnSuccessListener(documentSnapshot -> {
                    String avatar = documentSnapshot.getString("Avatar");
                    String phoneNumber = documentSnapshot.getString("Phone number");
                    if(additionPhoneNumber.equals(phoneNumber)) {
                        Contacts newContacts = new Contacts(avatar, phoneNumber, additionUserName);
                        addContact(newContacts);
                    }
                    else{
                        newPhoneNumber.requestFocus();
                        newPhoneNumber.setError("Phone number is not correct");
                    }
                });
                check = true;
                break;
            }
        }
        if(!check) {
            newUserName.requestFocus();
            newUserName.setError("Username is not exist");
        }
    }

    private void addContact(Contacts newContacts) {
        FirebaseFirestore.getInstance().collection("User")
                .document(currentUserName)
                .update("Contacts", FieldValue.arrayUnion(newContacts));
    }
}
