package io.elearningmu.android.muvon.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.elearningmu.android.muvon.R;
import io.elearningmu.android.muvon.adapter.CourseListAdapter;
import io.elearningmu.android.muvon.model.list.CourseList;
import io.elearningmu.android.muvon.util.SingletonRequestQueue;
import io.elearningmu.android.muvon.view.activity.CourseDetailActivity;

import static io.elearningmu.android.muvon.util.Config.COURSE_URL;
import static io.elearningmu.android.muvon.util.Config.FEATURED_COURSE_URL;
import static io.elearningmu.android.muvon.util.Config.POPULAR_COURSE_URL;

public class HomeCourseFragment extends Fragment implements CourseListAdapter.CourseAdapterOnClickHandler {

    private View view;
    private ProgressBar progressbar;
    private RecyclerView recyclerView;
    private CourseListAdapter mAdapter;

    public static HomeCourseFragment newInstance() {
        return new HomeCourseFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment_course, container, false);

        // activate fragment menu
        setHasOptionsMenu(true);

        mAdapter = new CourseListAdapter(getActivity(), this);
        progressbar = view.findViewById(R.id.progressbar);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        getCourseRequest(COURSE_URL);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_course, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // nothing to do now
        switch (item.getItemId()){
//            case R.id.action_new_feed:
//                Snackbar.make(view, item.getTitle()+" Clicked", Snackbar.LENGTH_SHORT).show();
//                return true;
            case R.id.navigation_home_latest:
                getCourseRequest(COURSE_URL);
                break;
            case R.id.navigation_home_popular:
                getCourseRequest(POPULAR_COURSE_URL);
                break;
            case R.id.navigation_home_featured:
                getCourseRequest(FEATURED_COURSE_URL);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getCourseRequest(String url) {
        RequestQueue queue = SingletonRequestQueue.getInstance(getContext()).getRequestQueue();
        VolleyLog.DEBUG = true;
        progressbar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = "{\"list\": " + response + "}";

                VolleyLog.wtf(response, "utf-8");
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                CourseList list = gson.fromJson(response, CourseList.class);

                mAdapter.setData(list.listCourses);
                //System.out.println(adapter.getItemCount());
                progressbar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Toast.makeText(getActivity(), "No network available", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                }
                progressbar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
        });

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        queue.add(stringRequest);
    }

    @Override
    public void onClick(int courseId) {
        Intent courseDetailIntent = new Intent(getActivity(), CourseDetailActivity.class);
        courseDetailIntent.putExtra(CourseDetailActivity.EXTRA_COURSE_ID, courseId);
        startActivity(courseDetailIntent);
    }
}
