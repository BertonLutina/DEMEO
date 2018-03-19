package app.stucre;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by TI_Laptop-008 on 15/03/2018.
 */

public class courseAdapter extends BaseAdapter {


    public courseAdapter(Context mContext, List<Vak> Vakken) {
        this.mContext = mContext;
        this.Vakken = Vakken;
        //this.filter = "";
    }


    private Context mContext;
    private List<Vak> Vakken;

   /* public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }*/

    private String filter;
    @Override
    public int getCount() {
        return Vakken.size();
    }

    @Override
    public Object getItem(int position) {
        return Vakken.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
        convertView = View.inflate(mContext, R.layout.list_item,null);}

        TextView tvCourse = (TextView)convertView.findViewById(R.id.text1);
        TextView tvCredit = (TextView)convertView.findViewById(R.id.text2);
        TextView tvId = (TextView) convertView.findViewById(R.id.text3);

        //Set text for Textview
        tvCourse.setText(Vakken.get(position).getCourse());
        tvCredit.setText(Vakken.get(position).getCredit());
        tvId.setText(Vakken.get(position).getId());

        convertView.setTag(Vakken.get(position).getId());



            //if(filter != "")

        return convertView;
    }
}
