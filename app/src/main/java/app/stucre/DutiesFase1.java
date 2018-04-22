package app.stucre;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class DutiesFase1 extends Fragment implements SearchView.OnQueryTextListener {

  private RecyclerView recyclerViewDF1;
  private courseAdapter cA1;
  private CardView fase1;
  private List<Vak> Vakken1;
  public SearchView searchViewFase1;
  private ProgressWheel progressWheelFase1;
  private View LayoutCredits;

  int count = 0;
  private Button btnSend;


  public DutiesFase1 (){

    }

  FirebaseDatabase database = FirebaseDatabase.getInstance();
  DatabaseReference dutiesFase1 = database.getReference("Bedrijfskunde/TI/Duties/fase 1");

  @Override
  public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

    View vFase1 = inflater.inflate(R.layout.fragment_duties_fase1, container, false);

    LinearLayout linearLayout = (LinearLayout) vFase1.findViewById(R.id.linearLayout1);



    recyclerViewDF1 = (RecyclerView) vFase1.findViewById(R.id.dutiesfase1);
    recyclerViewDF1.setHasFixedSize(true);
    recyclerViewDF1.setLayoutManager(new LinearLayoutManager(getContext()));

    // Test data
    //Vakken();
    Vakken1 = new ArrayList<>();

    //Real Data
    VakkenDatabase();

      cA1 = new courseAdapter(getActivity(),Vakken1);
      recyclerViewDF1.setAdapter(cA1);
      //cA1.notifyDataSetChanged();
      setHasOptionsMenu(true);


    LayoutCredits = getActivity().findViewById(R.id.slideUpCreditsView);
    LinearLayout lay = LayoutCredits.findViewById(R.id.Progress_bar_points);
    btnSend = (Button) lay.findViewById(R.id.versturen_credits);
    progressWheelFase1 = (ProgressWheel) lay.findViewById(R.id.count_progressBar);




    clickOnVakken();
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
        Toast.makeText(getContext(),"search and filter course in fase 3",Toast.LENGTH_SHORT).show();


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






  }
  private void VakkenDatabase (){
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
          Vakken1.add(new Vak(course_id.toString(),course.toString(),credit.toString()+" sp.",creditPunten.toString()));
          cA1.notifyDataSetChanged();
        }

      }


      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });

  }

  private void clickOnVakken(){


    cA1.setOnItemClickListener(new courseAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(int position) {
        //String plaats = Vakken1.get(position).getCourse();
        String point = Vakken1.get(position).getCreditPunten();
        boolean checked = Vakken1.get(position).isChecked();

        if(!checked){
          Vakken1.get(position).setChecked(true);
          int x = (int) cA1.getItemId(position);
          if(!(count> 60)){
            count += Integer.parseInt(point);
            int percent = (360/60) * count;
            Toasty.custom(getContext(), "+ "+ count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.DKGRAY,Toast.LENGTH_SHORT,true,true).show();
            progressWheelFase1.setProgress(percent);
            progressWheelFase1.setText(Integer.toString(count)+" sp");

            if (count == 60) {
              progressWheelFase1.setBarColor(Color.	rgb(0,128,0));
              //btnSend.setEnabled(true);
              //btnSend.setBackgroundColor(Color.rgb(0,128,0));
            }else if(count >= 45 && count < 60){
              progressWheelFase1.setBarColor(Color.rgb(255,69,0));
            }else if (count >= 30 && count < 45) {
              progressWheelFase1.setBarColor(Color.rgb(255,140,0));
            }else if (count >= 15 && count < 30) {
              progressWheelFase1.setBarColor(Color.rgb(255,165,0));
            }else if (count >= 0 && count < 15) {
              progressWheelFase1.setBarColor(Color.	rgb(255,215,0));
            }
          }else{
            Toasty.error(getContext()," Je mag maar met 60 credit uur jaar starten", Toast.LENGTH_SHORT).show();
            count = count + 0;
          }

        }else{
          Vakken1.get(position).setChecked(false);
          if(!(count < 0)){
            count -= Integer.parseInt(point);
            int percent = (360/60) * count;
            Toasty.custom(getContext(), "+ "+count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.rgb(204,204,0),Toast.LENGTH_SHORT,true,true).show();
            progressWheelFase1.setProgress(percent);
            progressWheelFase1.setText(Integer.toString(count)+" sp");
            if (count == 60) {
              progressWheelFase1.setBarColor(Color.	rgb(0,128,0));
            }else if(count >= 45 && count < 60){
              progressWheelFase1.setBarColor(Color.rgb(255,69,0));
              //btnSend.setEnabled(false);
            }else if (count >= 30 && count < 45) {
              progressWheelFase1.setBarColor(Color.rgb(255,140,0));
            }else if (count >= 15 && count < 30) {
              progressWheelFase1.setBarColor(Color.rgb(255,165,0));
            }else if (count >= 0 && count < 15) {
              progressWheelFase1.setBarColor(Color.	rgb(255,215,0));
            }
          }else{
            Toasty.error(getContext(),"Mag niet Onder de 0", Toast.LENGTH_SHORT).show();
            count = count + 0;
          }


        }

      }
    });

    cA1.setOnItemLongClickListener(new courseAdapter.onItemLongClickListerner() {
      @Override
      public boolean onItemLongClick(int position) {
        String plaats = Vakken1.get(position).getCourse();
        String point = Vakken1.get(position).getCreditPunten();

        count -= Integer.parseInt(point);
        Toast.makeText(getContext(),count+" sp . press short to select", Toast.LENGTH_SHORT).show();
        return true;
      }
    });


  }

}
