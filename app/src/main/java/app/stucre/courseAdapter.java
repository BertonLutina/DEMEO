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

import app.stucre.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TI_Laptop-008 on 15/03/2018.
 */

public class courseAdapter extends RecyclerView.Adapter<courseAdapter.ViewHolder> {


    private Context context;
    private List <Vak> Vakken;
    private OnItemClickListener mListener;
    private onItemLongClickListerner mLongListener;
    private int focusedItem = -1;

    public boolean isClickable = true;


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
        holder.vakImage.setImageResource(R.drawable.booksstackofthreeeen);
        holder.optnemenVakken.setImageResource(R.drawable.vakkenbeschikbaar);

        if(vak.isChecked()) {
            holder.check.setVisibility(View.VISIBLE);
            holder.cardViewList.setCardBackgroundColor(Color.argb(200,40, 50, 50));
            holder.tvCourse.setTypeface(null,Typeface.BOLD_ITALIC);
            holder.tvCourse.setTextColor(Color.WHITE);
            holder.tvCredit.setTypeface(null,Typeface.BOLD_ITALIC);
            holder.tvCredit.setTextColor(Color.WHITE);
            holder.tvId.setTypeface(null,Typeface.BOLD_ITALIC);
            holder.tvId.setTypeface(null,Typeface.BOLD_ITALIC);
            holder.tvId.setTextColor(Color.WHITE);
            holder.optnemenVakken.setVisibility(View.GONE);
        }else{
            holder.check.setVisibility(View.GONE);
            holder.cardViewList.setCardBackgroundColor(Color.WHITE);
            holder.tvCourse.setTypeface(null,Typeface.NORMAL);
            holder.tvCourse.setTextColor(Color.rgb(34,41,43));
            holder.tvCredit.setTypeface(null,Typeface.NORMAL);
            holder.tvCredit.setTextColor(Color.argb(135,34,41,43));
            holder.tvId.setTypeface(null,Typeface.NORMAL);
            holder.tvId.setTextColor(Color.rgb(210, 100, 40));
            holder.optnemenVakken.setVisibility(View.VISIBLE);
        }

        if(!isClickable){
            holder.cardViewList.setCardBackgroundColor(Color.argb(13,40, 50, 50));
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
        public ImageView vakImage;
        public ImageView check;
        public ImageView optnemenVakken;
        public CardView cardViewList;





        public ViewHolder(final View itemView) {

            super(itemView);

            tvCourse = (TextView) itemView.findViewById(R.id.text1);
            tvCredit = (TextView) itemView.findViewById(R.id.text2);
            tvId = (TextView) itemView.findViewById(R.id.text3);
            check = (ImageView) itemView.findViewById(R.id.checkImage);
            vakImage = (ImageView) itemView.findViewById(R.id.Beschikbaarvakken);
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
                            if(!isClickable)
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
                            if(!isClickable)
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







}
