package com.utn.mobile.mapasolidario;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends BaseFragment implements OnMapReadyCallback {


    private static final int LOCATION_REQUEST_CODE = 1;

    GoogleMap mMap;
    MapView mMapView;
    View mView;
    private OnFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_map, container, false);
        return mView;
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


        MapsInitializer.initialize(getContext());

        mMap = googleMap;

        LatLng ejemplo1 = new LatLng(-34.6085 , -58.3812);
        LatLng ejemplo2 = new LatLng(-34.6083 , -58.3732);
        LatLng ejemplo3 = new LatLng(-34.607 , -58.3712);

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        // Controles UI
        if (ContextCompat.checkSelfPermission(super.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(super.getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Mostrar diálogo explicativo
            } else {
                // Solicitar permiso
                ActivityCompat.requestPermissions(
                        super.getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        }

        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Marcadores
        //mMap.addMarker(new MarkerOptions().position(ejemplo1).title("Test").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)).snippet("Prueba de texto"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ejemplo1, 18));
        mMap.addMarker(new MarkerOptions().position(ejemplo2).title("Test").draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ejemplo2, 18));
        mMap.addMarker(new MarkerOptions().position(ejemplo3).title("Test").snippet("Prueba de texto"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ejemplo3, 18));



    }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            // ¿Permisos asignados?
            if (permissions.length > 0 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            } else {
                Toast.makeText(getContext(), "Error de permisos", Toast.LENGTH_LONG).show();
            }

        }
    }

    /*// TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // The request code must be 0 or greater.
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    // The URL to +1.  Must be a valid URL.
    private final String PLUS_ONE_URL = "http://developer.android.com";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private PlusOneButton mPlusOneButton;



    public MapFragment() {
        // Required empty public constructor
    }

    */

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     *//*
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Refresh the state of the +1 textView each time the activity receives focus.
        mPlusOneButton.initialize(PLUS_ONE_URL, PLUS_ONE_REQUEST_CODE);
    }*/

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
     *//*
    */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
