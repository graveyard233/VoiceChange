package com.example.voicechange.FloatThings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voicechange.R;

import java.util.List;

public class FloatTextAdapter extends RecyclerView.Adapter<FloatTextAdapter.ViewHolder> {

    private List<FloatText> myFloatTextList;

    public FloatTextAdapter(List<FloatText> floatTextList){
        myFloatTextList = floatTextList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView floatTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            floatTextView = itemView.findViewById(R.id.textView_text);
        }
    }

    @NonNull
    @Override
    public FloatTextAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.float_text_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FloatTextAdapter.ViewHolder holder, int position) {
        FloatText floatText = myFloatTextList.get(position);
        holder.floatTextView.setText(floatText.getText());
    }

    @Override
    public int getItemCount() {
        return myFloatTextList.size();
    }
}
