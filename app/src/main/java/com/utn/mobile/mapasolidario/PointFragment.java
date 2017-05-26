package com.utn.mobile.mapasolidario;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import roboguice.inject.InjectView;

import static com.utn.mobile.mapasolidario.MapFragment.PUNTO_MESSAGE;


public class PointFragment extends BaseFragment
        implements View.OnClickListener, PointFragmentView {
    private OnFragmentInteractionListener mListener;

//    @InjectView(R.id.pointcontainer) private FrameLayout view;

    @InjectView(R.id.fcancel_boton) private Button bcancelar;
    @InjectView(R.id.fcont_boton) private Button bcontinuar;

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

        if(getArguments()!=null)
        {
            BasePoint claseEnvio = (BasePoint) getArguments().getSerializable(PUNTO_MESSAGE);
            revisarAccion(claseEnvio);
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_point, container, false);
    }

    public void revisarAccion (BasePoint claseEnvio){

        switch (claseEnvio.accion){
            case ALTA:
//                algo();
                break;
            case CONSULTA:
  //              otro();
                break;
            case MODIFICACION:
    //            otro();
                break;
        }

    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //accionBotonVolver();
        bcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        accionBotonContinuar();
    }

    public void  accionBotonContinuar(){
        bcontinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
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
