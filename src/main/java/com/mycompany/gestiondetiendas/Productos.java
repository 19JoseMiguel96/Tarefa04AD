/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestiondetiendas;

/**
 *
 * @author Jose Miguel
 */
public class Productos {
    private final String nombre;
    private final String descripcion;
    private double precio;
            
    public Productos(String nombre,String descripcion,double precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }   

    public String getDescripcion() {
        return descripcion;
    }
    
    public double getPrecio() {
        return precio;
    }

    
    
}
