package com.example.practicasemana14;


import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {

    private long date;
    private boolean completed;
    private String title;
    private String id;
    private String description;
    private String completado;



    public Task(){

    }

    public Task(String id, String title, String description){
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = false;
        this.date = new Date().getTime();
    }

    public String getDateStr(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        date.setTime(this.date);
        return sdf.format(date);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getCompletado() {
        return completado;
    }

    public void setCompletado(String completado) {
        this.completado = completado;
    }

    @Override
    public String toString() {
        if(completed == false){
            completado = "Sin completar";
        }
        if(completed == true){
            completado = "Completada";
        }
        return title+"\n"+description+"\n"+getDateStr()+"\n"+completado;
    }
}