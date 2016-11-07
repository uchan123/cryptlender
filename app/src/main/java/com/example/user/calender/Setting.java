package com.example.user.calender;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by user on 02/11/2016.
 */

public class Setting implements Serializable {
    Integer idpengaturan;
    String nama;
    String value;
    String description;
    String tag;
    Integer rel;

    private Context mContext;

    public Setting (Integer idpengaturan, String nama, String value, String description, String tag, Integer rel){
        this.idpengaturan = idpengaturan;
        this.nama = nama;
        this.value = value;
        this.description = description;
        this.tag = tag;
        this.rel = rel;
    }

    public Setting(){

    }
    public Integer getIdpengaturan() {
        return idpengaturan;
    }

    public void setIdpengaturan(Integer idpengaturan) {
        this.idpengaturan = idpengaturan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getValue() {
        return value;
    }

    public Setting setValue(String value) {
        this.value = value;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getRel() {
        return rel;
    }

    public Setting setRel(Integer rel) {
        this.rel = rel;
        return this;
    }

    //save
    public void save(Context c){
        DatabaseHelper db = new DatabaseHelper(c);
        if (idpengaturan == 0){
            db.addSetting(this);
        }else {
            db.editSetting(this);
        }

    }

    public void save(){
        save(mContext);
    }

    public void setContext(Context context){
        mContext=context;
    }
}
