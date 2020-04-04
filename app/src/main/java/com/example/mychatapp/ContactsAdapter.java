package com.example.mychatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactHolder> {
    private  ArrayList<Contacts> contacts;
    private Context context;
    private ContactHolder.onItemListener onItemListener;

    public ContactsAdapter(ArrayList<Contacts> contacts, Context context, ContactHolder.onItemListener onItemListener) {
        this.contacts = contacts;
        this.context = context;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item,parent,false);
        return new ContactHolder(itemView,onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
            holder.name.setText(contacts.get(position).getUserName());
            holder.number.setText(contacts.get(position).getPhoneNumber());
            Picasso.with(context).load(contacts.get(position).getAvatarImage()).into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView avatar;
        TextView name;
        TextView number;
        onItemListener onItemListener;
        public ContactHolder(@NonNull View itemView, onItemListener onItemListener) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            number = (TextView) itemView.findViewById(R.id.number);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onClick(getAdapterPosition());
        }
        public interface onItemListener{
            void onClick(int position);
        }
    }
}
