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
import android.widget.Button;
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

public class DutiesFase2 extends Fragment implements android.support.v7.widget.SearchView.OnQueryTextListener {

  private RecyclerView recyclerViewDF2;
  private courseAdapter cA2;

  private List<Vak> Vakken2 = new ArrayList<>();

    private ProgressWheel progressWheelFase2;
    private View dutiesLayout;

  private int count = 0;
    private Button btnSend;



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

    // object aanmaken
      Vakken2 = new ArrayList<>();

      // Test data
        //Vakken();

      //Real Data
      VakkenDatabase();


        cA2 = new courseAdapter(getContext(),Vakken2);
        recyclerViewDF2.setAdapter(cA2);
        //cA2.notifyDataSetChanged();

        clickOnVakken();

    setHasOptionsMenu(true);

      dutiesLayout = getActivity().findViewById(R.id.slideUpCreditsView);
      LinearLayout lay = dutiesLayout.findViewById(R.id.Progress_bar_points);
      btnSend = (Button) lay.findViewById(R.id.versturen_credits);
      progressWheelFase2 = (ProgressWheel) lay.findViewById(R.id.count_progressBar);

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

  private void Vakken(){

      Vakken2.add(new Vak("HBI02C","ICT Organisation 4", "4 sp","4"));
      Vakken2.add(new Vak("HBI25B","ICT Organisation 3", "4 sp","4"));
      Vakken2.add(new Vak("HBI34B","Mobile en internet 3", "3 sp ","3"));
      Vakken2.add(new Vak("HBI36B","System Management 3", "3 sp","3"));
      Vakken2.add(new Vak("HBI38B","Network Management 3", "3 sp","3"));
      Vakken2.add(new Vak("HBI68B","Communicatietraining 4", "4 sp","4"));
      Vakken2.add(new Vak("HBI69B","Information management 3", "3sp","3"));
      Vakken2.add(new Vak("HBI70B","Information management 4", "3sp","3"));
      Vakken2.add(new Vak("HBI71B","Database development 3", "3 sp","3"));
      Vakken2.add(new Vak("HBI73B","Application development 3", "3 sp","3"));
      Vakken2.add(new Vak("OH3100","Software Engineering 3", "3 sp","3"));
      Vakken2.add(new Vak("OH4100","Communicatietraining 3", "6 sp","6"));
  }
  private void VakkenDatabase(){
      dutiesFase2.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {

              Iterable<DataSnapshot> children = dataSnapshot.getChildren();

              for (DataSnapshot child: children) {


                  Object course_id = child.child("COURSE_ID").getValue(Object.class);
                  Object course = child.child("COURSE").getValue(Object.class);
                  Object credit = child.child("CREDITS").getValue(Object.class);
                  Object creditPunten = child.child("CREDITS").getValue(Object.class);
                  Vakken2.add(new Vak(course_id.toString(),course.toString(),credit.toString()+" sp.",creditPunten.toString()));
                  cA2.notifyDataSetChanged();


              }
          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
      });
  }
  private void clickOnVakken(){
      cA2.setOnItemClickListener(new courseAdapter.OnItemClickListener() {
          @Override
          public void onItemClick(int position) {


              //String plaats = Vakken1.get(position).getCourse();
              String point = Vakken2.get(position).getCreditPunten();
              boolean checked = Vakken2.get(position).isChecked();

              //btnSend.setEnabled(false);

              if(!checked) {
                  Vakken2.get(position).setChecked(true);
                  if( !(count>= 60)){
                      count += Integer.parseInt(point);
                      int percent = (360/60) * count;
                      progressWheelFase2.setProgress(percent);
                      progressWheelFase2.setText(Integer.toString(count)+" sp");
                      Toasty.custom(getContext(), "+ "+count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.DKGRAY,Toast.LENGTH_SHORT,true,true).show();

                  }else{
                      Toasty.error(getContext(),"60 sp is de limiet => Electives Modules", Toast.LENGTH_SHORT).show();
                  }
                  if (count == 60) {
                      progressWheelFase2.setBarColor(Color.	rgb(0,128,0));
                      //btnSend.setEnabled(true);
                      //btnSend.setBackgroundColor(Color.rgb(0,128,0));
                  }else if(count >= 45 && count < 60){
                      progressWheelFase2.setBarColor(Color.rgb(255,69,0));
                  }else if (count >= 30 && count < 45) {
                      progressWheelFase2.setBarColor(Color.rgb(255,140,0));
                  }else if (count >= 15 && count < 30) {
                      progressWheelFase2.setBarColor(Color.rgb(255,165,0));
                  }else if (count >= 0 && count < 15) {
                      progressWheelFase2.setBarColor(Color.	rgb(255,215,0));
                  }
              }else{
                  Vakken2.get(position).setChecked(false);
                  if(!(count < 0)){
                      count -= Integer.parseInt(point);
                      int percent = (360/60) * count;
                      progressWheelFase2.setProgress(percent);
                      progressWheelFase2.setText(Integer.toString(count)+" sp");
                      Toasty.custom(getContext(), "- "+count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.rgb(204,204,0),Toast.LENGTH_SHORT,true,true).show();

                  }else{
                      Toasty.error(getContext()," Mag niet onder de 0", Toast.LENGTH_SHORT).show();
                  }
                  if (count == 60) {
                      progressWheelFase2.setBarColor(Color.	rgb(0,128,0));
                  }else if(count >= 45 && count < 60){
                      progressWheelFase2.setBarColor(Color.rgb(255,69,0));
                      //btnSend.setEnabled(false);
                  }else if (count >= 30 && count < 45) {
                      progressWheelFase2.setBarColor(Color.rgb(255,140,0));
                  }else if (count >= 15 && count < 30) {
                      progressWheelFase2.setBarColor(Color.rgb(255,165,0));
                  }else if (count >= 0 && count < 15) {
                      progressWheelFase2.setBarColor(Color.	rgb(255,215,0));
                  }
              }


          }
      });
  }
}
