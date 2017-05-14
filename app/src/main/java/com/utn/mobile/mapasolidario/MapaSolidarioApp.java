package com.utn.mobile.mapasolidario;

import android.app.Application;

import roboguice.RoboGuice;

/**
 * Created by svillarreal on 14/05/17.
 */

public class MapaSolidarioApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RoboGuice.overrideApplicationInjector(this, new MapaSolidarioModule());
    }

}
