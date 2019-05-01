package com.g9.letsmoveapp;

public class RidesDataModel {
    private String r_name = "";//Nombre identificador del viaje
    private String origen = "";//Ciudad de origen
    private double lat_orig = 0;//Latitud origen se saca de maps geocoder
    private double lng_orig = 0;//Longitud origen se saca de maps geocoder
    private String fecha_salida = "";//Fecha de salida "dd/MM/aa"
    private String hora_salida = "";//Hora de salida "hh:mm"
    private String destino = "";//Ciudad destino
    private double lat_dest = 0;//latidud de destino se saca de maps geocoder
    private double lng_dest = 0;//longitud de destino se saca de maps geocoder
    private String fecha_llegada = "";//Fecha de llegada "dd/MM/aa"
    private String hora_llegada="";//Hora de llegada "hh:mm"
    private String hora_limite = "";//Hora límite a la que sale el viaje
    private int precio = 0;//Precio estimado del viaje
    private int num_viajerxs=0;//número de viajeros contando a quien conduce
    private String period = "";//Si es periodico o no
    private String tipo = "";//Semanal, diario?


    public RidesDataModel(String r_name, String origen, double lat_orig,
                          double lng_orig, String fecha_salida,String hora_salida, String destino, double lat_dest, double lng_dest,
                          String fecha_llegada,String hora_llegada, String hora_limite, int precio, String period, String tipo, int num_viajerxs) {
        this.r_name = r_name;
        this.origen = origen;
        this.lat_orig = lat_orig;
        this.lng_orig = lng_orig;
        this.fecha_salida = fecha_salida;
        this.hora_salida = hora_salida;
        this.destino = destino;
        this.lat_dest = lat_dest;
        this.lng_dest = lng_dest;
        this.fecha_llegada = fecha_llegada;
        this.hora_llegada = hora_llegada;
        this.hora_limite = hora_limite;
        this.precio = precio;
        this.period = period;
        this.tipo = tipo;
        this.num_viajerxs = num_viajerxs;
    }

    public RidesDataModel() {
        this.r_name = "Viaje1";
        this.origen = "St Petersburgo";
        this.lat_orig = 12;
        this.lng_orig = 23;
        this.fecha_salida = "01/12/19";
        this.hora_salida = "10:14";
        this.destino = "Paris";
        this.lat_dest = 24;
        this.lng_dest = 54;
        this.fecha_llegada = "01/12/19";
        this.hora_llegada = "12:24";
        this.hora_limite = "12min";
        this.precio = 12;
        this.period = "S";
        this.tipo = "Semanal";
        this.num_viajerxs = 4;
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

    public String getHora_limite() {
        return hora_limite;
    }

    public void setHora_limite(String hora_limite) {
        this.hora_limite = hora_limite;
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

    public int getNum_viajerxs() {
        return num_viajerxs;
    }

    public void setNum_viajerxs(int num_viajerxs) {
        this.num_viajerxs = num_viajerxs;
    }
    public String getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(String hora_salida) {
        this.hora_salida = hora_salida;
    }

    public String getHora_llegada() {
        return hora_llegada;
    }

    public void setHora_llegada(String hora_llegada) {
        this.hora_llegada = hora_llegada;
    }
}

