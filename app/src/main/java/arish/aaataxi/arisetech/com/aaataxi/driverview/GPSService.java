package arish.aaataxi.arisetech.com.aaataxi.driverview;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import arish.aaataxi.arisetech.com.aaataxi.SharedPrefManager;

/**
 * Created by Arish on 8/30/2016.
 */
public class GPSService extends Service {
    private LocationManager locationManager;
    private LocationListener locationListener;
    private SharedPreferences sharedPreferences;
    private String driveremail;
//Context context;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        driveremail = sharedPreferences.getString(SharedPrefManager.EMAIL_SHARED_PREF, "email");

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double lon = location.getLongitude();
                double lat = location.getLatitude();
                UpdateGps(lat, lon, driveremail);
//                Intent i = new Intent("Location Update");
//                i.putExtra("coordinate", location.getLatitude() + "" + location.getLongitude());
//                sendBroadcast(i);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        };
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, locationListener);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
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
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
                return;
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
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
            locationManager.removeUpdates(locationListener);

        }
    }

    public void UpdateGps(final Double latitude, final Double longitude, final String driveremail) {
        String tag_req_login = "req_login";
//        progressDialog.setMessage("Updating Driver Location Successfully.....");
//        progressDialog.show();
//
        StringRequest streq = new StringRequest(Request.Method.POST, "http://www.aaataxinj.com/admin/includes/android/gps2.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                progressDialog.hide();
                try {
                    JSONObject jObj = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                    //  Log.e("JSON Parser", "Error parsing data " + e.toString());
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_LONG).show();
//                progressDialog.hide();
            }
        }) {
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "location_inserted");
                params.put("latitude", String.valueOf(latitude));
                params.put("longitude", String.valueOf(longitude));
                params.put("driveremail", driveremail);
                // params.put("email",email);
                return params;


            }


        };
        AppController.getInstance().addToRequestQueue(streq, tag_req_login);


    }
}