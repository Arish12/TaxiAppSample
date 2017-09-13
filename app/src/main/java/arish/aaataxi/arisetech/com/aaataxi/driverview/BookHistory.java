package arish.aaataxi.arisetech.com.aaataxi.driverview;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import arish.aaataxi.arisetech.com.aaataxi.R;
import arish.aaataxi.arisetech.com.aaataxi.SharedPrefManager;

public class BookHistory extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private String driveremail;
    private ListView listView1;
    private static String JSON_LIST="http://www.aaataxinj.com/admin/includes/android/driverhistorylist.php?driveremail=";
    private ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_history);
        sharedPreferences = getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        driveremail = sharedPreferences.getString(SharedPrefManager.EMAIL_SHARED_PREF, "email");
        listView1 = (ListView) findViewById(R.id.listView1);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        bookHistory();
    }
        private void bookHistory(){
        progressDialog.setMessage("Loading......");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                JSON_LIST+driveremail,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {showHistory(response);
                        progressDialog.hide();


                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("RESPONSE ERROR", "Error: " + error.getMessage());
                        progressDialog.hide();
//                        new MaterialDialog.Builder(BookHistory.this).title("AAATAXI").content("Unable To Connect To The Server. Please Check Your Internet Connection and Try Again.")
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

    private void showHistory(String json) {
        BookedList pj = new BookedList(json);
        pj.parseList();
        HistoryArray cl = new HistoryArray(this, BookedList.ids, BookedList.emails, BookedList.pickupaddress, BookedList.dropofaddress,
                BookedList.noofpassenger, BookedList.booktime, BookedList.reservedate);
        listView1.setAdapter(cl);
    }

}
