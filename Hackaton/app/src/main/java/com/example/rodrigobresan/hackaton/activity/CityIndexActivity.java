package com.example.rodrigobresan.hackaton.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rodrigobresan.hackaton.R;
import com.example.rodrigobresan.hackaton.models.City;
import com.example.rodrigobresan.hackaton.models.Votes;
import com.firebase.client.Firebase;
import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CityIndexActivity extends AppCompatActivity {

    private ArrayList<ImageView> arrayImagesBarIndex = new ArrayList<>();
    private City currentCity;
    private Votes votes;
    private boolean alreadyVoted;
    private int currentPositiveVotes, currentNegativeVotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadReceivedCityData();
        fillFields();

        startIndexBarAnimations();
        checkVoteCount();

        final TextView txtLikes = (TextView) findViewById(R.id.txtCountLikes);
        final TextView txtDislikes = (TextView) findViewById(R.id.txtCountDislikes);

        final ImageView btnLike = (ImageView) findViewById(R.id.btnLike);
        final ImageView btnDislike = (ImageView) findViewById(R.id.btnDislike);

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase firebase = new Firebase("https://glowing-inferno-4346.firebaseio.com/cidadevotos/" + currentCity.getCode());

                Votes votes = new Votes();
                votes.setCodCidade(currentCity.getCode());
                votes.setPositiveVotes(currentPositiveVotes + 1);
                firebase.setValue(votes);
                btnDislike.setEnabled(false);
            }
        });

        btnDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase firebase = new Firebase("https://glowing-inferno-4346.firebaseio.com/cidadevotos/" + currentCity.getCode());

                Votes votes = new Votes();
                votes.setCodCidade(currentCity.getCode());
                votes.setNegativeVotes(currentNegativeVotes + 1);
                firebase.setValue(votes);
                btnLike.setEnabled(false);
            }
        });
    }

    private class VoteCheckerAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            URL url = null;
            String body = null;
            try {
                url = new URL("https://glowing-inferno-4346.firebaseio.com/cidadevotos/"+currentCity.getCode()+".json");

                URLConnection con = url.openConnection();
                InputStream in = con.getInputStream();
                String encoding = con.getContentEncoding();
                encoding = encoding == null ? "UTF-8" : encoding;
                body = IOUtils.toString(in, encoding);

                if (body == null) { // hasnt created the vote section
                    Firebase firebase = new Firebase("https://glowing-inferno-4346.firebaseio.com/cidadevotos/" + currentCity.getCode());

                    Votes votes = new Votes();
                    votes.setCodCidade(currentCity.getCode());
                    votes.setPositiveVotes(0);
                    votes.setNegativeVotes(0);

                    firebase.setValue(votes);
                } else {
                    Gson gson = new Gson();
                    Votes votes = (Votes) gson.fromJson(body, Votes.class);

                    currentPositiveVotes = votes.getPositiveVotes();
                    currentNegativeVotes = votes.getNegativeVotes();
                }


            } catch (Exception e) {  // the url doesnt exists

            }

            return null;
        }
    }
    private void checkVoteCount() {
        new VoteCheckerAsync().execute();
    }

    private void loadReceivedCityData() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        currentCity = (City) bundle.getSerializable("cityData");
    }

    private void fillFields() {
        Drawable drawableIEGM = getIndexBarDrawable(currentCity.getIegm());
        Drawable drawableMeioAmbiente = getIndexBarDrawable(currentCity.getIenviroment());
        Drawable drawablePlanejamento = getIndexBarDrawable(currentCity.getIplan());
        Drawable drawableEducacao = getIndexBarDrawable(currentCity.getIeducation());
        Drawable drawableFiscalizacao = getIndexBarDrawable(currentCity.getIfisc());
        Drawable drawableSaude = getIndexBarDrawable(currentCity.getIhealth());
        Drawable drawableGoverno = getIndexBarDrawable(currentCity.getIgov());
        Drawable drawableSeguranca = getIndexBarDrawable(currentCity.getIcity());

        ImageView imgIEGM = (ImageView) findViewById(R.id.imgIGEM);
        imgIEGM.setImageDrawable(drawableIEGM);

        ImageView imgMeioAmbiente = (ImageView) findViewById(R.id.imgIndex1);
        imgMeioAmbiente.setImageDrawable(drawableMeioAmbiente);

        ImageView imgPlanejamento = (ImageView) findViewById(R.id.imgIndex2);
        imgPlanejamento.setImageDrawable(drawablePlanejamento);

        ImageView imgEducacao = (ImageView) findViewById(R.id.imgIndex3);
        imgEducacao.setImageDrawable(drawableEducacao);

        ImageView imgFiscalizacao = (ImageView) findViewById(R.id.imgIndex4);
        imgFiscalizacao.setImageDrawable(drawableFiscalizacao);

        ImageView imgSaude = (ImageView) findViewById(R.id.imgIndex5);
        imgSaude.setImageDrawable(drawableSaude);

        ImageView imgSeguranca = (ImageView) findViewById(R.id.imgIndex6);
        imgSeguranca.setImageDrawable(drawableSeguranca);

        ImageView imgGoverno = (ImageView) findViewById(R.id.imgIndex7);
        imgGoverno.setImageDrawable(drawableGoverno);

        arrayImagesBarIndex.add(imgEducacao);
        arrayImagesBarIndex.add(imgFiscalizacao);
        arrayImagesBarIndex.add(imgGoverno);
        arrayImagesBarIndex.add(imgSeguranca);
        arrayImagesBarIndex.add(imgMeioAmbiente);
        arrayImagesBarIndex.add(imgPlanejamento);
        arrayImagesBarIndex.add(imgSaude);
        arrayImagesBarIndex.add(imgIEGM);

        setTextFieldsValues();

        setTitle(currentCity.getName());
    }

    private void setTextFieldsValues() {
        TextView txtEducacao = (TextView) findViewById(R.id.txtIndiceEducacao);
        TextView txtFiscalizacao = (TextView) findViewById(R.id.txtIndiceFiscalizacao);
        TextView txtGoverno = (TextView) findViewById(R.id.txtIndiceTI);
        TextView txtSeguranca = (TextView) findViewById(R.id.txtIndiceSeguranca);
        TextView txtMeioAmbiente = (TextView) findViewById(R.id.txtIndiceMeioAmbiente);
        TextView txtPlanejamento = (TextView) findViewById(R.id.txtIndicePlanejamento);
        TextView txtSaude = (TextView) findViewById(R.id.txtIndiceSaude);
        TextView txtIEGM = (TextView) findViewById(R.id.txtIndiceIEGM);

        txtEducacao.setText(currentCity.getIeducation());
        txtFiscalizacao.setText(currentCity.getIfisc());
        txtGoverno.setText(currentCity.getIgov());
        txtSeguranca.setText(currentCity.getIcity());
        txtPlanejamento.setText(currentCity.getIplan());
        txtMeioAmbiente.setText(currentCity.getIenviroment());
        txtSaude.setText(currentCity.getIhealth());
        txtIEGM.setText(currentCity.getIegm());
    }

    private Drawable getIndexBarDrawable(String index) {
        Drawable drawable = null;
        switch (index) {
            case "A" :
                drawable =  ContextCompat.getDrawable(getApplicationContext(), R.drawable.seta);
                break;
            case "B+" :
                drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.setabemais);
                break;
            case "B" :
                drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.setab);
                break;
            case "C+" :
                drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.setacmais);
                break;
            case "C" :
                drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.setac);
                break;
        }
        return drawable;
    }

    public void slideToRight(View view, int duration){
        Animation animation = new TranslateAnimation(-300.0f, 0.0f, 0.0f, 0.0f);
        animation.setDuration(duration);
        animation.setFillAfter(true);

        view.startAnimation(animation);
    }


    private void startIndexBarAnimations() {
        for (int currentBar = 0; currentBar < arrayImagesBarIndex.size(); currentBar++) {
            ImageView currentBarImageView = arrayImagesBarIndex.get(currentBar);

            int duration = 500 * (currentBar + 1);
            slideToRight(currentBarImageView, duration);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
