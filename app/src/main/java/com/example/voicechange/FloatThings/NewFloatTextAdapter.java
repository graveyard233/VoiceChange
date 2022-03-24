package com.example.voicechange.FloatThings;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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
        if (expand != null){
            if (expand.getFont_weight().equals("1")){
                holder.floatTextPerson.getPaint().setFakeBoldText(true);
                holder.floatTextView.getPaint().setFakeBoldText(true);
            } else {
                holder.floatTextPerson.getPaint().setFakeBoldText(false);
                holder.floatTextView.getPaint().setFakeBoldText(false);
            }
        }

        //设置最大行数
        if (expand != null){
            holder.floatTextView.setMaxLines(Integer.parseInt(expand.getRows()) - 1);//- 2
        }
        //修改字体
        if (expand != null && mgr!= null){
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
        if (expand!=null && floatText.getOn_change_text() != null && floatText.getText() != null){
//            SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
//            StringBuffer stringBuffer = new StringBuffer();
//            stringBuffer.append(floatText.getText());
//            stringBuilder.append(stringBuffer);
//            stringBuilder.append(floatText.getOn_change_text());
//            //设置已经转好的文字
//            ForegroundColorSpan colorSpan1 = new
//                    ForegroundColorSpan(Color.parseColor(expand.getContentModule().getColor()));
//            stringBuilder.setSpan(colorSpan1,0,floatText.getText().length(),
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//            //设置转写中的文字
//            ForegroundColorSpan colorSpan2 = new
//                    ForegroundColorSpan(Color.parseColor(expand.getContentModule().getFocus_color()));
//            stringBuilder.setSpan(colorSpan2,
//                    floatText.getText().length(),
//                    floatText.getOn_change_text().length() + floatText.getText().length(),
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            stringBuilder.setSpan(new BackgroundColorSpan(Color.parseColor(expand.getContentModule().getFocus_bg_color())),
//                    floatText.getText().length(),
//                    floatText.getOn_change_text().length() + floatText.getText().length(),
//                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            holder.floatTextView.setText(stringBuilder);
            final SpannableString s1 = new SpannableString(floatText.getText());
            final SpannableString s2 = new SpannableString(floatText.getOn_change_text());

            //将第一段变成正在转写的样式
//            final ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(
//                    Color.parseColor(expand.getContentModule().getFocus_color()));
//            final BackgroundColorSpan backgroundColorSpan1 = new BackgroundColorSpan(
//                    Color.parseColor(expand.getContentModule().getFocus_bg_color()));
//
//            s1.setSpan(colorSpan1,0,s1.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            s1.setSpan(backgroundColorSpan1,0,s1.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            holder.floatTextView.append(s1);
//
//            Editable editable = (Editable) holder.floatTextView.getEditableText();

            //复制出来的
            ForegroundColorSpan colorSpan0 = new ForegroundColorSpan(
                            Color.parseColor(expand.getContentModule().getColor()));
            s1.setSpan(colorSpan0,0,s1.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            s1.removeSpan(backgroundColorSpan1);

            holder.floatTextView.append(s1);

            ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(
                            Color.parseColor(expand.getContentModule().getFocus_color()));
            BackgroundColorSpan backgroundColorSpan2 = new BackgroundColorSpan(
                            Color.parseColor(expand.getContentModule().getFocus_bg_color()));
            s2.setSpan(colorSpan2,0,s2.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            s2.setSpan(backgroundColorSpan2,0,s2.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.floatTextView.append(s2);

//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    ForegroundColorSpan colorSpan0 = new ForegroundColorSpan(
//                            Color.parseColor(expand.getContentModule().getColor()));
//                    s1.setSpan(colorSpan0,0,s1.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    s1.removeSpan(backgroundColorSpan1);
//
//                    editable.replace(0,s1.length(),s1);
//
//                    ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(
//                            Color.parseColor(expand.getContentModule().getFocus_color()));
//                    BackgroundColorSpan backgroundColorSpan2 = new BackgroundColorSpan(
//                            Color.parseColor(expand.getContentModule().getFocus_bg_color()));
//                    s2.setSpan(colorSpan2,0,s2.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    s2.setSpan(backgroundColorSpan2,0,s2.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    holder.floatTextView.append(s2);
//                }
//            },500);
        }

        //修改字体大小,设置为px,并且修改颜色
        if (expand != null){
            holder.floatTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    Float.parseFloat(expand.getContentModule().getSize()));
        }

        //我自己的删除方法
//        if (expand != null){
//            final Editable editable2 = (Editable) holder.floatTextView.getEditableText();
//            holder.floatTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                @Override
//                public void onGlobalLayout() {
//                    int Lines = holder.floatTextView.getLineCount();
//                    int need_lines = Integer.parseInt(expand.getRows());
//                    int line_to_move = Lines - need_lines + 1;
//                    if (line_to_move > 0){
//                        for (int i = 0; i < line_to_move; i++) {
//                            int lineStart = holder.floatTextView.getLayout().getLineStart(0);
//                            int lineEnd = holder.floatTextView.getLayout().getLineEnd(0);
//                            editable2.delete(lineStart,lineEnd);
//                        }
//                    }
//                    holder.floatTextView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                }
//            });
//        }




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
