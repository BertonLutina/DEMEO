package app.stucre;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
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

import es.dmoral.toasty.Toasty;


public class OptionsIntership extends Fragment implements SearchView.OnQueryTextListener{

    private RecyclerView recyclerViewOF3;
    private courseAdapter cAOF3;

    private ArrayList<Vak> VakkenOF3;

    private ProgressWheel progressWheelOptionFase3;
    private View LayoutCredits;
    private int count = 0;
    private Button btnSend;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference optionsFase3 = database.getReference("Bedrijfskunde/TI/Options/fase 3");
    private Switch selectall;

    public OptionsIntership(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_options_intership, container, false);


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewOF3 = (RecyclerView) view.findViewById(R.id.optionsFase3);
        recyclerViewOF3.setHasFixedSize(true);
        recyclerViewOF3.setLayoutManager(new LinearLayoutManager(getContext()));

        // Object aanmaken
        Intent intent = getActivity().getIntent();
        VakkenOF3 = (ArrayList<Vak>) intent.getSerializableExtra("Intership");

        // TestVakken
        //Vakken();

        //Vakken van de DATABASE
        //VakkenDatabase();

        cAOF3= new courseAdapter(getContext(),VakkenOF3);
        recyclerViewOF3.setAdapter(cAOF3);
        //cAOF3.notifyDataSetChanged();

        LayoutCredits = getActivity().findViewById(R.id.slideUpCreditsView);
        LinearLayout lay = LayoutCredits.findViewById(R.id.Progress_bar_points);
        btnSend = (Button) lay.findViewById(R.id.versturen_credits);
        progressWheelOptionFase3 = (ProgressWheel) lay.findViewById(R.id.count_progressBar);
        setHasOptionsMenu(true);
        clickOnVakken();
        selectall = (Switch) getActivity().findViewById(R.id.selectAllSwitch);

