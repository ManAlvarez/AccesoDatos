/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.franquicia;

/**
 *
 * @author manuel
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    
    private static Menu menu = new Menu();
    private static Controlador contralador = new Controlador();
     
    public static void main(String[] args) {
        // TODO code application logic here 
        contralador.leerJson();
        menu.menu();
    }
    
    
    
    
}
