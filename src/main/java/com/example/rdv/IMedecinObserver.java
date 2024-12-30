package com.example.rdv;
import java.util.ArrayList;

public interface IMedecinObserver {
    public void onReceiveMedecins(ArrayList<Medecin> medecins);
}
