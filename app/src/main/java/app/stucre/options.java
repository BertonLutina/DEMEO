package app.stucre;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.mancj.slideup.SlideUp;

public class options extends AppCompatActivity {

    private DrawerLayout dLayout;
    private ActionBarDrawerToggle dToggle;
    private ViewPager mViewPager;
    private NavigationView nav_options;
    private SlideUp slideUp ;
    private View slideView;
    private View dimOptions;
    private FloatingActionButton floatOption;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorO));
        }


        slideView = findViewById(R.id.slideUpCreditsView);
        dimOptions = findViewById(R.id.dim_options);

        slideUp = new SlideUp(slideView);
        slideUp.hideImmediately();

        floatOption = findViewById(R.id.floatBtnOptions);

        floatOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideUp.animateIn();
                floatOption.hide();
            }
        });

        slideUp.setSlideListener(new SlideUp.SlideListener() {
            @Override
            public void onSlideDown(float v) {
                dimOptions.setAlpha(1-(v/100));
            }

            @Override
            public void onVisibilityChanged(int i) {
                    if(i == View.GONE){
                        floatOption.show();
                    }
            }
        });



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarOptions);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsOptions);
        tabLayout.addTab(tabLayout.newTab().setText("Intership: Fase 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mViewPager = (ViewPager) findViewById(R.id.pagerOptions);
        final ViewPageAdapter2 viewPageAdapter2 = new ViewPageAdapter2(getSupportFragmentManager(),tabLayout.getTabCount());
        mViewPager.setAdapter(viewPageAdapter2);


        dLayout = (DrawerLayout)findViewById(R.id.drawerO);

        dToggle = new ActionBarDrawerToggle(this,dLayout,R.string.open_drawer,R.string.close_drawer);

        dLayout.addDrawerListener(dToggle);
        dToggle.syncState();

        // These lines are needed to display the top-left hamburger button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(dToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
