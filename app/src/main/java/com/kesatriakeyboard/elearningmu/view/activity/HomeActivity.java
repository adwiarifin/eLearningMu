package com.kesatriakeyboard.elearningmu.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.kesatriakeyboard.elearningmu.R;
import com.kesatriakeyboard.elearningmu.util.Config;
import com.kesatriakeyboard.elearningmu.view.fragment.CourseFragment;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        Bundle args = new Bundle();
        args.putString("params", Config.COURSE_URL);
        Fragment firstFragment = CourseFragment.newInstance();
        firstFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, firstFragment);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        Bundle args = new Bundle();

        switch (item.getItemId()) {
            case R.id.navigation_home_latest:
                args.putString("params", Config.COURSE_URL);
                selectedFragment = CourseFragment.newInstance();
                selectedFragment.setArguments(args);
                break;
            case R.id.navigation_home_popular:
                args.putString("params", Config.POPULAR_COURSE_URL);
                selectedFragment = CourseFragment.newInstance();
                selectedFragment.setArguments(args);
                break;
            case R.id.navigation_home_featured:
                args.putString("params", Config.FEATURED_COURSE_URL);
                selectedFragment = CourseFragment.newInstance();
                selectedFragment.setArguments(args);
                break;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, selectedFragment);
        transaction.commit();
        return true;
    }
}