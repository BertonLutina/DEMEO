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


public class OptionsIntership extends Fragment {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference optionsFase3 = database.getReference("Bedrijfskunde/TI/Options/fase 3");
    private List<Vak> Vakken = new ArrayList<>();
    private courseAdapter cA;

    public OptionsIntership(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_options, container, false);
        ListView listViewO = (ListView) view.findViewById(R.id.list_options);
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
                                    Vakken.add(new Vak(course_id.toString(), course.toString(), credit.toString()+" sp.      Combination: out of Europe"));
                                cA.notifyDataSetChanged();


                            }}
                        else if (TextUtils.equals(vakken_hm ,"Option 5 : Intership mobility in Europe : SHORT"))
                        {
                            Iterable<DataSnapshot> kids = child.getChildren();

                            for (DataSnapshot kid : kids) {
                                Object course_id = kid.child("COURSE_ID").getValue(Object.class);
                                Object course = kid.child("COURSE").getValue(Object.class);
                                Object credit = kid.child("CREDITS").getValue(Object.class);
                                Vakken.add(new Vak(course_id.toString(), course.toString(), credit.toString()+" sp.      Combination: in or out of Europe"));
                                cA.notifyDataSetChanged();


                            }
                        }
                    }
                    else{
                        Object course_id = child.child("COURSE_ID").getValue(Object.class);
                        Object course = child.child("COURSE").getValue(Object.class);
                        Object credit = child.child("CREDITS").getValue(Object.class);
                        Vakken.add(new Vak(course_id.toString(),course.toString(),credit.toString()+" sp."));
                        cA.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        cA  = new courseAdapter(getContext(),Vakken);
        listViewO.setAdapter(cA);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        ListView listViewO = (ListView) getActivity().findViewById(R.id.list_options);
        super.onStart();
        listViewO.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
}
