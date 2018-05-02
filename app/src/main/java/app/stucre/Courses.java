package app.stucre;

import android.content.Intent;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.microsoft.identity.client.AuthenticationResult;
import com.microsoft.identity.client.PublicClientApplication;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class Courses extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private ActionBarDrawerToggle dToggle;
    private NavigationView nav_course;


    // Database Firebase
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    // Duties
    private DatabaseReference dutiesFase1 = database.getReference("Bedrijfskunde/TI/Duties/fase 1");
    private DatabaseReference dutiesFase2 = database.getReference("Bedrijfskunde/TI/Duties/fase 2");
    private DatabaseReference dutiesFase3 = database.getReference("Bedrijfskunde/TI/Duties/fase 3");

    //EM
    private DatabaseReference EMOPAdatabase = database.getReference("Bedrijfskunde/TI/ElectivesModules/Option B : Manage Internet and Cloud");
    private DatabaseReference EMOPBdatabase = database.getReference("Bedrijfskunde/TI/ElectivesModules/Option A: Design and Build Software");

    //Electives
    private DatabaseReference electivFase3 = database.getReference("Bedrijfskunde/TI/Electives/fase 3");

    //Option
    private DatabaseReference optionsFase3 = database.getReference("Bedrijfskunde/TI/Options/fase 3");




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


    private List<Vak> Vakken1;
    private List<Vak> Vakken2;
    private List<Vak> Vakken3;
    private List <Vak>VakkenOF3;
    private List<Vak> VakkenEF3;
    private List<Vak> VakkenEmA;
    private List<Vak> VakkenEmB;

    private String InfoUser = "User";
    private static final int maxCredit = 60;
    private int credit = 0;


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

        // button click to navigate to the course page
        dCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VakkenDutiesFase1();
                VakkenDutiesFase2();
                VakkenDutiesFase3();
                Intent intDuties = new Intent(Courses.this, duties.class);
                intDuties.putExtra("FaseEen",(ArrayList<Vak>)Vakken1);
                intDuties.putExtra("FaseTwee",(ArrayList<Vak>)Vakken2);
                intDuties.putExtra("FaseDrie",(ArrayList<Vak>)Vakken3);
                intDuties.putExtra("Credit",credit);
                startActivity(intDuties);

            }
        });
        eMCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                VakkenEMA();
                VakkenEMB();
                Intent intEM = new Intent(Courses.this, electivesModules.class);
                intEM.putExtra("EMA",(ArrayList<Vak>)VakkenEmA);
                intEM.putExtra("EMB",(ArrayList<Vak>)VakkenEmB);

                startActivity(intEM);

            }
        });
        eCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VakkenEF3();
                Intent intE = new Intent(Courses.this, electives.class);
                intE.putExtra("EleFase3",(ArrayList<Vak>)VakkenEF3);
                startActivity(intE);

            }
        });
        oCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                VakkenOption();
                Intent intOptions = new Intent(Courses.this, options.class);
                intOptions.putExtra("Intership",(ArrayList<Vak>)VakkenOF3);
                startActivity(intOptions);

            }
        });

    }


    private void VakkenDutiesFase1(){
        Vakken1 = new ArrayList<>();
        Vakken1.add(new Vak("HBI09B","ICT Organisation 1", "4 sp","4",1,false));
        Vakken1.add(new Vak("HBI10B","ICT Organisation 2", "4 sp","4",1,false));
        Vakken1.add(new Vak("HBI18B","Mobile en internet 1", "3 sp","3",1,false));
        Vakken1.add(new Vak("HBI19B","Mobile en internet 2", "3 sp","3",1,false));
        Vakken1.add(new Vak("HBI20B","System Management 1", "3 sp","3",1,false));
        Vakken1.add(new Vak("HBI21B","System Management 2", "3 sp","3",1,false));
        Vakken1.add(new Vak("HBI22B","Network Management 1", "3 sp","3",1,false));
        Vakken1.add(new Vak("HBI23B","Network Management 2", "3 sp","3",1,false));
        Vakken1.add(new Vak("HBI59B","Communicatietraining 1", "4 sp","4",1,false));
        Vakken1.add(new Vak("HBI60B","Communicatietraining 2", "4 sp","4",1,false));
        Vakken1.add(new Vak("HBI61B","Information management 1", "3 sp","3",1,false));
        Vakken1.add(new Vak("HBI62B","Information management 2", "3 sp","3",1,false));
        Vakken1.add(new Vak("HBI63B","Database development 1", "3 sp","3",1,false));
        Vakken1.add(new Vak("HBI64B","Database development 2", "3 sp","3",1,false));
        Vakken1.add(new Vak("HBI65B","Application development 1", "4 sp","4",1,false));
        Vakken1.add(new Vak("HBI66B","Application development 2", "4 sp","4",1,false));
        Vakken1.add(new Vak("HBI77B","Software engineering 1", "3 sp","3",1,false));
        Vakken1.add(new Vak("HBI78B","Software engineering 2", "3 sp","3",1,false));






    }
    private void VakkenDutiesFase2(){
        Vakken2 = new ArrayList<>();
        Vakken2.add(new Vak("HBI02C","ICT Organisation 4", "4 sp","4",2,false));
        Vakken2.add(new Vak("HBI25B","ICT Organisation 3", "4 sp","4",2,false));
        Vakken2.add(new Vak("HBI34B","Mobile en internet 3", "3 sp ","3",2,false));
        Vakken2.add(new Vak("HBI36B","System Management 3", "3 sp","3",2,false));
        Vakken2.add(new Vak("HBI38B","Network Management 3", "3 sp","3",2,false));
        Vakken2.add(new Vak("HBI68B","Communicatietraining 4", "4 sp","4",2,false));
        Vakken2.add(new Vak("HBI69B","Information management 3", "3sp","3",2,false));
        Vakken2.add(new Vak("HBI70B","Information management 4", "3sp","3",2,false));
        Vakken2.add(new Vak("HBI71B","Database development 3", "3 sp","3",2,false));
        Vakken2.add(new Vak("HBI73B","Application development 3", "3 sp","3",2,false));
        Vakken2.add(new Vak("OH3100","Software Engineering 3", "3 sp","3",2,false));
        Vakken2.add(new Vak("OH4100","Communicatietraining 3", "6 sp","6",2,false));
    }
    private void VakkenDutiesFase3(){
        Vakken3 = new ArrayList<>();
        Vakken3.add(new Vak("HBI04C","IM 5 - Big Data", "3 sp","3",3,false));
        Vakken3.add(new Vak("HBI07C","Business Ethics", "3 sp","3",3,false));
        Vakken3.add(new Vak("HBI12C","ICT 5: Creative Entrepreneurship", "3 sp","3",3,false));
    }
    private void VakkenEMA(){
        VakkenEmA = new ArrayList<>();
        VakkenEmA.add(new Vak("OH3101","Software Engineering 4", "6 sp","6",2,false));
        VakkenEmA.add(new Vak("OH3102","Application Development 4", "6 sp","6",2,false));
        VakkenEmA.add(new Vak("OH3103","Database Development 4", "6 sp","6",2,false));
        VakkenEmA.add(new Vak("HBI03C","Software Engineering 5: Software testing", "4 sp","4",3,false));
        VakkenEmA.add(new Vak("HBI84B","Application Development 5", "4 sp","4",3,false));
        VakkenEmA.add(new Vak("OH3107","Usability and Interaction Design", "4 sp","4",3,false));
        VakkenEmA.add(new Vak("OH3107","Integration project software", "9 sp","9",3,false));
    }
    private void VakkenEMB(){
        VakkenEmB = new ArrayList<>();
        VakkenEmB.add(new Vak("OH3104","Mobile and Internet 4", "6 sp","6",2,false));
        VakkenEmB.add(new Vak("OH3105","System Management 4", "6 sp","6",2,false));
        VakkenEmB.add(new Vak("OH4101","Network Management 4", "6 sp","6",2,false));
        VakkenEmB.add(new Vak("HBI87B","System Management 5 : Datacenter and cloud", "4 sp","4",3,false));
        VakkenEmB.add(new Vak("HBI97B","Security Management 5 and Information Security", "4 sp","4",3,false));
        VakkenEmB.add(new Vak("OH3108","Mobile & Internet 5: Smart App", "4 sp","4",3,false));
        VakkenEmB.add(new Vak("OH3109","Integration project Internet and Cloud", "9 sp","9",3,false));
    }
    private void VakkenEF3(){
        VakkenEF3 = new ArrayList<>();
        VakkenEF3.add(new Vak("HBI33A","Sales and Customer interaction", "3 sp","3",3,false));
        VakkenEF3.add(new Vak("HBI47B","Content management", "3 sp","3",3,false));
        VakkenEF3.add(new Vak("HBI53B","Advanced Switching", "6 sp","6",3,false));
        VakkenEF3.add(new Vak("HBI54B","Advanced Routing", "6 sp","6",3,false));
        VakkenEF3.add(new Vak("OBI01A","ICT Partner Training", "3 sp","3",3,false));
        VakkenEF3.add(new Vak("OBI02A","ICT Partner Training B", "4 sp","4",3,false));
        VakkenEF3.add(new Vak("OBI03A","ICT Partner Training C", "5 sp","5",3,false));
        VakkenEF3.add(new Vak("OBI04A","ICT Partner Training D", "6 sp","6",3,false));
    }
    private void VakkenOption(){
        VakkenOF3 = new ArrayList<>();
        VakkenOF3.add(new Vak("OBI07A","Intercommunautaire stagemobiliteit","24 sp","24",3,false));
        VakkenOF3.add(new Vak("HBI89B","Stagemobiliteit in Vlaanderen","24 sp", "24",3,false));
        VakkenOF3.add(new Vak("HBI90B","Stagemobiliteit in Europa","24 sp","24",3,false));
        VakkenOF3.add(new Vak("HBI91B","Stagemobiliteit buiten Europa","24 sp","24",3,false));
        VakkenOF3.add(new Vak("OS3000","Buiten Europa: voorbereid op stage","3 sp","3",3,false));
        VakkenOF3.add(new Vak("HBI92B","Studiemobiliteit in of buiten Europa", "3 sp", "3",3,false));
        VakkenOF3.add(new Vak("OBI05A","Option 5 : Werkplekleren: ICT Partner Training","12 sp","12",3,false));
        VakkenOF3.add(new Vak("OBI06A", "Option 5 : Stageproject","12 sp","12",3,false));
    }




}
