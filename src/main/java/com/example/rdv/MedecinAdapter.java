package com.example.rdv;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MedecinAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Medecin> medecins;

    public MedecinAdapter(Context context, ArrayList<Medecin> medecins) {
        this.context = context;
        this.medecins = medecins;
    }

    @Override
    public int getCount() {
        return medecins.size();
    }

    @Override
    public Object getItem(int position) {
        return medecins.get(position);
    }

    @Override
    public long getItemId(int position) {
        return medecins.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_medecin, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.textViewMedecinNom);
        textViewName.setText(medecins.get(position).getNom() + " " + medecins.get(position).getPrenom());

        TextView textViewSpecialite = convertView.findViewById(R.id.textViewMedecinSpecialite);
        textViewSpecialite.setText(medecins.get(position).getSpecialite());

        return convertView;
    }
}
