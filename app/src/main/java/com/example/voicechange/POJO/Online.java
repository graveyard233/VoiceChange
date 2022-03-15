package com.example.voicechange.POJO;

import java.util.List;

public class Online {

    private int from;
    private int to;
    private String request_id;
    private String fashion;
    private String type;
    private String content;
    private List<Expand> expand;
    private long time;
    public void setFrom(int from) {
        this.from = from;
    }
    public int getFrom() {
        return from;
    }

    public void setTo(int to) {
        this.to = to;
    }
    public int getTo() {
        return to;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }
    public String getRequest_id() {
        return request_id;
    }

    public void setFashion(String fashion) {
        this.fashion = fashion;
    }
    public String getFashion() {
        return fashion;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public void setExpand(List<Expand> expand) {
        this.expand = expand;
    }
    public List<Expand> getExpand() {
        return expand;
    }

    public void setTime(long time) {
        this.time = time;
    }
    public long getTime() {
        return time;
    }
}
