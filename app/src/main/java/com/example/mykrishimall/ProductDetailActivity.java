package com.example.mykrishimall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.mykrishimall.Model.Products;
import com.example.mykrishimall.Prevalent.Prevalent;
import com.example.mykrishimall.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailActivity extends AppCompatActivity {

   // private FloatingActionButton addTOCartBtn;
    private ImageView productImage;
    private Button addToCartButton;
    //private ElegantNumberButton numberButton;
    private TextView productPrice, productName, productDescription;
    private String productID = "", state = "Normal";
    private EditText usersRequirement;
    String price, name, description, quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        productID = getIntent().getStringExtra("pid");

       // addTOCartBtn = findViewById(R.id.add_product_to_cart);
        addToCartButton = findViewById(R.id.add_to_cart_btn);
        productImage = findViewById(R.id.product_image_detail);
        productName = findViewById(R.id.product_name_detail);
        productPrice = findViewById(R.id.product_price_detail);
        productDescription = findViewById(R.id.product_description_detail);
       // numberButton = findViewById(R.id.number_btn);
        usersRequirement = findViewById(R.id.user_quantity_requirement);

        getProductDetails(productID);

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if (state.equals("Order Placed") || state.equals("Order shipped"))
                {
                    Toast.makeText(ProductDetailActivity.this,"you can purchase more product, once farmer confirmed your previous order", Toast.LENGTH_LONG).show();
                }
                else
                {
                    AddingToCartList();
                }
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        CheckOrderState();
    }

    private void AddingToCartList()
    {
        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate =  new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());


        SimpleDateFormat currentTime =  new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartMap =  new HashMap<>();
        cartMap.put("pid", productID);
        cartMap.put("pname", name);
        cartMap.put("price", price);
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("quantity", usersRequirement.getText().toString());

        System.out.println(""+productID+" ,,,,,,,,,\n"+name+" ,,,,,,,,,,,\n"+price+" ,,,,,,,,,,,,\n"+saveCurrentDate+" ,,,,,,,,,\n"+saveCurrentTime+",,,,,,,,,,,,\n "+quantity);

        cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone()).child("Crops")
                .child(productID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            cartListRef.child("Farmer View").child(Prevalent.currentOnlineUser.getPhone()).child("Crops")
                                    .child(productID)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if (task.isSuccessful())
                                            {
                                                Toast.makeText(ProductDetailActivity.this, "Added to the cart list", Toast.LENGTH_SHORT).show();

//                                                FragmentManager manager = getSupportFragmentManager();
//                                                FragmentTransaction t = manager.beginTransaction();


                                                Intent intent = new Intent(ProductDetailActivity.this, HomeActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                        }
                    }
                });

    }

    private void getProductDetails(final String productID)
    {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Crops");

        productRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    Products products = snapshot.getValue(Products.class);

                   // productName.setText(products.getName());
                    productName.setText(products.getName());
                    productDescription.setText(products.getDescription());
                    productPrice.setText(products.getPrice());
                    Picasso.get().load(products.getImage()).into(productImage);
                    price = products.getPrice();
                    name = products.getName();
                    description = products.getDescription();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }


    private void CheckOrderState()
    {
        DatabaseReference orderRef;
        orderRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getPhone());

        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    String shippingState = snapshot.child("State").getValue().toString();

                    if (shippingState.equals("shipped"))
                    {
                        state = "Order shipped";
                    }
                    else if (shippingState.equals("Not shipped"))
                    {
                        state = "Order Placed";
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}