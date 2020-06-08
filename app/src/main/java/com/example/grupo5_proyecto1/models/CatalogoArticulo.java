package com.example.grupo5_proyecto1.models;

public class CatalogoArticulo {
    private String codTipoArticulo;
    private String descripcion;

    public CatalogoArticulo() {
    }

    public String getCodTipoArticulo() {
        return codTipoArticulo;
    }

    public void setCodTipoArticulo(String codTipoArticulo) {
        this.codTipoArticulo = codTipoArticulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
