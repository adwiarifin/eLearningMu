package io.elearningmu.android.muvon.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import io.elearningmu.android.muvon.R;
import io.elearningmu.android.muvon.adapter.PageFragmentAdapter;
import io.elearningmu.android.muvon.util.Tools;
import io.elearningmu.android.muvon.view.fragment.HomeCourseFragment;
import io.elearningmu.android.muvon.view.fragment.HomeProfileFragment;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private PageFragmentAdapter adapter;

    private HomeCourseFragment f_course;
    private HomeProfileFragment f_profile;
    private static int[] imageResId = {
            R.drawable.tab_course,
            R.drawable.tab_profile
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(false);

        viewPager = findViewById(R.id.viewpager);
        setupViewPager();

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        setupTabClick();

        // for system bar in lollipop
        Tools.systemBarLolipop(this);
    }

    private void setupViewPager() {
        // initialize fragment
        if (f_course == null) {
            f_course = new HomeCourseFragment();
        }
        if (f_profile == null) {
            f_profile = new HomeProfileFragment();
        }

        // add fragment to adapter
        adapter = new PageFragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(f_course, getString(R.string.tab_course));
        adapter.addFragment(f_profile, getString(R.string.tab_profile));
        viewPager.setAdapter(adapter);
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(imageResId[0]);
        tabLayout.getTabAt(1).setIcon(imageResId[1]);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        PrefManager pm = PrefManager.getInstance(this);
//        if (pm.isLoggedIn()) {
//            getMenuInflater().inflate(R.menu.menu_activity_main_alt, menu);
//        } else {
//            getMenuInflater().inflate(R.menu.menu_activity_main, menu);
//        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        switch (id) {
//            case R.id.action_login: {
//                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(i);
//                return true;
//            }
//            case R.id.action_logout: {
//                logout();
//                return true;
//            }
//            case R.id.action_settings:
//                Snackbar.make(parent_view, "Setting Clicked", Snackbar.LENGTH_SHORT).show();
//                return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    // handle click profile page
    public void actionClick(View view){
        f_profile.actionClick(view);
    }
}
