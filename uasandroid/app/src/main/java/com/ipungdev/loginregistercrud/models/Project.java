package com.ipungdev.loginregistercrud.models;

import com.google.gson.annotations.SerializedName;

public class Project {
    @SerializedName("id")
    private Integer id;

    @SerializedName("nama")
    private String nama;

    @SerializedName("intro")
    private String intro;

    @SerializedName("lokasi")
    private String lokasi;

    @SerializedName("harga")
    private String harga;


    public Project(Integer id, String nama, String intro, String lokasi, String harga) {
        this.id = id;
        this.nama = nama;
        this.intro = intro;
        this.lokasi = lokasi;
        this.harga = harga;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}
