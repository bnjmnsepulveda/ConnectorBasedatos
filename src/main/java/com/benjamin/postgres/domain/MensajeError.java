
package com.benjamin.postgres.domain;

/**
 *
 * @author benjamin
 */
public class MensajeError {

    private String error;
    private String descripcion;
    private String traza;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTraza() {
        return traza;
    }

    public void setTraza(String traza) {
        this.traza = traza;
    }
    
}
