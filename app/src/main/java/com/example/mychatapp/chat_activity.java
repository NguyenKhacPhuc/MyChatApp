package com.example.mychatapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


import de.hdodenhof.circleimageview.CircleImageView;

public class chat_activity extends AppCompatActivity {
    private Button backtoChat;
    private ImageButton send_btn;
    private ImageButton attachFile;
    private Intent intent;
    private ImageButton record;
    private TextView opponentUserName;
    private CircleImageView avatar;
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
        avatar = (CircleImageView) findViewById(R.id.current_avatar);
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
        Message mess = new Message(userName,opponentUserNameString,content);
        messages.add(mess);
        databaseReference = FirebaseFirestore.getInstance().document("User/"+userName);
        databaseReference.update("Chat History", FieldValue.arrayUnion(mess));
        FirebaseFirestore.getInstance().document("Chat/"+userName).set(mess);
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
        if(userName.equals(sender) && opponentUserNameString.equals(receiver)
                || userName.equals(receiver) && opponentUserNameString.equals(sender)) {
            Message mess = new Message(sender, receiver, message);
            realtimeMess.add(mess);
        }
        messageAdapter = new MessageAdapter(getApplicationContext(),realtimeMess,userName,opponentUserNameString);
        chatContent.setAdapter(messageAdapter);
    }


}
