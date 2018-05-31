package app.stucre;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.todddavies.components.progressbar.ProgressWheel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class ElectivesModulesOptionB extends Fragment implements SearchView.OnQueryTextListener{

    private RecyclerView recyclerViewEmB;
    private courseAdapter cAEMB;

    private ArrayList<Vak> VakkenEmB = new ArrayList<>();

    private ProgressWheel progressWheelEMB;
    private View dutiesLayout;
    private int count = 0;
    private Button btnSend;
    private Switch selectall;
    private int clickbutton = 0;
    private int clicks =0;
    private HashMap<String,Integer> scoreVak = new HashMap<String, Integer>();
    private boolean checked ;

    public ElectivesModulesOptionB(){

    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference EMdatabases = database.getReference("Bedrijfskunde/TI/Electives Modules/Option B: Manage Internet and Cloud");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_electives_modules_option_b, container, false);

        VakkenDatabase();

        // Inflate the layout for this fragment

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewEmB = (RecyclerView) view.findViewById(R.id.emoptionB);
        recyclerViewEmB.setHasFixedSize(true);
        recyclerViewEmB.setLayoutManager(new LinearLayoutManager(getContext()));

        cAEMB = new courseAdapter(getContext(),VakkenEmB);
        recyclerViewEmB.setAdapter(cAEMB);
        cAEMB.notifyDataSetChanged();

        dutiesLayout = getActivity().findViewById(R.id.slideUpCreditsView);
        LinearLayout lay = dutiesLayout.findViewById(R.id.Progress_bar_points);
        btnSend = (Button) lay.findViewById(R.id.versturen_credits);
        progressWheelEMB = (ProgressWheel) lay.findViewById(R.id.count_progressBar);
        setHasOptionsMenu(true);

        // Click
        clickOnVakken();
        selectall = (Switch) getActivity().findViewById(R.id.selectAllSwitchEMB);

        selectall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    for (Vak vak : VakkenEmB){
                        vak.setChecked(true);
                        boolean checked = vak.isChecked();
                        String point = vak.getCreditPunten();
                        count =+ Integer.parseInt(point);
                        count++;
                        int percent = (360/60) * (count+1);
                        progressWheelEMB.setProgress(percent);
                        progressWheelEMB.setText(Integer.toString(count)+" sp");
                        cAEMB.notifyDataSetChanged();
                    }
                    Toasty.custom(getContext(), "+ "+ count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.DKGRAY,Toast.LENGTH_SHORT,true,true).show();
                }else{
                    for (Vak vak : VakkenEmB) {
                        vak.setChecked(false);
                        boolean checked = vak.isChecked();
                        String point = vak.getCreditPunten();
                        count = -Integer.parseInt(point);
                        count--;
                        int percent = (360 / 60) * (count + 1);
                        progressWheelEMB.setProgress(percent);
                        progressWheelEMB.setText(Integer.toString(count) + " sp");
                        cAEMB.notifyDataSetChanged();
                    }

                }

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //listViewEmb.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
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
                Toast.makeText(getContext(),"search and filter course in Option B",Toast.LENGTH_SHORT).show();


                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(getContext(),"close search.",Toast.LENGTH_SHORT).show();
                cAEMB.setFilter(VakkenEmB);
                cAEMB.notifyDataSetChanged();
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
        final List<Vak> filteredVakken = filter(VakkenEmB,newText);
        cAEMB.setFilter(filteredVakken);
        cAEMB.notifyDataSetChanged();
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
        EMdatabases.addValueEventListener(new ValueEventListener() {
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
                            VakkenEmB.add(new Vak(course_id.toString(), course.toString(), credit.toString(),creditPunten.toString(),Integer.parseInt(fa.toString()),Integer.parseInt(score.toString()),Boolean.valueOf(succeeded.toString()),false));

                            cAEMB.notifyDataSetChanged();


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
                            VakkenEmB.add(new Vak(course_id.toString(), course.toString(), credit.toString(),creditPunten.toString(),Integer.parseInt(fa.toString()),Integer.parseInt(score.toString()),Boolean.valueOf(succeeded.toString()),false));

                            cAEMB.notifyDataSetChanged();


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
        cAEMB.setOnItemClickListener(new courseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {


                //String plaats = Vakken1.get(position).getCourse();
                String point = VakkenEmB.get(position).getCreditPunten();
                boolean checked = VakkenEmB.get(position).isChecked();

                if(!checked){
                    VakkenEmB.get(position).setChecked(true);
                    if(!(count> 60)) {
                        count += Integer.parseInt(point);
                        int percent = (360/60) * (count+1);
                        Toasty.custom(getContext(), "+ "+count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.DKGRAY,Toast.LENGTH_SHORT,true,true).show();
                        progressWheelEMB.setProgress(percent);
                        progressWheelEMB.setText(Integer.toString(count)+" sp");
                    } else{
                        Toasty.error(getContext()," 60 sp is de limiet => Fase 2", Toast.LENGTH_SHORT).show();
                    }
                    if (count == 60) {
                        progressWheelEMB.setBarColor(Color.	rgb(0,128,0));
                        //btnSend.setEnabled(true);
                        //btnSend.setBackgroundColor(Color.rgb(0,128,0));
                    }else if(count >= 45 && count < 60){
                        progressWheelEMB.setBarColor(Color.rgb(255,69,0));
                    }else if (count >= 30 && count < 45) {
                        progressWheelEMB.setBarColor(Color.rgb(255,140,0));
                    }else if (count >= 15 && count < 30) {
                        progressWheelEMB.setBarColor(Color.rgb(255,165,0));
                    }else if (count >= 0 && count < 15) {
                        progressWheelEMB.setBarColor(Color.	rgb(255,215,0));
                    }
                }else{
                    VakkenEmB.get(position).setChecked(false);

                    if(!(count < 0)) {
                        count -= Integer.parseInt(point);
                        int percent = (360/60) * (count+1);
                        Toasty.custom(getContext(), "* "+count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.rgb(204,204,0),Toast.LENGTH_SHORT,true,true).show();
                        progressWheelEMB.setProgress(percent);
                        progressWheelEMB.setText(Integer.toString(count)+" sp");

                    }else{
                        Toasty.error(getContext()," MAG niet onder de 0", Toast.LENGTH_SHORT).show();
                    }

                    if (count == 60) {
                        progressWheelEMB.setBarColor(Color.	rgb(0,128,0));
                    }else if(count >= 45 && count < 60){
                        progressWheelEMB.setBarColor(Color.rgb(255,69,0));
                        //btnSend.setEnabled(false);
                    }else if (count >= 30 && count < 45) {
                        progressWheelEMB.setBarColor(Color.rgb(255,140,0));
                    }else if (count >= 15 && count < 30) {
                        progressWheelEMB.setBarColor(Color.rgb(255,165,0));
                    }else if (count >= 0 && count < 15) {
                        progressWheelEMB.setBarColor(Color.	rgb(255,215,0));
                    }
                }


            }
        });

        cAEMB.setOnItemLongClickListener(new courseAdapter.onItemLongClickListerner() {
            @Override
            public boolean onItemLongClick(int position) {
                clickbutton++;
                final int pos = position;
                boolean geslaagd = VakkenEmB.get(pos).isGeslaagd();

                String course = VakkenEmB.get(pos).getCourse();
                boolean  checked = VakkenEmB.get(pos).isChecked();

                Integer getScore = VakkenEmB.get(pos).getScore();
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
        String textScore = Integer.toString(VakkenEmB.get(pos).getScore());
        Integer Score = VakkenEmB.get(pos).setScore(Integer.parseInt(textScore));
        final String course = VakkenEmB.get(pos).getCourse();
        Integer getScore = VakkenEmB.get(pos).getScore();

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
                if(VakkenEmB.get(pos).isGeslaagd() == false)
                    NietGeslaagdeVakken(pos,course,VakkenEmB.get(pos).isGeslaagd());


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



                VakkenEmB.get(pos).setScore(Integer.parseInt(inputText.getText().toString()));

                scoreVak.put(VakkenEmB.get(pos).getCourse(), VakkenEmB.get(pos).getScore());

                Integer value = scoreVak.get(VakkenEmB.get(pos).getCourse());

                if ((value < 0 || value > 20))
                    Toasty.error(getContext(), "Score moet tussen 0 en 20 zijn!", Toast.LENGTH_SHORT).show();

                if ( value == 0 || value < 10 )
                {
                    VakkenEmB.get(pos).setGeslaagd(false);

                }
                else if (value>= 10 || value <= 20)
                {
                    VakkenEmB.get(pos).setGeslaagd(true);
                }

                Vakken(pos,VakkenEmB.get(pos).isGeslaagd());



            }
        });


        AlertDialog dialog = inputVak.create();

        dialog.show();
    }

}
