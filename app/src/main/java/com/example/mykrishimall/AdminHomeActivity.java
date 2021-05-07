package com.example.mykrishimall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rey.material.widget.Button;

public class AdminHomeActivity extends AppCompatActivity {

    private Button checkOrderBtn, farmerLogoutBtn, maintainProductBtn, checkProductBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);


        checkOrderBtn = findViewById(R.id.cehck_order_btn);
        farmerLogoutBtn = findViewById(R.id.farmer_logout_btn);
        maintainProductBtn = findViewById(R.id.maintain_product_btn);
        checkProductBtn = findViewById(R.id.cehck_new_product_btn);



        maintainProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminHomeActivity.this, HomeActivity.class);
                intent.putExtra("Farmer","Farmer");
                startActivity(intent);

            }
        });


        farmerLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        checkOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AdminHomeActivity.this, FarmerNewOrdersActivity.class);
                startActivity(intent);
            }
        });

        checkProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, CheckNewProductsUploadByFarmesActivity.class);
                startActivity(intent);
            }
        });
    }
}