package com.example.andrea.diariostraordinari.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.widget.ImageView;
import com.example.andrea.diariostraordinari.R;

public class Splash extends AppCompatActivity {

    int timeout = 4000;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.logo = (ImageView) findViewById(R.id.enel);
        logo.setImageResource(R.drawable.logo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

               Intent main = new Intent(Splash.this, OperaioActivity2.class);
                startActivity(main);
                finish();
            }
        }, timeout);

    }
}
