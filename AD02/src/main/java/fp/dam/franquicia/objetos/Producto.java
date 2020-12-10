/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.franquicia.objetos;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * @author manuel
 */
public class Producto implements Serializable {
    
    private int identificador;
    private String descripcion;
    private double precio;
    private int cantidade;

    public Producto(int identificador, String descripcion, double precio, int cantidade) {
        this.identificador = identificador;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidade = cantidade;
    }
    
    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidade() {
        return cantidade;
    }

    public void setCantidade(int cantidade) {
        this.cantidade = cantidade;
    }
    
}
