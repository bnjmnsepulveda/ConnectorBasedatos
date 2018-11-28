package com.benjamin.postgres.util;

import com.benjamin.postgres.exception.ConnectionException;
import com.benjamin.postgres.exception.DatabaseException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author benjamin
 */
public class BasedatosService {

    private String host;
    private String basedatos;
    private String usuario;
    private String clave;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private void connect() throws SQLException {
        String url = "jdbc:postgresql://" + host + ":5432/" + basedatos;
        connection = null;
        try {
            Class.forName("org.postgresql.Driver").newInstance();
            connection = DriverManager.getConnection(url, usuario, clave);
        } catch (Exception ex) {
            throw new ConnectionException(ex);
        }
    }

    private void disconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            //ignorar
        }
    }

    public void ejecutarSQL(String sql) {
        try {
            connect();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            disconnect();
        }
    }

    public String ejecutarResultadosJson(String sql) {
        JsonArray jsonArray = new JsonArray();
        try {
            connect();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData meta = resultSet.getMetaData();
            int cantCol = meta.getColumnCount();
            while (resultSet.next()) {
                JsonObject json = new JsonObject();
                String valor = "vacio";
                String key;
                String javaClass;
                int index;
                for (int x = 0; x < cantCol; x++) {
                    index = x + 1;
                    key = meta.getColumnLabel(index);
                    javaClass = meta.getColumnClassName(index);
                    if (javaClass.equals("java.lang.String")) {
                        valor = resultSet.getString(key);
                    } else if (javaClass.equals("java.lang.Boolean")) {
                        valor = String.valueOf(resultSet.getBoolean(key));
                    } else if (javaClass.equals("java.lang.Integer")) {
                        valor = String.valueOf(resultSet.getInt(key));
                    } else if (javaClass.equals("java.lang.Double")) {
                        valor = String.valueOf(resultSet.getDouble(key));
                    } else if (javaClass.equals("java.lang.Long")) {
                        valor = String.valueOf(resultSet.getInt(key));
                    } else if (javaClass.equals("java.sql.Timestamp")) {
                        Timestamp time = resultSet.getTimestamp(key);
                        if (time != null) {
                            valor = String.valueOf(new Date(time.getTime()));
                        } else {
                            valor = null;
                        }
                    } else {
                        throw new RuntimeException("No se pudo convertir resultado a clase java " + javaClass);
                    }
                    json.addProperty(key, valor);
                }
                jsonArray.add(json);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
        if(jsonArray.size() == 1){
            return jsonArray.get(0).toString();
        }
        return jsonArray.toString();
    }

    public String ejecutarResultadosTabla(String sql) {
        StringBuilder tabla = new StringBuilder("");
        try {
            connect();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData meta = resultSet.getMetaData();
            int cantCol = meta.getColumnCount();
            int indexCol;
            for (int x = 0; x < cantCol; x++) {
                indexCol = x +1;
                tabla.append(meta.getColumnLabel(indexCol).toUpperCase());
                tabla.append(" ");
            }
            tabla.append("\n");
            while (resultSet.next()) {
                String valor = "vacio";
                String javaClass;
                String columna;
                int index;
                for (int x = 0; x < cantCol; x++) {
                    index = x +1;
                    javaClass = meta.getColumnClassName(index);
                    columna =meta.getColumnLabel(index);
                    if (javaClass.equals("java.lang.String")) {
                        valor = resultSet.getString(columna);
                    } else if (javaClass.equals("java.lang.Boolean")) {
                        valor = String.valueOf(resultSet.getBoolean(columna));
                    } else if (javaClass.equals("java.lang.Integer")) {
                        valor = String.valueOf(resultSet.getInt(columna));
                    } else if (javaClass.equals("java.lang.Double")) {
                        valor = String.valueOf(resultSet.getDouble(columna));
                    } else if (javaClass.equals("java.lang.Long")) {
                        valor = String.valueOf(resultSet.getInt(columna));
                    } else if (javaClass.equals("java.sql.Timestamp")) {
                        Timestamp time = resultSet.getTimestamp(columna);
                        if (time != null) {
                            valor = String.valueOf(new Date(time.getTime()));
                        } else {
                            valor = null;
                        }
                    } else {
                        throw new RuntimeException("No se pudo convertir resultado a clase java " + javaClass);
                    }    
                    tabla.append(valor);
                    tabla.append(" ");
                }
                tabla.append("\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
        return tabla.toString();
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

}
