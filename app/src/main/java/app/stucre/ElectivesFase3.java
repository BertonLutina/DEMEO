package app.stucre;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

    private List<Vak> VakkenEF3;
    Vak test;
    private ProgressWheel progressWheelElectivesFase3;
    private View dutiesLayout;
    private int count = 0;


    public ElectivesFase3(){

    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference electivFase3 = database.getReference("Bedrijfskunde/TI/Electives/fase 3");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_electives_fase3, container, false);

        recyclerViewEF3 = (RecyclerView) view.findViewById(R.id.electivesFase3);
        recyclerViewEF3.setHasFixedSize(true);
        recyclerViewEF3.setLayoutManager(new LinearLayoutManager(getContext()));

        // Test vakken
        Vakken();

        // Vakken Van Database
        //VakkenDatabase();

        cAEF3= new courseAdapter(getContext(),VakkenEF3);
        recyclerViewEF3.setAdapter(cAEF3);
        cAEF3.notifyDataSetChanged();

        dutiesLayout = getActivity().findViewById(R.id.slideUpCreditsView);
        LinearLayout lay = dutiesLayout.findViewById(R.id.Progress_bar_points);
        progressWheelElectivesFase3 = (ProgressWheel) lay.findViewById(R.id.count_progressBar);

        clickOnVakken();

        setHasOptionsMenu(true);
        // Inflate the layout for this fragment

        return view;

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


    private void Vakken(){
        VakkenEF3 = new ArrayList<>();
        VakkenEF3.add(new Vak("HBI33A","Sales and Customer interaction", "3 sp","3"));
        VakkenEF3.add(new Vak("HBI47B","Content management", "3 sp","3"));
        VakkenEF3.add(new Vak("HBI53B","Advanced Switching", "6 sp","6"));
        VakkenEF3.add(new Vak("HBI54B","Advanced Routing", "6 sp","6"));
        VakkenEF3.add(new Vak("OBI01A","ICT Partner Training", "3 sp","3"));
        VakkenEF3.add(new Vak("OBI02A","ICT Partner Training B", "4 sp","4"));
        VakkenEF3.add(new Vak("OBI03A","ICT Partner Training C", "5 sp","5"));
        VakkenEF3.add(new Vak("OBI04A","ICT Partner Training D", "6 sp","6"));
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
                    VakkenEF3.add(new Vak(course_id.toString(),course.toString(),credit.toString()+" sp.",creditPunten.toString()));


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
                        Toasty.custom(getContext(), count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.DKGRAY,Toast.LENGTH_SHORT,true,true).show();
                        int percent = (360/60) * count;
                        progressWheelElectivesFase3.setProgress(percent);
                        progressWheelElectivesFase3.setText(Integer.toString(count)+" sp");
                        }else{
                        Toasty.error(getContext()," 60 sp is de limiet => Fase 3", Toast.LENGTH_SHORT).show();
                        count = count + 0;
                    }
                    if (count == 60) {
                        progressWheelElectivesFase3.setBarColor(Color.	rgb(0,128,0));
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
                        Toasty.custom(getContext(), count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.rgb(204,204,0),Toast.LENGTH_SHORT,true,true).show();
                        int percent = (360/60) * count;
                        progressWheelElectivesFase3.setProgress(percent);
                        progressWheelElectivesFase3.setText(Integer.toString(count)+" sp");
                    }else{
                        Toasty.error(getContext()," MAG niet onder nul", Toast.LENGTH_SHORT).show();
                        count = count + 0;
                    }
                    if (count == 60) {
                        progressWheelElectivesFase3.setBarColor(Color.	rgb(0,128,0));
                    }else if(count >= 45 && count < 60){
                        progressWheelElectivesFase3.setBarColor(Color.rgb(255,69,0));
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
    }
}
