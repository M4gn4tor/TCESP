package com.example.rodrigobresan.hackaton.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rodrigobresan.hackaton.R;
import com.example.rodrigobresan.hackaton.models.City;
import com.example.rodrigobresan.hackaton.models.JSONTagsConstants;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Rodrigo Bresan on 26/09/2015.
 */
public class CitySelectActivity extends Activity {

    private Spinner spinnerCities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_city_select);

        Firebase.setAndroidContext(this);

        // Instanciando a base de dados json
        Firebase firebase = new Firebase("https://glowing-inferno-4346.firebaseio.com/");

        // Procurando pelo objeto json "cidades" e adicionando um listener para
        // pegar o json correspondente.
        firebase.child("cidades").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Esse listener procura por alteracoes no banco de dados firebase
                // e assim pegar cada um das cidades registradas.
                List listJson = (ArrayList) dataSnapshot.getValue();
                Map<String, String> textMap = null;
                List<City> listPojo = null;

                // Fazendo cada iteracao para pegar os valores de cada cidade
                Iterator it = listJson.iterator();

                if (it != null) {
                    listPojo = new ArrayList<City>();
                    while (it.hasNext()) {
                        textMap = (Map<String, String>) it.next();

                        // Convertendo o JSON para um bean.
                        City pojo = new City();
                        pojo.setCode(textMap.get(JSONTagsConstants.TAG_CODE));
                        pojo.setName(textMap.get(JSONTagsConstants.TAG_NAME));
                        pojo.setIegm(textMap.get(JSONTagsConstants.TAG_IGPM));
                        pojo.setIeducation(textMap.get(JSONTagsConstants.TAG_EDUCATION));
                        pojo.setIhealth(textMap.get(JSONTagsConstants.TAG_HEALTH));
                        pojo.setIplan(textMap.get(JSONTagsConstants.TAG_PLAN));
                        pojo.setIfisc(textMap.get(JSONTagsConstants.TAG_FISC));
                        pojo.setIenviroment(textMap.get(JSONTagsConstants.TAG_ENV));
                        pojo.setIcity(textMap.get(JSONTagsConstants.TAG_CITY));
                        pojo.setIgov(textMap.get(JSONTagsConstants.TAG_GOV));

                        listPojo.add(pojo);
                    }

                    AutoCompleteTextView autoCompleteCities = (AutoCompleteTextView) findViewById(R.id.completeCities);
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.autocomplete_item, listPojo);

                    autoCompleteCities.setAdapter(adapter);
                    autoCompleteCities.setThreshold(1);

                    autoCompleteCities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            City city = (City) parent.getAdapter().getItem(position);

                            Bundle bundle = new Bundle();
                            bundle.putSerializable("cityData", city);

                            Intent intentIndex = new Intent(CitySelectActivity.this, CityIndexActivity.class);
                            intentIndex.putExtras(bundle);
                            startActivity(intentIndex);
                        }

                    });

                    /*spinnerCities = (Spinner) findViewById(R.id.spinnerCities);


                    spinnerCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });*/
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
}
