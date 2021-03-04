package com.example.mykrishimall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText mobileInput, passwordInput;
    private Button loginButton;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mobileInput = findViewById(R.id.login_phone_input);
        passwordInput = findViewById(R.id.login_password_input);
        loginButton = findViewById(R.id.login_btn);
        loadingBar = new ProgressDialog(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });

    }


    private void LoginUser()
    {
        String phone = mobileInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (TextUtils.isEmpty(phone)){
        Toast.makeText(this, "Please! Enter your Phone number.", Toast.LENGTH_SHORT).show();
    }
    else if (TextUtils.isEmpty(password)){
        Toast.makeText(this, "Please! Enter password.", Toast.LENGTH_SHORT).show();
    }
    else
        {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessAccount(phone, password);
        }


    }

    private void AllowAccessAccount(String phone, String password)
    {
        final DatabaseReference Rootref;
        Rootref = FirebaseDatabase.getInstance().getReference();

        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }
}