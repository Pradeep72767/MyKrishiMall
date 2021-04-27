package com.example.mykrishimall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminMaintainProductActivity extends AppCompatActivity {

    private Button applyChangeBtn, deleteBtn;
    private EditText name, price, description;
    private ImageView productImage;
    private String productID = "";
    private DatabaseReference productRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_product);

        productID = getIntent().getStringExtra("pid");
        productRef = FirebaseDatabase.getInstance().getReference().child("Crops").child(productID);

        applyChangeBtn = findViewById(R.id.maintain_product_Btn2);
        deleteBtn = findViewById(R.id.maintain_delete_product_btn);
        name = findViewById(R.id.maintain_product_name);
        price = findViewById(R.id.maintain_product_price);
        description = findViewById(R.id.maintain_product_description);
        productImage = findViewById(R.id.maintain_product_image);

        displaySpecificProductInfo();

        applyChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                applyChanges();

            }
        });


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                deleteThisProduct();
            }
        });
    }

    private void deleteThisProduct()
    {
        productRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                Intent intent = new Intent(AdminMaintainProductActivity.this, CropsCategoryActivity.class);
                startActivity(intent);
                finish();

                Toast.makeText(AdminMaintainProductActivity.this,"Product Deleted", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void applyChanges()
    {
        String pname = name.getText().toString();
        String pprice = price.getText().toString();
        String pdescription = description.getText().toString();

        if (pname.equals(""))
        {
            Toast.makeText(this,"please write down name of the product", Toast.LENGTH_SHORT).show();
        }
        if (pprice.equals(""))
        {
            Toast.makeText(this,"please write down price of the product", Toast.LENGTH_SHORT).show();
        }
        if (pdescription.equals(""))
        {
            Toast.makeText(this,"please write down description of the product", Toast.LENGTH_SHORT).show();
        }
        else
        {
            HashMap<String, Object> productMap = new HashMap<>();
            productMap.put("pid", productID);
            productMap.put("description", pdescription);
            productMap.put("price", pprice);
            productMap.put("name", pname);

            productRef.updateChildren( productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(AdminMaintainProductActivity.this,"Changes Applied", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(AdminMaintainProductActivity.this, CropsCategoryActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });

        }

    }

    private void displaySpecificProductInfo()
    {
        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    String pname = snapshot.child("name").getValue().toString();
                    String pprice = snapshot.child("price").getValue().toString();
                    String pdescription = snapshot.child("description").getValue().toString();
                    String pimage = snapshot.child("image").getValue().toString();

                    name.setText(pname);
                    price.setText(pprice);
                    description.setText(pdescription);

                    Picasso.get().load(pimage).into(productImage);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}