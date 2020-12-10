/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.minidrive.utiles;

import fp.dam.minidrive.db.DB;
import fp.dam.minidrive.objetos.Arquivo;
import fp.dam.minidrive.objetos.Directorio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.postgresql.PGConnection;
import org.postgresql.PGNotification;

/**
 *
 * @author manuel
 */
public class Listener extends Thread {

    private static final DB db = new DB();
    private static final Utiles utiles = new Utiles();
    private static ArrayList<Directorio> directorios = new ArrayList<>();
    private static final ArrayList<Arquivo> arquivos = new ArrayList<>();
    private static ArrayList<Arquivo> arquivosDir = new ArrayList<>();
    private static ArrayList<Arquivo> arquivosBD = new ArrayList<>();
    private static ArrayList<Arquivo> arquivosDescargar = new ArrayList<>();
    private Connection conn;

    public Listener() {
        conn = db.conectar();
    }

    @Override
    public void run() {

        try {

            while (true) {
                // Inicializamos las listas.
                directorios.clear();
                arquivos.clear();
                arquivosDir.clear();
                arquivosBD.clear();
                arquivosDescargar.clear();

                System.out.println("Guardando...");
                // Insertar en la base de datos todos los subdirectorios de los directorios almacenados en la base de datos.
                directorios = db.obtenerDirectorios();
                directorios.forEach((directorioAux) -> {
                    db.insertarDirectorio(utiles.listarDirectorios(directorioAux.getDirectorio()));
                });

                // Obtener todos los arquivos que pertenecen a los directorios almacenados en la base de datos.
                directorios.forEach((directorioAux) -> {
                    arquivos.addAll(utiles.listarArquivos(directorioAux.getDirectorio()));
                });

                // Añadir el id del directorio al arquivo.
                arquivosDir = utiles.arquivosAInsertar(arquivos, directorios);

                // Insertar los arquivos en la base de datos.
                db.insertarArquivo(arquivosDir);

                //Configuramos para estar a escoita
                PGConnection pgconn = conn.unwrap(PGConnection.class);
                Statement stmt = conn.createStatement();
                stmt.execute("LISTEN notificar_novo_arquivo");
                stmt.close();

                //Creamos a consulta que necesitaremos para obter a notificación.
                PreparedStatement sqlNotificacionArquivo = conn.prepareStatement("SELECT nome, iddirectorio FROM arquivos WHERE id = ?;");

                //Collemos todas as notificacions
                PGNotification notifications[] = pgconn.getNotifications();

                //Se hai notificacions, recorrémolas e imprimimos os parametros 
                if (notifications != null) {
                    for (int i = 0; i < notifications.length; i++) {

                        //A notificacion danos como parámetro o id da notificación.
                        int id = Integer.parseInt(notifications[i].getParameter());

                        //Facemos unha consulta a base de datos e imprimimos a notificación.
                        sqlNotificacionArquivo.setInt(1, id);
                        ResultSet rs = sqlNotificacionArquivo.executeQuery();
                        rs.next();
                        System.out.println(rs.getString(1) + ":" + rs.getString(2));
                        System.out.println("Se a engadido un novo arquivo.");

                        // Obtener los arquivos almacenados en la base de datos.
                        arquivosBD = db.obtenerArquivos();

                        // Comprobar si los arquivos almacenados en la bd ya no existen en los directorios.
                        arquivosDescargar = utiles.arquivosADescargar(arquivosBD, arquivosDir);

                        // Descargar el archivo de la base de datos que se no existen en los directorios.
                        db.descargarArquivo(arquivosDescargar);

                        System.out.println("Arquivos sincronizados...");

                        rs.close();
                    }
                }
                System.out.println("Esperando...");
                Thread.sleep(30000);
            }

        } catch (InterruptedException ex) {
            System.err.println("Error: " + ex.toString());
        } catch (SQLException ex) {
            Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
