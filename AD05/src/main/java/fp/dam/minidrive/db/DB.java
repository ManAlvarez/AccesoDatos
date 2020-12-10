package fp.dam.minidrive.db;

import fp.dam.minidrive.json.LeerJsonConfig;
import fp.dam.minidrive.objetos.Arquivo;
import fp.dam.minidrive.objetos.Directorio;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * @author manuel
 */
public class DB {

    /**
     * Método para conectarnos a la base de datos.
     *
     * @return Connection
     */
    public Connection conectar() {

        DBConnection db = LeerJsonConfig.leerJson();
        Connection conn = null;
        Properties props = new Properties();
        props.setProperty("user", db.getDbConnection().getUser());
        props.setProperty("password", db.getDbConnection().getPassword());

        String postgres = "jdbc:postgresql://" + db.getDbConnection().getAddress() + "/" + db.getDbConnection().getName();

        try {
            conn = DriverManager.getConnection(postgres, props);
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.toString());
        }
        return conn;
    }

    /**
     * Método para desconectar la conexión a la base de datos.
     *
     * @param conn
     */
    public void desconectar(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.err.println("Error: " + ex.toString());
            }
        }
    }

    /**
     * Método para crear las tablas necesarias.
     */
    public void crearTablas() {

        try {
            Connection conn = conectar();

            String sqlTableCreation1
                    = "CREATE TABLE IF NOT EXISTS directorios ("
                    + "id SERIAL PRIMARY KEY, "
                    + "directorio TEXT NOT NULL);";

            CallableStatement createTable1 = conn.prepareCall(sqlTableCreation1);
            createTable1.execute();
            createTable1.close();

            String sqlTableCreation2
                    = "CREATE TABLE IF NOT EXISTS arquivos ("
                    + "id SERIAL PRIMARY KEY, "
                    + "nome TEXT NOT NULL, "
                    + "arquivo BYTEA NOT NULL, "
                    + "iddirectorio INTEGER NOT NULL, "
                    + "FOREIGN KEY (iddirectorio) REFERENCES directorios (id) ON DELETE CASCADE ON UPDATE CASCADE);";

            CallableStatement createTable2 = conn.prepareCall(sqlTableCreation2);
            createTable2.execute();
            createTable2.close();

            desconectar(conn);

        } catch (SQLException ex) {
            System.err.println("Error: " + ex.toString());
        }
    }

    /**
     * Método para comprobar si la ruta completa del directorio ya existe en la
     * base de datos.
     *
     * @param directorio
     * @param conn
     * @return
     */
    public boolean existeDirectorio(String directorio, Connection conn) {
        try {
            String sqlDirectorio
                    = "SELECT directorio "
                    + "FROM directorios "
                    + "WHERE directorio = ?;";
            PreparedStatement ps = conn.prepareStatement(sqlDirectorio);
            ps.setString(1, directorio);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.toString());
        }
        return false;
    }

    /**
     * Método para comprobar si existe un arquivo en la base de datos.
     *
     * @param nome
     * @param idDirectorio
     * @param conn
     * @return
     */
    public boolean existeArquivo(String nome, int idDirectorio, Connection conn) {
        try {
            String sqlArquivo
                    = "SELECT nome "
                    + "FROM arquivos "
                    + "WHERE nome = ? AND iddirectorio = ?;";
            PreparedStatement ps = conn.prepareStatement(sqlArquivo);
            ps.setString(1, nome);
            ps.setInt(2, idDirectorio);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.toString());
        }
        return false;
    }

    /**
     * Método para insertar la ruta completa del directorio en la base de datos.
     *
     * @param nombresDirectorios
     */
    public void insertarDirectorio(ArrayList<String> nombresDirectorios) {
        try {
            try (Connection conn = conectar()) {
                if (!nombresDirectorios.isEmpty()) {
                    for (String nombreDirectorio : nombresDirectorios) {
                        if (!existeDirectorio(nombreDirectorio, conn)) {
                            String sqlInsertDirectorio
                                    = "INSERT INTO directorios(directorio) "
                                    + "VALUES (?);";

                            try (PreparedStatement ps = conn.prepareStatement(sqlInsertDirectorio)) {
                                ps.setString(1, nombreDirectorio);
                                ps.executeUpdate();
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.toString());
        }
    }

    /**
     * Método para insertar los arquivos en la base de datos.
     *
     * @param arquivos
     */
    public void insertarArquivo(ArrayList<Arquivo> arquivos) {
        try {
            Connection conn = conectar();
            if (!arquivos.isEmpty()) {
                for (Arquivo arquivoAux : arquivos) {
                    if (!existeArquivo(arquivoAux.getNome(), arquivoAux.getIdDirectorio(), conn)) {
                        File arquivo = new File(arquivoAux.getRutaAbsoluta());
                        try (FileInputStream flujoEntrada = new FileInputStream(arquivo)) {
                            String sqlInsertArquivo
                                    = "INSERT INTO arquivos(nome,arquivo,iddirectorio)"
                                    + " VALUES (?,?,?)";

                            try (PreparedStatement ps = conn.prepareStatement(sqlInsertArquivo)) {
                                ps.setString(1, arquivoAux.getNome());
                                ps.setBinaryStream(2, flujoEntrada, (int) arquivo.length());
                                ps.setInt(3, arquivoAux.getIdDirectorio());
                                ps.executeUpdate();
                            }
                        }
                    }
                }
            }
            desconectar(conn);

        } catch (FileNotFoundException | SQLException ex) {
            System.err.println("Error: " + ex.toString());
        } catch (IOException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    /**
     * Método para btener los directorios de la base de datos.
     *
     * @return
     */
    public ArrayList<Directorio> obtenerDirectorios() {
        Connection conn = conectar();
        ArrayList<Directorio> directorios = new ArrayList<>();
        directorios.clear();
        try {
            String sqlDirectorios = "SELECT id,directorio FROM directorios;";
            PreparedStatement ps = conn.prepareStatement(sqlDirectorios);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String ruta = rs.getString(2);
                directorios.add(new Directorio(id, ruta));
            }
        } catch (SQLException ex) {
            System.err.println("Error: " + ex.toString());
        }
        desconectar(conn);
        return directorios;
    }

    /**
     * Método para obtener los arquivos de la base de datos.
     *
     * @return
     */
    public ArrayList<Arquivo> obtenerArquivos() {
        Connection conn = conectar();
        ArrayList<Arquivo> arquivos = new ArrayList<>();
        arquivos.clear();
        try {
            String sqlArquivos = "SELECT nome,iddirectorio FROM arquivos;";
            PreparedStatement ps = conn.prepareStatement(sqlArquivos);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String nome = rs.getString(1);
                int idDirectorio = rs.getInt(2);
                arquivos.add(new Arquivo(nome, idDirectorio));
            }

        } catch (SQLException ex) {
            System.err.println("Error: " + ex.toString());
        }
        desconectar(conn);
        return arquivos;
    }

    /**
     * Método para descargar un arquivo.
     *
     * @param arquivos
     */
    public void descargarArquivo(ArrayList<Arquivo> arquivos) {

        Connection conn = conectar();
        try {
            for (Arquivo arquivoAux : arquivos) {
                String sqlArquivo
                        = "SELECT arquivo "
                        + "FROM arquivos "
                        + "WHERE nome = ? AND iddirectorio = ?;";
                PreparedStatement ps = conn.prepareStatement(sqlArquivo);
                ps.setString(1, arquivoAux.getNome());
                ps.setInt(2, arquivoAux.getIdDirectorio());
                ResultSet rs = ps.executeQuery();

                //Imos recuperando todos os bytes das imaxes
                byte[] datosBytes = null;
                while (rs.next()) {
                    datosBytes = rs.getBytes(1);
                }

                String sqlDirectorio
                        = "SELECT directorio "
                        + "FROM directorios "
                        + "WHERE id = ?;";

                PreparedStatement ps2 = conn.prepareStatement(sqlDirectorio);
                ps2.setInt(1, arquivoAux.getIdDirectorio());
                ResultSet rs2 = ps2.executeQuery();
                String rutaDirectorio = null;
                while (rs2.next()) {
                    rutaDirectorio = rs2.getString(1);
                }

                //Creamos o fluxo de datos para gardar o arquivo recuperado
                String ficheiroSaida = rutaDirectorio + File.separator + arquivoAux.getNome();
                File fileOut = new File(ficheiroSaida);
                FileOutputStream fluxoDatos = new FileOutputStream(fileOut);

                //Gardamos o arquivo recuperado
                if (datosBytes != null) {
                    fluxoDatos.write(datosBytes);
                }

                //cerramos o fluxo de datos de saida
                fluxoDatos.close();

                //Cerramos a conexión coa base de datos          
            }
        } catch (IOException | SQLException ex) {
            System.err.println("Error: " + ex.toString());
        }
        desconectar(conn);

    }

    /**
     * Método que crea una función y un trigger que crean una notificación
     * cuando se añade un nuevo arquivo.
     */
    public void notificarArquivo() {
        Connection conn = conectar();
        try {

            String sqlCreateFunction
                    = "CREATE OR REPLACE FUNCTION notificarArquivo() "
                    + "RETURNS trigger AS $$ "
                    + "BEGIN "
                    + "PERFORM pg_notify('notificar_novo_arquivo',NEW.id::text);"
                    + "RETURN NEW;"
                    + "END;"
                    + "$$ LANGUAGE plpgsql;";

            try (CallableStatement createFunction = conn.prepareCall(sqlCreateFunction)) {
                createFunction.execute();
            }

            String sqlCreateTrigger
                    = "DROP TRIGGER IF EXISTS notificar_novo_arquivo ON arquivos;"
                    + "CREATE TRIGGER notificar_novo_arquivo "
                    + "AFTER INSERT "
                    + "ON arquivos "
                    + "FOR EACH ROW "
                    + "EXECUTE PROCEDURE notificarArquivo();";

            try (CallableStatement createTrigger = conn.prepareCall(sqlCreateTrigger)) {
                createTrigger.execute();
            }

        } catch (SQLException ex) {
            System.err.println("Error: " + ex.toString());
        }
        desconectar(conn);

    }

}
