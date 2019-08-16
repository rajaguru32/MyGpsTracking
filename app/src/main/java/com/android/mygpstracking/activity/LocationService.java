package com.android.mygpstracking.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.mygpstracking.R;

public class LocationService extends AppCompatActivity  implements View.OnClickListener{

    Button mShareLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_service);
        mShareLocation= (Button) findViewById(R.id.location_share);
        mShareLocation.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.location_share :

                shareLocationToWhatapp();

                break;

                default:


        }

    }

    private void shareLocationToWhatapp() {

        Intent sendLocation= new Intent();
        sendLocation.setAction(Intent.ACTION_SEND);
        sendLocation.putExtra(Intent.EXTRA_TEXT, "this is my sample text");
        sendLocation.setType("text/plain");
        sendLocation.setPackage("com.whatsapp");
        startActivity(sendLocation);


    }
}
