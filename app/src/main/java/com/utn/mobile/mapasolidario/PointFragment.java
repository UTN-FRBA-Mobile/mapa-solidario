package com.utn.mobile.mapasolidario;

import android.app.DatePickerDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.utn.mobile.mapasolidario.util.FetchPuntosErrors;
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

    @Inject    private PointFragmentPresenter presenter;

    @InjectView(R.id.tubicacion) private TextView ubicacion;
    @InjectView(R.id.editText) private TextView descripcion;
    @InjectView(R.id.editTitulo) private TextView titulo;
    @InjectView(R.id.fcancel_boton) private Button bcancelar;
    @InjectView(R.id.fcont_boton) private Button bcontinuar;
    @InjectView(R.id.tipos_spinner) private Spinner spinner;
    @InjectView(R.id.fvencimiento) private EditText fechaVencimiento;
    @InjectView(R.id.pmap)    MapView mMapView;

    @Inject public BasePoint claseEnvio;

    static String PUNTO_MESSAGE = "mensaje.al.fragment";
    GoogleMap googleMap;
    @Inject private static Gson gson = new Gson();

    public PointFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate(this);
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
            claseEnvio.setId("");
         //  claseEnvio.setAccion(PointActions.CONSULTA);//valor para probar... sacarlo desp TODO
        }

        revisarAccion(view);

        configurarLayout();

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

        //Muestro el mapa
        if (mMapView != null){
            mMapView.onCreate(savedInstanceState);
            mMapView.onResume();
            cargarMapa();
        }

    }


    public void revisarAccion (View view){

        EditText editTitulo= (EditText) view.findViewById(R.id.editTitulo);
        EditText editDescripcion= (EditText) view.findViewById(R.id.editText);

        if (claseEnvio.accion == PointActions.CONSULTA || claseEnvio.accion == PointActions.MODIFICACION ) {

            presenter.obtenerPunto(getContext(), claseEnvio._id);
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

                    LatLng ubicacion = new LatLng(claseEnvio.latitud, claseEnvio.longitud);
                googleMap.addMarker(new MarkerOptions().position(ubicacion));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion,15));

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
        ubicacion.setText(getCompleteAddressString(claseEnvio.latitud, claseEnvio.longitud));

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

                claseEnvio.setFechaModificacion(new Date());
                claseEnvio.setDescripcion(descripcion.getText().toString());
                claseEnvio.setTitulo(titulo.getText().toString());
                claseEnvio.setFechaVto(fechaVencimiento.getText().toString());

                if (claseEnvio._id == ""){
                    claseEnvio.setId_usuario(22);
                    claseEnvio.setUsuario("Dani Chacur");
                }
                //TODO: Persistir los datos en la base de datos_por alguna razón no hacen nada
                if (claseEnvio.accion==PointActions.ALTA){
                    String texto =gson.toJson(claseEnvio);
                    presenter.guardarPunto(getContext(),texto);
                }
                if (claseEnvio.accion==PointActions.MODIFICACION){
                    presenter.actualizarPunto(getContext(),claseEnvio._id,gson.toJson(claseEnvio));
                }

  //              nuevoBackStack();
            }
        });
    }

    public void nuevoBackStack(){
        ocultarTeclado();
        getFragmentManager().popBackStack();
/*
        if (claseEnvio.accion == PointActions.MODIFICACION) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.content, new UserFragment(), "Fragment")
                    .commit();
        }else{
            getFragmentManager().beginTransaction()
                    .replace(R.id.content, new MapFragment(), "Fragment")
                    .commit();
        }*/
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

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
    public void loadPoint(BasePoint punto) {

            if(punto != null){
                claseEnvio = punto;

                //lleno el layout con los datos devueltos
                cargarMapa();
                ubicacion.setText(getCompleteAddressString(claseEnvio.latitud, claseEnvio.longitud));

                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                        R.array.tipos_array, android.R.layout.simple_spinner_item);
                int spinnerPosition = adapter.getPosition(claseEnvio.tipo);
                spinner.setSelection(spinnerPosition);

                titulo.setText(claseEnvio.titulo);
                fechaVencimiento.setText(claseEnvio.fechaVto);
                descripcion.setText(claseEnvio.descripcion);

            }else{
                Toast.makeText(getContext(), "Error al obtener los datos", Toast.LENGTH_LONG ).show();
            }
    }

    @Override
    public void okPoint(BasePoint punto) {

        if(punto != null){
            claseEnvio = punto;
            Toast.makeText(getContext(), "Punto Guardado correctamente", Toast.LENGTH_LONG ).show();
        }else{
            Toast.makeText(getContext(), "Error al obtener los datos", Toast.LENGTH_LONG ).show();
        }
        nuevoBackStack();
    }


    @Override
    public void showMessageError(FetchPuntosErrors error) {
        Toast.makeText(getContext(), "Error al consultar el servidor", Toast.LENGTH_LONG ).show();
/*        String msjError = "";
        String title = getString(R.string.POPUP_TITLE_SERVIDOR);
        switch (error) {
            case PROBLEMA_SERVIDOR:
                msjError = getString(R.string.sin_comunicacion);
                break;
            case PROBLEMA_BUSQUEDA:
                Toast.makeText(getContext(), "Error al consultar el servidor", Toast.LENGTH_LONG );
                title = getString(R.string.POPUP_TITLE_SIN_NOVEDADES);
                msjError = getString(R.string.POPUP_MENSAJE_SIN_NOVEDADES);
                break;
            case TIME_OUT:
                msjError = getString(R.string.sin_comunicacion);
                break;
        }*/
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
