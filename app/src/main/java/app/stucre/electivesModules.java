package app.stucre;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public class electivesModules extends AppCompatActivity {

    private DrawerLayout dLayout;
    private ActionBarDrawerToggle dToggle;
    private SectionPageAdapter2 mSectionPageAdapter2;
    private ViewPager mViewPager;

    private static final String TAG ="duties";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electives_modules);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorEM));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarEM);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabselectivesmodules);
        tabLayout.addTab(tabLayout.newTab().setText("Option A"));
        tabLayout.addTab(tabLayout.newTab().setText("Option B"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mViewPager = (ViewPager) findViewById(R.id.pager2);
        final SectionPageAdapter2 adapter2 = new SectionPageAdapter2(getSupportFragmentManager(),tabLayout.getTabCount());
        mViewPager.setAdapter(adapter2);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.setOffscreenPageLimit(3);

        dLayout = (DrawerLayout)findViewById(R.id.drawer);

        dToggle = new ActionBarDrawerToggle(this,dLayout,R.string.open_drawer,R.string.close_drawer);

        dLayout.addDrawerListener(dToggle);
        dToggle.syncState();

        // These lines are needed to display the top-left hamburger button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_em_bar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(dToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
