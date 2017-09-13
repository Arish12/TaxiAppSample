package arish.aaataxi.arisetech.com.aaataxi.driverview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import arish.aaataxi.arisetech.com.aaataxi.R;

public class ClientArrayAdap extends ArrayAdapter<String> {
    private String[] ids;
    private String[] emails;
    private String[] pickupaddress;
    private String[] dropofaddress;
    private String[] noofpassenger;
    private String[] booktime;
    private String[] reservedate;
    private Activity context;
    private static final String ACCE = "accept";
    private static final String DECC = "cancel";
    private ProgressDialog mProgressDialog;

    public ClientArrayAdap(Activity context, String[] ids, String[] emails, String[] pickupaddress, String[] dropofaddress, String[] noofpassenger, String[] booktime, String[] reservedate) {
        super(context, R.layout.activity_client_array_adap, ids);
        this.context = context;
        this.ids = ids;
        this.emails = emails;
        this.pickupaddress = pickupaddress;
        this.dropofaddress = dropofaddress;
        this.noofpassenger = noofpassenger;
        this.booktime = booktime;
        this.reservedate = reservedate;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_client_array_adap, null, true);
        TextView textViewEmail = (TextView) listViewItem.findViewById(R.id.textEmail);
        TextView textViewPickUpAdd = (TextView) listViewItem.findViewById(R.id.textPickup);
        TextView textViewDropOf = (TextView) listViewItem.findViewById(R.id.dropof);
        TextView textViewPassen = (TextView) listViewItem.findViewById(R.id.noofpasse);
        TextView textViewBooktime = (TextView) listViewItem.findViewById(R.id.textTime);
        TextView textViewReserveDate = (TextView) listViewItem.findViewById(R.id.textReserveDate);
        Button accept = (Button) listViewItem.findViewById(R.id.requestAccept);
        Button decline = (Button) listViewItem.findViewById(R.id.requestDecline);
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setCancelable(false);
        accept.setTag(ids[position]);
        decline.setTag(ids[position]);
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = v.getTag().toString();
                remove(id);

            }
        });


        accept.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String id = v.getTag().toString();
                send(id);


            }
        });

        textViewEmail.setText(emails[position]);
        textViewPickUpAdd.setText(pickupaddress[position]);
        textViewDropOf.setText(dropofaddress[position]);
        textViewPassen.setText(noofpassenger[position]);
        textViewBooktime.setText(booktime[position]);
        textViewReserveDate.setText(reservedate[position]);
        return listViewItem;
    }


    private void send(String id) {
        String d = "req_login";
        mProgressDialog.setMessage("Accepting.......");
        mProgressDialog.show();

        StringRequest b = new StringRequest(Request.Method.GET,
                "http://www.aaataxinj.com/admin/includes/android/driverfeedback2.php?id="
                        + id + "&status=" + ACCE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                mProgressDialog.hide();
                context.startActivity(new Intent(context,BookHistory.class));
                        context.finish();
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONObject user = jObj.getJSONObject("id");
                    String status = user.getString("status");

//                    }
                } catch (JSONException e) {


                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("RESPONSE ERROR", "Error: " + error.getMessage());
                mProgressDialog.hide();
//                new MaterialDialog.Builder(getContext()).title("AAATAXI").content("Unable To Connect To The Server. Please Check Your Internet Connection and Try Again.")
//                        .positiveText("OK").callback(new MaterialDialog.ButtonCallback() {
//                    @Override
//                    public void onPositive(MaterialDialog dialog) {
//
//                    }
//                }).show();

            }
        }) {
        };
        AppController.getInstance().addToRequestQueue(b, d);
    }

    public void remove(String id) {

        String c = "req_login";
        mProgressDialog.setMessage("Cancelling.......");
        mProgressDialog.show();

        StringRequest a = new StringRequest(
                Request.Method.GET, "http://www.aaataxinj.com/admin/includes/android/driverfeedback2.php?id=" + id + "&status=" + DECC, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgressDialog.hide();
                Toast.makeText(context, "You cancelled the Request", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, DriverMapsActivity.class);
                context.startActivity(i);
                context.finish();
                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONObject user = jObj.getJSONObject("user");
                    String status = user.getString("status");
//

//                    }
                } catch (JSONException e) {

                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("RESPONSE ERROR", "Error: " + error.getMessage());
                mProgressDialog.hide();
//                new MaterialDialog.Builder(getContext()).title("AAATAXI").content("Unable To Connect To The Server. Please Check Your Internet Connection and Try Again.")
//                        .positiveText("OK").callback(new MaterialDialog.ButtonCallback() {
//                    @Override
//                    public void onPositive(MaterialDialog dialog) {
//
//                    }
//                }).show();

            }
        }) {
        };
        AppController.getInstance().addToRequestQueue(a, c);
    }


}