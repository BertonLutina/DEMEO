package app.stucre;

import android.content.ClipData;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabItem;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jgabrielfreitas.core.BlurImageView;

public class Course extends AppCompatActivity {

    private ActionBarDrawerToggle dToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);


        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorCourse));
        }


        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        DrawerLayout dLayout = findViewById(R.id.drawer);




        dToggle = new ActionBarDrawerToggle(this, dLayout,R.string.open_drawer,R.string.close_drawer);

        dLayout.addDrawerListener(dToggle);
        dToggle.syncState();

        // These lines are needed to display the top-left hamburger button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // instantianton
        Button dButton = findViewById(R.id.dButton);
        Button emButton = findViewById(R.id.emButton);
        Button eButton = findViewById(R.id.eButton);
        Button oButton = findViewById(R.id.oButton);




        // button click to navigate to the course page
        dButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intDuties = new Intent(Course.this, duties.class);
                startActivity(intDuties);

            }
        });
        emButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intEM = new Intent(Course.this, electivesModules.class);
                startActivity(intEM);

            }
        });
        eButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intE = new Intent(Course.this, electives.class);
                startActivity(intE);

            }
        });
        oButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intOptions = new Intent(Course.this, options.class);
                startActivity(intOptions);

            }
        });





    }




    public boolean onOptionsItemSelected(MenuItem item) {


        return dToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);


    }


}

