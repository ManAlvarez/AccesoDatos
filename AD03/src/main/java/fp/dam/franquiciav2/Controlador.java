/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.franquiciav2;

import fp.dam.franquiciav2.db.ControladorDB;
import fp.dam.franquiciav2.db.DriverDB;
import fp.dam.franquiciav2.funcionalidades.Funcionalidades;
import fp.dam.franquiciav2.objetos.Provincia;
import fp.dam.franquiciav2.objetos.Provincias;
import fp.dam.franquiciav2.objetos.Titular;
import fp.dam.franquiciav2.sax.TitularesXML;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author manuel
 */
public class Controlador {

    private static DriverDB driverDB = new DriverDB();
    private static Funcionalidades funcion = new Funcionalidades();
    private static ControladorDB db = new ControladorDB();
    private static Provincias provincias = db.insertarProvinciaAProvincias();
    private static ArrayList<Provincia> provinciasArray = provincias.getProvincias();
    private static Provincia accionProvincia;
    private static boolean existeTienda = false;
    private static boolean existeProvincia = false;

    /**
     * Método que muestra las provincias del archivo json y que estan guardadas
     * en el array.
     */
    public void mostrarProvincias() {
        if (!provinciasArray.isEmpty()) {
            for (Provincia provinciaAux : provinciasArray) {
                System.out.println(provinciaAux.getNome());
            }
        } else {
            System.out.println("Error no se ha cargado el .json de las provincias.");
        }
    }

    /**
     * Método que añade una tienda a la base de datos.
     */
    public void anadirTienda() {
        mostrarTiendas();
        existeProvincia = false;
        accionProvincia = null;
        Connection con = driverDB.connectDatabase();
        String nombre = funcion.insertarDatosString("Inserta el nombre de la tienda: ");
        mostrarProvincias();
        String provincia = funcion.insertarDatosString("Inserta una provincia: ");
        if (!provinciasArray.isEmpty()) {
            for (Provincia provinciaAux : provinciasArray) {
                if (provinciaAux.getNome().equalsIgnoreCase(provincia.trim())) {
                    existeProvincia = true;
                    accionProvincia = provinciaAux;
                }
            }
        }
        if (existeProvincia) {
            String ciudad = funcion.insertarDatosString("Inserta la ciudad: ");
            if (db.existeTienda(con, nombre.trim().toUpperCase(), accionProvincia.getNome().trim().toUpperCase(), ciudad.trim().toUpperCase())) {
                System.out.println("La tienda ya existe.");
                funcion.pulsarEnter();
            } else {
                db.insertTienda(con, nombre.trim().toUpperCase(), accionProvincia.getNome().trim().toUpperCase(), ciudad.trim().toUpperCase());
                System.out.println("La tienda se ha insertado correctamente.");
                funcion.pulsarEnter();
            }
        } else {
            System.out.println("La provincia no existe en la base de datos.");
            funcion.pulsarEnter();
        }
        driverDB.desconnetDatabase(con);
        mostrarTiendas();
    }

    /**
     * Método para mostrar las tiendas.
     */
    public void mostrarTiendas() {
        Connection con = driverDB.connectDatabase();
        db.mostrarTiendas(con);
        driverDB.desconnetDatabase(con);
    }

    /**
     * Método para eliminar una tienda de la base de datos.
     */
    public void eliminarTienda() {
        mostrarTiendas();
        Connection con = driverDB.connectDatabase();
        int idTienda = funcion.insertarNumeroPositivo("Inserta el id de la tienda que desea eliminar: ");
        if (db.existeTienda(con, idTienda)) {
            String decision = funcion.tomaDecision("Desea eliminar la tienda " + idTienda + " (SI/NO): ");
            if (decision.trim().equalsIgnoreCase("SI")) {
                db.deleteTiendaAllProducto(con, idTienda);
                db.deleteTiendaAllEmpleado(con, idTienda);
                db.deleteTienda(con, idTienda);
                System.out.println("La tienda ha sido eliminada correctamente");
                funcion.pulsarEnter();
            } else {
                System.out.println("La tienda no ha sido eliminada.");
                funcion.pulsarEnter();
            }
        } else {
            System.out.println("La tienda no existe.");
            funcion.pulsarEnter();
        }
        driverDB.desconnetDatabase(con);
        mostrarTiendas();
    }

