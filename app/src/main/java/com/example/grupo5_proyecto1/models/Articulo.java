package com.example.grupo5_proyecto1.models;

public class Articulo {
    private String codigoArticulo;
    private String codTipoArticulo;
    private String fecha;
    private int estado;

    public Articulo() {
    }

    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public String getCodTipoArticulo() {
        return codTipoArticulo;
    }

    public void setCodTipoArticulo(String codTipoArticulo) {
        this.codTipoArticulo = codTipoArticulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
