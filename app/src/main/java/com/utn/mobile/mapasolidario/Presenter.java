package com.utn.mobile.mapasolidario;

import com.utn.mobile.mapasolidario.util.BaseView;

public interface Presenter<T extends BaseView> {

    public void onCreate(T view);

    public void onResume();

    public void onPause();

}