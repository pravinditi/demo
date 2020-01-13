package com.iqsolutions.mydemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.iqsolutions.mydemo.Utils.CommonMethods;
import com.iqsolutions.mydemo.database.DatabaseController;
import com.iqsolutions.mydemo.network.APIClient;
import com.iqsolutions.mydemo.network.ApiMethodInterface;
import com.iqsolutions.mydemo.pojo.Category;
import com.iqsolutions.mydemo.pojo.Product;
import com.iqsolutions.mydemo.pojo.ProductResponse;
import com.iqsolutions.mydemo.pojo.Ranking;
import com.iqsolutions.mydemo.pojo.Tax;
import com.iqsolutions.mydemo.pojo.Variant;
import com.iqsolutions.mydemo.ui.adapters.CategoriesAdapter;
import com.iqsolutions.mydemo.ui.adapters.RankingsAdapter;
import com.iqsolutions.mydemo.ui.adapters.SliderAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "HomeActivity";
    private ViewPager viewPager;
    private RecyclerView rv_categories;
    private ProgressBar pb_progress;
    private Button btn_view,btn_view_ranking;
    private SliderAdapter adapter;
    private CategoriesAdapter categoriesAdapter;
    private ArrayList<Category> categories;
    private ArrayList<Product> productArrayList;
    private ArrayList<Variant> variantArrayList;
    private ArrayList<Tax> taxArrayList;
    private List<Ranking> rankings;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Home");
        categories = new ArrayList<>();
        rankings = new ArrayList<>();
        productArrayList = new ArrayList<>();
        variantArrayList = new ArrayList<>();
        taxArrayList = new ArrayList<>();
        context = this;


        viewPager = findViewById(R.id.view_pager);
        pb_progress = findViewById(R.id.pb_progress);
        rv_categories = findViewById(R.id.rv_categories);
        btn_view = findViewById(R.id.btn_view);
        btn_view_ranking = findViewById(R.id.btn_view_ranking);

        adapter = new SliderAdapter(this);
        viewPager.setAdapter(adapter);

        categoriesAdapter = new CategoriesAdapter(categories,this);
        rv_categories.setLayoutManager(new GridLayoutManager(this,2));
        rv_categories.setHasFixedSize(true);
        rv_categories.setAdapter(categoriesAdapter);

        btn_view.setOnClickListener(this);
        btn_view_ranking.setOnClickListener(this);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(),2000,4000);

        if (CommonMethods.isConnected(context)) {
            if (DatabaseController.getInstance(context).isTableEmpty("r_most_shared_products")) {
                getData();
            } else {
                Toast.makeText(context, "please", Toast.LENGTH_SHORT).show();
                categories.clear();
                categories = DatabaseController.getInstance(context).getCategory();
                if (categories!=null && categories.size()>0)
                    categoriesAdapter.addCategories(categories,8);
            }

        } else {
            Toast.makeText(context, "please check internet connection", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_view:
                startActivity(new Intent(this,CategoryActivity.class).putExtra("category",categories));

                break;
            case R.id.btn_view_ranking:
                startActivity(new Intent(this,RankingActivity.class));

                break;
            case R.id.iv_product:


                Category category = (Category) v.getTag();
                        productArrayList.clear();
                        if(DatabaseController.getInstance(context).isTableEmpty("products")){

                            productArrayList = (ArrayList<Product>) category.getProducts();
                        }else{

                            productArrayList = DatabaseController.getInstance(context).getProducts(String.valueOf(category.getId()));
                        }
                        if(productArrayList.size()>0) {
                            startActivity(new Intent(this, ProductActivity.class).putExtra("products", productArrayList));
                        }else{
                            Toast.makeText(context,"Sorry no product found",Toast.LENGTH_SHORT).show();
                        }
                break;
        }
    }

    private class SliderTimer extends TimerTask{
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem()==0)
                        viewPager.setCurrentItem(1);
                    else if (viewPager.getCurrentItem()==1)
                        viewPager.setCurrentItem(2);
                    else
                        viewPager.setCurrentItem(0);
                }
            });
        }
    }


    private void getData(){
        pb_progress.setVisibility(View.VISIBLE);
        ApiMethodInterface objRetrofitInterface = APIClient.getClient().create(ApiMethodInterface.class);
        Call<ProductResponse> call = objRetrofitInterface.getAllData();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(@NotNull Call<ProductResponse> call, @NotNull Response<ProductResponse> response) {
                pb_progress.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    categories.clear();
                    categories = (ArrayList<Category>) response.body().getCategories();

                    for (int i=0; i<categories.size();i++){

                        ContentValues contentValues = new ContentValues();
                        contentValues.put("cat_id",categories.get(i).getId());
                        contentValues.put("cat_name",categories.get(i).getName());

                        StringBuilder stringBuilder = new StringBuilder();

                        if(categories.get(i).getChildCategories().size() > 0) {
                            for (int j = 0; j < categories.get(i).getChildCategories().size() ; j++) {
                                stringBuilder.append(categories.get(i).getChildCategories().get(j).toString());
                                stringBuilder.append("-");
                            }
                        }else{
                            stringBuilder.append("0");
                        }
                        contentValues.put("child_cat",stringBuilder.toString());
                        DatabaseController.getInstance(context).insertValuesInTable(contentValues,"category");

                        for(int k = 0 ;k<categories.get(i).getProducts().size();k++) {

                            ContentValues contentValues_products = new ContentValues();
                            contentValues_products.put("prod_id", categories.get(i).getProducts().get(k).getId());
                            contentValues_products.put("cat_id", categories.get(i).getId());
                            contentValues_products.put("prod_name", categories.get(i).getProducts().get(k).getName());
                            contentValues_products.put("date_added", categories.get(i).getProducts().get(k).getDateAdded());
                            contentValues_products.put("tax_name", categories.get(i).getProducts().get(k).getTax().getName());
                            contentValues_products.put("tax_value", categories.get(i).getProducts().get(k).getTax().getValue());

                            DatabaseController.getInstance(context).insertValuesInTable(contentValues_products, "products");

                            for(int l = 0 ;l<categories.get(i).getProducts().get(k).getVariants().size();l++) {


                                ContentValues contentValues_variants = new ContentValues();
                                contentValues_variants.put("v_id", categories.get(i).getProducts().get(k).getVariants().get(l).getId());
                                contentValues_variants.put("prod_id", categories.get(i).getProducts().get(k).getId());
                                contentValues_variants.put("color", categories.get(i).getProducts().get(k).getVariants().get(l).getColor());
                                contentValues_variants.put("size", (categories.get(i).getProducts().get(k).getVariants().get(l).getSize()!=null) ? categories.get(i).getProducts().get(k).getVariants().get(l).getSize().toString() : "0" );
                                contentValues_variants.put("price", categories.get(i).getProducts().get(k).getVariants().get(l).getPrice());
                                DatabaseController.getInstance(context).insertValuesInTable(contentValues_variants, "variants");

                            }


                    }

                }

                    if (categories!=null && categories.size()>0)
                        categoriesAdapter.addCategories(categories,8);

                    rankings.clear();
                    rankings = response.body().getRankings();
                    Log.d(TAG, "onResponse: "+categories.size()+"  "+rankings.size());

                    for(int i = 0 ;i<rankings.size();i++) {

                       // JSONObject jsonObject_rank = jsonArray_rankings.getJSONObject(i);

                        ContentValues contentValues = new ContentValues();
                        // contentValues.put("id", jsonObject_rank.optString("id"));
                        contentValues.put("ranking", rankings.get(i).getRanking());

                        DatabaseController.getInstance(context).insertValuesInTable(contentValues, "rankings");


                       // JSONArray jsonArray_products_rank =  jsonObject_rank.getJSONArray("products");


                        for (int k = 0; k < rankings.get(i).getProducts().size(); k++) {
                           // JSONObject jsonObject_products_rank = jsonArray_products_rank.getJSONObject(k);

                            if(i==0) {
                                ContentValues contentValues_view_count_ = new ContentValues();
                                contentValues_view_count_.put("prod_id", rankings.get(i).getProducts().get(k).getId());
                                contentValues_view_count_.put("mvp_id", "1");
                                contentValues_view_count_.put("view_count", rankings.get(i).getProducts().get(k).getViewCount());

                                DatabaseController.getInstance(context).insertValuesInTable(contentValues_view_count_, "r_most_viewd_products");
                            }
                            if(i==1){

                                ContentValues contentValues_order_count_ = new ContentValues();
                                contentValues_order_count_.put("prod_id", rankings.get(i).getProducts().get(k).getId());
                                contentValues_order_count_.put("mop_id", "2");
                                contentValues_order_count_.put("order_count", rankings.get(i).getProducts().get(k).getOrderCount());

                                DatabaseController.getInstance(context).insertValuesInTable(contentValues_order_count_, "r_most_ordered_products");

                            }
                            if(i==2){

                                ContentValues contentValues_share = new ContentValues();
                                contentValues_share.put("prod_id", rankings.get(i).getProducts().get(k).getId());
                                contentValues_share.put("msp_id", "3");
                                contentValues_share.put("shares", rankings.get(i).getProducts().get(k).getShares());

                                DatabaseController.getInstance(context).insertValuesInTable(contentValues_share, "r_most_shared_products");

                            }
                        }
                    }


                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                pb_progress.setVisibility(View.GONE);
            }
        });
    }
}
