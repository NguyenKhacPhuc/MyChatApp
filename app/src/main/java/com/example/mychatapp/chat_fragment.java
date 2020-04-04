package com.example.mychatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

public class chat_fragment extends Fragment implements ChatsAdapter.ChatHolder.onItemListener {
     ArrayList<Chat> chats;
     RecyclerView recyclerView;
     Bundle bundle;
     String currentUserName;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.chat_fragment,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.listChats);
        assert getArguments() != null;
        currentUserName = getArguments().getString("currentUserName");
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        chats = new ArrayList<>();
        FirebaseFirestore.getInstance().document("User/"+currentUserName).get().addOnCompleteListener(task -> {
            DocumentSnapshot documentSnapshot = task.getResult();
            assert documentSnapshot != null;
            Map<String,Object> allData =  documentSnapshot.getData();
            assert allData != null;
            for (Map.Entry<String,Object> data: allData.entrySet()){

            }
        });
        ChatsAdapter chatsAdapter = new ChatsAdapter(chats,getContext(),this);
        recyclerView.setAdapter(chatsAdapter);
        return view;
    }

    @Override
    public void onClick(int position) {
       Intent intent = new Intent(getActivity(),chat_activity.class);
       bundle = new Bundle();
       bundle.putString("opponentUserName", chats.get(position).getUsername());
       bundle.putString("currentUserName",currentUserName);
       intent.putExtra("bun", bundle);
       startActivity(intent);
    }
}
