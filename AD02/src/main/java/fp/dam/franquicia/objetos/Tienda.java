/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.franquicia.objetos;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author manuel
 */
public class Tienda implements Serializable{
    private String nombre;
    private String ciudad;
    private ArrayList<Producto> productos;
    private ArrayList<Empleado> empleados;

    public Tienda(String nombre, String ciudad) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.productos = new ArrayList<>();
        this.empleados = new  ArrayList<>();
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(ArrayList<Empleado> empleados) {
        this.empleados = empleados;
    }
       
}
