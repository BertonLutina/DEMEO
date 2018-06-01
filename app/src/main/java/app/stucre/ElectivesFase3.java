package app.stucre;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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


public class ElectivesFase3 extends Fragment implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerViewEF3;
    private courseAdapter cAEF3;
    private List<Vak> VakkenEF3 = new ArrayList<>();
    private Vak test;
    private ProgressWheel progressWheelElectivesFase3;
    private View dutiesLayout;
    private int count = 0;
    private Button btnSend;
    private Switch selectall;
    private HashMap<String, Integer> scoreVak = new HashMap<String, Integer>();
    private int clicks = 0;
    private boolean checked ;
    private int clickbutton = 0;


    public ElectivesFase3(){

    }
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference electivFase3 = database.getReference("Bedrijfskunde/TI/Electives");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_electives_fase3, container, false);


        return view;

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewEF3 = (RecyclerView) view.findViewById(R.id.electivesFase3);
        recyclerViewEF3.setHasFixedSize(true);
        recyclerViewEF3.setLayoutManager(new LinearLayoutManager(getContext()));


        // Test vakken
        //Vakken();

        VakkenEF3 = new ArrayList<>();
        //Vakken Van Database
        VakkenDatabase();


        cAEF3= new courseAdapter(getContext(),VakkenEF3);
        recyclerViewEF3.setAdapter(cAEF3);
        //cAEF3.notifyDataSetChanged();

        dutiesLayout = getActivity().findViewById(R.id.slideUpCreditsView);
        LinearLayout lay = dutiesLayout.findViewById(R.id.Progress_bar_points);
        btnSend = (Button) lay.findViewById(R.id.versturen_credits);
        progressWheelElectivesFase3 = (ProgressWheel) lay.findViewById(R.id.count_progressBar);

        clickOnVakken();


        setHasOptionsMenu(true);
        // Inflate the layout for this fragment

    }
    @Override
    public void onStart() {
        super.onStart();
        //listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
    // Implements methode from Search.OnQueryListener
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    // Use
    @Override
    public boolean onQueryTextChange(String newText) {
        //create a new ArrayList filteredVakken and put the filter methode in the arraylist.
        final List<Vak> filteredVakken = filter(VakkenEF3,newText);
        // Here is the magic setfilter methode will change everything the methode comes from the courseAdapter
        cAEF3.setFilter(filteredVakken);
        // Refresh data
        cAEF3.notifyDataSetChanged();
        return false;
    }
    // Menu option to create the Search bar in the actinBar and add the Loop Icon
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.electives_menu_bar,menu);
        MenuItem item = menu.findItem(R.id.menu_search_electives);

        SearchView dutiesSearch = (SearchView) item.getActionView();
        dutiesSearch.setOnQueryTextListener(this);

        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(getContext(),"search course fase 3",Toast.LENGTH_SHORT).show();


                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(getContext(),"close search",Toast.LENGTH_SHORT).show();
                cAEF3.setFilter(VakkenEF3);
                cAEF3.notifyDataSetChanged();
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    // Create a methode filter and give the paramter a Arraylist and a String
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
        electivFase3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child:children) {
                    Object course_id = child.child("COURSE_ID").getValue(Object.class);
                    Object course = child.child("COURSE").getValue(Object.class);
                    Object credit = child.child("CREDIT").getValue(Object.class);
                    Object creditPunten = child.child("CREDITPOINT").getValue(Object.class);
                    Object fase = child.child("FASE").getValue(Object.class);
                    Object score = child.child("SCORE").getValue(Object.class);
                    Object succeeded = child.child("SUCCEEDED").getValue(Object.class);
                    VakkenEF3.add(new Vak(course_id.toString(), course.toString(), credit.toString(),Integer.parseInt(creditPunten.toString()),Integer.parseInt(fase.toString()),Integer.parseInt(score.toString()),Boolean.valueOf(succeeded.toString()),false));

                    cAEF3.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void clickOnVakken(){
        cAEF3.setOnItemClickListener(new courseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {


                //String plaats = Vakken1.get(position).getCourse();
               Integer point = VakkenEF3.get(position).getCreditPunten();
                boolean checked = VakkenEF3.get(position).isChecked();
                if(!checked){
                    VakkenEF3.get(position).setChecked(true);
                    if(!(count> 60)){
                        count += point;
                        Toasty.custom(getContext(), "+ "+count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.DKGRAY,Toast.LENGTH_SHORT,true,true).show();
                        int percent = (360/60) * count;
                        progressWheelElectivesFase3.setProgress(percent);
                        progressWheelElectivesFase3.setText(Integer.toString(count)+" sp");
                        }else{
                        Toasty.error(getContext()," 60 sp is de limiet => Fase 3", Toast.LENGTH_SHORT).show();
                        count = count + 0;
                    }
                    if (count == 60) {
                        progressWheelElectivesFase3.setBarColor(Color.	rgb(0,128,0));
                        //btnSend.setEnabled(true);
                        //btnSend.setBackgroundColor(Color.rgb(0,128,0));
                    }else if(count >= 45 && count < 60){
                        progressWheelElectivesFase3.setBarColor(Color.rgb(255,69,0));
                    }else if (count >= 30 && count < 45) {
                        progressWheelElectivesFase3.setBarColor(Color.rgb(255,140,0));
                    }else if (count >= 15 && count < 30) {
                        progressWheelElectivesFase3.setBarColor(Color.rgb(255,165,0));
                    }else if (count >= 0 && count < 15) {
                        progressWheelElectivesFase3.setBarColor(Color.	rgb(255,215,0));
                    }
                }else{
                    VakkenEF3.get(position).setChecked(false);
                    if(!(count < 0)){
                        count -= point;
                        Toasty.custom(getContext(), "- "+count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.rgb(204,204,0),Toast.LENGTH_SHORT,true,true).show();
                        int percent = (360/60) * count;
                        progressWheelElectivesFase3.setProgress(percent);
                        progressWheelElectivesFase3.setText(Integer.toString(count)+" sp");
                    }else{
                        Toasty.error(getContext()," MAG niet onder nul", Toast.LENGTH_SHORT).show();
                    }
                    if (count == 60) {
                        progressWheelElectivesFase3.setBarColor(Color.	rgb(0,128,0));
                    }else if(count >= 45 && count < 60){
                        progressWheelElectivesFase3.setBarColor(Color.rgb(255,69,0));
                        //btnSend.setEnabled(false);
                    }else if (count >= 30 && count < 45) {
                        progressWheelElectivesFase3.setBarColor(Color.rgb(255,140,0));
                    }else if (count >= 15 && count < 30) {
                        progressWheelElectivesFase3.setBarColor(Color.rgb(255,165,0));
                    }else if (count >= 0 && count < 15) {
                        progressWheelElectivesFase3.setBarColor(Color.	rgb(255,215,0));
                    }
                }


            }
        });

        cAEF3.setOnItemLongClickListener(new courseAdapter.onItemLongClickListerner() {
            @Override
            public boolean onItemLongClick(int position) {

                clickbutton++;
                final int pos = position;
                boolean geslaagd = VakkenEF3.get(pos).isGeslaagd();

                String course = VakkenEF3.get(pos).getCourse();
                boolean  checked = VakkenEF3.get(pos).isChecked();

                Integer getScore = VakkenEF3.get(pos).getScore();
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
        String textScore = Integer.toString(VakkenEF3.get(pos).getScore());
        Integer Score = VakkenEF3.get(pos).setScore(Integer.parseInt(textScore));
        final String course = VakkenEF3.get(pos).getCourse();
        Integer getScore = VakkenEF3.get(pos).getScore();

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
                if(VakkenEF3.get(pos).isGeslaagd() == false)
                    NietGeslaagdeVakken(pos,course,VakkenEF3.get(pos).isGeslaagd());


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


                VakkenEF3.get(pos).setScore(Integer.parseInt(inputText.getText().toString()));

                scoreVak.put(VakkenEF3.get(pos).getCourse(), VakkenEF3.get(pos).getScore());

                Integer value = scoreVak.get(VakkenEF3.get(pos).getCourse());

                if ((value < 0 || value > 20))
                    Toasty.error(getContext(), "Score moet tussen 0 en 20 zijn!", Toast.LENGTH_SHORT).show();


                if ( value == 0 || value < 10 )
                {
                    VakkenEF3.get(pos).setGeslaagd(false);

                }
                else if (value>= 10 || value <= 20)
                {
                    VakkenEF3.get(pos).setGeslaagd(true);
                }

                Vakken(pos,VakkenEF3.get(pos).isGeslaagd());



            }
        });


        AlertDialog dialog = inputVak.create();

        dialog.show();
    }
}
