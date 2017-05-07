package com.utn.mobile.mapasolidario;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by svillarreal on 06/05/17.
 */

public class NewsFragmentAdapter extends RecyclerView.Adapter<NewsFragmentAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private String texto;

    public NewsFragmentAdapter(Context context, String texto) {
        layoutInflater = LayoutInflater.from(context);
        this.texto = texto;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.fragment_news_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(texto + " " + String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.txt_newDescription);
        }
    }
}
