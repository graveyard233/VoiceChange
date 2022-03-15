package com.example.voicechange.POJO;

import java.util.List;

public class Name_card_config {
        private String color;
        private String font;
        private String bg;
        private String font_weight;
        private List<Widget> widget;
        public void setColor(String color) {
            this.color = color;
        }
        public String getColor() {
            return color;
        }

        public void setFont(String font) {
            this.font = font;
        }
        public String getFont() {
            return font;
        }

        public void setBg(String bg) {
            this.bg = bg;
        }
        public String getBg() {
            return bg;
        }

        public void setFont_weight(String font_weight) {
            this.font_weight = font_weight;
        }
        public String getFont_weight() {
            return font_weight;
        }

        public void setWidget(List<Widget> widget) {
            this.widget = widget;
        }
        public List<Widget> getWidget() {
            return widget;
        }
}
