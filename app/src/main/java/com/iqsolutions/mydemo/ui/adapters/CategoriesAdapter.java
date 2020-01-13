package com.iqsolutions.mydemo.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iqsolutions.mydemo.R;
import com.iqsolutions.mydemo.pojo.Category;
import com.iqsolutions.mydemo.pojo.Product;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryHolder> {
    private static final String TAG = "ProductAdapter";
    private List<Category> categories;
    private View.OnClickListener clickListener;
    private int items=0;

    public CategoriesAdapter(List<Category> categories, View.OnClickListener clickListener) {
        this.categories = categories;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        Category category = categories.get(position);
        holder.tv_name.setText(category.getName());
        holder.iv_product.setTag(category);
        holder.iv_product.setOnClickListener(clickListener);
    }

    public void addCategories(List<Category> categories, int items){
        this.categories=categories;
        this.items=items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (items>0)
            return items;
        return categories.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder{
        ImageView iv_product;
        TextView tv_name;
        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            iv_product = itemView.findViewById(R.id.iv_product);
            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }
}
