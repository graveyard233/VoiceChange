package com.example.voicechange.Info;

import java.util.List;

public class OnChangeMsg {
    private String mid;
    private String serial_number;
    private String task_id;
    private String type;
    private String participant_name;
    private List<Arr > arr ;
    private int delete_data;
    public void setMid(String mid) {
        this.mid = mid;
    }
    public String getMid() {
        return mid;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }
    public String getSerial_number() {
        return serial_number;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }
    public String getTask_id() {
        return task_id;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public void setParticipant_name(String participant_name) {
        this.participant_name = participant_name;
    }
    public String getParticipant_name() {
        return participant_name;
    }

    public void setArr (List<Arr > arr ) {
        this.arr  = arr ;
    }
    public Arr getArr () {
        if (arr.get(0) != null)
            return arr.get(0);
        else {
            arr.get(0).setId(-1);
            return arr.get(0);
        }
    }

    public void setDelete_data(int delete_data) {
        this.delete_data = delete_data;
    }
    public int getDelete_data() {
        return delete_data;
    }
}
