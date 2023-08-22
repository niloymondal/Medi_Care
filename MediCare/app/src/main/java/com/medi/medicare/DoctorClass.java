package com.medi.medicare;

import androidx.annotation.NonNull;

public class DoctorClass  {

    private String email;
    private String fname;
    private String lname;
    private String dgree;
    private String college;
    private String chamber;
    private String id;
    private String pass;
    private String specialized;
    private String consultan_hour;
    private String imageUrl;
    public DoctorClass( String email, String fname, String lname,
                        String dgree, String college, String chamber, String id,
                        String pass, String specialized, String imageUrl) {

        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.dgree = dgree;
        this.college = college;
        this.chamber = chamber;
        this.id = id;
        this.pass = pass;
        this.specialized = specialized;
        this.imageUrl=imageUrl;
    }

    public DoctorClass() {
    }
    public DoctorClass(String id, String fname, String lname, String dgree, String specialized) {
        this.id=id;
        this.fname = fname;
        this.lname = lname;
        this.dgree = dgree;
        this.specialized = specialized;
    }



    public DoctorClass(String email, String name) {
        this.email = email;
        this.fname = name;
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

    public String getDgree() {
        return dgree;
    }

    public void setDgree(String dgree) {
        this.dgree = dgree;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getChamber() {
        return chamber;
    }

    public void setChamber(String chamber) {
        this.chamber = chamber;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSpecialized() {
        return specialized;
    }

    public void setSpecialized(String specialized) {
        this.specialized = specialized;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return fname;
    }

    public void setName(String name) {
        this.fname = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public String toString() {
        //String fname, String lname, String dgree, String specialized
        return
                "Name : "+fname+" "+lname+"\n"+" "+dgree+"\n"+"Specialized -In : "+specialized+"\n"+"ID = " + id ;
    }

    public String getConsultan_hour() {
        return consultan_hour;
    }

    public void setConsultan_hour(String consultan_hour) {
        this.consultan_hour = consultan_hour;
    }
}
