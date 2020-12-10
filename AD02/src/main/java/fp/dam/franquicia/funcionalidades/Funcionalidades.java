/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.franquicia.funcionalidades;

import java.util.Scanner;

/**
 *
 * @author manuel
 */
public class Funcionalidades {
      
    public static int insertarDatosInt(String informacion) {
        System.out.println(informacion);
        Scanner sc = new Scanner(System.in);
        String num = sc.nextLine();
        int numInt = -1;
        try {
            numInt = Integer.parseInt(num);
        } catch (NumberFormatException e) {
            System.out.println("Debes insertar un número :" + e);
            insertarDatosInt(informacion);
        }
        return numInt;
    }

    public static double insertarDatosDouble(String informacion) {
        System.out.println(informacion);
        Scanner sc = new Scanner(System.in);
        String num = sc.nextLine();
        double numDouble = -1;
        try {
            numDouble = Double.parseDouble(num);
        } catch (NumberFormatException e) {
            System.out.println("Debes insertar un número :" + e);
            insertarDatosDouble(informacion);
        }
        return numDouble;
    }

    public static String insertarDatosString(String informacion) {
        System.out.println(informacion);
        Scanner sc = new Scanner(System.in);
        String cadena = sc.nextLine();
        return cadena;
    }   
 
}
