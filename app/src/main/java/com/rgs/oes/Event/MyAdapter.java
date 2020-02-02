package com.rgs.oes.Event;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.rgs.oes.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
private List<Model>listData;

public MyAdapter(List<Model> listData) {
        this.listData = listData;
        }

@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.lidt_data,parent,false);
        return new ViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model ld=listData.get(position);
        holder.txtid.setText(ld.getQustion());
        holder.txtname.setText(ld.getAns());
        }

@Override
public int getItemCount() {
        return listData.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder{
    private TextView txtid,txtname,txtmovie;
    CardView cardView;
    public ViewHolder(View itemView) {
        super(itemView);
        txtid=(TextView)itemView.findViewById(R.id.one);
        txtname=(TextView)itemView.findViewById(R.id.two);
        cardView = itemView.findViewById(R.id.card_view);

    }
}
}