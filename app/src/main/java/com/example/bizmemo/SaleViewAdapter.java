package com.example.bizmemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SaleViewAdapter extends RecyclerView.Adapter<SaleViewAdapter.SaleDataHolder> {

    private ArrayList<Sale> sales;
    private Context myContext;

    public SaleViewAdapter(ArrayList<Sale> sales, Context myContext) {
        this.sales = sales;
        this.myContext = myContext;
    }

    @NonNull
    @Override
    public SaleDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_table_data_layout,parent,false);

        return new SaleDataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleDataHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return sales==null? 0:sales.size();
    }

    public class SaleDataHolder extends RecyclerView.ViewHolder{
        TextView c_name,p_qty,t_item,earning;

        public SaleDataHolder(@NonNull View itemView) {
            super(itemView);

            c_name= itemView.findViewById(R.id.sales_company_name);
            p_qty= itemView.findViewById(R.id.sales_product_quantity);
            t_item= itemView.findViewById(R.id.sales_total_item);
            earning= itemView.findViewById(R.id.sales_total_earnings);

        }
    }
}
