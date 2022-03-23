package com.example.voicechange.FloatThings;

public class FloatText {
    String text;
    String person;
    int text_id;
    String on_change_text;

    public FloatText(String text, String person) {
        this.text = text;
        this.person = person;
    }

    public FloatText(String text, String person, String on_change_text) {
        this.text = text;
        this.person = person;
        this.on_change_text = on_change_text;
    }

    public String getOn_change_text() {
        return on_change_text;
    }

    public void setOn_change_text(String on_change_text) {
        this.on_change_text = on_change_text;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
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
