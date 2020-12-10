/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.franquiciav2.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author manuel
 */
public class DriverDB {

    //Base de datos.
    private static final String DB = "franquicia.db";
    private static final String RUTA = new File("").getAbsolutePath();
    private static final String SEPARADOR = File.separator;
    private static final String ARCHIVODB = "jdbc:sqlite:" + RUTA + SEPARADOR + DB;

    /**
     * Método para crear la base de datos.
     */
    public void createDatabase() {
        try {
            Connection connection = DriverManager.getConnection(ARCHIVODB);
            if (connection != null) {
                DatabaseMetaData meta = connection.getMetaData();
                System.out.println("La base de datos se ha creado correctamente.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Método para establecer la conexión con la base de datos.
     *
     * @return
     */
    public Connection connectDatabase() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(ARCHIVODB);
            return connection;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Método para cerrar la conexión con la base de datos.
     *
     * @param connection
     */
    public void desconnetDatabase(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
