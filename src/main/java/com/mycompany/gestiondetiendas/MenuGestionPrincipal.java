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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
    static String nombre_t, ciudad, provincia, nombre_pro;
    static int id_pro;
    static int c= 0;
    static Empresa empresa = new Empresa();
    static Tiendas tiendas = new Tiendas(nombre_t, ciudad, provincia);
    static Provincias provincias = new Provincias(id_pro, nombre_pro );
    /**
     * @param args
     * @throws java.io.IOException
     */    
    public static void main(String[] args) throws IOException {
        String db = "BaseDeDatosTiendas.db";
        crearBaseDatos(db);
        Connection con = conectarBaseDatos(db);
        crearTablaProvincias(con);
        crearTablaTiendas(con);
        crearTablaProductos(con);
        crearTablaEmpleados(con);
        crearTablaClientes(con);
        crearTablaProductosTiendas(con);
        crearTablaEmpleadosTiendas(con);    
        leerJson();        
        System.out.println("\n|---------------------------|");
        System.out.println("|App de gestión [Franquicia]|");
        System.out.println("|---------------------------|");
                 
        String nombre_t, nombre_pd, nombre_c, nombre_e,ciudad,provincia,codigoId,descripcion,apellidos, apellidos_c,email;
        Double precio;
        int cantidad,i,c;
        Double horas_trabajadas;
        boolean cierre = false;        
        while(cierre==false){
            seleccionarTiendas(con);
            seleccionarProductos(con);
            seleccionarProductosTiendas(con);
            seleccionarEmpleados(con);
            seleccionarEmpleadosTiendas(con);
            seleccionarClientes(con);
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
            System.out.println("[19] Leer los titulares del periódico El País.");
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
                        if(!empresa.getTiendas().isEmpty()){
                            for(i=0;i<empresa.getTiendas().size();i++){
                                if(empresa.getTiendas().get(i).getNombre().equalsIgnoreCase(nombre_t)){
                                    control = true;
                                    controlB = true;
                                }                            
                            }
                            if(control==false){
                                System.out.println("Introduce la ciudad en la que se encuentra la tienda:");
                                ciudad = teclado.nextLine();
                                System.out.println("Introduce la provincia:");
                                provincia = teclado.nextLine();
                                //empresa.getTiendas().add(new Tiendas(nombre,ciudad, provincia));
                                insertarTienda(con, nombre_t.toUpperCase(),
                                        ciudad.toUpperCase(), provincia.toUpperCase());
                                
                            }
                            else if (controlB==true){
                                System.out.println("¡Esa tienda ya existe!");
                            }
                        }                
                        else{
                            System.out.println("Introduce la ciudad en la que se encuentra la tienda:");
                            ciudad = teclado.nextLine();
                            System.out.println("Introduce la provincia:");
                            provincia = teclado.nextLine();
                            empresa.getTiendas().add(new Tiendas(nombre_t,ciudad,provincia));
                            insertarTienda(con, nombre_t.toUpperCase(),
                                    ciudad.toUpperCase(), provincia.toUpperCase());
                        }
                        System.out.println("Redirigiendo al menú...");
                    break;
                case "2":
                    System.out.println("\n-Tiendas existentes-");
                    imprimirTiendas(con);
                    System.out.println("\n Redirigiendo al menú...");                    
                    break;

                case "3":
                    if(empresa.getTiendas().isEmpty()){
                        System.out.println("No hay tiendas para eliminar. Serás redirigido al menú.\n");
                        break;
                    }
                    System.out.println("Introduce el nombre de la tienda a eliminar:");
                    nombre_t = teclado.nextLine();

                    for(i=0;i<empresa.getTiendas().size();i++){
                        if(empresa.getTiendas().get(i).getNombre().equalsIgnoreCase(nombre_t)){
                            empresa.getTiendas().clear();              
                            control = true;
                        }
                    } 
                    if(control==true){
                        eliminarTienda(con, nombre_t.toUpperCase());
                        break;
                    }
                    else{
                        System.out.println("La tienda introducida no existe.");
                    }
                    System.out.println("Redirigiendo al menú...");
                    break;
                case "4":
                    System.out.println("Introduce el nombre del nuevo producto:");

                    nombre_pd = teclado.nextLine();
                        if(!tiendas.getProductos().isEmpty()){
                            for(i=0;i<tiendas.getProductos().size();i++){
                                if(tiendas.getProductos().get(i).getNombre().equalsIgnoreCase(nombre_pd)){
                                    control = true;
                                    controlB = true;
                                }                            
                            }
                            if(control==false){
                                System.out.println("Introduce una descripción para el producto:");
                                descripcion = teclado.nextLine();
                                System.out.println("Introduce el precio del producto:");
                                precio = teclado.nextDouble();
                                //empresa.getTiendas().add(new Tiendas(nombre,ciudad, provincia));
                                insertarProducto(con, nombre_pd.toUpperCase(),
                                        descripcion.toUpperCase(), precio);
                            }
                            else if (controlB==true){
                                System.out.println("¡Ese producto ya existe!");
                            }
                        }                
                        else{
                            System.out.println("Introduce una descripción para el producto:");
                            descripcion = teclado.nextLine();
                            System.out.println("Introduce el precio del producto:");
                            precio = teclado.nextDouble();
                            //empresa.getTiendas().add(new Tiendas(nombre,ciudad, provincia));
                            insertarProducto(con, nombre_pd.toUpperCase(),
                                    descripcion.toUpperCase(), precio);
                        }
                        System.out.println("Redirigiendo al menú...");
                    break;
                case "5":
                    System.out.println("\n-Productos de la franquicia-");
                    imprimirProductos(con);
                    System.out.println("\nRedirigiendo al menú...");
                    break;
                case "6":
                    if(empresa.getTiendas().isEmpty()){
                        System.out.println("No existe ninguna tienda para añadir productos.");
                        System.out.println("Redirigiendo al menú...");
                        break;
                    }
                    System.out.println("\n-Tiendas-");
                    imprimirTiendas2(con);
                    System.out.println("Introduce, de entre las existentes, el nombre"
                            + " de la tienda a la que añadir el producto:");
                    nombre_t = teclado.nextLine();
                    for(i=0;i<empresa.getTiendas().size();i++){
                        if(empresa.getTiendas().get(i).getNombre().equalsIgnoreCase(nombre_t)){
                            System.out.println("\n-Productos-");
                            imprimirProductos2(con);
                            control2 = true;
                            controlB = true;
                            System.out.println("Introduce, de entre los existentes, el nombre del producto:");
                            nombre_pd = teclado.nextLine();
                            if(!tiendas.getProductos().isEmpty()){
                                for(c=0;c<tiendas.getProductos().size();c++){
                                    if(tiendas.getProductos().get(c).getNombre().equalsIgnoreCase(nombre_pd)){
                                        control = true;
                                        controlB = false;
                                    }
                                }                                   
                            }
                            if (control==true){
                                for(c=0;c<tiendas.getProductosTiendas().size();c++){
                                    if(tiendas.getProductosTiendas().get(c).getNombrePd().equalsIgnoreCase(nombre_pd)
                                            && tiendas.getProductosTiendas().get(c).getNombreT().equalsIgnoreCase(nombre_t)){
                                        controlB = true;
                                        
                                    }
                                }
                                
                            }
                            
                            if (controlB==false){
                                System.out.println("Introduce el stock del producto en la tienda:");
                                cantidad = teclado.nextInt();
                                insertarProductosTiendas(con, nombre_t.toUpperCase(),
                                        nombre_pd.toUpperCase(), cantidad);
                                control3=false;
                                break;
                            }
                        }                        
                    }
                    if (control2==false){
                        System.out.println("La tienda introducida no existe.");
                        System.out.println("Redirigiendo al menú...");
                        break;
                        } 
                    if(control==false){
                        System.out.println("El producto introducido no se encuentra dentro de los disponibles en la franquicia.");
                        System.out.println("Redirigiendo al menú...");
                        break;
                    }
                    if(controlB==true){
                        System.out.println("El producto que se quiere añadir ya existe en dicha tienda.");
                        System.out.println("Redirigiendo al menú...");
                        break;
                    }
                    if (control3==false){
                        System.out.println("Redirigiendo al menú...");
                        break;
                    }
                case "7":
                    System.out.println("\n-Tiendas-");
                    imprimirTiendas2(con);
                    System.out.println("Introduce, de entre las existentes, el nombre"
                            + " de la tienda de la que se mostrarán los productos:");
                    nombre_t = teclado.nextLine();
                    if(empresa.getTiendas().isEmpty()){
                        System.out.println("No existe ninguna tienda para añadir productos.");
                        System.out.println("Redirigiendo al menú...");
                        break;
                    }
                    for(i=0;i<empresa.getTiendas().size();i++){
                        if(empresa.getTiendas().get(i).getNombre().equalsIgnoreCase(nombre_t)){
                            System.out.println("\n-Productos-");
                            imprimirProductosTiendas(con, nombre_t.toUpperCase());
                            control=true;
                            break;
                        }
                        
                    }
                    if (control==true){
                            System.out.println("Redirigiendo al menú...");
                            break;
                    }
                    else if (control==false){
                            System.out.println("La tienda introducida no se corresponde con ninguna de la lista.");
                            System.out.println("Redirigiendo al menú...");
                            break;
                    }
                case "8":
                    if(tiendas.getProductosTiendas().isEmpty()){
                        System.out.println("No existen tiendas con productos para actualizar su stock.");
                        System.out.println("Redirigiendo al menú...");
                        break;
                    }
                    System.out.println("\n-Tiendas-");
                    imprimirTiendas2(con);
                    System.out.println("\nIntroduce, de entre las existentes, el nombre"
                            + " de la tienda de la que se quiera modificar el stock de un producto:");
                    nombre_t = teclado.nextLine();
                    for(i=0;i<tiendas.getProductosTiendas().size();i++){
                        if(tiendas.getProductosTiendas().get(i).getNombreT().equalsIgnoreCase(nombre_t)){
                            System.out.println("\n-Productos-");
                            tiendas.getProductosTiendas().clear();
                            imprimirProductosTiendas(con, nombre_t.toUpperCase());
                            control2 = true;
                            System.out.println("Introduce, de entre los disponibles en esta tienda,"
                                    + " el nombre del producto cuyo stock se va a actualizar:");
                            nombre_pd = teclado.nextLine();
                            if(!tiendas.getProductosTiendas().isEmpty()){
                                for(c=0;c<tiendas.getProductosTiendas().size();c++){
                                    if(tiendas.getProductosTiendas().get(c).getNombrePd().equalsIgnoreCase(nombre_pd)){
                                        control = true;
                                        controlB = true;
                                    }
                                }                                   
                            }
                            
                            if (controlB==true){
                                System.out.println("Introduce el nuevo stock del producto en la tienda:");
                                cantidad = teclado.nextInt();
                                actualizarStockTiendasProductos(con, cantidad, nombre_t.toUpperCase(),
                                        nombre_pd.toUpperCase());
                                control3=false;
                                
                                break;
                            }
                        }                        
                    }
                    if (control2==false){
                        System.out.println("La tienda introducida no existe o no tiene productos.");
                        System.out.println("Redirigiendo al menú...");
                        break;
                        } 
                    if(control==false){
                        System.out.println("El producto introducido no se encuentra dentro del inventario de la tienda.");
                        System.out.println("Redirigiendo al menú...");
                        break;
                    }
                    if (control3==false){
                        System.out.println("Redirigiendo al menú...");
                        break;
                    }
                case "9":
                    if(tiendas.getProductosTiendas().isEmpty()){
                        System.out.println("No existen tiendas con productos para actualizar su stock.");
                        System.out.println("Redirigiendo al menú...");
                        break;
                    }
                    System.out.println("\n-Tiendas-");
                    imprimirTiendas2(con);
                    System.out.println("\nIntroduce, de entre las existentes, el nombre"
                            + " de la tienda de la que se quiera mostrar el stock de un producto:");
                    nombre_t = teclado.nextLine();
                    for(i=0;i<tiendas.getProductosTiendas().size();i++){
                        if(tiendas.getProductosTiendas().get(i).getNombreT().equalsIgnoreCase(nombre_t)){
                            System.out.println("\n-Productos-");
                            tiendas.getProductosTiendas().clear();
                            imprimirProductosTiendas2(con, nombre_t.toUpperCase());
                            control2 = true;
                            System.out.println("Introduce, de entre los disponibles en esta tienda,"
                                    + " el nombre del producto cuyo stock se va a mostrar:");
                            nombre_pd = teclado.nextLine();
                            if(!tiendas.getProductosTiendas().isEmpty()){
                                for(c=0;c<tiendas.getProductosTiendas().size();c++){
                                    if(tiendas.getProductosTiendas().get(c).getNombrePd().equalsIgnoreCase(nombre_pd)){
                                        control = true;
                                        controlB = true;
                                    }
                                }                                   
                            }
                            
                            if (controlB==true){
                                imprimirProductosDeterminadoTiendas(con, nombre_t.toUpperCase(),
                                        nombre_pd.toUpperCase());
                                control3=false;
                                
                                break;
                            }
                        }                        
                    }
                    if (control2==false){
                        System.out.println("La tienda introducida no existe o no tiene productos.");
                        System.out.println("Redirigiendo al menú...");
                        break;
                        } 
                    if(control==false){
                        System.out.println("El producto introducido no se encuentra dentro del inventario de la tienda.");
                        System.out.println("Redirigiendo al menú...");
                        break;
                    }
                    if (control3==false){
                        System.out.println("Redirigiendo al menú...");
                        break;
                    }
                case "10":
                    if(tiendas.getProductos().isEmpty()){
                        System.out.println("No hay productos para eliminar.\n");
                        System.out.println("Redirigiendo al menú...");
                        break;
                    }
                    else{
                        System.out.println("Introduce el nombre del producto a eliminar de la franquicia:");
                        nombre_pd = teclado.nextLine();

                        for(i=0;i<tiendas.getProductos().size();i++){
                            if(tiendas.getProductos().get(i).getNombre().equalsIgnoreCase(nombre_pd)){
                                tiendas.getProductos().clear();              
                                control = true;
                            }
                        } 
                        if(control==true){
                            eliminarProducto(con, nombre_pd.toUpperCase());
                            break;
                        }
                        else{
                            System.out.println("El producto introducido no existe.");
                        }
                        System.out.println("Redirigiendo al menú...");
                            break;
                    }
                case "11":
                    if(tiendas.getProductosTiendas().isEmpty()){
                        System.out.println("No existen tiendas con productos para eliminar un producto.");
                        System.out.println("Redirigiendo al menú...");
                        break;
                    }
                    System.out.println("\n-Tiendas-");
                    imprimirTiendas2(con);
                    System.out.println("\nIntroduce, de entre las existentes, el nombre"
                            + " de la tienda de la que se quiera eliminar un producto:");
                    nombre_t = teclado.nextLine();
                    for(i=0;i<tiendas.getProductosTiendas().size();i++){
                        if(tiendas.getProductosTiendas().get(i).getNombreT().equalsIgnoreCase(nombre_t)){
                            System.out.println("\n-Productos-");
                            tiendas.getProductosTiendas().clear();
                            imprimirProductosTiendas2(con, nombre_t.toUpperCase());
                            control2 = true;
                            System.out.println("Introduce, de entre los disponibles en esta tienda,"
                                    + " el nombre del producto a eliminar:");
                            nombre_pd = teclado.nextLine();
                            if(!tiendas.getProductosTiendas().isEmpty()){
                                for(c=0;c<tiendas.getProductosTiendas().size();c++){
                                    if(tiendas.getProductosTiendas().get(c).getNombrePd().equalsIgnoreCase(nombre_pd)){
                                        control = true;
                                        controlB = true;
                                    }
                                }                                   
                            }
                            
                            if (controlB==true){
                                eliminarProductosTiendas(con, nombre_t.toUpperCase(),
                                        nombre_pd.toUpperCase());
                                control3=false;
                                
                                break;
                            }
                        }                        
                    }
                    if (control2==false){
                        System.out.println("La tienda introducida no existe o no tiene productos.");
                        System.out.println("Redirigiendo al menú...");
                        break;
                        } 
                    if(control==false){
                        System.out.println("El producto introducido no se encuentra dentro del inventario de la tienda.");
                        System.out.println("Redirigiendo al menú...");
                        break;
                    }
                    if (control3==false){
                        System.out.println("Redirigiendo al menú...");
                        break;
                    }                     
                case "12":
                    System.out.println("Introduce el nombre del nuevo empleado:");
                    nombre_e = teclado.nextLine();
                    System.out.println("Introduce los apellidos del nuevo empleado:");
                    apellidos = teclado.nextLine();
                        if(!tiendas.getEmpleados().isEmpty()){
                            for(i=0;i<tiendas.getEmpleados().size();i++){
                                if(tiendas.getEmpleados().get(i).getNombre().equalsIgnoreCase(nombre_e) 
                                        && tiendas.getEmpleados().get(i).getApellidos().equalsIgnoreCase(apellidos)){
                                    control = true;
                                    controlB = true;
                                }                            
                            }
                            if(control==false){
                                insertarEmpleado(con, nombre_e.toUpperCase(),
                                        apellidos.toUpperCase());
                            }
                            else if (controlB==true){
                                System.out.println("¡Ese empleado ya existe!");
                            }
                        }                
                        else{
                            insertarEmpleado(con, nombre_e.toUpperCase(),
                                        apellidos.toUpperCase());
                        }
                        System.out.println("Redirigiendo al menú...");
                    break;
                case "13":
                    System.out.println("\n-Empleados de la franquicia-");
                    imprimirEmpleados(con);
                    System.out.println("\nRedirigiendo al menú...");
                    break;
                case "14":
                    if(empresa.getTiendas().isEmpty()){
                        System.out.println("No existe ninguna tienda para añadir empleados.");
                        System.out.println("Redirigiendo al menú...");
                        break;
                    }
                    System.out.println("\n-Tiendas-");
                    imprimirTiendas2(con);
                    System.out.println("Introduce, de entre las existentes, el nombre"
                            + " de la tienda a la que añadir el empleado:");
                    nombre_t = teclado.nextLine();
                    for(i=0;i<empresa.getTiendas().size();i++){
                        if(empresa.getTiendas().get(i).getNombre().equalsIgnoreCase(nombre_t)){
                            System.out.println("\n-Empleados-");
                            imprimirEmpleados(con);
                            control2 = true;
                            controlB = true;
                            System.out.println("Introduce, de entre los existentes, el nombre del empleado:");
                            nombre_e = teclado.nextLine();
                            System.out.println("Ahora, introduce sus apellidos:");
                            apellidos = teclado.nextLine();
                            if(!tiendas.getEmpleados().isEmpty()){
                                for(c=0;c<tiendas.getEmpleados().size();c++){
                                    if(tiendas.getEmpleados().get(c).getNombre().equalsIgnoreCase(nombre_e)
                                            && tiendas.getEmpleados().get(c).getApellidos().equalsIgnoreCase(apellidos)){
                                        control = true;
                                        controlB = false;
                                    }
                                }                                   
                            }
                            if (control==true){
                                for(c=0;c<tiendas.getEmpleadosTiendas().size();c++){
                                    if(tiendas.getEmpleadosTiendas().get(c).getNombreE().equalsIgnoreCase(nombre_e+" "+apellidos)
                                            && tiendas.getEmpleadosTiendas().get(c).getNombreT().equalsIgnoreCase(nombre_t)){
                                        controlB = true;
                                        
                                    }
                                }
                                
                            }
                            
                            if (controlB==false){
                                System.out.println("Introduce las horas trabajadas del empleado en la tienda:");
                                horas_trabajadas = teclado.nextDouble();
                                insertarEmpleadosTiendas(con, nombre_t.toUpperCase(),
                                        nombre_e.toUpperCase()+ " " +apellidos.toUpperCase(),
                                        horas_trabajadas);
                                control3=false;
                                break;
                            }
                        }                        
                    }
                    if (control2==false){
                        System.out.println("La tienda introducida no existe.");
                        System.out.println("Redirigiendo al menú...");
                        break;
                        } 
                    if(control==false){
                        System.out.println("El empleado introducido no está dentro de la franquicia.");
                        System.out.println("Redirigiendo al menú...");
                        break;
                    }
                    if(controlB==true){
                        System.out.println("El empleado que se quiere añadir ya existe en dicha tienda.");
                        System.out.println("Redirigiendo al menú...");
                        break;
                    }
                    if (control3==false){
                        System.out.println("Redirigiendo al menú...");
                        break;
                    }

                case "15":
                    
                    if(tiendas.getEmpleadosTiendas().isEmpty()){
                        System.out.println("No existen tiendas con empleados para eliminar.");
                        System.out.println("Redirigiendo al menú...");
                        break;
                    }
                    System.out.println("\n-Tiendas-");
                    imprimirTiendas2(con);
                    System.out.println("\nIntroduce, de entre las existentes, el nombre"
                            + " de la tienda de la que se quiera eliminar un empleado:");
                    nombre_t = teclado.nextLine();
                    for(i=0;i<tiendas.getEmpleadosTiendas().size();i++){
                        if(tiendas.getEmpleadosTiendas().get(i).getNombreT().equalsIgnoreCase(nombre_t)){
                            System.out.println("\n-Empleados-");
                            tiendas.getEmpleadosTiendas().clear();
                            imprimirEmpleadosTiendas2(con, nombre_t.toUpperCase());
                            control2 = true;
                            System.out.println("Introduce, de entre los disponibles en esta tienda,"
                                    + " el nombre del empleado a eliminar:");
                            nombre_e = teclado.nextLine();
                            System.out.println("Ahora, introduce sus apellidos:");
                            apellidos = teclado.nextLine();
                            if(!tiendas.getEmpleadosTiendas().isEmpty()){
                                for(c=0;c<tiendas.getEmpleadosTiendas().size();c++){
                                    if(tiendas.getEmpleadosTiendas().get(c).getNombreE().equalsIgnoreCase(nombre_e+" "+ apellidos)){
                                        control = true;
                                        controlB = true;
                                    }
                                }                                   
                            }
                            
                            if (controlB==true){
                                eliminarEmpleadosTiendas(con, nombre_t.toUpperCase(),
                                        nombre_e.toUpperCase()+" "+ apellidos.toUpperCase());
                                control3=false;
                                
                                break;
                            }
                        }                        
                    }
                    if (control2==false){
                        System.out.println("La tienda introducida no existe o no tiene empleados.");
                        System.out.println("Redirigiendo al menú...");
                        break;
                        } 
                    if(control==false){
                        System.out.println("El empleado no forma parte de la plantilla de la tienda.");
                        System.out.println("Redirigiendo al menú...");
                        break;
                    }
                    if (control3==false){
                        System.out.println("Redirigiendo al menú...");
                        break;
                    }  

                case "16":
                    System.out.println("Introduce el nombre del nuevo cliente:");
                    nombre_c = teclado.nextLine();
                    System.out.println("Ahora introduce sus apellidos:");
                    apellidos_c = teclado.nextLine();
                        if(!empresa.getClientes().isEmpty()){
                            for(i=0;i<empresa.getClientes().size();i++){
                                if(empresa.getClientes().get(i).getNombreC().equalsIgnoreCase(nombre_c)
                                        &&empresa.getClientes().get(i).getApellidosC().equalsIgnoreCase(apellidos_c)){
                                    control = true;
                                    controlB = true;
                                }                            
                            }
                            if(control==false){
                                System.out.println("Introduce el correo eletrónico del cliente:");
                                email = teclado.nextLine();
                                insertarClientes(con, nombre_c.toUpperCase(),
                                        apellidos_c.toUpperCase(), email.toUpperCase());
                            }
                            else if (controlB==true){
                                System.out.println("¡Ese cliente ya existe!");
                            }
                        }                
                        else{
                            System.out.println("Introduce el email:");
                                email = teclado.nextLine();
                            empresa.getClientes().add(new Clientes(nombre_c, apellidos_c, email));
                            insertarClientes(con, nombre_c.toUpperCase(),
                                        apellidos_c.toUpperCase(), email.toUpperCase());
                        }
                        System.out.println("Redirigiendo al menú...");
                    break;
                case "17":
                    System.out.println("\n-Clientes existentes-");
                    imprimirClientes(con);
                    System.out.println("\n Redirigiendo al menú...");                    
                    break;

                case "18":
                    if(empresa.getClientes().isEmpty()){
                        System.out.println("No hay clientes para eliminar.");
                        System.out.println("Redirigiendo al menú...");
                        break;
                    }
                    imprimirClientes2(con);
                    System.out.println("Introduce, de entre los existentes, el nombre del cliente a eliminar:");
                    nombre_c = teclado.nextLine();
                    System.out.println("Ahora sus apellidos:");
                    apellidos_c = teclado.nextLine();
                    for(i=0;i<empresa.getClientes().size();i++){
                        if(empresa.getClientes().get(i).getNombreC().equalsIgnoreCase(nombre_c)
                                && empresa.getClientes().get(i).getApellidosC().equalsIgnoreCase(apellidos_c)){
                            empresa.getClientes().clear();              
                            control = true;
                        }
                    } 
                    if(control==true){
                        eliminarCliente(con, nombre_c.toUpperCase(), apellidos_c.toUpperCase());
                        break;
                    }
                    else{
                        System.out.println("El cliente introducido no existe.");
                    }
                    System.out.println("Redirigiendo al menú...");
                    break;
                    
                case "19":
                    XMLReader procesadorXML = null;
                    try {
                        System.out.println("\n");
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
                    desconectarBaseDatos(con);
                    cierre=true;
                    break;
                default:
                    System.out.println("\n¡Error! El valor introducido debe estar entre el 0 y el 10.\n");
                                        
            }   
            
        }    
    }
    
    //Método para crear la base de datos Sqlite.
    private static void crearBaseDatos(String filename){
        String databaseFile = "jdbc:sqlite:" + filename;
        
        try{
            Connection connection = DriverManager.getConnection(databaseFile);
            if(connection != null){
                DatabaseMetaData meta = connection.getMetaData();
                System.out.println("¡La base de datos ha sido creada!");
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }
    
    //Este método se conecta a la base de datos SQLLite, a la cual se le pasa el nombe de la base de datos.    
    private static Connection conectarBaseDatos(String filename){
        Connection connection = null;
        try
        {
            //Creamos a conexión a base de datos
            connection = DriverManager.getConnection("jdbc:sqlite:" + filename);
            System.out.println("Conexión con la base de datos realizada con éxito.");
            return connection;
             
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
            return null;
        }
    }
    //Este método se desconecta de una base de datos en SQLLite a la que se le pasa la conexión.
    private static void desconectarBaseDatos(Connection connection){
        try{
            if(connection != null){
                connection.close();
                System.out.println("Desconexión de la base de datos realizada con éxito.");
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    //Método que crea la tabla Provincias en la base de datos. 
    private static void crearTablaProvincias(Connection con){
        try{
            String sql = "CREATE TABLE IF NOT EXISTS provincias (\n" +
                    "id integer PRIMARY KEY AUTOINCREMENT,\n"+
                    "nombre_p text NOT NULL\n"+
                    ");";

            Statement stmt = con.createStatement();
            stmt.execute(sql);
            System.out.println("Tabla -Provincias- creada con éxito.");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }

    
    //Método que crea la tabla Tiendas en la base de datos. 
    private static void crearTablaTiendas(Connection con){
        try{
            String sql = "CREATE TABLE IF NOT EXISTS Tiendas (\n" +
                    "nombre_t text PRIMARY KEY NOT NULL,\n"+
                    "ciudad text NOT NULL,\n"+
                    "provincia text NOT NULL\n"+
                    ");";

            Statement stmt = con.createStatement();
            stmt.execute(sql);
            System.out.println("Tabla -Tiendas- creada con éxito.");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }
    //Método que crea la tabla Empleados en la base de datos. 
    private static void crearTablaEmpleados(Connection con){
        try{
            String sql = "CREATE TABLE IF NOT EXISTS Empleados (\n" +
                    "id_e INTEGER PRIMARY KEY AUTOINCREMENT,\n"+
                    "nombre_e text NOT NULL,\n"+
                    "apellidos text NOT NULL\n"+
                    ");";

            Statement stmt = con.createStatement();
            stmt.execute(sql);
            System.out.println("Tabla -Empleados- creada con éxito.");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }
    //Método que crea la tabla Productos en la base de datos. 
    private static void crearTablaProductos(Connection con){
        try{
            String sql = "CREATE TABLE IF NOT EXISTS Productos (\n" +
                    "nombre_pd text PRIMARY KEY,\n"+
                    "descripcion text NOT NULL,\n"+
                    "precio DECIMAL(4,2) NOT NULL\n"+
                    ");";

            Statement stmt = con.createStatement();
            stmt.execute(sql);
            System.out.println("Tabla -Productos- creada con éxito.");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }
    //Método que crea la tabla Clientes en la base de datos. 
    private static void crearTablaClientes(Connection con){
        try{
            String sql = "CREATE TABLE IF NOT EXISTS Clientes (\n" +
                    "id_c INTEGER PRIMARY KEY AUTOINCREMENT,\n"+
                    "nombre_c text NOT NULL,\n"+
                    "apellidos text NOT NULL,\n"+
                    "email text NOT NULL\n"+
                    ");";

            Statement stmt = con.createStatement();
            stmt.execute(sql);
            System.out.println("Tabla -Clientes- creada con éxito.");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }
    //Método que crea la tabla Productostiendas en la base de datos. 
    private static void crearTablaProductosTiendas(Connection con){
        try{
            String sql = "CREATE TABLE IF NOT EXISTS Productos_Tiendas (\n" +
                    "nombre_pd text NOT NULL,\n"+
                    "nombre_t text NOT NULL,\n"+
                    "stock INTEGER NOT NULL,\n"+
                    "FOREIGN KEY(nombre_pd) REFERENCES Productos (nombre_pd),\n"+
                    "FOREIGN KEY(nombre_t) REFERENCES Tiendas (nombre_t)\n"+
                    ");";

            Statement stmt = con.createStatement();
            stmt.execute(sql);
            System.out.println("Tabla -Productos_Tienda- creada con éxito.");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }
    //Método que crea la tabla Empleadostiendas en la base de datos. 
    private static void crearTablaEmpleadosTiendas(Connection con){
        try{
            String sql = "CREATE TABLE IF NOT EXISTS Empleados_Tiendas (\n" +
                    "nombre_e text NOT NULL,\n"+
                    "nombre_t text NOT NULL,\n"+
                    "horas_trabajadas DECIMAL(4,2) NOT NULL,\n"+
                    "FOREIGN KEY(nombre_e) REFERENCES Empleados (nombre_e),\n"+
                    "FOREIGN KEY(nombre_t) REFERENCES Tiendas (nombre_t)\n"+
                    ");";

            Statement stmt = con.createStatement();
            stmt.execute(sql);
            System.out.println("Tabla -Empleados_Tienda- creada con éxito.");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
    }
     
    //Este método inserta una nueva tienda en la tabla Tiendas.
    private static void insertarTienda(Connection con, String nombre_t, String ciudad, String provincia){
        try{
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO tiendas(nombre_t, ciudad, provincia) VALUES(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setString(1, nombre_t);
            pstmt.setString(2, ciudad);
            pstmt.setString(3, provincia);
            pstmt.executeUpdate();
            System.out.println("¡Tienda añadida satisfactoriamente!");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    //Método que selecciona y guarda todas las tiendas.    
    private static void seleccionarTiendas(Connection con){
        try
        {

            Statement statement = con.createStatement();
            
            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select * from Tiendas");
            while(rs.next()){
                empresa.getTiendas().add(new Tiendas(rs.getString("nombre_t"),rs.getString("ciudad")
                        , rs.getString("provincia")));
            }
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    //Método que imprime todos los datos de todas las tiendas.    
    private static void imprimirTiendas(Connection con){
        try
        {

            Statement statement = con.createStatement();

            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select * from Tiendas");
            int i=0;
            while(rs.next()){
                i++;
                //imprimimos los datos de cada tienda.
                System.out.println(" -Tienda "+i+"-");
                System.out.println("\tNombre: " + rs.getString("nombre_t"));
                System.out.println("\tCiudad: " + rs.getString("ciudad"));
                System.out.println("\tProvincia " + rs.getString("provincia"));
            }
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    //Método que imprime los nombres de todas las tiendas.  
    private static void imprimirTiendas2(Connection con){
        try
        {

            Statement statement = con.createStatement();

            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select * from Tiendas");
            int i=0;
            while(rs.next()){
                i++;
                //imprimimos los datos de cada tienda.
                System.out.println("\n-Tienda "+i+"-");
                System.out.println("\tNombre: " + rs.getString("nombre_t"));
            }
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    
    //Este método borra una tienda de la base de datos según su nombre.
    private static void eliminarTienda(Connection con, String nombre){
        try{
            String sql = "DELETE FROM Tiendas WHERE nombre_t = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.executeUpdate();
            System.out.println("¡Tienda eliminada satisfactoriamente!");
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    //Este método inserta una nueva tienda en la tabla Tiendas.
    private static void insertarProducto(Connection con, String nombre_pd, String descripcion,
            double precio){
        try{
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO Productos(nombre_pd, descripcion, precio) VALUES(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setString(1, nombre_pd);
            pstmt.setString(2, descripcion);
            pstmt.setDouble(3, precio);
            pstmt.executeUpdate();
            System.out.println("¡Producto añadido satisfactoriamente!");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    //Método que selecciona y guarda todas las tiendas.    
    private static void seleccionarProductos(Connection con){
        try
        {

            Statement statement = con.createStatement();
            
            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select * from Productos");
            while(rs.next()){
                tiendas.getProductos().add(new Productos(rs.getString("nombre_pd"),rs.getString("descripcion")
                        , rs.getDouble("precio")));
            }
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    //Método que imprime todos los productos y todos sus datos.    
    private static void imprimirProductos(Connection con){
        try
        {

            Statement statement = con.createStatement();

            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select * from Productos");
            int i=0;
            while(rs.next()){
                i++;
                //imprimimos los datos de cada tienda.
                System.out.println(" -Producto "+i+"-");
                System.out.println("\tNombre producto: " + rs.getString("nombre_pd"));
                System.out.println("\tDescripción: " + rs.getString("descripcion"));
                System.out.println("\tPrecio: " + rs.getString("precio"+"€"));
            }
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    //Método que imprime todos los productos, pero solo sus nombres   
    private static void imprimirProductos2(Connection con){
        try
        {

            Statement statement = con.createStatement();

            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select * from Productos");
            int i=0;
            while(rs.next()){
                i++;
                //imprimimos los datos de cada tienda.
                System.out.println("-Producto "+i+"-");
                System.out.println("\tNombre producto: " + rs.getString("nombre_pd"));
            }
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    
    //Este método borra una tienda de la base de datos según su nombre.
    private static void eliminarProducto(Connection con, String nombre_pd){
        try{
            String sql = "DELETE FROM Productos WHERE nombre_pd = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, nombre_pd);
            pstmt.executeUpdate();
            System.out.println("¡Producto eliminado satisfactoriamente!");
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    //Este método inserta un producto relacionado con una tienda en la tabla Productos_Tiendas.
    private static void insertarProductosTiendas(Connection con, String nombre_t, String nombre_pd,
            int stock){
        try{
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO Productos_Tiendas(nombre_t, nombre_pd, stock) VALUES(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setString(1, nombre_t);
            pstmt.setString(2, nombre_pd);
            pstmt.setInt(3, stock);
            pstmt.executeUpdate();
            System.out.println("¡Producto añadido satisfactoriamente a la tienda!");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    //Método que selecciona y guarda los productos-tienda.    
    private static void seleccionarProductosTiendas(Connection con){
        try
        {

            Statement statement = con.createStatement();
            
            //Probamos a realizar la consulta
            ResultSet rs = statement.executeQuery("select * from Productos_Tiendas");
            while(rs.next()){
                tiendas.getProductosTiendas().add(new ProductosTiendas(rs.getString("nombre_t"),
                        rs.getString("nombre_pd"), rs.getInt("stock")));
            }
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    //Método que imprime los datos de todos los productos ligados a una determinada tienda.    
    private static void imprimirProductosTiendas(Connection con, String nombre_t){
        try
        {
            String sql = "select * from Productos_Tiendas where nombre_t=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, nombre_t);
            //Probamos a realizar unha consulta
            ResultSet rs = pstmt.executeQuery();
            int i=0;
            while(rs.next()){
                i++;
                //imprimimos los datos de cada tienda.
                System.out.println(" -Producto "+i+" -");
                System.out.println("\tNombre producto: " + rs.getString("nombre_pd"));
                System.out.println("\tStock: " + rs.getString("stock"));
                tiendas.getProductosTiendas().add(new ProductosTiendas(rs.getString("nombre_t"),
                        rs.getString("nombre_pd"), rs.getInt("stock")));
            }
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    //Método que imprime todos los productos ligados a una determinada tienda.    
    private static void imprimirProductosTiendas2(Connection con, String nombre_t){
        try
        {
            String sql = "select * from Productos_Tiendas where nombre_t=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, nombre_t);
            //Probamos a realizar unha consulta
            ResultSet rs = pstmt.executeQuery();
            int i=0;
            while(rs.next()){
                i++;
                //imprimimos los datos de cada tienda.
                System.out.println(" -Producto "+i+" -");
                System.out.println("\tNombre producto: " + rs.getString("nombre_pd"));
                tiendas.getProductosTiendas().add(new ProductosTiendas(rs.getString("nombre_t"),
                        rs.getString("nombre_pd"), rs.getInt("stock")));
            }
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    //Método que imprime el stock de un producto ligado a una determinada tienda.    
    private static void imprimirProductosDeterminadoTiendas(Connection con,
            String nombre_t, String nombre_pd){
        try
        {
            String sql = "select * from Productos_Tiendas where nombre_t=? and nombre_pd=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, nombre_t);
            pstmt.setString(2, nombre_pd);
            //Probamos a realizar una consulta
            ResultSet rs = pstmt.executeQuery();
            int i=0;
            while(rs.next()){
                i++;
                //imprimimos los datos de dicho producto.
                System.out.println("\n-Producto: " + rs.getString("nombre_pd"));
                System.out.println("\tStock: " + rs.getString("stock"));
            }
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    
    //Este método borra una tienda de la base de datos según su nombre y la tienda a la que esta ligado.
    private static void eliminarProductosTiendas(Connection con, String nombre_t, String nombre_pd){
        try{
            String sql = "DELETE FROM Productos_Tiendas WHERE nombre_t = ? and nombre_pd = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, nombre_t);
            pstmt.setString(2, nombre_pd);
            pstmt.executeUpdate();
            System.out.println("¡Producto eliminado satisfactoriamente de la tienda!");
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    //Método que actualiza el stock de un producto de una determinada tienda.
    private static void actualizarStockTiendasProductos(Connection con,int newStock,String nombre_t, String nombre_pd){
        try{
            String sql = "UPDATE Productos_Tiendas SET stock = ? "
                + "WHERE nombre_t = ? and nombre_pd = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, newStock);
            pstmt.setString(2, nombre_t);
            pstmt.setString(3, nombre_pd);
            pstmt.executeUpdate();
            System.out.println("¡Stock del producto actualizado satisfactoriamente!");
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    //Este método inserta un nuevo empleado en la tabla Empleados.
    private static void insertarEmpleado(Connection con, String nombre_e, String apellidos){
        try{
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO Empleados(nombre_e, apellidos) VALUES(?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setString(1, nombre_e);
            pstmt.setString(2, apellidos);
            pstmt.executeUpdate();
            System.out.println("¡Empleado añadido satisfactoriamente!");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    //Método que selecciona y guarda todos los empleados.    
    private static void seleccionarEmpleados(Connection con){
        try
        {

            Statement statement = con.createStatement();
            
            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select * from Empleados");
            while(rs.next()){
                tiendas.getEmpleados().add(new Empleados(rs.getString("nombre_e"),rs.getString("apellidos")));
            }
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    //Método que imprime todos los empleados con todos sus datos.    
    private static void imprimirEmpleados(Connection con){
        try
        {

            Statement statement = con.createStatement();

            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select * from Empleados");
            int i=0;
            while(rs.next()){
                i++;
                //imprimimos los datos de cada tienda.
                System.out.println(" -Empleado "+i+"-");
                System.out.println("\tNombre: " + rs.getString("nombre_e"));
                System.out.println("\tApellidos: " + rs.getString("apellidos"));
            }
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
     
    //Este método borra un empleado de la base de datos según su nombre y apellidos.
    private static void eliminarEmpleado(Connection con, String nombre_e, String apellidos){
        try{
            String sql = "DELETE FROM Empleados WHERE nombre_e = ? and apellidos = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, nombre_e);
            pstmt.setString(2, apellidos);
            pstmt.executeUpdate();
            System.out.println("¡Empleado eliminado satisfactoriamente!");
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    //Este método inserta un empleado relacionado con una tienda en la tabla Empleados_Tiendas.
    private static void insertarEmpleadosTiendas(Connection con, String nombre_t, String nombre_e,
            double horas_trabajadas){
        try{
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO Empleados_Tiendas(nombre_t, nombre_e, horas_trabajadas) VALUES(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setString(1, nombre_t);
            pstmt.setString(2, nombre_e);
            pstmt.setDouble(3, horas_trabajadas);
            pstmt.executeUpdate();
            System.out.println("¡Empleado añadido satisfactoriamente a la tienda!");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    //Método que imprime todos los empleados ligados a una determinada tienda.    
    private static void imprimirEmpleadosTiendas2(Connection con, String nombre_t){
        try
        {
            String sql = "select * from Empleados_Tiendas where nombre_t=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, nombre_t);
            //Probamos a realizar unha consulta
            ResultSet rs = pstmt.executeQuery();
            int i=0;
            while(rs.next()){
                i++;
                //imprimimos los datos de cada tienda.
                System.out.println(" -Empleado "+i+" -");
                System.out.println("\tNombre y apellidos: " + rs.getString("nombre_e"));
                tiendas.getEmpleadosTiendas().add(new EmpleadosTiendas(rs.getString("nombre_t"),
                        rs.getString("nombre_e"), rs.getDouble("horas_trabajadas")));
            }
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    //Método que selecciona y guarda los empleados-tienda.    
    private static void seleccionarEmpleadosTiendas(Connection con){
        try
        {

            Statement statement = con.createStatement();
            
            //Probamos a realizar la consulta
            ResultSet rs = statement.executeQuery("select * from Empleados_Tiendas");
            while(rs.next()){
                tiendas.getEmpleadosTiendas().add(new EmpleadosTiendas(rs.getString("nombre_t"),
                        rs.getString("nombre_e"), rs.getDouble("horas_trabajadas")));
            }
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    //Método que imprime los datos de todos los empleados ligados a una determinada tienda.    
    private static void imprimirEmpleadosTiendas(Connection con, String nombre_t){
        try
        {
            String sql = "select * from Empleados_Tiendas where nombre_t=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, nombre_t);
            //Probamos a realizar unha consulta
            ResultSet rs = pstmt.executeQuery();
            int i=0;
            while(rs.next()){
                i++;
                //imprimimos los datos de cada tienda.
                System.out.println(" -Empleado "+i+" -");
                System.out.println("\tNombre: " + rs.getString("nombre_e"));
                System.out.println("\tHoras trabajadas: " + rs.getString("horas_trabajadas"));
                tiendas.getProductosTiendas().add(new ProductosTiendas(rs.getString("nombre_t"),
                        rs.getString("nombre_pd"), rs.getInt("stock")));
            }
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    //Este método borra una tienda de la base de datos según su nombre y la tienda a la que esta ligado.
    private static void eliminarEmpleadosTiendas(Connection con, String nombre_t, String nombre_e){
        try{
            String sql = "DELETE FROM Empleados_Tiendas WHERE nombre_t = ? and nombre_e = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, nombre_t);
            pstmt.setString(2, nombre_e);
            pstmt.executeUpdate();
            System.out.println("¡Empleado eliminado satisfactoriamente de la tienda!");
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    //Este método inserta una nueva tienda en la tabla Tiendas.
    private static void insertarClientes(Connection con, String nombre_c, String apellidos, String email){
        try{
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO Clientes (nombre_c, apellidos, email) VALUES(?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setString(1, nombre_c);
            pstmt.setString(2, apellidos);
            pstmt.setString(3, email);
            pstmt.executeUpdate();
            System.out.println("¡Cliente añadido satisfactoriamente!");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    //Método que selecciona y guarda todas las tiendas.    
    private static void seleccionarClientes(Connection con){
        try
        {

            Statement statement = con.createStatement();
            
            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select * from Clientes");
            while(rs.next()){
                empresa.getClientes().add(new Clientes(rs.getString("nombre_c"),rs.getString("apellidos")
                        , rs.getString("email")));
            }
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    //Método que imprime todos los datos de todos los clientes   
    private static void imprimirClientes(Connection con){
        try
        {

            Statement statement = con.createStatement();

            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select * from Clientes");
            int i=0;
            while(rs.next()){
                i++;
                //imprimimos los datos de cada tienda.
                System.out.println(" -Cliente "+i+"-");
                System.out.println("\tNombre: " + rs.getString("nombre_c"));
                System.out.println("\tApellidos: " + rs.getString("apellidos"));
                System.out.println("\tEmail " + rs.getString("email"));
            }
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    //Método que imprime el nombre y apellidos de todos los clientes    
    private static void imprimirClientes2(Connection con){
        try
        {

            Statement statement = con.createStatement();

            //Probamos a realizar unha consulta
            ResultSet rs = statement.executeQuery("select * from Clientes");
            int i=0;
            while(rs.next()){
                i++;
                //imprimimos los datos de cada tienda.
                System.out.println(" -Cliente "+i+"-");
                System.out.println("\tNombre y apellidos: " + rs.getString("nombre_c")+" "+rs.getString("apellidos"));
            }
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    } 
    
    //Este método borra un cliente de la base de datos según su nombre y apellidos
    private static void eliminarCliente(Connection con, String nombre_c, String apellidos_c){
        try{
            String sql = "DELETE FROM Clientes WHERE nombre_c = ? and apellidos = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, nombre_c);
            pstmt.setString(2, apellidos_c);
            pstmt.executeUpdate();
            System.out.println("¡Cliente eliminado satisfactoriamente!");
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    //Método para insertar las provincias en la tabla provincias.
    private static void insertarProvincias(Connection con, String id, String nombre_p){
        try{
            //Fixate que no código SQL o valor do nome e "?". Este valor engadiremolo despois
            String sql = "INSERT INTO provincias(id, nombre_p) VALUES(?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            //Aquí e cando engadimos o valor do nome
            pstmt.setString(1, id);
            pstmt.setString(2, nombre_p);
            pstmt.executeUpdate();
            System.out.println("¡Provincias añadidas a la base de datos!");
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    //Método encargado de leer el fichero Json de las provincias.
    public static void leerJson(){
        //Comenzamos declarando el fichero.
        File fichero = new File("provincias.json");
        if(fichero.exists()){
            try {
                JsonParser parser = new JsonParser();
                FileReader fr = new FileReader("provincias.json");
                JsonElement datos = parser.parse(fr);
                dumpJSONElement(datos);                
            } 
            catch (FileNotFoundException e) {
                System.out.println("No se encuentra el archivo.");
            } 
            catch (IOException e) {
                System.out.println("Error de tipo E/S.");
            }
        }    
        
    }
    public static void dumpJSONElement(JsonElement elemento) {
    if (elemento.isJsonObject()) {
        JsonObject obj = elemento.getAsJsonObject();
        java.util.Set<java.util.Map.Entry<String,JsonElement>> entradas = obj.entrySet();
        java.util.Iterator<java.util.Map.Entry<String,JsonElement>> iter = entradas.iterator();
        while (iter.hasNext()) {
            java.util.Map.Entry<String,JsonElement> entrada = iter.next();
            System.out.println("-Clave: " + entrada.getKey());
            System.out.println("-Valor:");
            dumpJSONElement(entrada.getValue());
        }
 
    } 
    else if (elemento.isJsonArray()) {
        JsonArray array = elemento.getAsJsonArray();
        System.out.println("-Array. Numero de elementos: " + array.size());
        java.util.Iterator<JsonElement> iter = array.iterator();
        while (iter.hasNext()) {
            JsonElement entrada = iter.next();
            dumpJSONElement(entrada);
    }
    } 
    else if (elemento.isJsonPrimitive()) {
        System.out.println("\t(tipo primitivo)");
        JsonPrimitive valor = elemento.getAsJsonPrimitive();
        ++c;
        String valorId, valorNombre;
        if (c%2==0) {
            System.out.println("\t\ttexto: " + valor.getAsString()+" par");
            valorNombre=valor.getAsString();
        }
        else{
           System.out.println("\t\ttexto: " + valor.getAsString()+" impar"); 
           valorId = valor.getAsString();
        }
    } 
    else if (elemento.isJsonNull()) {
        System.out.println("Null");
    } 
    else {
        System.out.println("Es otra cosa");
    }
}
    
    public static void escribirJson(){
        //Pasamos nuestra clase a JSON utilizando la libreria GSON.
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(provincias);
                
        //Comenzamos declarando el fichero json.
        File fichero = new File("Provincias.json");

        try {
            //Creamos el flujo de escritura del fichero.
            FileWriter flw = new FileWriter(fichero);
            //Creamos el buffer de escritura.
            BufferedWriter bw = new BufferedWriter(flw);
            //Escribimos el fichero json.
            bw.write(json);
            //Cerramos el fichero.
            bw.close();
        }
        catch (IOException e) {
            System.out.println("Error de tipo E/S.");
        }                        
    }
}