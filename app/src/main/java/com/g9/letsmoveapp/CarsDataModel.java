package com.g9.letsmoveapp;

public class CarsDataModel {
    private String model;
    private int plazas;
    private String color;
    private String size;
    private double consumo;
    private int antig;
    private String uri;

    public CarsDataModel(String model, int plazas,
                         String color, String size, double consumo, String uri, int antig) {
        this.model = model;
        this.plazas = plazas;
        this.color = color;
        this.size = size;
        this.consumo = consumo;
        this.antig = antig;
        this.uri=uri;
    }

    public CarsDataModel() {
        this.model = "berlingo";
        this.plazas = 3;
        this.color = "red";
        this.size = "big";
        this.consumo = 0;
        this.antig = 1;
        this.uri="kdsjfalksdfjksadfjakslf";
    }




    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPlazas() {
        return plazas;
    }

    public void setPlazas(int plazas) {
        this.plazas = plazas;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getConsumo() {
        return consumo;
    }

    public void setConsumo(double consumo) {
        this.consumo = consumo;
    }

    public int getAntig() {
        return antig;
    }

    public void setAntig(int antig) {
        this.antig = antig;
    }

    public String getUri() {
        return uri;
    }

    public void setUri (String uri) {
        this.uri = uri;
    }
}