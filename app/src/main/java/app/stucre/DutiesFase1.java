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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class DutiesFase1 extends ListFragment  {
  ProgressBar progressBar ;

  boolean iscolor = true;

  View previousSelectedItem;
  TextView tCourse;
  TextView tCredits;
  TextView tId ;
  RadioGroup radioGroup;


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
  int counter = 0;

  FirebaseDatabase database = FirebaseDatabase.getInstance();
  DatabaseReference dutiesFase1 = database.getReference("Bedrijfskunde/TI/Duties/fase 1");


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


  progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBar2);
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


    //checkBox = (CheckBox) getActivity().findViewById(R.id.checkbox1);




    return super.onCreateView(inflater, container, savedInstanceState);




  }


  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);


    radioGroup = (RadioGroup) getActivity().findViewById(R.id.radioGroup1);

    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      ViewPager pager = (ViewPager) getActivity().findViewById(R.id.pager);
      CheckBox checkBox = (CheckBox) pager.findViewById(R.id.checkbox1);
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {


        checkedId= group.getCheckedRadioButtonId();

        switch (checkedId){
          case R.id.see:
            Toast.makeText(getContext(),"Disable Select Course ",Toast.LENGTH_SHORT).show();

            break;
          case R.id.select:
          //getListView().setSelector(R.drawable.select_item_listview);
            Toast.makeText(getContext(),"Enable Select Course",Toast.LENGTH_SHORT).show();
            break;
          default:
            break;
        }


      }

    });





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




  @Override
  public void onStart() {
    super.onStart();
   getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

  }

}