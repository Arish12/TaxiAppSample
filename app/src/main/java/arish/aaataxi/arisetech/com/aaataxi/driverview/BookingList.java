package arish.aaataxi.arisetech.com.aaataxi.driverview;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import arish.aaataxi.arisetech.com.aaataxi.R;
import arish.aaataxi.arisetech.com.aaataxi.SharedPrefManager;

public class BookingList extends AppCompatActivity {
    private static final String JSON_URL = "http://www.aaataxinj.com/admin/includes/android/driverbooknowlist.php?driveremail=";
private ProgressDialog progressDialog;
    private TextView got;
    private ListView listView;
  private String driveremail;
     private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list);
        listView = (ListView) findViewById(R.id.listView);
        sharedPreferences = getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        driveremail = sharedPreferences.getString(SharedPrefManager.EMAIL_SHARED_PREF, "email");
        got= (TextView) findViewById(R.id.book_list1);
        progressDialog =new ProgressDialog(this);
        progressDialog.setCancelable(false);
//        got.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
                sendRequest();
//            }
//        });

    }

    private void sendRequest(){
      progressDialog.setMessage("Loading......");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
          JSON_URL+driveremail,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.hide();

                            showJSON(response);

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("RESPONSE ERROR", "Error: " + error.getMessage());
                        progressDialog.hide();
//                        new MaterialDialog.Builder(BookingList.this).title("AAATAXI").content("Unable To Connect To The Server. Please Check Your Internet Connection and Try Again.")
//                                .positiveText("OK").callback(new MaterialDialog.ButtonCallback() {
//                            @Override
//                            public void onPositive(MaterialDialog dialog) {
//
//                            }
//                        }).show();
                        //Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void showJSON(String json) {
        ClientList pj = new ClientList(json);
        pj.parseJSON();
       ClientArrayAdap cl = new ClientArrayAdap(this,ClientList.ids, ClientList.emails,ClientList.pickupaddress,ClientList.dropofaddress,
               ClientList.noofpassenger,ClientList.booktime,ClientList.reservedate);
//        SharedPreferences pref = getSharedPreferences(Session.SHARED_PREF_NAME, 0);
//        pref.edit().putString(Session.ROLE_ID,ClientList.KEY_ID).commit();

        listView.setAdapter(cl);

    }




}