    /**
     * Método para añadir un producto.
     */
    public void anadirProducto() {
        mostrarProductosFranquicia();
        Connection con = driverDB.connectDatabase();
        String nombre = funcion.insertarDatosString("Inserta el nombre del producto: ");
        String descripcion = funcion.insertarDatosString("Inserta la descripcion del producto: ");
        if (!db.existeProducto(con, nombre, descripcion)) {
            double precio = funcion.insertarDatosDouble("Inserta el precio del producto: ");
            db.insertProducto(con, nombre.trim().toUpperCase(), descripcion.trim().toUpperCase(), precio);
            System.out.println("El producto se ha añadido correctamente.");
            funcion.pulsarEnter();
        } else {
            System.out.println("El producto ya existe con el mismo nombre y descripción.");
            funcion.pulsarEnter();
        }
        driverDB.desconnetDatabase(con);
        mostrarProductosFranquicia();
    }

    /**
     * Método para mostrar los productos.
     */
    public void mostrarProductosFranquicia() {
        Connection con = driverDB.connectDatabase();
        db.mostrarProductos(con);
        driverDB.desconnetDatabase(con);
    }

    /**
     * Método para añadir un producto a una tienda determinada.
     */
    public void anadirProductoTienda() {
        mostrarProductosFranquicia();
        Connection con = driverDB.connectDatabase();
        int id_producto = funcion.insertarNumeroPositivo("Inserta el id del producto que desea añadir a la tienda: ");
        if (db.existeProductoFranquicia(con, id_producto)) {
            mostrarTiendas();
            int id_tienda = funcion.insertarNumeroPositivo("Inserta el id de la tienda a la que deseas añadir el producto: ");
            if (db.existeTienda(con, id_tienda)) {
                int stockActual = db.existeProductoTienda(con, id_tienda, id_producto);
                if (stockActual == -1) {
                    int stock = funcion.insertarNumeroPositivo("Inserta la cantidad de stock de ese producto en esa tienda: ");
                    db.insertProductoTienda(con, id_tienda, id_producto, stock);
                    System.out.println("El producto ha sido añadido con éxito.");
                    funcion.pulsarEnter();
                    db.mostrarProductosTienda(con, id_tienda);
                } else {
                    System.out.println("El producto ya existe en tienda indicada.");
                }
            } else {
                System.out.println("La tienda no existe.");
            }
        } else {
            System.out.println("El producto no existe.");
        }
        driverDB.desconnetDatabase(con);
    }

    /**
     * Método para mostrar los productos de una tienda.
     */
    public void mostrarProductosTienda() {
        Connection con = driverDB.connectDatabase();
        mostrarTiendas();
        int idTienda = funcion.insertarNumeroPositivo("Inserta el id de la tienda de la cual deseas ver sus productos: ");
        if (db.existeTienda(con, idTienda)) {
            if (db.existeTiendaProducto(con, idTienda)) {
                db.mostrarProductosTienda(con, idTienda);
            } else {
                System.out.println("No hay productos en esa tienda.");
            }
        }else{
            System.out.println("La tienda no existe.");
        }

        driverDB.desconnetDatabase(con);
    }

