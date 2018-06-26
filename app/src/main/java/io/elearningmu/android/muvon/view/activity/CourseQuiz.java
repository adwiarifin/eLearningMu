package io.elearningmu.android.muvon.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.elearningmu.android.muvon.R;

public class CourseQuiz extends AppCompatActivity {

    public static final String EXTRA_COURSE_ID = "courseIndexId";
    public static final String EXTRA_ITEM_ID = "courseItemId";
    public static final String EXTRA_ITEM_TITLE = "courseItemTitle";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_quiz);
    }
}
