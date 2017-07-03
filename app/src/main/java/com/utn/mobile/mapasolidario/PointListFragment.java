package com.utn.mobile.mapasolidario;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.inject.Inject;
import com.utn.mobile.mapasolidario.dto.PuntoResponse;
import com.utn.mobile.mapasolidario.dummy.DummyContent.DummyItem;
import com.utn.mobile.mapasolidario.util.FetchPuntosErrors;

import java.util.List;

import roboguice.inject.InjectView;

public class PointListFragment extends BaseFragment implements PointItemListFragmentView {

    private OnListFragmentInteractionListener mListener;

    @InjectView(R.id.pointitem_list)
    private RecyclerView recyclerView;

    @Inject
    private PointListFragmentPresenter presenter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PointListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate(this);
        //presenter.fetchPuntos(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pointitem_list, container, false);
        presenter.fetchPuntos(getContext());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //presenter.fetchPuntos(getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PointItemRecyclerViewAdapter projectAdapter = new PointItemRecyclerViewAdapter(getContext(), this);
        recyclerView.setAdapter(projectAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        projectAdapter.SetOnItemClickListener(new NewsFragmentAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View v, int position, String id) {
//                BasePoint claseEnvio = new BasePoint();
//                claseEnvio.setId(id);
//                claseEnvio.setAccion(PointActions.CONSULTA);
//                FragmentManager fragmentManager = getFragmentManager();
//                consultarPunto(claseEnvio, fragmentManager);
//            }
//        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showMessageError(FetchPuntosErrors error) {
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
    }


    @Override
    public void loadPoints(List<PuntoResponse> resultadoDTO) {
        ((PointItemRecyclerViewAdapter) recyclerView.getAdapter()).updateData(resultadoDTO);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
