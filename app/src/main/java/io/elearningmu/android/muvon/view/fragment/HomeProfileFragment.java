package io.elearningmu.android.muvon.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import io.elearningmu.android.muvon.R;
import io.elearningmu.android.muvon.util.PrefManager;
import io.elearningmu.android.muvon.view.activity.LoginActivity;
import io.elearningmu.android.muvon.view.activity.UserActivity;

public class HomeProfileFragment extends Fragment {

    View view;

    private TextView textName;
    private TextView textHint;
    private TextView textLogout;
    private ImageView imagePhoto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment_profile, container, false);

        textName = view.findViewById(R.id.textName);
        textHint = view.findViewById(R.id.textHint);
        textLogout = view.findViewById(R.id.textLogout);
        imagePhoto = view.findViewById(R.id.imagePhoto);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        PrefManager pm = PrefManager.getInstance(getActivity());
        if (pm.isLoggedIn()) {
            Log.d("PROFILE", pm.getAccessToken());
            textName.setText(pm.getUserName());
            textHint.setText("View Your Profile");
            textLogout.setText("Logout");

            RequestOptions options = new RequestOptions()
                    .fitCenter();

            Glide.with(getActivity())
                    .load(pm.getUserAvatar())
                    .apply(options)
                    .into(imagePhoto);
        } else {
            textName.setText("Guest");
            textHint.setText("Please login to view your profile");
            textLogout.setText("Login");
            imagePhoto.setImageResource(R.drawable.ic_people);
        }
    }

    public void actionClick(View view) {
        PrefManager pm = PrefManager.getInstance(getActivity());

        switch (view.getId()) {
            case R.id.lyt_view_profile:
                if (pm.isLoggedIn()) {
                    profile();
                } else {
                    login();
                }
                break;
            case R.id.lyt_setting:
                Snackbar.make(view, "Setting Clicked", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.lyt_help:
                Snackbar.make(view, "Help nad FAQ Clicked", Snackbar.LENGTH_SHORT).show();
                break;
            case R.id.lyt_logout:
                if (pm.isLoggedIn()) {
                    logout();
                } else {
                    login();
                }
                break;
        }
    }

    private void profile() {
        Intent i = new Intent(getActivity(), UserActivity.class);
        startActivity(i);
    }

    private void login() {
        Intent i = new Intent(getActivity(), LoginActivity.class);
        startActivity(i);
    }

    private void logout() {
        PrefManager pm = PrefManager.getInstance(getActivity());
        pm.setLoggedIn(false);
        pm.setAccessToken("");
        pm.setUserId(0);
        pm.setUserName("");
        pm.setUserEmail("");
        pm.setUserAvatar("");
        pm.setExpires(0);

        onResume();
    }
}
