package app.stucre;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class ElectivesFase3 extends Fragment {

    private RecyclerView recyclerViewEF3;
    private RecyclerView.Adapter cAEF3;

    private List<Vak> VakkenEF3;
    Vak test;

    public ElectivesFase3(){

    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference electivFase3 = database.getReference("Bedrijfskunde/TI/Electives/fase 3");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_electives_fase3, container, false);

        recyclerViewEF3 = (RecyclerView) view.findViewById(R.id.electivesFase3);
        recyclerViewEF3.setHasFixedSize(true);
        recyclerViewEF3.setLayoutManager(new LinearLayoutManager(getContext()));

        VakkenEF3 = new ArrayList<>();
        VakkenEF3.add(new Vak("HBI33A","Sales and Customer interaction", "3 sp","3"));
        VakkenEF3.add(new Vak("HBI47B","Content management", "3 sp","3"));
        VakkenEF3.add(new Vak("HBI53B","Advanced Switching", "6 sp","6"));
        VakkenEF3.add(new Vak("HBI54B","Advanced Routing", "6 sp","6"));
        VakkenEF3.add(new Vak("OBI01A","ICT Partner Training", "3 sp","3"));
        VakkenEF3.add(new Vak("OBI02A","ICT Partner Training B", "4 sp","4"));
        VakkenEF3.add(new Vak("OBI03A","ICT Partner Training C", "5 sp","5"));
        VakkenEF3.add(new Vak("OBI04A","ICT Partner Training D", "6 sp","6"));


        electivFase3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child:children) {

                    Object course_id = child.child("COURSE_ID").getValue(Object.class);
                    Object course = child.child("COURSE").getValue(Object.class);
                    Object credit = child.child("CREDITS").getValue(Object.class);
                    Object creditPunten = child.child("CREDITS").getValue(Object.class);
                    //VakkenEF3.add(new Vak(course_id.toString(),course.toString(),credit.toString()+" sp.",creditPunten.toString()));


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        cAEF3= new courseAdapter(getContext(),VakkenEF3);
        recyclerViewEF3.setAdapter(cAEF3);
        cAEF3.notifyDataSetChanged();

        // Inflate the layout for this fragment
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        //listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
}
