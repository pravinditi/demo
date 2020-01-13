package com.iqsolutions.mydemo;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iqsolutions.mydemo.database.DatabaseController;
import com.iqsolutions.mydemo.pojo.Product_;
import com.iqsolutions.mydemo.pojo.Ranking;
import com.iqsolutions.mydemo.ui.adapters.RankingsAdapter;
import com.iqsolutions.mydemo.ui.adapters.RankingsProductsAdapter;

import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rv_ranking,rv_ranking_products;
    private RankingsAdapter adapter;
    private RankingsProductsAdapter rp_adapter;
    private List<Ranking>rankinglist;
    private List<Product_>product_list;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        setTitle("Ranking");
        rv_ranking = findViewById(R.id.rv_ranking);
        rv_ranking_products =findViewById(R.id.rv_ranking_products);
        product_list = new ArrayList<>();


        context = this;

       // rankinglist = (List<Ranking>) getIntent().getSerializableExtra("ranking");

        rankinglist = DatabaseController.getInstance(context).getRanking();


        adapter = new RankingsAdapter(rankinglist,this);
        rv_ranking.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rv_ranking.setHasFixedSize(true);
        rv_ranking.setAdapter(adapter);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_ranking_text:

                Ranking ranking = (Ranking) view.getTag();
                if(ranking.getRanking().equalsIgnoreCase("Most Viewed Products")){
                    product_list.clear();
                    product_list =  DatabaseController.getInstance(context).getRankingProducts(1);
                    rp_adapter = new RankingsProductsAdapter(product_list,this,1);
                    rv_ranking_products.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
                    rv_ranking_products.setHasFixedSize(true);
                    rv_ranking_products.setAdapter(rp_adapter);
                }
                if(ranking.getRanking().equalsIgnoreCase("Most OrdeRed Products")){
                    product_list.clear();
                    product_list = DatabaseController.getInstance(context).getRankingProducts(2);
                    rp_adapter = new RankingsProductsAdapter(product_list,this,2);
                    rv_ranking_products.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
                    rv_ranking_products.setHasFixedSize(true);
                    rv_ranking_products.setAdapter(rp_adapter);
                }
                if(ranking.getRanking().equalsIgnoreCase("Most ShaRed Products")){
                    product_list.clear();
                    product_list = DatabaseController.getInstance(context).getRankingProducts(3);
                    rp_adapter = new RankingsProductsAdapter(product_list,this,3);
                    rv_ranking_products.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
                    rv_ranking_products.setHasFixedSize(true);
                    rv_ranking_products.setAdapter(rp_adapter);
                }
                break;
        }

        }
}
