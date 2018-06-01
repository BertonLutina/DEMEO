package app.stucre;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.todddavies.components.progressbar.ProgressWheel;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class ElectivesModulesOptionA extends Fragment implements SearchView.OnQueryTextListener{


    private RecyclerView recyclerViewEmA;
    private courseAdapter cAEMA;
    private List<Vak> VakkenEmA = new ArrayList<>();
    private List<Vak> newVakkenFase2 = new ArrayList<>();
    private List<Vak> newVakkenFase3 = new ArrayList<>();
    private ProgressWheel progressWheelEMA;
    private View dutiesLayout;
    private int count = 0;
    private Button btnSend;
    private Switch selectall;
    private int clickbutton = 0;
    private HashMap<String,Integer> scoreVak = new HashMap<String, Integer>();
    private int clicks = 0;
    private boolean checked ;
    private List <String> vakkenText = new ArrayList<>();
    private List <String> vakkenText2 = new ArrayList<>();

    EventBus bus = EventBus.getDefault();
    private int percent = 0;
    private Integer tellen = 0;


    public ElectivesModulesOptionA(){

    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference EMdatabase = database.getReference("Bedrijfskunde/TI/Electives Modules/Option A: Design and Build Software");


    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getActivity().getIntent();

        if(null != (ArrayList<Vak>) intent.getSerializableExtra("D2") ){
        newVakkenFase2 = (ArrayList<Vak>) intent.getSerializableExtra("D2");
        vakkenText.clear();
        for(Vak vakD2 : newVakkenFase2){

            vakkenText.add(vakD2.getCourse());

        }

            count += intent.getIntExtra("Vaken",0);
        }

        if(null != (ArrayList<Vak>) intent.getSerializableExtra("D3") ){
            newVakkenFase3 = (ArrayList<Vak>) intent.getSerializableExtra("D3");
            vakkenText.clear();
            for(Vak vakD2 : newVakkenFase3){

                vakkenText2.add(vakD2.getCourse());

            }

            tellen += intent.getIntExtra("Vaken2",0);

        }
        percent += 6 * count;






    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_electives_modules_option, container, false);

        VakkenDatabase();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewEmA = (RecyclerView) view.findViewById(R.id.emoptionA);
        recyclerViewEmA.setHasFixedSize(true);
        recyclerViewEmA.setLayoutManager(new LinearLayoutManager(getContext()));

        cAEMA= new courseAdapter(getContext(),VakkenEmA);
        recyclerViewEmA.setAdapter(cAEMA);
        //cAEMA.notifyDataSetChanged();

        dutiesLayout = getActivity().findViewById(R.id.slideUpCreditsView);
        LinearLayout lay = dutiesLayout.findViewById(R.id.Progress_bar_points);
        btnSend = (Button) lay.findViewById(R.id.versturen_credits);
        progressWheelEMA = (ProgressWheel) lay.findViewById(R.id.count_progressBar);
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        clickOnVakken();

    }

    @Override
    public void onStart() {
        super.onStart();
       //listViewEma.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.elective_modules_menu_bar,menu);
        MenuItem item = menu.findItem(R.id.menu_search_EM);

        SearchView dutiesSearch = (SearchView) item.getActionView();
        dutiesSearch.setOnQueryTextListener(this);

        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(getContext(),"search and filter course in Option A",Toast.LENGTH_SHORT).show();


                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(getContext(),"close search.",Toast.LENGTH_SHORT).show();
                cAEMA.setFilter(VakkenEmA);
                cAEMA.notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Vak> filteredVakken = filter(VakkenEmA,newText);
        cAEMA.setFilter(filteredVakken);
        cAEMA.notifyDataSetChanged();
        return true;
    }



    // Here we will take the list and filter
    private List<Vak> filter(List<Vak> vakken1, String query) {
        //change the string in lowercase
        query = query.toLowerCase();

        // create a new ArrayList called filteredVakken give final instate of public
        final List<Vak> filteredVakken = new ArrayList<>();

        // Search in your collection of your current Object
        for (Vak vakken : vakken1){
            // create a string "Text" and put all the course  of the collection of the object
            final String text = vakken.getCourse().toLowerCase();
            //search if form the first it match withe the courses
            if(text.contains(query)){
                // put it in the nieuw Arraylist youve made filteredVakken Arraylist
                filteredVakken.add(vakken);
            }
        }

        // return the filtered Arraylist
        return filteredVakken;

    }


    private void VakkenDatabase(){

        EMdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot fase: children) {

                    String fases = fase.getKey();

                    if (TextUtils.equals(fases,"fase 2")){

                        Iterable<DataSnapshot> kids = fase.getChildren();

                        for (DataSnapshot kid : kids) {
                            Object course_id = kid.child("COURSE_ID").getValue(Object.class);
                            Object course = kid.child("COURSE").getValue(Object.class);
                            Object credit = kid.child("CREDIT").getValue(Object.class);
                            Object creditPunten = kid.child("CREDITPOINT").getValue(Object.class);
                            Object fa = kid.child("FASE").getValue(Object.class);
                            Object score = kid.child("SCORE").getValue(Object.class);
                            Object succeeded = kid.child("SUCCEEDED").getValue(Object.class);
                            VakkenEmA.add(new Vak(course_id.toString(), course.toString(), credit.toString(),Integer.parseInt(creditPunten.toString()),Integer.parseInt(fa.toString()),Integer.parseInt(score.toString()),Boolean.valueOf(succeeded.toString()),false));

                            cAEMA.notifyDataSetChanged();


                        }

                    }else if(TextUtils.equals(fases,"fase 3")){

                        Iterable<DataSnapshot> kids = fase.getChildren();

                        for (DataSnapshot kid : kids) {
                            Object course_id = kid.child("COURSE_ID").getValue(Object.class);
                            Object course = kid.child("COURSE").getValue(Object.class);
                            Object credit = kid.child("CREDIT").getValue(Object.class);
                            Object creditPunten = kid.child("CREDITPOINT").getValue(Object.class);
                            Object fa = kid.child("FASE").getValue(Object.class);
                            Object score = kid.child("SCORE").getValue(Object.class);
                            Object succeeded = kid.child("SUCCEEDED").getValue(Object.class);
                            VakkenEmA.add(new Vak(course_id.toString(), course.toString(), credit.toString(),Integer.parseInt(creditPunten.toString()),Integer.parseInt(fa.toString()),Integer.parseInt(score.toString()),Boolean.valueOf(succeeded.toString()),false));

                            cAEMA.notifyDataSetChanged();


                        }

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void clickOnVakken(){

        cAEMA.setOnItemClickListener(new courseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {


                //String plaats = Vakken1.get(position).getCourse();
                Integer point = VakkenEmA.get(position).getCreditPunten();
                boolean checked = VakkenEmA.get(position).isChecked();

                if(!checked){
                    VakkenEmA.get(position).setChecked(true);
                    vakkenText.add(VakkenEmA.get(position).getCourse());

                    if(!(count> 60))
                    {
                        count += point;
                        int percent = (360/60) * (count+1);
                        Toasty.custom(getContext(), "+ "+count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.DKGRAY,Toast.LENGTH_SHORT,true,true).show();
                        if(VakkenEmA.get(position).getFase() == 2){
                            newVakkenFase2.add(VakkenEmA.get(position));
                            bus.post(new electivesModules.OpgenomenVakken(vakkenText));
                            bus.post(new electivesModules.VakkenSturen(newVakkenFase2));
                        }
                        else
                            {
                                newVakkenFase3.add(VakkenEmA.get(position));
                                bus.post(new electivesModules.VakkenSturen3(newVakkenFase3));

                            }

                        progressWheelEMA.setProgress(percent);
                        progressWheelEMA.setText(Integer.toString(count)+" sp");

                        if(VakkenEmA.get(position).getFase() == 3){
                            tellen += VakkenEmA.get(position).getCreditPunten();
                            vakkenText2.add(VakkenEmA.get(position).getCourse());
                            if(tellen == 30){
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                                builder.setMessage("Would like to continue?")
                                        .setTitle("Go to Electives")
                                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Intent EleMa = new Intent(getActivity(),electives.class);
                                                EleMa.putExtra("D3E",tellen);
                                                EleMa.putExtra("CELE3",((ArrayList<String>) vakkenText2));
                                                EleMa.putExtra("Vakken3",(ArrayList<Vak>)newVakkenFase3);
                                                startActivity(EleMa);
                                            }
                                        }).setNegativeButton("Stop", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        return;
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    }else{
                        Toasty.error(getContext(),"60 sp is de limiet => Fase 3", Toast.LENGTH_SHORT).show();
                    }

                    if (count == 60) {
                        progressWheelEMA.setBarColor(Color.	rgb(0,128,0));
                        //btnSend.setEnabled(true);
                        //btnSend.setBackgroundColor(Color.rgb(0,128,0));
                    }else if(count >= 45 && count < 60){
                        progressWheelEMA.setBarColor(Color.rgb(255,69,0));
                    }else if (count >= 30 && count < 45) {
                        progressWheelEMA.setBarColor(Color.rgb(255,140,0));
                    }else if (count >= 15 && count < 30) {
                        progressWheelEMA.setBarColor(Color.rgb(255,165,0));
                    }else if (count >= 0 && count < 15) {
                        progressWheelEMA.setBarColor(Color.	rgb(255,215,0));
                    }


                }else{
                    VakkenEmA.get(position).setChecked(false);
                    vakkenText.remove(VakkenEmA.get(position).getCourse());
                    if(!(count < 0)){
                        count -= point;
                        int percent = (360/60) * (count+1);
                        Toasty.custom(getContext(), "- "+count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.rgb(204,204,0),Toast.LENGTH_SHORT,true,true).show();
                        progressWheelEMA.setProgress(percent);
                        progressWheelEMA.setText(Integer.toString(count)+" sp");
                    }else{
                        Toasty.error(getContext()," MAG niet meer onder de 0", Toast.LENGTH_SHORT).show();
                    }
                    if (count == 60) {
                        progressWheelEMA.setBarColor(Color.	rgb(0,128,0));
                    }else if(count >= 45 && count < 60){
                        progressWheelEMA.setBarColor(Color.rgb(255,69,0));
                        //btnSend.setEnabled(false);
                    }else if (count >= 30 && count < 45) {
                        progressWheelEMA.setBarColor(Color.rgb(255,140,0));
                    }else if (count >= 15 && count < 30) {
                        progressWheelEMA.setBarColor(Color.rgb(255,165,0));
                    }else if (count >= 0 && count < 15) {
                        progressWheelEMA.setBarColor(Color.	rgb(255,215,0));
                    }
                }

            }
        });

        cAEMA.setOnItemLongClickListener(new courseAdapter.onItemLongClickListerner() {
            @Override
            public boolean onItemLongClick(int position) {
                clickbutton++;
                final int pos = position;
                boolean geslaagd = VakkenEmA.get(pos).isGeslaagd();

                String course = VakkenEmA.get(pos).getCourse();
                boolean  checked = VakkenEmA.get(pos).isChecked();

                Integer getScore = VakkenEmA.get(pos).getScore();
                clicks = 0;

                scoreVak.put(course,getScore);
                Integer value = scoreVak.get(course);





                if(value == 0)
                {

                    NietGeslaagdeVakken(pos,course,geslaagd);
                }
                else if (value < 10 )
                {

                    NietGeslaagdeVakken(pos,course,geslaagd);
                }
                else if (value>= 10 || value <= 20)
                {

                    Vakken(pos, geslaagd);
                }





                return true;
            }
        });
    }

    public void Vakken(final int pos, boolean geslaagdvak){


        geslaagdvak = true;
        String textScore = Integer.toString(VakkenEmA.get(pos).getScore());
        Integer Score = VakkenEmA.get(pos).setScore(Integer.parseInt(textScore));
        final String course = VakkenEmA.get(pos).getCourse();
        Integer getScore = VakkenEmA.get(pos).getScore();

        final AlertDialog.Builder dialogvak = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();


        View dialogBack = inflater.inflate(R.layout.dialogscore, null);

        TextView vak = (TextView) dialogBack.findViewById(R.id.vakDialoog);
        TextView score = (TextView) dialogBack.findViewById(R.id.score);
        TextView geslaagd = (TextView) dialogBack.findViewById(R.id.txtgeslaagd);

        final Integer value = scoreVak.get(course);

        vak.setText(course);

        if (value > 10 || value < 20) {
            geslaagd.setText("Geslaagd");
            geslaagd.setBackgroundColor(Color.rgb(20, 120, 0));
            geslaagd.setTextColor(Color.rgb(246, 246, 246));
            score.setText(getScore.toString()+ "/20");
        } else if (value == 0) {
            geslaagd.setText("Onbekend");
            geslaagd.setBackgroundColor(Color.rgb(120, 120, 120));
            geslaagd.setTextColor(Color.rgb(246, 246, 246));
            score.setText(getScore.toString()+ "/20");
        }
        if (value < 10) {
            geslaagd.setText("Onvoldoende");
            geslaagd.setBackgroundColor(Color.rgb(128, 20, 0));
            geslaagd.setTextColor(Color.rgb(246, 246, 246));
            score.setText(getScore.toString()+ "/20");
        }

        dialogvak.setView(dialogBack).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getContext(),"Score: " + value+" /20",Toast.LENGTH_SHORT).show();

            }
        });

        dialogvak.setView(dialogBack).setNegativeButton("Rewrite", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(VakkenEmA.get(pos).isGeslaagd() == false)
                    NietGeslaagdeVakken(pos,course,VakkenEmA.get(pos).isGeslaagd());


            }
        });


        AlertDialog alertVak = dialogvak.create();

        alertVak.show();





    }

    public void NietGeslaagdeVakken(final int pos, String course, boolean geslaagd){

        geslaagd = false;
        AlertDialog.Builder inputVak = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View inputView = inflater.inflate(R.layout.inputscore, null);

        final EditText inputText = (EditText) inputView.findViewById(R.id.cijferinputvak);
        TextView vakText = (TextView) inputView.findViewById(R.id.vakinput);
        vakText.setText(course);


        inputVak.setView(inputView).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                clicks++;
                checked = true;
                if(scoreVak == null)
                    Toasty.error(getContext(), "Er werd geen vak geselecteerd", Toast.LENGTH_SHORT).show();

                if (clicks > 4)
                {
                    Toasty.error(getContext(), "Je mag maar 3 keren uw score veranderen!!", Toast.LENGTH_SHORT).show();
                    return;
                }



                VakkenEmA.get(pos).setScore(Integer.parseInt(inputText.getText().toString()));

                scoreVak.put(VakkenEmA.get(pos).getCourse(), VakkenEmA.get(pos).getScore());

                Integer value = scoreVak.get(VakkenEmA.get(pos).getCourse());

                if ((value < 0 || value > 20))
                    Toasty.error(getContext(), "Score moet tussen 0 en 20 zijn!", Toast.LENGTH_SHORT).show();


                if ( value > 0 || value < 10 )
                {
                    VakkenEmA.get(pos).setGeslaagd(false);

                }
                else if (value>= 10 || value <= 20)
                {
                    VakkenEmA.get(pos).setGeslaagd(true);
                }

                Vakken(pos,VakkenEmA.get(pos).isGeslaagd());



            }
        });


        AlertDialog dialog = inputVak.create();

        dialog.show();
    }

}
