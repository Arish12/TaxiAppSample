package arish.aaataxi.arisetech.com.aaataxi.userview;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import arish.aaataxi.arisetech.com.aaataxi.AppController;

import arish.aaataxi.arisetech.com.aaataxi.LoginActivity;
import arish.aaataxi.arisetech.com.aaataxi.R;
import arish.aaataxi.arisetech.com.aaataxi.SharedPrefManager;
import okhttp3.internal.Util;


public class UserMaps extends Fragment implements OnMapReadyCallback,LocationListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private GoogleMap mMap;
    private TextView disEmail;
    private SharedPreferences sh;
    private GoogleApiClient googleApiClient;
    private Handler handler;
    private Runnable runnable;
    private static final String URL_LOCATION = "http://www.aaataxinj.com/admin/includes/android/throwgps.php";

    public UserMaps() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserMaps.
     */
    // TODO: Rename and change types and number of parameters
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.driver_maps, container, false);
        handler = new Handler();
//        sh = getActivity().getSharedPreferences("login", 0);
//        sharedPreferences = getActivity().getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);
//        driveremail = sharedPreferences.getString(SharedPrefManager.EMAIL_SHARED_PREF, "email");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.driver_map);
        mapFragment.getMapAsync(this);

//        dialog = new ProgressDialog(getActivity());
//        dialog.setCancelable(true);
        return view;
    }


    @Override
    public void onLocationChanged(Location location) {
        double ori_lat = location.getLatitude();
        double ori_lon = location.getLongitude();
        Log.d("lat" + ori_lat, "lon" + ori_lon);
//        lat.setText("" + ori_lat);
//        lon.setText("" + ori_lon);


    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getActivity().getBaseContext(), "Gps is turned on!! ",
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getActivity().getBaseContext(), "Gps is turned off!! ",
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
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
            ActivityCompat.requestPermissions(getActivity(), PermissionsLocation, RequestLocationId);
            //return;

        }

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMap.setMyLocationEnabled(true);
        scheduleThread();
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                return false;
            }
        });
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 1, (LocationListener) this);
    }


    //
    public void fetchData() {
        AQuery aq = new AQuery(getActivity());
        aq.ajax(URL_LOCATION, JSONArray.class, new AjaxCallback<JSONArray>() {
            @Override
            public void callback(String url, JSONArray array, AjaxStatus status) {
                super.callback(url, array, status);
                System.out.print(array);
                Log.i("map:", "list size|" + array);

                parseJsonData(array);
            }


        });

    }

    public void parseJsonData(JSONArray array) {

        ArrayList<MyLocation> list = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject obj = array.getJSONObject(i);
                MyLocation info = new MyLocation();
                info.id = obj.getString("id");
                info.driveremail = obj.getString("driveremail");
                info.latitude = obj.getString("latitude");
                info.longitude = obj.getString("longitude");
                list.add(info);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        populateData(list);

    }


    public void populateData(final ArrayList<MyLocation> list) {
        Log.i("map:", "list size|" + list.size());
        for (int i = 0; i < list.size(); i++) {

            MyLocation info = list.get(i);
            Double lati = Double.parseDouble(info.latitude);
            Double longi = Double.parseDouble(info.longitude);
            LatLng latlng = new LatLng(lati, longi);

            LatLng position = new LatLng(Double.parseDouble(list.get(0).latitude), Double.parseDouble(list.get(0).longitude));
            Marker marker = mMap.addMarker(new MarkerOptions().position(latlng).title(info.getDriveremail())
                    .snippet("Book ME").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_event)));
            marker.setDraggable(true);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(20));
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
//                    bookNowDialog();
                    marker.getId();
                    Intent a = new Intent(getActivity(), LoginActivity.class);
                    a.putExtra("driverEmail", GetDriverEmail(list, marker.getPosition()));
                    startActivity(a);
                }
            });

        }
    }

    public String GetDriverEmail(ArrayList<MyLocation> list, LatLng position) {

        for (int i = 0; i < list.size(); i++) {
            if (String.valueOf(position.latitude).equals(list.get(i).latitude) && String.valueOf(position.longitude).equals(list.get(i).longitude))
                return list.get(i).driveremail;
        }

        return null;
    }
    public void scheduleThread(){
        handler=new Handler();
        runnable=new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
//                if(Util.isConnectingToInternet(MapsActivity.this)){
                    mMap.clear();
                    fetchData();
//                    Toast.makeText(MapsActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(runnable, 30000);
//                }else{
//                    Toast.makeText(MapsActivity.this, "Internet is not active", Toast.LENGTH_SHORT).show();
//                }
            }
        };
        handler.post(runnable);
    }





}
