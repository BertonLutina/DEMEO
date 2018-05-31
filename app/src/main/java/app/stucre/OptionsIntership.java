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


public class OptionsIntership extends Fragment implements SearchView.OnQueryTextListener{

    private RecyclerView recyclerViewOF3;
    private courseAdapter cAOF3;

    private ArrayList<Vak> VakkenOF3 = new ArrayList<>();

    private ProgressWheel progressWheelOptionFase3;
    private View LayoutCredits;
    private int count = 0;
    private Button btnSend;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference optionsFase3 = database.getReference("Bedrijfskunde/TI/Options");
    private Switch selectall;
    private int clickbutton = 0;
    private int clicks = 0;
    private HashMap<String,Integer> scoreVak = new HashMap<String,Integer>();
    private boolean checked;

    public OptionsIntership(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_options_intership, container, false);

        VakkenDatabase();


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewOF3 = (RecyclerView) view.findViewById(R.id.optionsFase3);
        recyclerViewOF3.setHasFixedSize(true);
        recyclerViewOF3.setLayoutManager(new LinearLayoutManager(getContext()));



        //Vakken van de DATABASE


        cAOF3= new courseAdapter(getContext(),VakkenOF3);
        recyclerViewOF3.setAdapter(cAOF3);
        //cAOF3.notifyDataSetChanged();

        LayoutCredits = getActivity().findViewById(R.id.slideUpCreditsView);
        LinearLayout lay = LayoutCredits.findViewById(R.id.Progress_bar_points);
        btnSend = (Button) lay.findViewById(R.id.versturen_credits);
        progressWheelOptionFase3 = (ProgressWheel) lay.findViewById(R.id.count_progressBar);
        setHasOptionsMenu(true);
        clickOnVakken();
        selectall = (Switch) getActivity().findViewById(R.id.selectAllSwitch);

