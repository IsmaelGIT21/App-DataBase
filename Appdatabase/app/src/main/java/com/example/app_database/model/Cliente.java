package com.example.app_database.model;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
@Entity
public class Cliente {
    @PrimaryKey
    @NonNull
    private String cnpj;
    private String razaoSocial;
    private long cidadeIbge;
    private String nomeFantasia;
    private String contato;
    private String telefone;
    private String email;
    private Date ultimaVisita;
    private String cep;
    private String logradouro;
    private String bairro;
    private String numero;

    public Cliente(){

    }
    @NonNull
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(@NonNull String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getUltimaVisita() {
        return ultimaVisita;
    }

    public void setUltimaVisita(Date ultimaVisita) {
        this.ultimaVisita = ultimaVisita;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    public long getCidadeIbge() {
        return cidadeIbge;
    }

    public void setCidadeIbge(long cidadeIbge) {
        this.cidadeIbge = cidadeIbge;
    }
    @Override
    public String toString() {
        // Escolha o que quer exibir (ex: Nome Fantasia ou Razão Social + CNPJ)
        return this.razaoSocial + " (CNPJ: " + this.cnpj + ")";
    }
}
