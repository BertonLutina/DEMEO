package app.stucre;

import android.content.Context;
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
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class OptionsIntership extends Fragment implements SearchView.OnQueryTextListener{

    private RecyclerView recyclerViewOF3;
    private courseAdapter cAOF3;

    private List<Vak> VakkenOF3;

    public OptionsIntership(){

    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference optionsFase3 = database.getReference("Bedrijfskunde/TI/Options/fase 3");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_options_intership, container, false);

        recyclerViewOF3 = (RecyclerView) view.findViewById(R.id.optionsFase3);
        recyclerViewOF3.setHasFixedSize(true);
        recyclerViewOF3.setLayoutManager(new LinearLayoutManager(getContext()));

        VakkenOF3 = new ArrayList<>();
        VakkenOF3.add(new Vak("OBI07A","Intercommunautaire stagemobiliteit","24 sp","24"));
        VakkenOF3.add(new Vak("HBI89B","Stagemobiliteit in Vlaanderen","24 sp", "2"));
        VakkenOF3.add(new Vak("HBI90B","Stagemobiliteit in Europa","24 sp","24"));
        VakkenOF3.add(new Vak("HBI91B","Stagemobiliteit buiten Europa","24 sp","24"));
        VakkenOF3.add(new Vak("OS3000","Buiten Europa: voorbereid op stage","3 sp","3"));
        VakkenOF3.add(new Vak("HBI92B","Studiemobiliteit in of buiten Europa", "3 sp", "3"));
        VakkenOF3.add(new Vak("OBI05A","Option 5 : Werkplekleren: ICT Partner Training","12","12"));
        VakkenOF3.add(new Vak("OBI06A", "Option 5 : Stageproject","12","12"));

        optionsFase3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child:children) {

                    String vakken_hm = child.getKey();

                    if(TextUtils.equals(vakken_hm ,"Option 2 : Intership mobility out of Europe") || TextUtils.equals(vakken_hm ,"Option 5 : Intership mobility in Europe : SHORT" )){
                        if(TextUtils.equals(vakken_hm ,"Option 2 : Intership mobility out of Europe"))
                        {
                            Iterable<DataSnapshot> kids = child.getChildren();

                            for (DataSnapshot kid : kids) {
                                Object course_id = kid.child("COURSE_ID").getValue(Object.class);
                                Object course = kid.child("COURSE").getValue(Object.class);
                                Object credit = kid.child("CREDITS").getValue(Object.class);
                                Object creditPunten = kid.child("CREDITS").getValue(Object.class);
                                    //VakkenOF3.add(new Vak(course_id.toString(), course.toString(), credit.toString()+" sp.      Combination: out of Europe",creditPunten.toString()));
                            }}
                        else if (TextUtils.equals(vakken_hm ,"Option 5 : Intership mobility in Europe : SHORT"))
                        {
                            Iterable<DataSnapshot> kids = child.getChildren();

                            for (DataSnapshot kid : kids) {
                                Object course_id = kid.child("COURSE_ID").getValue(Object.class);
                                Object course = kid.child("COURSE").getValue(Object.class);
                                Object credit = kid.child("CREDITS").getValue(Object.class);
                                Object creditPunten = kid.child("CREDITS").getValue(Object.class);
                                //VakkenOF3.add(new Vak(course_id.toString(), course.toString(), credit.toString()+" sp.      Combination: in or out of Europe",creditPunten.toString()));
                            }
                        }
                    }
                    else{
                        Object course_id = child.child("COURSE_ID").getValue(Object.class);
                        Object course = child.child("COURSE").getValue(Object.class);
                        Object credit = child.child("CREDITS").getValue(Object.class);
                        Object creditPunten = child.child("CREDITS").getValue(Object.class);
                        //VakkenOF3.add(new Vak(course_id.toString(),course.toString(),credit.toString()+" sp.",creditPunten.toString()));
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        cAOF3= new courseAdapter(getContext(),VakkenOF3);
        recyclerViewOF3.setAdapter(cAOF3);
        cAOF3.notifyDataSetChanged();

        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        return view;
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

}
