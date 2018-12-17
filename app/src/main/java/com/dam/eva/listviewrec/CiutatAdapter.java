package com.dam.eva.listviewrec;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CiutatAdapter extends RecyclerView.Adapter<CiutatAdapter.BlocViewHolder> {
    private List<Bloc> values;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class BlocViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView data;
        public TextView temperatura;
        public final ImageView imatge;
        public View layout;

        public BlocViewHolder(View v) {
            super(v);
            layout = v;
            data = v.findViewById(R.id.firstLine);
            temperatura = v.findViewById(R.id.secondLine);
            imatge = v.findViewById(R.id.icon);
        }
    }

    public void add(int position, Bloc item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CiutatAdapter(List<Bloc> myDataset) {
        values = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CiutatAdapter.BlocViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        BlocViewHolder vh = new BlocViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(BlocViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Bloc name = values.get(position);
        holder.data.setText(name.getData());
        holder.data.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });

        holder.temperatura.setText("Temp: " + name.getTermperatura()+" ºC");

        if(name.getFredCalor().equals("cold")){
            holder.imatge.setImageResource(R.drawable.ic_action_cold);
        }else{
            holder.imatge.setImageResource(R.drawable.ic_action_hot);

        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}

