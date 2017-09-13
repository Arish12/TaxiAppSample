package arish.aaataxi.arisetech.com.aaataxi;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Arish on 7/14/2016.
 */
public class SharedPrefManager {
    public static final String LOGIN_SUCCESS = "success";

    public static final String GCM_KEY ="gcmkey";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "login";

    //This would be used to store the email of current logged in user
    public static final String EMAIL_SHARED_PREF = "email";
    public static final String NAME_SHARED_PREF = "name";
    public static final String DESTINATION_ADDRESS = "dest";
    public static final String PICK_UP_ADDRESS = "pick";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

    public static final String KEY_IS_USER_ADDED = "isuseradded";


   private SharedPreferences pref;

    // Editor for Shared preferences
   private SharedPreferences.Editor editor;

    // Context
   private Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AAATAXI";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String Book_ID = "bookid";
    // Email address (make variable public to access from outside)

   public static final String KEY_USER_ID= "userid";

    // Constructor
    public SharedPrefManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public boolean setLogin(boolean status) {
        editor = pref.edit();
        editor.putBoolean("is_logged_in", status);
        editor.commit();
        return true;
    }

}


