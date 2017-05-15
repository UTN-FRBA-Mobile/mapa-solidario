package com.utn.mobile.mapasolidario;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.utn.mobile.mapasolidario.dto.NovedadResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by svillarreal on 06/05/17.
 */

public class NewsFragmentAdapter extends RecyclerView.Adapter<NewsFragmentAdapter.ViewHolder> {

    private final NewsFragmentView newsFragmentView;
    private LayoutInflater layoutInflater;
    private List<NovedadResponse> news = new ArrayList<>();

    public NewsFragmentAdapter(Context context, NewsFragmentView newsFragmentView) {
        layoutInflater = LayoutInflater.from(context);
        this.newsFragmentView = newsFragmentView;
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

    public void updateData(List<NovedadResponse> novedades) {
        news.clear();
        news.addAll(novedades);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        news.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView newsUserImg;
        private TextView newsTitle;
        private TextView newsDescription;
        private TextView newsExpireDate;
        private View relativeLayout;

        ViewHolder(View itemView) {
            super(itemView);
            newsUserImg = (ImageView) itemView.findViewById(R.id.img_newsUser);
            newsTitle = (TextView) itemView.findViewById(R.id.txt_newsTitle);
            newsDescription = (TextView) itemView.findViewById(R.id.txt_newsDescription);
            newsExpireDate = (TextView) itemView.findViewById(R.id.txt_newsExpireDate);
            relativeLayout = (View) itemView.findViewById(R.id.lay_newsIsImportant);

        }

        void bind(NovedadResponse novedad) {
//            User user = findUser(novedad.getUser());
//            if (user != null) {
//                ImageLoader.instance.loadImage(user.getProfile().getImage_24(), newsUserImg);
//            }
            newsTitle.setText(novedad.getTitle());
            newsDescription.setText(novedad.getDescription());
            if (novedad.getExpires() != null) {
                newsFragmentView.changeItemColor(itemView);
                newsExpireDate.setVisibility(View.VISIBLE);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                newsExpireDate.setText("Fecha de Finalizazión: " + sdf.format(novedad.getExpires()));
            } else {
                newsExpireDate.setVisibility(View.INVISIBLE);
            }
        }
    }
}
