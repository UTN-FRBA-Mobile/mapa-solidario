package com.utn.mobile.mapasolidario.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.inject.Inject;
import com.utn.mobile.mapasolidario.MainActivity;
import com.utn.mobile.mapasolidario.R;
import com.utn.mobile.mapasolidario.User;
import com.utn.mobile.mapasolidario.UserProvider;

import org.json.JSONObject;

import java.util.Arrays;

import roboguice.activity.RoboActivity;

public class LoginActivity extends RoboActivity implements  LoginView {

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private LoginPresenter presenter;
    private AccessToken accessToken;
    private AccessTokenTracker accessTokenTracker;

    private final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));

        presenter = new LoginPresenterImpl(this, this.getApplicationContext());

        accessToken = AccessToken.getCurrentAccessToken();

        if(accessToken != null)
            getUser();
        else
            setLoginButton();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void setLoginButton(){
        loginButton.setVisibility(View.VISIBLE);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getUser();
            }
            @Override
            public void onCancel() {}

            @Override
            public void onError(FacebookException e) {}
        });
    }

    public void getUser(){
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject user, GraphResponse graphResponse) {
                presenter.validateUser(user, graphResponse);
            }
        });

        graphRequest(request);
    }

    public void graphRequest(GraphRequest request){
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,email,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();
    }


    @Override
    public void navigateToHome(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void showProgress(){}

    @Override
    public void hideProgress(){}
}
