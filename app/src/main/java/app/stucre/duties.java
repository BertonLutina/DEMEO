package app.stucre;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.DepthPageTransformer;
import com.mancj.slideup.SlideUp;

import es.dmoral.toasty.Toasty;

public class duties extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout dLayout;
    private NavigationView nav_duties;
    private ActionBarDrawerToggle dToggle;
    private ViewPager mViewPager;

    private SlideUp slideUp ;
    private View slideView;
    private View dimDuties;
    private FloatingActionButton floatDuties;

    private TextView tvCourse;



    private static final String TAG = "duties";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duties);



        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorD));
        }

        slideView = findViewById(R.id.slideUpCreditsView);
        dimDuties = findViewById(R.id.dim_duties);

        slideUp = new SlideUp(slideView);
        slideUp.hideImmediately();

        floatDuties = findViewById(R.id.floatBtnDuties);

        floatDuties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideUp.animateIn();
                floatDuties.hide();
            }
        });

        slideUp.setSlideListener(new SlideUp.SlideListener() {
            @Override
            public void onSlideDown(float v) {
                dimDuties.setAlpha(1-(v/100));
            }

            @Override
            public void onVisibilityChanged(int i) {
                if(i == View.GONE){
                    floatDuties.show();
                }
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDuties);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsDuties);
        tabLayout.addTab(tabLayout.newTab().setText("Fase 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Fase 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Fase 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        final SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        mViewPager.setAdapter(adapter);

        mViewPager.setPageTransformer(true,new DepthPageTransformer());
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

        nav_duties = (NavigationView) findViewById(R.id.nav_duties);

        nav_duties.setNavigationItemSelectedListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //menu.clear();
        getMenuInflater().inflate(R.menu.nav_d_bar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(dToggle.onOptionsItemSelected(item)){
            return true;
        }

        int id = item.getItemId();

        switch (id){
            case R.id.course:
                Intent course = new Intent(duties.this,Courses.class );
                startActivity(course);
                return false;
            case R.id.profile:
                Intent em = new Intent(duties.this,profile.class );
                startActivity(em);
                return false;
            case R.id.setting:
                Intent electives = new Intent(duties.this,electives.class );
                startActivity(electives);
                return false;


            default:
                return super.onOptionsItemSelected(item);
        }


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        int id = item.getItemId();

        switch (id){
            case R.id.course:
                Intent course = new Intent(duties.this,Courses.class );
                startActivity(course);
                return false;
            case R.id.em:
                Intent em = new Intent(duties.this,electivesModules.class );
                startActivity(em);
                return false;
            case R.id.electives:
                Intent electives = new Intent(duties.this,electives.class );
                startActivity(electives);
                return false;
            case R.id.options:
                Intent options = new Intent(duties.this,options.class );
                startActivity(options);
                return false;
            default:
                return false;

        }


    }
}
