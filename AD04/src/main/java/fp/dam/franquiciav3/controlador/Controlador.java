/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.franquiciav3.controlador;

import fp.dam.franquiciav3.db.ControladorDB;
import fp.dam.franquiciav3.modelo.Cliente;
import fp.dam.franquiciav3.modelo.Empleado;
import fp.dam.franquiciav3.modelo.EmpleadosTiendas;
import fp.dam.franquiciav3.modelo.Producto;
import fp.dam.franquiciav3.modelo.ProductosTiendas;
import fp.dam.franquiciav3.modelo.Provincia;
import fp.dam.franquiciav3.modelo.Provincias;
import fp.dam.franquiciav3.modelo.Tienda;
import fp.dam.franquiciav3.modelo.Titular;
import fp.dam.franquiciav3.utils.Utils;
import fp.dam.franquiciav3.utils.json.EscribirJsonStock;
import fp.dam.franquiciav3.utils.json.LeerJsonProvincias;
import fp.dam.franquiciav3.utils.sax.TitularesXML;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 *
 * @author manuel
 */
public class Controlador {

    private static final Provincias provincias = LeerJsonProvincias.leerJson();
    private static final List<Provincia> provinciasAux = provincias.getProvincias();
    private static final ControladorDB controladordb = new ControladorDB();
    private static final Utils utiles = new Utils();
    private static boolean existeProvincia = false;
    private static boolean existeProducto = false;
    private static boolean existeEmpleado = false;
    private static ProductosTiendas tiendaAccion;

    /**
     * MÉTODOS PARA AÑADIR DATOS A LA BASE DE DATOS.
     * ########################################################################
     */
    /**
     * Método para insertar las provincias en la base de datos.
     */
    public void anadirProvincias() {
        for (Provincia provinciaAux : provinciasAux) {
            controladordb.anadirElemento(provinciaAux);
        }
    }

    /**
     * Método para añadir una tienda en la base de datos.
     */
    public void anadirTienda() {
        controladordb.mostrarTiendas();
        Provincia provinciaAccion = null;
        existeProvincia = false;
        String nombre = utiles.insertarDatosString("Inserta el nombre de la tienda: ");
        controladordb.mostrarProvincias();
        String nombreProvincia = utiles.insertarDatosString("Inserta el nombre de la provincia de la tienda: ");
        for (Provincia provinciaAux : provinciasAux) {
            if (provinciaAux.getNome().equalsIgnoreCase(nombreProvincia.trim())) {
                existeProvincia = true;
                provinciaAccion = provinciaAux;
            }
        }
        if (existeProvincia) {
            String ciudad = utiles.insertarDatosString("Inserta la ciudad de la tienda: ");
            if (!controladordb.existeTienda(nombre, provinciaAccion.getId(), ciudad)) {
                controladordb.anadirElemento(new Tienda(nombre.toUpperCase().trim(), provinciaAccion, ciudad.toUpperCase().trim()));
                System.out.println("La tienda se ha añadido correctamente.");
                controladordb.mostrarTiendas();
            } else {
                System.out.println("La tienda ya existe en la ciudad de esa provincia.");
                utiles.pulsarEnter();
                controladordb.mostrarTiendas();
            }
        } else {
            System.out.println("La provincia no existe.");
            controladordb.mostrarTiendas();
        }
    }

    /**
     * Método para añadir un producto a la base de datos.
     */
    public void anadirProducto() {
        controladordb.mostrarProductos();
        String nombre = utiles.insertarDatosString("Inserta el nombre del producto: ");
        String descripcion = utiles.insertarDatosString("Inserta una breve descripción del producto: ");
        if (!controladordb.existeProducto(nombre, descripcion)) {
            double precio = utiles.insertarDatosDouble("Inserta el precio del producto: ");
            controladordb.anadirElemento(new Producto(nombre.toUpperCase().trim(), descripcion.toUpperCase().trim(), precio));
            System.out.println("El producto se ha añdido correctamente.");
            controladordb.mostrarProductos();
        } else {
            System.out.println("Ya existe un producto con el mismo nombre y descripción.");
            utiles.pulsarEnter();
            controladordb.mostrarProductos();
        }

    }

