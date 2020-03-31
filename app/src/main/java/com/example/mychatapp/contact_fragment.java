package com.example.mychatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class contact_fragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Contacts> contacts;
    FloatingActionButton createContact;
    String currentUserName;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_fragment,container,false);
        //init
        assert getArguments() != null;
        currentUserName = getArguments().getString("currentUserName");
        recyclerView = (RecyclerView) view.findViewById(R.id.listContacts);
        contacts = new ArrayList<>();
        createContact = (FloatingActionButton) view.findViewById(R.id.create_new_contact_fragment);

        //event
        createContact.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("currentUserName",currentUserName);
            BottomAddContactSheet bottomAddContactSheet = new BottomAddContactSheet();
            bottomAddContactSheet.setArguments(bundle);
            assert getFragmentManager() != null;
            bottomAddContactSheet.show(getFragmentManager(),"Add contact");

        });

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);


        contacts.add(new Contacts("https://firebasestorage.googleapis.com/v0/b/my-chat-app-4ccd9.appspot.com/o/question-mark-483x335.jpg?alt=media&token=d93882bb-56a3-4fc0-9983-990256c01470"
                ,"0834222439"
                ,"phucnguyen"));

        ContactsAdapter contactsAdapter = new ContactsAdapter(contacts,getContext());
        recyclerView.setAdapter(contactsAdapter);
        return view;
    }
}
