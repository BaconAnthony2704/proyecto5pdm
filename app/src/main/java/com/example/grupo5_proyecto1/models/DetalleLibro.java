package com.example.grupo5_proyecto1.models;

public class DetalleLibro {

    private String codigoArticulo;
    private String isbn;
    private String idioma;
    private int codTipoLibro;
    private String titulo;

public DetalleLibro(){

}

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String  isbn) {
        this.isbn = isbn;
    }

    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getCodTipoLibro() {
        return codTipoLibro;
    }

    public void setCodTipoLibro(int codTipoLibro) {
        this.codTipoLibro = codTipoLibro;
    }
}
