package com.iqsolutions.mydemo.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iqsolutions.mydemo.R;
import com.iqsolutions.mydemo.pojo.Ranking;
import com.iqsolutions.mydemo.pojo.Variant;

import java.util.List;

public class RankingsAdapter extends RecyclerView.Adapter<RankingsAdapter.RankingHolder> {
    private static final String TAG = "VariantAdapter";
    private List<Ranking> rankings;
    private View.OnClickListener clickListener;


    public RankingsAdapter(List<Ranking> rankings, View.OnClickListener clickListener) {
        this.rankings = rankings;
        this.clickListener = clickListener;

    }

    @NonNull
    @Override
    public RankingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RankingHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rankings,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RankingHolder holder, int position) {
        Ranking ranking = rankings.get(position);
            holder.tv_ranking.setTag(ranking);
            holder.tv_ranking.setText(ranking.getRanking());
            holder.tv_ranking.setOnClickListener(clickListener);


    }

    @Override
    public int getItemCount() {
        return rankings.size();
    }

    class RankingHolder extends RecyclerView.ViewHolder{

        TextView tv_ranking;
        public RankingHolder(@NonNull View itemView) {
            super(itemView);

            tv_ranking = itemView.findViewById(R.id.tv_ranking_text);


        }
    }
}
