/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.franquiciav3.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "empleados_tienda")
public class EmpleadosTiendas implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tienda")
    private Tienda tienda;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;
    
    @NotNull
    private int semana;
    
    @NotNull
    private int horas;

    public EmpleadosTiendas() {
    }

    public EmpleadosTiendas(Tienda tienda, Empleado empleado, int semana, int horas) {
        this.tienda = tienda;
        this.empleado = empleado;
        this.semana = semana;
        this.horas = horas;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public int getSemana() {
        return semana;
    }

    public void setSemana(int semana) {
        this.semana = semana;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    @Override
    public String toString() {
        return "EmpleadosTiendas{" + "id=" + id + ", tienda=" + tienda + ", empleado=" + empleado + ", semana=" + semana + ", horas=" + horas + '}';
    }
    
    public String toStringMostrar(){
        return "\tId empleado: "+empleado.getId()+"\n"
                +"\tNombre: "+empleado.getNombre()+"\n"
                +"\tApellidos: "+empleado.getApellidos()+"\n"
                +"\tUsuario: "+empleado.getUsuario()+"\n"
                +"\t\tSemana: "+semana+"\n"
                +"\t\tHoras trabajadas: "+horas+"\n";
    }
  
}
