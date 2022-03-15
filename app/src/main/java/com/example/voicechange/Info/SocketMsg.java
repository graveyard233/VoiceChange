package com.example.voicechange.Info;

public class SocketMsg {
    private String from;
    private String to;
    private String request_id;
    private String fashion;
    private String type;
    private String content;
    private String expand;
    private long time;
    public void setFrom(String from) {
        this.from = from;
    }
    public String getFrom() {
        return from;
    }

    public void setTo(String to) {
        this.to = to;
    }
    public String getTo() {
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

    public void setExpand(String expand) {
        this.expand = expand;
    }
    public String getExpand() {
        return expand;
    }

    public void setTime(long time) {
        this.time = time;
    }
    public long getTime() {
        return time;
    }
}
