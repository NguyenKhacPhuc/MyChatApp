package com.example.mychatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.widget.Button;
import android.widget.DatePicker;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    private Button signUpBtn;
    private TextView email;
    private TextView password;
    private String signUpPass;
    private String signUpEmail;
    private TextView reconfirmPassword;
    private TextView userName;
    private TextView phoneNumber;
    private DatePicker birthday;
    private boolean check;
    private static FirebaseAuth firebaseAuth;
    private ArrayList<Contacts> contactsList;
    private ArrayList<Message> chatHistory;
    private DocumentReference databaseReference ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        initView();
        signUpBtn.setOnClickListener(v -> {
             signUpPass = password.getText().toString();
             signUpEmail = email.getText().toString();

            try {
                String hashPass = toHexString(signUpPass);
                checkCondition();
                if(check){
                    String currentuserName = userName.getText().toString();
                    String userEmail = email.getText().toString();
                    String userPhoneNumber = phoneNumber.getText().toString();
                    Date userBirthday = extractDate(birthday);

                    //create object and push data to it
                    Map<String, Object> user = new HashMap<>();
                    user.put("Username",currentuserName);
                    user.put("Email",userEmail);
                    user.put("Phone number",userPhoneNumber);
                    user.put("Birthday",userBirthday);
                    user.put("Password",hashPass);
                    user.put("Contacts", contactsList);
                    user.put("Chat History",chatHistory);

                    databaseReference = FirebaseFirestore.getInstance().document("User/"+currentuserName);
                    databaseReference.set(user).addOnSuccessListener(aVoid -> {
                        Log.d("Tag1", "Save Successfully");
                    }).addOnFailureListener(e -> {
                        Log.d("Tag2", "Save failed");
                    });

                    firebaseAuth.createUserWithEmailAndPassword(signUpEmail,hashPass).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(SignUp.this,"Create account successful",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUp.this,LogIn.class));
                    }else{
                        Toast.makeText(SignUp.this,"Error Occured",Toast.LENGTH_SHORT).show();
                    }
                });
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * @effects
     * init components
     */
    private void initView() {
        signUpBtn = (Button) findViewById(R.id.sign_up_btn);
        email = (TextView) findViewById(R.id.input_sign_up_email);
        password = (TextView) findViewById(R.id.sign_up_password);
        reconfirmPassword = (TextView) findViewById(R.id.sign_up_reconfirm_password);
        userName = (TextView) findViewById(R.id.input_sign_up_username);
        phoneNumber = (TextView) findViewById(R.id.input_sign_up_phone_number);
        birthday = (DatePicker) findViewById(R.id.birthday);
        firebaseAuth = FirebaseAuth.getInstance();
        contactsList = new ArrayList<>();
        chatHistory = new ArrayList<>();
    }

    /**
     *
     * @param s
     * @effects
     * @return hash-265 pass
     * @throws NoSuchAlgorithmException
     */
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

    /**
     * @effects
     * checks user input and focus on what is missing
     */
    private void checkCondition(){
        if(signUpPass.isEmpty()
                && signUpEmail.isEmpty()
                && reconfirmPassword.getText().toString().isEmpty()
                && userName.getText().toString().isEmpty()
                && phoneNumber.getText().toString().isEmpty()){
            Toast.makeText(SignUp.this,"Please Fill in the fields above",Toast.LENGTH_SHORT).show();
            check = false;
            userName.requestFocus();
            userName.setError("Please fill in the fields");
        }
        else if(signUpPass.isEmpty()){
            password.requestFocus();
            password.setError("PLease enter your password");
            check = false;
        }else if(signUpEmail.isEmpty()){
            email.requestFocus();
            email.setError("PLease enter your email");
            check = false;
        }else if(reconfirmPassword.getText().toString().isEmpty()){
            reconfirmPassword.requestFocus();
            reconfirmPassword.setError("Please fill in reconfirm password field");
            check = false;

        }else if(userName.getText().toString().isEmpty()){
            userName.requestFocus();
            userName.setError("Please enter your username");
            check = false;
        }else if(phoneNumber.getText().toString().isEmpty()){
            phoneNumber.requestFocus();
            phoneNumber.setError("Please enter your phone number");
            check = false;
        }else{
            check = true;
        }
    }

    /**
     * @effects
     * get user birthday
     * @param datePicker
     * @return
     */
    private Date extractDate(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day);
        return calendar.getTime();
    }
}
