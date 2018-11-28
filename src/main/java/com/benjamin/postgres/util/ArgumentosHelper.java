
package com.benjamin.postgres.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Clase de ayuda para obtener los argumentos de la Main class y setearlos en 
 * cualquier clase POJO que contenga variables con sus respectivos getters y setters.
 * @author benjamin
 */
public class ArgumentosHelper {

    /**
     * Recibe un Array de String que representan una lista de argumentos con el 
     * formato NombrePropiedad=Valor e instancia 
     * un objeto POJO que contenga como variables de clase el mismo nombre 
     * de los argumentos recibidos.
     * @param <E> Tipo generico de clase.
     * @param args Array de String con los argumentos a setear, deben tener el formato NombrePropiedad=Valor.
     * @param clase clase a instanciar con los valores de argumentos aceptados.
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws InvocationTargetException 
     */
    public static <E> E proccessArgs(String[] args, Class<E> clase) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {

        Field[] argumentFields = clase.getDeclaredFields();
        E configuracion = clase.getConstructor().newInstance();
        String nombreArg;
        String valorArg;
        String tipoObjeto;
        for (String arg : args) {
            if (arg.contains("=")) {
                nombreArg = arg.split("=")[0];
                valorArg = arg.split("=")[1];
            } else {
                continue;
            }
            for (Field field : argumentFields) {
                if (nombreArg.equals(field.getName())) {
                    //System.out.println("parametro " + field.getName() + " es tipo " + field.getType().getSimpleName() + " de valor " + valorArg);
                    tipoObjeto = field.getType().getSimpleName().toLowerCase();
                    field.setAccessible(true);
                    switch (tipoObjeto) {
                        case "int":
                            int valorInt = Integer.parseInt(valorArg);
                            field.set(configuracion, valorInt);
                            break;
                        case "integer":
                            int valorInteger = Integer.parseInt(valorArg);
                            field.set(configuracion, valorInteger);
                            break;
                        case "long":
                            long valorLong = Long.parseLong(valorArg);
                            field.set(configuracion, valorLong);
                            break;
                        case "double":
                            double valorDouble = Double.parseDouble(valorArg);
                            field.set(configuracion, valorDouble);
                            break;
                        case "float":
                            float valorFloat = Float.parseFloat(valorArg);
                            field.set(configuracion, valorFloat);
                            break;
                        case "string":
                            field.set(configuracion, valorArg);
                            break;
                        case "boolean":
                            boolean valorBoolean = Boolean.parseBoolean(valorArg.trim());
                            field.set(configuracion,valorBoolean);
                            break;
                        case "date":
                            throw new UnsupportedOperationException("Conversion a java.util.Date no soportada");
                        default:
                            break;
                    }
                    break;
                }
            }
        }
        return configuracion;
    }

    /**
     * Muestra los nombres de campo posibles a setear en la clase POJO, con los 
     * argumentos recibidos.
     * @param <E>
     * @param clase Clase a consultar.
     */
    public static <E> void showAvalaibleParams(Class<E> clase) {
        System.out.println("---- parametros disponibles ----");
        Method[] mts = clase.getMethods();
        for (Method mt : mts) {
            if(mt.getName().equals("getClass")){
                continue;
            }
            if (mt.getName().startsWith("get")) {
                System.out.println("parametro: "+mt.getName().substring(3) + "=");
            }
        }
        System.out.println();
    }
    
    /**
     * Muestra los atributos y valores asociados a la instancia de clase entregada.
     * @param <E>
     * @param instance 
     * @throws IllegalArgumentException
     * @throws IllegalAccessException 
     */
    public static <E> void showAttributes(E instance) throws IllegalArgumentException, IllegalAccessException {
        Class clase = instance.getClass();
        System.out.println("---- objeto clave valor ----");
        Field[] fs = clase.getDeclaredFields();
        for (Field field : fs) {
            field.setAccessible(true);
            System.out.println("atributo:" + field.getName() + "=" + field.get(instance));
        }        
        System.out.println();
    }
}
