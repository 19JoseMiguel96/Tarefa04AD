/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestiondetiendas;

/**
 *
 * @author JoseM
 */
public class ProductosTiendas {
    private final String nombre_t;
    private final String nombre_pd;
    private int cantidad;
            
    public ProductosTiendas(String nombre_t,String nombre_pd, int cantidad) {
        this.nombre_t = nombre_t;
        this.nombre_pd = nombre_pd;
        this.cantidad = cantidad;
    }

    public String getNombreT() {
        return nombre_t;
    } 
    public String getNombrePd() {
        return nombre_pd;
    }

    public int getCantidad() {
        return cantidad;
    }
    
}
