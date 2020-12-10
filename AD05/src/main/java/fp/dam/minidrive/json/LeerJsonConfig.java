
package fp.dam.minidrive.json;

import com.google.gson.Gson;
import fp.dam.minidrive.db.DBConnection;
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
    
    public static DBConnection leerJson(){
        
        File ficheiro = new File("config.json");
        DBConnection dbConnection = new DBConnection();

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
                dbConnection = gson.fromJson(json, DBConnection.class);

            } catch (FileNotFoundException e) {
                System.out.println("Non se encontra o arquivo");
            } catch (IOException e) {
                System.out.println("Erro de entrada saída");
            }
        }
        return dbConnection;
    }
    
}
