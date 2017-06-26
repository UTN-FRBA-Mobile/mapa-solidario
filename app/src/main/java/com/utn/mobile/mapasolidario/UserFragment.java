package com.utn.mobile.mapasolidario;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.inject.Inject;
import com.squareup.picasso.Picasso;
import com.utn.mobile.mapasolidario.dto.UserResponse;
import com.utn.mobile.mapasolidario.util.GetUserErrors;

import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment implements UserFragmentView {

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private AccessToken accessToken;
    private AccessTokenTracker accessTokenTracker;
    private final String TAG = "UserFragment";
    private ClaseUsuario usuarioActualf;


    //@Inject private UserFragmentPresenter presenter;

    private  UserFragmentPresenter presenter = new UserFragmentPresenter();
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.GetUser(getContext(), usuarioActualf.getId());
    }

    private OnFragmentInteractionListener mListener;


    public UserFragment() {

    }

    public UserFragment(ClaseUsuario usuarioActual) {
        usuarioActualf = usuarioActual;
    }

   /* public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate(this);

        callbackManager = CallbackManager.Factory.create();


        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    Log.d(TAG, "Logout");
                    Intent intent = new Intent(getActivity() , MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);

        if (usuarioActualf != null){

            ((TextView)view.findViewById(R.id.tv_apellido)).setText(usuarioActualf.getApellido());
            ((TextView)view.findViewById(R.id.tv_nombre)).setText(usuarioActualf.getNombre());
            ((TextView)view.findViewById(R.id.tv_email)).setText(usuarioActualf.getMail());
            Picasso.with(this.getActivity()).load(usuarioActualf.getUrl()).fit().centerCrop().into((ImageView) view.findViewById(R.id.iv_usuario));

        }

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void loadUserById(UserResponse resultadoDTO) {

        if ( resultadoDTO.getId() == null){

            UserResponse userResponseSend = new UserResponse();

            userResponseSend.setUserId(usuarioActualf.getId());
            userResponseSend.setFname(usuarioActualf.getNombre());
            userResponseSend.setLname(usuarioActualf.getApellido());

            presenter.PostUser(getContext(), userResponseSend);
        }
    }


    @Override
    public void okUser(UserResponse userResponse) {

        if(userResponse != null){

            Toast.makeText(getContext(), "Usuario creado" + userResponse.getId().toString(), Toast.LENGTH_LONG ).show();
        }
    }


    @Override
    public void showMessageError(GetUserErrors error) {
        Toast.makeText(getContext(), "Error al consultar el servidor", Toast.LENGTH_LONG ).show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showSuccessMessage(String message) {

    }

    @Override
    public void showErrorMessage(String message) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