    /**
     * Método para actualizar el stock de una tienda.
     */
    public void actualizarStock() {
        mostrarProductosFranquicia();
        Connection con = driverDB.connectDatabase();
        int id_producto = funcion.insertarNumeroPositivo("Inserta el id del producto que desea actualizar su stock: ");
        if (db.existeProductoFranquicia(con, id_producto)) {
            mostrarTiendas();
            int id_tienda = funcion.insertarNumeroPositivo("Inserta el id de la tienda a la que deseas actualizar la cantidad del stock del producto seleccionado: ");
            if (db.existeTienda(con, id_tienda)) {
                int stockActual = db.existeProductoTienda(con, id_tienda, id_producto);
                if (stockActual == -1) {
                    System.out.println("El producto no existe en esa tienda.");
                } else {
                    db.mostrarProductosTienda(con, id_tienda, id_producto);
                    int stock = funcion.insertarNumeroPositivo("Inserta la cantidad de stock a actualizar: ");
                    String decision = funcion.tomaDecision("Desea actualizar el stock del producto " + id_producto + " en la tienda " + id_tienda + " (SI/NO): ");
                    if (decision.trim().equalsIgnoreCase("SI")) {
                        db.updateStock(con, stock, id_tienda, id_producto);
                        System.out.println("Se ha actualizado el stock.");
                        funcion.pulsarEnter();
                        db.mostrarProductosTienda(con, id_tienda);
                    } else {
                        System.out.println("No se ha actualizado el stock.");
                        funcion.pulsarEnter();
                    }
                }
            } else {
                System.out.println("La tienda no existe.");
            }
        } else {
            System.out.println("El producto no existe.");
        }
        driverDB.desconnetDatabase(con);
    }

    /**
     * Método para mostrar el stock de un producto de una determinada tienda.
     */
    public void mostrarStockProductoTienda() {
        mostrarProductosFranquicia();
        Connection con = driverDB.connectDatabase();
        int id_producto = funcion.insertarNumeroPositivo("Inserta el id del producto que desea mostrar su stock: ");
        if (db.existeProductoFranquicia(con, id_producto)) {
            mostrarTiendas();
            int id_tienda = funcion.insertarNumeroPositivo("Inserta el id de la tienda a la que deseas mostrar el stock del producto seleccionado: ");
            if (db.existeTienda(con, id_tienda)) {
                int stock = db.existeProductoTienda(con, id_tienda, id_producto);
                if (stock >= 0) {
                    db.mostrarProductosTienda(con, id_tienda, id_producto);
                } else {
                    System.out.println("No hay ese producto en esa tienda.");
                }
            } else {
                System.out.println("No existe la tienda.");
            }
        } else {
            System.out.println("No existe el producto.");
        }
        driverDB.desconnetDatabase(con);
    }

    /**
     * Método para eliminar un producto de una determinada tienda.
     */
    public void eliminarProductoTienda() {
        mostrarProductosFranquicia();
        Connection con = driverDB.connectDatabase();
        int id_producto = funcion.insertarNumeroPositivo("Inserta el id del producto que desea eliminar: ");
        if (db.existeProductoFranquicia(con, id_producto)) {
            mostrarTiendas();
            int id_tienda = funcion.insertarNumeroPositivo("Inserta el id de la tienda a la que deseas eliminar el producto seleccionado: ");
            if (db.existeTienda(con, id_tienda)) {
                int stock = db.existeProductoTienda(con, id_tienda, id_producto);
                if (stock >= 0) {
                    db.mostrarProductosTienda(con, id_tienda);
                    String decision = funcion.tomaDecision("Desea eliminar el producto " + id_producto + " de la tienda " + id_tienda + " (SI/NO): ");
                    if (decision.trim().equalsIgnoreCase("SI")) {
                        db.deleteProductoTienda(con, id_tienda, id_producto);
                        System.out.println("El producto ha sido eliminado de la tienda.");
                        funcion.pulsarEnter();
                        db.mostrarProductosTienda(con, id_tienda);
                    } else {
                        System.out.println("El producto no ha sido eliminado de la tienda.");
                    }
                } else {
                    System.out.println("No hay ese producto en esa tienda.");
                }
            } else {
                System.out.println("No existe la tienda.");
            }
        } else {
            System.out.println("No existe el producto.");
        }
        driverDB.desconnetDatabase(con);
    }

