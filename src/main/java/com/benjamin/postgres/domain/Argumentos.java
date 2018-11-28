
package com.benjamin.postgres.domain;

/**
 *
 * @author benjamin
 */
public class Argumentos {

    private String host;
    private String basedatos;
    private String usuario;
    private String clave;
    private String sql;
    private boolean resultados;

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

    public boolean isResultados() {
        return resultados;
    }

    public void setResutados(boolean resutados) {
        this.resultados = resutados;
    }

    @Override
    public String toString() {
        return "Argumentos{" + "host=" + host + ", basedatos=" + basedatos + ", usuario=" + usuario + ", clave=" + clave + ", sql=" + sql + ", resutados=" + resultados + '}';
    }

}
