package com.example.voicechange.FloatThings;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voicechange.R;

import java.util.List;

public class FloatTextAdapter extends RecyclerView.Adapter<FloatTextAdapter.ViewHolder> {

    private List<FloatText> myFloatTextList;

    private String color ;

    private String isBold;

    private AssetManager mgr;

    public void setColor(String color) {
        this.color = color;
    }

    public void setFontStyle(String isBold) {
        this.isBold = isBold;
    }

    public void setMgr(AssetManager mgr) {
        this.mgr = mgr;
    }

    public FloatTextAdapter(List<FloatText> floatTextList){
        myFloatTextList = floatTextList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView floatTextView;
        TextView floatTextPerson;
        ScrollView scrollView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            floatTextView = itemView.findViewById(R.id.textView_text);
            floatTextPerson = itemView.findViewById(R.id.textView_person);
            scrollView = itemView.findViewById(R.id.Myscroll);
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

        //语音文字部分
        holder.floatTextView.setText(floatText.getText());
        holder.floatTextView.getPaint().setFakeBoldText(true);//字体加粗

        if (mgr != null){//修改字体
            Typeface typeface = Typeface.createFromAsset(mgr,
                    "ChuangKeTieJinGangTi-2.otf");
                    holder.floatTextView.setTypeface(typeface);
        }

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
                    }
                },1);
            }
        });

        int lineheight = holder.floatTextView.getLineHeight();

        holder.scrollView.setMinimumHeight(lineheight * 3);

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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                holder.floatTextView.append("最近发现自己负责的项目中，有使用 ScrollView 嵌套 RecyclerView 的地方");
            }
        },2000);




        //设置说话人的部分
        holder.floatTextPerson.setText(floatText.getPerson());
        if (color != null){
            holder.floatTextView.setTextColor(Color.parseColor(color));
        }

    }




    @Override
    public int getItemCount() {
        return myFloatTextList.size();
    }
}
