package com.example.shubhangi.gpsone;

/**
 * Created by shubhangi on 03-12-2017.
 */

public class User {
   private String usr_name;
   private String conact_no;
   private String usr_email;
   private String dob;
   private String usr_password;
   private int marital;
   private String blood_group;

    public User(String usr_name,String conact_no,String usr_email,String dob,String usr_password,int marital,String blood_group){
        this.usr_name=usr_name;
        this.conact_no=conact_no;
        this.usr_email=usr_email;
        this.dob=dob;
        this.usr_password=usr_password;
        this.marital=marital;
        this.blood_group=blood_group;
    }

    public String getUsr_name() {
        return usr_name;
    }

    public String getConact_no() {
        return conact_no;
    }

    public String getUsr_email() {
        return usr_email;
    }

    public String getUsr_password() {
        return usr_password;
    }

    public String getDob() {
        return dob;
    }

    public void setUsr_password(String usr_password) {
        this.usr_password = usr_password;
    }

    public String getBlood_group() {
        return blood_group;
    }

    public int getMarital() {
        return marital;
    }

    public void setBlood_group(String blood_group) {
        this.blood_group = blood_group;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setMarital(int marital) {
        this.marital = marital;
    }

    public void setConact_no(String conact_no) {
        this.conact_no = conact_no;
    }

    public void setUsr_email(String usr_email) {
        this.usr_email = usr_email;
    }

    public void setUsr_name(String usr_name) {
        this.usr_name = usr_name;
    }
}
