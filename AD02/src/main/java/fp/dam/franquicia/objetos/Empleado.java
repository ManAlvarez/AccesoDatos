/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.franquicia.objetos;

import java.io.Serializable;

/**
 *
 * @author manuel
 */
public class Empleado implements Serializable {
    
    private String nome;
    private String apellidos;

    public Empleado(String nome, String apellidos) {
        this.nome = nome;
        this.apellidos = apellidos;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
         
}
