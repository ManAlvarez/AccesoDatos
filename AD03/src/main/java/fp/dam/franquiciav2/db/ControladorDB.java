/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.franquiciav2.db;

import fp.dam.franquiciav2.objetos.Provincia;
import fp.dam.franquiciav2.objetos.Provincias;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author manuel
 */
public class ControladorDB {

    /**
     * ATRIBUTOS PARA LA CREACION DE LA BASE DE DATOS.
     * #############################################################################################################
     */
    //Tablas.
    private static final String TABLA_PROVINCIAS = "provincias";
    private static final String TABLA_TIENDAS = "tiendas";
    private static final String TABLA_PRODUCTOS = "productos";
    private static final String TABLA_EMPLEADOS = "empleados";
    private static final String TABLA_CLIENTES = "clientes";
    private static final String TABLA_PRODUCTOS_TIENDA = "productos_tienda";
    private static final String TABLA_EMPLEADOS_TIENDA = "empleados_tienda";

    //Campos.
    private static final String CAMPO_ID = "id";
    private static final String CAMPO_NOMBRE = "nombre";
    private static final String CAMPO_APELLIDOS = "apellidos";
    private static final String CAMPO_EMAIL = "email";
    private static final String CAMPO_PROVINCIA = "provincia";
    private static final String CAMPO_CIUDAD = "ciudad";
    private static final String CAMPO_DESCRIPCION = "descripcion";
    private static final String CAMPO_PRECIO = "precio";
    private static final String CAMPO_STOCK = "stock";
    private static final String CAMPO_HORAS = "horas";
    private static final String CAMPO_SEMANA = "semana";
    private static final String CAMPO_ID_TIENDA = "id_tienda";
    private static final String CAMPO_ID_PRODUCTO = "id_producto";
    private static final String CAMPO_ID_EMPLEADO = "id_empleado";

    //Sentencias.
    private static final String[] SQL_TABLAS = {
        "CREATE TABLE IF NOT EXISTS " + TABLA_PROVINCIAS + "("
        + CAMPO_ID + " INTEGER PRIMARY KEY UNIQUE,"
        + CAMPO_NOMBRE + " TEXT NOT NULL UNIQUE"
        + ");",
        "CREATE TABLE IF NOT EXISTS " + TABLA_TIENDAS + "("
        + CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + CAMPO_NOMBRE + " TEXT NOT NULL,"
        + CAMPO_PROVINCIA + " TEXT NOT NULL,"
        + CAMPO_CIUDAD + " TEXT NOT NULL"
        + ");",
        "CREATE TABLE IF NOT EXISTS " + TABLA_PRODUCTOS + "("
        + CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + CAMPO_NOMBRE + " TEXT NOT NULL,"
        + CAMPO_DESCRIPCION + " TEXT NOT NULL,"
        + CAMPO_PRECIO + " REAL NOT NULL"
        + ");",
        "CREATE TABLE IF NOT EXISTS " + TABLA_EMPLEADOS + "("
        + CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + CAMPO_NOMBRE + " TEXT NOT NULL,"
        + CAMPO_APELLIDOS + " TEXT NOT NULL"
        + ");",
        "CREATE TABLE IF NOT EXISTS " + TABLA_CLIENTES + "("
        + CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + CAMPO_NOMBRE + " TEXT NOT NULL,"
        + CAMPO_APELLIDOS + " TEXT NOT NULL,"
        + CAMPO_EMAIL + " TEXT NOT NULL"
        + ");",
        "CREATE TABLE IF NOT EXISTS " + TABLA_PRODUCTOS_TIENDA + "("
        + CAMPO_ID_TIENDA + " INTEGER NOT NULL,"
        + CAMPO_ID_PRODUCTO + " INTEGER NOT NULL,"
        + CAMPO_STOCK + " INTEGER NOT NULL,"
        + "PRIMARY KEY (" + CAMPO_ID_TIENDA + "," + CAMPO_ID_PRODUCTO + "),"
        + "FOREIGN KEY (" + CAMPO_ID_TIENDA + ") REFERENCES " + TABLA_TIENDAS + " (" + CAMPO_ID + ") ON DELETE CASCADE ON UPDATE CASCADE,"
        + "FOREIGN KEY (" + CAMPO_ID_PRODUCTO + ") REFERENCES " + TABLA_PRODUCTOS + " (" + CAMPO_ID + ") ON DELETE CASCADE ON UPDATE CASCADE"
        + ");",
        "CREATE TABLE IF NOT EXISTS " + TABLA_EMPLEADOS_TIENDA + "("
        + CAMPO_ID_TIENDA + " INTEGER NOT NULL,"
        + CAMPO_ID_EMPLEADO + " INTEGER NOT NULL,"
        + CAMPO_SEMANA + " INTEGER NOT NULL,"
        + CAMPO_HORAS + " INTEGER NOT NULL,"
        + "FOREIGN KEY (" + CAMPO_ID_TIENDA + ") REFERENCES " + TABLA_TIENDAS + " (" + CAMPO_ID + ") ON DELETE CASCADE ON UPDATE CASCADE,"
        + "FOREIGN KEY (" + CAMPO_ID_EMPLEADO + ") REFERENCES " + TABLA_EMPLEADOS + " (" + CAMPO_ID + ") ON DELETE CASCADE ON UPDATE CASCADE"
        + ");"
    };

