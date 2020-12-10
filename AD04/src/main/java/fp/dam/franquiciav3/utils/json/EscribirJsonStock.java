/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.franquiciav3.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import fp.dam.franquiciav3.db.ControladorDB;
import fp.dam.franquiciav3.modelo.Producto;
import fp.dam.franquiciav3.modelo.ProductosTiendas;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author manuel
 */
public class EscribirJsonStock {

    private static ControladorDB controladorDB = new ControladorDB();

    public static void escribirJson() {

        File ficheiro = new File("data.json");

        try {

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonArray stock = new JsonArray();

            List<Producto> productos = controladorDB.obtenerProductos();
            List<ProductosTiendas> stocks = controladorDB.obtenerStock();

            if (productos != null) {

                for (Producto productoAux : productos) {
                    JsonObject producto = new JsonObject();
                    producto.addProperty("id_producto", productoAux.getId());
                    producto.addProperty("nombre_producto", productoAux.getNombre());
                    producto.addProperty("descripcion_producto", productoAux.getDescripcion());

                    JsonArray StocktiendasArray = new JsonArray();

                    if (stocks != null) {
                        for (ProductosTiendas stockAux : stocks) {
                            JsonObject tienda = new JsonObject();
                            tienda.addProperty("id_tienda", stockAux.getTienda().getId());
                            tienda.addProperty("nombre_tienda", stockAux.getTienda().getNombre());
                            tienda.addProperty("provincia_tienda", stockAux.getTienda().getProvincia().getNome());
                            tienda.addProperty("ciudad_tienda", stockAux.getTienda().getCiudad());
                            tienda.addProperty("stock", stockAux.getStock());

                            StocktiendasArray.add(tienda);
                        }
                    }

                    producto.add("stock_tiendas", StocktiendasArray);
                    stock.add(producto);
                }
            }

            FileWriter outputFile = new FileWriter(ficheiro);
            gson.toJson(stock, outputFile);
            outputFile.close();

            System.out.println();
            System.out.println("Informe creado en " + ficheiro.getAbsolutePath());
        } catch (JsonIOException | IOException e) {
            System.out.println("No se ha podido crear el informe");
        }
    }
}
