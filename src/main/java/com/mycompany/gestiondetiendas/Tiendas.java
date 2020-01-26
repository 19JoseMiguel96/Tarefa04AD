/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestiondetiendas;

import java.util.ArrayList;

/**
 *
 * @author Jose Miguel
 */
public class Tiendas {
    private final String nombre_t;
    private final String ciudad;
    private final String provincia;
    private ArrayList<Empleados> empleados;
    private ArrayList<Productos> productos;
    private ArrayList<ProductosTiendas> productostiendas;
    private ArrayList<EmpleadosTiendas> empleadostiendas;
  
    public Tiendas(String nombre_t, String ciudad, String provincia) {
        this.nombre_t = nombre_t;
        this.ciudad = ciudad;    
        this.provincia = provincia;
        this.empleados = new ArrayList();
        this.productos = new ArrayList();
        this.productostiendas = new ArrayList(); 
        this.empleadostiendas = new ArrayList(); 
    } 
    

    public ArrayList<Empleados> getEmpleados() {
        return empleados;
    }

    public ArrayList<Productos> getProductos() {
        return productos;
    } 
    public ArrayList<ProductosTiendas> getProductosTiendas() {
        return productostiendas;
    } 
    public ArrayList<EmpleadosTiendas> getEmpleadosTiendas() {
        return empleadostiendas;
    }
            
    public String getNombre() {
        return nombre_t;
    }

    public String getCiudad() {
        return ciudad;
    } 
    public String getProvincia() {
        return provincia;
    } 
       
    
}
