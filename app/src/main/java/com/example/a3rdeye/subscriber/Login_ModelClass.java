package com.example.a3rdeye.subscriber;

public class Login_ModelClass {

    String id,fname,lname,mobileno;

    public Login_ModelClass(String id, String fname, String lname, String mobileno) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.mobileno = mobileno;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }
}
