package hw1.demirtas.tarik.com.instacomm;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    Context context;

    public MyRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public MyRecyclerViewAdapter(Context context, List<hw1.demirtas.tarik.com.instacomm.Comment> data) {
        this.context = context;
        Commons.comments = (ArrayList<hw1.demirtas.tarik.com.instacomm.Comment>) data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_comments, parent, false);
        return new MyViewHolder(v);
    }

    public void modifyData() {
        notifyDataSetChanged();
        notifyItemRangeChanged(0, Commons.comments.size());
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.txt.setText(Commons.comments.get(position).text);
        if(Commons.comments.get(position).getPremium() == 1)
            holder.premium.setText("Premium");
        else
            holder.premium.setText("Free");
        if(position%2 == 1){
            holder.cs.setBackgroundColor(Color.rgb(255,165,0));
        }
    }

    @Override
    public int getItemCount() {
        return Commons.comments.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt;
        TextView premium;
        ConstraintLayout cs;

        MyViewHolder(View viewItem) {
            super(viewItem);
            txt = viewItem.findViewById(R.id.txt);
            premium = viewItem.findViewById(R.id.txtPremium);
            cs = viewItem.findViewById(R.id.cs);

            viewItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Commons.currentComment = txt.getText().toString();
                    Toast.makeText(context,"You have selected "+Commons.getCurrentComment(),Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
    }
}