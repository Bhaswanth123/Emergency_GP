package com.example.aclass.emergency;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main3Activity extends AppCompatActivity {

    Button fire, police, ambulance;
    private static int REQUEST_CALL = 1;
    View v;


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                makePhoneCall(v);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        fire = (Button) findViewById(R.id.fire);
        police = (Button) findViewById(R.id.police);
        ambulance = (Button) findViewById(R.id.ambulance);

        fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall(v);
            }
        });

        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall(v);
            }
        });
        ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall(v);
            }
        });
    }

    private void makePhoneCall(View v) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);

        }
        else {


            // startActivity(new Intent(Intent.ACTION_CALL),Uri.parse("tel:9381423751"));
            switch (v.getId()) {
                case R.id.police:
                    //i1.setData(Uri.parse("tel:9381423751"));
                    startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("tel:9381423751")));
                    break;

                case R.id.ambulance:
                    //i1.setData(Uri.parse("tel:7995470290"));
                    startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("tel:7995470290")));
                    break;

                case R.id.fire:
                    startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("tel:7995470290")));
                    break;
            }
        }

    }
}