package com.utn.mobile.mapasolidario;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.inject.Inject;
import com.utn.mobile.mapasolidario.event.HideProgressDialogEvent;
import com.utn.mobile.mapasolidario.event.ShowProgressDialogEvent;

import org.greenrobot.eventbus.Subscribe;

import roboguice.activity.RoboFragmentActivity;
public class MainActivity extends RoboFragmentActivity
        implements MapFragment.OnFragmentInteractionListener,
        NewsFragment.OnFragmentInteractionListener,
        UserFragment.OnFragmentInteractionListener,
        PointFragment.OnFragmentInteractionListener{

    @Inject
    private ProgressDialog progressDialog;

    public static final String TAG = "MainActivity";
    private TextView mTextMessage;
    private ClaseUsuario usuarioActual;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, new MapFragment(), "Fragment")
                            .commit();
                    return true;
                case R.id.navigation_news:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, new NewsFragment(), "Fragment")
                            .commit();
                    return true;
                case R.id.navigation_user:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, new UserFragment(usuarioActual), "Fragment")
                            .commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        Intent intent = getIntent();
        usuarioActual = (ClaseUsuario)intent.getSerializableExtra("usuario");

        // Get Firebase token
        String token = FirebaseInstanceId.getInstance().getToken();

        // Log and toast
        Log.d(TAG, "Firebase token: "+token);

        if(accessToken != null){
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content, new MapFragment(), "Fragment")
                    .commit();
        }
        else {
            Intent _intent = new Intent(this, LoginActivity.class);
            startActivity(_intent);
        }
    }

    //@Override
    public void goBack() {
        getFragmentManager().popBackStack();
        //Muestro el botón que estaba oculto
   //     botonf.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        if (navigation.getVisibility()==View.GONE){
            navigation.setVisibility(View.VISIBLE);
        }
        super.onBackPressed();

    }

    @Subscribe
    public void hideProgressDialogEventRecieved(HideProgressDialogEvent hideProgressDialogEvent) {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Subscribe
    public void showProgressDialogEventRecieved(ShowProgressDialogEvent showProgressDialogEvent) {
        progressDialog.setMessage(getString(R.string.fetching_news));
        if (!progressDialog.isShowing()) {
            progressDialog.setIcon(android.R.drawable.ic_popup_sync);
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }

}
