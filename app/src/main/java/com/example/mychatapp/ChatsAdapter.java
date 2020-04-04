package com.example.mychatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ChatHolder> {
    private ArrayList<Chat> chats;
    private Context context;
    private ChatHolder.onItemListener onItemListener;

    public ChatsAdapter(ArrayList<Chat> chats, Context context, ChatHolder.onItemListener onItemListener){
        this.chats = chats;
        this.onItemListener = onItemListener;
        this.context = context;
    }
    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.chat_items,parent,false);
        return new ChatHolder(itemView,onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int position) {
            holder.username.setText(chats.get(position).getUsername());
            holder.lastMessage.setText(chats.get(position).getLastMessage());
            Picasso.with(context).load(chats.get(position).getAvatar()).into(holder.avatar);

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public static class ChatHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView username;
        TextView lastMessage;
        TextView date;
        CircleImageView avatar;
        onItemListener onItemListener;
        public ChatHolder(@NonNull View itemView, onItemListener onItemListener) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.chat_name);
            lastMessage = (TextView) itemView.findViewById(R.id.chat_last_message);
            avatar =(CircleImageView) itemView.findViewById(R.id.chat_avatar);
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
