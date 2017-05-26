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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.utn.mobile.mapasolidario.util.PointActions;

import roboguice.inject.InjectView;


public class MapFragment extends BaseFragment
        implements OnMapReadyCallback, View.OnClickListener{


    private static final int LOCATION_REQUEST_CODE = 1;

    //location
    private TrackGPS gps;
    LatLng currentLocation;

    GoogleMap mMap;
    MapView mMapView;
    View mView;

    @InjectView(R.id.bpunto)     private FloatingActionButton botonf;
    @InjectView(R.id.mcancel_boton)     private Button bcancel;
    @InjectView(R.id.mcont_boton)     private Button bcontinuar;
    @InjectView(R.id.mtexto)     private TextView texto;

    public static final String PUNTO_MESSAGE = "mensaje.al.fragment";

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
                texto.setVisibility(View.VISIBLE);
                bcancel.setVisibility(View.VISIBLE);
                bcontinuar.setVisibility(View.VISIBLE);

                mMap.clear();

                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                    @Override
                    public void onMapClick(LatLng point) {
                        // TODO Auto-generated method stub
                        //lstLatLngs.add(point);
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions().position(point));
                    }
                });


                mMap.addMarker(new MarkerOptions().position(currentLocation));

            }
        });
    }

    public void confirmarPunto(){
        bcontinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BasePoint claseEnvio = new BasePoint();
                claseEnvio.setUbicacion(currentLocation); //Acá le seteo la ubicación al fragment de creación
                claseEnvio.setAccion(PointActions.ALTA);
                enviarPunto(claseEnvio);
                ocultar();
            }
        });
    }


    //Usar este método para llamar al ABM del punto pasando por parametro el punto en cuestión
    public void enviarPunto(BasePoint claseEnvio){

        Fragment fragment = new PointFragment();
        Bundle args = new Bundle();
        args.putSerializable(PUNTO_MESSAGE,claseEnvio);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mapcontainer, fragment, "Fragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void cancelarPunto(){
        bcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        ocultar();    }
        });
    }

    public void ocultar(){
        //TODO: implementar acá que se oculte el marker de seleccion de lugar
        botonf.setVisibility(View.VISIBLE);
        texto.setVisibility(View.INVISIBLE);
        bcancel.setVisibility(View.INVISIBLE);
        bcontinuar.setVisibility(View.INVISIBLE);

        //mMap.clear();
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
/*
        texto = (TextView) mView.findViewById(R.id.mtexto);
        botonf = (FloatingActionButton) mView.findViewById(R.id.bpunto);
        bcontinuar = (Button) mView.findViewById(R.id.mcont_boton);
        bcancel = (Button) mView.findViewById(R.id.mcancel_boton);
*/
        ocultar();
        nuevaNecesidad();
        confirmarPunto();
        cancelarPunto();

        MapsInitializer.initialize(getContext());

        mMap = googleMap;
        gps = new TrackGPS(getContext());

        currentLocation = new LatLng(-34.6183, -58.3732);
        LatLng ejemplo2 = new LatLng(-34.6083, -58.3732);
        LatLng ejemplo3 = new LatLng(-34.607, -58.3712);

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setMapToolbarEnabled(false);




        // Custom InfoWindow
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker arg0) {

                // Getting view from the layout file info_window_layout
                View v = getActivity().getLayoutInflater().inflate(R.layout.map_infowindow, null);

                return v;

            }
        });



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
        //mMap.addMarker(new MarkerOptions().position(ejemplo1).title("Test").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).snippet("Prueba de texto"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ejemplo1, 18));
        mMap.addMarker(new MarkerOptions().position(ejemplo2).title("Test").draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ejemplo2, 18));
        mMap.addMarker(new MarkerOptions().position(ejemplo3));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 18));



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
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 19));
                            //mMap.addMarker(new MarkerOptions().position(currentLocation).title("Prueba location").snippet("Prueba de texto"));

                        }
                        else{
                            gps.showSettingsAlert();
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

}
