package io.elearningmu.android.muvon.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
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

import java.util.HashMap;
import java.util.Map;

import io.elearningmu.android.muvon.R;
import io.elearningmu.android.muvon.adapter.CurriculumListAdapter;
import io.elearningmu.android.muvon.model.CourseDetail;
import io.elearningmu.android.muvon.model.Instructor;
import io.elearningmu.android.muvon.model.response.CourseDetailResponse;
import io.elearningmu.android.muvon.util.Config;
import io.elearningmu.android.muvon.util.HTMLString;
import io.elearningmu.android.muvon.util.PrefManager;
import io.elearningmu.android.muvon.util.SingletonRequestQueue;

public class CourseDetailActivity extends AppCompatActivity {

    public static final String EXTRA_COURSE_ID = "courseId";

    private Context ctx;

    private RecyclerView rvCurriculum;
    private CurriculumListAdapter curriculumListAdapter;

    private ImageView backdrop;
    private TextView description;

    private ImageView instructorPhoto;
    private RatingBar instructorRating;
    private TextView instructorName;
    private TextView instructorStudentCount;
    private TextView instructorCourseCount;

    private Button buttonTakeCourse;

    private CollapsingToolbarLayout toolbarLayout;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;

        progressDialog = new ProgressDialog(ctx);
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

        curriculumListAdapter = new CurriculumListAdapter(ctx);
        rvCurriculum = findViewById(R.id.recycler_curriculums);
        rvCurriculum.setLayoutManager(new LinearLayoutManager(ctx));
        rvCurriculum.setAdapter(curriculumListAdapter);
        rvCurriculum.setHasFixedSize(true);

        instructorPhoto = findViewById(R.id.instructor_photo);
        instructorRating = findViewById(R.id.instructor_rating);
        instructorName = findViewById(R.id.instructor_name);
        instructorStudentCount = findViewById(R.id.intructor_student_count);
        instructorCourseCount = findViewById(R.id.instructor_course_count);

        buttonTakeCourse = findViewById(R.id.button_take_course);
        buttonTakeCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        PrefManager pm = PrefManager.getInstance(ctx);
        if (pm.isLoggedIn()) {
            buttonTakeCourse.setVisibility(View.VISIBLE);
            int courseId = getIntent().getIntExtra(EXTRA_COURSE_ID, 0);
            getCourseStatus(courseId);
        } else {
            buttonTakeCourse.setVisibility(View.GONE);
        }
    }

    private void getCourseDetailRequest(int courseId) {
        RequestQueue queue = SingletonRequestQueue.getInstance(ctx).getRequestQueue();
        VolleyLog.DEBUG = true;
        progressDialog.show();

        String url = Config.COURSE_URL + "/" + courseId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.substring(1, response.length() - 1);

                VolleyLog.wtf(response, "utf-8");
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                CourseDetailResponse courseResponse = gson.fromJson(response, CourseDetailResponse.class);

                setCourse(courseResponse.data);
                setInstructor(courseResponse.data.instructors.get(0));
                curriculumListAdapter.setCurriculumList(courseResponse.data.curriculum);

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

    private void getCourseStatus(int courseId) {
        RequestQueue queue = SingletonRequestQueue.getInstance(ctx).getRequestQueue();
        VolleyLog.DEBUG = true;

        String url = Config.USER_COURSE_STATUS_URL + "/" + courseId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                buttonTakeCourse.setText("Go To Course");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                buttonTakeCourse.setText("Take Course");
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

    private void postSubscribeCourse(int courseId){
        
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

        Glide.with(ctx)
                .load(detail.course.featuredImage)
                .apply(options)
                .into(backdrop);
    }

    public void setInstructor(Instructor instructor) {
        instructorName.setText(instructor.name);
        instructorStudentCount.setText(instructor.studentCount);
        instructorCourseCount.setText(instructor.courseCount.toString());
        instructorRating.setRating(Float.valueOf(instructor.averageRating));

        RequestOptions options = new RequestOptions()
                .fitCenter();

        Glide.with(ctx)
                .load(instructor.avatar)
                .apply(options)
                .into(instructorPhoto);
    }
}
