/*
 *
 *  * Copyright (C) 2014 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.utn.mobile.mapasolidario.login;

import android.content.Context;

import com.facebook.GraphResponse;
import com.google.inject.Inject;
import com.utn.mobile.mapasolidario.User;
import com.utn.mobile.mapasolidario.UserProvider;

import org.json.JSONObject;

public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnGetUserFinishedListener, LoginInteractor.OnSaveUserFinishedListener {

    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView, Context context) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl(context);
    }

    @Override
    public void validateUser(JSONObject user, GraphResponse response){
        if (loginView != null) {
            loginView.showProgress();
        }
        User _user = new User();
        _user.setId(user.optString("id"));
        _user.setNombre(user.optString("first_name"));
        _user.setApellido(user.optString("last_name"));
        _user.setCorreo(user.optString("email"));
        _user.setUrl_imagen("https://graph.facebook.com/" + user.optString("id") + "/picture?type=large");
        UserProvider.set(_user);

        loginInteractor.getUser(_user.getId(), this);
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

    @Override
    public void onUserExists(User user) {
        if (loginView != null) {
            loginView.hideProgress();
            UserProvider.set(user);
            loginView.navigateToHome();
        }
    }

    @Override
    public void onUserNotExists() {
        if (loginView != null) {
            loginView.hideProgress();
            loginInteractor.saveUser(this);
        }
    }

    @Override
    public void onUserSaved(User user){
        UserProvider.set(user);
        if (loginView != null) {
            loginView.hideProgress();
            loginView.navigateToHome();
        }
    }

    @Override
    public void onUserNotSaved(){

    }

}
