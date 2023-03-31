package com.eterces.bauetattendance;

public class StudentItem {
    private long sid;
    private int id;
    private String Name;
    private String status;

    public StudentItem(long sid,int id, String name) {
        this.sid =sid;
        this.id = id;
        Name = name;
        status="";

    }

    public int getId() {
      return id;}

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }
}
