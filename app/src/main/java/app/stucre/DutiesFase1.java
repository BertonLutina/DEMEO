package app.stucre;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DutiesFase1 extends Fragment implements SearchView.OnQueryTextListener {

  private RecyclerView recyclerViewDF1;
  private courseAdapter cA1;

  private List<Vak> Vakken1;
  public SearchView searchViewFase1;

  public DutiesFase1 (){

    }

  FirebaseDatabase database = FirebaseDatabase.getInstance();
  DatabaseReference dutiesFase1 = database.getReference("Bedrijfskunde/TI/Duties/fase 1");

  @Override
  public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

    View vFase1 = inflater.inflate(R.layout.fragment_duties_fase1, container, false);

    recyclerViewDF1 = (RecyclerView) vFase1.findViewById(R.id.dutiesfase1);
    recyclerViewDF1.setHasFixedSize(true);
    recyclerViewDF1.setLayoutManager(new LinearLayoutManager(getContext()));

    Vakken1 = new ArrayList<>();
    Vakken1.add(new Vak("HBI09B","ICT Organisation 1", "4 sp","4"));
    Vakken1.add(new Vak("HBI10B","ICT Organisation 2", "4 sp","4"));
    Vakken1.add(new Vak("HBI18B","Mobile en internet 1", "3 sp","3"));
    Vakken1.add(new Vak("HBI19B","Mobile en internet 2", "3 sp","3"));
    Vakken1.add(new Vak("HBI20B","System Management 1", "3 sp","3"));
    Vakken1.add(new Vak("HBI21B","System Management 2", "3 sp","3"));
    Vakken1.add(new Vak("HBI22B","Network Management 1", "3 sp","3"));
    Vakken1.add(new Vak("HBI23B","Network Management 2", "3 sp","3"));
    Vakken1.add(new Vak("HBI59B","Communicatietraining 1", "4 sp","4"));
    Vakken1.add(new Vak("HBI60B","Communicatietraining 2", "4 sp","4"));
    Vakken1.add(new Vak("HBI61B","Information management 1", "3 sp","3"));
    Vakken1.add(new Vak("HBI62B","Information management 2", "3 sp","3"));
    Vakken1.add(new Vak("HBI63B","Database development 1", "3 sp","3"));
    Vakken1.add(new Vak("HBI64B","Database development 2", "3 sp","3"));
    Vakken1.add(new Vak("HBI65B","Application development 1", "4 sp","4"));
    Vakken1.add(new Vak("HBI66B","Application development 2", "4 sp","4"));
    Vakken1.add(new Vak("HBI77B","Software engineering 1", "3 sp","3"));
    Vakken1.add(new Vak("HBI78B","Software engineering 2", "3 sp","3"));





   dutiesFase1.addValueEventListener(new ValueEventListener() {
     @Override
     public void onDataChange(DataSnapshot dataSnapshot) {
       Iterable<DataSnapshot> children = dataSnapshot.getChildren();


       for (DataSnapshot child: children) {
         Object course_id = child.child("COURSE_ID").getValue(Object.class);
         Object course = child.child("COURSE").getValue(Object.class);
         Object credit = child.child("CREDITS").getValue(Object.class);
         Object creditPunten = child.child("CREDITS").getValue(Object.class);
         //Vakken.add(new Vak(course_id.toString(),course.toString(),credit.toString()+" sp.",creditPunten.toString()));
         //Vakken1.add(new Vak(course_id.toString(),course.toString(),credit.toString()+" sp.",creditPunten.toString()));

       }

     }


     @Override
     public void onCancelled(DatabaseError databaseError) {

     }
   });

      cA1 = new courseAdapter(getContext(),Vakken1);
      recyclerViewDF1.setAdapter(cA1);
      cA1.notifyDataSetChanged();
      setHasOptionsMenu(true);


    /// SearchView voor het zoeken en filteren van de vakken.//




    return vFase1;




  }



  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);


  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.duties_menu_bar,menu);
    MenuItem item = menu.findItem(R.id.menu_search);

    SearchView dutiesSearch = (SearchView) item.getActionView();
    dutiesSearch.setOnQueryTextListener(this);

    item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
      @Override
      public boolean onMenuItemActionExpand(MenuItem item) {
        Toast.makeText(getContext(),"search course fase 1",Toast.LENGTH_SHORT).show();


        return true;
      }

      @Override
      public boolean onMenuItemActionCollapse(MenuItem item) {
          Toast.makeText(getContext(),"close search",Toast.LENGTH_SHORT).show();
          cA1.setFilter(Vakken1);
          cA1.notifyDataSetChanged();
        return true;
      }
    });


  }

  @Override
  public void onStart() {
    super.onStart();
   //Ldutiesfase1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

  }

  @Override
  public boolean onQueryTextSubmit(String query) {
    return false;
  }

  @Override
  public boolean onQueryTextChange(String newText) {
    final List<Vak> filteredVakken = filter(Vakken1,newText);
    cA1.setFilter(filteredVakken);
    cA1.notifyDataSetChanged();
    return true;
  }

  private List<Vak> filter(List<Vak> vakken1, String query) {
    query = query.toLowerCase();
    final List<Vak> filteredVakken = new ArrayList<>();

    for (Vak vakken : vakken1){
      final String text = vakken.getCourse().toLowerCase();
      if(text.contains(query)){
        filteredVakken.add(vakken);
      }
    }

    return filteredVakken;

  }
}
