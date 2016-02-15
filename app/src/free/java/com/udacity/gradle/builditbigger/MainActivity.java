package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.izodine.jokedisplay.JokeDisplayActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.syncedsoftware.jokeapp.backend.myApi.MyApi;
import java.io.IOException;

public class MainActivity extends ActionBarActivity implements JokeReceiver {

    private static MyApi myApiService = null;
    InterstitialAd mInterstitialAd;

    private void requestNewInterstitial() {

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                requestJoke();
            }
        });

        requestNewInterstitial();

    }

    public void requestJoke(){
        ((MainActivityFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment))
                .getSpinner().setVisibility(View.VISIBLE);
        new EndpointsAsyncTask().execute(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){
        if(mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
        }
        else{
            requestJoke();
        }
    }

    @Override
    public void onReceiveJoke(String jokeString) {
        ((MainActivityFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment))
                .getSpinner().setVisibility(View.VISIBLE);

        Intent intent = new Intent(this, JokeDisplayActivity.class);
        intent.putExtra("EXTRAS_JOKE", jokeString);
        this.startActivity(intent);
    }

    static class EndpointsAsyncTask extends AsyncTask<JokeReceiver, Void, String> {

        private JokeReceiver receiver;

        @Override
        protected String doInBackground(JokeReceiver... params) {
            if (myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://jokebackend-1183.appspot.com/_ah/api/");
                // end options for devappserver

                myApiService = builder.build();
            }

            receiver = params[0];

            try {
                return myApiService.getJoke().execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            receiver.onReceiveJoke(result);
        }
    }

}

