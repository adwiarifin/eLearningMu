package com.kesatriakeyboard.elearningmu.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kesatriakeyboard.elearningmu.R;
import com.kesatriakeyboard.elearningmu.model.response.CourseDetailResponse;
import com.kesatriakeyboard.elearningmu.model.response.TokenResponse;
import com.kesatriakeyboard.elearningmu.util.Config;
import com.kesatriakeyboard.elearningmu.util.PrefManager;
import com.kesatriakeyboard.elearningmu.util.SingletonRequestQueue;

import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String AUTHORIZE_URL = "https://www.elearningmu.com/wplmsoauth/authorize";
    private static final String TOKEN_URL = "https://www.elearningmu.com/wplmsoauth/token";
    private static final String SIGNIN_URL = "https://www.elearningmu.com/wp-json/wplms/v1/user/signin";
    private static final String CLIENT_ID = "lAD8KycBnrZQnXT0Schz8Fq";
    private static final String CLIENT_SECRET = "3UDNjD51&M)(AQ@@gFb7#4T7lncwl^$0$PhYQDUR";
    private static final String STATE = "1";
    private static final String CODE = "code";
    private static final String ERROR_CODE = "error_code";
    private static final String REDIRECT_URI = "com.kesatriakeyboard.elearningmu:/oauth2redirect";
    private static final String REDIRECT_URI_ROOT = "com.kesatriakeyboard.elearningmu";

    String code;
    String error;

    private Button btnLogin;
    private Button btnUserId;
    private Button btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*btnLogin = findViewById(R.id.btnLogin);
        btnUserId = findViewById(R.id.btnUserId);
        btnProfile = findViewById(R.id.btnProfile);

        btnLogin.setOnClickListener(this);
        btnUserId.setOnClickListener(this);
        btnProfile.setOnClickListener(this);

        Uri data = getIntent().getData();
        if (data != null && !TextUtils.isEmpty(data.getScheme())) {
            if (REDIRECT_URI_ROOT.equals(data.getScheme())) {
                code  = data.getQueryParameter(CODE);
                error = data.getQueryParameter(ERROR_CODE);
                Log.e("OAUTH2", "onCreate: handle result of authorization with code :" + code);
                if (!TextUtils.isEmpty(code)) {
                    //getTokenFormUrl();
                    Log.e("OAUTH2", "code: " + code);
                    oauthRequestToken();
                }
                if(!TextUtils.isEmpty(error)) {
                    //a problem occurs, the user reject our granting request or something like that
                    Toast.makeText(this, "quit", Toast.LENGTH_LONG).show();
                    Log.e("OAUTH2", "onCreate: handle result of authorization with error :" + error);
                    //then die
                    finish();
                }
            }
        }*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                //oauthAuthorize();
                loginApp();
                break;
            case R.id.btnUserId:
                getUserIdRequest();
                break;
            case R.id.btnProfile:
                PrefManager prefManager = PrefManager.getInstance(this);
                if(!prefManager.isLoggedIn()) {
                    Toast.makeText(this, "Please Login first!!", Toast.LENGTH_SHORT).show();
                } else {
                    getUserProfileRequest();
                }
                break;
        }
    }

    private void loginApp() {
        Log.d("OAUTH2", "login app");
        RequestQueue queue = SingletonRequestQueue.getInstance(this).getRequestQueue();
        VolleyLog.DEBUG = true;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, SIGNIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //response = response.substring(1,response.length()-1);

                VolleyLog.wtf(response, "utf-8");
                Log.e("OAUTH2", "response: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("OAUTH2", error.toString());
                if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "No network available", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = "{\n" +
                        "  \"username\": \"adwiarifin\",\n" +
                        "  \"password\": \"learningpassword123\",\n" +
                        "  \"client_id\": \"364WagFhQc3gxOCzEvyotxg\",\n" +
                        "  \"state\": \"@mA!*UkJp\",\n" +
                        "  \"device\": null,\n" +
                        "  \"platform\": null,\n" +
                        "  \"model\": null\n" +
                        "}";
                return str.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");

                return params;
            }
        };

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        queue.add(stringRequest);
    }

    private void oauthAuthorize() {
        Log.d("OAUTH2", "login invoked");

        HttpUrl authorizeUrl = HttpUrl.parse(AUTHORIZE_URL) //
                .newBuilder() //
                .addQueryParameter("client_id", CLIENT_ID)
                .addQueryParameter("response_type", CODE)
                .addQueryParameter("state", STATE)
                .addQueryParameter("redirect_uri", REDIRECT_URI)
                .build();

        Log.d("OAUTH2", "auth url: " + authorizeUrl.url());

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(String.valueOf(authorizeUrl.url())));
        startActivity(i);
    }

    private void oauthRequestToken() {
        Log.d("OAUTH2", "oauthRequestToken invoked");
        RequestQueue queue = SingletonRequestQueue.getInstance(this).getRequestQueue();
        VolleyLog.DEBUG = true;

        String url = TOKEN_URL;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //response = response.substring(1,response.length()-1);

                VolleyLog.wtf(response, "utf-8");
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                TokenResponse tokenResponse = gson.fromJson(response, TokenResponse.class);

                saveAuth(tokenResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("OAUTH2", error.toString());
                if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "No network available", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("grant_type", "authorization_code");
                params.put("code", code);
                params.put("client_id", CLIENT_ID);
                params.put("client_secret", CLIENT_SECRET);
                params.put("redirect_uri", REDIRECT_URI);

                return params;
            }
        };

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        queue.add(stringRequest);
    }

    private void saveAuth(TokenResponse tokenResponse) {
        Log.d("OAUTH2","access_token: " + tokenResponse.access_token);
        PrefManager prefManager = PrefManager.getInstance(this);
        prefManager.setLoggedIn(true);
        prefManager.setAccessToken(tokenResponse.access_token);
        prefManager.setRefreshToken(tokenResponse.refresh_token);

        getUserIdRequest();
    }

    private void getUserIdRequest() {
        final Context ctx = this;
        Log.d("OAUTH2", "getUserId invoked");
        RequestQueue queue = SingletonRequestQueue.getInstance(this).getRequestQueue();
        VolleyLog.DEBUG = true;

        String url = Config.USER_URL;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //response = response.substring(1,response.length()-1);

                VolleyLog.wtf(response, "utf-8");
                Log.d("OAUTH2", response);

                int userId = Integer.parseInt(response.substring(1, response.length() - 1));

                PrefManager pm = PrefManager.getInstance(ctx);
                pm.setUserId(userId);

                Toast.makeText(ctx, "User ID: " + userId, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("OAUTH2", error.toString());
                if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "No network available", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                PrefManager pm = PrefManager.getInstance(ctx);

                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", pm.getAccessToken());

                Log.d("OAUTH2", params.toString());
                return params;
            }
        };

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        queue.add(stringRequest);
    }

    private void getUserProfileRequest() {
        final Context ctx = this;
        Log.d("OAUTH2", "getUserProfile invoked");
        RequestQueue queue = SingletonRequestQueue.getInstance(this).getRequestQueue();
        VolleyLog.DEBUG = true;

        String url = Config.USER_PROFILE_URL;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //response = response.substring(1,response.length()-1);

                VolleyLog.wtf(response, "utf-8");
                Log.d("OAUTH2", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("OAUTH2", error.toString());
                if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "No network available", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                PrefManager pm = PrefManager.getInstance(ctx);

                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", pm.getAccessToken());

                Log.d("OAUTH2", params.toString());
                return params;
            }
        };

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        queue.add(stringRequest);
    }
}
