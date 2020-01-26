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
public class Provincias {
    private final int id_pro;
    private final String nombre_pro;
    
    public Provincias(int id_pro,String nombre_pro) {
        this.id_pro = id_pro;
        this.nombre_pro = nombre_pro;
    }
    public int getIdPro() {
        return id_pro;
    }

    public String getNombrePro() {
        return nombre_pro;
    }
    
}