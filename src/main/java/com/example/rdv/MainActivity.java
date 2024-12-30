package com.example.rdv;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity implements ISelectPatient, ISelectMedecin, ISelectRendezVous {

    private FragmentPatients fragmentPatients;
    private FragmentFormPatient fragmentFormPatient;
    private FragmentMedecins fragmentMedecins;
    private FragmentFormMedecin fragmentFormMedecin;
    private FragmentRendezVous fragmentRendezVous;
    private FragmentFormRendezVous fragmentFormRendezVous;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des fragments
        fragmentPatients = new FragmentPatients();
        fragmentMedecins = new FragmentMedecins();
        fragmentRendezVous = new FragmentRendezVous();
        fragmentFormPatient = new FragmentFormPatient();
        fragmentFormMedecin = new FragmentFormMedecin();
        fragmentFormRendezVous = new FragmentFormRendezVous();

        fragmentPatients.setListener(this);
        fragmentFormPatient.setListener(this);

        // Configuration des boutons
        Button btnPatients = findViewById(R.id.btnPatients);
        Button btnMedecins = findViewById(R.id.btnMedecins);
        Button btnRendezVous = findViewById(R.id.btnRendezVous);

        // Bouton Patients
        btnPatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(fragmentPatients);
            }
        });

        // Bouton Médecins
        btnMedecins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(fragmentMedecins);
            }
        });

        // Bouton Rendez-vous
        btnRendezVous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(fragmentRendezVous);
            }
        });

        // Affiche le fragment des patients par défaut
        getSupportFragmentManager().beginTransaction()
                .add(R.id.framelayout, fragmentPatients)
                .add(R.id.framelayout, fragmentMedecins)
                .add(R.id.framelayout, fragmentRendezVous)
                .add(R.id.framelayout, fragmentFormPatient)
                .add(R.id.framelayout, fragmentFormMedecin)
                .add(R.id.framelayout, fragmentFormRendezVous)
                .hide(fragmentMedecins)
                .hide(fragmentRendezVous)
                .hide(fragmentFormPatient)
                .hide(fragmentFormMedecin)
                .hide(fragmentFormRendezVous)
                .commit();

    }

    // Méthode pour afficher un fragment
    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .hide(fragmentPatients)
                .hide(fragmentMedecins)
                .hide(fragmentRendezVous)
                .hide(fragmentFormPatient)
                .hide(fragmentFormMedecin)
                .hide(fragmentFormRendezVous)
                .commit();
        getSupportFragmentManager().beginTransaction()
                .show(fragment)
                .commit();
    }

    // Gestion de la sélection dans les fragments
    @Override
    public void onSelectPatient(Patient patient) {
        fragmentFormPatient.setCurrentPatient(patient);
        showFragment(fragmentFormPatient);
    }

    @Override
    public void onValidFormPatient() {
        //showFragment(fragmentPatients);
        //showFragment(fragmentFormPatient);
        fragmentPatients.refresh(); // Recharge les données pour afficher les modifications
        showFragment(fragmentPatients); // Retourne à la liste des patients

    }

    @Override
    public void onEditPatient(Patient patient) {
        fragmentFormPatient.setCurrentPatient(patient);
        showFragment(fragmentFormPatient);
    }

    @Override
    public void onDeletePatient(Patient patient) {
        //fragmentFormPatient.setCurrentPatient(patient);
        //showFragment(fragmentFormPatient);
        fragmentPatients.deletePatient(patient); // Supprimer le patient dans le fragmentPatients
        showFragment(fragmentPatients); //

    }

    @Override
    public void onSelectMedecin(Medecin medecin) {
        fragmentFormMedecin.setCurrentMedecin(medecin);
        showFragment(fragmentFormMedecin);
    }

    @Override
    public void onValidFormMedecin() {
        showFragment(fragmentMedecins);
    }

    @Override
    public void onSelectRendezVous(RendezVous rendezVous) {
        fragmentFormRendezVous.setCurrentRendezVous(rendezVous);
        showFragment(fragmentFormRendezVous);
    }

    @Override
    public void onValidFormRendezVous() {
        showFragment(fragmentRendezVous);
    }

    @Override
    public void onBackPressed() {
        if (fragmentFormPatient.isVisible()) {
            showFragment(fragmentPatients);
        } else if (fragmentFormMedecin.isVisible()) {
            showFragment(fragmentMedecins);
        } else if (fragmentFormRendezVous.isVisible()) {
            showFragment(fragmentRendezVous);
        } else {
            super.onBackPressed();
        }
    }
}
