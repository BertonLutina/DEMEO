package app.stucre;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.todddavies.components.progressbar.ProgressWheel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by TI_Laptop-008 on 15/03/2018.
 */

public class courseAdapter extends RecyclerView.Adapter<courseAdapter.ViewHolder> {


    private Context context;
    private List <Vak> Vakken;

    public courseAdapter(Context context, List<Vak> vakken) {
        this.context = context;
        this.Vakken = vakken;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Vak vak = Vakken.get(position);

        holder.tvCourse.setText(vak.getCourse());
        holder.tvId.setText(vak.getId());
        holder.tvCredit.setText(vak.getCredit());
        holder.check.setImageResource(R.drawable.vakkenbeschikbaar);

    }

    @Override
    public int getItemCount() {
        return Vakken.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvCourse;
        public TextView tvCredit;
        public TextView tvId ;
        public ImageView check;



        public ViewHolder(View itemView) {

            super(itemView);

            tvCourse = (TextView) itemView.findViewById(R.id.text1);
            tvCredit = (TextView) itemView.findViewById(R.id.text2);
            tvId = (TextView) itemView.findViewById(R.id.text3);
            check = (ImageView) itemView.findViewById(R.id.Beschikbaarvakken);

        }
    }



}
