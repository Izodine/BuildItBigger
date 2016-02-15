package com.example.izodine.jokedisplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        String jokeText = getIntent().getExtras().getString("EXTRAS_JOKE");
        if(jokeText != null){
            ((TextView)findViewById(R.id.jokeText)).setText(jokeText);
        }
    }


}
