package com.example.rdv;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FragmentFormPatient extends Fragment implements View.OnClickListener {

    private EditText editNom, editPrenom, editEmail, editTel, editDateNaissance;
    private Button buttonSave;
    private Patient currentPatient;

    private ISelectPatient listener;

    public void setListener(ISelectPatient listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate la vue du fragment
        View v = inflater.inflate(R.layout.fragment_formpatient, container, false);

        // Initialisation des EditText
        editNom = v.findViewById(R.id.editTextNom);
        editPrenom = v.findViewById(R.id.editTextPrenom);
        editEmail = v.findViewById(R.id.editTextEmail);
        editTel = v.findViewById(R.id.editTextTel);
        editDateNaissance = v.findViewById(R.id.editTextDateNaissance);

        // Initialisation du bouton de sauvegarde
        buttonSave = v.findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(this);


        return v;
    }

    // Met à jour les champs avec les données du patient
    public void setCurrentPatient(Patient patient) {
        this.currentPatient = patient;
        if (patient != null) {
            // Formatage de la date de naissance
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dateNaissanceStr = (patient.getDateNaissance() != null) ? dateFormat.format(patient.getDateNaissance()) : "";

            // Assurez-vous que tous les champs sont remplis avec les informations du patient
            editNom.setText(patient.getNom());
            editPrenom.setText(patient.getPrenom());
            editEmail.setText(patient.getEmail());
            editTel.setText(patient.getTel());
            editDateNaissance.setText(dateNaissanceStr);
        } else {
            // Si aucun patient n'est sélectionné, videz les champs
            editNom.setText("");
            editPrenom.setText("");
            editEmail.setText("");
            editTel.setText("");
            editDateNaissance.setText("");
        }
    }



    @Override
    public void onClick(View v) {
        Patient patient = new Patient();
        patient.setNom(editNom.getText().toString());
        patient.setPrenom(editPrenom.getText().toString());
        patient.setEmail(editEmail.getText().toString());
        patient.setTel(editTel.getText().toString());
        patient.setTel(editDateNaissance.getText().toString());
        if(currentPatient==null){
            ApiService.createPatient(getActivity(), patient, listener);
        }else{
            patient.setId(currentPatient.getId());
            ApiService.updatePatient(getActivity(), patient, listener);
        }
    }
}
