/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.franquicia;

import fp.dam.franquicia.funcionalidades.Funcionalidades;
import java.util.Scanner;

/**
 *
 * @author manuel
 */
public class Menu {

    private static Controlador controlador = new Controlador();

    public void menu() {
        
        String opcion = "";
        
        while (!opcion.matches("\\d+")) {
            System.out.println("########## MENU ##########");
            System.out.println("0. Salir.");
            System.out.println("1. Añadir una tienda.");
            System.out.println("2. Eliminar una tienda.");
            System.out.println("3. Añadir un producto a la tienda.");
            System.out.println("4. Eliminar un producto de la tienda.");
            System.out.println("5. Añadir un empleado a la tienda.");
            System.out.println("6. Eliminar un empleado a la tienda.");
            System.out.println("7. Añadir un cliente.");
            System.out.println("8. Eliminar un cliente.");
            System.out.println("9. Crear copia de seguridad.");
            System.out.println("10. Leer los títulares del periódico El País.");
            System.out.println("Selecciona una opción: ");
            Scanner sc = new Scanner(System.in);
            opcion = sc.nextLine();
        }

        switch (opcion) {
            case "1":
                controlador.leerJson();
                controlador.anadirTienda();
                controlador.escribirJson();
                Funcionalidades.insertarDatosString("Pulsa Enter para continuar....");
                menu();
                break;
            case "2":
                controlador.leerJson();
                controlador.eliminarTienda();
                controlador.escribirJson();
                Funcionalidades.insertarDatosString("Pulsa Enter para continuar....");
                menu();
                break;
            case "3":
                controlador.leerJson();
                controlador.anadirProductoTienda();
                controlador.escribirJson();
                Funcionalidades.insertarDatosString("Pulsa Enter para continuar....");
                menu();
                break;
            case "4":
                controlador.leerJson();
                controlador.eliminarProductoTienda();
                controlador.escribirJson();
                Funcionalidades.insertarDatosString("Pulsa Enter para continuar....");
                menu();
                break;
            case "5":
                controlador.leerJson();
                controlador.anadirEmpleado();
                controlador.escribirJson();
                Funcionalidades.insertarDatosString("Pulsa Enter para continuar....");
                menu();
                break;
            case "6":
                controlador.leerJson();
                controlador.eliminarEmpleado();
                controlador.escribirJson();
                Funcionalidades.insertarDatosString("Pulsa Enter para continuar....");
                menu();
                break;
            case "7":
                controlador.leerJson();
                controlador.anadirCliente();
                controlador.escribirJson();
                Funcionalidades.insertarDatosString("Pulsa Enter para continuar....");
                menu();
                break;
            case "8":
                controlador.leerJson();
                controlador.eliminarCliente();
                controlador.escribirJson();
                Funcionalidades.insertarDatosString("Pulsa Enter para continuar....");
                menu();
                break;
            case "9":
                controlador.copiaSeguridad();
                Funcionalidades.insertarDatosString("Pulsa Enter para continuar....");
                menu();
                break;
            case "10":
                controlador.XMLReader();
                Funcionalidades.insertarDatosString("Pulsa Enter para continuar....");
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
