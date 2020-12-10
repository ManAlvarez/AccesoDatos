/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.minidrive.objetos;

/**
 *
 * @author manuel
 */
public class Arquivo {
    
    private int id;
    private String nome;
    private String rutaPadre;
    private String rutaAbsoluta;
    private int idDirectorio;

    public Arquivo(String nome, String rutaPadre, String rutaAbsoluta) {
        this.nome = nome;
        this.rutaPadre = rutaPadre;
        this.rutaAbsoluta = rutaAbsoluta;
    }

    public Arquivo(String nome, int idDirectorio) {
        this.nome = nome;
        this.idDirectorio = idDirectorio;
    }
      
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRutaPadre() {
        return rutaPadre;
    }

    public void setRutaPadre(String rutaPadre) {
        this.rutaPadre = rutaPadre;
    }

    public String getRutaAbsoluta() {
        return rutaAbsoluta;
    }

    public void setRutaAbsoluta(String rutaAbsoluta) {
        this.rutaAbsoluta = rutaAbsoluta;
    }

    public int getIdDirectorio() {
        return idDirectorio;
    }

    public void setIdDirectorio(int idDirectorio) {
        this.idDirectorio = idDirectorio;
    }

    
    
    
   
    
     
}