    /**
     * Método para añadir un cliente a la base de datos.
     */
    public void anadirCliente() {
        controladordb.mostrarClientes();
        String nombre = utiles.insertarDatosString("Inserta el nombre del cliente: ");
        String apellidos = utiles.insertarDatosString("Inserta los apellidos del cliente: ");
        String email = utiles.insertarDatosString("Inserta el email del cliente: ");
        if (!controladordb.existeCliente(email)) {
            controladordb.anadirElemento(new Cliente(nombre.toUpperCase().trim(), apellidos.toUpperCase().trim(), email.toUpperCase().trim()));
            System.out.println("El cliente se ha añadido correctamente.");
            utiles.pulsarEnter();
            controladordb.mostrarClientes();
        } else {
            System.out.println("El cliente ya existe.");
            utiles.pulsarEnter();
            controladordb.mostrarClientes();
        }
    }

    /**
     * Método para añadir un empleado a la base de datos.
     */
    public void anadirEmpleado() {
        controladordb.mostrarEmpleados();
        String nombre = utiles.insertarDatosString("Inserta el nombre del empleado: ");
        String apellidos = utiles.insertarDatosString("Inserta los apellidos del empleado: ");
        String usuario = utiles.insertarDatosString("Inserta el usuario del empleado: ");
        if (!controladordb.existeEmpleado(usuario)) {
            controladordb.anadirElemento(new Empleado(nombre.toUpperCase().trim(), apellidos.toUpperCase().trim(), usuario.toUpperCase().trim()));
            System.out.println("El empleado se ha añadido correctamente.");
            utiles.pulsarEnter();
            controladordb.mostrarEmpleados();
        } else {
            System.out.println("El empleado ya existe.");
            utiles.pulsarEnter();
            controladordb.mostrarEmpleados();
        }
    }

    /**
     * Método para añadir un producto a una tienda.
     */
    public void anadirProductoTienda() {
        existeProducto = false;
        controladordb.mostrarTiendas();
        int id_tienda = utiles.insertarNumeroPositivo("Inserta el id de la tienda a al que quieres añadir un producto: ");
        Tienda tienda = controladordb.selectTienda(id_tienda);
        if (tienda != null) {
            controladordb.mostrarProductos();
            int id_producto = utiles.insertarNumeroPositivo("Inserta el id del producto que quieres añadir a la tienda: ");
            Producto producto = controladordb.selectProducto(id_producto);
            if (producto != null) {
                List<ProductosTiendas> productos = controladordb.selectTiendasEnProductosTienda(tienda);
                if (productos != null) {
                    for (ProductosTiendas productosAux : productos) {
                        if (productosAux.getProducto() == producto) {
                            existeProducto = true;
                        }
                    }
                    if (existeProducto) {
                        System.out.println("El producto ya existe en la tienda.");
                        utiles.pulsarEnter();
                        controladordb.mostrarProductosTienda(tienda);
                    } else {
                        int cantidad = utiles.insertarNumeroPositivo("Inserta la cantidad de stock: ");
                        controladordb.anadirElemento(new ProductosTiendas(producto, tienda, cantidad));
                        System.out.println("El producto se ha añadido a la tienda.");
                        utiles.pulsarEnter();
                        controladordb.mostrarProductosTienda(tienda);
                    }
                } else {
                    int cantidad = utiles.insertarNumeroPositivo("Inserta la cantidad de stock: ");
                    controladordb.anadirElemento(new ProductosTiendas(producto, tienda, cantidad));
                    System.out.println("El producto se ha añadido a la tienda.");
                    utiles.pulsarEnter();
                    controladordb.mostrarProductosTienda(tienda);
                }
            } else {
                System.out.println("El producto no existe.");
            }
        } else {
            System.out.println("La tienda no existe.");
        }
    }

