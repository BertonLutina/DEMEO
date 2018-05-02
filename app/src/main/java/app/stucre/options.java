package app.stucre;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
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
import android.widget.Button;

import com.mancj.slideup.SlideUp;

import java.util.ArrayList;
import java.util.List;

public class options extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout dLayout;
    private ActionBarDrawerToggle dToggle;
    private ViewPager mViewPager;
    private NavigationView nav_options;
    private SlideUp slideUp ;
    private View slideView;
    private View dimOptions;
    private FloatingActionButton floatOption;
    private Button btnSend;
    private List <Vak> Options_Dialog;
    private List <String> opgenomenVakken = new ArrayList<>();
    private String[] course_array;


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

        Intent intent = getIntent();

        Options_Dialog = (ArrayList<Vak>) intent.getSerializableExtra("Intership");

        for (Vak course : Options_Dialog){
            opgenomenVakken.add(course.getCourse());
        }

        course_array = new String[opgenomenVakken.size()];
        opgenomenVakken.toArray(course_array);

        btnSend = (Button) slideView.findViewById(R.id.versturen_credits);
        btnSend.setEnabled(true);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(options.this);
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

        nav_options = (NavigationView) findViewById(R.id.nav_options);

        nav_options.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(dToggle.onOptionsItemSelected(item)){
            return true;
        }

        int id = item.getItemId();

        switch (id){
            case R.id.course:
                Intent course = new Intent(options.this,Courses.class );
                startActivity(course);
                return false;
            case R.id.profile:
                Intent em = new Intent(options.this,profile.class );
                startActivity(em);
                return false;
            case R.id.setting:
                Intent electives = new Intent(options.this,electives.class );
                startActivity(electives);
                return false;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //menu.clear();
        getMenuInflater().inflate(R.menu.nav_a_bar,menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.course:
                Intent course = new Intent(options.this,Courses.class );
                startActivity(course);
                return false;
            case R.id.em:
                Intent em = new Intent(options.this,electivesModules.class );
                startActivity(em);
                return false;
            case R.id.duties:
                Intent duties = new Intent(options.this,duties.class );
                startActivity(duties);
                return false;
            case R.id.electives:
                Intent electives = new Intent(options.this,electives.class );
                startActivity(electives);
                return false;
            default:
                return false;

        }
    }
}
