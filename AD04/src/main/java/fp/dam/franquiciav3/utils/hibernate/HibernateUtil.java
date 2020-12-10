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
import fp.dam.franquiciav3.modelo.Cliente;
import fp.dam.franquiciav3.modelo.Empleado;
import fp.dam.franquiciav3.modelo.EmpleadosTiendas;
import fp.dam.franquiciav3.modelo.Producto;
import fp.dam.franquiciav3.modelo.ProductosTiendas;
import fp.dam.franquiciav3.modelo.Provincia;
import fp.dam.franquiciav3.modelo.Tienda;
import fp.dam.franquiciav3.utils.json.LeerJsonConfig;
import java.util.Properties;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static final ConfigHibernate configHibernate = LeerJsonConfig.leerJson();

    //Este método devolve a sesión para poder facer operacións coa base de datos
    public static SessionFactory getSessionFactory() {

        //Se a sesion non se configurou, creamolo
        if (sessionFactory == null) {
            try {
                Configuration conf = new Configuration();

                //Engadimos as propiedades
                Properties settings = new Properties();

                //Indicamos o conector da base de datos que vamos a usar
                settings.put(Environment.DRIVER, configHibernate.getHibernate().getDriver());

                //Indicamos a localización da base de datos que vamos a utilizar              
                settings.put(Environment.URL, "jdbc:mysql://" + configHibernate.getDbConnection().getAddress()
                        + ":" + configHibernate.getDbConnection().getPort()
                        + "/" + configHibernate.getDbConnection().getName());

                //Indicamos o usuario da base de datos con cal nos vamos conectar e o seu contrasinal
                settings.put(Environment.USER, configHibernate.getDbConnection().getUser());
                settings.put(Environment.PASS, configHibernate.getDbConnection().getPassword());

                //Indicamos o dialecto que ten que usar Hibernate 
                settings.put(Environment.DIALECT, configHibernate.getHibernate().getDialect());

                //Indicamos que se as táboas todas se borren e se volvan crear
                settings.put(Environment.HBM2DDL_AUTO, configHibernate.getHibernate().getHBM2DDL_AUTO());

                //Indicamos que se mostre as operacións SQL que Hibernate leva a cabo
                settings.put(Environment.SHOW_SQL, configHibernate.getHibernate().getSHOW_SQL());
                conf.setProperties(settings);

                //Engaidmos aquelas clases nas que queremos facer persistencia
                conf.addAnnotatedClass(Provincia.class);
                conf.addAnnotatedClass(Tienda.class);
                conf.addAnnotatedClass(Producto.class);
                conf.addAnnotatedClass(Cliente.class);
                conf.addAnnotatedClass(ProductosTiendas.class);
                conf.addAnnotatedClass(Empleado.class);
                conf.addAnnotatedClass(EmpleadosTiendas.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
                sessionFactory = conf.buildSessionFactory(serviceRegistry);
            } catch (HibernateException e) {
            }
        }
        return sessionFactory;
    }

    public void close() {
        if (sessionFactory != null && sessionFactory.isOpen()) {
            sessionFactory.close();
        }
    }

}