    /**
     * Método para añadir un empleado a una tienda.
     */
    public void anadirEmpleadoTienda() {
        existeEmpleado = false;
        controladordb.mostrarTiendas();
        int id_tienda = utiles.insertarNumeroPositivo("Inserta el id de la tienda a al que quieres añadir un empleado: ");
        Tienda tienda = controladordb.selectTienda(id_tienda);
        if (tienda != null) {
            controladordb.mostrarEmpleados();
            int id_empleado = utiles.insertarNumeroPositivo("Inserta el id del empleado que quieres añadir a la tienda: ");
            Empleado empleado = controladordb.selectEmpleado(id_empleado);
            if (empleado != null) {
                List<EmpleadosTiendas> empleados = controladordb.selectTiendasEnEmpleadosTiendas(tienda);
                if (empleados != null) {
                    for (EmpleadosTiendas empleadosAux : empleados) {
                        if (empleadosAux.getEmpleado() == empleado) {
                            existeEmpleado = true;
                        }
                    }
                    if (existeEmpleado) {
                        int semana = utiles.insertarNumeroSemanal("Inserta la semana que ha trabajado el empleado: ");
                        if (controladordb.existeEmpleado(empleado, semana)) {
                            System.out.println("El usuario ya tiene imputada esa semana.");
                            utiles.pulsarEnter();
                            controladordb.mostrarEmpleadosTienda(tienda);
                        } else {
                            int horas = utiles.insertarNumeroHorasSemanal("Inserta las horas que ha trabajado el empleado en la semana " + semana + ":");
                            controladordb.anadirElemento(new EmpleadosTiendas(tienda, empleado, semana, horas));
                            System.out.println("El empleado se ha añadido a la tienda.");
                            utiles.pulsarEnter();
                            controladordb.mostrarEmpleadosTienda(tienda);
                        }
                    } else {
                        int semana = utiles.insertarNumeroSemanal("Inserta la semana en la cual trabajo el empleado: ");
                        int horas = utiles.insertarNumeroHorasSemanal("Inserta las horas que ha trabajado el empleado en la semana " + semana + ":");
                        controladordb.anadirElemento(new EmpleadosTiendas(tienda, empleado, semana, horas));
                        System.out.println("El empleado se ha añadido a la tienda.");
                        utiles.pulsarEnter();
                        controladordb.mostrarEmpleadosTienda(tienda);
                    }
                } else {
                    int semana = utiles.insertarNumeroSemanal("Inserta la semana en la cual trabajo el empleado: ");
                    int horas = utiles.insertarNumeroHorasSemanal("Inserta las horas que ha trabajado el empleado en la semana " + semana + ":");
                    controladordb.anadirElemento(new EmpleadosTiendas(tienda, empleado, semana, horas));
                    System.out.println("El empleado se ha añadido a la tienda.");
                    utiles.pulsarEnter();
                    controladordb.mostrarEmpleadosTienda(tienda);
                }
            } else {
                System.out.println("El empleado no existe.");
            }
        } else {
            System.out.println("La tienda no existe.");
        }
    }

    /**
     * MÉTODOS PARA ELIMINAR REGISTROS DE UNA TABLA.
     * #########################################################################
     */
    /**
     * Método para eliminar una tienda.
     */
    public void eliminarTienda() {
        controladordb.mostrarTiendas();
        int idTienda = utiles.insertarNumeroPositivo("Inserta el id de la tienda que deseas eliminar: ");
        Tienda tienda = controladordb.selectTienda(idTienda);
        if (tienda != null) {
            String decision = utiles.tomaDecision("Desea eliminar la tienda (SI/NO): ");
            if (decision.trim().equalsIgnoreCase("SI")) {
                List<ProductosTiendas> tiendas = controladordb.selectTiendasEnProductosTienda(tienda);
                if (tiendas != null) {
                    for (ProductosTiendas tiendaAux : tiendas) {
                        controladordb.eliminarElemento(tiendaAux);
                    }
                }
                List<EmpleadosTiendas> tiendasEmpleados = controladordb.selectTiendasEnEmpleadosTiendas(tienda);
                if(tiendasEmpleados != null){
                    for(EmpleadosTiendas tiendaEAux : tiendasEmpleados){
                         controladordb.eliminarElemento(tiendaEAux);
                    }
                }
                controladordb.eliminarElemento(tienda);
                System.out.println("La tienda se ha eliminado correctamente.");
                utiles.pulsarEnter();
                controladordb.mostrarTiendas();
            } else {
                System.out.println("No se ha eliminado la tienda.");
                utiles.pulsarEnter();
                controladordb.mostrarTiendas();
            }
        } else {
            System.out.println("La tienda no existe.");
            utiles.pulsarEnter();
            controladordb.mostrarTiendas();
        }
    }

