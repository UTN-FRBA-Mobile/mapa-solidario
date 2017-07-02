package com.utn.mobile.mapasolidario;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.utn.mobile.mapasolidario.PointListFragment.OnListFragmentInteractionListener;
import com.utn.mobile.mapasolidario.dto.NovedadResponse;
import com.utn.mobile.mapasolidario.dto.PuntoResponse;
import com.utn.mobile.mapasolidario.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class PointItemRecyclerViewAdapter extends RecyclerView.Adapter<PointItemRecyclerViewAdapter.ViewHolder> {

    private final PointItemListFragmentView pointItemListFragmentView;
    private final List<PuntoResponse> pointsItems = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public PointItemRecyclerViewAdapter(Context context, PointItemListFragmentView pointItemListFragmentView ) {
        layoutInflater = LayoutInflater.from(context);
        this.pointItemListFragmentView = pointItemListFragmentView ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_pointitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(pointsItems.get(position));
    }

    @Override
    public int getItemCount() {
        return pointsItems.size();
    }

    public void updateData(List<PuntoResponse> pointsItems) {
        pointsItems.clear();
        pointsItems.addAll(pointsItems);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView id;
        private TextView fecha;
        private TextView user;
        private TextView titulo;
        private TextView descripcion;
        private PuntoResponse mItem;

        public ViewHolder(View view) {
            super(view);
            fecha = (TextView) view.findViewById(R.id.pointitem_fecha);
            id = (TextView) view.findViewById(R.id.pointitem_id);
            user = (TextView) view.findViewById(R.id.pointitem_user);
            titulo = (TextView) view.findViewById(R.id.pointitem_titulo);
            descripcion = (TextView) view.findViewById(R.id.pointitem_descripcion);
        }

        void bind(PuntoResponse puntoResponse) {
            id.setText(puntoResponse.get_id());
            titulo.setText(puntoResponse.getTitulo());
            descripcion.setText(puntoResponse.getDescripcion());
            fecha.setText("una fecha");
            user.setText("un usuario");
        }

        @Override
        public String toString() {
            return super.toString() + " '" + fecha.getText() + "'";
        }

        @Override
        public void onClick(View v) {

        }

    }
}
