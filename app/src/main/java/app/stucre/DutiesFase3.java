package app.stucre;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
public class DutiesFase3 extends Fragment {

    private RecyclerView recyclerViewDF3;
    private RecyclerView.Adapter cA3;

    private List<Vak> Vakken3 = new ArrayList<>();

    public DutiesFase3() {
    }



    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dutiesFase3 = database.getReference("Bedrijfskunde/TI/Duties/fase 3");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vFase3 = inflater.inflate(R.layout.fragment_duties_fase3, container, false);

        recyclerViewDF3 = (RecyclerView) vFase3.findViewById(R.id.dutiesfase3);
        recyclerViewDF3.setHasFixedSize(true);
        recyclerViewDF3.setLayoutManager(new LinearLayoutManager(getContext()));

        Vakken3 = new ArrayList<>();

        dutiesFase3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();


                    for (DataSnapshot child:children) {
                        Object course_id = child.child("COURSE_ID").getValue(Object.class);
                        Object course = child.child("COURSE").getValue(Object.class);
                        Object credit = child.child("CREDITS").getValue(Object.class);
                        Object creditPunten = child.child("CREDITS").getValue(Object.class);
                        Vakken3.add(new Vak(course_id.toString(),course.toString(),credit.toString()+" sp.",creditPunten.toString()));


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        cA3 = new courseAdapter(getContext(),Vakken3);
        recyclerViewDF3.setAdapter(cA3);
        cA3.notifyDataSetChanged();


        return vFase3;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }



    @Override
    public void onStart() {

        super.onStart();
        //Ldutiesfase3.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
}
