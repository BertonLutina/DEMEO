package app.stucre;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.support.v7.widget.SearchView;
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
public class DutiesFase2 extends Fragment implements android.support.v7.widget.SearchView.OnQueryTextListener {

  private RecyclerView recyclerViewDF2;
  private courseAdapter cA2;

  private List<Vak> Vakken2 = new ArrayList<>();

  public DutiesFase2(){

  }

  FirebaseDatabase database = FirebaseDatabase.getInstance();
  DatabaseReference dutiesFase2 = database.getReference("Bedrijfskunde/TI/Duties/fase 2");


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View vFase2 = inflater.inflate(R.layout.fragment_duties_fase2, container, false);

    recyclerViewDF2 = (RecyclerView) vFase2.findViewById(R.id.dutiesfase2);
    recyclerViewDF2.setHasFixedSize(true);
    recyclerViewDF2.setLayoutManager(new LinearLayoutManager(getContext()));

    Vakken2 = new ArrayList<>();
    Vakken2.add(new Vak("HBI02C","ICT Organisation 4", "4 sp","4"));
    Vakken2.add(new Vak("HBI25B","ICT Organisation 3", "4 sp","4"));
    Vakken2.add(new Vak("HBI34B","Mobile en internet 3", "3 sp ","3"));
    Vakken2.add(new Vak("HBI36B","System Management 3", "3 sp","3"));
    Vakken2.add(new Vak("HBI38B","Network Management 3", "3 sp","3"));
    Vakken2.add(new Vak("HBI68B","Communicatietraining 4", "4 sp","3"));
    Vakken2.add(new Vak("HBI69B","Information management 3", "3sp","3"));
    Vakken2.add(new Vak("HBI70B","Information management 4", "3sp","3"));
    Vakken2.add(new Vak("HBI71B","Database development 3", "3 sp","3"));
    Vakken2.add(new Vak("HBI73B","Application development 3", "3 sp","3"));
    Vakken2.add(new Vak("OH3100","Software Engineering 3", "3 sp","3"));
    Vakken2.add(new Vak("OH4100","Communicatietraining 3", "6 sp","6"));


    dutiesFase2.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {

        Iterable<DataSnapshot> children = dataSnapshot.getChildren();

        for (DataSnapshot child: children) {


          Object course_id = child.child("COURSE_ID").getValue(Object.class);
          Object course = child.child("COURSE").getValue(Object.class);
          Object credit = child.child("CREDITS").getValue(Object.class);
          Object creditPunten = child.child("CREDITS").getValue(Object.class);
          //Vakken2.add(new Vak(course_id.toString(),course.toString(),credit.toString()+" sp.",creditPunten.toString()));


        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });


    cA2 = new courseAdapter(getContext(),Vakken2);
    recyclerViewDF2.setAdapter(cA2);
    cA2.notifyDataSetChanged();

    setHasOptionsMenu(true);

    return vFase2;


  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

  }


  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.duties_menu_bar,menu);

    MenuItem item = menu.findItem(R.id.menu_search);
    SearchView search_fase2 = (SearchView) item.getActionView();

    search_fase2.setOnQueryTextListener(this);

    item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
      @Override
      public boolean onMenuItemActionExpand(MenuItem item) {
        Toast.makeText(getContext(),"search and filter course in fase 2",Toast.LENGTH_SHORT).show();

        return true;
      }

      @Override
      public boolean onMenuItemActionCollapse(MenuItem item) {
        Toast.makeText(getContext(),"close search",Toast.LENGTH_SHORT).show();
        cA2.setFilter(Vakken2);
        cA2.notifyDataSetChanged();
        return true;
      }
    });
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public void onStart() {

    super.onStart();
    //Ldutiesfase2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

  }

  @Override
  public boolean onQueryTextSubmit(String query) {
    return false;
  }

  @Override
  public boolean onQueryTextChange(String newText) {
    final List<Vak> filteredVakken = filter(Vakken2,newText);
    cA2.setFilter(filteredVakken);
    cA2.notifyDataSetChanged();
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
