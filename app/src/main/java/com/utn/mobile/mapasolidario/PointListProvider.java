package com.utn.mobile.mapasolidario;

import com.utn.mobile.mapasolidario.dto.PuntoResponse;

import java.util.ArrayList;
import java.util.List;

public class PointListProvider {

    public static List<PuntoResponse> pointList;

    public static List<PuntoResponse>  get(){
        if(pointList == null)
            pointList = new ArrayList<PuntoResponse>();
        return pointList;
    }

    public static List<PuntoResponse> set(List<PuntoResponse> _pointlist){
        return pointList = _pointlist;
    }
}