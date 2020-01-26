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
public class EmpleadosTiendas {
 private final String nombre_t;
    private final String nombre_e;
    private Double horas_trabajadas;
            
    public EmpleadosTiendas(String nombre_t,String nombre_e, Double horas_trabajadas) {
        this.nombre_t = nombre_t;
        this.nombre_e = nombre_e;
        this.horas_trabajadas = horas_trabajadas;
    }

    public String getNombreT() {
        return nombre_t;
    } 
    public String getNombreE() {
        return nombre_e;
    }

    public Double getHorasTrabajadas() {
        return horas_trabajadas;
    }
    
}