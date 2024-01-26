package com.example.medtaxi.classi;

import java.util.Date;
import java.time.LocalDate;

public class Disponibilita {
    private String partitaIVA;
    private LocalDate data;
    private int dispMattina;
    private int dispSera;

    public Disponibilita(LocalDate data, int dispMattina, int dispSera) {
        this.data = data;
        this.dispMattina = dispMattina;
        this.dispSera = dispSera;
    }

    public String getPartitaIVA() {
        return partitaIVA;
    }

    public void setPartitaIVA(String partitaIVA) {
        this.partitaIVA = partitaIVA;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getDispMattina() {
        return dispMattina;
    }

    public void setDispMattina(int dispMattina) {
        this.dispMattina = dispMattina;
    }

    public int getDispSera() {
        return dispSera;
    }

    public void setDispSera(int dispSera) {
        this.dispSera = dispSera;
    }
}
