package com.iqsolutions.mydemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.iqsolutions.mydemo.database.DatabaseController;
import com.iqsolutions.mydemo.pojo.Category;
import com.iqsolutions.mydemo.pojo.Product;

import com.iqsolutions.mydemo.ui.adapters.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ProductActivity";
    private RecyclerView rv_products;
    private ProgressBar pb_progress;
    private List<Product> products;
    private ProductAdapter adapter;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        setTitle("Products");
        context = this;

        products = (List<Product>) getIntent().getSerializableExtra("products");
        rv_products = findViewById(R.id.rv_products);
        pb_progress = findViewById(R.id.pb_progress);

        adapter = new ProductAdapter(products,this);
        rv_products.setLayoutManager(new GridLayoutManager(this,2));
        rv_products.setHasFixedSize(true);
        rv_products.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.iv_product:

                Product product = (Product) v.getTag();

                startActivity(new Intent(this,ProductDetailActivity.class).putExtra("products",product));
                break;
        }
    }
}