    /**
     * Método para eliminar un producto de una tienda.
     */
    public void eliminarProductoStockTienda() {
        tiendaAccion = null;
        existeProducto = false;
        controladordb.mostrarTiendas();
        int idTienda = utiles.insertarNumeroPositivo("Inserta el id de la tienda de la que deseas eliminar un producto: ");
        Tienda tienda = controladordb.selectTienda(idTienda);
        if (tienda != null) {
            List<ProductosTiendas> tiendas = controladordb.selectTiendasEnProductosTienda(tienda);
            if (tiendas != null) {
                controladordb.mostrarProductosTienda(tienda);
                int id_producto = utiles.insertarNumeroPositivo("Inserta el id del producto que quieres eliminar: ");
                Producto producto = controladordb.selectProducto(id_producto);
                if (producto != null) {
                    for (ProductosTiendas tiendaAux : tiendas) {
                        if (tiendaAux.getProducto() == producto) {
                            existeProducto = true;
                            tiendaAccion = tiendaAux;
                        }
                    }
                    if (existeProducto) {
                        String decision = utiles.tomaDecision("Desea eliminar el producto de la tienda (SI/NO): ");
                        if (decision.trim().equalsIgnoreCase("SI")) {
                            controladordb.eliminarElemento(tiendaAccion);
                            System.out.println("El producto ha sido eliminado de la tienda.");
                            utiles.pulsarEnter();
                            controladordb.mostrarProductosTienda(tienda);
                        } else {
                            System.out.println("El producto no ha sido eliminado de la tienda.");
                            utiles.pulsarEnter();
                        }
                    } else {
                        System.out.println("El producto indicado no existe en la tienda.");
                        utiles.pulsarEnter();
                        controladordb.mostrarProductosTienda(tienda);
                    }
                } else {
                    System.out.println("El producto no existe.");
                    utiles.pulsarEnter();
                    controladordb.mostrarProductosTienda(tienda);
                }
            } else {
                System.out.println("La tienda no tiene productos.");
            }
        } else {
            System.out.println("La tienda no existe.");
        }
    }

    /**
     * Método para eliminar un producto.
     */
    public void eliminarProducto() {
        controladordb.mostrarProductos();
        int idProducto = utiles.insertarNumeroPositivo("Inserta el id del producto que deseas eliminar: ");
        Producto producto = controladordb.selectProducto(idProducto);
        if (producto != null) {
            String decision = utiles.tomaDecision("Desea eliminar el producto (SI/NO): ");
            if (decision.trim().equalsIgnoreCase("SI")) {
                List<ProductosTiendas> productos = controladordb.selectTiendasEnProductosTienda(producto);
                if (productos != null) {
                    for (ProductosTiendas productoAux : productos) {
                        controladordb.eliminarElemento(productoAux);
                    }
                }
                controladordb.eliminarElemento(producto);
                System.out.println("El producto se ha eliminado correctamente.");
                utiles.pulsarEnter();
                controladordb.mostrarProductos();
            } else {
                System.out.println("No se ha eliminado el producto.");
                utiles.pulsarEnter();
                controladordb.mostrarProductos();
            }
        } else {
            System.out.println("El producto no existe.");
            utiles.pulsarEnter();
            controladordb.mostrarProductos();
        }
    }

    /**
     * Método para eliminar un cliente.
     */
    public void eliminarCliente() {
        controladordb.mostrarClientes();
        int idCliente = utiles.insertarNumeroPositivo("Insertar el id del cliente que deseas eliminar: ");
        Cliente cliente = controladordb.selectCliente(idCliente);
        if (cliente != null) {
            String decision = utiles.tomaDecision("Desea eliminar el producto (SI/NO): ");
            if (decision.trim().equalsIgnoreCase("SI")) {
                controladordb.eliminarElemento(cliente);
                System.out.println("El cliente ha sido eliminado correctamente.");
                utiles.pulsarEnter();
                controladordb.mostrarClientes();
            }
        } else {
            System.out.println("El cliente no existe.");
            utiles.pulsarEnter();
            controladordb.mostrarClientes();
        }
    }

