/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestiondetiendas;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;


/**
 *
 * @author Jose Miguel
 */
public class MenuGestionPrincipal {
    static String nombre_t, ciudad, nombre_pro;
    static int id_pro;
    static int c= 0, idPro;
    static Empresa empresa;
    /**
     * @param args
     * @throws java.io.IOException
     */    
    public static void main(String[] args) throws IOException, Exception {
        empresa = new Empresa();
        insertarProvinciasBD();
        comprobarProvincias();
        System.out.println("\n|---------------------------|");
        System.out.println("|App de gestión [Franquicia]|");
        System.out.println("|---------------------------|");
        String nombre_t, nombre_pd, nombre_c, nombre_e,ciudad,codigoId,descripcion,apellidos, apellidos_c,email;
        float precio, horas_trabajadas;
        int cantidad, i, c;
        Long stock, nuevoStock;
        Provincia provincia;
        Tiendas tienda;
        Productos producto;
        Empleados empleado;
        Clientes cliente;
        boolean cierre = false;        
        while(cierre==false){
            Scanner teclado = new Scanner(System.in);
            System.out.println("\n<-----------------MENÚ----------------->");
            System.out.println("[1] Añadir una tienda.");
            System.out.println("[2] Mostrar todas las tiendas.");
            System.out.println("[3] Eliminar una tienda.");
            System.out.println("[4] Añadir un producto a la franquicia.");
            System.out.println("[5] Mostrar los productos de la franquicia.");
            System.out.println("[6] Añadir un producto a una tienda.");
            System.out.println("[7] Mostrar los productos de una tienda.");
            System.out.println("[8] Actualizar el stock de un producto de una tienda.");
            System.out.println("[9] Mostrar el stock de un producto de una tienda.");
            System.out.println("[10] Eliminar un producto de la franquicia.");
            System.out.println("[11] Eliminar un producto de una  tienda.");
            System.out.println("[12] Añadir un empleado a la franquicia.");
            System.out.println("[13] Mostrar los empleados de la franquicia.");
            System.out.println("[14] Añadir un empleado a una tienda.");
            System.out.println("[15] Eliminar un empleado de una tienda.");
            System.out.println("[16] Añadir un cliente.");
            System.out.println("[17] Mostrar los clientes.");
            System.out.println("[18] Eliminar un cliente.");
            System.out.println("[19] Generar informe del stock en formato JSON.");
            System.out.println("[20] Leer los titulares del periódico El País.");
            System.out.print("[0] Salir del programa.");
            System.out.println("\n<-------------------------------------->\n");
            
            System.out.println("Introduce el número de la opción del menú:");
            
            boolean control = false;
            boolean controlB = false;
            boolean control2 = false;
            boolean control3 = true;
            String opcion = teclado.nextLine();
            switch(opcion){                        
                case "1":
                    System.out.println("Introduce el nombre de la nueva tienda:");
                    nombre_t = teclado.nextLine();
                    System.out.println("Introduce la ciudad de la nueva tienda:");
                    ciudad = teclado.nextLine();
                    System.out.println("Introduce el ID de la provincia de la nueva tienda"
                                        + "(si quieres ver todas las provincias introduce -V-):");
                    provincia = selectProvincias();

                    if (provincia != null) {
                        if (empresa.añadirTienda(nombre_t, ciudad, provincia)) {
                            System.out.println("Tienda añadida correctamente");
                        }
                        else {
                            System.out.println("¡Error! La tienda no se ha añadido.");
                        }
                    }
                    System.out.println("Redirigiendo al menú...");
                    break;
                case "2":
                    List<Tiendas> tiendas = empresa.getTiendas();
                    System.out.println("\n-Tiendas-");        
                    if (!tiendas.isEmpty()) {
                        mostrarLista(tiendas);
                        System.out.println("\nRedirigiendo al menú...");
                    }
                    else {
                        System.out.println("No hay tiendas para mostrar.");
                        System.out.println("\nRedirigiendo al menú...");
                    }                    
                    break;
                case "3":
                    System.out.println("Introduce el ID de la tienda a eliminar"
                                        + "(si quieres ver las tiendas existentes introduce -V-):");
                    tienda = selectTienda();
                    if (tienda != null ) {
                        if (empresa.eliminarTienda(tienda)) {
                            System.out.println("¡Tienda eliminada correctamente!");
                        }
                    }
                    System.out.println("\nRedirigiendo al menú...");
                    break;
                case "4":
                    System.out.println("Introduce el nombre del nuevo producto:");
                    nombre_pd = teclado.nextLine();
                    System.out.println("Introduce la descripción del nuevo producto:");
                    descripcion = teclado.nextLine();
                    System.out.println("Introduce el precio del nuevo producto:");
                    precio = teclado.nextFloat();
                    if (empresa.añadirProducto(nombre_pd, descripcion, precio)) {
                        System.out.println("¡Producto añadido satisfactoriamente!");
                    } 
                    else {
                        System.out.println("¡Error! El producto no se ha añadido.");
                    }
                    System.out.println("Redirigiendo al menú...");
                    break;
                case "5":
                    System.out.println("\n-Productos de la franquicia-");
                    List<Productos> productos = empresa.getProductos();
                    if (!productos.isEmpty()) {
                        mostrarLista(productos);
                    }
                    else {
                        System.out.println("No hay productos para mostrar.");
                    }
                    System.out.println("\nRedirigiendo al menú...");
                    break;
                case "6":
                    System.out.println("Introduce el ID de la tienda a la que añadir el producto"
                                        + "(si quieres ver las tiendas existentes introduce -V-):");
                    tienda = selectTienda();
                    if(tienda != null){
                        System.out.println("Introduce el ID del producto a añadir"
                                        + "(si quieres ver los productos existentes introduce -V-):");
                        producto = selectProducto();
                        if (producto != null){
                            System.out.println("Introduce el stock del producto a introducir");
                            stock = teclado.nextLong();
                            if (empresa.añadirProductoATienda(producto, tienda, stock)) {
                                System.out.println("¡Producto añadido satisfactoriamente a la tienda!");
                            } 
                            else {
                                System.out.println("¡Error! El producto no ha sido añadido a la tienda.");
                            }
                        }
                    }
                    System.out.println("\nRedirigiendo al menú...");
                    break;
                case "7":
                    System.out.println("Introduce el ID de la tienda de la que mostrar los productos"
                                        + "(si quieres ver las tiendas existentes introduce -V-):");
                    tienda = selectTienda();
                    if (tienda != null) {
                        List<ProductosTiendas> relsTiendaProductos = tienda.getProductos();
                        if (!relsTiendaProductos.isEmpty()) {
                            for (ProductosTiendas rel : relsTiendaProductos) {
                                producto = rel.getProducto();
                                System.out.println("-Producto "+producto.getId() + "-"
                                    + "\n\t" +
                                        producto.getNombre()
                                    + "\n\t" + producto.getDescripcion()
                                    + "\n\t" + producto.getPrecio() + "€"
                                    + "\n\t" + rel.getStock() + "(stock)"
                                );
                            }
                        }
                        else {
                            System.out.println("La tienda seleccionada no tiene productos.");
                        }
                    }
                    System.out.println("\nRedirigiendo al menú...");
                    break;
                case "8":
                    System.out.println("Introduce el ID de la tienda de la que actualizar el stock de un producto"
                                        + "(si quieres ver las tiendas existentes introduce -V-):");
                    tienda = selectTienda();
                    if (tienda != null) {
                        ProductosTiendas relProductoTienda = selectProductoDeTienda(tienda, true);
                        if (relProductoTienda != null) {
                            System.out.println("Introduce el nuevo stock:");
                            nuevoStock = teclado.nextLong();
                            if (empresa.actualizarStock(relProductoTienda, nuevoStock)) {
                                System.out.println("¡Stock actualizado satisfactoriamente!");
                            } 
                            else {
                                System.out.println("¡Error! El stock no se actualizó correctamente.");
                            }
                        }
                    }
                    System.out.println("\nRedirigiendo al menú...");  
                    break;
                case "9":
                    mostrarStock();
                    System.out.println("\nRedirigiendo al menú...");
                    break;
                case "10":
                    System.out.println("Introduce el ID del producto a eliminar"
                                        + "(si quieres ver los productos existentes introduce -V-):");
                    producto = selectProducto();
                    if (producto != null ) {
                        System.out.println();
                        if (empresa.eliminarProducto(producto)) {
                            System.out.println("¡Producto eliminado satisfactoriamente!");
                        } 
                        else {
                            System.out.println("¡Error! El producto no ha sido eliminado.");
                        }
                    }
                    System.out.println("\nRedirigiendo al menú...");
                    break;
                case "11":
                    System.out.println("Introduce el ID de la tienda a eliminar"
                                        + "(si quieres ver las tiendas existentes introduce -V-):");
                    tienda = selectTienda();

                    if (tienda != null) {
                        ProductosTiendas relProductoTienda = selectProductoDeTienda(tienda, true);

                        if (relProductoTienda != null ) {
                            if (empresa.eliminarProductoDeTienda(relProductoTienda)) {
                                tienda.delProducto(relProductoTienda.getProducto());
                                System.out.println("¡El producto ha sido eliminado de la tienda satisfactoriamente!");
                            } 
                            else {
                                System.out.println("¡Error! El producto no ha sido eliminado.");
                            }
                        }
                    }
                    break;
                case "12":
                    System.out.println("Introduce el nombre del nuevo empleado:");
                    nombre_e = teclado.nextLine();
                    System.out.println("Introduce los apellidos del nuevo empleado:");
                    apellidos = teclado.nextLine();
                    if (empresa.añadirEmpleado(nombre_e, apellidos)) {
                        System.out.println("¡Empleado añadido satisfactoriamente!");
                    }
                    else {
                        System.out.println("¡Error! El empleado no ha sido añadido.");
                    }
                    System.out.println("\nRedirigiendo al menú...");
                    break;
                case "13":
                    System.out.println("\n-Empleados de la franquicia-");
                    List<Empleados> empleados = empresa.getEmpleados();
                    if (!empleados.isEmpty()) {
                        mostrarLista(empleados);
                    }
                    else {
                        System.out.println("No hay empleados para mostrar.");
                    }
                    System.out.println("\nRedirigiendo al menú...");
                    break;
                case "14":
                    System.out.println("Introduce el ID de la tienda a la que añadir el empleado"
                                        + "(si quieres ver las tiendas existentes introduce -V-):");
                    tienda = selectTienda();
                    if (tienda != null) {
                        System.out.println("Introduce el ID del empleado a añadir"
                                        + "(si quieres ver las tiendas existentes introduce -V-):");
                        empleado = selectEmpleado();
                        if (empleado != null) {
                            System.out.println("Introduce las horas de trabajo del empleado:");
                            horas_trabajadas = teclado.nextFloat();
                            if (empresa.añadirEmpleadoATienda(empleado, tienda, horas_trabajadas)) {
                                System.out.println("¡Empleado añadido a la tienda satisfactoriamente!");
                            }
                            else {
                                System.out.println("¡Error! El empleado no ha sido añadido.");
                            }
                        }
                    }
                    System.out.println("Redirigiendo al menú...");
                    break;
                case "15":
                    System.out.println("Introduce el ID de la tienda de la que eliminar el empleado"
                                        + "(si quieres ver las tiendas existentes introduce -V-):");
                    tienda = selectTienda();
                    if (tienda != null) {
                        System.out.println("Introduce el ID del empleado a eliminar"
                                        + "(si quieres ver las tiendas existentes introduce -V-):");
                        EmpleadosTiendas relEmpleadoTienda = selectEmpleadoDeTienda(tienda, true);
                        if (relEmpleadoTienda != null) {
                            if (empresa.eliminarEmpleadoDeTienda(relEmpleadoTienda)) {
                                tienda.delEmpleado(relEmpleadoTienda.getEmpleado());
                                System.out.println("¡Empleado eliminado de la tienda satisfactoriamente!");
                            }
                            else {
                                System.out.println("¡Error! El empleado no ha sido eliminado de la tienda.");
                            }
                        }
                    }
                    System.out.println("Redirigiendo al menú...");
                    break;
                case "16":
                    System.out.println("Introduce el nombre del nuevo cliente:");
                    nombre_c = teclado.nextLine();
                    System.out.println("Ahora introduce sus apellidos:");
                    apellidos_c = teclado.nextLine(); 
                    System.out.println("Introduce el email:");
                    email = teclado.nextLine();
                    if (empresa.añadirCliente(nombre_c, apellidos_c, email)) {
                        System.out.println("¡Cliente añadido satisfactoriamente!");
                    } 
                    else {
                        System.out.println("¡Error! El empleado no ha sido añadido.");
                    }
                    System.out.println("\nRedirigiendo al menú...");
                    break;
                case "17":
                    System.out.println("\n-Clientes-");
                    List<Clientes> clientes = empresa.getClientes();
                        if (!clientes.isEmpty()) {
                            mostrarLista(clientes);
                        } 
                        else {
                            System.out.println("No hay clientes en la base de datos");
                        }
                    System.out.println("\n Redirigiendo al menú...");                    
                    break;

                case "18":
                    System.out.println("Introduce el ID del cliente a eliminar."
                                        + "(si quieres ver los clientes existentes introduce -V-):");
                    cliente = selectCliente();
                    if (cliente != null) {
                            System.out.println();
                            if (empresa.eliminarCliente(cliente)) {
                            System.out.println("¡Cliente eliminado satisfactoriamente!");
                        }
                        else {
                            System.out.println("¡Error! El cliente no ha sido eliminado.");
                        }
                    }
                    System.out.println("Redirigiendo al menú...");
                    break;
                case "19":
                    System.out.println("Generando informe de stock...\n");
                    informeStock();
                    System.out.println("\n Redirigiendo al menú...");                    
                    break;    
                case "20":
                    XMLReader procesadorXML = null;
                    try {
                        System.out.println();
                        /*Creamos un parseador de texto nuestra clase que se va a encargar
                        de parsear el texto.*/
                        procesadorXML = XMLReaderFactory.createXMLReader();
                        TitularesXML titularesXML = new TitularesXML();
                        procesadorXML.setContentHandler(titularesXML);

                        //Indicamos donde están guardados los titulares.
                        InputSource archivo = new InputSource("http://ep00.epimg.net/rss/elpais/portada.xml");
                        procesadorXML.parse(archivo);

                        //Imprimimos los datos que se han leido del XML.
                        ArrayList<Titular> titulares = titularesXML.getTitulares();
                        for(i=0;i<titulares.size();i++){
                            Titular tituloAux = titulares.get(i);
                            int n=i+1;
                            System.out.println("Titular "+n+": "+ tituloAux.getTitular());
                        }

                    } 
                    catch (SAXException e) {
                        System.out.println("No existe el fichero.");
                    } 
                    catch (IOException e) {
                        System.out.println("Ha ocurrido un error al leer el archivo XML.");
                    }
                    System.out.println("\nPulsa intro para salir de esta sección.");
                    teclado.nextLine();
                    break;
                    
                case "0":
                    System.out.println("El programa será cerrado... ¡Vuelve pronto!");
                    cierre=true;
                    break;
                default:
                    System.out.println("\n¡Error! El valor introducido debe estar entre el 0 y el 10.\n");
                                        
            }   
            
        }    
    }
    //Imprime en pantalla una lista de elementos.
    private static <T> void mostrarLista(List<T> l) {
        for (T t : l) {
            System.out.println(t.toString());
        }
    }
    private static void mostrarStock() {
        System.out.println("Introduce el ID de la tienda de la que mostrar el stock de un producto:");
        Tiendas tienda = selectTienda();

        if (tienda != null) {
            ProductosTiendas relProductoTienda = selectProductoDeTienda(tienda, false);

            if (relProductoTienda != null) {
                Productos producto = relProductoTienda.getProducto();
                System.out.println("   Producto "+ producto.getId()
                        + ":\n\tNombre: " + producto.getNombre());
                System.out.println("\n\tStock: "
                        + relProductoTienda.getStock());
            }
        }
    }
    private static void mostrarJornada() {
        System.out.println();

        Tiendas tienda = selectTienda();

        if (tienda != null) {
            EmpleadosTiendas relEmpleadoTienda = selectEmpleadoDeTienda(tienda, false);

            if (relEmpleadoTienda != null) {
                Empleados empleado = relEmpleadoTienda.getEmpleado();

                System.out.print("-Empleado "+empleado.getId() + "-"
                                +"\n\t"+empleado.getNombreCompleto());
                System.out.println("\t"
                        + relEmpleadoTienda.getJornada()+("Jornada laboral"));
            }
        }
    }
    private static Provincia selectProvincias() {
        if (!empresa.getProvincias().isEmpty()) {
            while (true) {
                Scanner teclado = new Scanner(System.in);
                String idProvincia = teclado.nextLine();
                if (idProvincia.equalsIgnoreCase("V")) {
                    System.out.println();
                    mostrarLista(empresa.getProvincias());
                    System.out.println();
                } 
                else {
                    try {
                        Provincia provincia = empresa.getProvincia(
                                Long.parseLong(idProvincia));

                        if (provincia != null) {
                            return provincia;
                        } 
                        else {
                            System.out.println("No existe ninguna provincia con el código introducido.");
                            System.out.println("Vuelva a intentarlo:");
                        }
                    } 
                    catch (NumberFormatException e) {
                        System.out.println("El código introducido no es válido.");
                        System.out.println("Vuelva a intentarlo:");
                    }
                }
            }
        } 
        else {
            System.out.println("No hay ninguna provincia guardada.");
            return null;
        }
    }
    private static Tiendas selectTienda() {
        if (!empresa.getTiendas().isEmpty()) {
            while (true) {
                Scanner teclado = new Scanner(System.in);
                String idTienda = teclado.nextLine(); 

                if (idTienda.equalsIgnoreCase("V")) {
                    mostrarLista(empresa.getTiendas());
                } 
                else {
                    try {
                        Tiendas tienda = empresa.getTienda(
                                Long.parseLong(idTienda));

                        if (tienda != null) {
                            return tienda;
                        } 
                        else {
                            System.out.println("No existe ninguna tienda con el código introducido.");
                            System.out.println("Vuelva a intentarlo:");
                        }
                    } 
                    catch (NumberFormatException e) {
                        System.out.println("El código introducido no es válido.");
                        System.out.println("Vuelva a intentarlo:");
                    }
                }
            }
        } 
        else {
            System.out.println("No hay ninguna tienda guardada.");
            return null;
        }
    }
    private static Productos selectProducto() {
        Scanner teclado = new Scanner(System.in);
        if (!empresa.getProductos().isEmpty()) {
            while (true) {
                String idProducto = teclado.nextLine();
                if (idProducto.equalsIgnoreCase("V")) {
                    System.out.println();
                    mostrarLista(empresa.getProductos());
                    System.out.println();
                } else {
                    try {
                        Productos producto = empresa.getProducto(
                                Long.parseLong(idProducto));

                        if (producto != null) {
                            return producto;
                        } else {
                            System.out.println("No existe ningún producto con el código introducido.");
                            System.out.println("Vuelva a intentarlo:");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("El código introducido no es válido.");
                        System.out.println("Vuelva a intentarlo:");
                    }
                }
            }
        } 
        else {
            System.out.println("No hay ningún producto en la franquicia.");
            return null;
        }
    }
    private static Clientes selectCliente() {
        Scanner teclado = new Scanner(System.in);
        if (!empresa.getClientes().isEmpty()) {
            while (true) {
                String idCliente = teclado.nextLine();

                if (idCliente.equalsIgnoreCase("V")) {
                    System.out.println();
                    mostrarLista(empresa.getClientes());
                    System.out.println();
                } else {
                    try {
                        Clientes cliente = empresa.getCliente(
                                Long.parseLong(idCliente));

                        if (cliente != null) {
                            return cliente;
                        } 
                        else {
                            System.out.println("No existe ningún cliente con el código introducido.");
                            System.out.println("Vuelva a intentarlo:");
                        }
                    } 
                    catch (NumberFormatException e) {
                        System.out.println("El código introducido no es válido.");
                        System.out.println("Vuelva a intentarlo:");
                    }
                }
            }
        } 
        else {
            System.out.println("No hay ningún cliente guardado.");
            return null;
        }
    }
    private static ProductosTiendas selectProductoDeTienda(Tiendas tienda, boolean mostrarStock) {
        Scanner teclado = new Scanner(System.in);
        if (!tienda.getProductos().isEmpty()) {
            while (true) {
                System.out.println("Introduce el ID del producto"
                                        + "(si quieres ver los productos existentes introduce -V-):");
                String idProducto = teclado.nextLine();
                if (idProducto.equalsIgnoreCase("V")) {
                    for (ProductosTiendas rel : tienda.getProductos()) {
                        Productos producto = rel.getProducto();

                        System.out.print("-Producto "+producto.getId() + "-"
                                + "\n\t"+ producto.getNombre()
                                + "\n\t" + producto.getDescripcion()
                                + "\n\t" + producto.getPrecio() + "€");

                        if (mostrarStock) {
                            System.out.println("\n\t" + rel.getStock() + "(stock)");
                        } 
                        else {
                            System.out.println();
                        }
                    }
                } 
                else {
                    try {
                        ProductosTiendas relProductoTienda =
                                empresa.getRelProductoTienda(
                                        new ProductosTiendasID(
                                                empresa.getProducto(
                                                        Long.parseLong(idProducto)),
                                                tienda));

                        if (relProductoTienda != null) {
                            return relProductoTienda;
                        } else {
                            System.out.println("No existe ningún producto con el código introducido.");
                            System.out.println("Vuelva a intentarlo:");
                        }
                    } 
                    catch (NumberFormatException e) {
                        System.out.println("El código introducido no es válido.");
                        System.out.println("Vuelva a intentarlo:");
                    }
                }
            }
        } 
        else {
            System.out.println("No hay productos en la tienda seleccionada");
            return null;
        }
    }
    private static Empleados selectEmpleado() {
        Scanner teclado = new Scanner(System.in);
        if (!empresa.getEmpleados().isEmpty()) {
            while (true) {
                String idEmpleado = teclado.nextLine();
                if (idEmpleado.equalsIgnoreCase("V")) {
                    System.out.println();
                    mostrarLista(empresa.getEmpleados());
                    System.out.println();
                }
                else {
                    try {
                        Empleados empleado = empresa.getEmpleado(
                                Long.parseLong(idEmpleado));

                        if (empleado != null) {
                            return empleado;
                        } else {
                            System.out.println("No existe ningún empleado con el código introducido.");
                            System.out.println("Vuelva a intentarlo:");
                        }
                    } 
                    catch (NumberFormatException e) {
                        System.out.println("El código introducido no es válido.");
                        System.out.println("Vuelva a intentarlo:");
                    }
                }
            }
        } 
        else {
            System.out.println("No hay ningún empleado guardado.");
            return null;
        }
    }
    private static EmpleadosTiendas selectEmpleadoDeTienda(Tiendas tienda, boolean mostrarJornada) {
        Scanner teclado = new Scanner(System.in);
        if (!tienda.getEmpleados().isEmpty()) {
            while (true) {
                String idEmpleado = teclado.nextLine();
                if (idEmpleado.equalsIgnoreCase("V")) {
                    System.out.println();

                    for (EmpleadosTiendas e : tienda.getEmpleados()) {
                        Empleados empleado = e.getEmpleado();

                        System.out.print("-Empleado "+empleado.getId() + "-"
                                +"\n\t"+empleado.getNombreCompleto());

                        if (mostrarJornada) {
                            System.out.println("\n\t" + e.getJornada() + "(jornada)");
                        } 
                        else {
                            System.out.println();
                        }
                    }
                } 
                else {
                    try {
                        EmpleadosTiendas relEmpleadoTienda =
                                empresa.getRelEmpleadoTienda(
                                        new EmpleadosTiendasID(
                                                empresa.getEmpleado(
                                                        Long.parseLong(idEmpleado)),
                                                tienda));

                        if (relEmpleadoTienda != null) {
                            return relEmpleadoTienda;
                        } 
                        else {
                            System.out.println("No existe ningún empleado con el código introducido en dicha tienda.");
                            System.out.println("Vuelva a intentarlo:");
                        }
                    } 
                    catch (NumberFormatException e) {
                        System.out.println("El código introducido no es válido.");
                        System.out.println("Vuelva a intentarlo:");
                    }
                }
            }
        } 
        else {
            System.out.println("No hay empleados en la tienda seleccionada.");
            return null;
        }
    }
    private static void informeStock() {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Introduce el nombre del archivo del informe:");
        String filename = teclado.nextLine();
        if (Files.exists(Paths.get(filename))) {
            System.out.println("El nombre del archivo ya existe. ¿Desea sobrescribir el archivo? S/N");
            String confirmacion = teclado.nextLine();
            if(confirmacion=="N"){
                System.out.println("Archivo no sobreescribido.");
                return;
            }
        }
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonArray stock = new JsonArray();

            for (Tiendas t : empresa.getTiendas()) {
                JsonObject tienda = new JsonObject();
                tienda.addProperty("id_tienda", t.getId());
                tienda.addProperty("nombre_tienda", t.getNombre());

                JsonArray productos = new JsonArray();

                for (ProductosTiendas p : t.getProductos()) {
                    JsonObject producto = new JsonObject();
                    producto.addProperty("id_producto", p.getId().getProducto_id());
                    producto.addProperty("nombre_producto", p.getProducto().getNombre());
                    producto.addProperty("stock", p.getStock());

                    productos.add(producto);
                }

                tienda.add("stock_productos", productos);
                stock.add(tienda);
            }

            FileWriter outputFile = new FileWriter(filename);
            gson.toJson(stock, outputFile);
            outputFile.close();
            System.out.println("\n¡El informe de stock se ha generado satisfactoriamente!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\n¡Error! No se ha podido generar el informe de stock");
        }
    }
    public static void insertarProvinciasBD() throws Exception{
        File file = new File("err.log");
        FileOutputStream fos = new FileOutputStream(file, true);
        PrintStream ps = new PrintStream(fos);
        System.setErr(ps);
        empresa = new Empresa();

            /* Se comprueba si en la base de datos tenemos alguna provincia, si no las tenemos
            las obtenemos desde el fichero provincias.json y las guardamos.*/
            if (empresa.getProvincias().isEmpty()) {
                Gson gson = new Gson();
                try {
                    JsonReader jr = new JsonReader(
                            new InputStreamReader(
                                    MenuGestionPrincipal.class.getResourceAsStream(
                                            "/provincias.json")));
                    Provincias provincias = gson.fromJson(jr, Provincias.class);

                    for (Provincia provincia : provincias.getProvincias()) {
                        empresa.añadirProvincia(provincia);
                    }
                    System.out.println("El número de provincias existentes es: "+empresa.getProvincias().size());
                    System.out.println("¡Provincias insertadas satisfactoriamente en la base de datos!");
                } 
                catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    empresa.close();
                    System.out.println("¡Error! Las provincias no han sido añadidas a la base de datos.");

                    ps.close();
                    fos.close();
                    System.exit(1);
                }
            }
    }
    public static void comprobarProvincias(){
        // Si no tenemos 52 provincias guardadas, que son las que hay en el JSON, la app se cierra.
        if (empresa.getProvincias().size() != 52) {
            empresa.close();
            System.out.println("¡Error! No hay el número de provincias que debería.");
            System.exit(1);
        } 
    }
            
}