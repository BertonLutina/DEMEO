package app.stucre;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.support.v7.widget.SearchView;
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

public class DutiesFase3 extends Fragment implements android.support.v7.widget.SearchView.OnQueryTextListener{

    private RecyclerView recyclerViewDF3;
    private courseAdapter cA3;
    private List<Vak> Vakken3 = new ArrayList<>();
    private ProgressWheel progressWheelFase3;
    private View dutiesLayout;

    private int count = 0;

    public DutiesFase3() {
    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dutiesFase3 = database.getReference("Bedrijfskunde/TI/Duties/fase 3");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vFase3 = inflater.inflate(R.layout.fragment_duties_fase3, container, false);

        recyclerViewDF3 = (RecyclerView) vFase3.findViewById(R.id.dutiesfase3);
        recyclerViewDF3.setHasFixedSize(true);
        recyclerViewDF3.setLayoutManager(new LinearLayoutManager(getContext()));

        // Test data
        Vakken();

        //Real Data
        //VakkenDatabase();

        cA3 = new courseAdapter(getContext(),Vakken3);
        recyclerViewDF3.setAdapter(cA3);
        cA3.notifyDataSetChanged();
        setHasOptionsMenu(true);

        dutiesLayout = getActivity().findViewById(R.id.slideUpCreditsView);
        LinearLayout lay = dutiesLayout.findViewById(R.id.Progress_bar_points);
        progressWheelFase3 = (ProgressWheel) lay.findViewById(R.id.count_progressBar);

        clickOnVakken();
        return vFase3;
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

    private void Vakken(){
        Vakken3 = new ArrayList<>();
        Vakken3.add(new Vak("HBI04C","IM 5 - Big Data", "3 sp","3"));
        Vakken3.add(new Vak("HBI07C","Business Ethics", "3 sp","3"));
        Vakken3.add(new Vak("HBI12C","ICT 5: Creative Entrepreneurship", "3 sp","3"));
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

                if(!checked) {
                    Vakken3.get(position).setChecked(true);
                    if(!(count> 60)){
                        count += Integer.parseInt(point);
                        int percent = (360/60) * count;
                        progressWheelFase3.setProgress(percent);
                        progressWheelFase3.setText(Integer.toString(count)+" sp");
                        Toasty.custom(getContext(), count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.DKGRAY,Toast.LENGTH_SHORT,true,true).show();
                    }else{
                        Toasty.error(getContext(), "9 sp is de limiet => Intership & Electives Modules and Electives", Toast.LENGTH_SHORT).show();
                    }
                    if (count == 60) {
                        progressWheelFase3.setBarColor(Color.	rgb(0,128,0));
                    }else if(count >= 45 && count < 60){
                        progressWheelFase3.setBarColor(Color.rgb(255,69,0));
                    }else if (count >= 30 && count < 45) {
                        progressWheelFase3.setBarColor(Color.rgb(255,140,0));
                    }else if (count >= 15 && count < 30) {
                        progressWheelFase3.setBarColor(Color.rgb(255,165,0));
                    }else if (count >= 0 && count < 15) {
                        progressWheelFase3.setBarColor(Color.	rgb(255,215,0));
                    }
                }else{
                    Vakken3.get(position).setChecked(false);
                    if(!(count <= 0)){
                        count -= Integer.parseInt(point);
                        int percent = (360/60) * count;
                        progressWheelFase3.setProgress(percent);
                        progressWheelFase3.setText(Integer.toString(count)+" sp");
                        Toasty.custom(getContext(), count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.rgb(204,204,0),Toast.LENGTH_SHORT,true,true).show();}

                        else{
                        Toasty.error(getContext()," Mag niet Onder de 0", Toast.LENGTH_SHORT).show();
                    }
                    if (count == 60) {
                        progressWheelFase3.setBarColor(Color.	rgb(0,128,0));
                    }else if(count >= 45 && count < 60){
                        progressWheelFase3.setBarColor(Color.rgb(255,69,0));
                    }else if (count >= 30 && count < 45) {
                        progressWheelFase3.setBarColor(Color.rgb(255,140,0));
                    }else if (count >= 15 && count < 30) {
                        progressWheelFase3.setBarColor(Color.rgb(255,165,0));
                    }else if (count >= 0 && count < 15) {
                        progressWheelFase3.setBarColor(Color.	rgb(255,215,0));
                    }
                }

            }
        });
    }
}
