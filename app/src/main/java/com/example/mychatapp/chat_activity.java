package com.example.mychatapp;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.firestore.DocumentSnapshot;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


import de.hdodenhof.circleimageview.CircleImageView;

public class chat_activity extends AppCompatActivity {
    private Button backtoChat;
    private ImageButton send_btn;
    private ImageButton attachFile;
    private Intent intent;
    private ImageButton record;
    private TextView opponentUserName;
    private CircleImageView avatarImage;
    private RecyclerView chatContent;
    private EditText inputMessage;
    private FirebaseUser firebaseUser;
    private DocumentReference reference;
    private Bundle bundle ;
    private String opponentUserNameString ;
    private String userName;
    private MessageAdapter messageAdapter;
    private DocumentReference databaseReference;
    private ArrayList<Message> messages = new ArrayList<>();
    private ArrayList<Message> realtimeMess =  new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.specific_chat_layout);
        initView();
        bundle = intent.getBundleExtra("bun");
        assert bundle != null;
        opponentUserNameString = bundle.getString("opponentUserName");
        userName = bundle.getString("currentUserName");
        assert bundle != null;
        opponentUserName.setText(opponentUserNameString);
        opponentUserNameString = bundle.getString("opponentUserName");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        linearLayoutManager.setStackFromEnd(true);
        chatContent.setLayoutManager(linearLayoutManager);
        send_btn.setOnClickListener(v -> {
            String content = inputMessage.getText().toString();
            if(!content.isEmpty()){
                sendMessage(content);
                inputMessage.setText("");
            }
            inputMessage.requestFocus();
        });
        FirebaseFirestore.getInstance().document("ChatBoxes"+userName+"-"+opponentUserNameString)
                .get()
                .addOnCompleteListener(task ->{
            DocumentSnapshot documentSnapshot = task.getResult();
                    assert documentSnapshot != null;
                    Map<String, Object> data = documentSnapshot.getData();
                    assert data != null;
                    for(Map.Entry<String,Object> specificData : data.entrySet()){
                        if(specificData.getKey().equals("Box")){
                            ArrayList<HashMap<String, Object>> dtbBox = (ArrayList<HashMap<String, Object>>) specificData.getValue();
                            for(HashMap<String,Object> oldMessages: dtbBox){
                                    parseData(oldMessages);
                            }
                        }
            }
        });
        FirebaseFirestore.getInstance().document("Chat/"+userName).addSnapshotListener((documentSnapshot, e) -> {
            assert documentSnapshot != null;
            parseData(documentSnapshot);
        });
        FirebaseFirestore.getInstance().document("Chat/"+opponentUserNameString).addSnapshotListener((documentSnapshot, e) -> {
            assert documentSnapshot != null;
            parseData(documentSnapshot);
        });
    }
    private void initView(){
        backtoChat = (Button) findViewById(R.id.backToChatList);
        backtoChat.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_chevron_left_black_24dp,0,0,0);
        send_btn  = (ImageButton) findViewById(R.id.send_btn);
        opponentUserName = (TextView)findViewById(R.id.current_username_chat);
        avatarImage = (CircleImageView) findViewById(R.id.current_avatar);
        chatContent =(RecyclerView) findViewById(R.id.chatContent);
        attachFile = (ImageButton)  findViewById(R.id.attach_file);
        record = (ImageButton) findViewById(R.id.record);
        inputMessage = (EditText) findViewById(R.id.MessageInput);
        intent = getIntent();

    }
    private void sendMessage(String content){

        HashMap<String,Object> chatDetails = new HashMap<String,Object>();
        chatDetails.put("Sender",userName);
        chatDetails.put("Receiver",opponentUserNameString);
        chatDetails.put("Message", content);
        FirebaseFirestore.getInstance().document("User/"+opponentUserNameString).get().addOnCompleteListener(task->{
            DocumentSnapshot documentSnapshot = task.getResult();
            assert documentSnapshot != null;
            String avatar = documentSnapshot.getString("Avatar");
            Picasso.with(chat_activity.this).load(avatar).into(avatarImage);
            Message mess = new Message(userName,opponentUserNameString,content);
            messages.add(mess);
            databaseReference = FirebaseFirestore.getInstance().document("User/"+userName);
            databaseReference.update("Chat History."+opponentUserNameString, FieldValue.arrayUnion(mess));
            FirebaseFirestore.getInstance().document("Chat/"+userName).set(mess);
            FirebaseFirestore.getInstance()
                    .document("ChatBoxes/"+userName+"-"+opponentUserNameString)
                    .update("Box",FieldValue.arrayUnion(mess));
        });

    }
