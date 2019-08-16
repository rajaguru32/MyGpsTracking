package com.android.mygpstracking.activity;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.mygpstracking.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class MapViewActivity extends AppCompatActivity implements OnMapReadyCallback {

   // public MyBroadcastReceiver myReceiver;

    //Keys
    //Keys
    public static final String KEY_LAT = "lat";
    public static final String KEY_LONG = "log";
    public static final String KEY_Token = "token";


    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    //Connection to Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //LocationTheard theard;

    private DocumentReference locationRef = db.collection("loct-view")
            .document("loct-view-user");

    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        locationRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                Double title = snapshot.getDouble(KEY_LAT);
                Double thought = snapshot.getDouble(KEY_LONG);
                        LatLng currenetLocation = new LatLng(title, thought);
                        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(currenetLocation).title("New Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currenetLocation));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currenetLocation, 20));
                Log.d("taf", "onEvent: started" + title + " "+ thought);
                if (e != null) {
                    Log.w("tag", "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d("", "Current data: " + snapshot.getData());
                } else {
                    Log.d("", "Current data: null");
                }
            }
        });


//        locationRef.get()
//                       .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                            @Override
//                            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                if (documentSnapshot.exists()) {
//
//                                  //  Journal journal = documentSnapshot.toObject(Journal.class);
//                                   Double title = documentSnapshot.getDouble(KEY_LAT);
//                                  Double thought = documentSnapshot.getDouble(KEY_LONG);
//                                    Log.d("tag", "onSuccess: da " + documentSnapshot);
//
////                                    if (journal != null) {
////                                        recTitle.setText(journal.getTitle());
////                                        recThought.setText(journal.getThought());
////                                    }
//                                    Toast.makeText(MapViewActivity.this,
//                                            "No data exists" + title,
//                                            Toast.LENGTH_LONG)
//                                            .show();
//
//
//                                }else {
//                                     Toast.makeText(MapViewActivity.this,
//                                             "No data exists",
//                                             Toast.LENGTH_LONG)
//                                             .show();
//                                }
//
//                            }
//                        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {

                            //  Journal journal = documentSnapshot.toObject(Journal.class);
                            Double title = documentSnapshot.getDouble(KEY_LAT);
                            Double thought = documentSnapshot.getDouble(KEY_LONG);
                            Log.d("tag", "onSuccess: da " + documentSnapshot);

//                                    if (journal != null) {
//                                        recTitle.setText(journal.getTitle());
//                                        recThought.setText(journal.getThought());
//                                    }
                            Toast.makeText(MapViewActivity.this,
                                    "No data exists" + title,
                                    Toast.LENGTH_LONG)
                                    .show();


                        }else {
                            Toast.makeText(MapViewActivity.this,
                                    "No data exists",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }

                    }
                });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.clear(); // clears our map
  /*
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };*/

        mMap.clear(); // clears our map

        //sendFCMPush(location.getLatitude(), location.getLongitude());
        // mMap.addMarker(new MarkerOptions().position(location.getLatitude(), location.getLongitude(), 1));

        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);

        String longt = prefs.getString("longt", "No name defined");//"No name defined" is the default value.
        String lat = prefs.getString("lat", "no lang"); //0 is the default value.



        //  Log.d("tag", "onLocationChanged:  get location from fcm " + longt + lat);

//                double longitude =Double.parseDouble(longt);
//                        double latitude=Double.parseDouble(lat);

        //  Log.d("tag", "onLocationChanged:  get location from fcm " + latitude +"  "+longitude);


        if (Build.VERSION.SDK_INT < 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
            //  locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //Ask for permission
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {
                // we have permission!
                //       locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }


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
                    == PackageManager.PERMISSION_GRANTED) {

                // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,  locationListener);

            }

        }
    }


        private void showGPSDisabledAlertToUser () {
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
/*


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
*/
