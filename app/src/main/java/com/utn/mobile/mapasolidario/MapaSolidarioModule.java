package com.utn.mobile.mapasolidario;

import android.app.ProgressDialog;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * Created by svillarreal on 14/05/17.
 */

public class MapaSolidarioModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ProgressDialog.class).toProvider(ProgressDialogProvider.class).in(Singleton.class);
        bind(IRemoteService.class).to(RemoteServiceImpl.class);

    }
}
