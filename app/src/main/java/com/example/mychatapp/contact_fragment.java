package com.example.mychatapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class contact_fragment extends Fragment implements ContactsAdapter.ContactHolder.onItemListener {
    private RecyclerView recyclerView;
    private ArrayList<Contacts> contacts;
    private FloatingActionButton createContact;
    private String currentUserName;
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_fragment, container, false);
        //init
        assert getArguments() != null;
        currentUserName = getArguments().getString("currentUserName");
        recyclerView = (RecyclerView) view.findViewById(R.id.listContacts);
        contacts = new ArrayList<>();
        createContact = (FloatingActionButton) view.findViewById(R.id.create_new_contact_fragment);
        //event
        createContact.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("currentUserName", currentUserName);
            BottomAddContactSheet bottomAddContactSheet = new BottomAddContactSheet();
            bottomAddContactSheet.setArguments(bundle);
            assert getFragmentManager() != null;
            bottomAddContactSheet.show(getFragmentManager(), "Add contact");

        });

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        FirebaseFirestore.getInstance().document("User/" + currentUserName).get().addOnCompleteListener(task -> {

            DocumentSnapshot documentSnapshot = task.getResult();
            assert documentSnapshot != null;
            Map<String, Object> data = documentSnapshot.getData();
            assert data != null;
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                if (entry.getKey().equals("Contacts")) {
                    ArrayList<HashMap<String, Object>> dtbContacts = (ArrayList<HashMap<String, Object>>) entry.getValue();
                    for (HashMap<String, Object> dtb : dtbContacts) {
                        String avatar = Objects.requireNonNull(dtb.get("avatarImage")).toString();
                        String phoneNumber = Objects.requireNonNull(dtb.get("phoneNumber")).toString();
                        String username = Objects.requireNonNull(dtb.get("userName")).toString();
                        Contacts contact = new Contacts(avatar, phoneNumber, username);
                        contacts.add(contact);
                    }
                }
            }
            ContactsAdapter contactsAdapter = new ContactsAdapter(contacts, getContext(),this);
            recyclerView.setAdapter(contactsAdapter);

        });
        return view;
    }

    @Override
    public void onClick(int position) {
        ArrayList<Message> box = new ArrayList<>();
        HashMap<String,Object> boxes = new HashMap<>();
        boxes.put("Box", box);
        Intent intent = new Intent(getActivity(),chat_activity.class);
        Message  mess = new Message(currentUserName,contacts.get(position).getUserName(),"",contacts.get(position).getAvatarImage());
        FirebaseFirestore.getInstance().document("User/"+currentUserName).update("Chat History."
                +contacts.get(position)
                .getUserName(), FieldValue.arrayUnion(mess));
        FirebaseFirestore.getInstance().document("ChatBoxes/"+currentUserName+"-"+contacts.get(position).getUserName()).set(boxes);
        bundle = new Bundle();
        bundle.putString("opponentUserName", contacts.get(position).getUserName());
        bundle.putString("currentUserName",currentUserName);
        intent.putExtra("bun", bundle);
        startActivity(intent);
    }
}