    /**
     * Método para eliminar un producto.
     */
    public void eliminarProducto() {
        mostrarProductosFranquicia();
        Connection con = driverDB.connectDatabase();
        int idProducto = funcion.insertarNumeroPositivo("Inserta el id del producto que desea eliminar: ");
        if (db.existeProductoFranquicia(con, idProducto)) {
            String decision = funcion.tomaDecision("Desea eliminar el producto " + idProducto + " de la franquicia (SI/NO): ");
            if (decision.trim().equalsIgnoreCase("SI")) {
                db.deleteProductoAll(con, idProducto);
                db.deleteProducto(con, idProducto);
                System.out.println("El producto ha sido eliminado.");
                funcion.pulsarEnter();
                mostrarProductosFranquicia();
            } else {
                System.out.println("El producto no ha sido eliminado.");
            }
        } else {
            System.out.println("El producto no existe.");
        }
        driverDB.desconnetDatabase(con);
    }

    /**
     * Método para añadir un cliente.
     */
    public void anadirCliente() {
        mostrarClientes();
        Connection con = driverDB.connectDatabase();
        String nombre = funcion.insertarDatosString("Inserta el nombre del nuevo cliente: ");
        String apellidos = funcion.insertarDatosString("Inserta los apellidos del cliente nuevo: ");
        String email = funcion.insertarDatosString("Inserta el email del cliente nuevo: ");
        if (db.existeCliente(con, email)) {
            System.out.println("El cliente ya existe.");
        } else {
            db.insertCliente(con, nombre.trim().toUpperCase(), apellidos.trim().toUpperCase(), email.trim());
            System.out.println("El cliente ha sido añadido con éxito.");
            funcion.pulsarEnter();
            mostrarClientes();
        }
        driverDB.desconnetDatabase(con);
    }

    /**
     * Método para mostrar los clientes.
     */
    public void mostrarClientes() {
        Connection con = driverDB.connectDatabase();
        db.mostrarClientes(con);
        driverDB.desconnetDatabase(con);
    }

    /**
     * Método para eliminar un cliente.
     */
    public void eliminarCliente() {
        mostrarClientes();
        Connection con = driverDB.connectDatabase();
        String email = funcion.insertarDatosString("Inserta el email del cliente que deseas eliminar: ");
        if (db.existeCliente(con, email.trim().toUpperCase())) {
            String decision = funcion.tomaDecision("Desea eliminar al cliente " + email + " de la franquicia (SI/NO): ");
            if (decision.trim().equalsIgnoreCase("SI")) {
                db.deleteCliente(con, email.trim().toUpperCase());
                System.out.println("El cliente ha sido eliminado.");
                funcion.pulsarEnter();
                mostrarClientes();
            } else {
                System.out.println("El cliente no ha sido eliminado.");
            }
        } else {
            System.out.println("El cliente no existe.");
        }
        driverDB.desconnetDatabase(con);
    }

    /**
     * Método para leer los titulares del periódico EL País.
     */
    public void XMLReader() {

        XMLReader procesadorXML = null;

        try {

            //Creamos un parseador de texto y le añadimos nuestra clase.
            procesadorXML = XMLReaderFactory.createXMLReader();
            TitularesXML titularesXML = new TitularesXML();
            procesadorXML.setContentHandler(titularesXML);

            //Indicamos donde esta guardado el xml.
            InputSource arquivo = new InputSource("https://feeds.elpais.com/mrss-s/pages/ep/site/elpais.com/portada");
            procesadorXML.parse(arquivo);

            //Imprimimos los datas que leimos del xml.
            ArrayList<Titular> titulares = titularesXML.getTitulares();

            for (Titular tituloAux : titulares) {
                System.out.println("Titular: " + tituloAux.getTitular());
            }

        } catch (SAXException | IOException e) {
            System.out.println("Error al leer el archivo: " + e);
        }
    }

