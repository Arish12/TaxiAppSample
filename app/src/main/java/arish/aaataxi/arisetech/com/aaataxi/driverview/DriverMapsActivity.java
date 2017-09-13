package arish.aaataxi.arisetech.com.aaataxi.driverview;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import arish.aaataxi.arisetech.com.aaataxi.LoginActivity;
import arish.aaataxi.arisetech.com.aaataxi.R;
import arish.aaataxi.arisetech.com.aaataxi.SharedPrefManager;

public class DriverMapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener {
    private GoogleMap mMaps;
     Button getList;
    FloatingActionButton fab_power, fab_list;
    private ProgressDialog progressDialog;
    private String driveremail;
//    ToggleButton mode;
    private static String on = "on";
    private static String off = "off";
    private SharedPreferences sharedPreferences;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_maps);

        if (ActivityCompat.checkSelfPermission(DriverMapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(DriverMapsActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //buildAlertMessageNoGps();
            String[] PermissionsLocation =
                    {
                            android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION,

                    };

            int RequestLocationId = 0;
            ActivityCompat.requestPermissions(DriverMapsActivity.this, PermissionsLocation, RequestLocationId);
            //return;
        }



        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
//

        sharedPreferences = getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        driveremail = sharedPreferences.getString(SharedPrefManager.EMAIL_SHARED_PREF, "email");
        fab_power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DriverMapsActivity.this);
                alertDialogBuilder.setMessage("Are you sure you want exit?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                           tokenDestroy(driveremail);
                                //Getting out sharedpreferences

                                //Getting editor
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                //Puting the value false for loggedin
                                editor.putBoolean(SharedPrefManager.LOGGEDIN_SHARED_PREF, false);

                                //Putting blank value to email
                                editor.putString(SharedPrefManager.EMAIL_SHARED_PREF, "");

                                //Saving the sharedpreferences
                                editor.commit();

                                //Starting login activity
                                Intent intent = new Intent(DriverMapsActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });

                //Showing the alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();




            }
        });


        fab_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DriverMapsActivity.this, BookHistory.class);
                startActivity(i);

            }
        });
        Long alertTime = new GregorianCalendar().getTimeInMillis()+5*1000;
        Intent alarmIntent = new Intent(DriverMapsActivity.this, AlarmReceiver.class);
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
       manager.set(AlarmManager.RTC_WAKEUP,alertTime, PendingIntent.getBroadcast(this,1,alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        Toast.makeText(getApplication(), "Successfully Updated ", Toast.LENGTH_SHORT).show();
//        mode = (ToggleButton) findViewById(R.id.toggleButton1);
//
//        mode.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                if (mode.isChecked()) {
//                    driverLocationoff();
//                    Toast.makeText(DriverMapsActivity.this, "You are now Offline", Toast.LENGTH_LONG).show();
//                } else {
//                    driverLocationOn();
//                    Toast.makeText(DriverMapsActivity.this, "You are now Online", Toast.LENGTH_LONG).show();
//                }
//            }
//        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMaps = googleMap;


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMaps.setMyLocationEnabled(true);
        mMaps.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {

                return false;

            }
        });

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 1, (LocationListener) this);
    }

    @Override
    public void onLocationChanged(Location location) {
        double ori_lat = location.getLatitude();
        double ori_lon = location.getLongitude();
        Log.d("lat" + ori_lat, "lon" + ori_lon);
//        lat.setText("" + ori_lat);
//        lon.setText("" + ori_lon);
        mMaps.clear(); //Remove all marker from map before refresh it.
        LatLng latLng = new LatLng(ori_lat, ori_lon);
        LatLng coordinate = new LatLng(ori_lat, ori_lon);
        Marker marker = mMaps.addMarker(new MarkerOptions().position(coordinate).title("Your ")
                .snippet("Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_event)));


        mMaps.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        // Zoom in the Google Map
        mMaps.animateCamera(CameraUpdateFactory.zoomTo(18));


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getBaseContext(), "Gps is turned on!! ",
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
        Toast.makeText(getBaseContext(), "Gps is turned off!! ",
                Toast.LENGTH_SHORT).show();


    }

    //


    public void tokenDestroy(final String driveremail) {
        String tag_req_login = "req_login";
        StringRequest streq = new StringRequest(Request.Method.POST, "http://www.aaataxinj.com/admin/includes/android/logout.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONObject user = jObj.getJSONObject("id");


                } catch (JSONException e) {
                    e.printStackTrace();
                    //  Log.e("JSON Parser", "Error parsing data " + e.toString());
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "location_inserted");
                params.put("email", driveremail);
                // params.put("email",email);
                return params;


            }


        };
        AppController.getInstance().addToRequestQueue(streq, tag_req_login);


    }

    private void driverLocationOn() {

        String tag_req_login = "req_login";

        StringRequest streq = new StringRequest(Request.Method.GET, "http://www.aaataxinj.com/admin/includes/android/onoff.php?driveremail=" + driveremail + "&mode=" + on, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONObject user = jObj.getJSONObject("user");


                } catch (JSONException e) {


                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("RESPONSE ERROR", "Error: " + error.getMessage());
//
//                new MaterialDialog.Builder(DriverMapsActivity.this).title("AAA").content("Unable To Connect To The Server. Please Check Your Internet Connection and Try Again.")
//                        .positiveText("OK").callback(new MaterialDialog.ButtonCallback() {
//                    @Override
//                    public void onPositive(MaterialDialog dialog) {
//
//                    }
//                }).show();

            }
        });


        AppController.getInstance().addToRequestQueue(streq, tag_req_login);
    }

    private void driverLocationoff() {

        String tag_req_login = "req_login";

        StringRequest streq = new StringRequest(Request.Method.GET, "http://www.aaataxinj.com/admin/includes/android/onoff.php?driveremail=" + driveremail + "&mode=" + off, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONObject user = jObj.getJSONObject("user");
//


//                    if (!jObj.getBoolean("error")) {
//                        int id = jObj.getInt("id");
//                            session.addUser(id, name_user, email_user);
//                    }


                } catch (JSONException e) {


                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("RESPONSE ERROR", "Error: " + error.getMessage());

//                new MaterialDialog.Builder(DriverMapsActivity.this).title("AAA").content("Unable To Connect To The Server. Please Check Your Internet Connection and Try Again.")
//                        .positiveText("OK").callback(new MaterialDialog.ButtonCallback() {
//                    @Override
//                    public void onPositive(MaterialDialog dialog) {
//
//                    }
//                }).show();

            }
        });


        AppController.getInstance().addToRequestQueue(streq, tag_req_login);
            }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//      Intent i= new Intent(this, AlarmReceiver.class);
//       PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1000, i, PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        alarmManager.cancel(pendingIntent);
    }


}