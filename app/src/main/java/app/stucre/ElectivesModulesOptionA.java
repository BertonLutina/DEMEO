package app.stucre;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
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


public class ElectivesModulesOptionA extends ListFragment {


    private List<Vak> Vakken = new ArrayList<>();
    private courseAdapter cA;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference EMdatabase = database.getReference("Bedrijfskunde/TI/ElectivesModules/Option A: Design and Build Software");

    public ElectivesModulesOptionA(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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
                            Vakken.add(new Vak(course_id.toString(), course.toString(), credit.toString()+" sp.      fase 2"));
                            cA.notifyDataSetChanged();

                        }

                    }else if(TextUtils.equals(fases,"fase 3")){

                        Iterable<DataSnapshot> kids = fase.getChildren();

                        for (DataSnapshot kid : kids) {
                            Object course_id = kid.child("COURSE_ID").getValue(Object.class);
                            Object course = kid.child("COURSE").getValue(Object.class);
                            Object credit = kid.child("CREDITS").getValue(Object.class);
                            Vakken.add(new Vak(course_id.toString(), course.toString(), credit.toString()+" sp.      fase 3"));
                            cA.notifyDataSetChanged();

                        }

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        cA = new courseAdapter(getContext(),Vakken);
        setListAdapter(cA);

        // Inflate the layout for this fragment
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
}
