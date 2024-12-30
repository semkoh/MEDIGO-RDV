package com.example.rdv;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FragmentFormRendezVous extends Fragment implements View.OnClickListener {

    private EditText editDate, editMotif;
    private Spinner spinnerStatut, spinnerPatient, spinnerMedecin;
    private Button buttonSave;
    private RendezVous currentRendezVous;

    private ISelectRendezVous listener;

    public void setListener(ISelectRendezVous listener) {
        this.listener = listener;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_formrendezvous, container, false);

        // Initialisation des vues
        editDate = v.findViewById(R.id.editTextDate);  // Utilisation de editDate
        editMotif = v.findViewById(R.id.editTextMotif);
        spinnerStatut = v.findViewById(R.id.spinnerStatut);
        spinnerPatient = v.findViewById(R.id.spinnerPatient);
        spinnerMedecin = v.findViewById(R.id.spinnerMedecin);

        buttonSave = v.findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(this);

        // Initialisation du Spinner des statuts
        ArrayAdapter<CharSequence> adapterStatut = ArrayAdapter.createFromResource(getActivity(),
                R.array.statuts_rendezvous, android.R.layout.simple_spinner_item);
        adapterStatut.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatut.setAdapter(adapterStatut);

        // Initialisation du Spinner des patients (exemple avec une liste fictive)
        ArrayList<Patient> patientsList = new ArrayList<>();
        ArrayAdapter<Patient> adapterPatient = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, patientsList);
        adapterPatient.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPatient.setAdapter(adapterPatient);

        // Initialisation du Spinner des médecins (exemple avec une liste fictive)
        ArrayList<Medecin> medecinsList = new ArrayList<>();
        ArrayAdapter<Medecin> adapterMedecin = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, medecinsList);
        adapterMedecin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMedecin.setAdapter(adapterMedecin);

        return v;
    }

    public void setCurrentRendezVous(RendezVous rendezVous) {
        this.currentRendezVous = rendezVous;
        if (rendezVous != null) {
            // Affichage des données actuelles du rendez-vous
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  // Format de la date simple
            editDate.setText(dateFormat.format(rendezVous.getDate()));  // Utilisation de editDate

            editMotif.setText(rendezVous.getMotif());
            spinnerStatut.setSelection(((ArrayAdapter) spinnerStatut.getAdapter()).getPosition(rendezVous.getStatut()));

            // Sélectionner le patient et le médecin dans les spinners en fonction de leur ID
            for (int i = 0; i < spinnerPatient.getCount(); i++) {
                if (((Patient) spinnerPatient.getItemAtPosition(i)).getId() == rendezVous.getPatientId()) {
                    spinnerPatient.setSelection(i);
                    break;
                }
            }

            for (int i = 0; i < spinnerMedecin.getCount(); i++) {
                if (((Medecin) spinnerMedecin.getItemAtPosition(i)).getId() == rendezVous.getMedecinId()) {
                    spinnerMedecin.setSelection(i);
                    break;
                }
            }
        } else {
            // Réinitialisation des champs si aucun rendez-vous sélectionné
            editDate.setText("");  // Mise à jour du champ de date
            editMotif.setText("");
            spinnerStatut.setSelection(0); // Statut par défaut
            spinnerPatient.setSelection(0); // Patient par défaut
            spinnerMedecin.setSelection(0); // Médecin par défaut
        }
    }

    @Override
    public void onClick(View v) {
        try {
            // Récupérer les valeurs saisies par l'utilisateur
            String dateString = editDate.getText().toString();  // Utilisation de editDate
            String motif = editMotif.getText().toString();
            String statut = spinnerStatut.getSelectedItem().toString();
            int patientId = ((Patient) spinnerPatient.getSelectedItem()).getId(); // Récupérer l'ID du patient sélectionné
            int medecinId = ((Medecin) spinnerMedecin.getSelectedItem()).getId(); // Récupérer l'ID du médecin sélectionné

            // Convertir la date en objet Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  // Format simple de la date
            Date date = dateFormat.parse(dateString);  // Conversion de la chaîne en objet Date

            // Créer ou mettre à jour un rendez-vous
            RendezVous rendezVous = new RendezVous();
            rendezVous.setDate(date);  // Utilisation de setDateHeure
            rendezVous.setMotif(motif);
            rendezVous.setStatut(statut);
            rendezVous.setPatientId(patientId);
            rendezVous.setMedecinId(medecinId);

            if (currentRendezVous == null) {
                // Créer un nouveau rendez-vous via une API ou base de données
                ApiService.createRendezVous(getActivity(), rendezVous, listener);
            } else {
                // Mettre à jour le rendez-vous existant
                rendezVous.setId(currentRendezVous.getId());
                ApiService.updateRendezVous(getActivity(), rendezVous, listener);
            }

        } catch (Exception e) {
            Toast.makeText(getActivity(), "Erreur lors de la création du rendez-vous", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
