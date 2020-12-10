/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.mongotwitter.menus;

import fp.dam.mongotwitter.bd.Controlador;
import fp.dam.mongotwitter.objetos.Usuario;
import java.util.Scanner;

/**
 *
 * @author manuel
 */
public class MenuInicio {

    private static final Controlador controlador = new Controlador();

    public static void menu(Usuario usuario) {

        String opcion = "";

        while (!opcion.matches("\\d+")) {
            System.out.println("########## MENU ##########");
            System.out.println("0.  Sair.");
            System.out.println("1.  Ver tódalas mensaxes.");
            System.out.println("2.  Ver mensaxes de usuarios que sigo.");
            System.out.println("3.  Buscar por hashtag ('#palabra').");
            System.out.println("4.  Escribir unha mensaxe.");
            System.out.println("5.  Buscar usuarios.");
            System.out.println("6.  Seguir a usuarios.");
            System.out.println("Selecciona una opción: ");
            Scanner sc = new Scanner(System.in);
            opcion = sc.nextLine();
        }

        switch (opcion) {
            case "1":
                controlador.verTodasAsMensaxes();
                menu(usuario);
                break;
            case "2":
                controlador.verMensaxesDeUsuariosQueSigo(usuario);
                menu(usuario);
                break;
            case "3":
                controlador.buscarHashtag();
                menu(usuario);
                break;
            case "4":
                controlador.escribirMensaxe(usuario);
                menu(usuario);
                break;
            case "5":
                controlador.buscarUsuario();
                menu(usuario);
                break;
            case "6":
                controlador.seguirUsuario(usuario);
                menu(usuario);
                break;
            case "0":
                System.exit(0);
                break;
            default:
                menu(usuario);
                break;
        }
    }

}
