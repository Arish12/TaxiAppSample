package arish.aaataxi.arisetech.com.aaataxi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import arish.aaataxi.arisetech.com.aaataxi.AAATAXINOTIFICATION.FireBaseRegistrationIntentService;
import arish.aaataxi.arisetech.com.aaataxi.userview.UserActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {
    @InjectView(R.id.et_username)
    EditText email_login;
    @InjectView(R.id.et_password)
    EditText password_login;
    @InjectView(R.id.btn_login)
    Button login;
    @InjectView(R.id.cv)
    CardView cv;

    @InjectView(R.id.btn_signUp)
    TextView signUp;

    private ProgressDialog mProgressDialog;
    private static final String LOGIN_URL = "http://www.aaataxinj.com/admin/includes/android/login.php";

    private SharedPreferences sharedPreferences;
    private String token;
    private String email;
    private String password;
    private boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        ButterKnife.inject(this);
        ActionBar myActionBar = getSupportActionBar();

        //For hiding android actionbar
        myActionBar.hide();
        token = getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME,0).getString(SharedPrefManager.GCM_KEY,"");
        sharedPreferences = getSharedPreferences(SharedPrefManager.LOGGEDIN_SHARED_PREF, 0);
        loggedIn = sharedPreferences.getBoolean(SharedPrefManager.LOGGEDIN_SHARED_PREF, false);
        if(loggedIn){
            //We will start the Profile Activity
            Intent intent = new Intent(LoginActivity.this,UserActivity.class);
            startActivity(intent);
            finish();
        }
        mProgressDialog = new ProgressDialog(LoginActivity.this);
        mProgressDialog.setCancelable(false);
//        signUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(LoginActivity.this,RegisterForm.class);
//                startActivity(i);
//            }
//        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              email = email_login.getText().toString();
                password = password_login.getText().toString();
//                token = toke.getText().toString();
                // Save the text in SharedPreference

                if (TextUtils.isEmpty(email_login.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please Write Your Email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password_login.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Please Write Your Password", Toast.LENGTH_SHORT).show();

                }
                else {
                    CheckLogin(email,password,token);
                    if(token.length()==0) {
                        startService(new Intent(LoginActivity.this, FireBaseRegistrationIntentService.class));
//
                    } else {

                    }

                }

            }
        });


    }

    private void CheckLogin(final String email, final String password,final String token) {

        String tag_req_login = "req_login";
        mProgressDialog.setMessage("SignIn.......");
        mProgressDialog.show();

        StringRequest streq = new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                mProgressDialog.hide();

                try {
                    JSONObject jObj = new JSONObject(response);
                    JSONObject user = jObj.getJSONObject("user");
                    String name_user = user.getString("name");
                    String email_user = user.getString("email");
                    String role = user.getString("role");


                    Log.d("name" + name_user, "email" + email_user);
//
                    //  msg = "Registration ApplicationConstants :" + regId;

                    if (role.trim().equals("2")) {
                        SharedPreferences.Editor editor = LoginActivity.this.sharedPreferences.edit();
                        editor.putBoolean(SharedPrefManager.SHARED_PREF_NAME, true);
                        editor.putString(SharedPrefManager.EMAIL_SHARED_PREF, email_user);
                        editor.putString(SharedPrefManager.NAME_SHARED_PREF, name_user);
                        editor.commit();
                        Intent client = new Intent(LoginActivity.this, UserActivity.class);
                        startActivity(client);
                        Toast.makeText(getApplicationContext(), "Successful Enjoy Your Ride", Toast.LENGTH_SHORT).show();
                        finish();

                    }  else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {

//                    new MaterialDialog.Builder(LoginPage.this).title("LOGIN Failed").content("Please Check Your Email and password")
//                            .positiveText("OK").callback(new MaterialDialog.ButtonCallback() {
//                        @Override
//                        public void onPositive(MaterialDialog dialog) {
//
//                        }
//                    }).show();
                    String errorMsg ="error_msg";
//                    Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_SHORT).show();

                }}
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("RESPONSE ERROR", "Error: " + error.getMessage());
//                mProgressDialog.hide();
//                new MaterialDialog.Builder(LoginPage.this).title("AAATAXI").
//                        content("Unable To Connect To The Server. Please Check Your Internet Connection and Try Again.")
//                        .positiveText("OK").callback(new MaterialDialog.ButtonCallback() {
//                    @Override
//                    public void onPositive(MaterialDialog dialog) {
//
//                    }
//                }).show();

            }
        }) {
            protected Map<String, String> getParams()throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "login");
                params.put("email",email);
                params.put("password",password);
                params.put("token", token);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(streq, tag_req_login);
    }

//    public void forgotPasswordDialog(){
//        final Dialog d = new Dialog(this);
//        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        d.setContentView(R.layout.activity_forgot_password);
//        d.setCancelable(true);
//        d.show();
//        reset_email = (EditText)d.findViewById(R.id.editText_reset_email);
//        btnResetPassword = (Button)d.findViewById(R.id.resetPassword);
//        btnResetPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = reset_email.getText().toString();
//
//                if (email.trim().length() > 0 ) {
//                    Toast.makeText(getApplicationContext(), "Please Check Your Email", Toast.LENGTH_SHORT).show();
//                    recoverPassword(email);
//                } else {
//                    Toast.makeText(getApplicationContext(), "Please Provide Your Information", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });
//    }
//    private void recoverPassword(final String email) {
//
//        String tag_req_login = "req_login";
//        mProgressDialog.setMessage("Please Wait.......");
//        mProgressDialog.show();
//
//        StringRequest streq = new StringRequest(Request.Method.POST, "http://www.aaataxinj.com/admin/includes/android/forgotpassword.php", new Response.Listener<String>() {
//
//
//            @Override
//            public void onResponse(String response) {
//                mProgressDialog.hide();
//                Toast.makeText(getApplicationContext(),"Please Check Your Email",Toast.LENGTH_LONG).show();
//                try {
//                    JSONObject jObj = new JSONObject(response);
//                    String uid = jObj.getString("uid");
//                    JSONObject user = jObj.getJSONObject("user");
//                    String email = user.getString("email");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    //  Log.e("JSON Parser", "Error parsing data " + e.toString());
//                }
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d("RESPONSE ERROR", "Error: " + error.getMessage());
//                mProgressDialog.hide();
//                new MaterialDialog.Builder(LoginPage.this).title("AAATAXI").content("Unable To Connect To The Server. Please Check Your Internet Connection and Try Again.")
//                        .positiveText("OK").callback(new MaterialDialog.ButtonCallback(){
//                    @Override
//                    public void onPositive(MaterialDialog dialog) {
//
//                    }
//                }).show();
//
//            }
//        }) {
//            protected Map<String, String> getParams() {
//
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("tag", "login");
//                params.put("email", email);
//                return params;
//
//
//            }
//
//
//        };
//        AppController.getInstance().addToRequestQueue(streq, tag_req_login);
//    }
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
    }
}