package com.benjamin.postgres.main;

import com.benjamin.postgres.domain.Argumentos;
import com.benjamin.postgres.domain.MensajeError;
import com.benjamin.postgres.exception.ConnectionException;
import com.benjamin.postgres.exception.DatabaseException;
import com.benjamin.postgres.util.ArgumentosHelper;
import com.benjamin.postgres.util.BasedatosService;
import com.google.gson.Gson;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author benjamin
 */
public class App {

    public static void main(String[] args) {
        Argumentos entrada = null;
        try {
            entrada = ArgumentosHelper.proccessArgs(args, Argumentos.class);
        } catch (ClassNotFoundException | IllegalAccessException 
                | IllegalArgumentException | InstantiationException 
                | NoSuchMethodException | InvocationTargetException e) {
            System.err.println(e.getMessage());
            System.err.println("error ingresando parametros");
            System.exit(1);
        }
        try {            
            //System.out.println("Argumentos de entrada: " + entrada);
            BasedatosService service = new BasedatosService();
            service.setBasedatos(entrada.getBasedatos());
            service.setClave(entrada.getClave());
            service.setHost(entrada.getHost());
            service.setUsuario(entrada.getUsuario());
            String salida;
            if (entrada.getSql().toLowerCase().startsWith("select ")) {
                if (entrada.getFormatoSalida().equals("json")) {
                    salida = service.ejecutarResultadosJson(entrada.getSql());
                } else if (entrada.getFormatoSalida().equals("text")) {
                    salida = service.ejecutarResultadosTabla(entrada.getSql());
                } else {
                    salida = service.ejecutarResultadosJson(entrada.getSql());
                }
                System.out.println(salida);
            } else {
                service.ejecutarSQL(entrada.getSql());
            }
        } catch (ConnectionException ce) {
            //ce.printStackTrace(System.err);
            MensajeError error = new MensajeError();
            error.setError("No se pudo establecer conexion a base de datos");
            error.setDescripcion(ce.getMessage());
            System.out.println(new Gson().toJson(error));
        } catch (DatabaseException de){
            //de.printStackTrace(System.err);            
            MensajeError error = new MensajeError();
            error.setError("No se pudo ejecutar " + entrada.getSql());
            error.setDescripcion(de.getMessage());
            System.out.println(new Gson().toJson(error));
        } catch(Exception e){
            MensajeError error = new MensajeError();
            error.setError("Error no esperado");
            error.setDescripcion(e.getMessage());
            System.out.println(new Gson().toJson(error));
        }
    }
    
    public static void imprimirAyuda(){
        
    }
}
