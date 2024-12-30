package com.example.rdv;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;



public class ApiService {

    private static final String URL_API = "http://192.168.1.189:8081"; // URL de base
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    // Récupérer tous les patients
    public static void getAllPatients(Context context, IPatientObserver observer) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = URL_API + "/patients";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        ArrayList<Patient> patients = new ArrayList<>();
                        JsonArray jsonArray = JsonParser.parseString(response).getAsJsonArray();

                        for (int i = 0; i < jsonArray.size(); i++) {
                            JsonObject object = jsonArray.get(i).getAsJsonObject();
                            Patient patient = new Patient();
                            patient.setId(object.get("id").getAsInt());
                            patient.setNom(object.get("nom").getAsString());
                            patient.setPrenom(object.get("prenom").getAsString());
                            patient.setEmail(object.get("email").getAsString());
                            patient.setTel(object.get("tel").getAsString());
                            String dateNaissance = object.get("dateNaissance").getAsString();
                            patient.setDateNaissance(DATE_FORMAT.parse(dateNaissance));
                            patients.add(patient);
                        }
                        observer.onReceivePatients(patients);
                    } catch (Exception e) {
                        Log.e("ApiService", "Erreur parsing patients", e);
                        Toast.makeText(context, "Erreur de parsing des données", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("ApiService", "Erreur requête patients", error);
                    Toast.makeText(context, "Erreur de récupération des patients", Toast.LENGTH_SHORT).show();
                });

        queue.add(request);
    }

    // Créer un patient
    public static void createPatient(Context context, Patient patient, ISelectPatient listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = URL_API + "/patients";

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("nom", patient.getNom());
            jsonBody.put("prenom", patient.getPrenom());
            jsonBody.put("email", patient.getEmail());
            jsonBody.put("tel", patient.getTel());
            jsonBody.put("dateNaissance", "2000-01-01");

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                    response -> listener.onValidFormPatient(),
                    error -> {
                        Log.e("ApiService", "Erreur création patient", error);
                        Toast.makeText(context, "Erreur lors de la création", Toast.LENGTH_SHORT).show();
                    });

            queue.add(request);
        } catch (JSONException e) {
            Log.e("ApiService", "Erreur création JSON", e);
            Toast.makeText(context, "Erreur interne", Toast.LENGTH_SHORT).show();
        }
    }

    // Mettre à jour un patient
    public static void updatePatient(final Context context, final Patient patient, ISelectPatient listener) {
        RequestQueue queue = Volley.newRequestQueue(context);

        String url = URL_API + "/patients";

        try {
            JSONObject jsonParams = new JSONObject();
            jsonParams.put("nom", patient.getNom());
            jsonParams.put("prenom", patient.getPrenom());
            jsonParams.put("email", patient.getEmail());
            jsonParams.put("tel", patient.getTel());
            jsonParams.put("dateNaissance", DATE_FORMAT.format(patient.getDateNaissance())); // Assurez-vous que le format est correct

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.PUT, URL_API + "/patients/" + patient.getId(),
                    jsonParams,
                    response -> listener.onValidFormPatient(),
                    error -> Log.e("APIError", "Erreur lors de la mise à jour du patient", error)
            );

            queue.add(request);
        } catch (JSONException ex) {
            Log.e("JSONException", "Erreur lors de la création de l'objet JSON", ex);
        }
    }

    public static void deletePatient(final Context context, int patientId, ISelectPatient listener) {
        RequestQueue queue = Volley.newRequestQueue(context);

        String url = URL_API + "/patients/" + patientId; // URL pour supprimer un patient

        // Création de la requête DELETE
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE, url, null,
                response -> {
                    // Appel du callback en cas de succès
                    listener.onValidFormPatient();
                },
                error -> {
                    // Log de l'erreur en cas d'échec
                    Log.e("APIError", "Erreur lors de la suppression du patient", error);
                }
        );

        // Ajout de la requête à la queue
        queue.add(request);
    }


    // Récupérer tous les médecins
    public static void getAllMedecins(Context context, IMedecinObserver observer) {
        RequestQueue queue = Volley.newRequestQueue(context);

        String url = URL_API + "/medecins";

        StringRequest request = new StringRequest(Request.Method.GET, URL_API + "/medecins",
                response -> {
                    try {
                        ArrayList<Medecin> medecins = new ArrayList<>();
                        JsonArray jsonArray = JsonParser.parseString(response).getAsJsonArray();
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JsonObject object = jsonArray.get(i).getAsJsonObject();
                            Medecin medecin = new Medecin();
                            medecin.setId(object.get("id").getAsInt());
                            medecin.setNom(object.get("nom").getAsString());
                            medecin.setPrenom(object.get("prenom").getAsString());
                            medecin.setSpecialite(object.get("specialite").getAsString());
                            medecins.add(medecin);
                        }
                        observer.onReceiveMedecins(medecins);
                    } catch (Exception e) {
                        Log.e("ApiService", "Erreur parsing médecins", e);
                        Toast.makeText(context, "Erreur de récupération des médecins", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("ApiService", "Erreur requête médecins", error);
                    Toast.makeText(context, "Erreur de récupération des médecins", Toast.LENGTH_SHORT).show();
                });

        queue.add(request);
    }

    // Créer un médecin
    public static void createMedecin(Context context, Medecin medecin, ISelectMedecin listener) {
        RequestQueue queue = Volley.newRequestQueue(context);

        String url = URL_API + "/medecins";


        try {
            JSONObject jsonParams = new JSONObject();
            jsonParams.put("nom", medecin.getNom());
            jsonParams.put("prenom", medecin.getPrenom());
            jsonParams.put("specialite", medecin.getSpecialite());

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL_API + "/medecins",
                    jsonParams,
                    response -> listener.onValidFormMedecin(),
                    error -> {
                        Log.e("APIError", error.toString());
                        Toast.makeText(context, "Erreur lors de la création du médecin", Toast.LENGTH_SHORT).show();
                    });

            queue.add(request);
        } catch (JSONException ex) {
            Log.e("JSONException", "Erreur lors de la création de l'objet JSON", ex);
            Toast.makeText(context, "Erreur lors de la création de l'objet JSON", Toast.LENGTH_SHORT).show();
        }
    }

    // Mettre à jour un médecin
    public static void updateMedecin(final Context context, final Medecin medecin, ISelectMedecin listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = URL_API + "/medecins";


        try {
            JSONObject jsonParams = new JSONObject();
            jsonParams.put("nom", medecin.getNom());
            jsonParams.put("prenom", medecin.getPrenom());
            jsonParams.put("specialite", medecin.getSpecialite());

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, URL_API + "/medecins/" + medecin.getId(),
                    jsonParams,
                    response -> listener.onValidFormMedecin(),
                    error -> {
                        Log.e("APIError", error.toString());
                        Toast.makeText(context, "Erreur lors de la mise à jour du médecin", Toast.LENGTH_SHORT).show();
                    });

            queue.add(request);
        } catch (JSONException ex) {
            Log.e("JSONException", "Erreur lors de la création de l'objet JSON", ex);
            Toast.makeText(context, "Erreur lors de la création de l'objet JSON", Toast.LENGTH_SHORT).show();
        }
    }

    // Récupérer tous les rendez-vous

    public static void getAllRendezVous(Context context, IRendezVousObserver observer) {
        RequestQueue queue = Volley.newRequestQueue(context);

        String url = URL_API + "/rendez_vous";

        StringRequest request = new StringRequest(Request.Method.GET, URL_API + "/rendez_vous",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Liste pour stocker les rendez-vous
                            ArrayList<RendezVous> rendezVousList = new ArrayList<>();
                            // Analyse de la réponse JSON
                            JsonArray jsonArray = JsonParser.parseString(response).getAsJsonArray();

                            // Format de la date
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                            // Parcours du tableau JSON
                            for (int i = 0; i < jsonArray.size(); i++) {
                                JsonObject object = jsonArray.get(i).getAsJsonObject();
                                RendezVous rendezVous = new RendezVous();

                                // Vérification de l'existence des clés avant d'extraire les données
                                if (object.has("id") && object.has("date") && object.has("motif") &&
                                        object.has("statut") && object.has("patient") && object.has("medecin")) {

                                    // Extraction des données
                                    rendezVous.setId(object.get("id").getAsInt());

                                    // Conversion de la date
                                    String dateString = object.get("date").getAsString();
                                    try {
                                        Date date = dateFormat.parse(dateString);
                                        rendezVous.setDate(date);
                                    } catch (ParseException e) {
                                        Log.e("ApiService", "Erreur de conversion de la date", e);
                                        rendezVous.setDate(null); // Si la conversion échoue
                                    }

                                    rendezVous.setMotif(object.get("motif").getAsString());
                                    rendezVous.setStatut(object.get("statut").getAsString());
                                    rendezVous.setPatientId(object.get("patient").getAsJsonObject().get("id").getAsInt());
                                    rendezVous.setMedecinId(object.get("medecin").getAsJsonObject().get("id").getAsInt());

                                    // Ajout du rendez-vous à la liste
                                    rendezVousList.add(rendezVous);
                                } else {
                                    Log.w("ApiService", "Clés manquantes dans le rendez-vous : " + object);
                                }
                            }

                            // Appel de l'observer avec la liste des rendez-vous
                            observer.onReceiveRendezVous(rendezVousList);

                        } catch (Exception e) {
                            // Gestion des erreurs de parsing
                            Log.e("ApiService", "Erreur parsing rendez-vous", e);
                            Toast.makeText(context, "Erreur de récupération des rendez-vous", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Gestion des erreurs de la requête Volley
                        Log.e("ApiService", "Erreur requête rendez-vous", error);
                        Toast.makeText(context, "Erreur de récupération des rendez-vous", Toast.LENGTH_SHORT).show();
                    }
                });

        // Ajout de la requête à la file d'attente Volley
        queue.add(request);
    }



    // Créer un rendez-vous
    public static void createRendezVous(Context context, RendezVous rendezVous, ISelectRendezVous listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = URL_API + "/rendez_vous";


        try {
            JSONObject jsonParams = new JSONObject();
            jsonParams.put("date", rendezVous.getDate());
            jsonParams.put("motif", rendezVous.getMotif());
            jsonParams.put("statut", rendezVous.getStatut());
            jsonParams.put("patient_id", rendezVous.getPatientId());
            jsonParams.put("medecin_id", rendezVous.getMedecinId());

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL_API + "/rendez_vous",
                    jsonParams,
                    response -> listener.onValidFormRendezVous(),
                    error -> {
                        Log.e("APIError", error.toString());
                        Toast.makeText(context, "Erreur lors de la création du rendez-vous", Toast.LENGTH_SHORT).show();
                    });

            queue.add(request);
        } catch (JSONException ex) {
            Log.e("JSONException", "Erreur lors de la création de l'objet JSON", ex);
            Toast.makeText(context, "Erreur lors de la création de l'objet JSON", Toast.LENGTH_SHORT).show();
        }
    }
    // Mettre à jour un rendez-vous
    public static void updateRendezVous(final Context context, final RendezVous rendezVous, ISelectRendezVous listener) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = URL_API + "/rendez_vous";


        try {
            // Créer un objet JSON avec les paramètres du rendez-vous
            JSONObject jsonParams = new JSONObject();
            jsonParams.put("date", rendezVous.getDate());
            jsonParams.put("motif", rendezVous.getMotif());
            jsonParams.put("statut", rendezVous.getStatut());
            jsonParams.put("patient_id", rendezVous.getPatientId());
            jsonParams.put("medecin_id", rendezVous.getMedecinId());

            // Construire la requête PUT
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, URL_API + "/rendez_vous/" + rendezVous.getId(),
                    jsonParams,
                    response -> {
                        // Appeler le callback en cas de succès
                        listener.onValidFormRendezVous();
                    },
                    error -> {
                        Log.e("APIError", error.toString());
                        Toast.makeText(context, "Erreur lors de la mise à jour du rendez-vous", Toast.LENGTH_SHORT).show();
                    });

            // Ajouter la requête à la file d'attente
            queue.add(request);
        } catch (JSONException ex) {
            Log.e("JSONException", "Erreur lors de la création de l'objet JSON", ex);
            Toast.makeText(context, "Erreur lors de la création de l'objet JSON", Toast.LENGTH_SHORT).show();
        }
    }


}
