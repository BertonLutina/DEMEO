package app.stucre;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import app.stucre.R;

import java.util.ArrayList;
import java.util.List;

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
        holder.bind(vak);
    }

    @Override
    public int getItemCount() {
        return Vakken.size();
    }

    public void setFilter(List<Vak> vakken) {
        Vakken = new ArrayList<>();
        Vakken.addAll(vakken);
        notifyDataSetChanged();
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

        public void bind(Vak vak){
            tvCourse.setText(vak.getCourse());
            tvId.setText(vak.getId());
            tvCredit.setText(vak.getCredit());
            check.setImageResource(R.drawable.bookssstack);
        }
    }







}
