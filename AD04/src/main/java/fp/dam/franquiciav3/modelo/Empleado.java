/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.franquiciav3.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author manuel
 */

@Entity
@Table(name = "empleado")
public class Empleado implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)        
    private int id;
    
    @NotNull
    private String nombre;
    
    @NotNull
    private String apellidos;
    
    @NotNull
    private String usuario;

    public Empleado() {
    }

    public Empleado(String nombre, String apellidos, String usuario) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Empleado{" + "id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", usuario=" + usuario + '}';
    }
    
    public String toStringMostrar(){
        return "\tId empleado: "+id+"\n"
                +"\tNombre: "+nombre+"\n"
                +"\tApellidos: "+apellidos+"\n"
                +"\tUsuario: "+usuario+"\n";
    }
    
      
}
