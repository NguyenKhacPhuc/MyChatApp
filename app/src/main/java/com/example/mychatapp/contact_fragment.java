package com.example.mychatapp;

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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_fragment,container,false);
        //init
        recyclerView = (RecyclerView) view.findViewById(R.id.listContacts);
        contacts = new ArrayList<>();
        createContact = (FloatingActionButton) view.findViewById(R.id.create_new_contact_fragment);

        //event
        createContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomAddContactSheet bottomAddContactSheet = new BottomAddContactSheet();
                assert getFragmentManager() != null;
                bottomAddContactSheet.show(getFragmentManager(),"Add contact");
            }
        });

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);


        contacts.add(new Contacts(R.drawable.toanaa,"0834222439","Phuc Nguyen"));
        contacts.add(new Contacts(R.drawable.toanaa,"0834222439","Tien Dat"));
        contacts.add(new Contacts(R.drawable.toanaa,"0834222439","Hariwon"));
        contacts.add(new Contacts(R.drawable.toanaa,"0834222439","Tran Thanh"));
        contacts.add(new Contacts(R.drawable.toanaa,"0834222439","Chi Pu"));
        contacts.add(new Contacts(R.drawable.toanaa,"0834222439","Ta Hiep"));
        contacts.add(new Contacts(R.drawable.toanaa,"0834222439","Khac Phuc"));
        contacts.add(new Contacts(R.drawable.toanaa,"0834222439","Minh Phuong"));
        contacts.add(new Contacts(R.drawable.toanaa,"0834222439","Van Dat"));
        contacts.add(new Contacts(R.drawable.toanaa,"0834222439","Trong Nam"));
        ContactsAdapter contactsAdapter = new ContactsAdapter(contacts,getContext());
        recyclerView.setAdapter(contactsAdapter);
        return view;
    }
}
