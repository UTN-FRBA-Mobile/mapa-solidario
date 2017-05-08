package com.utn.mobile.mapasolidario;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by svillarreal on 06/05/17.
 */

public class NewsFragmentAdapter extends RecyclerView.Adapter<NewsFragmentAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<NewsEvent> news = new ArrayList<>();

    public NewsFragmentAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        news.add(new NewsEvent(null, "Nuevo punto en Banfield", "En la Escuela Media N°21 se están recibiendo alimentos", false, null));
        news.add(new NewsEvent(null, "Campaña en Boedo", "En la Iglesia de Av. La Plata se creó una heladera solidaria", true, "21/07/2017"));
        news.add(new NewsEvent(null, "Nuevo punto en La Matanza", "En el Centro San Agustín se está recibiendo ropa de todo tipo", false, null));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.fragment_news_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(news.get(position));
    }

    @Override
    public int getItemCount() {
        return news != null ? news.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView newsUserImg;
        private TextView newsTitle;
        private TextView newsDescription;
        private ImageView newsIsImportantImg;

        ViewHolder(View itemView) {
            super(itemView);
            newsUserImg = (ImageView) itemView.findViewById(R.id.img_newsUser);
            newsTitle = (TextView) itemView.findViewById(R.id.txt_newsTitle);
            newsDescription = (TextView) itemView.findViewById(R.id.txt_newsDescription);
            newsIsImportantImg = (ImageView) itemView.findViewById(R.id.img_newsIsImportant);
        }

        void bind(NewsEvent message) {
            /*User user = findUser(message.getUser());
            if (user != null) {
                ImageLoader.instance.loadImage(user.getProfile().getImage_24(), newsUserImg);
            }*/
            newsTitle.setText(message.getNewsTitle());
            newsDescription.setText(message.getNewsDescription());
            if (message.isNewsIsImportant()) {
                newsIsImportantImg.setVisibility(View.VISIBLE);
            } else {
                newsIsImportantImg.setVisibility(View.INVISIBLE);
            }
        }
    }
}
