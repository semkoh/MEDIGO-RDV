package com.example.rdv;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class FragmentMedecins extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, IMedecinObserver {

    private ArrayList<Medecin> medecins = new ArrayList<>();
    private ListView listViewMedecins;

    private ISelectMedecin listener;

    public void setListener(ISelectMedecin listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_medecins, null);

        // Initialiser la ListView pour les médecins
        listViewMedecins = v.findViewById(R.id.listViewMedecins);
        listViewMedecins.setOnItemClickListener(this);

        // Bouton pour ajouter un médecin
        Button button = v.findViewById(R.id.buttonAddMedecins);
        button.setOnClickListener(this);

        // Actualiser la liste des médecins
        this.refresh();
        return v;
    }

    // Méthode pour actualiser la liste des médecins
    public void refresh() {
        ApiService.getAllMedecins(getActivity(), this); // Appel à l'API pour récupérer tous les médecins
    }

    // Action au clic sur le bouton
    @Override
    public void onClick(View v) {
        listener.onSelectMedecin(null); // Lorsque l'on clique sur le bouton "Ajouter", on passe un médecin null
    }

    // Action lorsque l'on clique sur un élément de la liste
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        listener.onSelectMedecin(medecins.get(position)); // Sélectionner le médecin correspondant
    }

    // Méthode pour recevoir la liste des médecins depuis l'API
    @Override
    public void onReceiveMedecins(ArrayList<Medecin> medecins) {
        this.medecins = medecins;
        MedecinAdapter adapter = new MedecinAdapter(getActivity(), this.medecins); // Créer l'adaptateur pour la ListView
        listViewMedecins.setAdapter(adapter);
    }
}
