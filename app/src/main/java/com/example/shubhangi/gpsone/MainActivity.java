package com.example.shubhangi.gpsone;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements LocationListener {
    TextView tvl,test;
    Button btn,editbtn;
    LocationManager locationManager;
    private static final int MY_PERMISSION_REQUEST_CODE = 7171;
    Location location;
    EditText ed1;
    double latd,lngd;
    TextView tv;
    String setadr=" ";
    String url="http://app.connect247.co.in:8080/connect247";
    AuthenticationInput auth;
    int SELECT_PICTURE=4;
    public static final MediaType MEDIA_TYPE =
            MediaType.parse("application/json");
    MediaType MEDIA_TYPE_IMAGE=MediaType.parse("image/jpg");
    String imageEncoded;
    List<String> imagesEncodedList;
    private final int PICK_IMAGE_MULTIPLE =1;
    private ArrayList<String> imagesPathList;


    final OkHttpClient client=new OkHttpClient();
    ArrayList<String> ar1=new ArrayList<>();
    Calendar cal=Calendar.getInstance();
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy/mm/dd");
    String str=sdf.format(cal.getTime());
    Date dte=new Date(str);
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 200, 1000,this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        tvl=(TextView)findViewById(R.id.location);
        tv=(TextView)findViewById(R.id.cattxt);
        test=(TextView)findViewById(R.id.loctest);
        btn=(Button)findViewById(R.id.locbuttn);
        editbtn=(Button)findViewById(R.id.set);
        ed1=(EditText)findViewById(R.id.edit1);
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText(ed1.getText().toString());
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ReportActivity.class);
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
                Addhandler ah=new Addhandler();

                String cityName = null;
                String locality= null;
                String addr1=null;
                Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
                List<Address> addresses;
                try {
                    addresses=gcd.getFromLocation(latd,lngd,1);
                    if(addresses.size()>0){
                        cityName = addresses.get(0).getLocality();
                        locality=addresses.get(0).getSubLocality();
                        addr1=addresses.get(0).getAddressLine(0);
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                tvl.setText(locality+" "+cityName);
                ed1.setText(addr1);
                Log.i("loc",latd+" ");
                Log.i("loc",latd+" ");
            }

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
        Addhandler ah=new Addhandler();
        String cityName = null;
        String address=null;
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses=gcd.getFromLocation(latd,lngd,1);
            if(addresses.size()>0){
                cityName = addresses.get(0).getLocality();
                address = addresses.get(0).getAddressLine(0);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        tvl.setText(setadr);
        ed1.setText(address);
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
        Addhandler ah=new Addhandler();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode == PICK_IMAGE_MULTIPLE){
                imagesPathList = new ArrayList<String>();
                String[] imagesPath = data.getStringExtra("data").split("\\|");
                for(int i=0;i<imagesPath.length;i++){
                    imagesPathList.add(imagesPath[i]);
                }
                Toast.makeText(getApplicationContext(),imagesPath[0],Toast.LENGTH_LONG).show();
                }
            }
        }


    private class Addhandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            String locationAddress;
            switch (msg.what){
                case 1:
                    Bundle bundle = msg.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress=null;
            }
            ed1.setText(locationAddress);
        }
    }

}
