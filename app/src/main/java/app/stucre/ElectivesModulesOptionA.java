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


public class ElectivesModulesOptionA extends Fragment {


    private RecyclerView recyclerViewEmA;
    private RecyclerView.Adapter cAEMA;

    private List<Vak> VakkenEmA;

    public ElectivesModulesOptionA(){

    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference EMdatabase = database.getReference("Bedrijfskunde/TI/ElectivesModules/Option A: Design and Build Software");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_electives_modules_option, container, false);

        recyclerViewEmA = (RecyclerView) view.findViewById(R.id.emoptionA);
        recyclerViewEmA.setHasFixedSize(true);
        recyclerViewEmA.setLayoutManager(new LinearLayoutManager(getContext()));

        VakkenEmA = new ArrayList<>();
        VakkenEmA.add(new Vak("OH3101","Software Engineering 4", "6 sp","6"));
        VakkenEmA.add(new Vak("OH3102","Application Development 4", "6 sp","6"));
        VakkenEmA.add(new Vak("OH3103","Database Development 4", "6 sp","6"));
        VakkenEmA.add(new Vak("HBI03C","Software Engineering 5: Software testing", "4 sp","60"));
        VakkenEmA.add(new Vak("HBI84B","Application Development 5", "4 sp","4"));
        VakkenEmA.add(new Vak("OH3107","Usability and Interaction Design", "4 sp","4"));
        VakkenEmA.add(new Vak("OH3107","Integration project software", "9 sp","9"));

        EMdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot fase: children) {

                    String fases = fase.getKey();

                    if (TextUtils.equals(fases,"fase 2")){

                        Iterable<DataSnapshot> kids = fase.getChildren();

                        for (DataSnapshot kid : kids) {
                            Object course_id = kid.child("COURSE_ID").getValue(Object.class);
                            Object course = kid.child("COURSE").getValue(Object.class);
                            Object credit = kid.child("CREDITS").getValue(Object.class);
                            Object creditPunten = kid.child("CREDITS").getValue(Object.class);
                            //VakkenEmA.add(new Vak(course_id.toString(), course.toString(), credit.toString()+" sp.      fase 2",creditPunten.toString()));


                        }

                    }else if(TextUtils.equals(fases,"fase 3")){

                        Iterable<DataSnapshot> kids = fase.getChildren();

                        for (DataSnapshot kid : kids) {
                            Object course_id = kid.child("COURSE_ID").getValue(Object.class);
                            Object course = kid.child("COURSE").getValue(Object.class);
                            Object credit = kid.child("CREDITS").getValue(Object.class);
                            Object creditPunten = kid.child("CREDITS").getValue(Object.class);
                            //VakkenEmA.add(new Vak(course_id.toString(), course.toString(), credit.toString()+" sp.      fase 3",creditPunten.toString()));

                        }

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        cAEMA= new courseAdapter(getContext(),VakkenEmA);
        recyclerViewEmA.setAdapter(cAEMA);
        cAEMA.notifyDataSetChanged();
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
       //listViewEma.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
}
