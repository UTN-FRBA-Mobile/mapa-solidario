package com.utn.mobile.mapasolidario;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.inject.Inject;
import com.utn.mobile.mapasolidario.dto.PuntoResponse;
import com.utn.mobile.mapasolidario.util.FetchPuntosErrors;
import com.utn.mobile.mapasolidario.util.PointActions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import roboguice.inject.InjectFragment;
import roboguice.inject.InjectView;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.greenrobot.eventbus.EventBus;


import static com.utn.mobile.mapasolidario.MainActivity.CLASS_MESSAGE;
import static com.utn.mobile.mapasolidario.util.Utils.consultarPunto;


public class MapFragment extends BaseFragment
        implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnInfoWindowClickListener, MapFragmentView{

    private static final int LOCATION_REQUEST_CODE = 1;

    //location
    private TrackGPS gps;
    LatLng currentLocation;
    BasePoint claseEnvio = new BasePoint();
    boolean firstTime = true;

    //asigno tag a cada marker del mapa
    Marker mAux;

    GoogleMap mMap;
    MapView mMapView;
    View mView;
    private int zoom = 16;

    @Inject
    private MapFragmentPresenter  presenter;

    @InjectView(R.id.bpunto)     private FloatingActionButton botonf;
    @InjectView(R.id.mcancel_boton)     private Button bcancel;
    @InjectView(R.id.mcont_boton)     private Button bcontinuar;
    //    @InjectView(R.id.mtexto)     private TextView texto;

    @InjectView(R.id.lnuevo) private FrameLayout layout_nuevo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate(this);
        gps = new TrackGPS(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_map, container, false);

        if(getArguments()!=null) {

            ClaseUsuario usuarioActual = (ClaseUsuario) getArguments().getSerializable(CLASS_MESSAGE);
            claseEnvio.setId_usuario(usuarioActual.getId());
            claseEnvio.setUsuario(usuarioActual.getNombre()+" "+usuarioActual.getApellido());
        }


        return mView;
    }

    public void nuevaNecesidad(){
        botonf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //muestro los botones
                botonf.setVisibility(View.INVISIBLE);
                layout_nuevo.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mMapView.getLayoutParams();
                params.addRule(RelativeLayout.ABOVE, R.id.lnuevo);
                mMapView.setLayoutParams(params);


                mMap.clear();

                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                    @Override
                    public void onMapClick(LatLng point) {

                        //lstLatLngs.add(point);
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions().position(point).icon(BitmapDescriptorFactory.fromResource(R.drawable.new_marker)));
//                        claseEnvio.setUbicacion(point); //Acá le seteo la ubicación al fragment de creación
                       // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, zoom));
                        claseEnvio.setLatitud(point.latitude);
                        claseEnvio.setLongitud(point.longitude);
                    }
                });


                mMap.addMarker(new MarkerOptions().position(currentLocation).icon(BitmapDescriptorFactory.fromResource(R.drawable.new_marker)));
         //       mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, zoom));
            }
        });
    }

    public void confirmarPunto(){
        bcontinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                claseEnvio.setAccion(PointActions.ALTA);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                consultarPunto(claseEnvio,fragmentManager);
                ocultar();
            }
        });
    }

    public void cancelarPunto(){
        bcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        ocultar();    }
        });
    }

    public void ocultar(){

        botonf.setVisibility(View.VISIBLE);
        layout_nuevo.setVisibility(View.INVISIBLE);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mMapView.getLayoutParams();
        params.removeRule(RelativeLayout.ABOVE);
        mMapView.setLayoutParams(params);


        mMap.clear(); //limpio new_marker
        presenter.fetchPuntos(getContext()); //get points
    }

    @Override
    public void onClick(View v) {

        }

    @Override
    public void onViewCreated(View view, Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);

        mMapView = (MapView) mView.findViewById(R.id.map);
                if (mMapView != null){
                    mMapView.onCreate(null);
                    mMapView.onResume();
                    mMapView.getMapAsync(this);
                }
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (firstTime){
 //           firstTime = false;
   //     }
     //   else{
//            gps.getLocation();
//            this.setCurrentLocation();
//        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        presenter.fetchPuntos(getContext()); //get points

        //ocultar();
        nuevaNecesidad();
        confirmarPunto();
        cancelarPunto();

        MapsInitializer.initialize(getContext());

        mMap = googleMap;

        currentLocation = new LatLng(-34.603748, -58.381533); //Obelisco

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);

/*
        // Custom InfoWindow
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker marker){

                // Getting view from the layout file info_window_layout

                // Setting up the infoWindow with current's marker info

                View v = getActivity().getLayoutInflater().inflate(R.layout.map_infowindow, null);

                return v;

            }
        });
*/

        // Permisos
            //no tengo los permisos de localización y pregunto si los otorgan, si no los otorgan muestro la última ubicación conocida.
            // si no quiero que me pregunten mas por los permisos muestro ubicación por default.
            //si los otorgo muestro la ubicación.
        if (ContextCompat.checkSelfPermission(super.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                requestPermissions(
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
            else {

                Toast toast1 = Toast.makeText(getContext(), "Recuerde que puede utilizar la localización para generar un punto con mayor precisión", Toast.LENGTH_LONG);
                toast1.setGravity(Gravity.CENTER,5,5);
                toast1.show();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, zoom));
            }
        }
        // tengo los permisos, muestro la ubicación
