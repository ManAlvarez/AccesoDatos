/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.franquicia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fp.dam.franquicia.funcionalidades.Funcionalidades;
import fp.dam.franquicia.funcionalidades.TitularesXML;
import fp.dam.franquicia.objetos.Cliente;
import fp.dam.franquicia.objetos.Empleado;
import fp.dam.franquicia.objetos.Franquicia;
import fp.dam.franquicia.objetos.Producto;
import fp.dam.franquicia.objetos.Tienda;
import fp.dam.franquicia.objetos.Titular;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

    private static Franquicia franquicia = new Franquicia();
    private static ArrayList<Tienda> tiendas = new ArrayList<>();
    private static ArrayList<Producto> productos = new ArrayList<>();
    private static ArrayList<Empleado> empleados = new ArrayList<>();
    private static ArrayList<Cliente> clientes = new ArrayList<>();

    private static Tienda accionTienda = null;
    private static Producto accionProducto = null;
    private static Empleado accionEmpleado = null;
    private static Cliente accionCliente = null;

    private static boolean tiendaExiste = false;
    private static boolean idProductoExiste = false;
    private static boolean empleadoExiste = false;
    private static boolean clienteExiste = false;

    public void anadirTienda() {
        tiendas.clear();
        tiendaExiste = false;
        String nombreTienda = Funcionalidades.insertarDatosString("Inserta el nombre de la tienda: ");
        tiendas = franquicia.getTiendas();
        if (!tiendas.isEmpty()) {
            for (Tienda tiendaInsertar : tiendas) {
                if (tiendaInsertar.getNombre().equalsIgnoreCase(nombreTienda)) {
                    tiendaExiste = true;
                }
            }
            if (tiendaExiste) {
                System.out.println("La tienda ya existe");
            } else {
                String ciudad = Funcionalidades.insertarDatosString("Inserte la ciudad de la tienda: ");
                franquicia.getTiendas().add(new Tienda(nombreTienda, ciudad));
            }
        } else {
            String ciudad = Funcionalidades.insertarDatosString("Inserte la ciudad de la tienda: ");
            franquicia.getTiendas().add(new Tienda(nombreTienda, ciudad));
        }

    }

    public void eliminarTienda() {
        tiendas.clear();
        accionTienda = null;
        tiendas = franquicia.getTiendas();
        tiendaExiste = false;

        if (!tiendas.isEmpty()) {
            String nombreTienda = Funcionalidades.insertarDatosString("Inserta el nombre de la tienda que necesitas eliminar: ");
            for (Tienda tiendaEliminar : tiendas) {
                if (tiendaEliminar.getNombre().equalsIgnoreCase(nombreTienda)) {
                    tiendaExiste = true;
                    accionTienda = tiendaEliminar;
                }
            }
            if (tiendaExiste) {
                tiendas.remove(accionTienda);
                System.out.println("La tienda se ha eliminado correctamente.");
            } else {
                System.out.println("La tienda no existe.");
            }
        } else {
            System.out.println("No hay tiendas que eliminar.");
        }
    }

    public void anadirProductoTienda() {
        tiendas.clear();
        productos.clear();
        accionTienda = null;
        tiendaExiste = false;
        idProductoExiste = false;
        tiendas = franquicia.getTiendas();

        if (!tiendas.isEmpty()) {
            String nombreTienda = Funcionalidades.insertarDatosString("Inserta la tienda a la que quieres añadir un producto: ");
            for (Tienda tiendaProducto : tiendas) {
                if (tiendaProducto.getNombre().equalsIgnoreCase(nombreTienda)) {
                    tiendaExiste = true;
                    accionTienda = tiendaProducto;
                }
            }
            if (tiendaExiste) {
                int id = Funcionalidades.insertarDatosInt("Inserta el identificador para el producto: ");
                productos = accionTienda.getProductos();
                if (!productos.isEmpty()) {
                    for (Producto productoInsertar : productos) {
                        if (productoInsertar.getIdentificador() == id) {
                            idProductoExiste = true;
                        }
                    }
                    if (idProductoExiste) {
                        System.out.println("El producto ya existe.");
                    } else {
                        String descripcion = Funcionalidades.insertarDatosString("Inserta una descripción para el producto: ");
                        double precio = Funcionalidades.insertarDatosDouble("Inserta el precio del producto: ");
                        int cantidad = Funcionalidades.insertarDatosInt("Inserta la cantidad: ");
                        accionTienda.getProductos().add(new Producto(id, descripcion, precio, cantidad));
                        System.out.println("Producto insertado correctamente.");
                    }

                } else {
                    String descripcion = Funcionalidades.insertarDatosString("Inserta una descripción para el producto: ");
                    double precio = Funcionalidades.insertarDatosDouble("Inserta el precio del producto: ");
                    int cantidad = Funcionalidades.insertarDatosInt("Inserta la cantidad: ");
                    accionTienda.getProductos().add(new Producto(id, descripcion, precio, cantidad));
                    System.out.println("Producto insertado correctamente.");
                }

            } else {
                System.out.println("La tienda no existe.");
            }

        } else {
            System.out.println("No hay tiendas para añadirle productos.");
        }
    }

    public void eliminarProductoTienda() {
        tiendas.clear();
        productos.clear();
        accionTienda = null;
        accionProducto = null;
        idProductoExiste = false;
        tiendaExiste = false;
        tiendas = franquicia.getTiendas();
        if (!tiendas.isEmpty()) {
            String nombreTienda = Funcionalidades.insertarDatosString("Inserta el nombre de la tienda de la que quiere eliminar un producto: ");
            for (Tienda tiendaProductosEliminar : tiendas) {
                if (tiendaProductosEliminar.getNombre().equalsIgnoreCase(nombreTienda)) {
                    tiendaExiste = true;
                    accionTienda = tiendaProductosEliminar;
                }
            }
            if (tiendaExiste) {
                if (!accionTienda.getProductos().isEmpty()) {
                    int idEliminar = Funcionalidades.insertarDatosInt("Inserta el identificador del producto que desea eliminar: ");
                    productos = accionTienda.getProductos();
                    for (Producto productoEliminar : productos) {
                        if (productoEliminar.getIdentificador() == idEliminar) {
                            idProductoExiste = true;
                            accionProducto = productoEliminar;
                        }
                    }
                    if (idProductoExiste) {
                        productos.remove(accionProducto);
                        System.out.println("El productos se ha eliminado correctamente.");
                    } else {
                        System.out.println("El producto no existe.");
                    }
                } else {
                    System.out.println("No hay productos.");
                }
            } else {
                System.out.println("La tienda no existe.");
            }
        } else {
            System.out.println("No hay tiendas para la eliminación de productos.");
        }
    }

    public void anadirEmpleado() {
        tiendas.clear();
        empleados.clear();
        tiendaExiste = false;
        empleadoExiste = false;
        accionTienda = null;
        accionEmpleado = null;
        tiendas = franquicia.getTiendas();

        if (!tiendas.isEmpty()) {
            String nombreTienda = Funcionalidades.insertarDatosString("Inserta la tienda para añadir al nuevo empleado: ");
            for (Tienda tiendaEmpleado : tiendas) {
                if (tiendaEmpleado.getNombre().equalsIgnoreCase(nombreTienda)) {
                    tiendaExiste = true;
                    accionTienda = tiendaEmpleado;
                }
            }
            if (tiendaExiste) {
                String nombreEmpleado = Funcionalidades.insertarDatosString("Inserta el nombre del empleado: ");
                String apellidosEmpleado = Funcionalidades.insertarDatosString("Inserta los apellidos del empleado: ");
                empleados = accionTienda.getEmpleados();
                if (!empleados.isEmpty()) {
                    for (Empleado empleadosTienda : empleados) {
                        if (!empleadosTienda.getNome().equalsIgnoreCase(nombreEmpleado) || !empleadosTienda.getApellidos().equalsIgnoreCase(apellidosEmpleado)) {
                            empleadoExiste = false;
                            accionEmpleado = empleadosTienda;
                        }
                    }
                    if (empleadoExiste) {
                        System.out.println("El empleado ya existe.");
                    } else {
                        accionTienda.getEmpleados().add(new Empleado(nombreEmpleado, apellidosEmpleado));
                        System.out.println("Empleado insertado correctamente.");
                    }
                } else {
                    accionTienda.getEmpleados().add(new Empleado(nombreEmpleado, apellidosEmpleado));
                    System.out.println("Empleado insertado correctamente.");
                }
            } else {
                System.out.println("La tienda no existe");
            }

        } else {
            System.out.println("No hay tiendas para añadir empleados.");
        }
    }

    public void eliminarEmpleado() {
        tiendas.clear();
        empleados.clear();
        tiendaExiste = false;
        empleadoExiste = false;
        accionTienda = null;
        accionEmpleado = null;
        tiendas = franquicia.getTiendas();

        if (!tiendas.isEmpty()) {
            String nombreTienda = Funcionalidades.insertarDatosString("Inserta la tienda en la que desea eliminar el usuario: ");
            for (Tienda tiendaEliminarEmpleado : tiendas) {
                if (tiendaEliminarEmpleado.getNombre().equalsIgnoreCase(nombreTienda)) {
                    tiendaExiste = true;
                    accionTienda = tiendaEliminarEmpleado;
                }
            }
            if (tiendaExiste) {
                if (!accionTienda.getEmpleados().isEmpty()) {
                    String nombreEmpleado = Funcionalidades.insertarDatosString("Inserta en nombre del empleados que desea eliminar");
                    String apellidosEmpleado = Funcionalidades.insertarDatosString("Inserta los apellidos del empleado a aliminar");
                    empleados = accionTienda.getEmpleados();
                    for (Empleado empleadoEliminar : empleados) {
                        if (empleadoEliminar.getNome().equalsIgnoreCase(nombreEmpleado) && empleadoEliminar.getApellidos().equalsIgnoreCase(apellidosEmpleado)) {
                            empleadoExiste = true;
                            accionEmpleado = empleadoEliminar;
                        }
                    }
                    if (empleadoExiste) {
                        empleados.remove(accionEmpleado);
                        System.out.println("El empleado se ha eliminado correctamente.");
                    } else {
                        System.out.println("El empleado no existe.");
                    }
                } else {
                    System.out.println("No existen empleados para eliminar.");
                }

            } else {
                System.out.println("La tienda no existe.");
            }
        } else {
            System.out.println("No existen tiendas para eliminar los empleados.");
        }
    }

    public void anadirCliente() {
        clientes.clear();
        clienteExiste = false;
        String nombreCliente = Funcionalidades.insertarDatosString("Inserta el nombre del nuevo cliente: ");
        String apellidosCliente = Funcionalidades.insertarDatosString("Inserta los apellidos del nuevo cliente: ");
        String email = Funcionalidades.insertarDatosString("Inserta el email del nuevo cliente: ");
        clientes = franquicia.getClientes();
        if (!clientes.isEmpty()) {
            for (Cliente clienteNuevo : clientes) {
                if (clienteNuevo.getEmail().equalsIgnoreCase(email)) {
                    clienteExiste = true;
                }
            }
            if (clienteExiste) {
                System.out.println("El cliente ya existe.");
            } else {
                clientes.add(new Cliente(nombreCliente, apellidosCliente, email));
                System.out.println("El cliente se a añadido correctamente.");
            }
        } else {
            clientes.add(new Cliente(nombreCliente, apellidosCliente, email));
            System.out.println("El cliente se a añadido correctamente.");
        }

    }

    public void eliminarCliente() {
        clientes.clear();
        accionCliente = null;
        clienteExiste = false;
        clientes = franquicia.getClientes();
        if (!clientes.isEmpty()) {
            String emailEliminar = Funcionalidades.insertarDatosString("Inserta el email del cliente que quieres eliminar: ");
            for (Cliente clienteElim : clientes) {
                if (clienteElim.getEmail().equalsIgnoreCase(emailEliminar)) {
                    clienteExiste = true;
                    accionCliente = clienteElim;
                }
            }
            if (clienteExiste) {
                clientes.remove(accionCliente);
                System.out.println("El cliente se ha eliminado correctamente.");
            } else {
                System.out.println("El cliente no existe.");
            }
        } else {
            System.out.println("Non hay clientes para eliminar.");
        }
    }

    public void leerJson() {

        File ficheiro = new File("data.json");

        if (ficheiro.exists()) {
            try {
                FileReader fluxoDatos;
                fluxoDatos = new FileReader(ficheiro);

                BufferedReader buferEntrada = new BufferedReader(fluxoDatos);

                StringBuilder jsonBuilder = new StringBuilder();
                String linea;

                while ((linea = buferEntrada.readLine()) != null) {
                    System.out.println(linea);
                    jsonBuilder.append(linea).append("\n");
                }

                buferEntrada.close();

                String json = jsonBuilder.toString();

                Gson gson = new Gson();
                franquicia = gson.fromJson(json, Franquicia.class);

            } catch (FileNotFoundException e) {
                System.out.println("Non se encontra o arquivo");
            } catch (IOException e) {
                System.out.println("Erro de entrada saída");
            }
        }
    }

    public void escribirJson() {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(franquicia);

        File ficheiro = new File("data.json");

        try {
            FileWriter fluxoDatos = new FileWriter(ficheiro);
            BufferedWriter buferSaida = new BufferedWriter(fluxoDatos);

            buferSaida.write(json);

            buferSaida.close();

        } catch (FileNotFoundException e) {
            System.out.println("Non se encontra o arquivo");
        } catch (IOException e) {
            System.out.println("Erro de entrada saída");
        }
    }
    
    public void copiaSeguridad(){
        
        File archivo = new File("data.json");
        if(archivo.exists()){
            if(archivo.isFile()){
                String nome = archivo.getName();
                if(nome.contains(".json")){               
                    try {
                        // Creamos el flujo de datos de entrada.
                        FileReader flujoEntrada = new FileReader(archivo);
                        // Creamos un buffer de entrada.
                        BufferedReader bufferEntrada = new BufferedReader(flujoEntrada);
                        String datosGuardados="";
                        String linea;
                        while((linea=bufferEntrada.readLine())!= null){
                            datosGuardados += linea+"\n";                           
                        }
                        System.out.println(datosGuardados);
                        // Cerramos el archivo.
                        bufferEntrada.close();
                        // Creamos el archivo nuevo
                        String nombreArchivoNuevo = nome.replace(".json","_backup.json");
                        File nuevoArchivo = new File(archivo.getParent(),nombreArchivoNuevo);
                        // Creamos un flujo de datos de salida.
                        FileWriter flujoSalida = new FileWriter(nuevoArchivo);
                        // Creamos un buffer de datos de salida.
                        BufferedWriter bufferSalida = new BufferedWriter(flujoSalida);
                        // Creamos un array con todas las lineas.
                        String[] lineadatos = datosGuardados.split("\n");
                        for(String lineaAux:lineadatos){
                        bufferSalida.write(lineaAux);
                        bufferSalida.newLine();
                        }
                        //Cerramos el archivo
                        bufferSalida.close();
                        System.out.println("Copia de seguridad completada correctamente.");
                        
                    } catch (IOException e) {
                        System.out.println("Error de entrada y salida");
                    }                       
                }else{
                    System.out.println("No es un .json");
                }
            }else{
                System.out.println("No es un archivo");
            }
        }else{
            System.out.println("El archivo no existe");
        }
    }

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
            
            for(Titular tituloAux: titulares){
                System.out.println("Titular: " + tituloAux.getTitular());
            }
                   
        } catch (SAXException | IOException e) {
            System.out.println("Error al leer el archivo: "+e);
        }
    }
}
