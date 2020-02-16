/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestiondetiendas;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import javax.persistence.EntityManagerFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;
import org.hibernate.query.Query;
import utilidades.ConfigHibernate;

/**
 *
 * @author Jose Miguel
 */
public class Empresa {
    private final EntityManagerFactory entityManagerFactory;
    private static Session session;

    public Empresa() throws Exception {
        Gson gson = new Gson();
        Configuration conf = new Configuration();
        Properties properties = new Properties();

        try {
            JsonReader jr = new JsonReader(new FileReader("config.json"));
            ConfigHibernate config = gson.fromJson(jr, ConfigHibernate.class);

            ConfigHibernate.DbConnection dbConnection = config.getDbConnection();
            ConfigHibernate.Hibernate hibernate = config.getHibernate();

            properties.put(Environment.URL, "jdbc:mysql://"
                    + dbConnection.getAddress()
                    + ":" + dbConnection.getPort()
                    + "/" + dbConnection.getName());
            properties.put(Environment.USER, dbConnection.getUser());
            properties.put(Environment.PASS, dbConnection.getPassword());

            properties.put(Environment.DRIVER, hibernate.getDriver());
            properties.put(Environment.DIALECT, hibernate.getDialect());
            properties.put(Environment.HBM2DDL_AUTO, hibernate.getHBM2DDL_AUTO());
            properties.put(Environment.SHOW_SQL, hibernate.getSHOW_SQL());

            conf.setProperties(properties);

            conf.addAnnotatedClass(Clientes.class);
            conf.addAnnotatedClass(Provincia.class);
            conf.addAnnotatedClass(Tiendas.class);
            conf.addAnnotatedClass(Productos.class);
            conf.addAnnotatedClass(ProductosTiendasID.class);
            conf.addAnnotatedClass(ProductosTiendas.class);
            conf.addAnnotatedClass(Empleados.class);
            conf.addAnnotatedClass(EmpleadosTiendasID.class);
            conf.addAnnotatedClass(EmpleadosTiendas.class);
        } catch (FileNotFoundException | JsonSyntaxException e) {
            e.printStackTrace();
            throw new Exception(e);
        }


        final StandardServiceRegistry registry =
                new StandardServiceRegistryBuilder()
                        .applySettings(conf.getProperties())
                        .build();

        try {
            entityManagerFactory = conf
                    .buildSessionFactory(registry);
        } catch (Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
            throw new Exception(e);
        }


        session = entityManagerFactory.createEntityManager().unwrap(Session.class);
    }

