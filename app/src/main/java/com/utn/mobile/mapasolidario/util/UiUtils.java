package com.utn.mobile.mapasolidario.util;

import android.content.Context;
import android.widget.Toast;

import com.utn.mobile.mapasolidario.R;

/**
 * Created by svillarreal on 08/05/17.
 */

public class UiUtils {

    public static boolean checkNetworkAvailable(Context context) {
        boolean networkAvailable = Utils.isNetworkAvailable(context);
        if (!networkAvailable) {
            Toast.makeText(context, context.getString(R.string.sin_conexion), Toast.LENGTH_LONG).show();
        }
        return networkAvailable;
    }

}
