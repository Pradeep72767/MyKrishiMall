package com.example.mykrishimall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class FarmersAddCropsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmers_add_crops);

        Toast.makeText(this,"Farmers, Welcome..", Toast.LENGTH_SHORT).show();
    }
}