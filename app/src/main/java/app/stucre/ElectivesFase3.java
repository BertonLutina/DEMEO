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


public class ElectivesFase3 extends ListFragment {

    private String course;
    private String credits;


    public ElectivesFase3(){

    }


    HashMap<String,String> mCourse = new HashMap<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference electivFase3 = database.getReference("Bedrijfskunde/TI/Electives/fase 3");

    SimpleAdapter simpleAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        final List<HashMap<String,String>> listItems = new ArrayList<>();

        electivFase3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listItems.clear();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();


                for (DataSnapshot child:children) {



                    Object course = child.child("COURSE").getValue(Object.class);
                    Object credit = child.child("CREDITS").getValue(Object.class);

                    mCourse.put(course.toString(),credit.toString());


                    Iterator<Map.Entry<String, String>> courses = mCourse.entrySet().iterator();

                    if (courses.hasNext()){
                        HashMap<String,String> resultMap = new HashMap<>();
                        Map.Entry pair = (Map.Entry)courses.next();
                        resultMap.put("First Line",pair.getKey().toString());
                        resultMap.put("Second Line",pair.getValue().toString().concat("sp"));
                        listItems.add(resultMap);
                    }








                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //adapter = new ArrayAdapter<HashMap<String,String>>(getActivity(), android.R.layout.select_dialog_multichoice, listItems);

         simpleAdapter = new SimpleAdapter(getActivity(),listItems,R.layout.list_item,
                new String[]{"First Line","Second Line"},
                new int[]{R.id.text1, R.id.text2});

        //setListAdapter(adapter);
        setListAdapter(simpleAdapter);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_electives, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    }
}
