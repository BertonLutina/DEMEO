package app.stucre;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.ListFragment;
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
import java.util.Map;


public class DutiesFase1 extends android.support.v4.app.ListFragment {

  public DutiesFase1 (){

  }

  final ArrayList<String> mCoursenames = new ArrayList<String>();

  FirebaseDatabase database = FirebaseDatabase.getInstance();
  DatabaseReference dutiesFase1 = database.getReference("Bedrijfskunde/TI/Duties/fase 1");



  ArrayAdapter<String>adapter;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


   dutiesFase1.addValueEventListener(new ValueEventListener() {
     @Override
     public void onDataChange(DataSnapshot dataSnapshot) {
       Iterable<DataSnapshot> children = dataSnapshot.getChildren();

       for (DataSnapshot child: children) {
         Object value = child.child("COURSE").getValue(Object.class);
         mCoursenames.add(value.toString());
         adapter.notifyDataSetChanged();

       }



     }


     @Override
     public void onCancelled(DatabaseError databaseError) {

     }
   });

    adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_multichoice, mCoursenames);


   if(adapter.isEmpty()){

    setListAdapter(adapter);}

    return super.onCreateView(inflater, container, savedInstanceState);

  }




  @Override
  public void onStart() {
    super.onStart();
    getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
  }
}