    /**
     * ATRIBUTOS PARA LA INSERCION DE DATOS EN LA BASE DE DATOS.
     * #########################################################################
     */
    private static final String SQL_INSERT_PROVINCIA = "INSERT INTO " + TABLA_PROVINCIAS
            + "(" + CAMPO_ID + "," + CAMPO_NOMBRE + ")"
            + " VALUES(?, ?);";

    private static final String SQL_INSERT_TIENDA = "INSERT INTO " + TABLA_TIENDAS
            + "(" + CAMPO_NOMBRE + "," + CAMPO_PROVINCIA + "," + CAMPO_CIUDAD + ")"
            + " VALUES(?, ?, ?);";

    private static final String SQL_INSERT_PRODUCTO = "INSERT INTO " + TABLA_PRODUCTOS
            + "(" + CAMPO_NOMBRE + "," + CAMPO_DESCRIPCION + "," + CAMPO_PRECIO + ")"
            + " VALUES(?, ?, ?);";

    private static final String SQL_INSERT_EMPLEADO = "INSERT INTO " + TABLA_EMPLEADOS
            + "(" + CAMPO_NOMBRE + "," + CAMPO_APELLIDOS + ")"
            + " VALUES(?, ?);";

    private static final String SQL_INSERT_CLIENTE = "INSERT INTO " + TABLA_CLIENTES
            + "(" + CAMPO_NOMBRE + "," + CAMPO_APELLIDOS + "," + CAMPO_EMAIL + ")"
            + " VALUES(?, ?, ?);";

    private static final String SQL_INSERT_PRODUCTOS_TIENDA = "INSERT INTO " + TABLA_PRODUCTOS_TIENDA
            + "(" + CAMPO_ID_TIENDA + "," + CAMPO_ID_PRODUCTO + "," + CAMPO_STOCK + ")"
            + " VALUES(?, ?, ?);";

    private static final String SQL_INSERT_EMPLEADOS_TIENDA = "INSERT INTO " + TABLA_EMPLEADOS_TIENDA
            + "(" + CAMPO_ID_TIENDA + "," + CAMPO_ID_EMPLEADO + "," + CAMPO_SEMANA + "," + CAMPO_HORAS + ")"
            + " VALUES(?, ?, ?, ?);";

    /**
     * ATRIBUTOS PARA LA CONSULTA DE LA BASE DE DATOS.
     * #########################################################################
     */
    private static final String SQL_PROVINCIAS = "SELECT * FROM " + TABLA_PROVINCIAS + ";";
    private static final String SQL_TIENDAS = "SELECT * FROM " + TABLA_TIENDAS + ";";
    private static final String SQL_PRODUCTOS = "SELECT * FROM " + TABLA_PRODUCTOS + ";";
    private static final String SQL_EMPLEADOS = "SELECT * FROM " + TABLA_EMPLEADOS + ";";
    private static final String SQL_CLIENTES = "SELECT * FROM " + TABLA_CLIENTES + ";";
    private static final String SQL_PRODUCTOS_TIENDA = "SELECT * FROM " + TABLA_PRODUCTOS_TIENDA + ";";
    private static final String SQL_EMPLEADOS_TIENDA = "SELECT * FROM " + TABLA_EMPLEADOS_TIENDA + ";";

    /**
     * ATRIBUTOS PARA LA ACTUALIZACION DE DATOS DE LA BASE DE DATOS.
     * #########################################################################
     */
    private static final String UPDATE_STOCK = "UPDATE " + TABLA_PRODUCTOS_TIENDA
            + " SET " + CAMPO_STOCK + " = ?"
            + " WHERE " + CAMPO_ID_TIENDA + " = ?"
            + " AND " + CAMPO_ID_PRODUCTO + " = ?;";

