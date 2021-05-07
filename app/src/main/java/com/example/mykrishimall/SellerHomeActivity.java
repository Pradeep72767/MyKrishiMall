package com.example.mykrishimall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class SellerHomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);

        bottomNavigationView = findViewById(R.id.botton_nevigation_bar);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = new Shome();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmeny_container, selectedFragment).commit();
                    switch (item.getItemId())
                    {
                        case R.id.navigation_home:
                            selectedFragment = new Shome();
                            break;

                        case R.id.navigation_add:
                            Intent intentadd = new Intent(SellerHomeActivity.this, CropsCategoryActivity.class);
                            startActivity(intentadd);
                            break;

                        case R.id.navigation_logout:
//                            selectedFragment = new Slogout();
                            System.out.println("inside logout");
                            final FirebaseAuth mAuth;
                            mAuth = FirebaseAuth.getInstance();
                            mAuth.signOut();

                            Intent intent = new Intent(SellerHomeActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK  | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmeny_container, selectedFragment).commit();

                    return true;

                }
            };
}