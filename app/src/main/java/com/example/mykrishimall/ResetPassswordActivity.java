package com.example.mykrishimall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mykrishimall.Prevalent.Prevalent;
import com.example.mykrishimall.ui.Setting.SettingViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.concurrent.locks.ReadWriteLock;

public class ResetPassswordActivity extends AppCompatActivity {

    private String check = "";
    private TextView pageTitle, titleQuestion;
    private EditText phoneNumber, question1, question2;
    private Button verifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_passsword);

        check = getIntent().getStringExtra("check");

        pageTitle = findViewById(R.id.Reset_password_title);
        titleQuestion = findViewById(R.id.security_txtView);
        phoneNumber = findViewById(R.id.find_phone_number);
        question1 = findViewById(R.id.security_Question_1);
        question2 = findViewById(R.id.security_Question_2);
        verifyButton = findViewById(R.id.verify_btn);


    }

    @Override
    protected void onStart()
    {
        super.onStart();

        phoneNumber.setVisibility(View.GONE);
        if (check.equals("settings"))
        {
            pageTitle.setText("Set Question");
            //titleQuestion.setText("What's Your favourite ANIME");
            verifyButton.setText("Set");

            displayAnswer();

            verifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    setAnswer();

                }
            });
        }
        else if(check.equals("login"))
        {
            phoneNumber.setVisibility(View.VISIBLE);


            verifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    verifyUser();

                }
            });


        }
    }

    private void setAnswer()
    {
        String answer1 = question1.getText().toString().toLowerCase();
        String answer2 = question2.getText().toString().toLowerCase();

        if (question1.equals("") && question2.equals(""))
        {
            Toast.makeText(ResetPassswordActivity.this,"Please! Give answer", Toast.LENGTH_SHORT).show();
        }
        else
        {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                    .child("Users")
                    .child(Prevalent.currentOnlineUser.getPhone());


            HashMap<String, Object> securityMap = new HashMap<>();
            securityMap.put("answer1", answer1);
            securityMap.put("answer2", answer2);

            ref.child("Security Question").updateChildren(securityMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(ResetPassswordActivity.this,"Your Security answer is set successfully.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ResetPassswordActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }

                }
            });
        }
    }

    private void displayAnswer()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(Prevalent.currentOnlineUser.getPhone());


        ref.child("Security Question").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    String ans1 = snapshot.child("answer1").getValue().toString();
                    String ans2 = snapshot.child("answer2").getValue().toString();

                    question1.setText(ans1);
                    question2.setText(ans2);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

   private void verifyUser()
    {
        final String phone = phoneNumber.getText().toString();
        final String answer1 = question1.getText().toString().toLowerCase();
        final String answer2 = question2.getText().toString().toLowerCase();

        if (!phone.equals("") && !answer1.equals("") && !answer2.equals(""))
        {
            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                    .child("Users")
                    .child(phone);

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    if (snapshot.exists())
                    {
                        String mphone = snapshot.child("phone").getValue().toString();

                        if (snapshot.hasChild("Security Question"))
                        {
                            String ans1 = snapshot.child("Security Question").child("answer1").getValue().toString();
                            String ans2 = snapshot.child("Security Question").child("answer2").getValue().toString();

                            if (!ans1.equals(answer1))
                            {
                                Toast.makeText(ResetPassswordActivity.this,"Your First Answer is wrong", Toast.LENGTH_SHORT).show();
                            }
                            else if (!ans2.equals(  answer2))
                            {
                                Toast.makeText(ResetPassswordActivity.this,"Your second Answer is wrong", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ResetPassswordActivity.this);
                                builder.setTitle("New Password");

                                final EditText newPassword = new EditText(ResetPassswordActivity.this);
                                newPassword.setHint("Enter your new password");
                                builder.setView(newPassword);

                                builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        if (!newPassword.getText().toString().equals(""))
                                        {
                                            ref.child("password")
                                                    .setValue(newPassword.getText().toString())
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task)
                                                        {
                                                            if (task.isSuccessful())
                                                            {
                                                                Toast.makeText(ResetPassswordActivity.this,"Password change successfully", Toast.LENGTH_SHORT).show();

                                                                Intent intent = new Intent(ResetPassswordActivity.this, LoginActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        }
                                                    });
                                        }

                                    }
                                });
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        dialogInterface.cancel();
                                    }
                                });

                                builder.show();
                            }

                        }
                        else
                        {
                            Toast.makeText(ResetPassswordActivity.this,"You have not set security question.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(ResetPassswordActivity.this,"this phone number is not exist in our database", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error)
                {

                }
            });

        }
        else
        {
            Toast.makeText(ResetPassswordActivity.this,"You should complete the form", Toast.LENGTH_SHORT).show();
        }



    }
}
