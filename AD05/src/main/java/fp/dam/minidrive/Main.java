package fp.dam.minidrive;

import fp.dam.minidrive.db.DB;
import fp.dam.minidrive.objetos.Arquivo;
import fp.dam.minidrive.objetos.Directorio;
import fp.dam.minidrive.utiles.Listener;
import fp.dam.minidrive.utiles.Utiles;
import java.util.ArrayList;

/**
 *
 * @author manuel
 */
public class Main {

    private static final DB db = new DB();
    private static final Utiles utiles = new Utiles();
    private static final ArrayList<Arquivo> arquivosDir = new ArrayList<>();
    private static ArrayList<Arquivo> arquivosBD = new ArrayList<>();
    private static ArrayList<Arquivo> arquivosDescargar = new ArrayList<>();

    public static void main(String[] args) {

        // Inicializamos las listas.      
        arquivosBD.clear();
        arquivosDescargar.clear();

        // Creamos la tablas.
        db.crearTablas();

        // Insertar en la base de datos el directorio raiz  y sus subdirectorios.
        db.insertarDirectorio(utiles.listarDirectorios(utiles.obtenerDirectorioRaiz()));

        // Obtener los arquivos almacenados en la base de datos.
        arquivosBD = db.obtenerArquivos();

        // Comprobar si los arquivos almacenados en la bd ya no existen en los directorios.
        arquivosDescargar = utiles.arquivosADescargar(arquivosBD, arquivosDir);

        // Descargar el archivo de la base de datos que se no existen en los directorios.
        db.descargarArquivo(arquivosDescargar);
        System.out.println("Sincronizando arquivos...");

        Listener hilo = new Listener();
        hilo.start();
        db.notificarArquivo();

    }

}
