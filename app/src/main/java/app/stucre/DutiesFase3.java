package app.stucre;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.support.v7.widget.SearchView;
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

public class DutiesFase3 extends Fragment implements android.support.v7.widget.SearchView.OnQueryTextListener{

    private RecyclerView recyclerViewDF3;
    private courseAdapter cA3;
    private ArrayList<Vak> Vakken3;
    private ProgressWheel progressWheelFase3;
    private View dutiesLayout;

    private int count = 0;
    private Button btnSend;
    private Switch selectall;
    private Switch selectall2;
    private Switch selectall3;
    private int clickbutton = 0;
    private int clicks = 0;
    private int input = 0;
    private boolean checked;
    private HashMap <String, Integer>scoreVak = new HashMap<String, Integer>();


    public DutiesFase3() {
    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dutiesFase3 = database.getReference("Bedrijfskunde/TI/Duties/fase 3");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vFase3 = inflater.inflate(R.layout.fragment_duties_fase3, container, false);


        return vFase3;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewDF3 = (RecyclerView) view.findViewById(R.id.dutiesfase3);
        recyclerViewDF3.setHasFixedSize(true);
        recyclerViewDF3.setLayoutManager(new LinearLayoutManager(getContext()));


        //Real Data
        Vakken3 = new ArrayList<>();
        VakkenDatabase();

        cA3 = new courseAdapter(getContext(),Vakken3);
        recyclerViewDF3.setAdapter(cA3);
        //cA3.notifyDataSetChanged();
        setHasOptionsMenu(true);

        dutiesLayout = getActivity().findViewById(R.id.slideUpCreditsView);
        LinearLayout lay = dutiesLayout.findViewById(R.id.Progress_bar_points);
        btnSend = (Button) lay.findViewById(R.id.versturen_credits);
        progressWheelFase3 = (ProgressWheel) lay.findViewById(R.id.count_progressBar);

        clickOnVakken();


        selectall3 = (Switch) getActivity().findViewById(R.id.selectAllSwitchFase3);


        selectall3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    for (Vak vak : Vakken3){
                        vak.setChecked(true);
                        boolean checked = vak.isChecked();
                        String point = vak.getCreditPunten();
                        //count = Integer.parseInt(point);
                        //count++;
                        count =+ 9;
                        int percent = 360;
                        progressWheelFase3.setProgress(percent);
                        progressWheelFase3.setText(Integer.toString(count)+" sp");
                        cA3.notifyDataSetChanged();

                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setMessage("Would like to continue?")
                            .setTitle("Go to Electives Modules")
                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Intent change = new Intent(getActivity(),profile.class);
                                    startActivity(change);

                                }
                            }).setNegativeButton("Stop", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    Toasty.custom(getContext(), "+ "+ count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.DKGRAY,Toast.LENGTH_SHORT,true,true).show();
                }else{
                    for (Vak vak : Vakken3) {
                        vak.setChecked(false);
                        boolean checked = vak.isChecked();
                        String point = vak.getCreditPunten();
                        //count = Integer.parseInt(point);
                        //count--;
                        count = 0;
                        int percent = 0;
                        progressWheelFase3.setProgress(percent);
                        progressWheelFase3.setText(Integer.toString(count) + " sp");
                        cA3.notifyDataSetChanged();

                    }
                }

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {

        super.onStart();
        //Ldutiesfase3.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.duties_menu_bar,menu);

        MenuItem item = menu.findItem(R.id.menu_search);
        SearchView search_fase3 = (SearchView) item.getActionView();

        search_fase3.setOnQueryTextListener(this);

        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(getContext(),"search and filter course in fase 3",Toast.LENGTH_SHORT).show();

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(getContext(),"close search",Toast.LENGTH_SHORT).show();
                cA3.setFilter(Vakken3);
                cA3.notifyDataSetChanged();
                return true;
            }
        });


        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Vak> filteredVakken = filter(Vakken3,newText);
        cA3.setFilter(filteredVakken);
        cA3.notifyDataSetChanged();
        return false;
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
        dutiesFase3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();


                for (DataSnapshot child:children) {
                    Object course_id = child.child("COURSE_ID").getValue(Object.class);
                    Object course = child.child("COURSE").getValue(Object.class);
                    Object credit = child.child("CREDITS").getValue(Object.class);
                    Object creditPunten = child.child("CREDITS").getValue(Object.class);
                    //Vakken3.add(new Vak(course_id.toString(),course.toString(),credit.toString()+" sp.",creditPunten.toString()));
                    cA3.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void clickOnVakken(){
        cA3.setOnItemClickListener(new courseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {


                //String plaats = Vakken1.get(position).getCourse();
                String point = Vakken3.get(position).getCreditPunten();
                boolean checked = Vakken3.get(position).isChecked();

                //btnSend.setEnabled(false);

                if(!checked) {
                    Vakken3.get(position).setChecked(true);
                    if(!(count> 60)){
                        count += Integer.parseInt(point);
                        int percent = (360/9) * count;
                        progressWheelFase3.setProgress(percent);
                        progressWheelFase3.setText(Integer.toString(count)+" sp");
                        Toasty.custom(getContext(), "+ "+ point+" sp. ->  "+"Total: "+count, getResources().getDrawable(R.drawable.booksstacktwee), Color.DKGRAY,Toast.LENGTH_SHORT,true,true).show();
                    }else{
                        Toasty.error(getContext(), "9 sp is de limiet => Intership & Electives Modules and Electives", Toast.LENGTH_SHORT).show();
                    }
                    if (count == 9) {
                        progressWheelFase3.setBarColor(Color.	rgb(0,128,0));
                        //btnSend.setEnabled(true);
                        //btnSend.setBackgroundColor(Color.rgb(0,128,0));

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                        builder.setMessage("Would like to continue?")
                                .setTitle("Go to Electives Modules")
                                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        Intent change = new Intent(getActivity(),profile.class);
                                        startActivity(change);

                                    }
                                }).setNegativeButton("Stop", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }else if(count == 6){
                        progressWheelFase3.setBarColor(Color.rgb(255,69,0));
                    }else if (count >= 0 && count <= 3) {
                        progressWheelFase3.setBarColor(Color.	rgb(255,215,0));
                    }
                }else{
                    Vakken3.get(position).setChecked(false);
                    if(!(count <= 0)){
                        count -= Integer.parseInt(point);
                        int percent = (360/9) * count;
                        progressWheelFase3.setProgress(percent);
                        progressWheelFase3.setText(Integer.toString(count)+" sp");
                        Toasty.custom(getContext(), "- "+ point+" sp. ->  "+"Total: "+count, getResources().getDrawable(R.drawable.booksstacktwee), Color.DKGRAY,Toast.LENGTH_SHORT,true,true).show();}

                        else{
                        Toasty.error(getContext()," Mag niet Onder de 0", Toast.LENGTH_SHORT).show();
                    }
                    if (count == 9) {
                        progressWheelFase3.setBarColor(Color.rgb(0,128,0));
                    }else if(count == 6){
                        progressWheelFase3.setBarColor(Color.rgb(255,69,0));
                        //btnSend.setEnabled(false);
                    }else if (count >= 0 && count <= 3) {
                        progressWheelFase3.setBarColor(Color.rgb(255,215,0));
                    }
                }

            }
        });

        cA3.setOnItemLongClickListener(new courseAdapter.onItemLongClickListerner() {


            @Override
            public boolean onItemLongClick(final int position) {
                clickbutton++;
                final int pos = position;
                boolean geslaagd = Vakken3.get(pos).isGeslaagd();

                String course = Vakken3.get(pos).getCourse();
                boolean  checked = Vakken3.get(pos).isChecked();

                Integer getScore = Vakken3.get(pos).getScore();
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
        cA3.notifyDataSetChanged();
    }

    public void Vakken(final int pos, boolean geslaagdvak){


            geslaagdvak = true;
            String textScore = Integer.toString(Vakken3.get(pos).getScore());
            Integer Score = Vakken3.get(pos).setScore(Integer.parseInt(textScore));
            final String course = Vakken3.get(pos).getCourse();
            Integer getScore = Vakken3.get(pos).getScore();

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
                    if(Vakken3.get(pos).isGeslaagd() == false)
                    NietGeslaagdeVakken(pos,course,Vakken3.get(pos).isGeslaagd());


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



                    Vakken3.get(pos).setScore(Integer.parseInt(inputText.getText().toString()));

                scoreVak.put(Vakken3.get(pos).getCourse(), Vakken3.get(pos).getScore());

                Integer value = scoreVak.get(Vakken3.get(pos).getCourse());

                    if ((value < 0 || value > 20))
                        Toasty.error(getContext(), "Score moet tussen 0 en 20 zijn!", Toast.LENGTH_SHORT).show();


                if ( value == 0 || value < 10 )
                {
                    Vakken3.get(pos).setGeslaagd(false);

                }
                else if (value>= 10 || value <= 20)
                {
                    Vakken3.get(pos).setGeslaagd(true);
                }

                    Vakken(pos,Vakken3.get(pos).isGeslaagd());



            }
        });


        AlertDialog dialog = inputVak.create();

        dialog.show();
    }
}
