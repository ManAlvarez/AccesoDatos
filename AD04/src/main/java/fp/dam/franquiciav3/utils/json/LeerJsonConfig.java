/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.franquiciav3.utils.json;

import com.google.gson.Gson;
import fp.dam.franquiciav3.utils.hibernate.ConfigHibernate;
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

    public static ConfigHibernate leerJson() {

        File ficheiro = new File("config.json");
        ConfigHibernate configHibernate = new ConfigHibernate();

        if (ficheiro.exists()) {
            try {
                FileReader fluxoDatos;
                fluxoDatos = new FileReader(ficheiro);

                BufferedReader buferEntrada = new BufferedReader(fluxoDatos);

                StringBuilder jsonBuilder = new StringBuilder();
                String linea;

                while ((linea = buferEntrada.readLine()) != null) {
                    jsonBuilder.append(linea).append("\n");
                }

                buferEntrada.close();

                String json = jsonBuilder.toString();

                Gson gson = new Gson();
                configHibernate = gson.fromJson(json, ConfigHibernate.class);

            } catch (FileNotFoundException e) {
                System.out.println("Non se encontra o arquivo");
            } catch (IOException e) {
                System.out.println("Erro de entrada saída");
            }
        }
        return configHibernate;
    }

}
