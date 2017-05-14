package com.utn.mobile.mapasolidario;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.google.inject.Inject;
import com.utn.mobile.mapasolidario.event.HideProgressDialogEvent;
import com.utn.mobile.mapasolidario.event.ShowProgressDialogEvent;

import org.greenrobot.eventbus.Subscribe;

import roboguice.activity.RoboFragmentActivity;

public class MainActivity extends RoboFragmentActivity
        implements MapFragment.OnFragmentInteractionListener,
        NewsFragment.OnFragmentInteractionListener,
        UserFragment.OnFragmentInteractionListener{

    @Inject
    private ProgressDialog progressDialog;

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
                            .replace(R.id.content, new UserFragment(), "Fragment")
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

    @Override
    public void onFragmentInteraction(Uri uri) {

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
