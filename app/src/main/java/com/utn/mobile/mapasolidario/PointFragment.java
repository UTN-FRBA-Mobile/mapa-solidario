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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.inject.Inject;
import com.utn.mobile.mapasolidario.dto.PuntoUpdate;
import com.utn.mobile.mapasolidario.util.FetchPuntosErrors;
import com.utn.mobile.mapasolidario.util.PointActions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import roboguice.inject.InjectView;

public class PointFragment extends BaseFragment
        implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, PointFragmentView,
        AdapterView.OnItemSelectedListener {
    private OnFragmentInteractionListener mListener;

    @Inject
    private PointFragmentPresenter presenter;

    @InjectView(R.id.tubicacion)
    private TextView ubicacion;
    @InjectView(R.id.editText)
    private TextView descripcion;
    @InjectView(R.id.editTitulo)
    private TextView titulo;
    @InjectView(R.id.fcancel_boton)
    private Button bcancelar;
    @InjectView(R.id.fcont_boton)
    private Button bcontinuar;
    @InjectView(R.id.fborrar_boton)
    private Button bborrar;
    @InjectView(R.id.tipos_spinner)
    private Spinner spinner;
    @InjectView(R.id.fvencimiento)
    private EditText fechaVencimiento;
    @InjectView(R.id.fcreacion)
    private EditText fechacreacion;
    @InjectView(R.id.eayuda)
    EditText ayudas;
    @InjectView(R.id.pmap)
    MapView mMapView;
    @InjectView(R.id.lcreacion)
    LinearLayout creacion;
    @InjectView(R.id.lvencimiento)
    LinearLayout lvencimiento;
    @InjectView(R.id.svencimiento)
    ToggleButton vswitch;


    @Inject
    public BasePoint claseEnvio;

    private PointActions accion;
    static String PUNTO_MESSAGE = "mensaje.al.fragment";
    GoogleMap googleMap;

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
        if (getArguments() != null) {
            claseEnvio = (BasePoint) getArguments().getSerializable(PUNTO_MESSAGE);
            //     claseEnvio.setId("");
            //    claseEnvio.setAccion(PointActions.MODIFICACION);//valor para probar... sacarlo desp TODO
            accion = claseEnvio.accion;
        }

        //oculto la barra de abajo
        getActivity().findViewById(R.id.navigation).setVisibility(View.GONE);

        revisarAccion(view);

        configurarLayout();

        accionBotonContinuar();
        accionBotonBorrar();

        //accion botón cancelar
        bcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bcontinuar.setVisibility(View.VISIBLE);
                nuevoBackStack();
            }
        });

        //Muestro el mapa
        if (mMapView != null) {
            mMapView.onCreate(savedInstanceState);
            mMapView.onResume();
            //cargarMapa(); TODO: sino en CONSULTA se carga doble el mapa, primero con claseEnvio default y luego con claseEnvio de loadPoint
        }

        vswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    fechaVencimiento.setVisibility(View.VISIBLE);
                } else {
                    fechaVencimiento.setText("");
                    fechaVencimiento.setVisibility(View.GONE);
                }
            }
        });

    }

    public void revisarAccion(View view) {

        EditText editTitulo = (EditText) view.findViewById(R.id.editTitulo);
        EditText editDescripcion = (EditText) view.findViewById(R.id.editText);

        if (claseEnvio.accion == PointActions.CONSULTA || claseEnvio.accion == PointActions.MODIFICACION) {

            presenter.obtenerPunto(getContext(), claseEnvio._id);
            creacion.setVisibility(View.VISIBLE); // muestra fecha de creación
            creacion.setEnabled(false);
        }
        if (claseEnvio.accion == PointActions.CONSULTA) {
            //Deshabilitar la edición
            //  bcontinuar.setVisibility(View.INVISIBLE);
            vswitch.setVisibility(View.GONE);//oculto el toggle
            bcontinuar.setText(getResources().getString(R.string.punto_ayudar));
            fechacreacion.setEnabled(false);
            fechaVencimiento.setEnabled(false);
            editTitulo.setEnabled(false);
            editDescripcion.setEnabled(false);
            spinner.setEnabled(false);
        }
        if (claseEnvio.accion == PointActions.ALTA) {
            LinearLayout frame = (LinearLayout) view.findViewById(R.id.layuda);
            frame.setVisibility(View.GONE);
            cargarMapa(); //TODO: agregado y sacado del onViewCreated
        }

        if (claseEnvio.accion == PointActions.MODIFICACION) {

//            float value = getResources().getDimension(R.dimen.vencimiento);
//            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) fechaVencimiento.getLayoutParams();
//            params.setMarginStart(410);
//            params.setMarginStart(230);
//            fechaVencimiento.setLayoutParams(params);
        }

    }

    void cargarMapa() {

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
                String tipo_aux = claseEnvio.tipo;

                googleMap.clear();

//TODO: depende el evento llega el tipo vacío, si toco volver del detalle (no se cargan los puntos) y toco +, me toma el tipo individuo que es el primero del array
//TODO: no me toma el if
//                    if (claseEnvio.accion==(PointActions.CONSULTA) ) {
                if (tipo_aux.equals("Heladera Solidaria")) {
                    // creo punto de tipo heladera
                    googleMap.addMarker(new MarkerOptions().position(ubicacion).icon(BitmapDescriptorFactory.fromResource(R.drawable.heladera_marker)));
                }
                if (tipo_aux.equals("Ropero Solidario")) {
                    // creo punto de tipo ropero
                    googleMap.addMarker(new MarkerOptions().position(ubicacion).icon(BitmapDescriptorFactory.fromResource(R.drawable.ropero_marker)));
                }
                if (tipo_aux.equals("Individuo")) {
                    // creo punto de tipo individuo
                    googleMap.addMarker(new MarkerOptions().position(ubicacion).icon(BitmapDescriptorFactory.fromResource(R.drawable.individuo_marker)));
                }
                if (tipo_aux.equals("Emergencia")) {
                    // creo punto de tipo emergencia
                    googleMap.addMarker(new MarkerOptions().position(ubicacion).icon(BitmapDescriptorFactory.fromResource(R.drawable.emergencia_marker)));
                }
//                    }
//                if (tipo_aux.equals("")) {
                if (claseEnvio.accion == (PointActions.ALTA)) {
                    // creo punto de tipo +
                    googleMap.addMarker(new MarkerOptions().position(ubicacion).icon(BitmapDescriptorFactory.fromResource(R.drawable.new_marker)));
                }

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 17));

                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.getUiSettings().setCompassEnabled(false);
                googleMap.getUiSettings().setZoomGesturesEnabled(false);
                googleMap.getUiSettings().setScrollGesturesEnabled(false);
                googleMap.getUiSettings().setMapToolbarEnabled(false);
            }
        });
        mMapView.setClickable(false);
    }

    public void configurarLayout() {

        //Mostrar dirección del punto
        ubicacion.setText(getCompleteAddressString(claseEnvio.latitud, claseEnvio.longitud));

        // Lleno el combo de tipo de necesidad

        int flags[] = {R.drawable.individuo_marker, R.drawable.heladera_marker, R.drawable.ropero_marker, R.drawable.emergencia_marker};

//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
//                R.array.tipos_array, android.R.layout.simple_spinner_item);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
        //              R.layout.row, R.id.tipo, R.array.tipos_array);

        CustomAdapter adapter = new CustomAdapter(getContext(), flags, R.array.tipos_array);
        //      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //Oculto el teclado cuando sale del foco
        ocultarTeclado();

        //Cargo el datePicker de la fecha vencimiento
        llenarDatepicker();

        ayudas.setText(String.valueOf(claseEnvio.contador));
        ayudas.setEnabled(false);
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

                strAdd = address + ", " + state + ", " + country;

            } else {
                strAdd = getResources().getString(R.string.error_geolocalizacion);
            }
        } catch (Exception e) {
            e.printStackTrace();
            strAdd = getResources().getString(R.string.error_geolocalizacion);
        }
        return strAdd;
    }

    public void ocultarTeclado() {
        //Oculto el teclado cuando sale del foco
        View foco = getActivity().getCurrentFocus();
        if (foco != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(foco.getWindowToken(), imm.HIDE_NOT_ALWAYS);
        }

    }

    public void llenarDatepicker() {

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

    public void accionBotonContinuar() {
        bcontinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (claseEnvio.accion == PointActions.CONSULTA) {
                    claseEnvio.setContador(claseEnvio.contador + 1);//sumo un punto cada vez que tocan el botón
                } else {
//                    claseEnvio.setFechaModificacion(new Date());
                    claseEnvio.setDescripcion(descripcion.getText().toString());
                    claseEnvio.setTitulo(titulo.getText().toString());
                    if (vswitch.isChecked() == true) {
                        String fecha = fechaVencimiento.getText().toString();
                      //            fechacreacion.setText(convertirFecha(claseEnvio.fechaCreacion));
                         claseEnvio.setFechaVto(fecha.substring(6,10)+fecha.substring(3,5)+fecha.substring(0,2)+"000000");
                    }
                }

                String mysz2 = claseEnvio.titulo.replaceAll("\\s", "");
                if (mysz2 == "") {
                    Toast.makeText(getContext(), "El título es obligatorio", Toast.LENGTH_LONG).show();
                } else {
                    if (claseEnvio.accion == PointActions.ALTA) {
                        claseEnvio.setId_usuario(UserProvider.get().getId());
                        claseEnvio.setUsuario(UserProvider.get().getNombre() + " " + UserProvider.get().getApellido());
                        presenter.guardarPunto(getContext(), claseEnvio);
                    }
                    if (claseEnvio.accion == PointActions.MODIFICACION) {
                        presenter.actualizarPunto(getContext(), claseEnvio._id, new PuntoUpdate(claseEnvio));
                    }
                }
                if (claseEnvio.accion == PointActions.CONSULTA) {
                    presenter.actualizarPunto(getContext(), claseEnvio._id);
                }


                //              nuevoBackStack();
            }
        });
    }

    public void accionBotonBorrar() {
        if (claseEnvio.accion == PointActions.MODIFICACION) {
            bborrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.borrarPunto(getContext(), claseEnvio._id);
                }
            });
        } else
            bborrar.setVisibility(View.GONE);

    }

    public void nuevoBackStack() {
        ocultarTeclado();
        //muestro la barra de abajo
        getActivity().findViewById(R.id.navigation).setVisibility(View.VISIBLE);
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

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            fechaVencimiento.setVisibility(View.VISIBLE);
        } else {
            fechaVencimiento.setVisibility(View.GONE);
            claseEnvio.setFechaVto("20010101000000");
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
//        claseEnvio.setTipo(parent.getItemAtPosition(pos).toString());
        claseEnvio.setTipo(getResources().getTextArray(R.array.tipos_array)[pos].toString());
        ocultarTeclado();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    public void loadPoint(BasePoint punto) {

        if (punto != null) {
            claseEnvio = punto;

            //lleno el layout con los datos devueltos
            cargarMapa();
            ubicacion.setText(getCompleteAddressString(claseEnvio.latitud, claseEnvio.longitud));

            int flags[] = {R.drawable.individuo_marker, R.drawable.heladera_marker, R.drawable.ropero_marker, R.drawable.emergencia_marker};
            CustomAdapter adapter = new CustomAdapter(getContext(), flags, R.array.tipos_array);
            //               ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
            //                     R.array.tipos_array, android.R.layout.simple_spinner_item);
//                int spinnerPosition = adapter.getPosition(claseEnvio.tipo);
            spinner.setSelection(adapter.getPosition(claseEnvio.tipo));

            titulo.setText(claseEnvio.titulo);

            if (claseEnvio.getFechaVto().equals("20010101000000") || claseEnvio.fechaVto == "") {
                if (accion == PointActions.MODIFICACION) {
                    vswitch.setChecked(false);
                } else {
                    lvencimiento.setVisibility(View.GONE);
                }
            } else {
                fechaVencimiento.setVisibility(View.VISIBLE);
                fechaVencimiento.setText(convertirFecha(claseEnvio.fechaVto));
                if (accion == PointActions.MODIFICACION) {
                    vswitch.setChecked(true);
                }
            }
            descripcion.setText(claseEnvio.descripcion);

            //Mostrarlo en el text view
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss", Locale.US);
//            fechacreacion.setText(sdf.format(claseEnvio.fechaCreacion));
            fechacreacion.setText(convertirFecha(claseEnvio.fechaCreacion));

            ayudas.setText(String.valueOf(claseEnvio.contador));

            claseEnvio.setAccion(accion);
        } else {
            Toast.makeText(getContext(), "Error al obtener los datos", Toast.LENGTH_LONG).show();
        }
    }


    public String convertirFecha (String fecha){
        String fechaBonita = "";
        fechaBonita = fecha.substring(6,8)+"/"+fecha.substring(4,6)+"/"+fecha.substring(0,4);
        return fechaBonita;
    }

    @Override
    public void okPoint(BasePoint punto) {

        if (punto != null) {
            claseEnvio = punto;
            if (accion == PointActions.CONSULTA) {
                Toast.makeText(getContext(), "¡Gracias por tu ayuda!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Punto Guardado correctamente", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), "Error al obtener los datos", Toast.LENGTH_LONG).show();
        }
        nuevoBackStack();
    }

    @Override
    public void showMessageError(FetchPuntosErrors error) {
        Toast.makeText(getContext(), "Error al consultar el servidor", Toast.LENGTH_LONG).show();
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
        void goBack();
    }
}
