package com.utn.mobile.mapasolidario;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Created by svillarreal on 13/05/17.
 */
public class ProgressDialogProvider implements Provider<ProgressDialog> {

    @Inject
    Context context;

    @Override
    public ProgressDialog get() {
        return new ProgressDialog(context);
    }
}