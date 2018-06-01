package app.stucre;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import app.stucre.R;
import es.dmoral.toasty.Toasty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by TI_Laptop-008 on 15/03/2018.
 */

public class courseAdapter extends RecyclerView.Adapter<courseAdapter.ViewHolder> {


    private Context context;
    private List <Vak> Vakken = new ArrayList<>();
    private OnItemClickListener mListener;
    private onItemLongClickListerner mLongListener;
    private int input = 0;
    private int click = 0;
    private HashMap<String, Integer> scoreVak = new HashMap<String, Integer>();


    public boolean isClickable = true;
    public boolean isEnable = true;
    private boolean geslaagd = false;


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
        holder.check.setImageResource(R.drawable.donetick);
        holder.vakImage.setImageResource(R.drawable.booksstackofthreeen);
        holder.optnemenVakken.setImageResource(R.drawable.vakkenbeschikbaar);
        holder.nietGeslaagdVal.setImageResource(R.drawable.vakkenopnieuwdoen);
        holder.again.setImageResource(R.drawable.geslaagdvak);
        holder.succeed.setImageResource(R.drawable.again_course);
        holder.tvNietgeslaagd.setText("ONBEKEND");

        if(vak.isChecked()) {
            holder.check.setVisibility(View.VISIBLE);
            holder.cardViewList.setCardBackgroundColor(Color.argb(200,0, 39, 75));
            holder.tvCourse.setTypeface(null,Typeface.BOLD_ITALIC);
            holder.tvCourse.setTextColor(Color.WHITE);
            holder.tvCredit.setTypeface(null,Typeface.BOLD_ITALIC);
            holder.tvCredit.setTextColor(Color.WHITE);
            holder.tvNietgeslaagd.setVisibility(View.INVISIBLE);
            holder.tvId.setTypeface(null,Typeface.BOLD_ITALIC);
            holder.again.setVisibility(View.INVISIBLE);
            holder.succeed.setVisibility(View.INVISIBLE);
            holder.tvId.setTextColor(Color.WHITE);
            holder.nietGeslaagdVal.setVisibility(View.INVISIBLE);
            holder.optnemenVakken.setVisibility(View.GONE);


        }else{
            holder.check.setVisibility(View.GONE);
            holder.cardViewList.setCardBackgroundColor(Color.WHITE);
            holder.vakImage.setVisibility(View.VISIBLE);
            holder.tvCourse.setTypeface(null,Typeface.NORMAL);
            holder.tvCourse.setTextColor(Color.rgb(0, 39, 75));
            holder.tvCredit.setTypeface(null,Typeface.NORMAL);
            holder.again.setVisibility(View.INVISIBLE);
            holder.succeed.setVisibility(View.INVISIBLE);
            holder.tvCredit.setTextColor(Color.argb(135,34,41,43));
            holder.tvId.setTypeface(null,Typeface.NORMAL);
            holder.nietGeslaagdVal.setVisibility(View.INVISIBLE);
            holder.tvNietgeslaagd.setVisibility(View.INVISIBLE);

            holder.tvId.setTextColor(Color.rgb(26, 75, 121));
            holder.optnemenVakken.setVisibility(View.VISIBLE);

            scoreVak.put(vak.getCourse(),vak.getScore());

            Integer value = scoreVak.get(vak.getCourse());
            if (vak.isGeslaagd())
            {
                holder.tvNietgeslaagd.setVisibility(View.VISIBLE);
                holder.again.setVisibility(View.INVISIBLE);
                holder.succeed.setVisibility(View.VISIBLE);
                holder.vakImage.setVisibility(View.INVISIBLE);
                holder.tvNietgeslaagd.setText("GESLAAGD");
                holder.tvNietgeslaagd.setTextColor(Color.rgb(34,139,34));
                holder.optnemenVakken.setVisibility(View.INVISIBLE);
                holder.tvCredit.setText(vak.getCreditPunten() + " credit");

            }
            else{

                if(value > 0)
                {
                    holder.tvNietgeslaagd.setVisibility(View.VISIBLE);
                    holder.again.setVisibility(View.VISIBLE);
                    holder.succeed.setVisibility(View.INVISIBLE);
                    holder.nietGeslaagdVal.setVisibility(View.VISIBLE);
                    holder.tvNietgeslaagd.setText("NIET GESLAAGD");
                    holder.vakImage.setVisibility(View.INVISIBLE);
                    holder.tvNietgeslaagd.setTextColor(Color.rgb(204,0,0));
                    holder.optnemenVakken.setVisibility(View.INVISIBLE);

                }
            }


        }





    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public interface onItemLongClickListerner{
        boolean onItemLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setOnItemLongClickListener(onItemLongClickListerner listener) {
        mLongListener = listener;
    }


    @Override
    public int getItemCount() {
        return Vakken.size();
    }

    public Vak getItemVak(int position){

        return Vakken.get(position);

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
        public TextView tvNietgeslaagd ;

        public ImageView vakImage;
        public ImageView nietGeslaagdVal;
        public ImageView check;
        public ImageView optnemenVakken;
        public ImageView again;
        public ImageView succeed;
        public CardView cardViewList;






        public ViewHolder(View itemView) {

            super(itemView);

            tvCourse = (TextView) itemView.findViewById(R.id.text1);
            tvCredit = (TextView) itemView.findViewById(R.id.text2);
            tvId = (TextView) itemView.findViewById(R.id.text3);
            tvNietgeslaagd = (TextView) itemView.findViewById(R.id.text4);
            check = (ImageView) itemView.findViewById(R.id.checkImage);
            vakImage = (ImageView) itemView.findViewById(R.id.Beschikbaarvakken);
            nietGeslaagdVal = (ImageView) itemView.findViewById(R.id.nietgeslaagevakken);
            again = (ImageView) itemView.findViewById(R.id.again);
            succeed = (ImageView) itemView.findViewById(R.id.succeed);
            optnemenVakken = (ImageView) itemView.findViewById(R.id.optenemenVakken);
            cardViewList = (CardView) itemView.findViewById(R.id.listCardview);
            itemView.setEnabled(true);
            itemView.setClickable(true);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            if(!isClickable || !isEnable)
                                return;


                            mListener.onItemClick(position);


                            notifyDataSetChanged();

                        }
                    }


                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(mLongListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            if(!isClickable || !isEnable)
                                return false;

                            mLongListener.onItemLongClick(position);
                            notifyDataSetChanged();

                        }
                    }
                    return true;
                }
            });




        }

        public void bind(Vak vak){
            tvCourse.setText(vak.getCourse());
            tvId.setText(vak.getId());
            tvCredit.setText(vak.getCredit());

            check.setImageResource(R.drawable.donetick);
            vakImage.setImageResource(R.drawable.booksstackofthreeeen);

        }


    }

    public boolean isClickable() {
        return isClickable;
    }

    public void setClickable(boolean clickable) {
        isClickable = clickable;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }




}
