package com.iqsolutions.mydemo.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iqsolutions.mydemo.R;
import com.iqsolutions.mydemo.pojo.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    private static final String TAG = "ProductAdapter";
    private List<Product> products;
    private View.OnClickListener clickListener;

    public ProductAdapter(List<Product> products, View.OnClickListener clickListener) {
        this.products = products;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product product = products.get(position);
        holder.tv_name.setText(product.getName());
       // holder.tv_variants.setText(product.getVariants().size()+" variants");
        holder.iv_product.setTag(product);
        holder.iv_product.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder{
        ImageView iv_product;
        TextView tv_name,tv_variants;
        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            iv_product = itemView.findViewById(R.id.iv_product);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_variants = itemView.findViewById(R.id.tv_variants);
        }
    }
}