        selectall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    for (Vak vak : VakkenOF3){
                        vak.setChecked(true);
                        boolean checked = vak.isChecked();
                        String point = vak.getCreditPunten();
                        count = Integer.parseInt(point);
                        count++;
                        int percent = (360/60) * (count+1);
                        progressWheelOptionFase3.setProgress(percent);
                        progressWheelOptionFase3.setText(Integer.toString(count)+" sp");
                    }
                    Toasty.custom(getContext(), "+ "+ count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.DKGRAY,Toast.LENGTH_SHORT,true,true).show();
                }else{
                    for (Vak vak : VakkenOF3) {
                        vak.setChecked(false);
                        boolean checked = vak.isChecked();
                        String point = vak.getCreditPunten();
                        count = Integer.parseInt(point);
                        count--;
                        int percent = (360 / 60) * (count + 1);
                        progressWheelOptionFase3.setProgress(percent);
                        progressWheelOptionFase3.setText(Integer.toString(count) + " sp");
                    }
                    cAOF3.notifyDataSetChanged();
                }

            }
        });

    }

    @Override
    public void onStart() {

        super.onStart();
        //listViewO.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.options_menu_bar,menu);
        MenuItem item = menu.findItem(R.id.menu_search_Options);

        SearchView dutiesSearch = (SearchView) item.getActionView();
        dutiesSearch.setOnQueryTextListener(this);

        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(getContext(),"search and filter course in fase 3",Toast.LENGTH_SHORT).show();


                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(getContext(),"close search",Toast.LENGTH_SHORT).show();
                cAOF3.setFilter(VakkenOF3);
                cAOF3.notifyDataSetChanged();
                return true;
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Vak> filteredVakken = filter(VakkenOF3,newText);
        cAOF3.setFilter(filteredVakken);
        cAOF3.notifyDataSetChanged();
        return true;
    }


    // Here we will take the list and filter
    private List<Vak> filter(List<Vak> vakken1, String query) {
        //change the string in lowercase
        query = query.toLowerCase();

        // create a new ArrayList called filteredVakken give final instate of public
        final List<Vak> filteredVakken = new ArrayList<>();

        // Search in your collection of your current Object
        for (Vak vakken : vakken1){
            // create a string "Text" and put all the course  of the collection of the object
            final String text = vakken.getCourse().toLowerCase();
            //search if form the first it match withe the courses
            if(text.contains(query)){
                // put it in the nieuw Arraylist youve made filteredVakken Arraylist
                filteredVakken.add(vakken);
            }
        }

        // return the filtered Arraylist
        return filteredVakken;

    }

    private void VakkenDatabase(){  optionsFase3.addValueEventListener(new ValueEventListener() {
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
                            //VakkenOF3.add(new Vak(course_id.toString(), course.toString(), credit.toString()+" sp.      Combination: out of Europe",creditPunten.toString()));
                            cAOF3.notifyDataSetChanged();
                        }}
                    else if (TextUtils.equals(vakken_hm ,"Option 5 : Intership mobility in Europe : SHORT"))
                    {
                        Iterable<DataSnapshot> kids = child.getChildren();

                        for (DataSnapshot kid : kids) {
                            Object course_id = kid.child("COURSE_ID").getValue(Object.class);
                            Object course = kid.child("COURSE").getValue(Object.class);
                            Object credit = kid.child("CREDITS").getValue(Object.class);
                            Object creditPunten = kid.child("CREDITS").getValue(Object.class);
                            //VakkenOF3.add(new Vak(course_id.toString(), course.toString(), credit.toString()+" sp.      Combination: in or out of Europe",creditPunten.toString()));
                            cAOF3.notifyDataSetChanged();
                        }
                    }
                }
                else{
                    Object course_id = child.child("COURSE_ID").getValue(Object.class);
                    Object course = child.child("COURSE").getValue(Object.class);
                    Object credit = child.child("CREDITS").getValue(Object.class);
                    Object creditPunten = child.child("CREDITS").getValue(Object.class);
                    //VakkenOF3.add(new Vak(course_id.toString(),course.toString(),credit.toString()+" sp.",creditPunten.toString()));
                    cAOF3.notifyDataSetChanged();
                }

            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });}
    private void clickOnVakken(){
        cAOF3.setOnItemClickListener(new courseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {


                //String plaats = Vakken1.get(position).getCourse();
                String point = VakkenOF3.get(position).getCreditPunten();
                boolean checked = VakkenOF3.get(position).isChecked();

                if(!checked){
                    VakkenOF3.get(position).setChecked(true);
                    if(!(count> 60)){
                        count += Integer.parseInt(point);
                        int percent = (360/60) * count;
                        progressWheelOptionFase3.setProgress(percent);
                        progressWheelOptionFase3.setText(Integer.toString(count)+" sp");
                        Toasty.custom(getContext(), "+ "+count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.DKGRAY,Toast.LENGTH_SHORT,true,true).show();
                    }else{
                        Toasty.error(getContext()," 60 sp is de limiet => Fase 3", Toast.LENGTH_SHORT).show();
                    }
                    if (count == 60) {
                        progressWheelOptionFase3.setBarColor(Color.	rgb(0,128,0));
                        //btnSend.setEnabled(true);
                        //btnSend.setBackgroundColor(Color.rgb(0,128,0));
                    }else if(count >= 45 && count < 60){
                        progressWheelOptionFase3.setBarColor(Color.rgb(255,69,0));
                    }else if (count >= 30 && count < 45) {
                        progressWheelOptionFase3.setBarColor(Color.rgb(255,140,0));
                    }else if (count >= 15 && count < 30) {
                        progressWheelOptionFase3.setBarColor(Color.rgb(255,165,0));
                    }else if (count >= 0 && count < 15) {
                        progressWheelOptionFase3.setBarColor(Color.	rgb(255,215,0));
                    }
                }else{
                    VakkenOF3.get(position).setChecked(false);
                    if(!(count < 0)) {
                        count -= Integer.parseInt(point);
                        int percent = (360/60) * count;
                        progressWheelOptionFase3.setProgress(percent);
                        progressWheelOptionFase3.setText(Integer.toString(count)+" sp");
                        Toasty.custom(getContext(), "- "+count+" sp.", getResources().getDrawable(R.drawable.booksstacktwee), Color.rgb(204,204,0),Toast.LENGTH_SHORT,true,true).show();
                    }else{
                        Toasty.error(getContext()," MAG niet Onder de 0", Toast.LENGTH_SHORT).show();
                    }

                    if (count == 60) {
                        progressWheelOptionFase3.setBarColor(Color.	rgb(0,128,0));
                    }else if(count >= 45 && count < 60){
                        progressWheelOptionFase3.setBarColor(Color.rgb(255,69,0));
                        //btnSend.setEnabled(false);
                    }else if (count >= 30 && count < 45) {
                        progressWheelOptionFase3.setBarColor(Color.rgb(255,140,0));
                    }else if (count >= 15 && count < 30) {
                        progressWheelOptionFase3.setBarColor(Color.rgb(255,165,0));
                    }else if (count >= 0 && count < 15) {
                        progressWheelOptionFase3.setBarColor(Color.	rgb(255,215,0));
                    }
                }




            }
        });

        cAOF3.setOnItemLongClickListener(new courseAdapter.onItemLongClickListerner() {
            @Override
            public boolean onItemLongClick(int position) {

                String course = VakkenOF3.get(position).getCourse();
                Integer Score = VakkenOF3.get(position).setScore(0);
                Integer getScore = VakkenOF3.get(position).getScore();

                AlertDialog.Builder dialogvak = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getActivity().getLayoutInflater();

                View dialogView = inflater.inflate(R.layout.dialogscore,null);
                TextView vak = (TextView) dialogView.findViewById(R.id.vakDialoog);
                TextView score = (TextView) dialogView.findViewById(R.id.score);
                TextView geslaagd = (TextView) dialogView.findViewById(R.id.txtgeslaagd);

                vak.setText(course);
                score.setText(getScore.toString());
                geslaagd.setText("In progress...");
                geslaagd.setBackgroundColor(Color.rgb(0,128,0));
                geslaagd.setTextColor(Color.rgb(246,246,246));


                dialogvak.setView(dialogView).setPositiveButton("Back",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog alertVak =  dialogvak.create();

                alertVak.show();


                return true;
            }
        });
    }


}
