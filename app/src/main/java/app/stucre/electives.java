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

import com.mancj.slideup.SlideUp;

import java.util.ArrayList;
import java.util.List;

public class electives extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout dLayout;
    private ActionBarDrawerToggle dToggle;
    private NavigationView nav_ele;
    private ViewPager mViewPager;
    private SlideUp slideUp ;
    private View slideView;
    private View dimElectives;
    private FloatingActionButton floatElectives;
    private Button btnSend;
    private List<String> opgenomenVakken = new ArrayList<>();
    private String[] course_array;
    private List<Vak> Ele = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electives);


        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.Blauw));
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

        /*Intent intent = getIntent();
        Ele = (ArrayList<Vak>) intent.getSerializableExtra("EleFase3");

        for (Vak course : Ele){
            opgenomenVakken.add(course.getCourse());
        }

        course_array = new String[opgenomenVakken.size()];
        opgenomenVakken.toArray(course_array);

        btnSend = (Button) slideView.findViewById(R.id.versturen_credits);
        btnSend.setEnabled(true);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(electives.this);
                builder.setTitle("Included Courses")
                        .setItems(course_array, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
                            }
                        }).setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

        });*/

        mViewPager = (ViewPager) findViewById(R.id.pagerElectives);
        final ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        mViewPager.setAdapter(viewPageAdapter);

        dLayout = (DrawerLayout)findViewById(R.id.drawerE);


        dToggle = new ActionBarDrawerToggle(this,dLayout,R.string.open_drawer,R.string.close_drawer);
        dToggle.getDrawerArrowDrawable().setColor(Color.WHITE);

        dLayout.addDrawerListener(dToggle);
        dToggle.syncState();




        // These lines are needed to display the top-left hamburger button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nav_ele = (NavigationView) findViewById(R.id.nav_ele);

        nav_ele.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //menu.clear();
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
                Intent course = new Intent(electives.this,Courses.class );
                startActivity(course);
                return false;
            case R.id.profile:
                Intent pro = new Intent(electives.this,profile.class );
                startActivity(pro);
                return false;
            case R.id.setting:
                Intent electives = new Intent(electives.this,duties.class );
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
                Intent course = new Intent(electives.this,Courses.class );
                startActivity(course);
                return false;
            case R.id.em:
                Intent em = new Intent(electives.this,electivesModules.class );
                startActivity(em);
                return false;
            case R.id.duties:
                Intent duties = new Intent(electives.this,duties.class );
                startActivity(duties);
                return false;
            case R.id.options:
                Intent options = new Intent(electives.this,options.class );
                startActivity(options);
                return false;
            default:
                return false;

        }
    }
}
