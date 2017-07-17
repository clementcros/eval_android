package mobile.beweb.fondespierre.apprenantstest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import mobile.beweb.fondespierre.apprenantstest.Adapter.ListApprenantAdapter;

public class MainActivity extends AppCompatActivity implements ListApprenantAdapter.ListApprenantAdapterOnClickHandler {
    private static final String TAG = "MainActivity";
    JSONArray apprenantsData = null;
    // récupère un tableau JSON
    //get JSON ARRAY
    private RecyclerView mRecyclerView;
    //permet la création de la vue en fonction de la position de la liste pour optimliser la mémoire
    //recycler create your list view all time for optimize memmory
    private ListApprenantAdapter listeApprenantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        // affiche la vue
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_liste_apprenant);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        // crée la liste dans la vue avec la position vertical
        //reate view information in Vertical scroling
        mRecyclerView.setLayoutManager(layoutManager);
        //initialization your layout
        // initialise le recycler dans la layout
        mRecyclerView.setHasFixedSize(true);
        listeApprenantAdapter = new ListApprenantAdapter(this);
        mRecyclerView.setAdapter(listeApprenantAdapter);

        RequestQueue queue = VolleySingleton.getInstance(MainActivity.this).getRequestQueue();
        JsonArrayRequest jsonreq = new JsonArrayRequest("http://900grammes.fr/api",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Data : ", String.valueOf(response.length()));
                        listeApprenantAdapter.setWeatherData(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Spinner la_spinner_promo = (Spinner) findViewById(R.id.la_spinner_promo);
        Spinner la_spinner_session = (Spinner) findViewById(R.id.la_spinner_session);
        Spinner la_spinner_skills = (Spinner) findViewById(R.id.la_spinner_skills);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.La_spinner_promo_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterSes = ArrayAdapter.createFromResource(this,
                R.array.La_spinner_session_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterSkills = ArrayAdapter.createFromResource(this,
                R.array.La_spinner_skills_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        la_spinner_promo.setAdapter(adapter);
        la_spinner_session.setAdapter(adapterSes);
        la_spinner_skills.setAdapter(adapterSkills);
        //this function connect to your api for get information and convert this in JSON ARRAY
        // cette fonciton a pour but de se connecté a l'api pour récupèrer les information en JSON avec le JSONARRAY
        queue.add(jsonreq);
    }
    @Override
    public void onClick(JSONObject apprenantDetails) {
        Intent intent = new Intent(MainActivity.this, DetailapprenantActivity.class);
        intent.putExtra("apprenant",apprenantDetails.toString());
        MainActivity.this.getApplicationContext().startActivity(intent);
    }
    //this funciton make click funciton and get more details
    // cette fonciton permet l'orsque on click sur l'element en quesiton d'initialisé la vue en question pour obtenir le detail

    public class SpinnerExample extends Activity {

        private String[] arraySpinner;

        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            setContentView(R.layout.layout);

            this.arraySpinner = new String[] {
                    "1", "2", "3", "4", "5"
            };

            Spinner s = (Spinner) findViewById(R.id.Spinner01);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, arraySpinner);
            s.setAdapter(adapter);


        }
    }

}


// pseudo code spinner :
/*
afin de faire fonctionner notre spinner il faut lui faire récuperer les valeur que l'on veut filtrer

String spinner = String.valueOf(spinner2.getSelectedItem(TextNom));

de la il récupère les nom

il nous faut intérargir avec le spinner sur l'affichage du textview pour filtrer les resultats



/*