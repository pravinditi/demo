package com.iqsolutions.mydemo;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iqsolutions.mydemo.database.DatabaseController;
import com.iqsolutions.mydemo.pojo.Product;
import com.iqsolutions.mydemo.pojo.Variant;
import com.iqsolutions.mydemo.ui.adapters.VariantsAdapter;

import java.util.List;

public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener  {
    private Context context;

    private Product product;
    private TextView tv_category, tv_product_name;
    private RecyclerView rv_variants , rv_variants_price,rv_variants_size;
    private VariantsAdapter adapter;
    private List<Variant> variants;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        context = this;
        product = (Product)getIntent().getSerializableExtra("products");
        tv_category = findViewById(R.id.tv_category);
        tv_product_name = findViewById(R.id.tv_product_name);
        rv_variants = findViewById(R.id.rv_variants);
        rv_variants_price = findViewById(R.id.rv_variants_price);
        rv_variants_size = findViewById(R.id.rv_variants_Size);

        String cat_id =  DatabaseController.getInstance(context).getoneColumnfromdatabase("select distinct cat_id as cat_id from products where prod_id = '"+product.getId()+"'","cat_id");
        String cat_name =  DatabaseController.getInstance(context).getoneColumnfromdatabase("select distinct cat_name as cat_name from category where cat_id = '"+cat_id+"'","cat_name");
        tv_category.setText(cat_name);
        tv_product_name.setText(product.getName());
        setTitle(product.getName());
        variants = DatabaseController.getInstance(context).getVariants(String.valueOf(product.getId()));

        adapter = new VariantsAdapter(variants,this,"c");
        rv_variants.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rv_variants.setHasFixedSize(true);
        rv_variants.setAdapter(adapter);

        adapter = new VariantsAdapter(variants,this,"p");
        rv_variants_price.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rv_variants_price.setHasFixedSize(true);
        rv_variants_price.setAdapter(adapter);

        adapter = new VariantsAdapter(variants,this,"s");
        rv_variants_size.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rv_variants_size.setHasFixedSize(true);
        rv_variants_size.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
