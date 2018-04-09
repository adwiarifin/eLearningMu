package com.kesatriakeyboard.elearningmu.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kesatriakeyboard.elearningmu.R;
import com.kesatriakeyboard.elearningmu.model.Course;
import com.kesatriakeyboard.elearningmu.model.CourseDetail;
import com.kesatriakeyboard.elearningmu.model.CourseList;
import com.kesatriakeyboard.elearningmu.model.response.CourseDetailResponse;
import com.kesatriakeyboard.elearningmu.model.response.CourseResponse;
import com.kesatriakeyboard.elearningmu.util.Config;
import com.kesatriakeyboard.elearningmu.util.HTMLString;
import com.kesatriakeyboard.elearningmu.util.SingletonRequestQueue;

public class CourseDetailActivity extends AppCompatActivity {

    public static final String EXTRA_COURSE_ID = "courseId";

    private ImageView backdrop;
    private TextView description;

    private CollapsingToolbarLayout toolbarLayout;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Silahkan Tunggu...");

        setContentView(R.layout.activity_course_detail);
        if (getIntent().hasExtra(EXTRA_COURSE_ID)) {
            int courseId = getIntent().getIntExtra(EXTRA_COURSE_ID, 0);
            getCourseDetailRequest(courseId);
        } else {
            throw new IllegalArgumentException("Course Detail activity must receive an integer of ID");
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbarLayout = findViewById(R.id.toolbar_layout);

        backdrop = findViewById(R.id.backdrop);
        description = findViewById(R.id.course_description);
    }

    private void getCourseDetailRequest(int courseId) {
        RequestQueue queue = SingletonRequestQueue.getInstance(this).getRequestQueue();
        VolleyLog.DEBUG = true;
        progressDialog.show();

        String url = Config.COURSE_URL + "/" + courseId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.substring(1,response.length()-1);

                VolleyLog.wtf(response, "utf-8");
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                CourseDetailResponse courseResponse = gson.fromJson(response, CourseDetailResponse.class);

                setCourse(courseResponse.data);

                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "No network available", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        queue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void setCourse(CourseDetail detail) {
        toolbarLayout.setTitle(detail.course.name);

        String strip = HTMLString.stripHTML(detail.description);
        description.setText(strip);

        RequestOptions options = new RequestOptions()
                .fitCenter();

        Glide.with(this)
                .load(detail.course.featuredImage)
                .apply(options)
                .into(backdrop);
    }
}
