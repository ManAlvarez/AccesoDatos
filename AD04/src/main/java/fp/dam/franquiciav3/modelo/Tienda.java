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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author manuel
 */
@Entity
@Table(name = "tiendas")
public class Tienda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_provincia") //Inserta el id de la clase Provincia en la columna idProvincia.
    private Provincia provincia;

    @NotNull
    private String ciudad;

    public Tienda() {
    }

    public Tienda(String nombre, Provincia provincia, String ciudad) {
        this.nombre = nombre;
        this.provincia = provincia;
        this.ciudad = ciudad;
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

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    @Override
    public String toString() {
        return "Tienda{" + "id=" + id + ", nombre=" + nombre + ", provincia=" + provincia + ", ciudad=" + ciudad + '}';
    }

    public String toStringMostrar(){
        return "\tId tienda: "+id+"\n"
                +"\tNombre: "+nombre+"\n"
                +"\tProvincia: "+provincia.getNome()+"\n"
                +"\tCiudad: "+ciudad+"\n";
    }

 
}
