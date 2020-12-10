/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.mongotwitter.json;

import com.google.gson.Gson;
import fp.dam.mongotwitter.objetos.ConfigMongoDB;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author manuel
 */
public class LeerJsonConfig {

    /**
     * Método para leer el json de configuración de la base de datos.
     * @return 
     */
    public static ConfigMongoDB leerJson() {
        File ficheiro = new File("configuracion.json");
        ConfigMongoDB configMongoDB = new ConfigMongoDB();
        if (ficheiro.exists()) {
            try {
                FileReader fluxoDatos;
                fluxoDatos = new FileReader(ficheiro);
                StringBuilder jsonBuilder;
                try (BufferedReader buferEntrada = new BufferedReader(fluxoDatos)) {
                    jsonBuilder = new StringBuilder();
                    String linea;
                    while ((linea = buferEntrada.readLine()) != null) {
                        jsonBuilder.append(linea).append("\n");
                    }
                }
                String json = jsonBuilder.toString();
                Gson gson = new Gson();
                configMongoDB = gson.fromJson(json, ConfigMongoDB.class);
            } catch (FileNotFoundException e) {
                System.out.println("Non se encontra o arquivo");
            } catch (IOException e) {
                System.out.println("Erro de entrada saída");
            }
        }
        return configMongoDB;
    }

}
