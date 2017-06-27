package com.utn.mobile.mapasolidario;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.internal.Logger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private AccessToken accessToken;
    private AccessTokenTracker accessTokenTracker;


    private final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);
        loginButton = (LoginButton)findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));

        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        if(accessToken != null){

            GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject user, GraphResponse graphResponse) {

                    if (graphResponse.getRawResponse() != null) {

                        ClaseUsuario usuario = new ClaseUsuario();
                        usuario.setId(user.optString("id"));
                        usuario.setNombre(user.optString("first_name"));
                        usuario.setApellido(user.optString("last_name"));
                        usuario.setMail(user.optString("email"));
                        usuario.setUrl("https://graph.facebook.com/" + user.optString("id") + "/picture?type=large");

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("usuario", usuario);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Debe conectarse a Internet", Toast.LENGTH_SHORT).show();
                        }
                    }
            });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,first_name,last_name,email,picture.type(large)");
            request.setParameters(parameters);
            request.executeAsync();
        }
        else {
            loginButton.setVisibility(View.VISIBLE);
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {

                    GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject user, GraphResponse graphResponse) {

                            ClaseUsuario usuario = new ClaseUsuario();
                            usuario.setId(user.optString("id"));
                            usuario.setNombre(user.optString("first_name"));
                            usuario.setApellido(user.optString("last_name"));
                            usuario.setMail(user.optString("email"));
                            usuario.setUrl("https://graph.facebook.com/" + user.optString("id") + "/picture?type=large");

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("usuario", usuario);
                            startActivity(intent);
                            finish();
                        }
                    });

                    Bundle parameters = new Bundle();
                    parameters.putString("fields", "id,first_name,last_name,email,picture.type(large)");
                    request.setParameters(parameters);
                    request.executeAsync();

                    Intent _intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(_intent);
                    finish();
                }

                @Override
                public void onCancel() {}

                @Override
                public void onError(FacebookException e) {}
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
