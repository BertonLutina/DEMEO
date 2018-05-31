package app.stucre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class Opleiding extends AppCompatActivity {

    private CardView oplBM;
    private CardView oplOF;
    private CardView oplFA;
    private CardView oplIT;


    // Database Firebase
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    // Duties
    private DatabaseReference Modules = database.getReference();

    private List<Vak> Vakken1;
    private List<Vak> Vakken2;
    private List<Vak> Vakken3;
    private List<String> Vakken1_voltijdigheid;
    private List<String> Vakken2_voltijdigheid;
    private List <Vak>VakkenOF3;
    private List<Vak> VakkenEF3;
    private List<Vak> VakkenEmA;
    private List<Vak> VakkenEmB;


    // Lijst voldigheden

    private String [] application3;
    private String [] software3;
    private String [] netwerk3;
    private String [] ict3;
    private String [] database3;
    private String [] information3;
    private String [] system3;
    private String[] mobile3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opleiding);



        oplBM = findViewById(R.id.opleidingBM);
        oplOF = findViewById(R.id.opleidingOf);
        oplFA= findViewById(R.id.opleidingFA);
        oplIT = findViewById(R.id.opleidingIT);

        oplBM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(getBaseContext(),"It will be avaible in the update version", Toast.LENGTH_SHORT).show();
            }
        });

        oplOF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(getBaseContext(),"It will be avaible in the update version", Toast.LENGTH_SHORT).show();
            }
        });

        oplFA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.info(getBaseContext(),"It will be avaible in the update version", Toast.LENGTH_SHORT).show();
            }
        });

        oplIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PassDataDuties();
                PassDataElectives();
                PassDataElectivesModules();
                PassDataOptions();
                Intent modules = new Intent(Opleiding.this,Courses.class);
                startActivity(modules);
            }
        });
    }





    private void VakkenDutiesFase1(){
        Vakken1 = new ArrayList<>();
        Vakken1.add(new Vak("HBI09B","ICT Organisation 1", "4 sp","4",1,0,false,true));
        Vakken1.add(new Vak("HBI10B","ICT Organisation 2", "4 sp","4",1,0,false,true));
        Vakken1.add(new Vak("HBI18B","Mobile en internet 1", "3 sp","3",1,0,false,true));
        Vakken1.add(new Vak("HBI19B","Mobile en internet 2", "3 sp","3",1,0,false,true));
        Vakken1.add(new Vak("HBI20B","System Management 1", "3 sp","3",1,0,false,true));
        Vakken1.add(new Vak("HBI21B","System Management 2", "3 sp","3",1,0,false,true));
        Vakken1.add(new Vak("HBI22B","Network Management 1", "3 sp","3",1,0,false,true));
        Vakken1.add(new Vak("HBI23B","Network Management 2", "3 sp","3",1,0,false,true));
        Vakken1.add(new Vak("HBI59B","Communicatietraining 1", "4 sp","4",1,0,false,true));
        Vakken1.add(new Vak("HBI60B","Communicatietraining 2", "4 sp","4",1,0,false,true));
        Vakken1.add(new Vak("HBI61B","Information management 1", "3 sp","3",1,0,false,true));
        Vakken1.add(new Vak("HBI62B","Information management 2", "3 sp","3",1,0,false,true));
        Vakken1.add(new Vak("HBI63B","Database development 1", "3 sp","3",1,0,false,true));
        Vakken1.add(new Vak("HBI64B","Database development 2", "3 sp","3",1,0,false,true));
        Vakken1.add(new Vak("HBI65B","Application development 1", "4 sp","4",1,0,false,true));
        Vakken1.add(new Vak("HBI66B","Application development 2", "4 sp","4",1,0,false,true));
        Vakken1.add(new Vak("HBI77B","Software engineering 1", "3 sp","3",1,0,false,true));
        Vakken1.add(new Vak("HBI78B","Software engineering 2", "3 sp","3",1,0,false,true));






    }
    private void VakkenDutiesFase2(){
        Vakken2 = new ArrayList<>();
        application3 = new String[]{"Application development 1","Application development 2","Software engineering 1","Software engineering 2"};
        software3 = new String[]{"Software engineering 1","Software engineering 2"};
        netwerk3 = new String[]{"Network Management 1","Network Management 2"};
        system3 = new String[]{"System Management 1","System Management 2"};
        ict3 = new String[] {"ICT Organisation 1","ICT Organisation 2"};
        database3 = new String[] {"Database development 1","Database development 2","Information management 1"};
        mobile3 = new String []{"Mobile en internet 2"};

        Vakken1_voltijdigheid = new ArrayList<>();
        Vakken2.add(new Vak("HBI02C","ICT Organisation 4", "4 sp","4",2,0,false,true));
        Vakken2.add(new Vak("HBI25B","ICT Organisation 3", "4 sp","4",2,0,false,ict3,true));
        Vakken2.add(new Vak("HBI34B","Mobile en internet 3", "3 sp ","3",2,0,false,mobile3,true));
        Vakken2.add(new Vak("HBI36B","System Management 3", "3 sp","3",2,0,false,system3,true));
        Vakken2.add(new Vak("HBI38B","Network Management 3", "3 sp","3",2,0,false,netwerk3,true));
        Vakken2.add(new Vak("HBI68B","Communicatietraining 4", "4 sp","4",2,0,false,true));
        Vakken2.add(new Vak("HBI69B","Information management 3", "3sp","3",2,0,false,true));
        Vakken2.add(new Vak("HBI70B","Information management 4", "3sp","3",2,0,false,true));
        Vakken2.add(new Vak("HBI71B","Database development 3", "3 sp","3",2,0,false,database3,true));
        Vakken2.add(new Vak("HBI73B","Application development 3", "3 sp","3",2,0,false,application3,true));
        Vakken2.add(new Vak("OH3100","Software Engineering 3", "3 sp","3",2,0,false,software3,true));
        Vakken2.add(new Vak("OH4100","Communicatietraining 3", "6 sp","6",2,0,false,true));
    }
    private void VakkenDutiesFase3(){
        Vakken3 = new ArrayList<>();
        Vakken3.add(new Vak("HBI04C","IM 5 - Big Data", "3 sp","3",3,0,false,true));
        Vakken3.add(new Vak("HBI07C","Business Ethics", "3 sp","3",3,0,false,true));
        Vakken3.add(new Vak("HBI12C","ICT 5: Creative Entrepreneurship", "3 sp","3",3,0,false,true));
    }
    private void VakkenEMA(){
        VakkenEmA = new ArrayList<>();
        VakkenEmA.add(new Vak("OH3101","Software Engineering 4", "6 sp","6",2,0,false,true));
        VakkenEmA.add(new Vak("OH3102","Application Development 4", "6 sp","6",2,0,false,true));
        VakkenEmA.add(new Vak("OH3103","Database Development 4", "6 sp","6",2,0,false,true));
        VakkenEmA.add(new Vak("HBI03C","Software Engineering 5: Software testing", "4 sp","4",3,0,false,true));
        VakkenEmA.add(new Vak("HBI84B","Application Development 5", "4 sp","4",3,0,false,true));
        VakkenEmA.add(new Vak("OH3107","Usability and Interaction Design", "4 sp","4",3,0,false,true));
        VakkenEmA.add(new Vak("OH3107","Integration project software", "9 sp","9",3,0,false,true));
    }
    private void VakkenEMB(){
        VakkenEmB = new ArrayList<>();
        VakkenEmB.add(new Vak("OH3104","Mobile and Internet 4", "6 sp","6",2,0,false,true));
        VakkenEmB.add(new Vak("OH3105","System Management 4", "6 sp","6",2,0,false,true));
        VakkenEmB.add(new Vak("OH4101","Network Management 4", "6 sp","6",2,0,false,true));
        VakkenEmB.add(new Vak("HBI87B","System Management 5 : Datacenter and cloud", "4 sp","4",3,0,false,true));
        VakkenEmB.add(new Vak("HBI97B","Security Management 5 and Information Security", "4 sp","4",3,0,false,true));
        VakkenEmB.add(new Vak("OH3108","Mobile & Internet 5: Smart App", "4 sp","4",3,0,false,true));
        VakkenEmB.add(new Vak("OH3109","Integration project Internet and Cloud", "9 sp","9",3,0,false,true));
    }
    private void VakkenEF3(){
        VakkenEF3 = new ArrayList<>();
        VakkenEF3.add(new Vak("HBI33A","Sales and Customer interaction", "3 sp","3",3,0,false,true));
        VakkenEF3.add(new Vak("HBI47B","Content management", "3 sp","3",3,0,false,true));
        VakkenEF3.add(new Vak("HBI53B","Advanced Switching", "6 sp","6",3,0,false,true));
        VakkenEF3.add(new Vak("HBI54B","Advanced Routing", "6 sp","6",3,0,false,true));
        VakkenEF3.add(new Vak("OBI01A","ICT Partner Training", "3 sp","3",3,0,false,true));
        VakkenEF3.add(new Vak("OBI02A","ICT Partner Training B", "4 sp","4",3,0,false,true));
        VakkenEF3.add(new Vak("OBI03A","ICT Partner Training C", "5 sp","5",3,0,false,true));
        VakkenEF3.add(new Vak("OBI04A","ICT Partner Training D", "6 sp","6",3,0,false,true));
    }
    private void VakkenOption(){
        VakkenOF3 = new ArrayList<>();
        VakkenOF3.add(new Vak("OBI07A","Intercommunautaire stagemobiliteit","24 sp","24",3,0,false,true));
        VakkenOF3.add(new Vak("HBI89B","Stagemobiliteit in Vlaanderen","24 sp", "24",3,0,false,true));
        VakkenOF3.add(new Vak("HBI90B","Stagemobiliteit in Europa","24 sp","24",3,0,false,true));
        VakkenOF3.add(new Vak("HBI91B","Stagemobiliteit buiten Europa","24 sp","24",3,0,false,true));
        VakkenOF3.add(new Vak("OS3000","Buiten Europa: voorbereid op stage","3 sp","3",3,0,false,true));
        VakkenOF3.add(new Vak("HBI92B","Studiemobiliteit in of buiten Europa", "3 sp", "3",3,0,false,true));
        VakkenOF3.add(new Vak("OBI05A","Werkplekleren: ICT Partner Training","12 sp","12",3,0,false,true));
        VakkenOF3.add(new Vak("OBI06A", "Stageproject","12 sp","12",3,0,false,true));
    }



    public void PassDataDuties(){

        VakkenDutiesFase1();
        VakkenDutiesFase2();
        VakkenDutiesFase3();

        Map<String,String> map = new HashMap<>();
        for(final Vak ef3 : Vakken1){
            String id = ef3.getId();
            map.put("COURSE_ID",ef3.getId());
            map.put("COURSE",ef3.getCourse());
            map.put("CREDIT",ef3.getCredit());
            map.put("CREDITPOINT",ef3.getCreditPunten());
            map.put("SCORE",String.valueOf(ef3.getScore()));
            map.put("FASE",String.valueOf(ef3.getFase()));
            map.put("SUCCEEDED",String.valueOf(ef3.isGeslaagd()));

            Modules.child("Bedrijfskunde").child("TI").child("Duties").child("fase 1").child(id).setValue(map);

        }

        Map<String,String> map2 = new HashMap<>();
        for(final Vak ef3 : Vakken2){
            String id = ef3.getId();
            map2.put("COURSE_ID",ef3.getId());
            map2.put("COURSE",ef3.getCourse());
            map2.put("CREDIT",ef3.getCredit());
            map2.put("CREDITPOINT",ef3.getCreditPunten());
            map2.put("SCORE",String.valueOf(ef3.getScore()));
            map2.put("FASE",String.valueOf(ef3.getFase()));
            map2.put("SUCCEEDED",String.valueOf(ef3.isGeslaagd()));

            Modules.child("Bedrijfskunde").child("TI").child("Duties").child("fase 2").child(id).setValue(map2);

        }

        Map<String,String> map3 = new HashMap<>();
        for(final Vak ef3 : Vakken3){
            String id = ef3.getId();
            map3.put("COURSE_ID",ef3.getId());
            map3.put("COURSE",ef3.getCourse());
            map3.put("CREDIT",ef3.getCredit());
            map3.put("CREDITPOINT",ef3.getCreditPunten());
            map3.put("SCORE",String.valueOf(ef3.getScore()));
            map3.put("FASE",String.valueOf(ef3.getFase()));
            map3.put("SUCCEEDED",String.valueOf(ef3.isGeslaagd()));

            Modules.child("Bedrijfskunde").child("TI").child("Duties").child("fase 3").child(id).setValue(map3);

        }
    }
    public void PassDataElectivesModules(){

        VakkenEMA();
        VakkenEMB();
        Map<String,String> map = new HashMap<>();
        for(final Vak Ema : VakkenEmA){

            String id = Ema.getId();
            map.put("COURSE_ID",Ema.getId());
            map.put("COURSE",Ema.getCourse());
            map.put("CREDIT",Ema.getCredit());
            map.put("CREDITPOINT",Ema.getCreditPunten());
            map.put("SCORE",String.valueOf(Ema.getScore()));
            map.put("FASE",String.valueOf(Ema.getFase()));
            map.put("SUCCEEDED",String.valueOf(Ema.isGeslaagd()));

            if(Ema.getFase() == 2){
            Modules.child("Bedrijfskunde").child("TI").child("Electives Modules").child("Option A: Design and Build Software").child("fase 2").child(id).setValue(map);
            }
            Modules.child("Bedrijfskunde").child("TI").child("Electives Modules").child("Option A: Design and Build Software").child("fase 3").child(id).setValue(map);


        }

        Map<String,String> map2 = new HashMap<>();
        for(final Vak Emb : VakkenEmB){

            String id = Emb.getId();
            map2.put("COURSE_ID",Emb.getId());
            map2.put("COURSE",Emb.getCourse());
            map2.put("CREDIT",Emb.getCredit());
            map2.put("CREDITPOINT",Emb.getCreditPunten());
            map2.put("SCORE",String.valueOf(Emb.getScore()));
            map2.put("FASE",String.valueOf(Emb.getFase()));
            map2.put("SUCCEEDED",String.valueOf(Emb.isGeslaagd()));

            if(Emb.getFase() == 2){
                Modules.child("Bedrijfskunde").child("TI").child("Electives Modules").child("Option B: Manage Internet and Cloud").child("fase 2").child(id).setValue(map2);
            }
            Modules.child("Bedrijfskunde").child("TI").child("Electives Modules").child("Option B: Manage Internet and Cloud").child("fase 3").child(id).setValue(map2);

        }
    }
    public void PassDataElectives(){

        VakkenEF3();
        Map<String,String> map = new HashMap<>();
        for(Vak ef3 : VakkenEF3){

            String id = ef3.getId();
            map.put("COURSE_ID",ef3.getId());
            map.put("COURSE",ef3.getCourse());
            map.put("CREDIT",ef3.getCredit());
            map.put("CREDITPOINT",ef3.getCreditPunten());
            map.put("SCORE",String.valueOf(ef3.getScore()));
            map.put("FASE",String.valueOf(ef3.getFase()));
            map.put("SUCCEEDED",String.valueOf(ef3.isGeslaagd()));

            Modules.child("Bedrijfskunde").child("TI").child("Electives").child(id).setValue(map);

        }
    }
    public void PassDataOptions(){

        VakkenOption();
        Map<String,String> map = new HashMap<>();
        for(final Vak Of3 : VakkenOF3){

            String id = Of3.getId();
            map.put("COURSE_ID",Of3.getId());
            map.put("COURSE",Of3.getCourse());
            map.put("CREDIT",Of3.getCredit());
            map.put("CREDITPOINT",Of3.getCreditPunten());
            map.put("SCORE",String.valueOf(Of3.getScore()));
            map.put("FASE",String.valueOf(Of3.getFase()));
            map.put("SUCCEEDED",String.valueOf(Of3.isGeslaagd()));

            switch (Of3.getCourse())
            {
                case "Stagemobiliteit in Vlaanderen":
                    Modules.child("Bedrijfskunde").child("TI").child("Options").child("Optie 1A: Stagemobiliteit in Vlaanderen").child(id).setValue(map);
                    break;
                    case "Stagemobiliteit in Europa":
                    Modules.child("Bedrijfskunde").child("TI").child("Options").child("Optie 1B: Stagemobiliteit in Europa").child(id).setValue(map);
                    break;
                    case "Stagemobiliteit buiten Europa":
                    Modules.child("Bedrijfskunde").child("TI").child("Options").child("Optie 2: Stagemobiliteit buiten Europa").child(id).setValue(map);
                    break;
                    case "Buiten Europa: voorbereid op stage":
                    Modules.child("Bedrijfskunde").child("TI").child("Options").child("Optie 2: Stagemobiliteit buiten Europa").child(id).setValue(map);
                    break;
                    case "Studiemobiliteit in of buiten Europa":
                    Modules.child("Bedrijfskunde").child("TI").child("Options").child("Optie 3: Studiemobiliteit in of buiten Europa").child(id).setValue(map);
                    break;
                    case "Werkplekleren: ICT Partner Training":
                    Modules.child("Bedrijfskunde").child("TI").child("Options").child("Optie 5: Studiemobiliteit in Europa KORT").child(id).setValue(map);
                    break;
                    case "Stageproject":
                    Modules.child("Bedrijfskunde").child("TI").child("Options").child("Optie 5: Studiemobiliteit in Europa KORT").child(id).setValue(map);
                    break;
                    case "Intercommunautaire stagemobiliteit":
                    Modules.child("Bedrijfskunde").child("TI").child("Options").child("Optie 6: Intercommunautaire stagemobiliteit").child(id).setValue(map);
                    break;



            }


        }
    }

}
