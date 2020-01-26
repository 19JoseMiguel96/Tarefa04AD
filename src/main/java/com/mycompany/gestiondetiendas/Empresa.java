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
public class Empresa {
    private ArrayList<Tiendas> tiendas;
    private ArrayList<Clientes> clientes;
    

    public Empresa() {
        this.tiendas = new ArrayList();
        this.clientes = new ArrayList();
    }

    public ArrayList<Clientes> getClientes() {
        return clientes;
    }

    public ArrayList<Tiendas> getTiendas() {
        return tiendas;
    }
    
}
