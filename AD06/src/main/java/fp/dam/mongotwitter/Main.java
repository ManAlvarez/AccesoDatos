/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.mongotwitter;

import fp.dam.mongotwitter.bd.Controlador;
import fp.dam.mongotwitter.menus.MenuPrincipal;

/**
 *
 * @author manuel
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    private static final Controlador controlador = new Controlador();

    public static void main(String[] args) {
        // TODO code application logic here
        controlador.confInical();
        MenuPrincipal.menu();

    }

}
