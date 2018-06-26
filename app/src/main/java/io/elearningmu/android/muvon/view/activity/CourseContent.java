package io.elearningmu.android.muvon.view.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

import io.elearningmu.android.muvon.R;
import io.elearningmu.android.muvon.model.response.CourseContentResponse;
import io.elearningmu.android.muvon.util.Config;
import io.elearningmu.android.muvon.util.HTMLString;
import io.elearningmu.android.muvon.util.PrefManager;
import io.elearningmu.android.muvon.util.SingletonRequestQueue;

public class CourseContent extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_COURSE_ID = "courseIndexId";
    public static final String EXTRA_ITEM_ID = "courseItemId";
    public static final String EXTRA_ITEM_TITLE = "courseItemTitle";

    private Context ctx;
    private ProgressBar progressbar;
    private Button buttonBack;
    private TextView textContent;
    private TextView textTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_content);
        ctx = this;

        String title = getIntent().getStringExtra(EXTRA_ITEM_TITLE);

        progressbar = findViewById(R.id.progressbar);
        buttonBack = findViewById(R.id.btnBack);
        buttonBack.setOnClickListener(this);
        textContent = findViewById(R.id.textContent);
        textTitle = findViewById(R.id.textTitle);
        textTitle.setText(HTMLString.parseHTML(title));

        textContent.setMovementMethod(new ScrollingMovementMethod());

        if (getIntent().hasExtra(EXTRA_COURSE_ID) && getIntent().hasExtra(EXTRA_ITEM_ID)) {
            int courseId = getIntent().getIntExtra(EXTRA_COURSE_ID, 0);
            int itemId = getIntent().getIntExtra(EXTRA_ITEM_ID, 0);
            getCourseContent(courseId, itemId);
        } else {
            throw new IllegalArgumentException("Course Detail activity must receive an integer of ID");
        }
    }

    private void getCourseContent(int courseId, int itemId) {
        RequestQueue queue = SingletonRequestQueue.getInstance(ctx).getRequestQueue();
        VolleyLog.DEBUG = true;
        progressbar.setVisibility(View.VISIBLE);

        String url = Config.USER_COURSE_STATUS_URL + "/" + courseId + "/item/" + itemId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                VolleyLog.wtf(response, "utf-8");
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                CourseContentResponse cRes = gson.fromJson(response, CourseContentResponse.class);

                textContent.setText(HTMLString.parseHTML(cRes.content));

                progressbar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Toast.makeText(ctx, "No network available", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ctx, error.toString(), Toast.LENGTH_LONG).show();
                }
                progressbar.setVisibility(View.GONE);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                PrefManager pm = PrefManager.getInstance(ctx);
                params.put("Authorization", pm.getAccessToken());

                return params;
            }
        };

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        queue.add(stringRequest);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                break;
        }
    }
}
