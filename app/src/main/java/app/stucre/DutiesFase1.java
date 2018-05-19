package app.stucre;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.support.design.widget.TabLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.todddavies.components.progressbar.ProgressWheel;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
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

  private int credit= 0;
  private int count = 0;
  private Button btnSend;
  private Bundle bundle;
  private TabLayout Tab;
  private ViewPager vp;
  private RecyclerView recyclerViewDF2;
  private RecyclerView recyclerViewDF3;

  private List <Vak> vakken_te_Versturen = new ArrayList<>();
  private Intent intent;
  private Switch selectall;
  private Switch selectall2;
  private Switch selectall3;

  EventBus bus = EventBus.getDefault();


  public DutiesFase1 (){

    }



  FirebaseDatabase database = FirebaseDatabase.getInstance();
  DatabaseReference dutiesFase1 = database.getReference("Bedrijfskunde/TI/Duties/fase 1");



  @Override
  public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

    View vFase1 = inflater.inflate(R.layout.fragment_duties_fase1, container, false);

    return vFase1;

  }



  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

      LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout1);

      vp = (ViewPager) getActivity().findViewById(R.id.pager);
      Tab = (TabLayout) getActivity().findViewById(R.id.tabsDuties);
      vp.getAdapter();

      recyclerViewDF1 = (RecyclerView) view.findViewById(R.id.dutiesfase1);
      recyclerViewDF1.setHasFixedSize(true);
      recyclerViewDF1.setLayoutManager(new LinearLayoutManager(getContext()));

      //Vakken1 = new ArrayList<>();
    /*Bundle extras = getArguments();
    if(extras == null){
      return vFase1;

    }*/

      Intent intent = getActivity().getIntent();

      Vakken1 = (ArrayList<Vak>) intent.getSerializableExtra("FaseEen");
      count = intent.getIntExtra("Credit",credit);
      //duties D = (duties) getActivity();
      //D.sendData();

      // Test data
      //Vakken();

      //Vakken1 = new ArrayList<>();
      //Real Data
      //VakkenDatabase();

      cA1 = new courseAdapter(getActivity(),Vakken1);
      recyclerViewDF1.setAdapter(cA1);
      cA1.notifyDataSetChanged();

      setHasOptionsMenu(true);



      LayoutCredits = getActivity().findViewById(R.id.slideUpCreditsView);
      LinearLayout lay = LayoutCredits.findViewById(R.id.Progress_bar_points);
      LayoutInflater inflater = getActivity().getLayoutInflater();
      View vSlide = inflater.inflate(R.layout.slideupview_credit_point,null);
      progressWheelFase1 = (ProgressWheel) lay.findViewById(R.id.count_progressBar);




      clickOnVakken();

      selectall = (Switch) getActivity().findViewById(R.id.selectAllSwitchFase1);

      selectall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

              if(b){
                  for (Vak vak : Vakken1){
                      vak.setChecked(true);
                      boolean checked = vak.isChecked();
                      String point = vak.getCreditPunten();
                      //count = Integer.parseInt(point);
                      //count++;
                      count =+ 60;
                      int percent = 360;
                      progressWheelFase1.setProgress(percent);
                      progressWheelFase1.setText(Integer.toString(count)+" sp");
                      cA1.notifyDataSetChanged();
                  }
                  Toasty.custom(getContext(), "+ "+ count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.DKGRAY,Toast.LENGTH_SHORT,true,true).show();
              }else{
                  for (Vak vak : Vakken1) {
                      vak.setChecked(false);
                      boolean checked = vak.isChecked();
                      String point = vak.getCreditPunten();
                      //count =Integer.parseInt(point);
                      //count--;
                      count = 0;
                      int percent = count;
                      progressWheelFase1.setProgress(percent);
                      progressWheelFase1.setText(Integer.toString(count) + " sp");
                      cA1.notifyDataSetChanged();
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
          //Vakken1.add(new Vak(course_id.toString(),course.toString(),credit.toString()+" sp.",creditPunten.toString()));
          cA1.notifyDataSetChanged();
        }

      }


      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });

  }

  private void clickOnVakken(){

     intent = new Intent(getContext(),duties.class);

    cA1.setOnItemClickListener(new courseAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(int position) {
        //String plaats = Vakken1.get(position).getCourse();
        String point = Vakken1.get(position).getCreditPunten();
        Vak course_to_send = Vakken1.get(position);
        boolean checked = Vakken1.get(position).isChecked();

        if(!checked){

          Vakken1.get(position).setChecked(true);
          int x = (int) cA1.getItemId(position);

          if(!(count> 60)){
            count += Integer.parseInt(point);
            int percent = (360/60) * count;
            Toasty.custom(getContext(), "+ "+ point+" sp. ->  "+"Total: "+count , getResources().getDrawable(R.drawable.booksstacktwee), Color.DKGRAY,Toast.LENGTH_SHORT,true,true).show();
            progressWheelFase1.setProgress(percent);
            progressWheelFase1.setText(Integer.toString(count)+" sp");
            vakken_te_Versturen.add(course_to_send);
            Log.d("DutiesFase1", vakken_te_Versturen.toString());
            bus.post(new duties.Voltwaardigheden(course_to_send));
            Log.d("DutiesFase1", course_to_send.getCourse());

            if (count == 60) {
              progressWheelFase1.setBarColor(Color.	rgb(0,128,0));
              //intent.putExtra("OGV", (ArrayList<Vak>)vakken_te_Versturen);
              //btnSend.setEnabled(true);
              //btnSend.setBackgroundColor(Color.rgb(0,128,0));
            }else if(count >= 45 && count < 60){
              progressWheelFase1.setBarColor(Color.rgb(255,69,0));
            }else if (count >= 30 && count < 45) {
              progressWheelFase1.setBarColor(Color.rgb(255,140,0));
            }else if (count >= 15 && count < 30) {
              progressWheelFase1.setBarColor(Color.rgb(255,165,0));
            }else if (count >= 1 && count < 15) {
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
            Toasty.custom(getContext(), "- "+ point+" sp. ->  "+"Total: "+count, getResources().getDrawable(R.drawable.booksstacktwee), Color.DKGRAY,Toast.LENGTH_SHORT,true,true).show();
            progressWheelFase1.setProgress(percent);
            progressWheelFase1.setText(Integer.toString(count)+" sp");
            //vakken_te_Versturen.remove(course_to_send);
            //intent.putExtra("OGV", (ArrayList<Vak>)vakken_te_Versturen);
            //startActivity(intent);
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
        String course = Vakken1.get(position).getCourse();
        Integer Score = Vakken1.get(position).setScore(0);
        Integer getScore = Vakken1.get(position).getScore();

        AlertDialog.Builder dialogvak = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.inputscore,null);


        dialogvak.setView(dialogView).setPositiveButton("OK",new DialogInterface.OnClickListener() {
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
