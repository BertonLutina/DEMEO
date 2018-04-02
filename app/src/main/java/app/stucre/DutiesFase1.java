package app.stucre;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.ViewParent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v4.app.ListFragment;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.todddavies.components.progressbar.ProgressWheel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class DutiesFase1 extends ListFragment  {


  boolean iscolor = true;
  int count = 0;

  View previousSelectedItem;
  TextView tCourse;
  TextView tCredits;
  TextView tId ;
  RadioGroup radioGroup;
  ProgressWheel progressWheelDuties;
  private List <Vak> ToevoegenVakken = new ArrayList<>();


  public class ViewHolder{
    CheckBox checkBox;
  }

  public DutiesFase1 (){

  }

  private List<Vak> Vakken = new ArrayList<>();
  private Boolean [] vakkenSize = new Boolean[]{};
  private courseAdapter cA;
  private ArrayAdapter<Vak> arrayAdapter;


  SearchView searchView;
  CheckBox checkBox;


  FirebaseDatabase database = FirebaseDatabase.getInstance();
  DatabaseReference dutiesFase1 = database.getReference("Bedrijfskunde/TI/Duties/fase 1");


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



  searchView = (SearchView) getActivity().findViewById(R.id.duties_search);



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
         Vakken.add(new Vak(course_id.toString(),course.toString(),credit.toString()+" sp.",creditPunten.toString()));
         cA.notifyDataSetChanged();
       }

     }


     @Override
     public void onCancelled(DatabaseError databaseError) {

     }
   });

    cA  = new courseAdapter(getActivity(),Vakken);
    setListAdapter(cA);

    radioGroup = (RadioGroup) getActivity().findViewById(R.id.radioGroup1);
    progressWheelDuties = (ProgressWheel) getActivity().findViewById(R.id.progressBar);

    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      ViewPager pager = (ViewPager) getActivity().findViewById(R.id.pager);
      CheckBox checkBox = (CheckBox) pager.findViewById(R.id.checkbox1);
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
      int counter = 0;

        checkedId= group.getCheckedRadioButtonId();

        switch (checkedId){
          case R.id.see:
            Vakken = getModel(false);
            cA = new courseAdapter(getContext(), Vakken);
            setListAdapter(cA);
            for (int i = 0; i< Vakken.size(); i++){
              count += Integer.parseInt(Vakken.get(i).getCreditPunten());
            }
            progressWheelDuties.setProgress(count);
            Toast.makeText(getActivity(),"All courses are UnSelected ",Toast.LENGTH_SHORT).show();

            break;
          case R.id.select:
            Vakken = getModel(true);
            cA = new courseAdapter(getContext(), Vakken);
            setListAdapter(cA);
            for (int i = 0; i< Vakken.size(); i++){
              count -= Integer.parseInt(Vakken.get(i).getCreditPunten());
            }
            progressWheelDuties.setProgress(count);
            //getListView().setSelector(R.drawable.select_item_listview);
            Toast.makeText(getActivity(),"All courses are Selected",Toast.LENGTH_SHORT).show();
            break;
          default:
            break;
        }


      }

    });


    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        return false;
      }

      @Override
      public boolean onQueryTextChange(String newText) {

        cA.getFilter().filter(newText);
        return true;
      }
    });




    return super.onCreateView(inflater, container, savedInstanceState);




  }


  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);






   /* getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
      @Override
      public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
        Vak vakken = Vakken.get(position);

        tCourse = (TextView) v.findViewById(R.id.text1);
        tCredits = (TextView) v.findViewById(R.id.text2);
        tId = (TextView) v.findViewById(R.id.text3);

        for (int i =0; i < cA.getCount(); i++){

          v.setBackgroundColor(Color.rgb(34,41,43));

          tCourse.setTextColor(Color.rgb(221, 221, 221));
          tCredits.setTextColor(Color.rgb(221, 221, 221));
          tId.setTextColor(Color.rgb(255, 210, 186));

        }
        return false;
      }
    });*/






  }

  private List<Vak> getModel(final boolean isChecked){
    final List<Vak> list = new ArrayList<>();

    /*for(int i = 0; i < Vakken.size(); i++){

      Vak vak = new Vak();
      vak.setChecked(isChecked);
      vak.setCourse(vak.getCourse());
      vak.setCredit(vak.getCredit());
      vak.setId(vak.getId());
      list.add(vak);
    }*/

    dutiesFase1.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
        Vak vak = new Vak();
        TextView setPoint = (TextView) getActivity().findViewById(R.id.countCreditOptions);

        for (DataSnapshot child: children) {
          Object course_id = child.child("COURSE_ID").getValue(Object.class);
          Object course = child.child("COURSE").getValue(Object.class);
          Object credit = child.child("CREDITS").getValue(Object.class);
          Object creditPunten = child.child("CREDITS").getValue(Object.class);




          //vak.setId(course_id.toString());
          //vak.setCourse(course.toString());
          //vak.setCredit(credit.toString());
          //vak.setCreditPunten(creditPunten.toString());
          list.add(new Vak(course_id.toString(),course.toString(),credit.toString()+" sp.",isChecked,creditPunten.toString()));
          //Vakken.add(new Vak(course_id.toString(),course.toString(),credit.toString()+" sp.",creditPunten.toString()));
          cA.notifyDataSetChanged();
        }



        for (int i = 0 ; i < list.size(); i++){

        final Vak vakken = list.get(i);

        if(isChecked){
          vakken.setChecked(true);
          count += Integer.parseInt(vakken.getCreditPunten().toString());
          String punten = Integer.toString(count);
          setPoint.setText(punten);

        }
        else
          {
          vakken.setChecked(false);
          count -= Integer.parseInt(vakken.getCreditPunten().toString());
          String punten = Integer.toString(count);
          setPoint.setText(punten);
        }

        }

      }


      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
    return list;
  }


  @Override
  public void onStart() {
    super.onStart();
   getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

  }

}