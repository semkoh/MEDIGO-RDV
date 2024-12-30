package com.example.rdv;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class FragmentPatients extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, IPatientObserver {

    private ArrayList<Patient> patients = new ArrayList<>();
    private ListView listViewPatients;

    private ISelectPatient listener;

    private PatientAdapter adapter;

    public void setListener(ISelectPatient listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_patients, null);

        // Initialisation de la ListView
        listViewPatients = v.findViewById(R.id.listViewPatients);
        listViewPatients.setOnItemClickListener(this);

        // Initialisation du bouton pour ajouter un patient
        Button button = v.findViewById(R.id.buttonAddPatients);
        button.setOnClickListener(this);

        // Rafraîchissement de la liste des patients
        this.refresh();
        return v;
    }

    // Rafraîchit la liste des patients
    public void refresh() {
        ApiService.getAllPatients(getActivity(), this);
    }

    // Lorsque le bouton Ajouter un patient est cliqué
    @Override
    public void onClick(View v) {
        listener.onSelectPatient(null);
    }

    // Lorsque un patient de la liste est cliqué
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        listener.onSelectPatient(patients.get(position));
        final Patient selectedPatient = patients.get(position);

    }

    // Méthode de confirmation de suppression

    @Override

    public void onReceivePatients(ArrayList<Patient> patients) {
        this.patients = patients;
        // Ajout du listener dans le constructeur de PatientAdapter
        PatientAdapter adapter = new PatientAdapter(getActivity(), this.patients, listener);
        listViewPatients.setAdapter(adapter);
    }


    // Mise à jour de la liste sans recharger les données depuis l'API
    public void updatePatientsList() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    // Supprime un patient de la liste
    public void deletePatient(Patient patient) {
        patients.remove(patient); // Enlève le patient de la liste locale
        updatePatientsList(); // Rafraîchit la vue
    }


}