    private static final String UPDATE_HORAS = "UPDATE " + TABLA_EMPLEADOS_TIENDA
            + " SET " + CAMPO_HORAS + " = ?"
            + " WHERE " + CAMPO_ID_TIENDA + " = ?"
            + " AND " + CAMPO_ID_EMPLEADO + " = ?"
            + " AND " + CAMPO_SEMANA + " = ?;";

    /**
     * ATRIBUTOS PARA LA ELIMINACION DE DATOS DE LA BASE DE DATOS.
     * #########################################################################
     */
    private static final String DELETE_TIENDA = "DELETE FROM " + TABLA_TIENDAS + " WHERE " + CAMPO_ID + " = ?";
    private static final String DELETE_TIENDA_ALL_PRODUCTOS = "DELETE FROM " + TABLA_PRODUCTOS_TIENDA + " WHERE " + CAMPO_ID_TIENDA + " = ?";
    private static final String DELETE_TIENDA_ALL_EMPLEADOS = "DELETE FROM " + TABLA_EMPLEADOS_TIENDA + " WHERE " + CAMPO_ID_TIENDA + " = ?";
    private static final String DELETE_PRODUCTO = "DELETE FROM " + TABLA_PRODUCTOS + " WHERE " + CAMPO_ID + " = ?";
    private static final String DELETE_PRODUCTO_ALL = "DELETE FROM " + TABLA_PRODUCTOS_TIENDA + " WHERE " + CAMPO_ID_PRODUCTO + " = ?";
    private static final String DELETE_PRODUCTO_TIENDA = "DELETE FROM " + TABLA_PRODUCTOS_TIENDA
            + " WHERE " + CAMPO_ID_TIENDA + " = ?"
            + " AND " + CAMPO_ID_PRODUCTO + " = ?;";
    private static final String DELETE_CLIENTE = "DELETE FROM " + TABLA_CLIENTES + " WHERE " + CAMPO_EMAIL + " = ?";
    private static final String DELETE_EMPLEADO = "DELETE FROM " + TABLA_EMPLEADOS + " WHERE " + CAMPO_ID + " = ?";
    private static final String DELETE_EMPLEADO_ALL = "DELETE FROM " + TABLA_EMPLEADOS_TIENDA + " WHERE " + CAMPO_ID_EMPLEADO + " = ?";

    /**
     * ATRIBUTOS NECESARIOS PARA LA CLASE.
     * #########################################################################
     */
    private static final DriverDB driverDB = new DriverDB();
    private static final Provincias provincias = new Provincias();

