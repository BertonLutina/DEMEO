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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
public class DutiesFase1 extends Fragment  {

  private RecyclerView recyclerViewDF1;
  private RecyclerView.Adapter cA1;

  private List<Vak> Vakken1;

  public DutiesFase1 (){

  }

  FirebaseDatabase database = FirebaseDatabase.getInstance();
  DatabaseReference dutiesFase1 = database.getReference("Bedrijfskunde/TI/Duties/fase 1");

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View vFase1 = inflater.inflate(R.layout.fragment_duties_fase1, container, false);

    recyclerViewDF1 = (RecyclerView) vFase1.findViewById(R.id.dutiesfase1);
    recyclerViewDF1.setHasFixedSize(true);
    recyclerViewDF1.setLayoutManager(new LinearLayoutManager(getContext()));

    Vakken1 = new ArrayList<>();

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

       }

     }


     @Override
     public void onCancelled(DatabaseError databaseError) {

     }
   });

      cA1 = new courseAdapter(getContext(),Vakken1);
      recyclerViewDF1.setAdapter(cA1);
      cA1.notifyDataSetChanged();

    return vFase1;




  }


  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

  }




  @Override
  public void onStart() {
    super.onStart();
   //Ldutiesfase1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

  }

}