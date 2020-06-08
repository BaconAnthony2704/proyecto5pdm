package com.example.grupo5_proyecto1.models;

public class Asignacion {
    private int noDocumento;
    private int codMotivoAsignacion;
    private String codigoArticulo;
    private String docente;
    private String descripcion;
    private String fechaAsignacion;

    public Asignacion() {
    }

    public int getNoDocumento() {
        return noDocumento;
    }

    public void setNoDocumento(int noDocumento) {
        this.noDocumento = noDocumento;
    }

    public int getCodMotivoAsignacion() {
        return codMotivoAsignacion;
    }

    public void setCodMotivoAsignacion(int codMotivoAsignacion) {
        this.codMotivoAsignacion = codMotivoAsignacion;
    }

    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(String fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
}
