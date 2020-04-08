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

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class BottomSetName extends BottomSheetDialogFragment {
    private Button done;
    private Button cancel;
    private EditText changedUsername;
    private String currentUserName;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottm_sheet_set_name,container,false);
        done =(Button) v.findViewById(R.id.done_set_username_btn);
        cancel = (Button) v.findViewById(R.id.cancel_set_username_btn);
        changedUsername=(EditText) v.findViewById(R.id.changed_username);
        assert getArguments() != null;
        currentUserName = getArguments().getString("username");

        cancel.setOnClickListener(click ->{
            dismiss();
        });
        done.setOnClickListener(clickDone ->{
            String changesUsername = changedUsername.getText().toString();
            FirebaseFirestore.getInstance().document("User/"+currentUserName).update("Username",changesUsername);
            FirebaseFirestore.getInstance().document("User/"+currentUserName).get().addOnCompleteListener(v2->{
                FirebaseFirestore.getInstance().document("User/"+changesUsername).set(Objects.requireNonNull(v2.getResult().getData()));
            });
            FirebaseFirestore.getInstance().document("User/"+currentUserName).delete();
            FirebaseFirestore.getInstance().document("Chat/"+currentUserName).get().addOnCompleteListener(v3->{
                FirebaseFirestore.getInstance().document("Chat/"+changesUsername)
                        .set(Objects.requireNonNull(Objects.requireNonNull(v3.getResult()).getData()));
            });
            FirebaseFirestore.getInstance().document("Chat/"+currentUserName).delete();
            FirebaseFirestore.getInstance().document("UserNameList/UList")
                    .update("ListOfUserName", FieldValue.arrayUnion(changesUsername),FieldValue.arrayRemove(currentUserName));
        });

        return v;
    }
}
