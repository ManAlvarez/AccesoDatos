/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.franquiciav2;

import fp.dam.franquiciav2.funcionalidades.Funcionalidades;
import java.util.Scanner;

/**
 *
 * @author manuel
 */
public class Menu {

    private static Controlador controlador = new Controlador();
    private static Funcionalidades funcion = new Funcionalidades();

    public void menu() {

        String opcion = "";

        while (!opcion.matches("\\d+")) {
            System.out.println("########## MENU ##########");
            System.out.println("0.  Salir.");
            System.out.println("1.  Añadir una tienda.");
            System.out.println("2.  Mostrar las tiendas.");
            System.out.println("3.  Eliminar una tienda.");
            System.out.println("4.  Añadir un producto.");
            System.out.println("5.  Mostrar los productos de la franquicia.");
            System.out.println("6.  Mostrar los productos de una tienda.");
            System.out.println("7.  Añadir un producto a una tienda.");
            System.out.println("8.  Actualizar el stock de un producto en una tienda.");
            System.out.println("9.  Mostrar el stock de un producto en una tienda.");
            System.out.println("10. Eliminar un producto de una determinada tienda.");
            System.out.println("11. Eliminar un producto.");
            System.out.println("12. Añadir un cliente.");
            System.out.println("13. Mostrar los clientes.");
            System.out.println("14. Eliminar un cliente.");
            System.out.println("15. Leer los titulares del periódico EL País.");
            System.out.println("16. Añadir un empleado a la franquicia.");
            System.out.println("17. Eliminar un empleado de la franquicia.");
            System.out.println("18. Añadir las horas semanales de un empleado en una tienda.");
            System.out.println("Selecciona una opción: ");
            Scanner sc = new Scanner(System.in);
            opcion = sc.nextLine();
        }

        switch (opcion) {
            case "1":
                controlador.anadirTienda();
                funcion.pulsarEnter();
                menu();
                break;
            case "2":
                controlador.mostrarTiendas();
                funcion.pulsarEnter();
                menu();
                break;
            case "3":
                controlador.eliminarTienda();
                funcion.pulsarEnter();
                menu();
                break;
            case "4":
                controlador.anadirProducto();
                funcion.pulsarEnter();
                menu();
                break;
            case "5":
                controlador.mostrarProductosFranquicia();
                funcion.pulsarEnter();
                menu();
                break;
            case "6":
                controlador.mostrarProductosTienda();
                funcion.pulsarEnter();
                menu();
                break;
            case "7":
                controlador.anadirProductoTienda();
                funcion.pulsarEnter();
                menu();
                break;
            case "8":
                controlador.actualizarStock();
                funcion.pulsarEnter();
                menu();
                break;
            case "9":
                controlador.mostrarStockProductoTienda();
                funcion.pulsarEnter();
                menu();
                break;
            case "10":
                controlador.eliminarProductoTienda();
                funcion.pulsarEnter();
                menu();
                break;
            case "11":
                controlador.eliminarProducto();
                funcion.pulsarEnter();
                menu();
                break;
            case "12":
                controlador.anadirCliente();
                funcion.pulsarEnter();
                menu();
                break;
            case "13":
                controlador.mostrarClientes();
                funcion.pulsarEnter();
                menu();
                break;
            case "14":
                controlador.eliminarCliente();
                funcion.pulsarEnter();
                menu();
                break;
            case "15":
                controlador.XMLReader();
                funcion.pulsarEnter();
                menu();
                break;
            case "16":
                controlador.anadirEmpleado();
                funcion.pulsarEnter();
                menu();
                break;
            case "17":
                controlador.eliminarEmpleado();
                funcion.pulsarEnter();
                menu();
                break;
            case "18":
                controlador.anadirEmpleadoTienda();
                funcion.pulsarEnter();
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