        selectall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    for (Vak vak : VakkenOF3){
                        vak.setChecked(true);
                        boolean checked = vak.isChecked();
                        String point = vak.getCreditPunten();
                        count = Integer.parseInt(point);
                        count++;
                        int percent = (360/60) * (count+1);
                        progressWheelOptionFase3.setProgress(percent);
                        progressWheelOptionFase3.setText(Integer.toString(count)+" sp");
                    }
                    Toasty.custom(getContext(), "+ "+ count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.DKGRAY,Toast.LENGTH_SHORT,true,true).show();
                }else{
                    for (Vak vak : VakkenOF3) {
                        vak.setChecked(false);
                        boolean checked = vak.isChecked();
                        String point = vak.getCreditPunten();
                        count = Integer.parseInt(point);
                        count--;
                        int percent = (360 / 60) * (count + 1);
                        progressWheelOptionFase3.setProgress(percent);
                        progressWheelOptionFase3.setText(Integer.toString(count) + " sp");
                    }
                    cAOF3.notifyDataSetChanged();
                }

            }
        });

    }

    @Override
    public void onStart() {

        super.onStart();
        //listViewO.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.options_menu_bar,menu);
        MenuItem item = menu.findItem(R.id.menu_search_Options);

        SearchView dutiesSearch = (SearchView) item.getActionView();
        dutiesSearch.setOnQueryTextListener(this);

        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(getContext(),"search and filter course in fase 3",Toast.LENGTH_SHORT).show();


                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(getContext(),"close search",Toast.LENGTH_SHORT).show();
                cAOF3.setFilter(VakkenOF3);
                cAOF3.notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Vak> filteredVakken = filter(VakkenOF3,newText);
        cAOF3.setFilter(filteredVakken);
        cAOF3.notifyDataSetChanged();
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

    private void VakkenDatabase(){  optionsFase3.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Iterable<DataSnapshot> children = dataSnapshot.getChildren();

            for (DataSnapshot child:children) {

                String vakken_hm = child.getKey();

                if(TextUtils.equals(vakken_hm ,"Optie 2: Stagemobiliteit buiten Europa") || TextUtils.equals(vakken_hm ,"Optie 5: Studiemobiliteit in Europa KORT" )){
                    if(TextUtils.equals(vakken_hm ,"Optie 2: Stagemobiliteit buiten Europa"))
                    {
                        Iterable<DataSnapshot> kids = child.getChildren();

                        for (DataSnapshot kid : kids) {
                            Object course_id = kid.child("COURSE_ID").getValue(Object.class);
                            Object course = kid.child("COURSE").getValue(Object.class);
                            Object credit = kid.child("CREDIT").getValue(Object.class);
                            Object creditPunten = kid.child("CREDITPOINT").getValue(Object.class);
                            Object fase = kid.child("FASE").getValue(Object.class);
                            Object score = kid.child("FASE").getValue(Object.class);
                            Object succeeded = kid.child("SUCCEEDED").getValue(Object.class);
                            VakkenOF3.add(new Vak(course_id.toString(), course.toString(), credit.toString(),creditPunten.toString(),Integer.parseInt(fase.toString()),Integer.parseInt(score.toString()),Boolean.valueOf(succeeded.toString()),false));
                            cAOF3.notifyDataSetChanged();
                        }}
                    else if (TextUtils.equals(vakken_hm ,"Optie 5: Studiemobiliteit in Europa KORT"))
                    {
                        Iterable<DataSnapshot> kids = child.getChildren();

                        for (DataSnapshot kid : kids) {
                            Object course_id = kid.child("COURSE_ID").getValue(Object.class);
                            Object course = kid.child("COURSE").getValue(Object.class);
                            Object credit = kid.child("CREDIT").getValue(Object.class);
                            Object creditPunten = kid.child("CREDITPOINT").getValue(Object.class);
                            Object fase = kid.child("FASE").getValue(Object.class);
                            Object score = kid.child("FASE").getValue(Object.class);
                            Object succeeded = kid.child("SUCCEEDED").getValue(Object.class);
                            VakkenOF3.add(new Vak(course_id.toString(), course.toString(), credit.toString(),creditPunten.toString(),Integer.parseInt(fase.toString()),Integer.parseInt(score.toString()),Boolean.valueOf(succeeded.toString()),false));
                            cAOF3.notifyDataSetChanged();
                        }
                    }
                }
                else{
                    for(DataSnapshot kid: child.getChildren()){
                    Object course_id = kid.child("COURSE_ID").getValue(Object.class);
                    Object course = kid.child("COURSE").getValue(Object.class);
                    Object credit = kid.child("CREDIT").getValue(Object.class);
                    Object creditPunten = kid.child("CREDITPOINT").getValue(Object.class);
                    Object fase = kid.child("FASE").getValue(Object.class);
                    Object score = kid.child("SCORE").getValue(Object.class);
                    Object succeeded = kid.child("SUCCEEDED").getValue(Object.class);
                    VakkenOF3.add(new Vak(course_id.toString(), course.toString(), credit.toString(),creditPunten.toString(),Integer.parseInt(fase.toString()),Integer.parseInt(score.toString()),Boolean.valueOf(succeeded.toString()),false));

                    cAOF3.notifyDataSetChanged();}
                }

            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });}
    private void clickOnVakken(){
        cAOF3.setOnItemClickListener(new courseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {


                //String plaats = Vakken1.get(position).getCourse();
                String point = VakkenOF3.get(position).getCreditPunten();
                boolean checked = VakkenOF3.get(position).isChecked();

                if(!checked){
                    VakkenOF3.get(position).setChecked(true);
                    if(!(count> 60)){
                        count += Integer.parseInt(point);
                        int percent = (360/60) * count;
                        progressWheelOptionFase3.setProgress(percent);
                        progressWheelOptionFase3.setText(Integer.toString(count)+" sp");
                        Toasty.custom(getContext(), "+ "+count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.DKGRAY,Toast.LENGTH_SHORT,true,true).show();
                    }else{
                        Toasty.error(getContext()," 60 sp is de limiet => Fase 3", Toast.LENGTH_SHORT).show();
                    }
                    if (count == 60) {
                        progressWheelOptionFase3.setBarColor(Color.	rgb(0,128,0));
                        //btnSend.setEnabled(true);
                        //btnSend.setBackgroundColor(Color.rgb(0,128,0));
                    }else if(count >= 45 && count < 60){
                        progressWheelOptionFase3.setBarColor(Color.rgb(255,69,0));
                    }else if (count >= 30 && count < 45) {
                        progressWheelOptionFase3.setBarColor(Color.rgb(255,140,0));
                    }else if (count >= 15 && count < 30) {
                        progressWheelOptionFase3.setBarColor(Color.rgb(255,165,0));
                    }else if (count >= 0 && count < 15) {
                        progressWheelOptionFase3.setBarColor(Color.	rgb(255,215,0));
                    }
                }else{
                    VakkenOF3.get(position).setChecked(false);
                    if(!(count < 0)) {
                        count -= Integer.parseInt(point);
                        int percent = (360/60) * count;
                        progressWheelOptionFase3.setProgress(percent);
                        progressWheelOptionFase3.setText(Integer.toString(count)+" sp");
                        Toasty.custom(getContext(), "- "+count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.rgb(204,204,0),Toast.LENGTH_SHORT,true,true).show();
                    }else{
                        Toasty.error(getContext()," MAG niet Onder de 0", Toast.LENGTH_SHORT).show();
                    }

                    if (count == 60) {
                        progressWheelOptionFase3.setBarColor(Color.	rgb(0,128,0));
                    }else if(count >= 45 && count < 60){
                        progressWheelOptionFase3.setBarColor(Color.rgb(255,69,0));
                        //btnSend.setEnabled(false);
                    }else if (count >= 30 && count < 45) {
                        progressWheelOptionFase3.setBarColor(Color.rgb(255,140,0));
                    }else if (count >= 15 && count < 30) {
                        progressWheelOptionFase3.setBarColor(Color.rgb(255,165,0));
                    }else if (count >= 0 && count < 15) {
                        progressWheelOptionFase3.setBarColor(Color.	rgb(255,215,0));
                    }
                }




            }
        });

        cAOF3.setOnItemLongClickListener(new courseAdapter.onItemLongClickListerner() {
            @Override
            public boolean onItemLongClick(int position) {

                clickbutton++;
                final int pos = position;
                boolean geslaagd = VakkenOF3.get(pos).isGeslaagd();

                String course = VakkenOF3.get(pos).getCourse();
                boolean  checked = VakkenOF3.get(pos).isChecked();

                Integer getScore = VakkenOF3.get(pos).getScore();
                clicks = 0;

                scoreVak.put(course,getScore);
                Integer value = scoreVak.get(course);





                if(value == 0 || value < 10)
                {

                    NietGeslaagdeVakken(pos,course,geslaagd);
                }
                else if (value>= 10 || value <= 20)
                {
                    Vakken(pos, geslaagd);
                }


                /*

                if ( value > 0 || value < 10 )
                {
                    VakkenOF3.get(pos).setGeslaagd(false);

                }
                else if (value>= 10 || value <= 20)
                {
                    VakkenOF3.get(pos).setGeslaagd(true);
                }

*/

                return true;
            }
        });
    }

    public void Vakken(final int pos, boolean geslaagdvak){


        geslaagdvak = true;
        String textScore = Integer.toString(VakkenOF3.get(pos).getScore());
        Integer Score = VakkenOF3.get(pos).setScore(Integer.parseInt(textScore));
        final String course = VakkenOF3.get(pos).getCourse();
        Integer getScore = VakkenOF3.get(pos).getScore();

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
                if(VakkenOF3.get(pos).isGeslaagd() == false)
                    NietGeslaagdeVakken(pos,course,VakkenOF3.get(pos).isGeslaagd());


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


                VakkenOF3.get(pos).setScore(Integer.parseInt(inputText.getText().toString()));

                scoreVak.put(VakkenOF3.get(pos).getCourse(), VakkenOF3.get(pos).getScore());

                Integer value = scoreVak.get(VakkenOF3.get(pos).getCourse());

                if ((value < 0 || value > 20))
                    Toasty.error(getContext(), "Score moet tussen 0 en 20 zijn!", Toast.LENGTH_SHORT).show();


                if(value == 0 || value < 10) {

                    VakkenOF3.get(pos).setGeslaagd(false);


                }else if (value>= 10 || value <= 20)
                {
                    VakkenOF3.get(pos).setGeslaagd(true);


                }

                Vakken(pos,VakkenOF3.get(pos).isGeslaagd());



            }
        });


        AlertDialog dialog = inputVak.create();

        dialog.show();
    }


}
