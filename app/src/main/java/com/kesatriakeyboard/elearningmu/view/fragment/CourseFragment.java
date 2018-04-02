package com.kesatriakeyboard.elearningmu.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kesatriakeyboard.elearningmu.R;

public class CourseFragment extends Fragment {

    public static CourseFragment newInstance() {
        return new CourseFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_course, container, false);

        String params = getArguments().getString("params");
        TextView textFragment = view.findViewById(R.id.textFragment);
        textFragment.setText(params);

        return view;
    }
}
