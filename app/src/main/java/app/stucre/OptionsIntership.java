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


public class OptionsIntership extends Fragment {

    private RecyclerView recyclerViewOF3;
    private RecyclerView.Adapter cAOF3;

    private List<Vak> VakkenOF3;

    public OptionsIntership(){

    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference optionsFase3 = database.getReference("Bedrijfskunde/TI/Options/fase 3");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_options_intership, container, false);

        recyclerViewOF3 = (RecyclerView) getActivity().findViewById(R.id.list_options);
        recyclerViewOF3.setHasFixedSize(true);
        recyclerViewOF3.setLayoutManager(new LinearLayoutManager(getContext()));

        VakkenOF3 = new ArrayList<>();
        optionsFase3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child:children) {

                    String vakken_hm = child.getKey();

                    if(TextUtils.equals(vakken_hm ,"Option 2 : Intership mobility out of Europe") || TextUtils.equals(vakken_hm ,"Option 5 : Intership mobility in Europe : SHORT" )){
                        if(TextUtils.equals(vakken_hm ,"Option 2 : Intership mobility out of Europe"))
                        {
                            Iterable<DataSnapshot> kids = child.getChildren();

                            for (DataSnapshot kid : kids) {
                                Object course_id = kid.child("COURSE_ID").getValue(Object.class);
                                Object course = kid.child("COURSE").getValue(Object.class);
                                Object credit = kid.child("CREDITS").getValue(Object.class);
                                Object creditPunten = kid.child("CREDITS").getValue(Object.class);
                                    VakkenOF3.add(new Vak(course_id.toString(), course.toString(), credit.toString()+" sp.      Combination: out of Europe",creditPunten.toString()));
                            }}
                        else if (TextUtils.equals(vakken_hm ,"Option 5 : Intership mobility in Europe : SHORT"))
                        {
                            Iterable<DataSnapshot> kids = child.getChildren();

                            for (DataSnapshot kid : kids) {
                                Object course_id = kid.child("COURSE_ID").getValue(Object.class);
                                Object course = kid.child("COURSE").getValue(Object.class);
                                Object credit = kid.child("CREDITS").getValue(Object.class);
                                Object creditPunten = kid.child("CREDITS").getValue(Object.class);
                                VakkenOF3.add(new Vak(course_id.toString(), course.toString(), credit.toString()+" sp.      Combination: in or out of Europe",creditPunten.toString()));
                            }
                        }
                    }
                    else{
                        Object course_id = child.child("COURSE_ID").getValue(Object.class);
                        Object course = child.child("COURSE").getValue(Object.class);
                        Object credit = child.child("CREDITS").getValue(Object.class);
                        Object creditPunten = child.child("CREDITS").getValue(Object.class);
                        VakkenOF3.add(new Vak(course_id.toString(),course.toString(),credit.toString()+" sp.",creditPunten.toString()));
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        cAOF3= new courseAdapter(getContext(),VakkenOF3);
        recyclerViewOF3.setAdapter(cAOF3);
        cAOF3.notifyDataSetChanged();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {

        super.onStart();
        //listViewO.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
}
