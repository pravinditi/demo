package com.iqsolutions.mydemo.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iqsolutions.mydemo.R;
import com.iqsolutions.mydemo.pojo.Product_;


import java.util.List;

public class RankingsProductsAdapter extends RecyclerView.Adapter<RankingsProductsAdapter.RankingPHolder> {
    private static final String TAG = "VariantAdapter";
    private List<Product_> product_;
    private View.OnClickListener clickListener;
    int count;

    public RankingsProductsAdapter(List<Product_> productlist, View.OnClickListener clickListener, int count) {
        this.product_ = productlist;
        this.clickListener = clickListener;
        this.count = count;
    }

    @NonNull
    @Override
    public RankingPHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RankingPHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rankings_products,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RankingPHolder holder, int position) {
        Product_ product = product_.get(position);

            holder.tv_product_id.setText(String.valueOf(product.getId()));

            holder.tv_product_name_.setText("Product");

            if(count==1)
                holder.tv_count.setText(String.valueOf(product.getViewCount()));

            if(count==2)
                holder.tv_count.setText(String.valueOf(product.getOrderCount()));

            if(count==3)
                holder.tv_count.setText(String.valueOf(product.getShares()));
    }

    @Override
    public int getItemCount() {
        return product_.size();
    }

    class RankingPHolder extends RecyclerView.ViewHolder{

        TextView tv_product_id,tv_product_name_,tv_count;
        public RankingPHolder(@NonNull View itemView) {
            super(itemView);

            tv_product_id = itemView.findViewById(R.id.tv_product_id);
            tv_product_name_ = itemView.findViewById(R.id.tv_product_name_);
            tv_count = itemView.findViewById(R.id.tv_count);


        }
    }
}
