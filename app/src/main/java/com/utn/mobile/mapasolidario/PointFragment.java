package com.utn.mobile.mapasolidario;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import roboguice.inject.InjectView;

import static com.utn.mobile.mapasolidario.MapFragment.PUNTO_MESSAGE;


public class PointFragment extends BaseFragment
        implements View.OnClickListener, PointFragmentView, AdapterView.OnItemSelectedListener {
    private OnFragmentInteractionListener mListener;

//    @InjectView(R.id.pointcontainer) private FrameLayout view;

    @InjectView(R.id.fcancel_boton) private Button bcancelar;
    @InjectView(R.id.fcont_boton) private Button bcontinuar;
    @InjectView(R.id.tipos_spinner) private Spinner spinner;

    BasePoint claseEnvio = new BasePoint();
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
            claseEnvio = (BasePoint) getArguments().getSerializable(PUNTO_MESSAGE);
            revisarAccion();
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_point, container, false);
    }

    public void revisarAccion (){

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

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        claseEnvio.setTipo(parent.getItemAtPosition(pos).toString());
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


    @Override
    public void onClick(View v) {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.tipos_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

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
