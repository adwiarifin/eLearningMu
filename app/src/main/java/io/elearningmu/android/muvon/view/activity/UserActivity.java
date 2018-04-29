package io.elearningmu.android.muvon.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import io.elearningmu.android.muvon.R;
import io.elearningmu.android.muvon.adapter.UserFragmentAdapter;
import io.elearningmu.android.muvon.util.Tools;
import io.elearningmu.android.muvon.view.fragment.UserProfileFragment;

public class UserActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private UserFragmentAdapter adapter;
    private UserProfileFragment f_profile;

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
        setupTabTitle();


//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        // for system bar in lollipop
        Tools.systemBarLolipop(this);
    }

    private void setupTabTitle() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setText(adapter.getPageTitle(i));
        }
    }

    private void setupViewPager() {
        // initialize fragment
//        if (f_course == null) {
//            f_course = new PageCourseFragment();
//        }
        if (f_profile == null) {
            f_profile = new UserProfileFragment();
        }

        // add fragment to adapter
        adapter = new UserFragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(f_profile, "Profile");
        viewPager.setAdapter(adapter);
    }

    private void setupTabClick() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
                //actionbar.setTitle(adapter.getTitle(position));
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
