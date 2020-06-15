package com.example.grupo5_proyecto1.models;

public class CatalogoTipoLibro {

    private int codTipoLibro;
    private String descripcion;

    public CatalogoTipoLibro(){

    }

    public int getCodTipoLibro() {
        return codTipoLibro;
    }

    public void setCodTipoLibro(int codTipoLibro) {
        this.codTipoLibro = codTipoLibro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
