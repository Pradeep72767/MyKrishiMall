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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops_category);

        vegetable = findViewById(R.id.vegetables_icon);
        grain = findViewById(R.id.grains_icon);
        fruit = findViewById(R.id.fruit_icon);





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