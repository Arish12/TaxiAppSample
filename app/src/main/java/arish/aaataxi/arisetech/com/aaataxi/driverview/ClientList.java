package arish.aaataxi.arisetech.com.aaataxi.driverview;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Arish on 8/16/2016.
 */
public class ClientList {
    public static String[] ids;
    public static String[] emails;
    public static String[] pickupaddress;
    public static String[] dropofaddress;
    public static String[] booktime;
    public static String[] reservedate;
    public static String[] noofpassenger;

    public static final String JSON_ARRAY = "ridelist";
    public static final String KEY_DROPOF= "dropofaddress";
    public static final String KEY_ID= "id";
    public static final String KEY_PICKUP = "pickupaddress";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_BOOK = "booktime";
    public static final String KEY_PASSEN = "noofpassenger";
    public static final String KEY_RESERVE = "reservedate";
private SharedPreferences sharedPreferences;
 Context con;
    private JSONArray users = null;
    private String json;

    public ClientList(String json){
        this.json = json;
    }


    protected void parseJSON() {
        JSONObject jsonObject=null;

        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);
            ids = new String[users.length()];
            emails = new String[users.length()];
            pickupaddress = new String[users.length()];
            dropofaddress= new String[users.length()];
            noofpassenger=new String[users.length()];
            booktime = new String[users.length()];
            reservedate = new String[users.length()];

//            if(users.length()>0) {
                for (int i = 0; i < users.length(); i++) {
                    JSONObject jo = users.getJSONObject(i);
                    ids[i] = jo.getString(KEY_ID);
                    emails[i] = jo.getString(KEY_EMAIL);
                    pickupaddress[i] = jo.getString(KEY_PICKUP);
                    dropofaddress[i] = jo.getString(KEY_DROPOF);
                    noofpassenger[i] = jo.getString(KEY_PASSEN);
                    booktime[i] = jo.getString(KEY_BOOK);
                    reservedate[i] = jo.getString(KEY_RESERVE);

                   
//

                }



//            }
//            else{
//                new MaterialDialog.Builder((con)).title("Booking List").content("Sorry You Don't have any List.")
//                        .positiveText("OK").callback(new MaterialDialog.ButtonCallback() {
//                    @Override
//                    public void onPositive(MaterialDialog dialog) {
//
//                    }
//                }).show();
//            }
        } catch (JSONException e) {


        }

//        pref =con.getSharedPreferences(Session.SHARED_PREF_NAME, 0);
//        pref.edit().putString(Session.ROLE_ID, KEY_ID).commit();
    }


}
