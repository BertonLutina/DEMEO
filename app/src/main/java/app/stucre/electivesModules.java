package app.stucre;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.Switch;

import com.mancj.slideup.SlideUp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class electivesModules extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout dLayout;
    private ActionBarDrawerToggle dToggle;
    private SectionPageAdapter2 mSectionPageAdapter2;
    private ViewPager mViewPager;
    private NavigationView nav_em;
    private SlideUp slideUp ;
    private View slideView;
    private View dimEM;
    private FloatingActionButton floatEM;
    private List<String> opgenomenVakken = new ArrayList<>();
    private List<Vak> eMtoProfiel = new ArrayList<>();

    private String[] course_array;

    private static final String TAG ="duties";
    private Button btnSend;
    private Switch selectallB;
    private Switch selectallA;
    EventBus bus = EventBus.getDefault();

    private int count = 0;


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(electivesModules.OpgenomenVakken event){

        opgenomenVakken = event.vak;

    }

    @Subscribe
    public void onEvent2(electivesModules.VakkenSturen event){

        eMtoProfiel = event.vakList;

    }

    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electives_modules);
        bus.register(this);


        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Blauw));
        }

        slideView = findViewById(R.id.slideUpCreditsView);
        dimEM = findViewById(R.id.dim_EM);

        slideUp = new SlideUp(slideView);
        slideUp.hideImmediately();

        floatEM = findViewById(R.id.floatBtnEM);

        floatEM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideUp.animateIn();
                floatEM.hide();
            }
        });

        slideUp.setSlideListener(new SlideUp.SlideListener() {
            @Override
            public void onSlideDown(float v) {
                dimEM.setAlpha(1-(v/100));
            }

            @Override
            public void onVisibilityChanged(int i) {
                if(i == View.GONE){
                    floatEM.show();
                }
            }
        });
            btnSend = (Button) slideView.findViewById(R.id.versturen_credits);
            btnSend.setEnabled(true);
            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    course_array = new String[opgenomenVakken.size()];
                    course_array =opgenomenVakken.toArray(course_array);

                    AlertDialog.Builder builder = new AlertDialog.Builder(electivesModules.this);
                    builder.setTitle("Included Courses")
                            .setItems(course_array, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // The 'which' argument contains the index position
                                    // of the selected item
                                }
                            }).setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Intent sendToProfiel = new Intent(electivesModules.this, profile.class);
                            sendToProfiel.putExtra("FP",(ArrayList<Vak>) eMtoProfiel);
                            startActivity(sendToProfiel);

                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

            });



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

        selectallA = (Switch) findViewById(R.id.selectAllSwitchEMA);
        selectallB = (Switch) findViewById(R.id.selectAllSwitchEMB);

        selectallB.setVisibility(View.GONE);
        selectallA.setVisibility(View.GONE);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition(),true);
                if(tab.getPosition() == 0){

                    selectallB.setVisibility(View.GONE);
                    selectallA.setVisibility(View.GONE);
                }else if(tab.getPosition() == 1){

                    selectallB.setVisibility(View.GONE);
                    selectallA.setVisibility(View.GONE);

                }
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
        dToggle.getDrawerArrowDrawable().setColor(Color.WHITE);

        dLayout.addDrawerListener(dToggle);
        dToggle.syncState();

        // These lines are needed to display the top-left hamburger button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nav_em = (NavigationView) findViewById(R.id.nav_em);

        nav_em.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_a_bar,menu);
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
                Intent course = new Intent(electivesModules.this,Courses.class );
                startActivity(course);
                return false;
            case R.id.profile:
                Intent em = new Intent(electivesModules.this,profile.class );
                startActivity(em);
                return false;
            case R.id.setting:
                Intent electives = new Intent(electivesModules.this,electives.class );
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
                Intent course = new Intent(electivesModules.this,Courses.class );
                startActivity(course);
                return false;
            case R.id.duties:
                Intent duties = new Intent(electivesModules.this,duties.class );
                startActivity(duties);
                return false;
            case R.id.electives:
                Intent electives = new Intent(electivesModules.this,electives.class );
                startActivity(electives);
                return false;
            case R.id.options:
                Intent options = new Intent(electivesModules.this,options.class );
                startActivity(options);
                return false;
            default:
                return false;

        }
    }

    public int getCount() {
        return count;
    }

    public static class Voltwaardigheden {
        Vak vakpositie;

        public Voltwaardigheden(Vak vakpositie) {
            this.vakpositie = vakpositie;
        }
    }

    public static class OpgenomenVakken {
        List <String> vak;

        public OpgenomenVakken(List <String> vak) {
            this.vak = vak;
        }
    }

    public static class FaseTweeNaarEM {
        int credit;

        public FaseTweeNaarEM(int credit) {
            this.credit = credit;
        }
    }

    public  static class VakkenSturen{

        List <Vak> vakList;

        public VakkenSturen(List <Vak> vakList){
            this.vakList = vakList;
        }
    }

    public  static class VakkenSturen3{

        List <Vak> vakList;

        public VakkenSturen3(List <Vak> vakList){
            this.vakList = vakList;
        }
    }
}
