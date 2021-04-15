package com.example.bizmemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {

    private RecyclerView recyclerView;
    private View ProductView;
    private ProductViewAdapter productViewAdapter;

    ArrayList<Product> productsArrayList = new ArrayList<>();

    DatabaseReference productRef,categoryRef;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private ProgressDialog loadingBar;

    private Button addProduct, printPdf;

    private Spinner spinner;



    public ProductFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        ProductView= inflater.inflate(R.layout.fragment_product,container,false);

//        Toolbar toolbar =(Toolbar) ProductView.findViewById(R.id.toolbar);
//        toolbar.setTitle("BizMemo");
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);



        mAuth= FirebaseAuth.getInstance();
        productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        categoryRef = FirebaseDatabase.getInstance().getReference().child("Category");


        recyclerView = (RecyclerView) ProductView.findViewById(R.id.product_list_recylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        productViewAdapter= new ProductViewAdapter(productsArrayList,getContext());
        recyclerView.setAdapter(productViewAdapter);

        loadingBar = new ProgressDialog(getContext());

        addProduct= ProductView.findViewById(R.id.add_product_button);
        printPdf = ProductView.findViewById(R.id.print_button);

        spinner=(Spinner) ProductView.findViewById(R.id.category_spinner);


        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,new AddProductFragment()).commit();

            }
        });



        return ProductView;
    }

    @Override
    public void onStart() {
        super.onStart();

        loadingBar.setTitle("Products");
        loadingBar.setMessage("Please Wait,until the loading is completed.....");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        productRef.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productsArrayList.clear();

                for(DataSnapshot keyNode: snapshot.getChildren()){
                    Product product=keyNode.getValue(Product.class);
                    if(product!=null){
                        productsArrayList.add(product);
                        productViewAdapter.notifyDataSetChanged();
                    }
                }

                loadingBar.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                loadingBar.dismiss();
            }
        });

        categoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<String> items = new ArrayList<String>();

                for (DataSnapshot node:snapshot.getChildren()){
                    Category item= node.getValue(Category.class);

                    items.add(item.getCat_name());
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, items);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}