package com.utn.mobile.mapasolidario;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.AccessToken;

public class MainActivity extends AppCompatActivity
        implements MapFragment.OnFragmentInteractionListener,
        NewsFragment.OnFragmentInteractionListener,
        UserFragment.OnFragmentInteractionListener{

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
}
