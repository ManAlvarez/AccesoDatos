/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.mongotwitter.menus;

import fp.dam.mongotwitter.bd.Controlador;
import java.util.Scanner;

/**
 *
 * @author manuel
 */
public class MenuPrincipal {

    private static final Controlador controlador = new Controlador();

    public static void menu() {

        String opcion = "";

        while (!opcion.matches("\\d+")) {
            System.out.println("########## MENU ##########");
            System.out.println("0.  Sair.");
            System.out.println("1.  Loguearse.");
            System.out.println("2.  Rexistrarse.");
            System.out.println("Selecciona una opción: ");
            Scanner sc = new Scanner(System.in);
            opcion = sc.nextLine();
        }

        switch (opcion) {
            case "1":
                controlador.loguearUsuario();
                menu();
                break;
            case "2":
                controlador.rexistrarUsuario();
                menu();
                break;
            case "0":
                System.exit(0);
                break;
            default:
                menu();
                break;
        }
    }

}
