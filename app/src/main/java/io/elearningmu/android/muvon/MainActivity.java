package io.elearningmu.android.muvon;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import io.elearningmu.android.muvon.view.activity.HomeActivity;
import io.elearningmu.android.muvon.view.activity.LoginActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CoordinatorLayout coordinatorLayout;
    private Button btnCourse, btnTrack, btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCourse = findViewById(R.id.btnCourse);
        btnTrack = findViewById(R.id.btnTrack);
        btnLogin = findViewById(R.id.btnLogin);

        btnCourse.setOnClickListener(this);
        btnTrack.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCourse:
                //Snackbar.make(coordinatorLayout, "Button to Course Activity", Snackbar.LENGTH_LONG).show();
                Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(homeIntent);
                break;
            case R.id.btnTrack:
                Snackbar.make(coordinatorLayout, "Button to Track Activity", Snackbar.LENGTH_LONG).show();
                break;
            case R.id.btnLogin:
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                break;
        }
    }
}