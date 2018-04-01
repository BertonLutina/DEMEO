package app.stucre;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DutiesFase2 extends ListFragment {

  public DutiesFase2(){

  }

  private List<Vak> Vakken = new ArrayList<>();
  private courseAdapter cA;

  FirebaseDatabase database = FirebaseDatabase.getInstance();
  DatabaseReference dutiesFase2 = database.getReference("Bedrijfskunde/TI/Duties/fase 2");


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    dutiesFase2.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {

        Iterable<DataSnapshot> children = dataSnapshot.getChildren();

        for (DataSnapshot child: children) {


          Object course_id = child.child("COURSE_ID").getValue(Object.class);
          Object course = child.child("COURSE").getValue(Object.class);
          Object credit = child.child("CREDITS").getValue(Object.class);
          Object creditPunten = child.child("CREDITS").getValue(Object.class);
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
    return super.onCreateView(inflater, container, savedInstanceState);


  }

  @Override
  public void onStart() {
    super.onStart();
    getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

  }
}
