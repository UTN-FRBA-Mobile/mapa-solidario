package com.utn.mobile.mapasolidario;

import com.utn.mobile.mapasolidario.dto.UserResponse;
import com.utn.mobile.mapasolidario.util.BaseView;
import com.utn.mobile.mapasolidario.util.GetUserErrors;

/**
 * Created by Juanca on 25/6/17.
 */

interface UserFragmentView extends BaseView {

    void showProgressDialog();

    void hideProgressDialog();

    void showMessageError(GetUserErrors error);

    void loadUserById(UserResponse resultadoDTO);

    void okUser(UserResponse resultadoDTO);

}