//    private void readMessage(){
//        FirebaseFirestore.getInstance().collection("Chat").addSnapshotListener((queryDocumentSnapshots, e) -> {
//            assert queryDocumentSnapshots != null;
//            List<DocumentSnapshot> doc = queryDocumentSnapshots.getDocuments();
//            List<DocumentChange> docChanged = queryDocumentSnapshots.getDocumentChanges();
//            if(docChanged.isEmpty()) {
//                for (DocumentSnapshot documentSnapshot : doc) {
//                    parseData(documentSnapshot);
//                }
//            }else {
//                for (DocumentChange documentChange : docChanged){
//                    DocumentSnapshot snapshot = documentChange.getDocument();
//                    parseData(snapshot);
//                }
//            }
//        });
//    }

    private void parseData(DocumentSnapshot documentSnapshot){
        String sender = documentSnapshot.getString("sender");
        String receiver = documentSnapshot.getString("receiver");
        String message = documentSnapshot.getString("message");
        if(userName.equals(sender) && opponentUserNameString.equals(receiver)) {
            FirebaseFirestore.getInstance().document("User/"+userName).get().addOnCompleteListener(task->{
                String avatar = Objects.requireNonNull(task.getResult()).getString("Avatar");
                Message mess = new Message(sender, receiver, message,avatar);
                realtimeMess.add(mess);
            });

        }else if(userName.equals(receiver) && opponentUserNameString.equals(sender)){
            FirebaseFirestore.getInstance().document("User/"+opponentUserNameString).get().addOnCompleteListener(task->{
                String avatar = Objects.requireNonNull(task.getResult()).getString("Avatar");
                Message mess = new Message(sender, receiver, message,avatar);
                realtimeMess.add(mess);
            });
        }
        messageAdapter = new MessageAdapter(getApplicationContext(),realtimeMess,userName,opponentUserNameString);
        chatContent.setAdapter(messageAdapter);
    }
    private void parseData(HashMap<String,Object> data){
        String sender = Objects.requireNonNull(data.get("sender")).toString();
        String receiver = Objects.requireNonNull(data.get("receiver")).toString();
        String message = Objects.requireNonNull(data.get("message")).toString();
        if(userName.equals(sender) && opponentUserNameString.equals(receiver)) {
            FirebaseFirestore.getInstance().document("User/"+userName).get().addOnCompleteListener(task->{
                String avatar = Objects.requireNonNull(task.getResult()).getString("Avatar");
                Message mess = new Message(sender, receiver, message,avatar);
                realtimeMess.add(mess);
            });

        }else if(userName.equals(receiver) && opponentUserNameString.equals(sender)){
            FirebaseFirestore.getInstance().document("User/"+opponentUserNameString).get().addOnCompleteListener(task->{
                String avatar = Objects.requireNonNull(task.getResult()).getString("Avatar");
                Message mess = new Message(sender, receiver, message,avatar);
                realtimeMess.add(mess);
            });
        }
        messageAdapter = new MessageAdapter(getApplicationContext(),realtimeMess,userName,opponentUserNameString);
        chatContent.setAdapter(messageAdapter);
    }
}
