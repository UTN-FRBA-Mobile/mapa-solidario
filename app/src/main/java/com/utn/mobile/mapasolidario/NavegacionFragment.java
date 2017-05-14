package com.utn.mobile.mapasolidario;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NavegacionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NavegacionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavegacionFragment extends Fragment
        implements View.OnClickListener{

    private Button bretro;
    private Button bcontinuar;
    private Button bflot;
    private int activeP;

    private OnFragmentInteractionListener mListener;

    public NavegacionFragment() {
        // Required empty public constructor
    }

    public static NavegacionFragment newInstance(String param1, String param2) {
        NavegacionFragment fragment = new NavegacionFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_navegacion, container, false);

        activeP=0;

        bcontinuar = (Button)view.findViewById(R.id.favanza_boton);
        bretro = (Button)view.findViewById(R.id.fretro_boton);
        bflot = (Button)view.findViewById(R.id.bpunto);

        //cuando empieza solo puede avanzar
//        bcontinuar.setVisibility(View.VISIBLE);
//        bretro.setVisibility(View.INVISIBLE);
//        bflot.setVisibility(View.INVISIBLE);
        showCorrectPanel();

        accionBotonAvanzar();
        accionBotonRetroceder();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    public void  accionBotonAvanzar(){
        bcontinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activeP++;
                showCorrectPanel();
            }
        });
    }

    public void  accionBotonRetroceder(){
        bretro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activeP--;
                showCorrectPanel();
            }
        });
    }

    public void  accionBotonConfirmar(){
        bflot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activeP=0;

                //confirmar los datos cargados en la base de datos

            }
        });
    }


    void showCorrectPanel(){

        switch (activeP){
            case 0:
                bretro.setVisibility(View.INVISIBLE);
                bcontinuar.setVisibility(View.VISIBLE);
                bflot.setVisibility(View.INVISIBLE);

                break;
            case 1:
                bretro.setVisibility(View.VISIBLE);
                bcontinuar.setVisibility(View.VISIBLE);
                bflot.setVisibility(View.INVISIBLE);
                break;
            case 2:
                bretro.setVisibility(View.VISIBLE);
                bcontinuar.setVisibility(View.INVISIBLE);
                bflot.setVisibility(View.VISIBLE);
                break;
            default:
                mostrarError();
                break;
        }

    }

    void mostrarError (){

    }

    @Override
    public void onClick(View v) {
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
