/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.franquiciav3;

import fp.dam.franquiciav3.controlador.Controlador;

/**
 *
 * @author manuel
 */
public class Main {

    private static final Controlador controlador = new Controlador();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // Creamos las provincias.
        controlador.anadirProvincias();
        Menu.menu();
    }

}
