package com.example.aclass.emergency;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;


public class Main2Activity extends AppCompatActivity {

    Button fire, police, ambulance;
    String Name;
    String destination;
    View v;
    Switch Location_Switch;
    private LocationManager locationManager;
    private LocationListener locationListener;
    String map_url;
    String message;
    final int SEND_SMS_PERMISSION_REQUEST_CODE=1;

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    configureSwitch();
            case 1:
                if(grantResults.length>0&& grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    makePhoneCall(v);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        fire = (Button) findViewById(R.id.fire);
        police = (Button) findViewById(R.id.police);
        ambulance = (Button) findViewById(R.id.ambulance);
        Intent i = getIntent();
        destination = i.getStringExtra("Family");
        Name = i.getStringExtra("Name");
        Location_Switch = (Switch) findViewById(R.id.location);

        fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall(view);

            }
        });
        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall(view);

            }
        });
        ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall(view);
            }
        });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Toast.makeText(Main2Activity.this, "Latitude:"+location.getLatitude()+"\nLongitude:"+location.getLongitude(), Toast.LENGTH_SHORT).show();
                map_url="http://maps.google.com/?q="+location.getLatitude()+","+location.getLongitude();
                sendMessage(map_url);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.INTERNET
                },10);
            }
            else {
                configureSwitch();
            }
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 3, locationListener);

    }

    private boolean checkPermission(String permission) {
        int check= ContextCompat.checkSelfPermission(this,permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }



    private void makePhoneCall(View v) {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},1);

        }
        else {
            switch (v.getId()) {
                case R.id.police:
                    startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("tel:9381423751")));
                    break;

                case R.id.ambulance:
                    startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("tel:7995470290")));
                    break;

                case R.id.fire:
                    startActivity(new Intent(Intent.ACTION_CALL,Uri.parse("tel:7995470290")));
                    break;
            }
        }
    }
    private void configureSwitch()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 3, locationListener);
        }
        else
            Toast.makeText(this, "Location Permission not granted", Toast.LENGTH_SHORT).show();
    }
    private void sendMessage(String map_url)
    {
        if(checkPermission(Manifest.permission.SEND_SMS)==false)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},SEND_SMS_PERMISSION_REQUEST_CODE);
        }
        else
        {
            if(checkPermission(Manifest.permission.SEND_SMS))
            {
                SmsManager smsManager=SmsManager.getDefault();
                message="Your family member "+Name+" is in danger to know the exact location.Click the google maps link "+map_url;



                smsManager.sendTextMessage(destination,null,message,null,null);
                Toast.makeText(this, "Message Sent!", Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(this, "Message Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}