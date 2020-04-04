package com.example.mychatapp;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {
    private final int  LEFT_MESSAGE_MARK = 0;
    private final int  Right_MESSAGE_MARK = 1;
    private ArrayList<Message> messages;
    private Context context;
    private String userName;
    private String receiver;
    private boolean check;

    public MessageAdapter(Context context, ArrayList<Message> messages,String userName, String receiver){
        this.messages = messages;
        this.context = context;
        this.userName = userName;
        this.receiver = receiver;
    }
    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == Right_MESSAGE_MARK) {
           LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.chat_right_item, parent, false);
            return new MessageHolder(itemView);
        }
        else{
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.chat_left_item, parent, false);
            return new MessageHolder(itemView);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
            Message message = messages.get(position);
            holder.showMessage.setText(message.getMessage());
            if(check) {
                Picasso.with(context).load(message.getReceiverAvatar()).into(holder.avatar);
            }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder {
        TextView showMessage;
        CircleImageView avatar;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);
           showMessage = (TextView) itemView.findViewById(R.id.show_message);
           avatar = (CircleImageView) itemView.findViewById(R.id.avtImage);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(userName.equals(messages.get(position).getSender())){
            return Right_MESSAGE_MARK;
        }
        check = true;
        return LEFT_MESSAGE_MARK;
    }
}
