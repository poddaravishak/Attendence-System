package com.eterces.bauetattendance;

public class ClassItem {


    public ClassItem(long cid, String subjectName, String className) {
        this.cid = cid;
        this.subjectName = subjectName;
        this.className = className;
    }

    private long cid;
    private String subjectName;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

   private String className;

    public ClassItem(String className, String subjectName) {
        this.className = className;
        this.subjectName = subjectName;

    }

    public long getCid(){
        return cid;
    }
    public void setCid(long cid) {
        this.cid = cid;
    }
}
