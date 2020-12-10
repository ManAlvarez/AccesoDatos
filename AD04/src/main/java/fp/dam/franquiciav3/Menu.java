/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.franquiciav3;

import fp.dam.franquiciav3.controlador.Controlador;
import fp.dam.franquiciav3.db.ControladorDB;
import fp.dam.franquiciav3.utils.Utils;
import java.util.Scanner;

/**
 *
 * @author manuel
 */
public class Menu {

    private static final Controlador controlador = new Controlador();
    private static final ControladorDB controladordb = new ControladorDB();
    private static final Utils util = new Utils();

    public static void menu() {

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
            System.out.println("15. Generar un informe json con el stock de todos los productos en formato JSON.");
            System.out.println("16. Leer los titulares del periódico EL País.");
            System.out.println("17. Añadir un empleado.");
            System.out.println("18. Añadir un empleado a una tienda.");
            System.out.println("19. Eliminar un empleado.");
            System.out.println("20. Eliminar un empleado de una determinada tienda.");
            System.out.println("Selecciona una opción: ");
            Scanner sc = new Scanner(System.in);
            opcion = sc.nextLine();
        }

        switch (opcion) {
            case "1":
                controlador.anadirTienda();
                util.pulsarEnter();
                menu();
                break;
            case "2":
                controladordb.mostrarTiendas();
                util.pulsarEnter();
                menu();
                break;
            case "3":
                controlador.eliminarTienda();
                util.pulsarEnter();
                menu();
                break;
            case "4":
                controlador.anadirProducto();
                util.pulsarEnter();
                menu();
                break;
            case "5":
                controladordb.mostrarProductos();
                util.pulsarEnter();
                menu();
                break;
            case "6":
                controlador.mostrarProductosTienda();
                util.pulsarEnter();
                menu();
                break;
            case "7":
                controlador.anadirProductoTienda();
                util.pulsarEnter();
                menu();
                break;
            case "8":
                controlador.actualizarStock();
                util.pulsarEnter();
                menu();
                break;
            case "9":
                controlador.mostrarStock();
                util.pulsarEnter();
                menu();
                break;
            case "10":
                controlador.eliminarProductoStockTienda();
                util.pulsarEnter();
                menu();
                break;
            case "11":
                controlador.eliminarProducto();
                util.pulsarEnter();
                menu();
                break;
            case "12":
                controlador.anadirCliente();
                util.pulsarEnter();
                menu();
                break;
            case "13":
                controladordb.mostrarClientes();
                util.pulsarEnter();
                menu();
                break;
            case "14":
                controlador.eliminarCliente();
                util.pulsarEnter();
                menu();
                break;
            case "15":
                controlador.insertarStockJson();
                util.pulsarEnter();
                menu();
                break;
            case "16":
                controlador.XMLReader();
                util.pulsarEnter();
                menu();
                break;
            case "17":
                controlador.anadirEmpleado();
                util.pulsarEnter();
                menu();
                break;
            case "18":
                controlador.anadirEmpleadoTienda();
                util.pulsarEnter();
                menu();
                break;
            case "19":
                controlador.eliminarEmpleado();
                util.pulsarEnter();
                menu();
                break;
            case "20":
                controlador.eliminarEmpleadoTienda();
                util.pulsarEnter();
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
