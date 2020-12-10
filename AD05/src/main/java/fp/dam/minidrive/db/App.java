
package fp.dam.minidrive.db;

import java.io.Serializable;

/**
 *
 * @author manuel
 */
public class App implements Serializable{
    
    private String directory;

    /**
     * Constructor sin parámetros. 
     */
    
    public App() {
    }
    
    /**
     * Método para obtener el directorio.
     * @return directory
     */
    
    public String getDirectory() {
        return directory;
    }
    
    /**
     * Método para insertar un directorio.
     * @param directory 
     */

    public void setDirectory(String directory) {
        this.directory = directory;
    }
      
}
