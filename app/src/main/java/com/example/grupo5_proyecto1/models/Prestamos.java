package com.example.grupo5_proyecto1.models;

public class Prestamos {

    private int nodocumento;
    private String materia;
    private String Docente;
    private String actividad;
    private String duracion;
    private String codigoArticulo;

    public Prestamos () {}

    public int getNodocumento() {
        return nodocumento;
    }

    public void setNodocumento(int nodocumento) {
        this.nodocumento = nodocumento;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getDocente() {
        return Docente;
    }

    public void setDocente(String docente) {
        Docente = docente;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }
}
