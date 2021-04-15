package com.example.bizmemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductFragment extends Fragment {

    DatabaseReference productRef,categoryRef,rootRef;

    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    private Button addNewProduct;
    private EditText p_name,p_model,p_size,p_weight,p_price,p_desc;
    private Spinner category_spinner;

    private String selected_category;

    private ImageButton uploadButton;
    private ImageView selectedImage;

    private Uri imageUri;

    private StorageReference mStorageRef;

    List<String>  categories = new ArrayList<String>();




    private static final int PICK_IMAGE=233;




    public AddProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View addProductView=inflater.inflate(R.layout.fragment_add_product,container,false);


        mAuth= FirebaseAuth.getInstance();
        productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        rootRef= FirebaseDatabase.getInstance().getReference();

        categoryRef = FirebaseDatabase.getInstance().getReference().child("Category");


        loadingBar = new ProgressDialog(getContext());

        p_name= addProductView.findViewById(R.id.insert_product_name);
        p_model= addProductView.findViewById(R.id.insert_product_model);
        p_size= addProductView.findViewById(R.id.insert_product_size);
        p_weight= addProductView.findViewById(R.id.insert_product_weight);
        p_price= addProductView.findViewById(R.id.insert_product_price);
        p_desc= addProductView.findViewById(R.id.insert_product_desc);
        category_spinner= addProductView.findViewById(R.id.insert_category_spinner);

        uploadButton=addProductView.findViewById(R.id.product_image_upload_button_add_product);
        selectedImage=addProductView.findViewById(R.id.selected_product_image_add_product);


        mStorageRef= FirebaseStorage.getInstance().getReference();



        addNewProduct= addProductView.findViewById(R.id.add_new_product);

        addNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProductToDataBase();
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallery =new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery,"Select Product Image"),PICK_IMAGE);
            }
        });
        category_spinner.setOnItemSelectedListener(category_listener);




        return addProductView;
    }

    @Override
    public void onStart() {
        super.onStart();


        categoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<String> items = new ArrayList<String>();

                for (DataSnapshot node:snapshot.getChildren()){
                    Category item= node.getValue(Category.class);

                    items.add(item.getCat_name());
                    categories.add(item.getCat_name());
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_layout, items);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                category_spinner.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

   private AdapterView.OnItemSelectedListener category_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(position>0){
                selected_category = category_spinner.getItemAtPosition(position).toString();
//                selected_category=category.getCat_name();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            selected_category="All";

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode== Activity.RESULT_OK && requestCode==PICK_IMAGE && data!=null){

            imageUri = data.getData();
            try {
                Bitmap bitmap;
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                selectedImage.setVisibility(View.VISIBLE);
                selectedImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addProductToDataBase(){
        if(TextUtils.isEmpty(p_name.getText().toString())){
            p_name.setError("Please Enter Name");
            p_name.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(p_model.getText().toString())){
            p_model.setError("Please Enter Model");
            p_model.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(p_size.getText().toString())){
            p_size.setError("Please Enter Size");
            p_size.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(p_weight.getText().toString())){
            p_weight.setError("Please Enter Weight");
            p_weight.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(p_price.getText().toString())){
            p_price.setError("Please Enter Price");
            p_price.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(selected_category)){
            Toast.makeText(getContext(), "Please Select Category.", Toast.LENGTH_LONG).show();
            return;
        }

        loadingBar.setTitle("Add Product");
        loadingBar.setMessage("Please Wait Until the Product is Successfully Added...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        final Random random=new Random();
        int ran= random.nextInt(100000000);

        StorageReference filePath = mStorageRef.child("Product-Images").child("product"+ran+".jpg");


        filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                final Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();
                firebaseUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        final String downloadUrl= uri.toString();

                        DatabaseReference KeyRef=rootRef.child("Merchant-Products")
                                .push();

                        String messagePushKey =KeyRef.getKey();


                        Product product=new Product(messagePushKey,p_name.getText().toString(),downloadUrl,p_size.getText().toString(),p_model.getText().toString(),p_price.getText().toString(),p_weight.getText().toString(),p_desc.getText().toString(),selected_category);

                        productRef.child(messagePushKey).setValue(product).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getContext(), "Product added Successfully", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                }else{
                                    Toast.makeText(getContext(), "Error:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                }
                            }
                        });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();

                loadingBar.dismiss();

            }
        });



    }
}