    /**
     * Método para eliminar un empleado.
     */
    public void eliminarEmpleado() {
        controladordb.mostrarEmpleados();
        int idEmplado = utiles.insertarNumeroPositivo("Inserta el id del empleado que deseas eliminar: ");
        Empleado empleado = controladordb.selectEmpleado(idEmplado);
        if (empleado != null) {
            String decision = utiles.tomaDecision("Desea eliminar el producto (SI/NO): ");
            if (decision.trim().equalsIgnoreCase("SI")) {
                List<EmpleadosTiendas> emplados = controladordb.selectEmpleadosEnEmpleadosTiendas(empleado);
                if (emplados != null) {
                    for (EmpleadosTiendas empladoAux : emplados) {
                        controladordb.eliminarElemento(empladoAux);
                    }
                }
                controladordb.eliminarElemento(empleado);
                System.out.println("El empleado se ha eliminado correctamente.");
                utiles.pulsarEnter();
                controladordb.mostrarEmpleados();
            } else {
                System.out.println("No se ha eliminado el empleado.");
                utiles.pulsarEnter();
                controladordb.mostrarEmpleados();
            }
        } else {
            System.out.println("El empleado no existe.");
            utiles.pulsarEnter();
            controladordb.mostrarEmpleados();
        }
    }

    /**
     * Método para eliminar un empleado de una tienda.
     */
    public void eliminarEmpleadoTienda() {
        existeEmpleado = false;
        controladordb.mostrarTiendas();
        int idTienda = utiles.insertarNumeroPositivo("Inserta el id de la tienda de la que deseas eliminar un producto: ");
        Tienda tienda = controladordb.selectTienda(idTienda);
        if (tienda != null) {
            List<EmpleadosTiendas> tiendas = controladordb.selectTiendasEnEmpleadosTiendas(tienda);
            if (tiendas != null) {
                controladordb.mostrarEmpleadosTienda(tienda);
                int id_empleado = utiles.insertarNumeroPositivo("Inserta el id del empleado que quieres eliminar: ");
                Empleado empleado = controladordb.selectEmpleado(id_empleado);
                if (empleado != null) {
                    String decision = utiles.tomaDecision("Desea eliminar el empleado en la tienda (SI/NO): ");
                    if (decision.trim().equalsIgnoreCase("SI")) {
                        List<EmpleadosTiendas> emplados = controladordb.selectEmpleadosEnEmpleadosTiendas(empleado);
                        if (emplados != null) {
                            for (EmpleadosTiendas empladoAux : emplados) {
                                controladordb.eliminarElemento(empladoAux);
                            }
                        }
                        System.out.println("El empleado ha sido eliminado de la tienda.");
                        utiles.pulsarEnter();
                        controladordb.mostrarEmpleadosTienda(tienda);
                    } else {
                        System.out.println("El empleado no ha sido eliminado de la tienda.");
                        utiles.pulsarEnter();
                    }                 
                } else {
                    System.out.println("El empleado no existe.");
                    utiles.pulsarEnter();
                    controladordb.mostrarEmpleadosTienda(tienda);
                }
            } else {
                System.out.println("La tienda no tiene empleados.");
            }
        } else {
            System.out.println("La tienda no existe.");
        }
    }

    /**
     * MÉTODOS PARA MOSTRAR REGISTROS DE UNA TABLA.
     * ########################################################################
     */
    /**
     * Método para mostrar los proudctos de una tienda.
     */
    public void mostrarProductosTienda() {
        controladordb.mostrarTiendas();
        int idTienda = utiles.insertarNumeroPositivo("Inserta el id de la tienda de la que deseas ver sus productos: ");
        Tienda tienda = controladordb.selectTienda(idTienda);
        if (tienda != null) {
            controladordb.mostrarProductosTienda(tienda);
        } else {
            System.out.println("La tienda no existe.");
        }
    }

