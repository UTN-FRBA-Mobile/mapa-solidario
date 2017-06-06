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


import static com.utn.mobile.mapasolidario.util.Utils.consultarPunto;


public class MapFragment extends BaseFragment
        implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnInfoWindowClickListener, MapFragmentView{

    private static final int LOCATION_REQUEST_CODE = 1;

    //URL GET POINTS
    private static final String SERVICE_URL = "YOUR DRIVE SERVICE URL";

    //location
    private TrackGPS gps;
    LatLng currentLocation;
    BasePoint claseEnvio = new BasePoint();

    GoogleMap mMap;
    MapView mMapView;
    View mView;

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
        presenter.fetchPuntos(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_map, container, false);
        return mView;


    }

    public void nuevaNecesidad(){
        botonf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: implementar acá que se muestre el marker de seleccion de lugar

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
                        // TODO Auto-generated method stub
                        //lstLatLngs.add(point);
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions().position(point).icon(BitmapDescriptorFactory.fromResource(R.drawable.new_marker)));
                        claseEnvio.setUbicacion(point); //Acá le seteo la ubicación al fragment de creación
                    }
                });


                mMap.addMarker(new MarkerOptions().position(currentLocation).icon(BitmapDescriptorFactory.fromResource(R.drawable.new_marker)));

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
    public void onMapReady(GoogleMap googleMap) {


        //ocultar();
        nuevaNecesidad();
        confirmarPunto();
        cancelarPunto();


        MapsInitializer.initialize(getContext());

        mMap = googleMap;
        gps = new TrackGPS(getContext());

        currentLocation = new LatLng(-34.603748, -58.381533); //Obelisco
        LatLng ejemplo1 = new LatLng(-34.608, -58.3712);
        LatLng ejemplo2 = new LatLng(-34.6075, -58.3732);
        LatLng ejemplo3 = new LatLng(-34.607, -58.3712);
        LatLng ejemplo4 = new LatLng(-34.6085, -58.3732);

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setMapToolbarEnabled(false);




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
        if (ContextCompat.checkSelfPermission(super.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            this.setCurrentLocation();

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(super.getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Mostrar diálogo explicativo
            } else {
                // Solicitar permiso
                requestPermissions(
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        }

        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Marcadores

        mMap.addMarker(new MarkerOptions().title("Título 1").snippet("Haga click para Detalles").position(ejemplo1).icon(BitmapDescriptorFactory.fromResource(R.drawable.individuo_marker)));
        mMap.addMarker(new MarkerOptions().title("Título 2").snippet("Haga click para Detalles").position(ejemplo2).icon(BitmapDescriptorFactory.fromResource(R.drawable.heladera_marker)));
        mMap.addMarker(new MarkerOptions().title("Título 3").snippet("Haga click para Detalles").position(ejemplo3).icon(BitmapDescriptorFactory.fromResource(R.drawable.ropero_marker)));
        mMap.addMarker(new MarkerOptions().title("Título 4").snippet("Haga click para Detalles").position(ejemplo4).icon(BitmapDescriptorFactory.fromResource(R.drawable.emergencia_marker)));

        mMap.setOnInfoWindowClickListener(this);


    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        //TODO:IMPLEMENTAR ACÁ LLAMADO A FRAGMENT DETALLE DEL MARKER AL PRESIONAR INFOWINDOW
        Toast.makeText(getContext(),marker.getTitle(), Toast.LENGTH_LONG).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
                mMap.setMyLocationEnabled(true);
                this.setCurrentLocation();
            }
            else {
                Toast.makeText(getContext(), "Error de permisos", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setCurrentLocation(){
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if(gps.canGetLocation()){
                            Location _l = gps.getLocation();
                            currentLocation = new LatLng(_l.getLatitude(), _l.getLongitude());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 18));
                            gps.stopUsingGPS();

                        }
                        else{
                            gps.showSettingsAlert();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 18));
                        }
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
    public void loadPointsInMap(List<PuntoResponse> resultadoDTO) {
        for(PuntoResponse punto : resultadoDTO){

            if(punto.getTipo() != null){
                if (punto.getTipo().equals("heladera")){
                    // creo punto de tipo heladera
                    mMap.addMarker(new MarkerOptions().title(punto.getTitulo()).snippet("Haga click para Detalles").position(new LatLng(
                            punto.getLatitud(),
                            punto.getLongitud()
                    )).icon(BitmapDescriptorFactory.fromResource(R.drawable.heladera_marker)));

                }

                if (punto.getTipo().equals("ropero")){
                    // creo punto de tipo ropero
                    mMap.addMarker(new MarkerOptions().title(punto.getTitulo()).snippet("Haga click para Detalles").position(new LatLng(
                            punto.getLatitud(),
                            punto.getLongitud()
                    )).icon(BitmapDescriptorFactory.fromResource(R.drawable.ropero_marker)));

                }

                if (punto.getTipo().equals("individuo")){
                    // creo punto de tipo individuo
                    mMap.addMarker(new MarkerOptions().title(punto.getTitulo()).snippet("Haga click para Detalles").position(new LatLng(
                            punto.getLatitud(),
                            punto.getLongitud()
                    )).icon(BitmapDescriptorFactory.fromResource(R.drawable.individuo_marker)));

                }

                if (punto.getTipo().equals("emergencia")){
                    // creo punto de tipo emergencia
                    mMap.addMarker(new MarkerOptions().title(punto.getTitulo()).snippet("Haga click para Detalles").position(new LatLng(
                            punto.getLatitud(),
                            punto.getLongitud()
                    )).icon(BitmapDescriptorFactory.fromResource(R.drawable.emergencia_marker)));

                }
            }

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
     *//*
    */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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

}
