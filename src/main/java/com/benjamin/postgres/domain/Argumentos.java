
package com.benjamin.postgres.domain;

import com.google.gson.Gson;

/**
 * Argumentos de inicio para ejecutar la aplicacion.
 * @author benjamin
 */
public class Argumentos {

    private String host;
    private String basedatos;
    private String usuario;
    private String clave;
    private String sql;
    private String formatoSalida;

    public String getFormatoSalida() {
        return formatoSalida;
    }

    public void setFormatoSalida(String formatoSalida) {
        this.formatoSalida = formatoSalida;
    }

    
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getBasedatos() {
        return basedatos;
    }

    public void setBasedatos(String basedatos) {
        this.basedatos = basedatos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        return "Argumentos: " + new Gson().toJson(this);
    }

}
