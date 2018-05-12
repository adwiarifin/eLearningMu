package io.elearningmu.android.muvon.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import io.elearningmu.android.muvon.adapter.UserCourseListAdapter;
import io.elearningmu.android.muvon.model.list.UserCourseList;
import io.elearningmu.android.muvon.util.PrefManager;
import io.elearningmu.android.muvon.util.SingletonRequestQueue;

import static io.elearningmu.android.muvon.util.Config.USER_TAB_COURSE_URL;

public class UserCourseFragment extends Fragment implements UserCourseListAdapter.CourseAdapterOnClickHandler {

    private Context ctx;
    private View view;
    private ProgressBar progressbar;
    private RecyclerView recyclerView;
    private UserCourseListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment_course, container, false);
        ctx = getActivity();

        mAdapter = new UserCourseListAdapter(ctx, this);
        progressbar = view.findViewById(R.id.progressbar);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        getCourseRequest(USER_TAB_COURSE_URL);

        return view;
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
                UserCourseList list = gson.fromJson(response, UserCourseList.class);
                Log.e("COURSE", String.valueOf(list.listCourses.size()));

                mAdapter.setData(list.listCourses);
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
    public void onClick(int courseId) {
        Toast.makeText(ctx, "Course ID: " + courseId, Toast.LENGTH_SHORT).show();
    }
}
