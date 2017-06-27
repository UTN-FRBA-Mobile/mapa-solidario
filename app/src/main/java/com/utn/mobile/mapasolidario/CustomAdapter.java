package com.utn.mobile.mapasolidario;

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by danytin on 23/06/2017.
 */

public class CustomAdapter extends BaseAdapter {

    Context context;
    int flags[];
    CharSequence[] countryNames;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, int[] flags, @ArrayRes int textArrayResId) {
        this.context = applicationContext;
        this.flags = flags;
        this.countryNames = context.getResources().getTextArray(textArrayResId);
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return flags.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    public int getPosition(String item) {
        int index = -1;
        for (int i=0;i<countryNames.length;i++) {
            if (countryNames[i].equals(item)) {
                index = i;
                break;
            }
        }

        return index;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_item, null);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        TextView names = (TextView) view.findViewById(R.id.tipo);
        icon.setImageResource(flags[i]);
        names.setText(countryNames[i]);
        return view;
    }
}

