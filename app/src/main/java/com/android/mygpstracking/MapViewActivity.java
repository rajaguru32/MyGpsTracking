package com.android.mygpstracking;


import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapViewActivity extends AppCompatActivity implements OnMapReadyCallback {

    public MyBroadcastReceiver myReceiver;

    //Keys
    public static final String KEY_TITLE = "title";
    public static final String KEY_THOUGHT = "thought";

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    //Connection to Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //LocationTheard theard;
    private DocumentReference sendlocation = db.collection("UserLocation").document("Location");

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        sendFCMPush();




    }



/*    Runnable locationRunnable =new Runnable() {
        @Override
        public void run() {

        }
    };
    */



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.clear(); // clears our map

        //sendFCMPush(location.getLatitude(), location.getLongitude());
        // mMap.addMarker(new MarkerOptions().position(location.getLatitude(), location.getLongitude(), 1));


        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);

        String longt = prefs.getString("longt", "No name defined");//"No name defined" is the default value.
        String lat = prefs.getString("lat", "no lang"); //0 is the default value.


        //  Log.d("tag", "onLocationChanged:  get location from fcm " + longt + lat);

        double longitude = Double.parseDouble(longt);
        double latitude = Double.parseDouble(lat);

        Log.d("tag", "onLocationChanged:  get location from fcm " + latitude + "  " + longitude);

        // Add a marker in Sydney and move the camera


       // LatLng newLocation = new LatLng(latitude, longitude);
                // prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);

      /*  String longt=" hi";
         longt = prefs.getString("longt", "No name defined");//"No name defined" is the default value.
                String lat = prefs.getString("lat", "no lang"); //0 is the default value.


                //  Log.d("tag", "onLocationChanged:  get location from fcm " + longt + lat);

                double longitude = Double.parseDouble(longt);
                double latitude = Double.parseDouble(lat);*/

                Log.d("tag", "onLocationChanged:  get location from fcm " + longt + "  " + lat);

                // Your code to run in GUI thread here
                LatLng newLocation = new LatLng(latitude, longitude);

                mMap.addMarker(new MarkerOptions().position(newLocation).title("current location"));

                mMap.addMarker(new MarkerOptions().position(newLocation).title("New Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 19));



        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
      //  mMap.setMyLocationEnabled(true);

      /*  locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("My Locations: ", location.toString());
                mMap.clear(); // clears our map

                //sendFCMPush(location.getLatitude(), location.getLongitude());
                // mMap.addMarker(new MarkerOptions().position(location.getLatitude(), location.getLongitude(), 1));

                SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);

                    String longt = prefs.getString("longt", "No name defined");//"No name defined" is the default value.
                    String lat = prefs.getString("lat", "no lang"); //0 is the default value.


              //  Log.d("tag", "onLocationChanged:  get location from fcm " + longt + lat);

                double longitude =Double.parseDouble(longt);
                        double latitude=Double.parseDouble(lat);

                Log.d("tag", "onLocationChanged:  get location from fcm " + latitude +"  "+longitude);

                // Add a marker in Sydney and move the camera
                LatLng newLocation = new LatLng(latitude,longitude );
                mMap.addMarker(new MarkerOptions().position(newLocation).title("New Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 20));

              *//*  Map<String , Object> data=new HashMap<>();
                data.put(KEY_TITLE, location.getLatitude());
                data.put(KEY_THOUGHT,location.getLongitude());


                sendlocation.set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MapViewActivity.this,
                                        "Success", Toast.LENGTH_LONG)
                                        .show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("tag", "onFailure: " + e.toString());
                            }
                        });*//*


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                showGPSDisabledAlertToUser();

            }
        };

        if (Build.VERSION.SDK_INT < 23) {
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }else {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //Ask for permission
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            }else {
                // we have permission!
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
*/

//        LatLng washington = new LatLng(47.614762, -122.476333);
//        mMap.addMarker(new MarkerOptions().position(washington).title("New Location"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(washington));



    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0]
                == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED)

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,  locationListener);

        }

    }


    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

/*
    private void sendFCMPush(double lat , double logt) {

        final String Legacy_SERVER_KEY = "AIzaSyAmkeaiUGOcAe2RcwEB1mRA7xq4Jb5fpUw";
        String msg = "Click to view Location";
        String title = "Location";
        String token = "dRBADu7W5fg:APA91bErJj-L_uy2yXNb-Kc7sFQv8Us6ZsQYwon9CE_MYsCFnOklywjiB1Jsr9QyvH90PLLcDtvoroWZJqcuJt8Ow7tO_ohSJJDzYl9Iv9PjCQ0pUJPLao6Vbcn7bVhnv65VHOfwss0D";

        JSONObject obj = null;
        JSONObject objData = null;
        JSONObject dataobjData = null;

        try {
            obj = new JSONObject();
            objData = new JSONObject();
            objData.put("body", "80.0000");
            objData.put("body", "12.0000");
            objData.put("title", title);
            objData.put("sound", "default");
            objData.put("icon", "icon_name"); //   icon_name image must be there in drawable
            objData.put("tag", token);
            objData.put("priority", "high");

            dataobjData = new JSONObject();
            dataobjData.put("text", msg);
            dataobjData.put("title", title);
            dataobjData.put("lat",lat);
            dataobjData.put("long",logt);

            obj.put("to", token);
            //obj.put("priority", "high");

            obj.put("notification", objData);
            obj.put("data", dataobjData);
            Log.e("!_@rj@_@@_PASS:>", obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }



        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, "https://fcm.googleapis.com/fcm/send", obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("!_@@_SUCESS", response + "");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("!_@@_Errors--", error + "");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "key=" + Legacy_SERVER_KEY);
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        int socketTimeout = 1000 * 60;// 60 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        requestQueue.add(jsObjRequest);
    }*/


    @Override
    public void onResume() {
        super.onResume();
        myReceiver = new MyBroadcastReceiver();
        final IntentFilter intentFilter = new IntentFilter("YourAction");
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (myReceiver != null)
            LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
        myReceiver = null;
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // Here you have the received broadcast
            // And if you added extras to the intent get them here too
            // this needs some null checks
            Bundle b = intent.getExtras();
            String yourValue = b.getString("log");
            Log.d("tag", "onReceive:  broadcast " + yourValue);
            //someTextView.setText(yourValue);
            double someDouble = b.getDouble("doubleName");
            ///do something with someDouble
        }
    }






}

