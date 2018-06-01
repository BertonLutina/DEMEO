package app.stucre;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.support.v7.widget.SearchView;
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
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class DutiesFase2 extends Fragment implements android.support.v7.widget.SearchView.OnQueryTextListener {

  private RecyclerView recyclerViewDF2;
  private courseAdapter cA2;

  private List<Vak> Vakken2 = new ArrayList<>();
  private List<Vak> Vakken2_send = new ArrayList<>();

    private ProgressWheel progressWheelFase2;
    private View dutiesLayout;

  private int count = 0;
    private Button btnSend;
    private Switch selectall;
    private Switch selectall2;
    private Switch selectall3;

    EventBus bus = EventBus.getDefault();
    EventBus bus2 = EventBus.getDefault();

    private HashMap<String,Integer> scoreVak = new HashMap<String, Integer>();
    private int clicks = 0;
    private int clickbutton = 0;
    private boolean checked;
    private int score = 0;


    public DutiesFase2(){

  }

  FirebaseDatabase database = FirebaseDatabase.getInstance();
  DatabaseReference dutiesFase2 = database.getReference("Bedrijfskunde/TI/Duties/fase 2");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bus.register(this);
    }

    @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View vFase2 = inflater.inflate(R.layout.fragment_duties_fase2, container, false);


    return vFase2;


  }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewDF2 = (RecyclerView) view.findViewById(R.id.dutiesfase2);
        recyclerViewDF2.setHasFixedSize(true);
        recyclerViewDF2.setLayoutManager(new LinearLayoutManager(getContext()));



        VakkenDatabase();


        cA2 = new courseAdapter(getContext(),Vakken2);
        recyclerViewDF2.setAdapter(cA2);
        //cA2.isEnable = false;
        cA2.notifyDataSetChanged();

        clickOnVakken();

        selectall2 = (Switch) getActivity().findViewById(R.id.selectAllSwitchFase2);


        selectall2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    for (Vak vak : Vakken2){
                        vak.setChecked(true);
                        Vakken2_send.add(vak);
                        boolean checked = vak.isChecked();
                        Integer point = vak.getCreditPunten();
                        // count = Integer.parseInt(point);
                        //count++;
                        count += point;

                        int percent = 360;
                        progressWheelFase2.setProgress(percent);
                        progressWheelFase2.setText(Integer.toString(count)+" sp");
                        cA2.notifyDataSetChanged();


                    }


                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setMessage("Would like to continue?")
                            .setTitle("Go to Electives Modules")
                            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {


                                    Intent change = new Intent(getActivity(),electivesModules.class);
                                    change.putExtra("Vaken",count);
                                    change.putExtra("D2",(ArrayList<Vak>)Vakken2);

                                    startActivity(change);

                                }
                            }).setNegativeButton("Stop", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();


                    Toasty.custom(getContext(), "+ "+ count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.DKGRAY,Toast.LENGTH_SHORT,true,true).show();
                }else{
                    for (Vak vak : Vakken2) {
                        vak.setChecked(false);
                        Vakken2_send.remove(vak);
                        boolean checked = vak.isChecked();
                        Integer point = vak.getCreditPunten();
                        count -= point;
                        int percent = 0;
                        progressWheelFase2.setProgress(percent);
                        progressWheelFase2.setText(Integer.toString(count) + " sp");
                        cA2.notifyDataSetChanged();

                    }
                }
            }
        });

        setHasOptionsMenu(true);

        dutiesLayout = getActivity().findViewById(R.id.slideUpCreditsView);
        LinearLayout lay = dutiesLayout.findViewById(R.id.Progress_bar_points);
        btnSend = (Button) lay.findViewById(R.id.versturen_credits);
        progressWheelFase2 = (ProgressWheel) lay.findViewById(R.id.count_progressBar);
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
  private void VakkenDatabase(){
      dutiesFase2.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {

              Iterable<DataSnapshot> children = dataSnapshot.getChildren();

              for (DataSnapshot child: children) {


                  Object course_id = child.child("COURSE_ID").getValue(Object.class);
                  Object course = child.child("COURSE").getValue(Object.class);
                  Object credit = child.child("CREDIT").getValue(Object.class);
                  Object creditPunten = child.child("CREDITPOINT").getValue(Object.class);
                  Object fase = child.child("FASE").getValue(Object.class);
                  Object score = child.child("SCORE").getValue(Object.class);
                  Object succeeded = child.child("SUCCEEDED").getValue(Object.class);
                  Vakken2.add(new Vak(course_id.toString(), course.toString(), credit.toString(),Integer.parseInt(creditPunten.toString()),Integer.parseInt(fase.toString()),Integer.parseInt(score.toString()),Boolean.valueOf(succeeded.toString()),false));
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
              Integer point = Vakken2.get(position).getCreditPunten();
              boolean checked = Vakken2.get(position).isChecked();

              //btnSend.setEnabled(false);

              if(!checked) {
                  Vakken2.get(position).setChecked(true);
                  if( !(count>= 42)){
                      count += point;
                      int percent = (360/42) * count;
                      progressWheelFase2.setProgress(percent);
                      progressWheelFase2.setText(Integer.toString(count)+" sp");
                      Toasty.custom(getContext(), "+ "+ point+" sp. ->  "+"Total: "+count, getResources().getDrawable(R.drawable.booksstacktwee), Color.DKGRAY,Toast.LENGTH_SHORT,true,true).show();

                  }else{
                      Toasty.error(getContext(),"60 sp is de limiet => Electives Modules", Toast.LENGTH_SHORT).show();
                  }
                  if (count == 42) {
                      progressWheelFase2.setBarColor(Color.	rgb(0,128,0));
                      AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                      builder.setMessage("Would like to continue?")
                      .setTitle("Go to Electives Modules")
                      .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialogInterface, int i) {

                              Intent change = new Intent(getActivity(),electivesModules.class);
                              change.putExtra("Vaken",count);
                              change.putExtra("D2",(ArrayList<Vak>)Vakken2);
                              startActivity(change);

                          }
                      }).setNegativeButton("Stop", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialogInterface, int i) {
                              return;
                          }
                      });

                      AlertDialog dialog = builder.create();
                      dialog.show();
                  }else if(count >= 28 && count < 42){
                      progressWheelFase2.setBarColor(Color.rgb(255,69,0));
                  }else if (count >= 15 && count < 30) {
                      progressWheelFase2.setBarColor(Color.rgb(255,165,0));
                  }else if (count >= 0 && count < 15) {
                      progressWheelFase2.setBarColor(Color.	rgb(255,215,0));
                  }
              }else{
                  Vakken2.get(position).setChecked(false);
                  if(!(count < 0)){
                      count -= point;
                      int percent = (360/60) * count;
                      progressWheelFase2.setProgress(percent);
                      progressWheelFase2.setText(Integer.toString(count)+" sp");
                      Toasty.custom(getContext(), "+ "+ point+" sp. ->  "+"Total: "+count, getResources().getDrawable(R.drawable.booksstacktwee), Color.DKGRAY,Toast.LENGTH_SHORT,true,true).show();

                  }else{
                      Toasty.error(getContext()," Mag niet onder de 0", Toast.LENGTH_SHORT).show();
                  }
                  if (count == 42) {
                  }else if(count >= 28 && count < 42){
                      //btnSend.setEnabled(false);
                  }else if (count >= 15 && count < 30) {

                  }else if (count >= 0 && count < 15) {

                  }
              }


          }
      });

      cA2.setOnItemLongClickListener(new courseAdapter.onItemLongClickListerner() {
          @Override
          public boolean onItemLongClick(int position) {
              clickbutton++;
              final int pos = position;
              boolean geslaagd = Vakken2.get(pos).isGeslaagd();

              String course = Vakken2.get(pos).getCourse();
              boolean  checked = Vakken2.get(pos).isChecked();

              Integer getScore = Vakken2.get(pos).getScore();
              clicks = 0;

              scoreVak.put(course,getScore);
              Integer value = scoreVak.get(course);

              if(value == 0)
              {

                  NietGeslaagdeVakken(pos,course,geslaagd);
              }
              else if (value < 10 )
              {

                  NietGeslaagdeVakken(pos,course,geslaagd);
              }
              else if (value>= 10 || value <= 20)
              {

                  Vakken(pos, geslaagd);
              }





              return true;
          }
      });
  }

  @Subscribe
  public void onEvent (duties.Voltwaardigheden event){

          for(int i = 0; i < cA2.getItemCount(); i++){
              if(cA2.getItemVak(i).getVoltijdigheden() != null ){
                  for(int v = 0 ; v < cA2.getItemVak(i).getVoltijdigheden().length; v++){
                      if(TextUtils.equals(event.vakpositie.getCourse(),cA2.getItemVak(i).getVoltijdigheden()[v])) {
                          cA2.getItemVak(i).setCourse("Yellow");
                          cA2.notifyDataSetChanged();
                          Log.d("DutiesFase2", cA2.getItemVak(i).getVoltijdigheden().toString());
                      }
                  }
              }
          }
      }


    public void Vakken(final int pos, boolean geslaagdvak){


        geslaagdvak = true;
        String textScore = Integer.toString(Vakken2.get(pos).getScore());
        Integer Score = Vakken2.get(pos).setScore(Integer.parseInt(textScore));
        final String course = Vakken2.get(pos).getCourse();
        Integer getScore = Vakken2.get(pos).getScore();

        final AlertDialog.Builder dialogvak = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();


        View dialogBack = inflater.inflate(R.layout.dialogscore, null);

        TextView vak = (TextView) dialogBack.findViewById(R.id.vakDialoog);
        TextView score = (TextView) dialogBack.findViewById(R.id.score);
        TextView geslaagd = (TextView) dialogBack.findViewById(R.id.txtgeslaagd);

        final Integer value = scoreVak.get(course);

        vak.setText(course);

        if (value > 10 || value < 20) {
            geslaagd.setText("Geslaagd");
            geslaagd.setBackgroundColor(Color.rgb(20, 120, 0));
            geslaagd.setTextColor(Color.rgb(246, 246, 246));
            score.setText(getScore.toString()+ "/20");
        } else if (value == 0) {
            geslaagd.setText("Onbekend");
            geslaagd.setBackgroundColor(Color.rgb(120, 120, 120));
            geslaagd.setTextColor(Color.rgb(246, 246, 246));
            score.setText(getScore.toString()+ "/20");
        }
        if (value < 10) {
            geslaagd.setText("Onvoldoende");
            geslaagd.setBackgroundColor(Color.rgb(128, 20, 0));
            geslaagd.setTextColor(Color.rgb(246, 246, 246));
            score.setText(getScore.toString()+ "/20");
        }

        dialogvak.setView(dialogBack).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getContext(),"Score: " + value+" /20",Toast.LENGTH_SHORT).show();

            }
        });

        dialogvak.setView(dialogBack).setNegativeButton("Rewrite", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(Vakken2.get(pos).isGeslaagd() == false)
                    NietGeslaagdeVakken(pos,course,Vakken2.get(pos).isGeslaagd());


            }
        });


        AlertDialog alertVak = dialogvak.create();

        alertVak.show();





    }

    public void NietGeslaagdeVakken(final int pos, String course, boolean geslaagd){

        geslaagd = false;
        AlertDialog.Builder inputVak = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View inputView = inflater.inflate(R.layout.inputscore, null);

        final EditText inputText = (EditText) inputView.findViewById(R.id.cijferinputvak);
        TextView vakText = (TextView) inputView.findViewById(R.id.vakinput);
        vakText.setText(course);


        inputVak.setView(inputView).setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                clicks++;
                checked = true;
                if(scoreVak == null)
                    Toasty.error(getContext(), "Er werd geen vak geselecteerd", Toast.LENGTH_SHORT).show();

                if (clicks > 4)
                {
                    Toasty.error(getContext(), "Je mag maar 3 keren uw score veranderen!!", Toast.LENGTH_SHORT).show();
                    return;
                }



                Vakken2.get(pos).setScore(Integer.parseInt(inputText.getText().toString()));

                scoreVak.put(Vakken2.get(pos).getCourse(), Vakken2.get(pos).getScore());

                Integer value = scoreVak.get(Vakken2.get(pos).getCourse());

                if ((value < 0 || value > 20))
                    Toasty.error(getContext(), "Score moet tussen 0 en 20 zijn!", Toast.LENGTH_SHORT).show();


                if ( value > 0 || value < 10 )
                {
                    Vakken2.get(pos).setGeslaagd(false);

                }
                else if (value>= 10 || value <= 20)
                {
                    Vakken2.get(pos).setGeslaagd(true);
                }

                Vakken(pos,Vakken2.get(pos).isGeslaagd());



            }
        });


        AlertDialog dialog = inputVak.create();

        dialog.show();
    }


}
