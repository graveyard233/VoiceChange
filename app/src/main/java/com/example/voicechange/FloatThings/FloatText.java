package com.example.voicechange.FloatThings;

public class FloatText {
    String text;
    int text_id;

    public FloatText(String text, int text_id) {
        this.text = text;
        this.text_id = text_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getText_id() {
        return text_id;
    }

    public void setText_id(int text_id) {
        this.text_id = text_id;
    }
}
