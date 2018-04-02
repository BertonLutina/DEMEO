package app.stucre;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.todddavies.components.progressbar.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TI_Laptop-008 on 15/03/2018.
 */

public class courseAdapter extends BaseAdapter implements ListAdapter,Filterable{ //implements Filterable {


    private Context mContext;
    private List<Vak> Vakken;
    private List<Vak> OrginaLVakken;
    private List<Vak> ToevoegenVakken;
    private ArrayList<Vak> items;
    CustomFilter cs;
    LayoutInflater inflater;
    ViewHolder holder;
    int count = 0;
    private boolean IsChecked = false;
    private ProgressWheel pwone;
    private ProgressCircle pwcircle;



    public courseAdapter(Context mContext, List<Vak> OrginaLVakken ) {
        this.mContext = mContext;
        this.OrginaLVakken = OrginaLVakken;
        this.Vakken = OrginaLVakken;

    }


    private String filter;
    @Override
    public int getCount() {
        return OrginaLVakken.size();
    }

    @Override
    public Object getItem(int position) {
        return OrginaLVakken.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public class ViewHolder{
        TextView tvCourse;
        TextView tvCredit;
        TextView tvId ;
        RadioGroup radioGroup;
        CheckBox checkBoxList;

        int checked;
    }


    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        View v = convertView;
        ViewHolder holder = new ViewHolder();


        //Check als het null is
        if(v == null) {
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item, null);
            View view2 = (View) v.getParent();
            holder.tvCourse = (TextView) v.findViewById(R.id.text1);
            holder.tvCredit = (TextView) v.findViewById(R.id.text2);
            holder.tvId = (TextView) v.findViewById(R.id.text3);
            holder.checkBoxList = (CheckBox) v.findViewById(R.id.checkbox1);
            holder.radioGroup = (RadioGroup)v.findViewById(R.id.radioGroup1);

            v.setTag(holder);
        }else

            holder = (ViewHolder) v.getTag();
            final Vak vakken = OrginaLVakken.get(position);

            holder.tvCourse.setText(vakken.getCourse());
            holder.tvCredit.setText(vakken.getCredit());
            holder.tvId.setText(vakken.getId());
            holder.checkBoxList.setTag(position);


        boolean isChecked = false;
        if(vakken.isChecked()){
                holder.checkBoxList.setChecked(true);
            }else{
                holder.checkBoxList.setChecked(false);
            }

            holder.checkBoxList.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    View vCheck = buttonView.getRootView();
                    TextView setPoint;
                    setPoint = (TextView)buttonView.getRootView().findViewById(R.id.countCreditOptions);
                    TextView tCourse = (TextView) vCheck.findViewById(R.id.text1);
                    TextView tCredits = (TextView) vCheck.findViewById(R.id.text2);
                    TextView tId = (TextView) vCheck.findViewById(R.id.text3);
                    LinearLayout Las = (LinearLayout) vCheck.findViewById(R.id.linearLayout1);
                    ListView listView = (ListView) vCheck.findViewById(R.id.dutiesfase1);
                    pwone = (ProgressWheel) vCheck.findViewById(R.id.progressBar2);
                    //pwcircle = (ProgressCircle) vCheck.findViewById(R.id.progressBar2);



                    ToevoegenVakken = new ArrayList<>();
                    //View itemVak = (View) buttonView.findViewById(R.id.countCreditOptions).getParent();
                    if(isChecked){
                        vakken.setChecked(true);
                        ToevoegenVakken.add(OrginaLVakken.get(position));
                        count += Integer.parseInt(vakken.getCreditPunten().toString());
                        String punten = Integer.toString(count);
                        setPoint.setText(punten+ "/60");
                        Toast.makeText(mContext, "Credit new: " + count , Toast.LENGTH_SHORT).show();
                        //pwone.incrementProgress(count);
                        if (pwone.getProgress() == 60){
                            pwone.setRimColor(Color.WHITE);
                            pwone.setBarColor(Color.WHITE);
                            pwone.setText("60");
                            pwone.setTextColor(Color.YELLOW);
                            Toast.makeText(mContext, "You have Reached the 60 credit " , Toast.LENGTH_SHORT).show();
                        }{
                        pwone.setProgress(count);}

                        //pwcircle.incrementProgress(count);

                        Las.setBackgroundColor(Color.rgb(34,41,43));
                        tCourse.setTextColor(Color.WHITE);
                        tCredits.setTextColor(Color.WHITE);
                        tId.setTextColor(Color.WHITE);




                    }else{
                        vakken.setChecked(false);
                        ToevoegenVakken.remove(OrginaLVakken.get(position));
                        count -= Integer.parseInt(vakken.getCreditPunten().toString());
                        String punten = Integer.toString(count);
                        setPoint.setText(punten + "/60");
                        //pwone.incrementProgress(count);

                        pwone.setProgress(count);
                        //pwcircle.incrementProgress(count);
                        Toast.makeText(mContext, "Credit before: " + count , Toast.LENGTH_SHORT).show();
                        Las.setBackgroundColor(Color.WHITE);
                        tCourse.setTextColor(Color.rgb(34,41,43));
                        tCredits.setTextColor(Color.argb(135,34,41,43));
                        tId.setTextColor(Color.rgb(210, 100, 40));

                    }
                }
            });









        //v.setTag(OrginaLVakken.get(position).getId());

        return v;
    }

    @Override
    public Filter getFilter() {

        if(cs == null){
            cs = new CustomFilter();
        }
        return cs;
    }

    class CustomFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toUpperCase();


                ArrayList<Vak> filerVaks = new ArrayList<>();

                for (int i = 0; i < Vakken.size(); i++) {
                    if (Vakken.get(i).getCourse().toUpperCase().contains(constraint)) {
                        Vak vak = new Vak(Vakken.get(i).getId(), Vakken.get(i).getCourse(), Vakken.get(i).getCredit(), Vakken.get(i).getCreditPunten());
                        filerVaks.add(vak);
                    }

                }

                results.count = filerVaks.size();
                results.values = filerVaks;

            }else{
                results.count = Vakken.size();
                results.values = Vakken;

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

                    OrginaLVakken = (List<Vak>)results.values;
                    notifyDataSetChanged();

        }
    }

}
