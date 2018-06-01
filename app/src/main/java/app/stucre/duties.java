package app.stucre;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.DepthPageTransformer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.slideup.SlideUp;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class duties extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout dLayout;
    private NavigationView nav_duties;
    private ActionBarDrawerToggle dToggle;
    private ViewPager mViewPager;


    private SlideUp slideUp;
    private View slideView;
    private View dimDuties;
    private FloatingActionButton floatDuties;
    EventBus bus = EventBus.getDefault();

    private TextView tvCourse;


    private List<Vak> Vakken1;
    private List<Vak> Vakken2;
    private List<Vak> Vakken3;

    private List<String> Vakken1_Dialog = new ArrayList<>();
    private List<Vak> Vakken2_Dialog;
    private List<Vak> Vakken3_Dialog;

    private List<String> opgenomenVakken = new ArrayList<>();
    private String[] course_array;

    private static final String TAG = "duties";
    private Button btnSend;

    private static final int DUTIES_FASE1FRAGEMENY_REQUEST_CODE = 0;
    private Switch selectall;
    private Switch selectall2;
    private Switch selectall3;
    private List<Vak> OpgenomenCourse = new ArrayList<>();



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent (duties.OpgenomenVakken event){

        Vakken1_Dialog = event.vak;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent2 (duties.OpgenomenCourse event){

        OpgenomenCourse = event.vak;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duties);
        bus.register(this);




        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Blauw));
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
                dimDuties.setAlpha(1 - (v / 100));
            }

            @Override
            public void onVisibilityChanged(int i) {
                if (i == View.GONE) {
                    floatDuties.show();
                }
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDuties);
        setSupportActionBar(toolbar);



        btnSend = (Button) slideView.findViewById(R.id.versturen_credits);
        btnSend.setEnabled(true);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                course_array = new String[Vakken1_Dialog.size()];
                course_array = Vakken1_Dialog.toArray(course_array);

                AlertDialog.Builder builder = new AlertDialog.Builder(duties.this);
                builder.setTitle("Included Courses")
                        .setItems(course_array, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        }).setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent dutiesToProfiel = new Intent(duties.this, profile.class);
                        dutiesToProfiel.putExtra("dtp", (ArrayList<Vak>) OpgenomenCourse);
                        startActivity(dutiesToProfiel);
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

        });
        // ----------------------------------------------------------------------------


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsDuties);
        tabLayout.addTab(tabLayout.newTab().setText("Fase 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Fase 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Fase 3"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        selectall = (Switch) findViewById(R.id.selectAllSwitchFase1);
        selectall2 = (Switch) findViewById(R.id.selectAllSwitchFase2);
        selectall3 = (Switch) findViewById(R.id.selectAllSwitchFase3);

        selectall2.setVisibility(View.GONE);
        selectall3.setVisibility(View.GONE);
        selectall.setVisibility(View.VISIBLE);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        final SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        mViewPager.setAdapter(adapter);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition(), true);
                if (tab.getPosition() == 0) {
                    Toasty.info(getApplicationContext(), "Fase1").show();
                    selectall2.setVisibility(View.GONE);
                    selectall3.setVisibility(View.GONE);
                    selectall.setVisibility(View.VISIBLE);
                    floatDuties.setEnabled(true);
                    floatDuties.setVisibility(View.VISIBLE);
                    btnSend.setVisibility(View.VISIBLE);
                } else if (tab.getPosition() == 1) {
                    Toasty.info(getApplicationContext(), "Fase2").show();
                    selectall2.setVisibility(View.VISIBLE);
                    selectall.setVisibility(View.GONE);
                    selectall3.setVisibility(View.GONE);
                    //floatDuties.setEnabled(false);
                    //floatDuties.setVisibility(View.GONE);
                    btnSend.setVisibility(View.GONE);

                } else if (tab.getPosition() == 2) {
                    Toasty.info(getApplicationContext(), "Fase3").show();
                    selectall2.setVisibility(View.GONE);
                    selectall3.setVisibility(View.VISIBLE);
                    selectall.setVisibility(View.GONE);
                    //floatDuties.setEnabled(false);
                    //floatDuties.setVisibility(View.GONE);
                    btnSend.setVisibility(View.GONE);
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
        dLayout = (DrawerLayout) findViewById(R.id.drawer);
        dToggle = new ActionBarDrawerToggle(this, dLayout, R.string.open_drawer, R.string.close_drawer);
        dToggle.getDrawerArrowDrawable().setColor(Color.WHITE);

        dLayout.addDrawerListener(dToggle);
        dToggle.syncState();

        // These lines are needed to display the top-left hamburger button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nav_duties = (NavigationView) findViewById(R.id.nav_duties);
        nav_duties.setNavigationItemSelectedListener(this);


    }


    public List<Vak> sendData() {
        return Vakken1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //menu.clear();
        getMenuInflater().inflate(R.menu.nav_d_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (dToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();

        switch (id) {
            case R.id.course:
                Intent course = new Intent(duties.this, Courses.class);
                startActivity(course);
                return false;
            case R.id.profile:
                Intent em = new Intent(duties.this, profile.class);
                startActivity(em);
                return false;
            case R.id.setting:

                Intent electives = new Intent(duties.this, electives.class);
                startActivity(electives);
                return false;


            default:
                return super.onOptionsItemSelected(item);
        }


    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        int id = item.getItemId();

        switch (id) {
            case R.id.course:
                Intent course = new Intent(duties.this, Courses.class);
                startActivity(course);
                return false;
            case R.id.em:
                Intent em = new Intent(duties.this, electivesModules.class);
                startActivity(em);
                return false;
            case R.id.electives:
                Intent electives = new Intent(duties.this, electives.class);
                startActivity(electives);
                return false;
            case R.id.options:
                Intent options = new Intent(duties.this, options.class);
                startActivity(options);
                return false;
            default:
                return false;

        }


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

    public static class OpgenomenCourse {
        List <Vak> vak;

        public OpgenomenCourse(List <Vak> vak) {
            this.vak = vak;
        }
    }

    public static class FaseTweeNaarEM {
        int credit;

        public FaseTweeNaarEM(int credit) {
            this.credit = credit;
        }
    }



}

