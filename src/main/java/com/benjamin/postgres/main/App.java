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
            if (entrada.isResultados()) {
                String json = service.ejecutarResultadosTabla(entrada.getSql());
                System.out.println("resultados:\n " + json);
            } else {
                service.ejecutarSQL(entrada.getSql());
            }
            System.out.println("ejecucion exitosa!!!");
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace(System.err);
        }
    }
}
