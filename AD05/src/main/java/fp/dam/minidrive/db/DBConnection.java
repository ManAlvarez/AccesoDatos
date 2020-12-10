
package fp.dam.minidrive.db;

import java.io.Serializable;

/**
 *
 * @author manuel
 */
public class DBConnection implements Serializable{

    private DbConnection dbConnection;
    private App app;
    
    /**
     * Constructor sin parámetros.
     */

    public DBConnection() {
    }
    
    /**
     * Método para obtener los datos de la conexión.
     * @return dbConnection
     */

    public DbConnection getDbConnection() {
        return dbConnection;
    }
    
    /**
     * Método para insertar los datos de la conexión.
     * @param dbConnection 
     */

    public void setDbConnection(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }
    
    /**
     * Método para obtener los datos de la app.
     * @return app
     */

    public App getApp() {
        return app;
    }

    /**
     * Método para insertar los datos de la app.
     * @param app 
     */
    
    public void setApp(App app) {
        this.app = app;
    }
  
}
