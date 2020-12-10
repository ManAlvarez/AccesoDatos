/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.minidrive.utiles;

import fp.dam.minidrive.db.DBConnection;
import fp.dam.minidrive.json.LeerJsonConfig;
import fp.dam.minidrive.objetos.Arquivo;
import fp.dam.minidrive.objetos.Directorio;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author manuel
 */
public class Utiles {

    /**
     * Método para obtener la ruta del directorio indicado en el archivo de
     * configuración .json.
     *
     * @return
     */
    public String obtenerDirectorioRaiz() {
        DBConnection db = LeerJsonConfig.leerJson();
        String directorioRaiz = db.getApp().getDirectory();
        return directorioRaiz;
    }

    /**
     * Método para listar los directorios.
     *
     * @param directorio
     * @return
     */
    public ArrayList<String> listarDirectorios(String directorio) {
        ArrayList<String> directoriosAInsertar = new ArrayList<>();
        directoriosAInsertar.clear();
        File directorioRaiz = new File(directorio);
        if (directorioRaiz.exists()) {
            directoriosAInsertar.add(directorio);
            if (directorioRaiz.isDirectory()) {
                File[] directorios = directorioRaiz.listFiles();
                if (directorios.length > 0) {
                    for (File directorioAux : directorios) {
                        if (directorioAux.isDirectory()) {
                            directoriosAInsertar.add(directorioAux.getAbsolutePath());
                        }
                    }
                }
            }
        }
        return directoriosAInsertar;
    }

    /**
     * Método para listar los archivos.
     *
     * @param directorio
     * @return
     */
    public ArrayList<Arquivo> listarArquivos(String directorio) {
        ArrayList<Arquivo> arquivosAInsertar = new ArrayList<>();
        arquivosAInsertar.clear();
        File directorioRaiz = new File(directorio);
        if (directorioRaiz.exists()) {
            if (directorioRaiz.isDirectory()) {
                File[] arquivos = directorioRaiz.listFiles();
                for (File arquivo : arquivos) {
                    if (arquivo.isFile()) {
                        arquivosAInsertar.add(new Arquivo(arquivo.getName(), arquivo.getParent(), arquivo.getAbsolutePath()));
                    }
                }
            }
        }
        return arquivosAInsertar;
    }

    /**
     * Método para añadir el id del directorio al arquivo.
     *
     * @param arquivos
     * @param directorios
     * @return
     */
    public ArrayList<Arquivo> arquivosAInsertar(ArrayList<Arquivo> arquivos, ArrayList<Directorio> directorios) {
        for (Arquivo arquivoAux : arquivos) {
            String rutaPadre = arquivoAux.getRutaPadre();
            for (Directorio directorioAux : directorios) {
                if (directorioAux.getDirectorio().equals(rutaPadre)) {
                    arquivoAux.setIdDirectorio(directorioAux.getId());
                }
            }
        }
        return arquivos;
    }

    /**
     * Método para descargar los arquivos de la base de datos.
     *
     * @param arquivosBD
     * @param arquivosDirectorios
     * @return
     */
    public ArrayList<Arquivo> arquivosADescargar(ArrayList<Arquivo> arquivosBD, ArrayList<Arquivo> arquivosDirectorios) {
        boolean insertar = false;
        ArrayList<Arquivo> arquivosADescargar = new ArrayList<>();
        arquivosADescargar.clear();
        for (Arquivo arquivoBDAux : arquivosBD) {
            insertar = true;
            String nome = arquivoBDAux.getNome();
            int idDirectorio = arquivoBDAux.getIdDirectorio();
            for (Arquivo arquivoDirAux : arquivosDirectorios) {
                if (arquivoDirAux.getNome().equals(nome) && arquivoDirAux.getIdDirectorio() == idDirectorio) {
                    insertar = false;
                }
            }
            if (insertar) {
                arquivosADescargar.add(arquivoBDAux);
            }
        }
        return arquivosADescargar;
    }

}
