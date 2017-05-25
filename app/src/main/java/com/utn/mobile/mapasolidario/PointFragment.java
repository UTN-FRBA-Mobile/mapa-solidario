package com.utn.mobile.mapasolidario;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.inject.Inject;
import com.utn.mobile.mapasolidario.util.FetchNewsErrors;
import com.utn.mobile.mapasolidario.util.PointErrors;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

import static com.utn.mobile.mapasolidario.util.PointErrors.PROBLEMA_SERVIDOR;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PointFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PointFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PointFragment extends BaseFragment
        implements View.OnClickListener{//, PointFragmentView {
    private OnFragmentInteractionListener mListener;

    private Button bcancelar;
    private Button bcontinuar;
//    @InjectView(R.id.pointcontainer) private View view;

//    @InjectView(R.id.fcancel_boton) Button bcancelar;
//    @InjectView(R.id.fcont_boton) Button bcontinuar;

//    @Inject   private PointFragmentPresenter presenter;


    public PointFragment() {
        // Required empty public constructor
    }

    public static PointFragment newInstance(String param1, String param2) {
        PointFragment fragment = new PointFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        presenter.onCreate(this);
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_point, container, false);

        bcancelar = (Button) view.findViewById(R.id.fcancel_boton);
        bcontinuar = (Button) view.findViewById(R.id.fcont_boton);

        //      presenter = new PointFragmentPresenter();

        accionBotonVolver(view);
        accionBotonContinuar(view);
        return view;
    }


    public void  accionBotonVolver(View view){
            bcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
    }

    public void  accionBotonContinuar(View view){
        bcontinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //@Override
    public void showMessageError(PointErrors error) {
        String msjError = "";
        String title = getString(R.string.POPUP_TITLE_SERVIDOR);
        switch (error) {
            case PROBLEMA_SERVIDOR:
                msjError = getString(R.string.sin_comunicacion);
                break;
            case PROBLEMA_GUARDAR:
                title = "Error al guardar";//getString(R.string.POPUP_TITLE_SIN_NOVEDADES);
                msjError = "Ocurrió un error al guardar, revise su conexión a internet";//getString(R.string.POPUP_MENSAJE_SIN_NOVEDADES);
                break;
            case PROBLEMA_BUSQUEDA:
                title = "Error al consultar";//getString(R.string.POPUP_TITLE_SIN_NOVEDADES);
                msjError = "Ocurrió un error al obtener los datos, revise su conexión a internet";//getString(R.string.POPUP_MENSAJE_SIN_NOVEDADES);
                break;
            case TIME_OUT:
                msjError = getString(R.string.sin_comunicacion);
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void goBack ();
    }
}
