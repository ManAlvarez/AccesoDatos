/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.franquiciav2.objetos;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author manuel
 */
public class Provincias implements Serializable{
    private ArrayList<Provincia> provincias;

    public Provincias() {
        this.provincias = new ArrayList<>();
    }

    public ArrayList<Provincia> getProvincias() {
        return provincias;
    }

    public void setProvincias(ArrayList<Provincia> provincias) {
        this.provincias = provincias;
    }
       
}
