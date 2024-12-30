package com.example.rdv;
public interface ISelectRendezVous {

    // Méthode appelée lors de la sélection d'un rendez-vous
    void onSelectRendezVous(RendezVous rendezVous);

    // Méthode appelée pour valider le formulaire (par exemple, après création ou modification d'un rendez-vous)
    void onValidFormRendezVous();
}