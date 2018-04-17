package com.kesatriakeyboard.elearningmu.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.kesatriakeyboard.elearningmu.model.response.SignInResponse;
import com.kesatriakeyboard.elearningmu.util.PrefManager;
import com.kesatriakeyboard.elearningmu.util.SingletonRequestQueue;

import java.util.HashMap;
import java.util.Map;

import static com.kesatriakeyboard.elearningmu.util.Config.CLIENT_ID;
import static com.kesatriakeyboard.elearningmu.util.Config.SIGNIN_URL;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;
    private Button btnLogin;
    private ProgressBar progressBar;
    private View parent_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        parent_view = findViewById(android.R.id.content);

        inputLayoutEmail = findViewById(R.id.input_layout_email);
        inputLayoutPassword = findViewById(R.id.input_layout_password);
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        btnLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressBar);
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
    }

    private void submitForm() {

        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        loginApp();
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else if (inputPassword.getText().length()<5){
            inputLayoutPassword.setError(getString(R.string.inv_msg_password));
            requestFocus(inputPassword);
            return false;
        }else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }

    private void loginApp() {
        Log.d("OAUTH2", "login app");
        RequestQueue queue = SingletonRequestQueue.getInstance(this).getRequestQueue();
        VolleyLog.DEBUG = true;

        progressBar.setVisibility(View.VISIBLE);
        btnLogin.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, SIGNIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //response = response.substring(1,response.length()-1);

                VolleyLog.wtf(response, "utf-8");
                Log.e("OAUTH2", "response: " + response);

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                SignInResponse signinResponse = gson.fromJson(response, SignInResponse.class);

                progressBar.setVisibility(View.GONE);
                btnLogin.setVisibility(View.VISIBLE);
                hideKeyboard();

                if (signinResponse.status) {
                    saveAuth(signinResponse);
                    switchToHome();
                } else {
                    //Toast.makeText(LoginActivity.this, signinResponse.message, Toast.LENGTH_LONG).show();
                    inputEmail.setText("");
                    inputPassword.setText("");
                    requestFocus(inputEmail);
                    Snackbar.make(parent_view, signinResponse.message, Snackbar.LENGTH_LONG).show();
                }
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

                String username = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                String str = "{\n" +
                        "  \"username\": \"" + username + "\",\n" +
                        "  \"password\": \"" + password + "\",\n" +
                        "  \"client_id\": \"" + CLIENT_ID + "\",\n" +
                        "  \"state\": \"@mA!*UkJp\",\n" +
                        "  \"device\": null,\n" +
                        "  \"platform\": null,\n" +
                        "  \"model\": null\n" +
                        "}";
                return str.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");

                return params;
            }
        };

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        queue.add(stringRequest);
    }

    private void saveAuth(SignInResponse signInResponse) {
        Log.d("OAUTH2", "access_token: " + signInResponse.token.accessToken);
        PrefManager prefManager = PrefManager.getInstance(this);
        prefManager.setLoggedIn(true);
        prefManager.setAccessToken(signInResponse.token.accessToken);
    }

    private void switchToHome() {
        Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(homeIntent);
    }
}
