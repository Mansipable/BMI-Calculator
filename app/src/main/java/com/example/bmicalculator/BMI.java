package com.example.bmicalculator;

public class BMI {
    private float wt;
    private String date;

    BMI(){  }
    public  String toString(){
        return (" date and time : "+date+"       bmi : "+wt);
    }


    public BMI(float wt, String date) {
        this.wt = wt;
        this.date = date;
    }

    public float getWt() {
        return wt;
    }

    public void setWt(float wt) {
        this.wt = wt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
