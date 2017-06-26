package com.utn.mobile.mapasolidario.user;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.utn.mobile.mapasolidario.R;
import com.utn.mobile.mapasolidario.dto.NovedadResponse;
import com.utn.mobile.mapasolidario.dto.PuntoResponse;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by svillarreal on 06/05/17.
 */

public class UserPointsAdapter extends RecyclerView.Adapter<UserPointsAdapter.ViewHolder> {

    private final UserView userView;
    private LayoutInflater layoutInflater;
    private List<PuntoResponse> puntos = new ArrayList<>();

    public UserPointsAdapter(Context context, UserView userView) {
        layoutInflater = LayoutInflater.from(context);
        this.userView = userView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.fragment_user_point_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(puntos.get(position));
    }

    @Override
    public int getItemCount() {
        return puntos != null ? puntos.size() : 0;
    }

    public void updateData(List<PuntoResponse> points) {
        puntos.clear();
        puntos.addAll(points);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        puntos.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView pointTitle;
        private TextView pointDescription;

        ViewHolder(View itemView) {
            super(itemView);
            pointTitle = (TextView) itemView.findViewById(R.id.txt_user_point_title);
            pointDescription = (TextView) itemView.findViewById(R.id.txt_user_point_description);
        }

        void bind(PuntoResponse punto) {
            pointTitle.setText(punto.getTitulo());
            pointDescription.setText(punto.getDescripcion());
        }
    }
}
