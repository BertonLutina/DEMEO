package app.stucre;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import es.dmoral.toasty.Toasty;

public class profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout dLayout;
    private ActionBarDrawerToggle dToggle;
    private NavigationView nav_prof;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorP));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPr);
        setSupportActionBar(toolbar);

        dLayout = (DrawerLayout)findViewById(R.id.drawer);




        dToggle = new ActionBarDrawerToggle(this,dLayout,R.string.open_drawer,R.string.close_drawer);

        dLayout.addDrawerListener(dToggle);
        dToggle.syncState();

        // These lines are needed to display the top-left hamburger button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nav_prof = (NavigationView) findViewById(R.id.nav_prof);

        nav_prof.setNavigationItemSelectedListener(this);
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
                Intent course = new Intent(profile.this,Courses.class );
                startActivity(course);
                return false;
            case R.id.profile:
                Intent em = new Intent(profile.this,profile.class );
                startActivity(em);
                return false;
            case R.id.setting:
                Intent electives = new Intent(profile.this,electives.class );
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
                Intent course = new Intent(profile.this,Courses.class );
                startActivity(course);
                return false;
            case R.id.aboutus:
                Intent about = new Intent(profile.this,about.class );
                startActivity(about);
                return false;
            case R.id.logOut:
                Toasty.warning(getBaseContext(),"Logout").show();
                return false;
            default:
                return false;

        }


    }
}
