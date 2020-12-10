/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.franquiciav3.utils.hibernate;

/**
 *
 * @author manuel
 */
public class Hibernate {

    private String driver;
    private String dialect;
    private String HBM2DDL_AUTO;
    private boolean SHOW_SQL;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public String getHBM2DDL_AUTO() {
        return HBM2DDL_AUTO;
    }

    public void setHBM2DDL_AUTO(String HBM2DDL_AUTO) {
        this.HBM2DDL_AUTO = HBM2DDL_AUTO;
    }

    public boolean getSHOW_SQL() {
        return SHOW_SQL;
    }

    public void setSHOW_SQL(boolean SHOW_SQL) {
        this.SHOW_SQL = SHOW_SQL;
    }
}
