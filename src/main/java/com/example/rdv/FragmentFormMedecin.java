package com.example.rdv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentFormMedecin extends Fragment implements View.OnClickListener {

    private EditText editNom, editPrenom, editSpecialite;
    private Button buttonSave;
    private Medecin currentMedecin;
    private ISelectMedecin listener;

    public void setListener(ISelectMedecin listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_formmedecin, container, false); // Assurez-vous de gonfler correctement le layout

        // Assurez-vous que les IDs des éléments sont corrects et qu'ils existent dans le layout XML
        editNom = v.findViewById(R.id.editTextNom);
        editPrenom = v.findViewById(R.id.editTextPrenom);
        editSpecialite = v.findViewById(R.id.editTextSpecialite);
        buttonSave = v.findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(this);

        return v;
    }

    public void setCurrentMedecin(Medecin medecin) {
        this.currentMedecin = medecin;
        if (medecin != null) {
            editNom.setText(medecin.getNom());
            editPrenom.setText(medecin.getPrenom());
            editSpecialite.setText(medecin.getSpecialite());
        } else {
            editNom.setText("");
            editPrenom.setText("");
            editSpecialite.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        Medecin medecin = new Medecin();
        medecin.setNom(editNom.getText().toString());
        medecin.setPrenom(editPrenom.getText().toString());
        medecin.setSpecialite(editSpecialite.getText().toString());

        if (currentMedecin == null) {
            ApiService.createMedecin(getActivity(), medecin, listener);
        } else {
            medecin.setId(currentMedecin.getId());
            ApiService.updateMedecin(getActivity(), medecin, listener);
        }
    }
}
