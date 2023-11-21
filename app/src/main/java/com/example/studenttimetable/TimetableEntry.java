package com.example.studenttimetable;

public class TimetableEntry {
    private int id;
    private String day;
    private String time;
    private String info;
    private String subject;

    public TimetableEntry(int id, String day, String time, String subject,String info) {
        this.id = id;
        this.day = day;
        this.time = time;
        this.info = info;
        this.subject = subject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
