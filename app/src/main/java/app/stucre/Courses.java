package app.stucre;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;


public class Courses extends AppCompatActivity {


    private ActionBarDrawerToggle dToggle;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorCourse));
        }


        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        final DrawerLayout dLayout = findViewById(R.id.drawer);




        dToggle = new ActionBarDrawerToggle(this, dLayout,R.string.open_drawer,R.string.close_drawer);

        dLayout.addDrawerListener(dToggle);
        dToggle.syncState();

        // These lines are needed to display the top-left hamburger button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // instantianton
        CardView dCardview = findViewById(R.id.dCardview);
        CardView eMCardview = findViewById(R.id.eMCardview);
        CardView eCardview = findViewById(R.id.eCardview);
        CardView oCardview = findViewById(R.id.oCardview);




        // button click to navigate to the course page
        dCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intDuties = new Intent(Courses.this, duties.class);
                startActivity(intDuties);

            }
        });
        eMCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intEM = new Intent(Courses.this, electivesModules.class);
                startActivity(intEM);

            }
        });
        eCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intE = new Intent(Courses.this, electives.class);
                startActivity(intE);

            }
        });
        oCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intOptions = new Intent(Courses.this, options.class);
                startActivity(intOptions);

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(dToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
