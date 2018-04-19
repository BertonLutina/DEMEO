package app.stucre;

import android.support.design.widget.FloatingActionButton;
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

public class electives extends AppCompatActivity {

    private DrawerLayout dLayout;
    private ActionBarDrawerToggle dToggle;

    private ViewPager mViewPager;
    private SlideUp slideUp ;
    private View slideView;
    private View dimElectives;
    private FloatingActionButton floatElectives;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electives);


        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorE));
        }

        slideView = findViewById(R.id.slideUpCreditsView);
        dimElectives = findViewById(R.id.dim_electives);

        slideUp = new SlideUp(slideView);
        slideUp.hideImmediately();

        floatElectives = findViewById(R.id.floatBtnElectives);

        floatElectives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideUp.animateIn();
                floatElectives.hide();
            }
        });

        slideUp.setSlideListener(new SlideUp.SlideListener() {
            @Override
            public void onSlideDown(float v) {
                dimElectives.setAlpha(1-(v/100));
            }

            @Override
            public void onVisibilityChanged(int i) {
                if(i == View.GONE){
                    floatElectives.show();
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarElectives);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabselectives);
        tabLayout.addTab(tabLayout.newTab().setText("Fase 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mViewPager = (ViewPager) findViewById(R.id.pagerElectives);
        final ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        mViewPager.setAdapter(viewPageAdapter);

        dLayout = (DrawerLayout)findViewById(R.id.drawerE);


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
