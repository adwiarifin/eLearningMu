package io.elearningmu.android.muvon.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

import io.elearningmu.android.muvon.R;
import io.elearningmu.android.muvon.adapter.CourseIndexAdapter;
import io.elearningmu.android.muvon.model.response.CourseIndexResponse;
import io.elearningmu.android.muvon.util.Config;
import io.elearningmu.android.muvon.util.PrefManager;
import io.elearningmu.android.muvon.util.SingletonRequestQueue;

public class CourseIndex extends AppCompatActivity implements CourseIndexAdapter.CourseIndexAdapterOnClickHandler {

    public static final String EXTRA_COURSE_ID = "courseIndexId";

    private Context ctx;
    private CourseIndexAdapter mAdapter;
    private ProgressBar progressbar;
    private RecyclerView recyclerView;

    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.activity_course_index);

        mAdapter = new CourseIndexAdapter(ctx, this);
        progressbar = findViewById(R.id.progressbar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getIntent().hasExtra(EXTRA_COURSE_ID)) {
            int courseId = getIntent().getIntExtra(EXTRA_COURSE_ID, 0);
            getCourseIndex(courseId);
        } else {
            throw new IllegalArgumentException("Course Detail activity must receive an integer of ID");
        }
    }

    private void getCourseIndex(int courseId) {
        RequestQueue queue = SingletonRequestQueue.getInstance(ctx).getRequestQueue();
        VolleyLog.DEBUG = true;
        progressbar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        String url = Config.USER_COURSE_STATUS_URL + "/" + courseId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                VolleyLog.wtf(response, "utf-8");
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                CourseIndexResponse cRes = gson.fromJson(response, CourseIndexResponse.class);

                mAdapter.setData(cRes.courseitems);
                progressbar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
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
                recyclerView.setVisibility(View.VISIBLE);
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
    public void onClick(int courseItemId, String courseItemType, String courseItemTitle) {
        int courseId = getIntent().getIntExtra(EXTRA_COURSE_ID, 0);
        if (courseItemType.equals("unit")) {
            Intent courseContentIntent = new Intent(ctx, CourseContent.class);
            courseContentIntent.putExtra(CourseContent.EXTRA_COURSE_ID, courseId);
            courseContentIntent.putExtra(CourseContent.EXTRA_ITEM_ID, courseItemId);
            courseContentIntent.putExtra(CourseContent.EXTRA_ITEM_TITLE, courseItemTitle);
            startActivity(courseContentIntent);
        } else if (courseItemType.equals("quiz")) {
            Intent courseQuizIntent = new Intent(ctx, CourseQuiz.class);
            courseQuizIntent.putExtra(CourseQuiz.EXTRA_COURSE_ID, courseId);
            courseQuizIntent.putExtra(CourseQuiz.EXTRA_ITEM_ID, courseItemId);
            courseQuizIntent.putExtra(CourseQuiz.EXTRA_ITEM_TITLE, courseItemTitle);
            startActivity(courseQuizIntent);
        }
    }
}
