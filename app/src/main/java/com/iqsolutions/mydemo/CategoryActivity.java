package com.iqsolutions.mydemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.iqsolutions.mydemo.database.DatabaseController;
import com.iqsolutions.mydemo.pojo.Category;
import com.iqsolutions.mydemo.pojo.Product;
import com.iqsolutions.mydemo.ui.adapters.CategoriesAdapter;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "CategoryActivity";
    private RecyclerView rv_categories;
    private ProgressBar pb_progress;
    private CategoriesAdapter categoriesAdapter;
    private List<Category> categories;
    private ArrayList<Product> products;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        setTitle("Categories");
        categories = new ArrayList<>();
        products = new ArrayList<>();
        context = this;

        categories = (List<Category>) getIntent().getSerializableExtra("category");

        rv_categories = findViewById(R.id.rv_categories);
        pb_progress = findViewById(R.id.pb_progress);

        categoriesAdapter = new CategoriesAdapter(categories,this);
        rv_categories.setLayoutManager(new GridLayoutManager(this,2));
        rv_categories.setHasFixedSize(true);
        rv_categories.setAdapter(categoriesAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_product:

                Category category = (Category) v.getTag();
                    products.clear();
                if(DatabaseController.getInstance(context).isTableEmpty("products")){
                    products = (ArrayList<Product>) category.getProducts();
                }else{
                    products = DatabaseController.getInstance(context).getProducts(String.valueOf(category.getId()));
                }
                if (products.size() > 0) {
                    startActivity(new Intent(this,ProductActivity.class).putExtra("products",products));
                }else{
                    Toast.makeText(context, "Sorry products are not available", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
