package app.stucre;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
        public CardView cardViewList;



        public ViewHolder(View itemView) {

            super(itemView);

            tvCourse = (TextView) itemView.findViewById(R.id.text1);
            tvCredit = (TextView) itemView.findViewById(R.id.text2);
            tvId = (TextView) itemView.findViewById(R.id.text3);
            check = (ImageView) itemView.findViewById(R.id.checkImage);
            vakImage = (ImageView) itemView.findViewById(R.id.Beschikbaarvakken);
            cardViewList = (CardView) itemView.findViewById(R.id.listCardview);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                            check.setVisibility(View.VISIBLE);
                            tvCourse.setTypeface(null, Typeface.BOLD_ITALIC);
                            tvCourse.setTextSize(18);
                            cardViewList.setCardBackgroundColor(Color.rgb(211,211,211));

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
                            mLongListener.onItemLongClick(position);
                            check.setVisibility(View.INVISIBLE);
                            tvCourse.setTypeface(null, Typeface.NORMAL);
                            tvCourse.setTextSize(16);
                            cardViewList.setCardBackgroundColor(Color.WHITE);
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
            check.setImageResource(R.drawable.check_only);
            vakImage.setImageResource(R.drawable.bookssstack);

        }
    }







}
