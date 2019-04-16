package com.g9.letsmoveapp;

public class RidesDataModel {
        private int r_id = 0;
        private String r_name = "";
        private String origen = "";
        private double lat_orig = 0;
        private double lng_orig = 0;
        private String fecha_salida = "";
        private String destino = "";
        private double lat_dest = 0;
        private double lng_dest = 0;
        private String fecha_llegada = "";
        private String fecha_limite = "";
        private int precio = 0;
        private String period = "";
        private String tipo = "";
        private String program = "";

        public int getR_id() {
            return r_id;
        }

        public void setR_id(int r_id) {
            this.r_id = r_id;
        }

        public String getR_name() {
            return r_name;
        }

        public void setR_name(String r_name) {
            this.r_name = r_name;
        }

        public String getDestino() {
            return destino;
        }

        public void setDestino(String destino) {
            this.destino = destino;
        }


        public String getOrigen() {
            return origen;
        }

        public void setOrigen(String origen) {
            this.origen = origen;
        }

        public double getLat_orig() {
            return lat_orig;
        }

        public void setLat_orig(double lat_orig) {
            this.lat_orig = lat_orig;
        }

        public double getLng_orig() {
            return lng_orig;
        }

        public void setLng_orig(double lng_orig) {
            this.lng_orig = lng_orig;
        }

        public String getFecha_salida() {
            return fecha_salida;
        }

        public void setFecha_salida(String fecha_salida) {
            this.fecha_salida = fecha_salida;
        }

        public double getLat_dest() {
            return lat_dest;
        }

        public void setLat_dest(double lat_dest) {
            this.lat_dest = lat_dest;
        }

        public double getLng_dest() {
            return lng_dest;
        }

        public void setLng_dest(double lng_dest) {
            this.lng_dest = lng_dest;
        }

        public String getFecha_llegada() {
            return fecha_llegada;
        }

        public void setFecha_llegada(String fecha_llegada) {
            this.fecha_llegada = fecha_llegada;
        }

        public String getFecha_limite() {
            return fecha_limite;
        }

        public void setFecha_limite(String fecha_limite) {
            this.fecha_limite = fecha_limite;
        }

        public int getPrecio() {
            return precio;
        }

        public void setPrecio(int precio) {
            this.precio = precio;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public String getProgram() {
            return program;
        }

        public void setProgram(String program) {
            this.program = program;
        }
    }

