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
@Table(name = "productos_tienda")
public class ProductosTiendas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tienda")
    private Tienda tienda;

    @NotNull
    private int stock;

    public ProductosTiendas() {
    }

    public ProductosTiendas(Producto producto, Tienda tienda, int stock) {
        this.producto = producto;
        this.tienda = tienda;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "ProductosTiendas{" + "id=" + id + ", producto=" + producto + ", tienda=" + tienda + ", stock=" + stock + '}';
    }

    public String toStringMostrar() {
        return "\tId producto: " + producto.getId() + "\n"
                + "\tProducto: " + producto.getNombre() + "\n"
                + "\tDescripción: " + producto.getDescripcion() + "\n"
                + "\tStock: " + stock + "\n";
    }

}