    /**
     * Método para añadir un empleado a la base de datos.
     */
    public void anadirEmpleado() {
        mostrarEmpleados();
        Connection con = driverDB.connectDatabase();
        String nombre = funcion.insertarDatosString("Inserta el nombre del nuevo empleado: ");
        String apellidos = funcion.insertarDatosString("Inserta los apellidos del empleado nuevo: ");
        if (db.existeEmpleado(con, nombre.trim().toUpperCase(), apellidos.trim().toUpperCase())) {
            System.out.println("El empleado ya existe.");
            funcion.pulsarEnter();
        } else {
            db.insertEmpleado(con, nombre.trim().toUpperCase(), apellidos.trim().toUpperCase());
            System.out.println("El cliente ha sido añadido con éxito.");
            funcion.pulsarEnter();
        }
        driverDB.desconnetDatabase(con);
        mostrarEmpleados();
    }

    /**
     * Método para mostrar los empleados de la base de datos.
     */
    public void mostrarEmpleados() {
        Connection con = driverDB.connectDatabase();
        db.mostrarEmpleados(con);
        driverDB.desconnetDatabase(con);
    }

    /**
     * Método para eliminar un empleado de la base de datos.
     */
    public void eliminarEmpleado() {
        mostrarEmpleados();
        Connection con = driverDB.connectDatabase();
        int idEmpleado = funcion.insertarNumeroPositivo("Inserta el id del empleado que deseas eliminar.");
        if (db.existeEmpleado(con, idEmpleado)) {
            String decision = funcion.tomaDecision("Desea eliminar al empleado " + idEmpleado + " de la franquicia (SI/NO): ");
            if (decision.trim().equalsIgnoreCase("SI")) {
                db.deleteEmpleadoAll(con, idEmpleado);
                db.deleteEmpleado(con, idEmpleado);
                System.out.println("El empleado ha sido eliminado.");
                funcion.pulsarEnter();
                mostrarEmpleados();
            } else {
                System.out.println("El empleado no ha sido eliminado.");
            }
        } else {
            System.out.println("El empleado no existe.");
        }
        driverDB.desconnetDatabase(con);
    }

    /**
     * Método para añadir un empleado a una tienda.
     */
    public void anadirEmpleadoTienda() {
        mostrarEmpleados();
        Connection con = driverDB.connectDatabase();
        int id_empleado = funcion.insertarNumeroPositivo("Inserta el id del empleado que desea añadir a la tienda: ");
        if (db.existeEmpleado(con, id_empleado)) {
            mostrarTiendas();
            int id_tienda = funcion.insertarNumeroPositivo("Inserta el id de la tienda a la que deseas añadir el empleado: ");
            if (db.existeTienda(con, id_tienda)) {
                db.mostrarHorasSemanaEmpleadosTienda(con, id_tienda);
                int semana = funcion.insertarNumeroSemanal("Inserta la semana a la que deseas añadir las horas que a trabajado el empleado en la tienda: ");
                int horas = funcion.insertarNumeroHorasSemanal("Inserta las horas semanales que ha trabajado el empleado en la tienda: ");
                int horasActuales = db.existeEmpladoTiendaSemana(con, id_tienda, id_empleado, semana);
                if (horasActuales == -1) {
                    db.insertEmpleadoTienda(con, id_tienda, id_empleado, semana, horas);
                    System.out.println("El empleado ha sido añadido con éxito");
                    funcion.pulsarEnter();
                    db.mostrarHorasSemanaEmpleadosTienda(con, id_tienda);
                } else {
                    String decision = funcion.tomaDecision("El empleado " + id_empleado + " ya tiene imputado las horas semanales en esa tienda deseas actualizarlas (SI/NO):");
                    if (decision.trim().equalsIgnoreCase("SI")) {
                        db.updateHoras(con, horas, id_tienda, id_empleado, semana);                      
                        funcion.pulsarEnter();
                        db.mostrarHorasSemanaEmpleadosTienda(con, id_tienda);
                    } else {
                        System.out.println("Las horas semanales del empleado no han sido actualizadas");
                    }
                }
            } else {
                System.out.println("La tienda no existe.");
            }
        } else {
            System.out.println("El empleado no existe.");
        }
        driverDB.desconnetDatabase(con);
    }

}
