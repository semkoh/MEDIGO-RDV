package com.example.rdv;
import java.util.ArrayList;

public interface IRendezVousObserver {

    public void onReceiveRendezVous(ArrayList<RendezVous>rendez_vous);
}
