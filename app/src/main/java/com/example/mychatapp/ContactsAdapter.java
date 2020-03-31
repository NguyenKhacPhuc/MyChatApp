package com.example.mychatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.UserViewHolder> {
    private  ArrayList<Contacts> contacts;
    private Context context;

    public ContactsAdapter(ArrayList<Contacts> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item,parent,false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
            holder.name.setText(contacts.get(position).getUserName());
            holder.number.setText(contacts.get(position).getPhoneNumber());
            holder.avatar.setImageResource(contacts.get(position).getAvatarImage());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        ImageView avatar;
        TextView name;
        TextView number;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            number = (TextView) itemView.findViewById(R.id.number);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
        }
    }
}
