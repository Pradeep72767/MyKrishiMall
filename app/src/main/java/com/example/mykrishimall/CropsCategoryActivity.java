package com.example.mykrishimall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rey.material.widget.Button;
import com.rey.material.widget.ImageView;

public class CropsCategoryActivity extends AppCompatActivity {

    private ImageView vegetable;
    private ImageView grain;
    private ImageView fruit;

    private Button checkOrderBtn, farmerLogoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops_category);

        vegetable = findViewById(R.id.vegetables_icon);
        grain = findViewById(R.id.grains_icon);
        fruit = findViewById(R.id.fruit_icon);
        checkOrderBtn = findViewById(R.id.cehck_order_btn);
        farmerLogoutBtn = findViewById(R.id.farmer_logout_btn);


        farmerLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CropsCategoryActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        checkOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(CropsCategoryActivity.this, FarmerNewOrdersActivity.class);
                startActivity(intent);
            }
        });

        vegetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CropsCategoryActivity.this, FarmersAddCropsActivity.class);
                intent.putExtra("category","vegetable");
                startActivity(intent);
            }
        });

        grain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CropsCategoryActivity.this, FarmersAddCropsActivity.class);
                intent.putExtra("category","grain");
                startActivity(intent);
            }
        });

        fruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CropsCategoryActivity.this, FarmersAddCropsActivity.class);
                intent.putExtra("category","fruit");
                startActivity(intent);
            }
        });
    }
}