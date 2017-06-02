package com.utn.mobile.mapasolidario;

import android.app.DatePickerDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import roboguice.inject.InjectView;

import static com.utn.mobile.mapasolidario.MapFragment.PUNTO_MESSAGE;


public class PointFragment extends BaseFragment
        implements View.OnClickListener, PointFragmentView,
        AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {
    private OnFragmentInteractionListener mListener;

//    @InjectView(R.id.pointcontainer) private FrameLayout view;

    @InjectView(R.id.fcancel_boton) private Button bcancelar;
    @InjectView(R.id.fcont_boton) private Button bcontinuar;
    @InjectView(R.id.tipos_spinner) private Spinner spinner;
    @InjectView(R.id.fvencimiento) private EditText fechaVencimiento;

    @InjectView(R.id.pmap)    MapView mMapView;
    GoogleMap googleMap;

    BasePoint claseEnvio = new BasePoint();
//    @Inject   private PointFragmentPresenter presenter;

    public PointFragment() {
        // Required empty public constructor
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
        return inflater.inflate(R.layout.fragment_point, container, false);
    }

    void cargarMapa(){

        try {
            MapsInitializer.initialize(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;


                googleMap.addMarker(new MarkerOptions().position(claseEnvio.ubicacion));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(claseEnvio.ubicacion,15));

                googleMap.getUiSettings().setZoomControlsEnabled(false);
                googleMap.getUiSettings().setCompassEnabled(false);
                googleMap.getUiSettings().setZoomGesturesEnabled(false);
                googleMap.getUiSettings().setScrollGesturesEnabled(false);
            }
        });
          mMapView.setClickable(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
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

        if(getArguments()!=null)        {
            claseEnvio = (BasePoint) getArguments().getSerializable(PUNTO_MESSAGE);
            revisarAccion();
        }
        if (mMapView != null){
            mMapView.onCreate(savedInstanceState);
            mMapView.onResume();
            cargarMapa();
        }
//        datepickerAction();
        accionBotonContinuar();
    }

/*    public void datepickerAction(){

        fechaVencimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

                DatePickerDialog dialog = new DatePickerDialog(getContext(), this,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
    }

*/
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
  /*      _birthYear = year;
        _month = monthOfYear;
        _day = dayOfMonth;
        updateDisplay();*/
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

    public interface OnFragmentInteractionListener {
        void goBack ();
    }
}
