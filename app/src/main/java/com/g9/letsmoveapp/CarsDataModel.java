package com.g9.letsmoveapp;

public class CarsDataModel {
    private int c_id;
    private String c_name;
    private String model;
    private int plazas;
    private String color;
    private String size;
    private double consumo;
    private int antig;
    private String uri;

    public CarsDataModel(int c_id, String c_name, String model, int plazas,
                         String color, String size, double consumo, int antig, String uri) {
        this.c_id = c_id;
        this.c_name = c_name;
        this.model = model;
        this.plazas = plazas;
        this.color = color;
        this.size = size;
        this.consumo = consumo;
        this.antig = antig;
        this.uri=uri;
    }

    public CarsDataModel() {
        this.c_id = 0;
        this.c_name = "coche1";
        this.model = "berlingo";
        this.plazas = 3;
        this.color = "red";
        this.size = "big";
        this.consumo = 0;
        this.antig = 1;
        this.uri="kdsjfalksdfjksadfjakslf";
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
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