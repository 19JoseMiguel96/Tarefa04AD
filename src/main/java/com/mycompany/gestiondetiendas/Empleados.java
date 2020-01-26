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
public class Empleados {
    private final String nombre;
    private final String apellidos;
    public Empleados(String nome, String apellidos) {
        this.nombre = nome;
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }
    
    public String getApellidos() {
        return apellidos;
    }
    
}
