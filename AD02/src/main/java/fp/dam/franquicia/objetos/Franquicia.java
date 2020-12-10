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
public class Franquicia implements Serializable{
    private ArrayList<Tienda> tiendas;
    private ArrayList<Cliente> clientes;

    public Franquicia() {
        this.tiendas = new ArrayList<>();
        this.clientes = new ArrayList<>();
    }

    public ArrayList<Tienda> getTiendas() {
        return tiendas;
    }

    public void setTiendas(ArrayList<Tienda> tiendas) {
        this.tiendas = tiendas;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }
    
}
