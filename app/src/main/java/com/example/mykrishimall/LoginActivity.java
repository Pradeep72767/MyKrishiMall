package com.example.mykrishimall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mykrishimall.Model.Users;
import com.example.mykrishimall.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private EditText mobileInput, passwordInput;
    private Button loginButton;
    private ProgressDialog loadingBar;
    private TextView FarmerLink, notFarmerLink;

    private String parentDbName = "Users";
    private CheckBox chkBoxRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mobileInput = findViewById(R.id.login_phone_input);
        passwordInput = findViewById(R.id.login_password_input);
        loginButton = findViewById(R.id.login_btn);
        loadingBar = new ProgressDialog(this);
        chkBoxRememberMe = findViewById(R.id.remeber_me_chkbox);
        FarmerLink = findViewById(R.id.farmer_panel_link);
        notFarmerLink = findViewById(R.id.not_farmer_panel_link);
        Paper.init(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });

        FarmerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.setText("Login Farmer");
                FarmerLink.setVisibility(View.INVISIBLE);
                notFarmerLink.setVisibility(View.VISIBLE);
                parentDbName = "Farmers";
            }
        });

        notFarmerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.setText("Login");
                notFarmerLink.setVisibility(View.INVISIBLE);
                FarmerLink.setVisibility(View.VISIBLE);
                parentDbName = "Users";
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

    private void AllowAccessAccount(final String phone, final String password)
    {
        if (chkBoxRememberMe.isChecked())
        {
            Paper.book().write(Prevalent.UserPhoneKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, password);
        }

        final DatabaseReference Rootref;
        Rootref = FirebaseDatabase.getInstance().getReference();

        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot)
            {

                if (datasnapshot.child(parentDbName).child(phone).exists())
                {

                    Users usersData = datasnapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if (usersData.getPhone().equals(phone))
                    {
                        if (usersData.getPassword().equals(password)) {


                            if (parentDbName.equals("Farmers"))
                            {
                                Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, CropsCategoryActivity.class);
                                startActivity(intent);
                            }
                            else if (parentDbName.equals("Users"))
                            {
                                Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        }
                        }
                    }

                else
                    {
                    Toast.makeText(LoginActivity.this,"Please! Create Account", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }
}