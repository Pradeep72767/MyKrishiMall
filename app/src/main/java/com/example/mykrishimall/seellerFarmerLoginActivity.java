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

public class seellerFarmerLoginActivity extends AppCompatActivity {

    private EditText sellerEmailInput, sellerPasswordInput;
    private Button sellerLogin;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeller_farmer_login);

        mAuth = FirebaseAuth.getInstance();

        sellerEmailInput = findViewById(R.id.selle_farmer_login_email);
        sellerPasswordInput = findViewById(R.id.seller_farmer_login_password);
        sellerLogin = findViewById(R.id.seller_farmer_login_Btn);
        loadingBar = new ProgressDialog(this);



        sellerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LoginSeller();

            }
        });

    }

    private void LoginSeller()
    {
        final String email = sellerEmailInput.getText().toString();
        final String password = sellerPasswordInput.getText().toString();

        if (!email.equals("") && !password.equals(""))
        {
            loadingBar.setTitle("Login...");
            loadingBar.setMessage("Please wait...");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                Intent intent = new Intent(seellerFarmerLoginActivity.this, SellerHomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK  | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }

                        }
                    });
        }
        else
        {
            Toast.makeText(seellerFarmerLoginActivity.this, "Please fill both field", Toast.LENGTH_SHORT).show();
        }
    }
}