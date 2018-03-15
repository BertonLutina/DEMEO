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
    }


    private Context mContext;
    private List<Vak> Vakken;
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
        View v = View.inflate(mContext, R.layout.list_item,null);
        TextView tvCourse = (TextView)v.findViewById(R.id.text1);
        TextView tvCredit = (TextView)v.findViewById(R.id.text2);

        //Set text for Textview
        tvCourse.setText(Vakken.get(position).getCourse());
        tvCredit.setText(Vakken.get(position).getCredit());

        v.setTag(Vakken.get(position).getId());




        return v;
    }
}
