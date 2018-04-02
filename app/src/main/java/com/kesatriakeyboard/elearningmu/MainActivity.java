package com.kesatriakeyboard.elearningmu;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kesatriakeyboard.elearningmu.view.activity.HomeActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CoordinatorLayout coordinatorLayout;
    private Button btnCourse, btnTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCourse = findViewById(R.id.btnCourse);
        btnTrack = findViewById(R.id.btnTrack);

        btnCourse.setOnClickListener(this);
        btnTrack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCourse:
                //Snackbar.make(coordinatorLayout, "Button to Course Activity", Snackbar.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(i);
                break;
            case R.id.btnTrack:
                Snackbar.make(coordinatorLayout, "Button to Track Activity", Snackbar.LENGTH_LONG).show();
                break;
        }
    }
}