package com.example.mykrishimall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.customtabs.IPostMessageService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mykrishimall.Model.AdminOrder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FarmerNewOrdersActivity extends AppCompatActivity {

    private RecyclerView orderList;
    private DatabaseReference orderref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_new_orders);

        orderref = FirebaseDatabase.getInstance().getReference().child("Orders");

        orderList = findViewById(R.id.order_list);
        orderList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<AdminOrder> options =
                new FirebaseRecyclerOptions.Builder<AdminOrder>()
                .setQuery(orderref, AdminOrder.class)
                .build();

        FirebaseRecyclerAdapter<AdminOrder, AdminOrderViewHolder> adapter =
                new FirebaseRecyclerAdapter<AdminOrder, AdminOrderViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminOrderViewHolder holder, final int position, @NonNull final AdminOrder model)
                    {
                        holder.userName.setText("Name: " + model.getName());
                        holder.userAddress.setText("Address: "+ model.getAddress() + ", "+ model.getCity());
                        holder.userPhoneNumber.setText("Phone: "+ model.getPhone());
                        holder.userDateTime.setText("ordered at: " + model.getDate() + " " + model.getTime());
                        holder.userTotalPrice.setText("Total Price: " + model.getTotalAmount());


                        System.out.println(model.getName()+"\n\n"+model.getTotalAmount());

                        holder.showOrdersBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String uID = getRef(position).getKey();

                                Intent intent = new Intent(FarmerNewOrdersActivity.this, FarmerUserProductsActivity.class);
                                intent.putExtra("uid", uID);
                                startActivity(intent);
                            }
                        });

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                CharSequence options[] = new CharSequence[]
                                        {
                                                "Yes",
                                                "No"
                                        };
                                AlertDialog.Builder builder = new AlertDialog.Builder(FarmerNewOrdersActivity.this);
                                builder.setTitle(" have you shipped this order  product!");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        if (i == 0)
                                        {
                                            String uID = getRef(position).getKey();

                                            RemoveOrder(uID);
                                        }
                                        else
                                        {
                                            finish();
                                        }
                                    }
                                });

                                builder.show();

                            }
                        });


                    }

                    @NonNull
                    @Override
                    public AdminOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);
                        return new AdminOrderViewHolder(view);
                    }
                };

        orderList.setAdapter(adapter);
        adapter.startListening();
    }



    public static class AdminOrderViewHolder extends RecyclerView.ViewHolder
    {
        public TextView userName, userPhoneNumber, userTotalPrice, userAddress, userDateTime;
        public Button showOrdersBtn;

        public AdminOrderViewHolder(@NonNull View itemView)
        {
            super(itemView);


            userName = itemView.findViewById(R.id.ord_user_name);
            userPhoneNumber = itemView.findViewById(R.id.ord_order_phone);
            userTotalPrice = itemView.findViewById(R.id.ord_Total_price);
            userAddress = itemView.findViewById(R.id.ord_order_address);
            userDateTime = itemView.findViewById(R.id.ord_Date);

            showOrdersBtn = itemView.findViewById(R.id.show_all_product_btn);

        }
    }

    private void RemoveOrder(String uID)
    {
        orderref.child(uID).removeValue();
    }
}