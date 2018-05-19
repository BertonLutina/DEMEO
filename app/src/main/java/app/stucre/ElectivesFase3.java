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
import java.util.List;

import es.dmoral.toasty.Toasty;


public class ElectivesFase3 extends Fragment implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerViewEF3;
    private courseAdapter cAEF3;

    private ArrayList<Vak> VakkenEF3;
    Vak test;
    private ProgressWheel progressWheelElectivesFase3;
    private View dutiesLayout;
    private int count = 0;
    private Button btnSend;
    private Switch selectall;


    public ElectivesFase3(){

    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference electivFase3 = database.getReference("Bedrijfskunde/TI/Electives/fase 3");

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

        //Object aanmaken
        Intent intent = getActivity().getIntent();

        VakkenEF3 = (ArrayList<Vak>) intent.getSerializableExtra("EleFase3");


        // Test vakken
        //Vakken();

        // Vakken Van Database
        //VakkenDatabase();

        cAEF3= new courseAdapter(getContext(),VakkenEF3);
        recyclerViewEF3.setAdapter(cAEF3);
        //cAEF3.notifyDataSetChanged();

        dutiesLayout = getActivity().findViewById(R.id.slideUpCreditsView);
        LinearLayout lay = dutiesLayout.findViewById(R.id.Progress_bar_points);
        btnSend = (Button) lay.findViewById(R.id.versturen_credits);
        progressWheelElectivesFase3 = (ProgressWheel) lay.findViewById(R.id.count_progressBar);

        clickOnVakken();

        selectall = (Switch) getActivity().findViewById(R.id.selectAllSwitch);

        selectall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    for (Vak vak : VakkenEF3){
                        vak.setChecked(true);
                    }
                    Toasty.custom(getContext(), "+ "+ count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.DKGRAY,Toast.LENGTH_SHORT,true,true).show();
                }else{
                    for (Vak vak : VakkenEF3){
                        vak.setChecked(false);
                    }
                    Toasty.custom(getContext(), "- "+count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.rgb(204,204,0),Toast.LENGTH_SHORT,true,true).show();
                }
                cAEF3.notifyDataSetChanged();

                if(b){
                    for (Vak vak : VakkenEF3){
                        boolean checked = vak.isChecked();
                        String point = vak.getCreditPunten();
                        count = Integer.parseInt(point);
                        count++;
                        int percent = (360/60) * (count+1);
                        progressWheelElectivesFase3.setProgress(percent);
                        progressWheelElectivesFase3.setText(Integer.toString(count)+" sp");
                    }
                    Toasty.custom(getContext(), "+ "+ count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.DKGRAY,Toast.LENGTH_SHORT,true,true).show();
                }else{
                    for (Vak vak : VakkenEF3){
                        boolean checked = vak.isChecked();
                        String point = vak.getCreditPunten();
                        count = Integer.parseInt(point);
                        count--;
                        int percent = (360/60) * (count+1);
                        progressWheelElectivesFase3.setProgress(percent);
                        progressWheelElectivesFase3.setText(Integer.toString(count)+" sp");
                    }
                    Toasty.custom(getContext(), "- "+count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.rgb(204,204,0),Toast.LENGTH_SHORT,true,true).show();
                }

            }
        });
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
                    Object credit = child.child("CREDITS").getValue(Object.class);
                    Object creditPunten = child.child("CREDITS").getValue(Object.class);
                    //VakkenEF3.add(new Vak(course_id.toString(),course.toString(),credit.toString()+" sp.",creditPunten.toString()));
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
                String point = VakkenEF3.get(position).getCreditPunten();
                boolean checked = VakkenEF3.get(position).isChecked();
                if(!checked){
                    VakkenEF3.get(position).setChecked(true);
                    if(!(count> 60)){
                        count += Integer.parseInt(point);
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
                        count -= Integer.parseInt(point);
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

                String course = VakkenEF3.get(position).getCourse();
                Integer Score = VakkenEF3.get(position).setScore(0);
                Integer getScore = VakkenEF3.get(position).getScore();

                AlertDialog.Builder dialogvak = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getActivity().getLayoutInflater();

                View dialogView = inflater.inflate(R.layout.dialogscore,null);
                TextView vak = (TextView) dialogView.findViewById(R.id.vakDialoog);
                TextView score = (TextView) dialogView.findViewById(R.id.score);
                TextView geslaagd = (TextView) dialogView.findViewById(R.id.txtgeslaagd);

                vak.setText(course);
                score.setText(getScore.toString());
                geslaagd.setText("In progress...");
                geslaagd.setBackgroundColor(Color.rgb(0,128,0));
                geslaagd.setTextColor(Color.rgb(246,246,246));


                dialogvak.setView(dialogView).setPositiveButton("Back",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog alertVak =  dialogvak.create();

                alertVak.show();

                return true;
            }
        });
    }
}
