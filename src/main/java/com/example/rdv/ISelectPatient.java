package com.example.rdv;

import java.util.ArrayList;

public interface ISelectPatient {
    void onSelectPatient(Patient patient);  // Sélectionner un patient

    void onValidFormPatient();  // Action lorsque le formulaire est validé

   void onEditPatient(Patient patient);  // Action pour éditer un patient

     void onDeletePatient(Patient patient);  // Action pour supprimer un patient

}
