package io.elearningmu.android.muvon.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import io.elearningmu.android.muvon.model.list.ExtendedProfileList;
import io.elearningmu.android.muvon.model.response.ExtendedProfileResponse;
import io.elearningmu.android.muvon.util.PrefManager;
import io.elearningmu.android.muvon.util.SingletonRequestQueue;

import static io.elearningmu.android.muvon.util.Config.USER_EXTENDED_PROFILE_URL;

public class UserProfileFragment extends Fragment {

    private Context ctx;
    private View view;
    private TextView name;
    private TextView location1;
    private TextView location2;
    private TextView bio1;
    private TextView bio2;
    private TextView facebook1;
    private TextView facebook2;
    private TextView twitter1;
    private TextView twitter2;
    private TextView speciality1;
    private TextView speciality2;

    public static UserProfileFragment newInstance() {
        return new UserProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_fragment_profile, container, false);

        ctx = getActivity();

        name = view.findViewById(R.id.userProfileName);
        location1 = view.findViewById(R.id.userProfileLocation1);
        location2 = view.findViewById(R.id.userProfileLocation2);
        bio1 = view.findViewById(R.id.userProfileBio1);
        bio2 = view.findViewById(R.id.userProfileBio2);
        facebook1 = view.findViewById(R.id.userProfileFacebook1);
        facebook2 = view.findViewById(R.id.userProfileFacebook2);
        twitter1 = view.findViewById(R.id.userProfileTwitter1);
        twitter2 = view.findViewById(R.id.userProfileTwitter2);
        speciality1 = view.findViewById(R.id.userProfileSpeciality1);
        speciality2 = view.findViewById(R.id.userProfileSpeciality2);

        getUserProfileRequest(USER_EXTENDED_PROFILE_URL);

        return view;
    }

    private void getUserProfileRequest(String url) {
        RequestQueue queue = SingletonRequestQueue.getInstance(getContext()).getRequestQueue();
        VolleyLog.DEBUG = true;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = "{\"list\": " + response + "}";

                VolleyLog.wtf(response, "utf-8");
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                ExtendedProfileList list = gson.fromJson(response, ExtendedProfileList.class);

                ExtendedProfileResponse base = list.listExtendedProfile.get(0);
                ExtendedProfileResponse social = list.listExtendedProfile.get(1);
                ExtendedProfileResponse instructor = list.listExtendedProfile.get(2);

                name.setText(base.fields.get(0).value);
                location1.setText(base.fields.get(1).value);
                bio1.setText(base.fields.get(2).value);
                location2.setText(base.fields.get(3).value);
                bio2.setText(base.fields.get(4).value);

                facebook1.setText(social.fields.get(0).value);
                twitter1.setText(social.fields.get(1).value);
                facebook2.setText(social.fields.get(2).value);
                twitter2.setText(social.fields.get(3).value);

                speciality1.setText(instructor.fields.get(0).value);
                speciality2.setText(instructor.fields.get(1).value);
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
}
