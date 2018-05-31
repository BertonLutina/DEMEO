package app.stucre;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.microsoft.identity.client.AuthenticationResult;
import com.microsoft.identity.client.PublicClientApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;


public class Courses extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private ActionBarDrawerToggle dToggle;
    private NavigationView nav_course;





    //Login Microsoft
    final static String CLIENT_ID = "caad7c8b-8a90-49c7-8461-1cc2e7b2ee3e";
    final static String SCOPES[] = {"https://graph.microsoft.com/User.Read"};
    final static String MSGRAPH_URL = "https://graph.microsoft.com/v1.0/me";

    /* UI &amp; Debugging Variables */
    private static final String TAG = MicrosoftLogin.class.getSimpleName();
    Button callGraphButton;
    Button signOutButton;

    /* Azure AD Variables */
    private PublicClientApplication sampleApp;
    private AuthenticationResult authResult;

//Vakken
    private List<Vak> Vakken1;
    private List<Vak> Vakken2;
    private List<Vak> Vakken3;
    private List<String> Vakken1_voltijdigheid;
    private List<String> Vakken2_voltijdigheid;
    private List <Vak>VakkenOF3;
    private List<Vak> VakkenEF3;
    private List<Vak> VakkenEmA;
    private List<Vak> VakkenEmB;



    private String InfoUser = "User";
    private static final int maxCredit = 60;
    private int credit = 0;
    private int click = 0;


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


        buttonClicks();
        nav_course = (NavigationView) findViewById(R.id.nav_courses);
        nav_course.setNavigationItemSelectedListener(this);


        Intent IntentUser = getIntent();

        String [] userIndentif = (String[]) IntentUser.getStringArrayExtra("Info");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(dToggle.onOptionsItemSelected(item)){
            return true;
        }

        int id = item.getItemId();

        switch (id){

            case R.id.setting:
                Toasty.warning(getBaseContext(),"Logout").show();
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
            case R.id.profile:
                Intent profile = new Intent(Courses.this,profile.class );
                startActivity(profile);
                return false;
            case R.id.about:
                Intent about = new Intent(Courses.this,about.class );
                startActivity(about);
                return false;
            case R.id.logOut:

                return false;

            default:
                return false;

        }
    }

    private void buttonClicks(){

        // instantianton
        CardView dCardview = findViewById(R.id.dCardview);
        CardView eMCardview = findViewById(R.id.eMCardview);
        CardView eCardview = findViewById(R.id.eCardview);
        CardView oCardview = findViewById(R.id.oCardview);
        Button sendMail = findViewById(R.id.sendEmail);

        // button click to navigate to the course page
        dCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          click++;

                if(click == 1){
                final AlertDialog.Builder builder = new AlertDialog.Builder(Courses.this);
                builder.setTitle("Included Courses")
                        .setTitle("Are you allowed to start this course")
                        .setMessage("Do you have your Secundary Eduction degree? Or other Certifaction? ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Intent intDuties = new Intent(Courses.this, duties.class);
                                startActivity(intDuties);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),"Make a appointment with Student Administration",Toast.LENGTH_LONG).show();
                    }
                });

                //AlertDialog alertDialog =
                        builder.create().show();}else{
                    Intent intDuties = new Intent(Courses.this, duties.class);
                    startActivity(intDuties);
                }
               // alertDialog.show();

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

        if(!(Vakken1 == null)){
        sendMail.setBackgroundColor(Color.rgb(220,220,220));
        sendMail.setTextColor(Color.rgb(128,128,128));
        sendMail.setEnabled(false);
        sendMail.setClickable(false);
        }

        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vakken="";
               // VakkenDutiesFase1();
                Intent send = new Intent(Intent.ACTION_SEND);
                send.setData(Uri.parse("mailto:"));
                String [] to = {"berton.lutina@hotmail.com","berton.lutina@live.be"};
                send.putExtra(Intent.EXTRA_EMAIL, to);
                send.putExtra(Intent.EXTRA_SUBJECT,"Leerling R0282865 heeft 60 Credits opgenomen");

               /* for(Vak vak : Vakken1){
                    vakken += vak.getCourse()+ ": "+vak.getCredit() +", \n";
                }*/
                send.putExtra(Intent.EXTRA_TEXT,"Dear, \n\n Here is the course I have chosen: \n\n"+vakken+ "\n\n I would like to have and appointment to clearify this subject and to make it on point. \n\n Kind regards \n Berton Lutina Mulamba");
                send.setType("message/rfc822");
                Intent.createChooser(send,"Send Email");
                startActivity(send);

            }
        });

    }






}
