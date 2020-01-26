package com.mycompany.gestiondetiendas;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Jose Miguel
 */
public class Titular {
    private String titulo;
    
    public Titular(){
        this.titulo = new String("");
    }
    public Titular(String titulo1){
        this.titulo = titulo1;
    }

    public String getTitular() {
        return titulo;
    }

    public void setTitular(String titular) {
        this.titulo = titular;
    }
    
}
