package com.utn.mobile.mapasolidario;

import android.app.DatePickerDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.utn.mobile.mapasolidario.util.PointActions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import roboguice.inject.InjectView;


public class PointFragment extends BaseFragment
        implements View.OnClickListener, PointFragmentView,
        AdapterView.OnItemSelectedListener {
    private OnFragmentInteractionListener mListener;

//    @InjectView(R.id.pointcontainer) private FrameLayout view;
//    @Inject   private PointFragmentPresenter presenter;

    @InjectView(R.id.tubicacion) private TextView ubicacion;
    @InjectView(R.id.fcancel_boton) private Button bcancelar;
    @InjectView(R.id.fcont_boton) private Button bcontinuar;
    @InjectView(R.id.tipos_spinner) private Spinner spinner;
    @InjectView(R.id.fvencimiento) private EditText fechaVencimiento;
    @InjectView(R.id.pmap)    MapView mMapView;

    static String PUNTO_MESSAGE = "mensaje.al.fragment";
    GoogleMap googleMap;
    BasePoint claseEnvio = new BasePoint();

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

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Levanto el objeto que me mandan
        if(getArguments()!=null)        {
            claseEnvio = (BasePoint) getArguments().getSerializable(PUNTO_MESSAGE);
//            claseEnvio.setAccion(PointActions.MODIFICACION);//valor para probar... sacarlo desp TODO
        }

        //Muestro el mapa
        if (mMapView != null){
            mMapView.onCreate(savedInstanceState);
            mMapView.onResume();
            cargarMapa();
        }

        View scroll = view.findViewById(R.id.scrollVista);
        scroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ocultarTeclado();
            }
        });

        configurarLayout();

        revisarAccion(view);

        accionBotonContinuar();
        //accion botón cancelar
        bcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bcontinuar.setVisibility(View.VISIBLE);
                ocultarTeclado();
                getFragmentManager().popBackStack();
            }
        });

    }


    public void revisarAccion (View view){

        EditText editTitulo= (EditText) view.findViewById(R.id.editTitulo);
        EditText editDescripcion= (EditText) view.findViewById(R.id.editText);

        if (claseEnvio.accion == PointActions.CONSULTA || claseEnvio.accion == PointActions.MODIFICACION ){
            //TODO: hacer un GET a la base
            claseEnvio.setUbicacion(new LatLng(-34.6183, -58.3732));
            claseEnvio.setTipo("Heladera Solidaria");
            claseEnvio.setDescripcion("Robbins - 06 Decision:\n" +
                    "\n" +
                    "Tomar decisiones = hacer elecciones, gerentes de todos los niveles y todas las areas\n" +
                    "\n" +
                    "Etapas:\n" +
                    "1 Identificaicon de problema\n" +
                    "2: identificacion de criterios de decision\n" +
                    "3: Ponderacion de criterios\n" +
                    "4: Desarrollo de alternativas\n" +
                    "5: Analisis de alternativas\n" +
                    "6: Seleccion de una alternativa\n" +
                    "7: Implementacion de la Alternativa\n" +
                    "8: Evaluacion de la efectividad\n");
            claseEnvio.setTitulo("Decisiones racionales: son elecciones logicas y consistentes para maximizar valor.");
            claseEnvio.setFechaVto(new Date());

            //lleno el layout con los datos devueltos
            editTitulo.setText(claseEnvio.titulo);editDescripcion.setText(claseEnvio.descripcion);


            //Mostrarlo en el text view
            String myFormat = "dd/MM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            fechaVencimiento.setText(sdf.format(claseEnvio.fechaVto.getTime()));

//            fechaVencimiento.setText(claseEnvio.fechaVto.toString());

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    R.array.tipos_array, android.R.layout.simple_spinner_item);
            int spinnerPosition = adapter.getPosition(claseEnvio.tipo);
            spinner.setSelection(spinnerPosition);

        }

        if (claseEnvio.accion == PointActions.CONSULTA){
            //Deshabilitar la edición
            bcontinuar.setVisibility(View.INVISIBLE);
            editTitulo.setEnabled(false);
            editDescripcion.setEnabled(false);
            fechaVencimiento.setEnabled(false);
            spinner.setEnabled(false);
        }

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

    public void configurarLayout(){

        //Mostrar dirección del punto
        ubicacion.setText(getCompleteAddressString(claseEnvio.ubicacion.latitude, claseEnvio.ubicacion.longitude));

        // Lleno el combo de tipo de necesidad
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.tipos_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //Oculto el teclado cuando sale del foco
        ocultarTeclado();

        //Cargo el datePicker de la fecha vencimiento
        llenarDatepicker();

    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {

                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
 //               String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
//                String postalCode = addresses.get(0).getPostalCode();
//                String knownName = addresses.get(0).getFeatureName();

                strAdd = address + ", "+state+ ", "+country;

            } else {
                strAdd =  getResources().getString(R.string.error_geolocalizacion);
            }
        } catch (Exception e) {
            e.printStackTrace();
            strAdd = getResources().getString(R.string.error_geolocalizacion);
        }
        return strAdd;
    }

    public void ocultarTeclado(){
        //Oculto el teclado cuando sale del foco
        View foco = getActivity().getCurrentFocus();
        if (foco != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(foco.getWindowToken(), imm.HIDE_NOT_ALWAYS );
        }

    }

    public void llenarDatepicker(){

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                //Mostrarlo en el text view
                String myFormat = "dd/MM/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                fechaVencimiento.setText(sdf.format(myCalendar.getTime()));

            }
        };

        fechaVencimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void  accionBotonContinuar(){
        bcontinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO: Persistir los datos en la base de datos
                claseEnvio.setFechaModificacion(new Date()); //confirmar si new date devuelve la fecha actual

                if (claseEnvio.id == 0){
                    claseEnvio.setUsuario("Dani Chacur");
                    claseEnvio.setFechaCreacion(new Date());
                    claseEnvio.setId(123); //Guardar el que me devuelve al guardar en la base
                }

                ocultarTeclado();
                getFragmentManager().popBackStack();
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        claseEnvio.setTipo(parent.getItemAtPosition(pos).toString());
        ocultarTeclado();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
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

    public interface OnFragmentInteractionListener {
        void goBack ();
    }
}
