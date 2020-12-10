/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.franquiciav3.utils;

import java.util.Scanner;

/**
 *
 * @author manuel
 */
public class Utils {

    /**
     * Método para insertar datos de tipo int.
     * @param informacion
     * @return 
     */
    public int insertarDatosInt(String informacion) {
        System.out.println(informacion);
        Scanner sc = new Scanner(System.in);
        String num = sc.nextLine();
        int numInt = -1;
        try {
            numInt = Integer.parseInt(num);
        } catch (NumberFormatException e) {
            System.out.println("Debes insertar un número.");
            return insertarDatosInt(informacion);
        }
        return numInt;
    }

    /**
     * Método para insertar datos de tipo double.
     * @param informacion
     * @return 
     */
    public double insertarDatosDouble(String informacion) {
        System.out.println(informacion);
        Scanner sc = new Scanner(System.in);
        String num = sc.nextLine();
        double numDouble = -1;
        try {
            numDouble = Double.parseDouble(num);
        } catch (NumberFormatException e) {
            System.out.println("Debes insertar un número.");
            return insertarDatosDouble(informacion);
        }
        return numDouble;
    }

    /**
     * Método para insertar datos de tipo String.
     * @param informacion
     * @return 
     */
    public String insertarDatosString(String informacion) {
        System.out.println(informacion);
        Scanner sc = new Scanner(System.in);
        String cadena = sc.nextLine();
        if (cadena.trim().isEmpty()) {
            System.out.println("Debes insertar algo.");
            return insertarDatosString(informacion);
        } else {
            return cadena;
        }
    }

    /**
     * Método para hacer una pausa.
     * @return 
     */
    public String pulsarEnter() {
        System.out.println("Pulsa enter para continuar....");
        Scanner sc = new Scanner(System.in);
        String cadena = sc.nextLine();
        return cadena;
    }

    /**
     * Método para tomar una decisión.
     * @param informacion
     * @return 
     */
    public String tomaDecision(String informacion) {
        System.out.println(informacion);
        Scanner sc = new Scanner(System.in);
        String cadena = sc.nextLine();
        if (cadena.trim().equalsIgnoreCase("SI")) {
            return cadena;
        } else if (cadena.trim().equalsIgnoreCase("NO")) {
            return cadena;
        } else {
            return tomaDecision(informacion);
        }
    }
    
    /**
     * Método para insertar un número positivo.
     * @param informacion
     * @return 
     */
    public int insertarNumeroPositivo(String informacion) {
        System.out.println(informacion);
        Scanner sc = new Scanner(System.in);
        String num = sc.nextLine();
        int numIntPos = -1;
        try {
            numIntPos = Integer.parseInt(num);
            if(numIntPos>=0){
                return numIntPos;
            }else{
                System.out.println("Debes insertar un número positivo");
                return insertarNumeroPositivo(informacion);
            }
        } catch (NumberFormatException e) {
            System.out.println("Debes insertar un número.");
            return insertarNumeroPositivo(informacion);
        }      
    }
    
    /**
     * Método para insertar un número de semana.
     * @param informacion
     * @return 
     */
    public int insertarNumeroSemanal(String informacion) {
        System.out.println(informacion);
        Scanner sc = new Scanner(System.in);
        String num = sc.nextLine();
        int numIntSem = -1;
        try {
            numIntSem = Integer.parseInt(num);
            if(numIntSem>0 && numIntSem<=52){
                return numIntSem;
            }else{
                System.out.println("Debes insertar un número de semana entre  el 1 y el 52");
                return insertarNumeroSemanal(informacion);
            }
        } catch (NumberFormatException e) {
            System.out.println("Debes insertar un número.");
            return insertarNumeroSemanal(informacion);
        }
    }
    
    /**
     * Método para insertar el número de horas semanales.
     * @param informacion
     * @return 
     */
    public int insertarNumeroHorasSemanal(String informacion) {
        System.out.println(informacion);
        Scanner sc = new Scanner(System.in);
        String num = sc.nextLine();
        int numIntHorSem = -1;
        try {
            numIntHorSem = Integer.parseInt(num);
            if(numIntHorSem>0 && numIntHorSem<=168){
                return numIntHorSem;
            }else{
                System.out.println("Debes insertar un número de horas entre  el 1 y el 168");
                return insertarNumeroHorasSemanal(informacion);
            }
        } catch (NumberFormatException e) {
            System.out.println("Debes insertar un número.");
            return insertarNumeroHorasSemanal(informacion);
        }
    }

}