//        else
  //      {
            this.setCurrentLocation();
      //  }

        mMap.setOnInfoWindowClickListener(this);

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        //String idPoint = (String) marker.getTag();
        //Toast.makeText(getContext(),idPoint, Toast.LENGTH_LONG).show();
        claseEnvio.setId((String) marker.getTag());
        claseEnvio.setAccion(PointActions.CONSULTA);
        FragmentManager fragmentManager = getFragmentManager();
        consultarPunto(claseEnvio, fragmentManager);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.setCurrentLocation();
                } else {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, zoom));
                }
                return;
            }
        }
    }
        //si tengo desactivado el gps y lo activo primero me ubica en la ultima ubicación conocida
    private void setCurrentLocation(){
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if(gps.canGetLocation()){
                            gps.getLocation();
                            currentLocation = new LatLng(gps.getLatitude(), gps.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, zoom));
                            if (ContextCompat.checkSelfPermission(getContext(),
                                    Manifest.permission.ACCESS_FINE_LOCATION)
                                    == PackageManager.PERMISSION_GRANTED) {
                                mMap.setMyLocationEnabled(true);
                            }
                            else {
                                Toast.makeText(getContext(), "Última ubicación conocida!", Toast.LENGTH_SHORT).show();
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, zoom));
                            }
                            gps.stopUsingGPS();

                        }
                        else{
                            if (firstTime) {
                                gps.showSettingsAlert();
                                //TODO: falta llamar nuevamente a setCurrentLocation()
                                firstTime =false;
                            }
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, zoom));
                        }
                        claseEnvio.setLatitud(currentLocation.latitude);
                        claseEnvio.setLongitud(currentLocation.longitude);
                    }
                },
                1000);
    }

    private OnFragmentInteractionListener mListener;

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
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showMessageError(FetchPuntosErrors error) {
        String msjError = "";
        String title = getString(R.string.POPUP_TITLE_SERVIDOR);
        switch (error) {
            case PROBLEMA_SERVIDOR:
                msjError = getString(R.string.sin_comunicacion);
                break;
            case PROBLEMA_BUSQUEDA:
                title = getString(R.string.POPUP_TITLE_SIN_NOVEDADES);
                msjError = getString(R.string.POPUP_MENSAJE_SIN_NOVEDADES);
                break;
            case TIME_OUT:
                msjError = getString(R.string.sin_comunicacion);
                break;
        }
    }

    @Override
    public void loadPointsInMap(List<PuntoResponse> resultadoDTO) {
        for(PuntoResponse punto : resultadoDTO){

            if(punto.getTipo() != null){

                if (punto.getTipo().equals("Heladera Solidaria")){
                    // creo punto de tipo heladera
                    mAux=mMap.addMarker(new MarkerOptions().title(punto.getTitulo()).snippet(getString(R.string.leyenda_infoWindow)).position(new LatLng(
                            punto.getLatitud(),
                            punto.getLongitud()
                    )).icon(BitmapDescriptorFactory.fromResource(R.drawable.heladera_marker)));
                    mAux.setTag(punto.get_id());
                }

                if (punto.getTipo().equals("Ropero Solidario")){
                    // creo punto de tipo ropero
                    mAux=mMap.addMarker(new MarkerOptions().title(punto.getTitulo()).snippet(getString(R.string.leyenda_infoWindow)).position(new LatLng(
                            punto.getLatitud(),
                            punto.getLongitud()
                    )).icon(BitmapDescriptorFactory.fromResource(R.drawable.ropero_marker)));
                    mAux.setTag(punto.get_id());
                }

                if (punto.getTipo().equals("Individuo")){
                    // creo punto de tipo individuo
                    mAux=mMap.addMarker(new MarkerOptions().title(punto.getTitulo()).snippet(getString(R.string.leyenda_infoWindow)).position(new LatLng(
                            punto.getLatitud(),
                            punto.getLongitud()
                    )).icon(BitmapDescriptorFactory.fromResource(R.drawable.individuo_marker)));
                    mAux.setTag(punto.get_id());
                }

                if (punto.getTipo().equals("Emergencia")){
                    // creo punto de tipo emergencia
                    mAux=mMap.addMarker(new MarkerOptions().title(punto.getTitulo()).snippet(getString(R.string.leyenda_infoWindow)).position(new LatLng(
                            punto.getLatitud(),
                            punto.getLongitud()
                    )).icon(BitmapDescriptorFactory.fromResource(R.drawable.emergencia_marker)));
                    mAux.setTag(punto.get_id());
                }

            }

        }

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}