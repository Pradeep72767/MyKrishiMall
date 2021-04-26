package com.example.mykrishimall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AdminMaintainProductActivity extends AppCompatActivity {

    private Button applyChangeBtn;
    private EditText name, price, description;
    private ImageView productImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_product);

        applyChangeBtn = findViewById(R.id.maintain_product_btn);
        name = findViewById(R.id.maintain_product_name);
        price = findViewById(R.id.maintain_product_price);
        description = findViewById(R.id.maintain_product_description);
        productImage = findViewById(R.id.maintain_product_image);
    }
}