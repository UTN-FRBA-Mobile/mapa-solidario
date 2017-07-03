package com.utn.mobile.mapasolidario.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.widget.TextView;

import com.utn.mobile.mapasolidario.BasePoint;
import com.utn.mobile.mapasolidario.Constants;
import com.utn.mobile.mapasolidario.PointFragment;
import com.utn.mobile.mapasolidario.R;

import org.apache.commons.lang.StringUtils;

//import static com.utn.mobile.mapasolidario.MapFragment.PUNTO_MESSAGE;

/**
 * Created by svillarreal on 08/05/17.
 */
public class Utils {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static String getUsernameUsuarioLogin(Context context) {
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            return sharedPreferences.getString(Constants.KEY_USUARIO, Constants.KEY_NO_USUARIO);
        } catch (Exception e) {
            return Constants.KEY_NO_USUARIO;
        }
    }

    public static String getPasswordUsuarioLogin(Context context) {
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            return sharedPreferences.getString(Constants.PASSWORD, "");
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }

    public static void createMessageDialog(Context context, String title, String message, int iconId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(iconId);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        AlertDialog dialog = builder.show();
        TextView messageText = (TextView) dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);
        dialog.show();
    }

    public static void  consultarPunto(BasePoint claseEnvio,FragmentManager fragmentManager){
        Fragment fragment = new PointFragment();
        Bundle args = new Bundle();
        String PUNTO_MESSAGE = "mensaje.al.fragment";
        args.putSerializable(PUNTO_MESSAGE,claseEnvio);
        fragment.setArguments(args);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment, "Fragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}
