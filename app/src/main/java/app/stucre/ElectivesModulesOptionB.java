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


public class ElectivesModulesOptionB extends Fragment{

    private RecyclerView recyclerViewEmB;
    private RecyclerView.Adapter cAEMB;

    private List<Vak> VakkenEmB;

    public ElectivesModulesOptionB(){

    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference EMdatabases = database.getReference("Bedrijfskunde/TI/ElectivesModules/Option B : Manage Internet and Cloud");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_electives_modules_option_b, container, false);

        recyclerViewEmB = (RecyclerView) view.findViewById(R.id.emoptionB);
        recyclerViewEmB.setHasFixedSize(true);
        recyclerViewEmB.setLayoutManager(new LinearLayoutManager(getContext()));

        VakkenEmB = new ArrayList<>();

        EMdatabases.addValueEventListener(new ValueEventListener() {
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
                            VakkenEmB.add(new Vak(course_id.toString(), course.toString(), credit.toString()+" sp.      fase 2",creditPunten.toString()));


                        }

                    }else if(TextUtils.equals(fases,"fase 3")){

                        Iterable<DataSnapshot> kids = fase.getChildren();

                        for (DataSnapshot kid : kids) {
                            Object course_id = kid.child("COURSE_ID").getValue(Object.class);
                            Object course = kid.child("COURSE").getValue(Object.class);
                            Object credit = kid.child("CREDITS").getValue(Object.class);
                            Object creditPunten = kid.child("CREDITS").getValue(Object.class);
                            VakkenEmB.add(new Vak(course_id.toString(), course.toString(), credit.toString()+" sp.      fase 3",creditPunten.toString()));


                        }

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        cAEMB = new courseAdapter(getContext(),VakkenEmB);
        recyclerViewEmB.setAdapter(cAEMB);
        cAEMB.notifyDataSetChanged();

        //listViewEmb.setAdapter(cA);


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //listViewEmb.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
}
