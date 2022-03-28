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
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
        ScrollView scrollView;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            floatTextView = itemView.findViewById(R.id.textView_text);
            floatTextPerson = itemView.findViewById(R.id.textView_person);
            scrollView = itemView.findViewById(R.id.Myscroll);
            linearLayout = itemView.findViewById(R.id.MyLinearlayout);
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

            // TODO: 2022/3/25  
            //设置scroll,还没测试完
            int maxlines = holder.floatTextView.getMaxLines();
            int lineheight =  holder.floatTextView.getLineHeight();
            float scrollheight = lineheight * maxlines;

            ViewGroup.LayoutParams lp;
            lp = holder.linearLayout.getLayoutParams();
            lp.height = (int) holder.floatTextView.getTextSize() * (Integer.parseInt(expand.getRows()) - 1) ;
            lp.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            holder.linearLayout.setLayoutParams(lp);

            holder.scrollView.setMinimumHeight(lineheight * Integer.parseInt(expand.getRows()));

            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    holder.scrollView.fullScroll(View.FOCUS_DOWN);
                }
            });
            //让scrollview碰不到
            holder.scrollView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });

            holder.floatTextView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            holder.scrollView.fullScroll(View.FOCUS_DOWN);
//                            final
                        }
                    },0);
                }
            });

//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    String append_text = "最近发现自己负责的项目中，有使用 ScrollView 嵌套 RecyclerView 的地方";
//                    //设置成正在转写的样式
//                    final SpannableString s_append = new SpannableString(append_text);
//                    ForegroundColorSpan colorSpan_append = new ForegroundColorSpan(
//                            Color.parseColor(expand.getContentModule().getFocus_color()));
//                    BackgroundColorSpan backgroundColorSpan_append = new BackgroundColorSpan(
//                            Color.parseColor(expand.getContentModule().getFocus_bg_color()));
//                    s_append.setSpan(colorSpan_append,0,s_append.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    s_append.setSpan(backgroundColorSpan_append,0,s_append.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    holder.floatTextView.append(s_append);
//                }
//            },2000);
        }
        //修改字体
        if (expand != null && mgr!= null){
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


    public void changeData(int position){
        Log.e("TAG", "changeData: item update");

        notifyItemChanged(position,myFloatTextList);
    }

    public void setMyFloatTextList(List<FloatText> myFloatTextList) {
        this.myFloatTextList = myFloatTextList;
    }

    @Override
    public int getItemCount() {
        return myFloatTextList.size();
    }
}
