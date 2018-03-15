package app.stucre;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static app.stucre.R.id.text1;
import static app.stucre.R.id.text2;


public class ElectivesFase3 extends Fragment {


    public ElectivesFase3(){

    }



    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference electivFase3 = database.getReference("Bedrijfskunde/TI/Electives/fase 3");
    private List<Vak> Vakken = new ArrayList<>();
    private courseAdapter cA;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_electives, container, false);

        ListView listView = (ListView) view.findViewById(R.id.list_electives);
        electivFase3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child:children) {

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
        listView.setAdapter(cA);

        // Inflate the layout for this fragment
        return view;

    }

    @Override
    public void onStart() {
        ListView listView = (ListView) getActivity().findViewById(R.id.list_electives);
        super.onStart();
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    }
}
