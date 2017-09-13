package arish.aaataxi.arisetech.com.aaataxi.driverview;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Arish on 8/21/2016.
 */
public class BookedList {
    public static String[] ids;
    public static String[] emails;
    public static String[] pickupaddress;
    public static String[] dropofaddress;
    public static String[] booktime;
    public static String[] reservedate;
    public static String[] noofpassenger;

    public static final String JSON_ARRAY1 = "ridelist";
    public static final String KEY_DROPOF2= "dropofaddress";
    public static final String KEY_ID3= "id";
    public static final String KEY_PICKUP4 = "pickupaddress";
    public static final String KEY_EMAIL5 = "email";
    public static final String KEY_BOOK6 = "booktime";
    public static final String KEY_PASSEN7 = "noofpassenger";
    public static final String KEY_RESERVE8 = "reservedate";
    Context con;
    private JSONArray users1 = null;

    private String json;

    public BookedList(String json){
        this.json = json;
    }

    protected void parseList() {
        JSONObject jsonObject=null;

        try {
            jsonObject = new JSONObject(json);
            users1 = jsonObject.getJSONArray(JSON_ARRAY1);
            ids = new String[users1.length()];
            emails = new String[users1.length()];
            pickupaddress = new String[users1.length()];
            dropofaddress= new String[users1.length()];
            noofpassenger=new String[users1.length()];
            booktime = new String[users1.length()];
            reservedate = new String[users1.length()];
            if(users1.length()>0) {

                for (int i = 0; i < users1.length(); i++) {
                    JSONObject jo = users1.getJSONObject(i);
                    ids[i] = jo.getString(KEY_ID3);
                    emails[i] = jo.getString(KEY_EMAIL5);
                    pickupaddress[i] = jo.getString(KEY_PICKUP4);
                    dropofaddress[i] = jo.getString(KEY_DROPOF2);
                    noofpassenger[i] = jo.getString(KEY_PASSEN7);
                    booktime[i] = jo.getString(KEY_BOOK6);
                    reservedate[i] = jo.getString(KEY_RESERVE8);

                }
            }
            else{


            }
        } catch (JSONException e) {



        }}
}
