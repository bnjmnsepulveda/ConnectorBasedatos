package com.benjamin.postgres.main;

import com.benjamin.postgres.domain.Argumentos;
import com.benjamin.postgres.util.ArgumentosHelper;
import com.benjamin.postgres.util.BasedatosService;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author benjamin
 */
public class App {

    public static void main(String[] args) {
        System.out.println("--- accediendo a basedatos ---");
        try {
            Argumentos entrada = ArgumentosHelper.proccessArgs(args, Argumentos.class);
            System.out.println("Argumentos de entrada: " + entrada);
            BasedatosService service = new BasedatosService();
            service.setBasedatos(entrada.getBasedatos());
            service.setClave(entrada.getClave());
            service.setHost(entrada.getHost());
            service.setUsuario(entrada.getUsuario());
            String salida = "";
            if (entrada.getSql().toLowerCase().startsWith("select ")) {
                if (entrada.getFormatoSalida().equals("json")) {
                    salida = service.ejecutarResultadosJson(entrada.getSql());
                } else if (entrada.getFormatoSalida().equals("tabla")) {
                    salida = service.ejecutarResultadosTabla(entrada.getSql());
                } else {
                    salida = service.ejecutarResultadosJson(entrada.getSql());
                }
                System.out.println("resultados:\n " + salida);
            } else {
                service.ejecutarSQL(entrada.getSql());
            }
            System.out.println("ejecucion exitosa!!!");
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace(System.err);
        }
    }
}
