package com.example.grupo5_proyecto1.models;

public class CatalogoLibros {

    private String isbn;
    private String titulo;
    private String descripcionlibro;

    public CatalogoLibros (){

    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcionlibro() {
        return descripcionlibro;
    }

    public void setDescripcionlibro(String descripcionlibro) {
        this.descripcionlibro = descripcionlibro;
    }
}
