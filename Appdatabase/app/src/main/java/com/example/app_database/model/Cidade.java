package com.example.app_database.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class Cidade {
    @PrimaryKey
    private long ibge;
    private String nome;
    private int ddd;
    public Cidade(){

    }

    public long getIbge() {
        return ibge;
    }

    public void setIbge(long ibge) {
        this.ibge = ibge;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDdd() {
        return ddd;
    }

    public void setDdd(int ddd) {
        this.ddd = ddd;
    }
}
