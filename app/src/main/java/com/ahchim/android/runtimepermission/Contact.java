package com.ahchim.android.runtimepermission;

import java.util.ArrayList;

/**
 * Created by Ahchim on 2017-02-01.
 * 전화번호부 POJO (Pure Old Java Object)
 */
public class Contact {
    private int id;
    private String name;
    private ArrayList<String> tel;

    public Contact(){
        tel = new ArrayList<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTel(ArrayList<String> tel) {
        this.tel = tel;
    }

    public void addTel(String tel){
        this.tel.add(tel);
    }

    public void removeTel(String tel){
        this.tel.remove(tel);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getTel() {
        return tel;
    }

    public String getTelOne(){
        if(tel.size()>0) return tel.get(0);
        else return null;
    }
}
