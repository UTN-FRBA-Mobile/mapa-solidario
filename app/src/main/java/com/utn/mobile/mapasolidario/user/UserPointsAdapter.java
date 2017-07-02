package com.utn.mobile.mapasolidario.user;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.utn.mobile.mapasolidario.BasePoint;
import com.utn.mobile.mapasolidario.PointFragment;
import com.utn.mobile.mapasolidario.R;
import com.utn.mobile.mapasolidario.dto.NovedadResponse;
import com.utn.mobile.mapasolidario.dto.PuntoResponse;
import com.utn.mobile.mapasolidario.util.PointActions;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.utn.mobile.mapasolidario.util.Utils.consultarPunto;

import android.support.v4.app.FragmentActivity;

/**
 * Created by svillarreal on 06/05/17.
 */

public class UserPointsAdapter extends RecyclerView.Adapter<UserPointsAdapter.ViewHolder> {

    private final UserView userView;
    private LayoutInflater layoutInflater;
    private List<PuntoResponse> puntos = new ArrayList<>();
    private Context context;

    public UserPointsAdapter(Context context, UserView userView) {
        layoutInflater = LayoutInflater.from(context);
        this.userView = userView;
        this.context = context;
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
        private View pointView;
        private PuntoResponse point;

        ViewHolder(View itemView) {
            super(itemView);
            pointTitle = (TextView) itemView.findViewById(R.id.txt_user_point_title);
            pointDescription = (TextView) itemView.findViewById(R.id.txt_user_point_description);
            pointView = itemView;
        }

        void bind(final PuntoResponse punto) {
            point = punto;
            pointTitle.setText(punto.getTitulo());
            pointDescription.setText(punto.getDescripcion());

            //TODO: Bind click on view for point details

            pointView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    BasePoint point = new BasePoint();
                    point.setId(punto.get_id());
                    point.setDescripcion(punto.getDescripcion());
                    point.setTitulo(punto.getTitulo());
                    point.setTipo(punto.getTipo());
                    point.setLatitud(punto.getLatitud());
                    point.setLongitud(punto.getLongitud());
                    point.setContador(punto.getContador());
                    point.setAccion(PointActions.MODIFICACION);

                    FragmentManager fragmentManager = ((FragmentActivity)v.getContext()).getSupportFragmentManager();

                    Fragment fragment = new PointFragment();
                    Bundle args = new Bundle();
                    String PUNTO_MESSAGE = "mensaje.al.fragment";
                    args.putSerializable(PUNTO_MESSAGE,point);
                    fragment.setArguments(args);

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.content, fragment, "Fragment");
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });
        }
    }
}
