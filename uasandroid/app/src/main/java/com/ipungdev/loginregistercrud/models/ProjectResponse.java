package com.ipungdev.loginregistercrud.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProjectResponse {

    @SerializedName("proyeks")
    private List<Project> proyeks;

    @SerializedName("message")
    private String message;

    public List<Project> getProyeks() {
        return proyeks;
    }

    public void setProyeks(List<Project> proyeks) {
        this.proyeks = proyeks;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
