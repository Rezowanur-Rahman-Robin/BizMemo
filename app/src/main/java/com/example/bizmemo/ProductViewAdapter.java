package com.example.bizmemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductViewAdapter extends RecyclerView.Adapter<ProductViewAdapter.ProductsDataHolder> {

    private ArrayList<Product> products;
    private Context myContext;

    public ProductViewAdapter(ArrayList<Product> products, Context myContext) {
        this.products = products;
        this.myContext = myContext;
    }

    @NonNull
    @Override
    public ProductViewAdapter.ProductsDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_table_data_layout,parent,false);

        return new ProductsDataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewAdapter.ProductsDataHolder holder, int position) {

        Product product =products.get(position);
        Picasso.with(myContext).load(product.getPhoto()).into(holder.productImage);
        holder.name.setText(product.getName());
        holder.size.setText(product.getSize());
        holder.model.setText(product.getModel());
        holder.price.setText(product.getPrice());
        holder.weight.setText(product.getWeight());
        holder.desc.setText(product.getDesc());

    }

    @Override
    public int getItemCount() {
        return products==null? 0: products.size();
    }

    public class ProductsDataHolder extends RecyclerView.ViewHolder{

        ImageView productImage;
        TextView name,size,model,price,weight,desc;




        public ProductsDataHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.product_photo);
            name = itemView.findViewById(R.id.product_name);
            size = itemView.findViewById(R.id.product_size);
            model = itemView.findViewById(R.id.product_model);
            price = itemView.findViewById(R.id.product_price);
            weight = itemView.findViewById(R.id.product_weight);
            desc = itemView.findViewById(R.id.product_desc);




        }


    }
}
