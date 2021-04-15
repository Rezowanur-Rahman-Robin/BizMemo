package com.example.bizmemo;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalesFragment extends Fragment {

    private RecyclerView recyclerView;
    private View SalesView;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private ProgressDialog loadingBar;

    private SaleViewAdapter saleViewAdapter;

    ArrayList<Sale> saleArrayList = new ArrayList<>();

    DatabaseReference saleRef;

    private Button addSales,printPdf;

    private Spinner spinner;

    private List<String> timePeriodList = new ArrayList<String>();

    private String selected_timePeriod;




    public SalesFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SalesView=inflater.inflate(R.layout.fragment_sales, container, false);

        mAuth= FirebaseAuth.getInstance();
        saleRef = FirebaseDatabase.getInstance().getReference().child("Sales");



        recyclerView = (RecyclerView) SalesView.findViewById(R.id.sales_list_recylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        saleViewAdapter= new SaleViewAdapter(saleArrayList,getContext());
        recyclerView.setAdapter(saleViewAdapter);

        loadingBar = new ProgressDialog(getContext());

        addSales= SalesView.findViewById(R.id.add_sale_button);
        printPdf = SalesView.findViewById(R.id.print_sale_button);

        spinner=(Spinner) SalesView.findViewById(R.id.sales_spinner);

        timePeriodList.add("30 Days");
        timePeriodList.add("20 Days");
        timePeriodList.add("15 Days");
        timePeriodList.add("7 Days");
        timePeriodList.add("60 Days");
        timePeriodList.add("90 Days");
        timePeriodList.add("365 Days");




        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),R.layout.spinner_layout,timePeriodList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);

        addSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,new AddSaleFragment()).commit();
            }
        });

        spinner.setOnItemSelectedListener(sale_listener);




        return SalesView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private AdapterView.OnItemSelectedListener sale_listener= new AdapterView.OnItemSelectedListener(){

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selected_timePeriod = spinner.getItemAtPosition(position).toString();

            Toast.makeText(getContext(),selected_timePeriod,Toast.LENGTH_LONG);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            selected_timePeriod=timePeriodList.get(0);
        }
    };
}