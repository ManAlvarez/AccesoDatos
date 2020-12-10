/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.franquiciav2;

import fp.dam.franquiciav2.db.ControladorDB;
import fp.dam.franquiciav2.db.DriverDB;
import fp.dam.franquiciav2.json.TratarJSON;


/**
 *
 * @author manuel
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        // TODO code application logic here  
        ControladorDB db = new ControladorDB();
        DriverDB driverDB = new DriverDB();
        db.crearDB();    
        db.insertarDatosProvincias(driverDB.connectDatabase(),TratarJSON.leerJson());
        Menu menu = new Menu();
        menu.menu();
    }
    
}
