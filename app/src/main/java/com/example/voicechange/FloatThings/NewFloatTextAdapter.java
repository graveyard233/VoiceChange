package com.example.voicechange.FloatThings;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
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

        //全局部分
        //字体粗细
        if (expand.getFont_weight().equals("1")){
            holder.floatTextPerson.getPaint().setFakeBoldText(true);
            holder.floatTextView.getPaint().setFakeBoldText(true);
        } else {
            holder.floatTextPerson.getPaint().setFakeBoldText(false);
            holder.floatTextView.getPaint().setFakeBoldText(false);
        }
        //设置最大行数
        if (expand.getRows() != null){
            holder.floatTextView.setMaxLines(Integer.parseInt(expand.getRows()) - 1);//- 2
        }
        if (expand != null && mgr!= null){//修改字体
            Log.d("TAG", "开始修改字体");
            if (expand.getFont().equals("kaiTi")){
                Typeface typeface1 = Typeface.createFromAsset(mgr,
                        "STKAITI.TTF");
                holder.floatTextView.setTypeface(typeface1);
            } else if (expand.getFont().equals("SimSun")){
                Typeface typeface1 = Typeface.createFromAsset(mgr,
                        "simsun.ttc");
                holder.floatTextView.setTypeface(typeface1);
            } else if (expand.getFont().equals("Source Han Sans CN")){
                Typeface typeface1 = Typeface.createFromAsset(mgr,
                        "simhei.ttf");
                holder.floatTextView.setTypeface(typeface1);
            }
            else
                System.out.println("没有");
        }

        //语音文字部分
        holder.floatTextView.setText(floatText.getText());
        //设置转写中的文字
        if (floatText.getOn_change_text() != null){
            SpannableString ss = new SpannableString(floatText.getOn_change_text());
            ss.setSpan(new ForegroundColorSpan(Color.parseColor(expand.getContentModule().getFocus_color())),
                    0,floatText.getOn_change_text().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new BackgroundColorSpan(Color.parseColor(expand.getContentModule().getFocus_bg_color())),
                    0,floatText.getOn_change_text().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.floatTextView.append(ss);
        }


        if (expand != null){
            //修改字体大小,设置为px
            holder.floatTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    Float.parseFloat(expand.getContentModule().getSize()));
            holder.floatTextView.setTextColor(Color.parseColor(expand.getContentModule().getColor()));
        }





        //设置说话人的部分
        holder.floatTextPerson.setText(floatText.getPerson());
        if (expand != null){
            //修改字体颜色
            holder.floatTextPerson.setTextColor(Color.parseColor(expand.getNameModule().getColor()));
            //修改字体大小,设置为px
            holder.floatTextPerson.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    Float.parseFloat(expand.getNameModule().getSize()));

        }
    }




    @Override
    public int getItemCount() {
        return myFloatTextList.size();
    }
}
