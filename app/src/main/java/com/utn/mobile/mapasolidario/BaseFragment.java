package com.utn.mobile.mapasolidario;

import android.app.ProgressDialog;
import android.widget.Toast;

import com.utn.mobile.mapasolidario.event.HideProgressDialogEvent;
import com.utn.mobile.mapasolidario.event.ShowProgressDialogEvent;
import com.utn.mobile.mapasolidario.util.BaseView;

import org.greenrobot.eventbus.EventBus;

import roboguice.fragment.RoboFragment;

/**
 * Created by svillarreal on 08/05/17.
 */

public abstract class BaseFragment extends RoboFragment implements BaseView {

    private ProgressDialog progress;

    @Override
    public void showSuccessMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        progress = new ProgressDialog(getActivity());
        EventBus.getDefault().post(new ShowProgressDialogEvent(progress));
    }

    @Override
    public void hideProgressDialog() {
        EventBus.getDefault().post(new HideProgressDialogEvent(progress));
    }


}