    /**
     * CREAR BASE DE DATOS Y TABLAS.
     * #########################################################################
     */
    /**
     * Método que crear la base de datos.
     */
    public void crearDB() {
        driverDB.createDatabase();
        Connection con = driverDB.connectDatabase();
        try {
            Statement stmt = con.createStatement();
            for (String sqlAux : SQL_TABLAS) {
                stmt.execute(sqlAux);
            }
            System.out.println("Las tablas han sido creadas correctamente.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        driverDB.desconnetDatabase(con);
    }

    /**
     * INSERTAR DATOS EN TABLAS.
     * #########################################################################
     */
    /**
     * Método para inserta los datos de las provincias en la base de datos.
     *
     * @param con
     * @param id
     * @param nome
     */
    public void insertProvincia(Connection con, int id, String nombre) {
        try {
            PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_PROVINCIA);
            pstmt.setInt(1, id);
            pstmt.setString(2, nombre.trim().toUpperCase());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Método para insertar los datos de la tienda en la base de datos.
     *
     * @param con
     * @param nombre
     * @param provincia
     * @param ciudad
     */
    public void insertTienda(Connection con, String nombre, String provincia, String ciudad) {
        try {
            PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_TIENDA);
            pstmt.setString(1, nombre.trim().toUpperCase());
            pstmt.setString(2, provincia.trim().toUpperCase());
            pstmt.setString(3, ciudad.trim().toUpperCase());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Método para insertar un producto en la base de datos.
     *
     * @param con
     * @param nombre
     * @param descripcion
     * @param precio
     */
    public void insertProducto(Connection con, String nombre, String descripcion, double precio) {
        try {
            PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_PRODUCTO);
            pstmt.setString(1, nombre.trim().toUpperCase());
            pstmt.setString(2, descripcion.trim().toUpperCase());
            pstmt.setDouble(3, precio);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Método para insertar el empleado en la base de datos.
     *
     * @param con
     * @param nombre
     * @param apellidos
     */
    public void insertEmpleado(Connection con, String nombre, String apellidos) {
        try {
            PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_EMPLEADO);
            pstmt.setString(1, nombre.trim().toUpperCase());
            pstmt.setString(2, apellidos.trim().toUpperCase());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Método para insertar al cliente en la base de datos.
     *
     * @param con
     * @param nombre
     * @param apellidos
     * @param email
     */
    public void insertCliente(Connection con, String nombre, String apellidos, String email) {
        try {
            PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_CLIENTE);
            pstmt.setString(1, nombre.trim().toUpperCase());
            pstmt.setString(2, apellidos.trim().toUpperCase());
            pstmt.setString(3, email.trim().toLowerCase());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Método para insertar un producto en una tienda.
     *
     * @param con
     * @param tienda
     * @param producto
     * @param stock
     */
    public void insertProductoTienda(Connection con, int tienda, int producto, int stock) {
        try {
            PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_PRODUCTOS_TIENDA);
            pstmt.setInt(1, tienda);
            pstmt.setInt(2, producto);
            pstmt.setInt(3, stock);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Método para insertar un empleado en una tienda.
     *
     * @param con
     * @param tienda
     * @param empleado
     * @param semana
     * @param horas
     */
    public void insertEmpleadoTienda(Connection con, int tienda, int empleado, int semana, int horas) {
        try {
            PreparedStatement pstmt = con.prepareStatement(SQL_INSERT_EMPLEADOS_TIENDA);
            pstmt.setInt(1, tienda);
            pstmt.setInt(2, empleado);
            pstmt.setInt(3, semana);
            pstmt.setInt(4, horas);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * ACTUALIZAR LOS DATOS DE UNA TABLA.
     * #########################################################################
     */
    /**
     * Método para actualizar el stock de un producto en una tienda.
     *
     * @param con
     * @param newStock
     * @param idTienda
     * @param idProducto
     */
    public void updateStock(Connection con, int newStock, int idTienda, int idProducto) {
        try {
            PreparedStatement pstmt = con.prepareStatement(UPDATE_STOCK);
            pstmt.setInt(1, newStock);
            pstmt.setInt(2, idTienda);
            pstmt.setInt(3, idProducto);
            pstmt.executeUpdate();
            System.out.println("El Stock ha sido actualizado con éxito.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Método para actualizar las horas semanales de un empleado en una tienda.
     *
     * @param con
     * @param newHoras
     * @param idTienda
     * @param idEmpleado
     * @param semana
     */
    public void updateHoras(Connection con, int newHoras, int idTienda, int idEmpleado, int semana) {
        try {
            PreparedStatement pstmt = con.prepareStatement(UPDATE_HORAS);
            pstmt.setInt(1, newHoras);
            pstmt.setInt(2, idTienda);
            pstmt.setInt(3, idEmpleado);
            pstmt.setInt(4, semana);
            pstmt.executeUpdate();
            System.out.println("Las horas han sido actualizadas con éxito.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * MOSTRAR DATOS DE LAS TABLAS.
     * #########################################################################
     */
    /**
     * Método para mostrar las tiendas que hay en la base de datos.
     *
     * @param con
     */
    public void mostrarTiendas(Connection con) {
        try {
            System.out.println("TIENDAS: ");
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_TIENDAS);
            while (rs.next()) {
                int id = rs.getInt(CAMPO_ID);
                String nombre = rs.getString(CAMPO_NOMBRE);
                String provincia = rs.getString(CAMPO_PROVINCIA);
                String ciudad = rs.getString(CAMPO_CIUDAD);
                System.out.println("ID TIENDA: " + id + " | NOMBRE: " + nombre + " | PROVINCIA: " + provincia + " | CIUDAD: " + ciudad);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Método para mostrar los productos que hay en la base de datos.
     *
     * @param con
     */
    public void mostrarProductos(Connection con) {
        try {
            System.out.println("PRODUCTOS: ");
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_PRODUCTOS);
            while (rs.next()) {
                int id = rs.getInt(CAMPO_ID);
                String nombre = rs.getString(CAMPO_NOMBRE);
                String descripcion = rs.getString(CAMPO_DESCRIPCION);
                double precio = rs.getDouble(CAMPO_PRECIO);
                System.out.println("ID PRODUCTO: " + id + " | NOMBRE: " + nombre + " | DESCRIPCION: " + descripcion + " | PRECIO: " + precio);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Método para mostrar los productos de una tienda.
     *
     * @param con
     * @param id
     */
    public void mostrarProductosTienda(Connection con, int id) {

        try {
            System.out.println("PRODUCTOS EN TIENDA: ");
            String nombreTienda = "";
            String provinciaTienda = "";
            String ciudadTienda = "";
            String nombreProducto = "";
            String descripcionProducto = "";
            int stock = -1;
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_PRODUCTOS_TIENDA);
            while (rs.next()) {
                int id_tienda_producto = rs.getInt(CAMPO_ID_TIENDA);
                if (id_tienda_producto == id) {
                    int id_producto_tienda = rs.getInt(CAMPO_ID_PRODUCTO);
                    stock = rs.getInt(CAMPO_STOCK);
                    Statement statement2 = con.createStatement();
                    ResultSet rs2 = statement2.executeQuery(SQL_TIENDAS);
                    while (rs2.next()) {
                        int id_tienda = rs2.getInt(CAMPO_ID);
                        if (id_tienda == id_tienda_producto) {
                            nombreTienda = rs2.getString(CAMPO_NOMBRE);
                            provinciaTienda = rs2.getString(CAMPO_PROVINCIA);
                            ciudadTienda = rs2.getString(CAMPO_CIUDAD);
                        }
                    }
                    Statement statement3 = con.createStatement();
                    ResultSet rs3 = statement3.executeQuery(SQL_PRODUCTOS);
                    while (rs3.next()) {
                        int id_producto = rs3.getInt(CAMPO_ID);
                        if (id_producto == id_producto_tienda) {
                            nombreProducto = rs3.getString(CAMPO_NOMBRE);
                            descripcionProducto = rs3.getString(CAMPO_DESCRIPCION);
                        }
                    }
                    System.out.println("NOMBRE TIENDA: " + nombreTienda + " | PROVINCIA: " + provinciaTienda + " | CIUDAD: " + ciudadTienda
                            + " | NOMBRE PRODUCTO: " + nombreProducto + " | DESCRIPCION: " + descripcionProducto + " | STOCK: " + stock);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Método para mostrar los productos de una tienda.
     *
     * @param con
     * @param idtienda
     * @param idProducto
     */
    public void mostrarProductosTienda(Connection con, int idtienda, int idProducto) {

        try {
            System.out.println("PRODUCTOS EN TIENDA: ");
            String nombreTienda = "";
            String provinciaTienda = "";
            String ciudadTienda = "";
            String nombreProducto = "";
            String descripcionProducto = "";
            int stock = -1;
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_PRODUCTOS_TIENDA);
            while (rs.next()) {
                int id_tienda = rs.getInt(CAMPO_ID_TIENDA);
                if (id_tienda == idtienda) {
                    int id_producto = rs.getInt(CAMPO_ID_PRODUCTO);
                    if (id_producto == idProducto) {
                        stock = rs.getInt(CAMPO_STOCK);
                        Statement statement1 = con.createStatement();
                        ResultSet rs1 = statement1.executeQuery(SQL_TIENDAS);
                        while (rs1.next()) {
                            int id_tienda_tienda = rs1.getInt(CAMPO_ID);
                            if (id_tienda_tienda == id_tienda) {
                                nombreTienda = rs1.getString(CAMPO_NOMBRE);
                                provinciaTienda = rs1.getString(CAMPO_PROVINCIA);
                                ciudadTienda = rs1.getString(CAMPO_CIUDAD);
                            }
                        }
                        Statement statement2 = con.createStatement();
                        ResultSet rs2 = statement2.executeQuery(SQL_PRODUCTOS);
                        while (rs2.next()) {
                            int id_producto_producto = rs2.getInt(CAMPO_ID);
                            if (id_producto_producto == idProducto) {
                                nombreProducto = rs2.getString(CAMPO_NOMBRE);
                                descripcionProducto = rs2.getString(CAMPO_DESCRIPCION);
                            }
                        }
                        System.out.println("NOMBRE TIENDA: " + nombreTienda + " | PROVINCIA: " + provinciaTienda + " | CIUDAD: " + ciudadTienda
                                + " | NOMBRE PRODUCTO: " + nombreProducto + " | DESCRIPCION: " + descripcionProducto + " | STOCK: " + stock);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Método para mostrar los clientes de la base de datos.
     *
     * @param con
     */
    public void mostrarClientes(Connection con) {
        try {
            System.out.println("CLIENTES: ");
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_CLIENTES);
            while (rs.next()) {
                int id = rs.getInt(CAMPO_ID);
                String nombre = rs.getString(CAMPO_NOMBRE);
                String apellidos = rs.getString(CAMPO_APELLIDOS);
                String email = rs.getString(CAMPO_EMAIL);
                System.out.println("ID CLIENTE: " + id + " | NOMBRE: " + nombre + " | APELLIDOS: " + apellidos + " | EMAIL: " + email);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Método para mostrar los empleados de la base de datos.
     *
     * @param con
     */
    public void mostrarEmpleados(Connection con) {
        try {
            System.out.println("EMPLEADOS: ");
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_EMPLEADOS);
            while (rs.next()) {
                int id = rs.getInt(CAMPO_ID);
                String nombre = rs.getString(CAMPO_NOMBRE);
                String apellidos = rs.getString(CAMPO_APELLIDOS);
                System.out.println("ID EMPLEADO: " + id + " | NOMBRE: " + nombre + " | APELLIDOS: " + apellidos);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Método para mostrar los empleados de una tienda, la semanas y las horas
     * que trabajo.
     *
     * @param con
     * @param id
     */
    public void mostrarHorasSemanaEmpleadosTienda(Connection con, int id) {
        try {
            System.out.println("EMPLEADOS EN TIENDA: ");
            String nombreTienda = "";
            String provinciaTienda = "";
            String ciudadTienda = "";
            String nombreEmpleado = "";
            String apellidosEmpleado = "";
            int semana = -1;
            int horas = -1;
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_EMPLEADOS_TIENDA);
            while (rs.next()) {
                int id_tienda_empleado = rs.getInt(CAMPO_ID_TIENDA);
                if (id_tienda_empleado == id) {
                    int id_empleado_tienda = rs.getInt(CAMPO_ID_EMPLEADO);
                    semana = rs.getInt(CAMPO_SEMANA);
                    horas = rs.getInt(CAMPO_HORAS);
                    Statement statement2 = con.createStatement();
                    ResultSet rs2 = statement2.executeQuery(SQL_TIENDAS);
                    while (rs2.next()) {
                        int id_tienda = rs2.getInt(CAMPO_ID);
                        if (id_tienda == id_tienda_empleado) {
                            nombreTienda = rs2.getString(CAMPO_NOMBRE);
                            provinciaTienda = rs2.getString(CAMPO_PROVINCIA);
                            ciudadTienda = rs2.getString(CAMPO_CIUDAD);
                        }
                    }
                    Statement statement3 = con.createStatement();
                    ResultSet rs3 = statement3.executeQuery(SQL_EMPLEADOS);
                    while (rs3.next()) {
                        int id_empleado = rs3.getInt(CAMPO_ID);
                        if (id_empleado == id_empleado_tienda) {
                            nombreEmpleado = rs3.getString(CAMPO_NOMBRE);
                            apellidosEmpleado = rs3.getString(CAMPO_APELLIDOS);
                        }
                    }
                    System.out.println("NOMBRE TIENDA: " + nombreTienda + " | PROVINCIA: " + provinciaTienda + " | CIUDAD: " + ciudadTienda
                            + " | NOMBRE EMPLEADO: " + nombreEmpleado + " | APELLIDOS: " + apellidosEmpleado + " | SEMANA: " + semana + " | HORAS SEMANALES: " + horas);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * COMPROBACION DE DATOS TABLA.
     * #########################################################################
     */
    /**
     * Método para comprabar si existe la tienda en la base de datos.
     *
     * @param con
     * @param tienda
     * @param provincia
     * @param ciudad
     * @return
     */
    public boolean existeTienda(Connection con, String tienda, String provincia, String ciudad) {
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_TIENDAS);
            while (rs.next()) {
                String nombre = rs.getString(CAMPO_NOMBRE);
                if (nombre.equalsIgnoreCase(tienda.trim())) {
                    String nomProvincia = rs.getString(CAMPO_PROVINCIA);
                    if (nomProvincia.equalsIgnoreCase(provincia)) {
                        String nomCiudad = rs.getString(CAMPO_CIUDAD);
                        if (nomCiudad.equalsIgnoreCase(ciudad)) {
                            return true;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Método para comprabar si existe la tienda en la base de datos.
     *
     * @param con
     * @param id
     * @return
     */
    public boolean existeTienda(Connection con, int id) {

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_TIENDAS);
            while (rs.next()) {
                int id_tienda = rs.getInt(CAMPO_ID);
                if (id_tienda == id) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public boolean existeProductoFranquicia(Connection con, int id) {

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_PRODUCTOS);
            while (rs.next()) {
                int id_producto = rs.getInt(CAMPO_ID);
                if (id_producto == id) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Método para comprobar si hay un producto con el mismo nombre y
     * descripción.
     *
     * @param con
     * @param nombre
     * @param descripcion
     * @return
     */
    public boolean existeProducto(Connection con, String nombre, String descripcion) {

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_PRODUCTOS);
            while (rs.next()) {
                String nombre_producto = rs.getString(CAMPO_NOMBRE);
                if (nombre_producto.equalsIgnoreCase(nombre.trim())) {
                    String descripcion_producto = rs.getString(CAMPO_DESCRIPCION);
                    if (descripcion_producto.equalsIgnoreCase(descripcion.trim())) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Método para comprobar si existe un producto en una determinada tienda.
     *
     * @param con
     * @param id
     * @return
     */
    public boolean existeTiendaProducto(Connection con, int id) {

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_PRODUCTOS_TIENDA);
            while (rs.next()) {
                int id_tienda = rs.getInt(CAMPO_ID_TIENDA);
                if (id_tienda == id) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Método para comprobar si existe un producto en una determinada tienda.
     *
     * @param con
     * @param idTienda
     * @param idProducto
     * @return
     */
    public int existeProductoTienda(Connection con, int idTienda, int idProducto) {
        int stock = -1;
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_PRODUCTOS_TIENDA);
            while (rs.next()) {
                int id_tienda = rs.getInt(CAMPO_ID_TIENDA);
                if (id_tienda == idTienda) {
                    int id_producto = rs.getInt(CAMPO_ID_PRODUCTO);
                    if (id_producto == idProducto) {
                        stock = rs.getInt(CAMPO_STOCK);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return stock;
    }

    /**
     * Método para comprobar si existe el cliente en la base de datos.
     *
     * @param con
     * @param email
     * @return
     */
    public boolean existeCliente(Connection con, String email) {

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_CLIENTES);
            while (rs.next()) {
                String emailCliente = rs.getString(CAMPO_EMAIL);
                if (emailCliente.equalsIgnoreCase(email.trim())) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Método para comprobar si existe un empleado en la base de datos.
     *
     * @param con
     * @param nombre
     * @param apellidos
     * @return
     */
    public boolean existeEmpleado(Connection con, String nombre, String apellidos) {

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_EMPLEADOS);
            while (rs.next()) {
                String nombreEmpleado = rs.getString(CAMPO_NOMBRE);
                String apellidosEmpleado = rs.getString(CAMPO_APELLIDOS);
                if (nombreEmpleado.equalsIgnoreCase(nombre.trim()) && apellidosEmpleado.equalsIgnoreCase(apellidos.trim())) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Método para comprobar si existe un empleado en la base de datos.
     *
     * @param con
     * @param id
     * @return
     */
    public boolean existeEmpleado(Connection con, int id) {

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_EMPLEADOS);
            while (rs.next()) {
                int id_empleado = rs.getInt(CAMPO_ID);
                if (id_empleado == id) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Método para comprobar si existe un empleado en una tienda que trabajara
     * una semana determinada.
     *
     * @param con
     * @param idTienda
     * @param idEmpleado
     * @param semana
     * @return
     */
    public int existeEmpladoTiendaSemana(Connection con, int idTienda, int idEmpleado, int semana) {
        int horas = -1;
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_EMPLEADOS_TIENDA);
            while (rs.next()) {
                int id_tienda = rs.getInt(CAMPO_ID_TIENDA);
                if (id_tienda == idTienda) {
                    int id_empleado = rs.getInt(CAMPO_ID_EMPLEADO);
                    if (id_empleado == idEmpleado) {
                        int num_semana = rs.getInt(CAMPO_SEMANA);
                        if (num_semana == semana) {
                            horas = rs.getInt(CAMPO_HORAS);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return horas;
    }

    /**
     * ELIMINAR REGISTROS TABLAS.
     * #########################################################################
     */
    /**
     * Método para eliminar una tienda de la base de datos.
     *
     * @param con
     * @param id
     */
    public void deleteTienda(Connection con, int id) {
        try {
            PreparedStatement pstmt = con.prepareStatement(DELETE_TIENDA);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Método para eliminar tiendas de productosTienda.
     * @param con
     * @param idTienda 
     */
    public void deleteTiendaAllProducto(Connection con, int idTienda) {
        try {
            PreparedStatement pstmt = con.prepareStatement(DELETE_TIENDA_ALL_PRODUCTOS);
            pstmt.setInt(1, idTienda);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Método para eliminar tiendas de empleadosTienda.
     * @param con
     * @param idTienda 
     */
    public void deleteTiendaAllEmpleado(Connection con, int idTienda) {
        try {
            PreparedStatement pstmt = con.prepareStatement(DELETE_TIENDA_ALL_EMPLEADOS);
            pstmt.setInt(1, idTienda);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Método para eliminar un producto de una tienda.
     *
     * @param con
     * @param idTienda
     * @param idProducto
     */
    public void deleteProductoTienda(Connection con, int idTienda, int idProducto) {
        try {
            PreparedStatement pstmt = con.prepareStatement(DELETE_PRODUCTO_TIENDA);
            pstmt.setInt(1, idTienda);
            pstmt.setInt(2, idProducto);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Método para eliminar un producto de la base de datos.
     *
     * @param con
     * @param idProducto
     */
    public void deleteProducto(Connection con, int idProducto) {
        try {
            PreparedStatement pstmt = con.prepareStatement(DELETE_PRODUCTO);
            pstmt.setInt(1, idProducto);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Método para eliminar el producto de todos los sitios.
     *
     * @param con
     * @param idProducto
     */
    public void deleteProductoAll(Connection con, int idProducto) {
        try {
            PreparedStatement pstmt = con.prepareStatement(DELETE_PRODUCTO_ALL);
            pstmt.setInt(1, idProducto);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Método para eliminar un cliente de la base de datos.
     *
     * @param con
     * @param email
     */
    public void deleteCliente(Connection con, String email) {
        try {
            PreparedStatement pstmt = con.prepareStatement(DELETE_CLIENTE);
            pstmt.setString(1, email.trim().toLowerCase());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Método para eliminar un empleado de la base de datos.
     *
     * @param con
     * @param idEmpleado
     */
    public void deleteEmpleado(Connection con, int idEmpleado) {
        try {
            PreparedStatement pstmt = con.prepareStatement(DELETE_EMPLEADO);
            pstmt.setInt(1, idEmpleado);
            pstmt.executeUpdate();
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Método para eliminar al empleado de todos los sitios.
     *
     * @param con
     * @param idEmpleado
     */
    public void deleteEmpleadoAll(Connection con, int idEmpleado) {
        try {
            PreparedStatement pstmt = con.prepareStatement(DELETE_EMPLEADO_ALL);
            pstmt.setInt(1, idEmpleado);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * PARTE DE TRATAMIENTO DE LA TABLA PROVINCIAS.
     * #########################################################################
     */
    /**
     * Método que inserta los datos de las provincias que obtenemos de un
     * archivo .json.
     *
     * @param provincias
     */
    public void insertarDatosProvincias(Connection con, Provincias provincias) {
        ArrayList<Provincia> provinciasAux = provincias.getProvincias();
        if (!provinciasAux.isEmpty()) {
            int cantidad = consultarCantidad(con, SQL_PROVINCIAS);
            if (provinciasAux.size() != cantidad) {
                for (Provincia provinciaAux : provinciasAux) {
                    insertProvincia(con, provinciaAux.getId(), provinciaAux.getNome().trim().toUpperCase());
                }
            } else {
                System.out.println("Las provincias ya están insertadas.");
            }
        } else {
            System.out.println("No hay provincias que insertar.");
        }
        driverDB.desconnetDatabase(con);
    }

    /**
     * Método para comprobar si la provincia está en la base de datos.
     *
     * @param con
     * @param provincia
     * @return
     */
    public boolean comprobarProvincia(Connection con, String provincia) {

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_PROVINCIAS);
            while (rs.next()) {
                String nombre = rs.getString(CAMPO_NOMBRE);
                if (nombre.equalsIgnoreCase(provincia.trim())) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Método para insertar las provincias en la clase provincias.
     *
     * @return
     */
    public Provincias insertarProvinciaAProvincias() {

        Connection con = driverDB.connectDatabase();
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_PROVINCIAS);
            while (rs.next()) {
                int id = rs.getInt(CAMPO_ID);
                String nombre = rs.getString(CAMPO_NOMBRE);
                provincias.getProvincias().add(new Provincia(id, nombre.trim().toUpperCase()));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        driverDB.desconnetDatabase(con);
        return provincias;
    }

    /**
     * HERRAMIENTAS PARA EL TRATAMIENTO DE DATOS DE LA BASE DE DATOS.
     * #########################################################################
     */
    /**
     * Consulta la cantidad de registros de la tabla.
     *
     * @param con
     * @param sql
     * @return
     */
    public int consultarCantidad(Connection con, String sql) {

        int cantidad = 0;
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                cantidad++;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return cantidad;
    }

}
