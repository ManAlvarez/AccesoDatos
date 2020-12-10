/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.franquiciav3.utils.hibernate;

import java.io.Serializable;

/**
 *
 * @author manuel
 */
public class ConfigHibernate implements Serializable {

    private DbConnection dbConnection;
    private Hibernate hibernate;

    public DbConnection getDbConnection() {
        return dbConnection;
    }

    public Hibernate getHibernate() {
        return hibernate;
    }

}
