package com.kesatriakeyboard.elearningmu.view.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.kesatriakeyboard.elearningmu.R;
import com.kesatriakeyboard.elearningmu.data.CourseAdapter;
import com.kesatriakeyboard.elearningmu.model.CourseList;
import com.kesatriakeyboard.elearningmu.util.SingletonRequestQueue;

public class CourseFragment extends Fragment {

    private ProgressDialog progressDialog;
    private RecyclerView rvCourse;
    private CourseAdapter adapter;

    public static CourseFragment newInstance() {
        return new CourseFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        String url = getArguments().getString("params");
        getCourseRequest(url);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_course, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Silahkan Tunggu...");

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new CourseAdapter(getActivity());
        rvCourse = rootView.findViewById(R.id.rv_course);
        rvCourse.setLayoutManager(llm);
        rvCourse.setAdapter(adapter);

        return rootView;
    }

    public void getCourseRequest(String url) {
        RequestQueue queue = SingletonRequestQueue.getInstance(getContext()).getRequestQueue();
        VolleyLog.DEBUG = true;
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = "{\"list\": " + response + "}";

                VolleyLog.wtf(response, "utf-8");
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                CourseList list = gson.fromJson(response, CourseList.class);

                adapter.setData(list.listCourseWithHeaderStatus);
                //System.out.println(adapter.getItemCount());
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Toast.makeText(getActivity(), "No network available", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        queue.add(stringRequest);
    }
}
