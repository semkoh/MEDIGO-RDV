package com.example.rdv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RendezVousAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<RendezVous> rendezVousList;

    public RendezVousAdapter(Context context, ArrayList<RendezVous> rendezVousList) {
        this.context = context;
        this.rendezVousList = rendezVousList;
    }

    @Override
    public int getCount() {
        return rendezVousList.size();
    }

    @Override
    public Object getItem(int position) {
        return rendezVousList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rendezVousList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_rendezvous, parent, false);
        }

        // Récupération des vues
        TextView textViewDateHeure = convertView.findViewById(R.id.textViewRendezVousDate);
        TextView textViewMotif = convertView.findViewById(R.id.textViewRendezVousMotif);
        TextView textViewStatut = convertView.findViewById(R.id.textViewRendezVousStatut);

        // Formatage de la date et de l'heure
        RendezVous rendezVous = rendezVousList.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(rendezVous.getDate());
        textViewDateHeure.setText(formattedDate);

        // Affichage du motif
        textViewMotif.setText(rendezVous.getMotif());

        // Affichage du statut
        textViewStatut.setText(rendezVous.getStatut());

        return convertView;
    }
}