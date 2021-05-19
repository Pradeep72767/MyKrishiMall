package com.example.mykrishimall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.YuvImage;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mykrishimall.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private EditText nameEditText, phoneEditText, addressEditText, cityEditText;
    private Button confirmOrderBtn;
    private TextView totalAmountTextView, totalAmountShowingTextView;

    private  String totalAmount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        totalAmount = getIntent().getStringExtra("Total Price");

        confirmOrderBtn  = findViewById(R.id.confirm_final_button);
        nameEditText = findViewById(R.id.shipment_customer_name);
        phoneEditText = findViewById(R.id.shipment_customer_phone);
        addressEditText = findViewById(R.id.shipment_customer_address);
        cityEditText = findViewById(R.id.shipment_customer_city);
        totalAmountTextView = findViewById(R.id.txt_total_amount);
        totalAmountShowingTextView = findViewById(R.id.txt_total_amount_show);

        totalAmountShowingTextView.setText(totalAmount);

        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Check();
            }
        });


    }

    private void Check()
    {
        if (TextUtils.isEmpty(nameEditText.getText().toString()))
        {
            Toast.makeText(this,"Please provide your name", Toast.LENGTH_SHORT).show();
        }
        else  if (TextUtils.isEmpty(phoneEditText.getText().toString()))
        {
            Toast.makeText(this,"Please provide your mobile number", Toast.LENGTH_SHORT).show();
        }
        else  if (TextUtils.isEmpty(addressEditText.getText().toString()))
        {
            Toast.makeText(this,"Please provide your Address", Toast.LENGTH_SHORT).show();
        }
        else  if (TextUtils.isEmpty(cityEditText.getText().toString()))
        {
            Toast.makeText(this,"Please provide your City name", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ConfirmOrder();
        }
    }

    private void ConfirmOrder()
    {
        final String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate =  new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());


        SimpleDateFormat currentTime =  new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());

        final HashMap<String, Object> orderMap = new HashMap<>();
        orderMap.put("totalAmount",totalAmount);
        orderMap.put("name",nameEditText.getText().toString());
        orderMap.put("phone", phoneEditText.getText().toString());
        orderMap.put("Address", addressEditText.getText().toString());
        orderMap.put("City", cityEditText.getText().toString());
        orderMap.put("Date", saveCurrentDate);
        orderMap.put("Time", saveCurrentTime);
        orderMap.put("State", "not shipped");

        orderRef.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                FirebaseDatabase.getInstance().getReference().child("Cart List")
                        .child("User View")
                        .child(Prevalent.currentOnlineUser.getPhone())
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(ConfirmFinalOrderActivity.this,"Order placed successfully!!", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(ConfirmFinalOrderActivity.this, HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });


    }
}