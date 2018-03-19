package app.stucre;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.ListFragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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


public class DutiesFase1 extends android.support.v4.app.ListFragment {

  public DutiesFase1 (){

  }

  private List<Vak> Vakken = new ArrayList<>();
  private courseAdapter cA;
  SearchView searchView;


  FirebaseDatabase database = FirebaseDatabase.getInstance();
  DatabaseReference dutiesFase1 = database.getReference("Bedrijfskunde/TI/Duties/fase 1");






  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View v = super.onCreateView(inflater, container, savedInstanceState);
    //searchView = (SearchView) searchView.findViewById(R.id.duties_search);

   dutiesFase1.addValueEventListener(new ValueEventListener() {
     @Override
     public void onDataChange(DataSnapshot dataSnapshot) {
       Iterable<DataSnapshot> children = dataSnapshot.getChildren();

       for (DataSnapshot child: children) {
         Object course_id = child.child("COURSE_ID").getValue(Object.class);
         Object course = child.child("COURSE").getValue(Object.class);
         Object credit = child.child("CREDITS").getValue(Object.class);
         Vakken.add(new Vak(course_id.toString(),course.toString(),credit.toString()+" sp."));
         cA.notifyDataSetChanged();

       }



     }


     @Override
     public void onCancelled(DatabaseError databaseError) {

     }
   });

    cA  = new courseAdapter(getContext(),Vakken);
    setListAdapter(cA);
/*
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        return false;
      }

      @Override
      public boolean onQueryTextChange(String newText) {


        return false;
      }
    });

    getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

      }
    });*/

    return v;



  }




  @Override
  public void onStart() {
    super.onStart();
    getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
  }
}