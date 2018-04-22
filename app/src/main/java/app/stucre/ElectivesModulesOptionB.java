package app.stucre;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.todddavies.components.progressbar.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class ElectivesModulesOptionB extends Fragment implements SearchView.OnQueryTextListener{

    private RecyclerView recyclerViewEmB;
    private courseAdapter cAEMB;
    private List<Vak> VakkenEmB;

    private ProgressWheel progressWheelEMB;
    private View dutiesLayout;
    private int count = 0;
    private Button btnSend;

    public ElectivesModulesOptionB(){

    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference EMdatabases = database.getReference("Bedrijfskunde/TI/ElectivesModules/Option B : Manage Internet and Cloud");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_electives_modules_option_b, container, false);

        recyclerViewEmB = (RecyclerView) view.findViewById(R.id.emoptionB);
        recyclerViewEmB.setHasFixedSize(true);
        recyclerViewEmB.setLayoutManager(new LinearLayoutManager(getContext()));

        // object aanmaken
        VakkenEmB = new ArrayList<>();


        // Test vakken
        //Vakken();

        // Vakken Van Database
        VakkenDatabase();

        cAEMB = new courseAdapter(getContext(),VakkenEmB);
        recyclerViewEmB.setAdapter(cAEMB);
        //cAEMB.notifyDataSetChanged();

        dutiesLayout = getActivity().findViewById(R.id.slideUpCreditsView);
        LinearLayout lay = dutiesLayout.findViewById(R.id.Progress_bar_points);
        btnSend = (Button) lay.findViewById(R.id.versturen_credits);
        progressWheelEMB = (ProgressWheel) lay.findViewById(R.id.count_progressBar);
        setHasOptionsMenu(true);

        // Click
        clickOnVakken();


        // Inflate the layout for this fragment

        return view;
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

    private void Vakken(){

        VakkenEmB.add(new Vak("OH3104","Mobile and Internet 4", "6 sp","6"));
        VakkenEmB.add(new Vak("OH3105","System Management 4", "6 sp","6"));
        VakkenEmB.add(new Vak("OH4101","Network Management 4", "6 sp","6"));
        VakkenEmB.add(new Vak("HBI87B","System Management 5 : Datacenter and cloud", "4 sp","4"));
        VakkenEmB.add(new Vak("HBI97B","Security Management 5 and Information Security", "4 sp","4"));
        VakkenEmB.add(new Vak("OH3108","Mobile & Internet 5: Smart App", "4 sp","4"));
        VakkenEmB.add(new Vak("OH3109","Integration project Internet and Cloud", "9 sp","9"));
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
                            Object credit = kid.child("CREDITS").getValue(Object.class);
                            Object creditPunten = kid.child("CREDITS").getValue(Object.class);
                            VakkenEmB.add(new Vak(course_id.toString(), course.toString(), credit.toString()+" sp.      fase 2",creditPunten.toString()));
                            cAEMB.notifyDataSetChanged();


                        }

                    }else if(TextUtils.equals(fases,"fase 3")){

                        Iterable<DataSnapshot> kids = fase.getChildren();

                        for (DataSnapshot kid : kids) {
                            Object course_id = kid.child("COURSE_ID").getValue(Object.class);
                            Object course = kid.child("COURSE").getValue(Object.class);
                            Object credit = kid.child("CREDITS").getValue(Object.class);
                            Object creditPunten = kid.child("CREDITS").getValue(Object.class);
                            VakkenEmB.add(new Vak(course_id.toString(), course.toString(), credit.toString()+" sp.      fase 3",creditPunten.toString()));
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
    }

}