    public void close() {
        if (session != null && session.isOpen()) {
            session.close();
        }

        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

    // Añade cada objeto a la base de datos.
    private boolean añadirElemento(Object element) {
        if (element != null) {
            try {
                session.beginTransaction();
                session.save(element);
                session.getTransaction().commit();
                return true;
            } 
            catch (Exception e) {
                e.printStackTrace();
                // IMPORTANTE HACER ROLLBACK
                session.getTransaction().rollback();
                return false;
            }
        } else {
            return false;
        }
    }

    //Crea un objeto de la clase Tienda y lo añade a la base de datos.
    public boolean añadirTienda(String nombre, String ciudad, Provincia provincia) {
        Tiendas tienda = new Tiendas(nombre, ciudad, provincia);

        if (añadirElemento(tienda)) {
            return tienda.getId() > 0;
        } else {
            return false;
        }
    }

    // Crea un objeto de la clase Producto y lo añade a la base de datos.
    public boolean añadirProducto(String nombre, String descripcion, float precio) {
        Productos producto = new Productos(nombre, descripcion, precio);

        if (añadirElemento(producto)) {
            return producto.getId() > 0;
        } else {
            return false;
        }
    }

    //Crea un objeto de la clase Cliente y lo añade a la base de datos.
    public boolean añadirCliente(String nombre, String apellidos, String mail) {
        Clientes cliente = new Clientes(nombre, apellidos, mail);

        if (añadirElemento(cliente)) {
            return cliente.getIdC() > 0;
        } else {
            return false;
        }
    }

    //Crea un objeto de la clase Empleado y lo  añade a la base de datos.
    public boolean añadirEmpleado(String nombre, String apellidos) {
        Empleados empleado = new Empleados(nombre, apellidos);

        if (añadirElemento(empleado)) {
            return empleado.getId() > 0;
        }
        else {
            return false;
        }
    }

    // Crea un objeto de la clase Provincia y lo añade a la base de datos.
    public boolean añadirProvincia(Provincia provincia) {
        return añadirElemento(provincia);
    }

    // Añade un producto a una tienda con un stock inicial.
    public boolean añadirProductoATienda(Productos producto, Tiendas tienda, Long stock) {
        ProductosTiendas relProductoTienda = new ProductosTiendas(producto, tienda, stock);

        if (añadirElemento(relProductoTienda)) {
            tienda.addProducto(producto, stock);
            return true;
        } else {
            return false;
        }
    }

    //Añade un empleado a una tienda con una jornada  semana determinada.
    public boolean añadirEmpleadoATienda(Empleados empleado, Tiendas tienda, Float horas) {
        EmpleadosTiendas relEmpleadoTienda = new EmpleadosTiendas(empleado, tienda, horas);

        if (añadirElemento(relEmpleadoTienda)) {
            tienda.addEmpleado(empleado, horas);
            return true;
        } else {
            return false;
        }
    }


    // Elimina un objeto de la base de datos.
    private boolean eliminarElemento(Object element) {
        if (element != null) {
            try {
                session.beginTransaction();
                session.delete(element);
                session.getTransaction().commit();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                // IMPORTANTE HACER ROLLBACK
                session.getTransaction().rollback();
                return false;
            }
        } else {
            return false;
        }
    }

    //Elimina un objeto Tiendas de la base de datos.
    public boolean eliminarTienda(Tiendas tienda) {
        if (tienda.getId() != null && getTienda(tienda.getId()) != null) {
            if (eliminarElemento(tienda)) {
                return getTienda(tienda.getId()) == null;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //Elimina un objeto Productos de la base de datos.
    public boolean eliminarProducto(Productos producto) {
        if (producto.getId() != null && getProducto(producto.getId()) != null) {
            if (eliminarElemento(producto)) {
                return getProducto(producto.getId()) == null;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //Elimina un objeto Clientes de la base de datos.
    public boolean eliminarCliente(Clientes cliente) {
        if (cliente.getIdC() != null && getCliente(cliente.getIdC()) != null) {
            if (eliminarElemento(cliente)) {
                return getCliente(cliente.getIdC()) == null;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //Elimina un objeto Empleados de la base de datos.
    public boolean eliminarEmpleado(Empleados empleado) {
        if (empleado.getId() != null && getEmpleado(empleado.getId()) != null) {
            if (eliminarElemento(empleado)) {
                return getEmpleado(empleado.getId()) == null;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //Elimina de la base de datos un producto de una tienda.
    public boolean eliminarProductoDeTienda(ProductosTiendas relProductoTienda) {
        if (getRelProductoTienda(relProductoTienda.getId()) != null) {
            if (eliminarElemento(relProductoTienda)) {
                // Tras eliminar la relación entre producto y tienda de la base
                // de datos, ya no debería encontrarse al buscarla.
                return getRelProductoTienda(relProductoTienda.getId()) == null;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //Elimina de la base de datos un empleado de una tienda.
    public boolean eliminarEmpleadoDeTienda(EmpleadosTiendas relEmpleadoTienda) {
        if (getRelEmpleadoTienda(relEmpleadoTienda.getId()) != null) {
            if (eliminarElemento(relEmpleadoTienda)) {
                // Tras eliminar la relación entre producto y tienda de la base
                // de datos, ya no debería encontrarse al buscarla.
                return getRelEmpleadoTienda(relEmpleadoTienda.getId()) == null;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }



    //Actualiza un elemento de la base de datos.
    private boolean actualizarElemento(Object element) {
        if (element != null) {
            try {
                session.beginTransaction();
                session.update(element);
                session.getTransaction().commit();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                // IMPORTANTE HACER ROLLBACK
                session.getTransaction().rollback();
                return false;
            }
        } else {
            return false;
        }
    }

    //Actualiza el stock de un producto en una tienda.
    public boolean actualizarStock(ProductosTiendas relProductoTienda, Long nuevoStock) {
        relProductoTienda.setStock(nuevoStock);
        return actualizarElemento(relProductoTienda);
    }

    //Actualiza la jornada laboral de un trabajador en una tienda.
    public boolean actualizarJornada(EmpleadosTiendas relEmpleadoTienda, Float newJornada) {
        relEmpleadoTienda.setJornada(newJornada);
        return actualizarElemento(relEmpleadoTienda);
    }

    /*Recupera un elemento de la base de datos a partir de un ID.
    Devuelve null si no existe tal elemento.*/
    private <T, U> T getElement(U id, Class<T> type) {
        if (id != null) {
            return session.find(type, id);
        } else {
            return null;
        }
    }

    /* Obtiene una lista de objetos de la base de datos, dada una consulta
      determinada y la clase de los objetos.*/
    private <T> List<T> getElements(String query, Class<T> type) {
        return session.createQuery(query, type).getResultList();
    }

    //Obtiene una tienda de la base de datos a partir de su ID.
    public Tiendas getTienda(Long id) {
        return getElement(id, Tiendas.class);
    }

    //Obtiene una producto de la base de datos a partir de su ID.
    public Productos getProducto(Long id) {
        return getElement(id, Productos.class);
    }

    //Obtiene un cliente de la base de datos a partir de su ID.
    public Clientes getCliente(Long id) {
        return getElement(id, Clientes.class);
    }

    //Obtiene un empleado de la base de datos a partir de su ID.
    public Empleados getEmpleado(Long id) {
        return getElement(id, Empleados.class);
    }

    //Obtiene una provincia de la base de datos a partir de su ID.
    public Provincia getProvincia(Long id) {
        return getElement(id, Provincia.class);
    }

    //Obtiene un producto de una tienda.
    public ProductosTiendas getRelProductoTienda(ProductosTiendasID id) {
        return getElement(id, ProductosTiendas.class);
    }

    //Obtiene un empleado de una tienda.
    public EmpleadosTiendas getRelEmpleadoTienda(EmpleadosTiendasID id) {
        return getElement(id, EmpleadosTiendas.class);
    }

    //Obtiene la lista de todas las tiendas existentes.
    public List<Tiendas> getTiendas() {
        return getElements(
                "from Tiendas order by nombre_t, provincia.nome, ciudad",
                Tiendas.class);
    }

    //Obtiene la lista de todos los productos existentes.
    public List<Productos> getProductos() {
        return getElements(
                "from Productos order by nombre_p",
                Productos.class);
    }

    //Obtiene una lista de todos los clentes existentes.
    public List<Clientes> getClientes() {
        return getElements(
                "from Clientes order by nombre_c, apellidos",
                Clientes.class);
    }

    //Obtiene una lista de todos los empleados existentes.
    public List<Empleados> getEmpleados() {
        return getElements(
                "from Empleados order by nombre_e, apellidos",
                Empleados.class);
    }

    //Obtiene una lista de todas las provincias existentes.
    public List<Provincia> getProvincias() {
        return getElements(
                "from Provincia order by id",
                Provincia.class);
    }
}
