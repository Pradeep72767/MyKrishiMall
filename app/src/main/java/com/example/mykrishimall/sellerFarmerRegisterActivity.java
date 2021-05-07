package com.example.mykrishimall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class sellerFarmerRegisterActivity extends AppCompatActivity {

    private Button sellerLoginpageBtn, registerBtn;
    private EditText nameInput, phoneInput, emailInput, passwordInput, addressInput;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_farmer_register);

        mAuth = FirebaseAuth.getInstance();

        sellerLoginpageBtn = findViewById(R.id.seller_farmer_alreadyHaveAcc_Btn);
        nameInput = findViewById(R.id.selle_farmer_name);
        phoneInput = findViewById(R.id.seller_farmer_number);
        emailInput = findViewById(R.id.seller_farmer_email);
        passwordInput = findViewById(R.id.seller_farmer_password);
        addressInput = findViewById(R.id.seller_farmer_address);
        registerBtn = findViewById(R.id.seller_farmer_register_Btn);
        loadingBar = new ProgressDialog(this);


        sellerLoginpageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(sellerFarmerRegisterActivity.this, seellerFarmerLoginActivity.class);
                startActivity(intent);
            }
        });


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                registerSeller();

            }
        });

    }

    private void registerSeller()
    {
        final String name = nameInput.getText().toString();
        final String phone = phoneInput.getText().toString();
        final String email = emailInput.getText().toString();
        final String password = passwordInput.getText().toString();
        final String address = addressInput.getText().toString();


        if (!name.equals("") && !phone.equals("") && !email.equals("") && !password.equals("") && !address.equals(""))
        {
            loadingBar.setTitle("Creating Account");
            loadingBar.setMessage("Please wait...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                final DatabaseReference rootRef;
                                rootRef = FirebaseDatabase.getInstance().getReference();

                                String sid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                HashMap<String, Object> sellerMap = new HashMap<>();
                                sellerMap.put("sid", sid);
                                sellerMap.put("name", name);
                                sellerMap.put("phone", phone);
                                sellerMap.put("email", email);
                                sellerMap.put("password", password);
                                sellerMap.put("address", address);


                                rootRef.child("Seller Farmers").child(sid).updateChildren(sellerMap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task)
                                            {
                                                loadingBar.dismiss();
                                                Toast.makeText(sellerFarmerRegisterActivity.this, "Seller registered seccussfully", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(sellerFarmerRegisterActivity.this, SellerHomeActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK  | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                            }
                        }
                    });

        }
        else
        {
            Toast.makeText(sellerFarmerRegisterActivity.this,"Please, fill all fiels", Toast.LENGTH_SHORT).show();
        }
    }
}