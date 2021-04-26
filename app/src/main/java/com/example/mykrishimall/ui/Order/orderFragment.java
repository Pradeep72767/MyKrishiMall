package com.example.mykrishimall.ui.Order;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mykrishimall.Model.Products;
import com.example.mykrishimall.ProductDetailActivity;
import com.example.mykrishimall.R;
import com.example.mykrishimall.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class orderFragment extends Fragment {

    private OrderViewModel orderViewModel;

    private Button searchBtn;
    private EditText inputText;
    private RecyclerView searchList;
    private String searchInput;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final  View root = inflater.inflate(R.layout.order_fragment, container, false);



//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//           // setContentView(R.layout.activity_search_crops);

            inputText = root.findViewById(R.id.search_product_name);
            searchBtn = root.findViewById(R.id.search_btn);
            searchList = root.findViewById(R.id.search_list);
            searchList.setLayoutManager(new LinearLayoutManager(getActivity()));

            searchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    searchInput = inputText.getText().toString();

                    onStart();
                }
            });

            return root;

        }

        @Override
        public void onStart() {
            super.onStart();

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Crops");

            FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                    .setQuery(reference.orderByChild("name").startAt(searchInput), Products.class)
                    .build();


            FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                    new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model)
                        {
                            holder.txtProductName.setText(model.getName());
                            holder.txtProductDesc.setText(model.getDescription());
                            holder.txtProductPrice.setText("Price = " + model.getPrice() + "Rs.");

                            Picasso.get().load(model.getImage()).into(holder.imageView);

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                                    intent.putExtra("pid", model.getPid());
                                    startActivity(intent);
                                }
                            });

                        }

                        @NonNull
                        @Override
                        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                        {

                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
                            ProductViewHolder holder = new ProductViewHolder(view);
                            return holder;

                        }
                    };

            searchList.setAdapter(adapter);
            adapter.startListening();

        }



}