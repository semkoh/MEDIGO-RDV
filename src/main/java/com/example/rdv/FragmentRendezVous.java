package com.example.rdv;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class FragmentRendezVous extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, IRendezVousObserver {

    private ArrayList<RendezVous> rendezVousList = new ArrayList<RendezVous>();
    private ListView listViewRendezVous;

    private ISelectRendezVous listener;

    public void setListener(ISelectRendezVous listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rendezvous, null);

        listViewRendezVous = v.findViewById(R.id.listViewRendezVous);
        listViewRendezVous.setOnItemClickListener(this);

        Button button = v.findViewById(R.id.buttonAddRendezVous);
        button.setOnClickListener(this);

        this.refresh();
        return v;
    }

    // Rafraîchit la liste des rendez-vous en récupérant les données depuis l'API
    public void refresh() {
        ApiService.getAllRendezVous(getActivity(), this);
    }

    @Override
    public void onClick(View v) {
        // Lorsque le bouton d'ajout est cliqué, on appelle onSelectRendezVous avec null pour une nouvelle entrée
        listener.onSelectRendezVous(null);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Lorsque l'utilisateur sélectionne un rendez-vous, on notifie l'élément parent
        listener.onSelectRendezVous(rendezVousList.get(position));
    }

    @Override
    public void onReceiveRendezVous(ArrayList<RendezVous> rendezVous) {
        this.rendezVousList = rendezVous;
        RendezVousAdapter adapter = new RendezVousAdapter(getActivity(), this.rendezVousList);
        listViewRendezVous.setAdapter(adapter);
    }
}
