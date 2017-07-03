package com.utn.mobile.mapasolidario.user;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.squareup.picasso.Picasso;
import com.utn.mobile.mapasolidario.BaseFragment;
import com.utn.mobile.mapasolidario.MainActivity;
import com.utn.mobile.mapasolidario.R;
import com.utn.mobile.mapasolidario.UserProvider;
import com.utn.mobile.mapasolidario.dto.PuntoResponse;

import java.util.List;

import roboguice.inject.InjectView;

public class UserFragment extends BaseFragment implements UserView {

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private AccessToken accessToken;
    private AccessTokenTracker accessTokenTracker;
    private final String TAG = "UserFragment";
    public MainActivity mActivity = null;

    private UserPresenter presenter;
    @InjectView(R.id.user_points_recyclerview)
    private RecyclerView recyclerView;

    private OnFragmentInteractionListener mListener;
    private List<PuntoResponse> points;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();

        presenter = new UserPresenterImpl(this, getContext());

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    Log.d(TAG, "Logout");
                    mActivity.navigateToLogin();
                }
            }
        };
        presenter.fetchUserPoints(getContext());

    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.points != null)
            ((UserPointsAdapter) recyclerView.getAdapter()).updateData(this.points);
        presenter.fetchUserPoints(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);

        setUserData(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setAdapter(new UserPointsAdapter(getContext(), this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void setUserData(View view) {
        ((TextView) view.findViewById(R.id.tv_apellido)).setText(UserProvider.get().getApellido());
        ((TextView) view.findViewById(R.id.tv_nombre)).setText(UserProvider.get().getNombre());
        ((TextView) view.findViewById(R.id.tv_email)).setText(UserProvider.get().getCorreo());
        String puntuacion = UserProvider.get().getPuntuacion() == null || UserProvider.get().getPuntuacion().isEmpty() ? "0" : UserProvider.get().getPuntuacion();
        ((TextView) view.findViewById(R.id.tv_mis_puntos)).setText("Mis puntos: " + puntuacion);
        Picasso.with(this.getActivity()).load(UserProvider.get().getUrl_imagen()).fit().centerCrop().into((ImageView) view.findViewById(R.id.iv_usuario));

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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (MainActivity) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void loadPoints(List<PuntoResponse> points) {
        this.points = points;
        ((UserPointsAdapter) recyclerView.getAdapter()).updateData(points);

    }
}
