package com.example.medidex;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MediAdapter extends RecyclerView.Adapter<MediAdapter.ViewHolder> {
    ArrayList<Medicine> items = new ArrayList<Medicine>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.medicine_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Medicine item = items.get(position);
        holder.setItem(item);
    }

    public void clearItems() {
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Medicine item) {
        items.add(item);
    }

    public void setItems(ArrayList<Medicine> items) {
        this.items = items;
    }

    public Medicine getItem(int position) {
        return items.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView, textView2, textView3;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            imageView = itemView.findViewById(R.id.imageView);
            }

            public void setItem(Medicine item) {
            textView.setText(item.ITEM_SEQ);
            textView2.setText(item.ENTP_NAME);
            textView3.setText(item.ITEM_NAME);
            Glide.with(imageView).load(item.ITEM_IMAGE).into(imageView);
        }
    }
}
