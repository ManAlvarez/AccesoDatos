/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fp.dam.franquiciav3.db;

import fp.dam.franquiciav3.modelo.Cliente;
import fp.dam.franquiciav3.modelo.Empleado;
import fp.dam.franquiciav3.modelo.EmpleadosTiendas;
import fp.dam.franquiciav3.modelo.Producto;
import fp.dam.franquiciav3.modelo.ProductosTiendas;
import fp.dam.franquiciav3.modelo.Provincia;
import fp.dam.franquiciav3.modelo.Tienda;
import fp.dam.franquiciav3.utils.hibernate.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author manuel
 */
public class ControladorDB {

    private static final Session session = HibernateUtil.getSessionFactory().openSession();

    /**
     * Método para eliminar un elemento de la base de datos.
     *
     * @param elemento
     * @return
     */
    public boolean anadirElemento(Object elemento) {
        if (elemento != null) {
            try {
                session.beginTransaction();
                session.save(elemento);
                session.getTransaction().commit();
                return true;
            } catch (Exception e) {
                session.getTransaction().rollback();
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Método para updatear un elemento en la base de datos;
     *
     * @param element
     * @return
     */
    public boolean updateElement(Object element) {
        if (element != null) {
            try {
                session.beginTransaction();
                session.update(element);
                session.getTransaction().commit();
                return true;
            } catch (Exception e) {
                session.getTransaction().rollback();
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Método para eliminar un elemento.
     *
     * @param elemento
     * @return
     */
    public boolean eliminarElemento(Object elemento) {
        if (elemento != null) {
            try {
                session.beginTransaction();
                session.delete(elemento);
                session.getTransaction().commit();
                return true;
            } catch (Exception e) {
                session.getTransaction().rollback();
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Métodos para mostrar todas las provincias.
     */
    public void mostrarProvincias() {
        Query hql = session.createQuery("SELECT p FROM Provincia p");
        List<Provincia> provincias = hql.list();
        if (provincias.isEmpty()) {
            System.out.println("No existen provincias.");
        } else {
            System.out.println("PROVINCIAS:");
            for (Provincia provinciaAux : provincias) {
                System.out.println(provinciaAux.toStringMostrar());
            }
        }
    }

    /**
     * Método para mostrar todas las tiendas.
     */
    public void mostrarTiendas() {
        Query hql = session.createQuery("SELECT t FROM Tienda t");
        List<Tienda> tiendas = hql.list();
        if (tiendas.isEmpty()) {
            System.out.println("No existen tiendas.");
        } else {
            System.out.println("TIENDAS: ");
            for (Tienda tiendaAux : tiendas) {
                System.out.println(tiendaAux.toStringMostrar());
            }
        }

    }

    /**
     * Método para mostrar todos los productos.
     */
    public void mostrarProductos() {
        Query hql = session.createQuery("SELECT p FROM Producto p");
        List<Producto> productos = hql.list();
        if (productos.isEmpty()) {
            System.out.println("No existen productos.");
        } else {
            System.out.println("PRODUCTOS:");
            for (Producto productoAux : productos) {
                System.out.println(productoAux.toStringMostrar());
            }
        }
    }

    /**
     * Método para mostrar los productos de una tienda
     *
     * @param tienda
     */
    public void mostrarProductosTienda(Tienda tienda) {
        Query hql = session.createQuery("SELECT pt FROM ProductosTiendas pt WHERE pt.tienda=:tienda");
        hql.setParameter("tienda", tienda);
        List<ProductosTiendas> productosTienda = hql.list();
        if (productosTienda.isEmpty()) {
            System.out.println("No hay productos en la tienda.");
        } else {
            System.out.println("Tienda: " + tienda.getNombre() + "\nProvincia: " + tienda.getProvincia().getNome() + "\nCiudad: " + tienda.getCiudad() + "\n");
            for (ProductosTiendas productoTiendaAux : productosTienda) {
                System.out.println(productoTiendaAux.toStringMostrar());
            }
        }
    }

    /**
     * Método para mostrar los empleados de una tienda.
     *
     * @param tienda
     */
    public void mostrarEmpleadosTienda(Tienda tienda) {
        Query hql = session.createQuery("SELECT et FROM EmpleadosTiendas et WHERE et.tienda=:tienda");
        hql.setParameter("tienda", tienda);
        List<EmpleadosTiendas> empleadosTienda = hql.list();
        if (empleadosTienda.isEmpty()) {
            System.out.println("No hay empleados en la tienda.");
        } else {
            System.out.println("Tienda: " + tienda.getNombre() + "\nProvincia: " + tienda.getProvincia().getNome() + "\nCiudad: " + tienda.getCiudad() + "\n");
            for (EmpleadosTiendas empleadoTiendaAux : empleadosTienda) {
                System.out.println(empleadoTiendaAux.toStringMostrar());
            }
        }
    }

    /**
     * Método para mostrar todos los clientes.
     */
    public void mostrarClientes() {
        Query hql = session.createQuery("SELECT c FROM Cliente c");
        List<Cliente> clientes = hql.list();
        if (clientes.isEmpty()) {
            System.out.println("No existen clientes.");
        } else {
            System.out.println("CLIENTES: ");
            for (Cliente clienteAux : clientes) {
                System.out.println(clienteAux.toStringMostrar());
            }
        }
    }

    /**
     * Método para mostrar los empleados de la base de datos.
     */
    public void mostrarEmpleados() {
        Query hql = session.createQuery("SELECT e FROM Empleado e");
        List<Empleado> empleados = hql.list();
        if (empleados.isEmpty()) {
            System.out.println("No existen empleados.");
        } else {
            System.out.println("CLIENTES: ");
            for (Empleado empleadoAux : empleados) {
                System.out.println(empleadoAux.toStringMostrar());
            }
        }
    }

    /**
     * Método para obtener el stock de los productos de una tienda.
     *
     * @return
     */
    public List<ProductosTiendas> obtenerStock() {
        Query hql = session.createQuery("SELECT s FROM ProductosTiendas s");
        List<ProductosTiendas> stocks = hql.list();
        if (stocks.isEmpty()) {
            return null;
        } else {
            return stocks;
        }
    }

    /**
     * Método para obtener las tiendas.
     *
     * @return
     */
    public List<Tienda> obtenerTiendas() {
        Query hql = session.createQuery("SELECT t FROM Tienda t");
        List<Tienda> tiendas = hql.list();
        if (tiendas.isEmpty()) {
            return null;
        } else {
            return tiendas;
        }
    }

    /**
     * Método para obtener las productos.
     *
     * @return
     */
    public List<Producto> obtenerProductos() {
        Query hql = session.createQuery("SELECT p FROM Producto p");
        List<Producto> productos = hql.list();
        if (productos.isEmpty()) {
            return null;
        } else {
            return productos;
        }
    }

    /**
     * Método para seleccionar una tienda.
     *
     * @param idTienda
     * @return
     */
    public Tienda selectTienda(int idTienda) {
        Query hql = session.createQuery("SELECT t FROM Tienda t WHERE t.id=:id");
        hql.setParameter("id", idTienda);
        List<Tienda> tiendas = hql.list();
        if (tiendas.isEmpty()) {
            return null;
        } else {
            return tiendas.get(0);
        }
    }

    /**
     * Método para seleccionar todos los stock de una tienda.
     *
     * @param tienda
     * @return
     */
    public List<ProductosTiendas> selectTiendasEnProductosTienda(Tienda tienda) {
        Query hql = session.createQuery("SELECT t FROM ProductosTiendas t WHERE t.tienda=:tienda");
        hql.setParameter("tienda", tienda);
        List<ProductosTiendas> tiendas = hql.list();
        if (tiendas.isEmpty()) {
            return null;
        } else {
            return tiendas;
        }
    }

    /**
     * Método para seleccionar todos los stock de un producto.
     *
     * @param producto
     * @return
     */
    public List<ProductosTiendas> selectTiendasEnProductosTienda(Producto producto) {
        Query hql = session.createQuery("SELECT p FROM ProductosTiendas p WHERE p.producto=:producto");
        hql.setParameter("producto", producto);
        List<ProductosTiendas> productos = hql.list();
        if (productos.isEmpty()) {
            return null;
        } else {
            return productos;
        }
    }

    /**
     * Método para seleccionar todos los registros de la tienda en
     * EmpladosTiendas.
     *
     * @param tienda
     * @return
     */
    public List<EmpleadosTiendas> selectTiendasEnEmpleadosTiendas(Tienda tienda) {
        Query hql = session.createQuery("SELECT t FROM EmpleadosTiendas t WHERE t.tienda=:tienda");
        hql.setParameter("tienda", tienda);
        List<EmpleadosTiendas> tiendas = hql.list();
        if (tiendas.isEmpty()) {
            return null;
        } else {
            return tiendas;
        }
    }

    /**
     * Método para seleccionar todos los registros del empleado en
     * EmpleadosTiendas.
     *
     * @param empleado
     * @return
     */
    public List<EmpleadosTiendas> selectEmpleadosEnEmpleadosTiendas(Empleado empleado) {
        Query hql = session.createQuery("SELECT e FROM EmpleadosTiendas e WHERE e.empleado=:empleado");
        hql.setParameter("empleado", empleado);
        List<EmpleadosTiendas> empleados = hql.list();
        if (empleados.isEmpty()) {
            return null;
        } else {
            return empleados;
        }
    }

    /**
     * Método para seleccionar un producto.
     *
     * @param idProducto
     * @return
     */
    public Producto selectProducto(int idProducto) {
        Query hql = session.createQuery("SELECT p FROM Producto p WHERE p.id=:id");
        hql.setParameter("id", idProducto);
        List<Producto> productos = hql.list();
        if (productos.isEmpty()) {
            System.out.println("No existen productos.");
            return null;
        } else {
            return productos.get(0);
        }
    }

    /**
     * Método para seleccionar un cliente.
     *
     * @param idCliente
     * @return
     */
    public Cliente selectCliente(int idCliente) {
        Query hql = session.createQuery("SELECT c FROM Cliente c WHERE c.id=:id");
        hql.setParameter("id", idCliente);
        List<Cliente> clientes = hql.list();
        if (clientes.isEmpty()) {
            System.out.println("No existen clientes.");
            return null;
        } else {
            return clientes.get(0);
        }
    }

    /**
     * Método para seleccionar un empleado por id.
     *
     * @param idEmpleado
     * @return
     */
    public Empleado selectEmpleado(int idEmpleado) {
        Query hql = session.createQuery("SELECT p FROM Empleado p WHERE p.id=:id");
        hql.setParameter("id", idEmpleado);
        List<Empleado> empleados = hql.list();
        if (empleados.isEmpty()) {
            System.out.println("No existen empleados.");
            return null;
        } else {
            return empleados.get(0);
        }
    }

    /**
     * Método para comprobar si existe una tienda con los parámetros
     * especificados.
     *
     * @param nombre
     * @param idProvincia
     * @param ciudad
     * @return
     */
    public boolean existeTienda(String nombre, int idProvincia, String ciudad) {
        Query hql = session.createQuery("SELECT t FROM Tienda t WHERE t.nombre=:nombre");
        hql.setParameter("nombre", nombre);
        List<Tienda> tiendas = hql.list();
        if (tiendas.isEmpty()) {
            return false;
        } else {
            for (Tienda tiendaAux : tiendas) {
                if (tiendaAux.getProvincia().getId() == idProvincia) {
                    if (tiendaAux.getCiudad().equalsIgnoreCase(ciudad)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /**
     * Método para comprobar si existe un empleado con los parémetros
     * especificados.
     *
     * @param empleado
     * @param semana
     * @return
     */
    public boolean existeEmpleado(Empleado empleado, int semana) {
        Query hql = session.createQuery("SELECT et FROM EmpleadosTiendas et");
        List<EmpleadosTiendas> empleados = hql.list();
        if (empleados.isEmpty()) {
            return false;
        } else {
            for (EmpleadosTiendas empleadoAux : empleados) {
                if (empleadoAux.getEmpleado().getId() == empleado.getId()) {
                    if (empleadoAux.getSemana() == semana) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /**
     * Método para comprobar si existe un producto con los parámetros
     * especificados.
     *
     * @param nombre
     * @param descripcion
     * @return
     */
    public boolean existeProducto(String nombre, String descripcion) {
        Query hql = session.createQuery("SELECT p FROM Producto p WHERE p.nombre=:nombre");
        hql.setParameter("nombre", nombre);
        List<Producto> productos = hql.list();
        if (productos.isEmpty()) {
            return false;
        } else {
            for (Producto productoAux : productos) {
                if (productoAux.getDescripcion().equalsIgnoreCase(descripcion)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Método para comprobar si existe un cliente con los parámetros indicados.
     *
     * @param email
     * @return
     */
    public boolean existeCliente(String email) {
        Query hql = session.createQuery("SELECT c FROM Cliente c WHERE c.email=:email");
        hql.setParameter("email", email);
        List<Cliente> clientes = hql.list();
        if (clientes.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Método para comprobar si existe un empleado con los parámetros indicados.
     *
     * @param usuario
     * @return
     */
    public boolean existeEmpleado(String usuario) {
        Query hql = session.createQuery("SELECT e FROM Empleado e WHERE e.usuario=:usuario");
        hql.setParameter("usuario", usuario);
        List<Cliente> clientes = hql.list();
        if (clientes.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

}
