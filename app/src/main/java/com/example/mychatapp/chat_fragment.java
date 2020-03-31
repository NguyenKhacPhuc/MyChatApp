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

import java.util.ArrayList;

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
        currentUserName = getArguments().getString("currentUserName");
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        chats = new ArrayList<>();
        chats.add(new Chat("Phuc","hi","12/2/2020",R.drawable.toanaa));
        chats.add(new Chat("Nam","hello","",R.drawable.toanaa));
        chats.add(new Chat("Hiep","how are you","12/2/2020",R.drawable.toanaa));
        chats.add(new Chat("Co","Phuc oi!!","20/3/2020",R.drawable.toanaa));
        chats.add(new Chat("Hen","Cai gi","12/3/2020",R.drawable.toanaa));
        chats.add(new Chat("Dat","Khong co gi","12/12/2020",R.drawable.toanaa));
        chats.add(new Chat("phucnguyen","Testing","12/12/2020",R.drawable.toanaa));
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
