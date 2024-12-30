package com.example.rdv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class PatientAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Patient> patients;
    private ISelectPatient listener;  // Interface pour les actions de sélection, édition, suppression

    public PatientAdapter(Context context, ArrayList<Patient> patients, ISelectPatient listener) {
        this.context = context;
        this.patients = patients;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return patients.size();
    }

    @Override
    public Object getItem(int position) {
        return patients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return patients.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_patient, parent, false);
        }

        // Récupération des éléments de la vue
        TextView textViewName = convertView.findViewById(R.id.textViewPatientNom);
        textViewName.setText(patients.get(position).getNom() + " " + patients.get(position).getPrenom());

        TextView textViewEmail = convertView.findViewById(R.id.textViewPatientEmail);
        textViewEmail.setText(patients.get(position).getEmail());

        TextView textViewTel = convertView.findViewById(R.id.textViewPatientTel);
        textViewTel.setText(patients.get(position).getTel());

        TextView textViewDateNaissance = convertView.findViewById(R.id.textViewPatientDateNaissance);
        textViewDateNaissance.setText(patients.get(position).getDateNaissance().toString());

        // Initialisation des boutons d'édition et de suppression
        Button buttonEdit = convertView.findViewById(R.id.buttonEditPatient);
        Button buttonDelete = convertView.findViewById(R.id.buttonDeletePatient);

        // Configuration des actions des boutons
        buttonEdit.setOnClickListener(v -> listener.onEditPatient(patients.get(position)));
        buttonDelete.setOnClickListener(v -> listener.onDeletePatient(patients.get(position)));

        return convertView;
    }
}
