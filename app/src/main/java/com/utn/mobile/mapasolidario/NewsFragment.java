package com.utn.mobile.mapasolidario;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.inject.Inject;
import com.utn.mobile.mapasolidario.dto.NovedadResponse;
import com.utn.mobile.mapasolidario.util.FetchNewsErrors;
import com.utn.mobile.mapasolidario.util.PointActions;
import com.utn.mobile.mapasolidario.util.Utils;

import java.util.List;

import roboguice.inject.InjectView;

import static com.utn.mobile.mapasolidario.util.Utils.consultarPunto;

public class NewsFragment extends BaseFragment implements NewsFragmentView {
    private OnFragmentInteractionListener mListener;

    @InjectView(R.id.newsRecyclerView)
    private RecyclerView recyclerView;

    @Inject
    private NewsFragmentPresenter presenter;

    private ProgressDialog progress;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.fetchNews(getContext());

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NewsFragmentAdapter projectAdapter = new NewsFragmentAdapter(getContext(), this);
        recyclerView.setAdapter(projectAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        projectAdapter.SetOnItemClickListener(new NewsFragmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, String id) {
//                System.out.println("onItemClick" + id);
                BasePoint claseEnvio = new BasePoint();
                claseEnvio.setId(id);
                claseEnvio.setAccion(PointActions.CONSULTA);
                FragmentManager fragmentManager = getFragmentManager();
                consultarPunto(claseEnvio, fragmentManager);
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        presenter = new NewsFragmentPresenter();
        presenter.onCreate(this);

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
    public void showMessageError(FetchNewsErrors error) {
        String msjError = "";
        String title = getString(R.string.POPUP_TITLE_SERVIDOR);
        switch (error) {
            case PROBLEMA_SERVIDOR:
                msjError = getString(R.string.sin_comunicacion);
                break;
            case PROBLEMA_BUSQUEDA:
                title = getString(R.string.POPUP_TITLE_SIN_NOVEDADES);
                msjError = getString(R.string.POPUP_MENSAJE_SIN_NOVEDADES);
                break;
            case TIME_OUT:
                msjError = getString(R.string.sin_comunicacion);
                break;
        }
        Utils.createMessageDialog(this.getContext(), title, msjError, R.drawable.ic_notifications_black_24dp);
    }

    @Override
    public void showMessageSinNovedades() {
        Utils.createMessageDialog(this.getContext(), getString(R.string.POPUP_TITLE_SIN_NOVEDADES), getString(R.string.POPUP_MENSAJE_SIN_NOVEDADES), R.drawable.ic_notifications_black_24dp);
    }

    @Override
    public void loadNovedadesPreNavigate(List<NovedadResponse> resultadoDTO) {
        ((NewsFragmentAdapter) recyclerView.getAdapter()).updateData(resultadoDTO);
    }

    @Override
    public void changeItemColor(View itemView) {
        itemView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
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
