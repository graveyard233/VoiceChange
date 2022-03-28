package com.example.voicechange.Info;

public class Arr {
    private long id ;
    private String content ;
    private int sort;
    private String section_sort ;
    private String pcm_file ;
    private int status;
    private int start_time;
    private int end_time;
    private String create_time;
    public void setId (long id ) {
        this.id  = id ;
    }
    public long getId () {
        return id ;
    }

    public void setContent (String content ) {
        this.content  = content ;
    }
    public String getContent () {
        return content ;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
    public int getSort() {
        return sort;
    }

    public void setSection_sort (String section_sort ) {
        this.section_sort  = section_sort ;
    }
    public String getSection_sort () {
        return section_sort ;
    }

    public void setPcm_file (String pcm_file ) {
        this.pcm_file  = pcm_file ;
    }
    public String getPcm_file () {
        return pcm_file ;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }
    public int getStart_time() {
        return start_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }
    public int getEnd_time() {
        return end_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
    public String getCreate_time() {
        return create_time;
    }

    public Arr(long id) {
        this.id = id;
    }
}
