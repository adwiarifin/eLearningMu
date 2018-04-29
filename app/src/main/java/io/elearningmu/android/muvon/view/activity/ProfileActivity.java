package io.elearningmu.android.muvon.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import io.elearningmu.android.muvon.R;
import io.elearningmu.android.muvon.adapter.PageFragmentAdapter;
import io.elearningmu.android.muvon.util.Tools;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private PageFragmentAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(false);

        viewPager = findViewById(R.id.container);
        setupViewPager();

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabClick();
//        setupTabTitle();


//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        // for system bar in lollipop
        Tools.systemBarLolipop(this);
    }

    private void setupViewPager() {
        // initialize fragment
//        if (f_course == null) {
//            f_course = new PageCourseFragment();
//        }
//        if (f_profile == null) {
//            f_profile = new PageProfileFragment();
//        }

        // add fragment to adapter
        adapter = new PageFragmentAdapter(getSupportFragmentManager());
//        adapter.addFragment(f_course, getString(R.string.tab_course));
        viewPager.setAdapter(adapter);
    }

    private void setupTabClick() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
                actionbar.setTitle(adapter.getTitle(position));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}
