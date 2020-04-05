package com.example.mychatapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LogIn extends AppCompatActivity {
    private Button create;
    private EditText username;
    private EditText password;
    private Button logInBtn;
    private static FirebaseAuth firebaseAuth;
    private DocumentReference databaseReference;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initView();
        create.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_create_black_24dp,0);
//
        logInBtn.setOnClickListener(v -> {
            String usernameInput = username.getText().toString();
            String userPasswordInput = password.getText().toString();
            try {
                String hexPass = toHexString(userPasswordInput);

            if(usernameInput.isEmpty() ){
                username.requestFocus();
                username.setError("Please enter username");
            }else if(userPasswordInput.isEmpty()){
                password.requestFocus();
                password.setError("Please enter the password");
            }else if(usernameInput.isEmpty() && userPasswordInput.isEmpty()){
               Toast.makeText(LogIn.this, "Please enter the fields in order to log in",Toast.LENGTH_SHORT).show();
            }else{
                databaseReference = FirebaseFirestore.getInstance().document("User/"+username.getText().toString());
                databaseReference.get().addOnSuccessListener(documentSnapshot -> {
                    if(documentSnapshot.exists()){
                        String pass = documentSnapshot.getString("Password");
                        if(hexPass.equals(pass)){
                            Intent intent = new Intent(LogIn.this, MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("currentUserName",username.getText().toString());
                            intent.putExtra("bunUserName",bundle);
                            startActivity(intent);
                        }else{
                            password.requestFocus();
                            password.setError("Wrong password");
                            password.setText("");
                        }
                    }
                    else {
                        Toast.makeText(LogIn.this,"Account is not exist",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this,SignUp.class));
            }
        });

    }
    private void initView(){
        create = (Button) findViewById(R.id.create_new_account);
        username = (EditText) findViewById(R.id.input_username);
        password = (EditText) findViewById(R.id.input_password);
        logInBtn = (Button) findViewById(R.id.btnLogin);
        firebaseAuth = FirebaseAuth.getInstance();
    }
    private String toHexString(String s) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(s.getBytes(StandardCharsets.UTF_8));
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

//
}
