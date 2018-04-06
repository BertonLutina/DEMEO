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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SearchView;
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
public class DutiesFase2 extends Fragment {

  private RecyclerView recyclerViewDF2;
  private RecyclerView.Adapter cA2;

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


        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });


    cA2 = new courseAdapter(getContext(),Vakken2);
    recyclerViewDF2.setAdapter(cA2);
    cA2.notifyDataSetChanged();


    return vFase2;


  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

  }




  @Override
  public void onStart() {

    super.onStart();
    //Ldutiesfase2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

  }
}
