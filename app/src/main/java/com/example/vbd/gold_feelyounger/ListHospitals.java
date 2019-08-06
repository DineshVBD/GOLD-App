package com.example.vbd.gold_feelyounger;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class ListHospitals extends AppCompatActivity {

    public static java.lang.StringBuffer stringBuffer = new StringBuffer();
    StringBuffer buffer = new StringBuffer();


    private FusedLocationProviderClient fusedLocationProviderClient;
    ListView hospitalListView;
    double latitude, longitude;
    LocationManager locationManager;
    Location location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_hospitals);

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().hide();
        }

        hospitalListView = (ListView) findViewById(R.id.hospitallistview);

        hospitalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listSelection(i);
            }
        });


        this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                try {
                    updateLoc(); //current location
                    loadLocation();

                } catch (IllegalArgumentException e) {
                    Toast.makeText(ListHospitals.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    MainPageActivity.progressDialog.cancel();
                    e.printStackTrace();
                    finish();
                }
            }
        });

      }

    void listSelection(int i) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(true);
        dialog.setTitle("RATING: "+GeometryController.detailArrayList.get(i).getRating()+"/5");
        dialog.setMessage(GeometryController.detailArrayList.get(i).getAddress());
        dialog.setIcon(R.drawable.notification);
        dialog.show();
    }


    public void updateLoc() {

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            throw new IllegalArgumentException("No GPS Available");
        }
        else if (!isGooglePlayServicesAvailable(this)) {
            throw new IllegalArgumentException("No Google Play Services Available");
        }
            else
            getLocation();
    }

    public boolean isGooglePlayServicesAvailable(Context context) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context);
        return resultCode == ConnectionResult.SUCCESS;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    if (location == null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }

                    if(location==null)
                    {
                        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this,
                                new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location1) {
                                       if(location1!=null)
                                       {
                                        location=location1;
                                       }
                                    }
                                });
                    }
                    if (location == null)
                    {
                        throw new IllegalArgumentException("Unable to find location...Please Wait for a few seconds");
                    }

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();


                }
            }
        }
        if(requestCode==2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    if (location == null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                    if(location==null)
                    {
                        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this,
                                new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location1) {
                                        if(location1!=null)
                                        {
                                            location=location1;
                                        }
                                    }
                                });
                    }


                    if (location == null) throw new IllegalArgumentException("Unable to find location...Please Try after a few seconds");

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();


                }
            }
        }
    }


    void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        else {

            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if(location==null)
            {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            if(location==null)
            {
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this,
                        new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location1) {
                                if(location1!=null)
                                {
                                    location=location1;
                                }
                            }
                        });
            }



            if (location == null)
            {
                throw new IllegalArgumentException("Unable to find location...Please Try after a few seconds");
            }

            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
    }

    
    protected void fillList() {

        ArrayList<String> placeName = new ArrayList();
        for (int i = 0; i < GeometryController.detailArrayList.size(); i++){
           // Log.d("data",GeometryController.detailArrayList.get(i).getHospitalName());
            placeName.add(GeometryController.detailArrayList.get(i).getHospitalName());
         }

        ArrayList<String> kmText = new ArrayList();
        for (int i = 0; i < GeometryController.detailArrayList.size(); i++){
            kmText.add(GeometryController.detailArrayList.get(i).getkm()+" KM away");
        }

        ArrayList<String> openNow = new ArrayList<>();
        for (int i = 0; i < GeometryController.detailArrayList.size(); i++){
            openNow.add(GeometryController.detailArrayList.get(i).getOpeningHours());
        }

        CustomPlacesAdapter customPlacesAdapter = new CustomPlacesAdapter(this, placeName, kmText, openNow);
       hospitalListView.setAdapter(customPlacesAdapter);

        MainPageActivity.progressDialog.cancel();
    }

    void loadLocation() {
        try {
            new RetrieveFeedTask().execute();
           // new Thread(new Runnable() {
            //    @Override
             //   public void run() {
               //     Log.d("data1",buffer.toString());

                //}
           // }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

class RetrieveFeedTask extends AsyncTask<StringBuffer, StringBuffer, StringBuffer > {

    @Override
    protected StringBuffer doInBackground(StringBuffer... stringBuffers) {
        try {
            StringBuffer stringBuilder = new StringBuffer()
                    .append("https://maps.googleapis.com/maps/api/place/search/json?rankby=distance&keyword=hospital&location=")
                    .append(latitude)
                    .append(",")
                    .append(longitude)
                    .append("&key=AIzaSyC6-gwhsbRMAbtSNhR56y2EBV9S16bZhHE&sensor=false&libraries=places");

          //  Log.d("data",stringBuilder.toString());
            URL url = new URL(stringBuilder.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String n = "";
            while((n=bufferedReader.readLine())!=null){
                buffer.append(n);
            }

            Log.d("data",buffer.toString());
            ListHospitals.stringBuffer = buffer;
            GeometryController.manipulateData(ListHospitals.stringBuffer,latitude,longitude);
            //fillList();
            return buffer;

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(StringBuffer stringBuffer)
    {
        fillList();
    }


}
}


//AIzaSyC6-gwhsbRMAbtSNhR56y2EBV9S16bZhHE
//AIzaSyCqDpYbzlO3gjJB8IcVOhBfgKSrfZD8Acg