    /**
     * Método para mostrar el stock de un producto en una determinada tienda.
     */
    public void mostrarStock() {
        tiendaAccion = null;
        existeProducto = false;
        controladordb.mostrarTiendas();
        int idTienda = utiles.insertarNumeroPositivo("Inserta el id de la tienda de la que deseas ver su stock: ");
        Tienda tienda = controladordb.selectTienda(idTienda);
        if (tienda != null) {
            List<ProductosTiendas> tiendas = controladordb.selectTiendasEnProductosTienda(tienda);
            if (tiendas != null) {
                controladordb.mostrarProductosTienda(tienda);
                int id_producto = utiles.insertarNumeroPositivo("Inserta el id del producto al que quieres ver su stock: ");
                Producto producto = controladordb.selectProducto(id_producto);
                if (producto != null) {
                    for (ProductosTiendas tiendaAux : tiendas) {
                        if (tiendaAux.getProducto() == producto) {
                            existeProducto = true;
                            tiendaAccion = tiendaAux;
                        }
                    }
                    if (existeProducto) {
                        System.out.println("La tienda " + tienda.getNombre()
                                + " situada en la provincia de " + tienda.getProvincia().getNome()
                                + " en la ciudad de " + tienda.getCiudad()
                                + " tiene un stock de " + tiendaAccion.getStock()
                                + " unidades de " + tiendaAccion.getProducto().getNombre() + ".");
                    } else {
                        System.out.println("El producto no existe en la tienda.");
                        utiles.pulsarEnter();
                        controladordb.mostrarProductosTienda(tienda);
                    }
                } else {
                    System.out.println("El producto no existe.");
                    utiles.pulsarEnter();
                    controladordb.mostrarProductos();
                }
            } else {
                System.out.println("La tienda no tiene productos.");
            }
        } else {
            System.out.println("La tienda no existe.");
        }
    }

    /**
     * MÉTODOS PARA ACTUALIZAR REGISTROS DE UNA TABLA.
     * #########################################################################
     */
    /**
     * Método para actualizar el stock de un producto en una determinada tienda.
     */
    public void actualizarStock() {
        tiendaAccion = null;
        existeProducto = false;
        controladordb.mostrarTiendas();
        int idTienda = utiles.insertarNumeroPositivo("Inserta el id de la tienda de la que deseas actualizar su stock: ");
        Tienda tienda = controladordb.selectTienda(idTienda);
        if (tienda != null) {
            List<ProductosTiendas> tiendas = controladordb.selectTiendasEnProductosTienda(tienda);
            if (tiendas != null) {
                controladordb.mostrarProductosTienda(tienda);
                int id_producto = utiles.insertarNumeroPositivo("Inserta el id del producto al que quieres actualizar su stock: ");
                Producto producto = controladordb.selectProducto(id_producto);
                if (producto != null) {
                    for (ProductosTiendas tiendaAux : tiendas) {
                        if (tiendaAux.getProducto() == producto) {
                            existeProducto = true;
                            tiendaAccion = tiendaAux;
                        }
                    }
                    if (existeProducto) {
                        int stock = utiles.insertarNumeroPositivo("Inserta el nuevo stock: ");
                        tiendaAccion.setStock(stock);
                        controladordb.updateElement(tiendaAccion);
                        System.out.println("El stock se actualizado correctamente.");
                        controladordb.mostrarProductosTienda(tienda);
                    } else {
                        System.out.println("El producto no está en el stock.");
                        utiles.pulsarEnter();
                        controladordb.mostrarProductosTienda(tienda);
                    }
                } else {
                    System.out.println("El producto no existe.");
                    utiles.pulsarEnter();
                    controladordb.mostrarProductosTienda(tienda);
                }
            } else {
                System.out.println("La tienda no tiene stoks.");
            }
        } else {
            System.out.println("La tienda no existe.");
        }
    }

    /**
     * MÉTODO PARA AÑADIR DATOS A UN ARCHIVO JSON.
     * #########################################################################
     */
    /**
     * Método para crear un json con el stock de los productos en las diferentes
     * tiendas.
     */
    public void insertarStockJson() {
        EscribirJsonStock.escribirJson();
    }

    /**
     * MÉTODO PARA LEER LOS TITULARES DE EL PAÍS EN XML.
     * #########################################################################
     */
    /**
     * Método para leer los titiulares del diario El País.
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
}
