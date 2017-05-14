package com.utn.mobile.mapasolidario;

import android.widget.Toast;

import com.utn.mobile.mapasolidario.util.BaseView;

import roboguice.fragment.RoboFragment;

/**
 * Created by svillarreal on 08/05/17.
 */

public abstract class BaseFragment extends RoboFragment implements BaseView {

    @Override
    public void showSuccessMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
