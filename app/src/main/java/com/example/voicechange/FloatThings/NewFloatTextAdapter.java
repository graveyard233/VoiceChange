package com.example.voicechange.FloatThings;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voicechange.Info.Expand_updateAsrResultLayoutConfig;
import com.example.voicechange.R;

import java.util.List;

public class NewFloatTextAdapter extends RecyclerView.Adapter<NewFloatTextAdapter.ViewHolder> {

    private List<FloatText> myFloatTextList;

    private String color ;

    private String isBold;

    private AssetManager mgr;

    private Expand_updateAsrResultLayoutConfig expand;

    public void setExpand(Expand_updateAsrResultLayoutConfig expand) {
        this.expand = expand;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setFontStyle(String isBold) {
        this.isBold = isBold;
    }

    public void setMgr(AssetManager mgr) {
        this.mgr = mgr;
    }

    public NewFloatTextAdapter(List<FloatText> floatTextList){
        myFloatTextList = floatTextList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView floatTextView;
        TextView floatTextPerson;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            floatTextView = itemView.findViewById(R.id.textView_text);
            floatTextPerson = itemView.findViewById(R.id.textView_person);
        }
    }

    @NonNull
    @Override
    public NewFloatTextAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.float_text_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewFloatTextAdapter.ViewHolder holder, int position) {
        FloatText floatText = myFloatTextList.get(position);

        //语音文字部分
        holder.floatTextView.setText(floatText.getText());
        holder.floatTextView.getPaint().setFakeBoldText(true);//字体加粗
        if (mgr != null){//修改字体
            Typeface typeface = Typeface.createFromAsset(mgr,
                    "ChuangKeTieJinGangTi-2.otf");
            holder.floatTextView.setTypeface(typeface);
        }





        //设置说话人的部分
        holder.floatTextPerson.setText(floatText.getPerson());
        if (expand != null){
            //修改字体颜色
            holder.floatTextPerson.setTextColor(Color.parseColor(expand.getNameModule().getColor()));
            //修改字体大小,设置为px
            holder.floatTextPerson.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    Float.parseFloat(expand.getNameModule().getSize()));
            if (expand.getFont_weight().equals("1")){
                holder.floatTextPerson.getPaint().setFakeBoldText(true);
            }
        }
    }




    @Override
    public int getItemCount() {
        return myFloatTextList.size();
    }
}
