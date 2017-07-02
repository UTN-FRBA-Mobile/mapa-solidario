package com.utn.mobile.mapasolidario.event;


import android.app.ProgressDialog;

/**
 * Created by svillarreal on 14/05/17.
 */

public class HideProgressDialogEvent {

    public HideProgressDialogEvent (){

    }

    public HideProgressDialogEvent (ProgressDialog progress){
        progress.dismiss();

    }

}
