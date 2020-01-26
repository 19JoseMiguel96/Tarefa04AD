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
public class Clientes {
    private final String nombre_c;
    private final String apellidos_c;
    private String email;
    
    public Clientes(String nombre_c,String apellidos_c,String email) {
        this.nombre_c = nombre_c;
        this.apellidos_c = apellidos_c;
        this.email = email;
    }

    public String getNombreC() {
        return nombre_c;
    }
    
    public String getApellidosC() {
        return apellidos_c;
    }

    public String getEmail() {
        return email;
    }
    
}
