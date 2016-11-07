package com.example.user.calender;

import java.io.Serializable;

/**
 * Created by user on 24/10/2016.
 */
public class Agenda implements Serializable {
    Integer id;
    String tanggal;
    String jam;
    String judul;
    String isi;
    Double latitude;
    Double longitude;
    String partisipan;

    public Agenda(Integer id, String tanggal, String jam, String judul, String isi, Double latitude, Double longitude, String partisipan ) {
        this.id = id;
        this.tanggal = tanggal;
        this.jam = jam;
        this.judul = judul;
        this.isi = isi;
        this.latitude = latitude;
        this.longitude = longitude;
        this.partisipan = partisipan;
    }

//constructur
    public Agenda() {
    }
//getter setter

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getPartisipan() {
        return partisipan;
    }

    public void setPartisipan(String partisipan) {
        this.partisipan = partisipan;
    }
}
