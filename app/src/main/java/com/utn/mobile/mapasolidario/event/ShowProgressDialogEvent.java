package com.utn.mobile.mapasolidario.event;

import android.app.ProgressDialog;


/**
 * Created by svillarreal on 14/05/17.
 */


public class ShowProgressDialogEvent {


    public ShowProgressDialogEvent (){
    }

    public ShowProgressDialogEvent (ProgressDialog progress){

        progress.setMessage("Cargando ...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
     //   progress.setProgress(0);
        progress.show();

/*
        final int totalProgressTime = 1000;
        final Thread t = new Thread() {
            @Override
            public void run() {
                int jumpTime = 0;

                while(jumpTime < totalProgressTime) {
                    try {
                        sleep(200);
                        jumpTime += 5;
                        progress.setProgress(jumpTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();

*/

    }


}
