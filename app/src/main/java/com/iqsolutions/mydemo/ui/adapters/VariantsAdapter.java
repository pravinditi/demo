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
import com.iqsolutions.mydemo.pojo.Variant;

import java.util.List;

public class VariantsAdapter extends RecyclerView.Adapter<VariantsAdapter.VariantHolder> {
    private static final String TAG = "VariantAdapter";
    private List<Variant> variant;
    private View.OnClickListener clickListener;
    String type_of_v;

    public VariantsAdapter(List<Variant> variantt, View.OnClickListener clickListener,String type_of_variant) {
        this.variant = variantt;
        this.clickListener = clickListener;
        this.type_of_v = type_of_variant;
    }

    @NonNull
    @Override
    public VariantHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VariantHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_details,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VariantHolder holder, int position) {
        Variant variants = variant.get(position);
        if(type_of_v.equalsIgnoreCase("c")) {
            holder.tv_color.setVisibility(View.VISIBLE);
            holder.tv_color.setText(variants.getColor());
        }
        if(type_of_v.equalsIgnoreCase("p")) {

            holder.tv_price.setVisibility(View.VISIBLE);
            holder.tv_price.setText(String.valueOf(variants.getPrice()));
        }
        if(type_of_v.equalsIgnoreCase("s")) {

            holder.tv_size.setVisibility(View.VISIBLE);
            holder.tv_size.setText(variants.getSize().toString());
        }




    }

    @Override
    public int getItemCount() {
        return variant.size();
    }

    class VariantHolder extends RecyclerView.ViewHolder{

        TextView tv_color,tv_size,tv_price;
        public VariantHolder(@NonNull View itemView) {
            super(itemView);

            tv_color = itemView.findViewById(R.id.tv_color);
            tv_size = itemView.findViewById(R.id.tv_size);
            tv_price = itemView.findViewById(R.id.tv_price);

            tv_price.setVisibility(View.GONE);
            tv_size.setVisibility(View.GONE);
            tv_color.setVisibility(View.GONE);

        }
    }
}
