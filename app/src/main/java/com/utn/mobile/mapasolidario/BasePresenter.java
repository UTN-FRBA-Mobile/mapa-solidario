package com.utn.mobile.mapasolidario;

import com.utn.mobile.mapasolidario.util.BaseView;

import org.greenrobot.eventbus.EventBus;

public abstract class BasePresenter<T extends BaseView> implements Presenter<T> {

    protected T view;

    @Override
    public void onCreate(T view) {
        this.view = view;
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
    }

}
