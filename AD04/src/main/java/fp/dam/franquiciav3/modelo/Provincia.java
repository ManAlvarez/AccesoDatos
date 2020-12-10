/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.franquiciav3.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author manuel
 */
@Entity
@Table(name = "provincias")
public class Provincia implements Serializable {

    @Id
    private int id;

    @Column(unique = true, name = "nombre")
    @NotNull
    private String nome;

    public Provincia() {
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

    @Override
    public String toString() {
        return "Provincia{" + "id=" + id + ", nombre=" + nome + '}';
    }
    
    public String toStringMostrar(){
        return nome;
    }
    
}
