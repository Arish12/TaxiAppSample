package arish.aaataxi.arisetech.com.aaataxi.userview;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import arish.aaataxi.arisetech.com.aaataxi.R;


public class DriverListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView Client_Driver;
    private DriverListadapter DrivermAdapter;
//    String jsonResponse;
//    private static String TAG = MainActivity.class.getSimpleName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public DriverListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DriverListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DriverListFragment newInstance(String param1, String param2) {
        DriverListFragment fragment = new DriverListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        View view = inflater.inflate(R.layout.fragment_driver_list, container, false);
        Client_Driver = (RecyclerView) view.findViewById(R.id.client_driver_detail_list);
        new AsyncFetch().execute();
        return view;
    }
    private class AsyncFetch extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(getActivity());
        HttpURLConnection connn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your json file resides
                // Even you can make call to php file which returns json data
                url = new URL("http://www.aaataxinj.com/admin/includes/android/viewdriverdetails.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {

                // Setup HttpURLConnection class to send and receive data from php and mysql
                connn = (HttpURLConnection) url.openConnection();
                connn.setReadTimeout(READ_TIMEOUT);
                connn.setConnectTimeout(CONNECTION_TIMEOUT);
                connn.setRequestMethod("GET");

                // setDoOutput to true as we recieve data from json file
                connn.setDoOutput(true);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = connn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = connn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                connn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();
            List<Driverlist> listdata = new ArrayList<>();

            pdLoading.dismiss();
            try {

                String jArray = new String(result);
                JSONArray arr = new JSONArray(jArray);

                // Extract data from json and store into ArrayList as class objects
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject json_data = arr.getJSONObject(i);
                    Driverlist driverlist = new Driverlist();
                    //  fishData.fishImage= json_data.getString("fish_img");
                    driverlist.name = json_data.getString("name");
                    driverlist.address = json_data.getString("address");
                    driverlist.email = json_data.getString("email");
                    driverlist.phone = json_data.getString("phone");
                    driverlist.taxino = json_data.getString("taxino");
                    driverlist.taxitype = json_data.getString("taxitype");
                    listdata.add(driverlist);
                }

                // Setup and Handover data to recyclerview

                DrivermAdapter = new DriverListadapter(getActivity(), listdata);
                Client_Driver.setAdapter(DrivermAdapter);
                Client_Driver.setLayoutManager(new LinearLayoutManager(getActivity()));

            } catch (JSONException e) {

            }

        }}

}
