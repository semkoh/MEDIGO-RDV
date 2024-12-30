package com.example.rdv;
import java.util.ArrayList;

public interface IPatientObserver {
    public void onReceivePatients(ArrayList<Patient> patients);
}
