package com.example.shubhangi.gpsone;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {
    TextView tvl,test;
    Button btn;
    LocationManager locationManager;
    private static final int MY_PERMISSION_REQUEST_CODE = 7171;
    Location location;
    double latd,lngd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 200, 1000,this);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        tvl=findViewById(R.id.location);
        test=findViewById(R.id.loctest);
        btn=findViewById(R.id.locbuttn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SampleSubcat.class);
                startActivity(intent);
            }
        });
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_CODE);
        }
        else {
            location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location!=null){
                latd=location.getLatitude();
                lngd=location.getLongitude();
                test.setText(latd+"\n"+lngd);
                String cityName = null;
                String locality= null;
                Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
                List<Address> addresses;
                try {
                    addresses=gcd.getFromLocation(latd,lngd,1);
                    if(addresses.size()>0){
                        cityName = addresses.get(0).getLocality();
                        locality=addresses.get(0).getSubLocality();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                tvl.setText(locality+" "+cityName);
                Log.i("loc",latd+" ");
                Log.i("loc",latd+" ");
            }

        }
        if(ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_SETTINGS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_SETTINGS},MY_PERMISSION_REQUEST_CODE);
        }
        else {

        }

    }

    @Override
    public void onLocationChanged(Location location) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        latd=location.getLatitude();
        lngd=location.getLongitude();
        String cityName = null;
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses=gcd.getFromLocation(latd,lngd,1);
            if(addresses.size()>0){
                cityName = addresses.get(0).getLocality();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        tvl.setText(cityName);
        Log.i("loc",latd+" ");
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getlocation();
                    Catview();
                    break;
                }
        }
    }
    public void getlocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        latd=location.getLatitude();
        lngd=location.getLongitude();
        String cityName = null;
        String locality= null;
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses=gcd.getFromLocation(latd,lngd,1);
            if(addresses.size()>0){
                cityName = addresses.get(0).getLocality();
                locality=addresses.get(0).getSubLocality();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        tvl.setText(locality+" "+cityName);
    }
    public void Catview(){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SampleSubcat.class);
                startActivity(intent);
            }
        });
    }

}
