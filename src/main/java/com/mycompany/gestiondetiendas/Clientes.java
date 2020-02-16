/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestiondetiendas;
import javax.persistence.*;
import java.io.Serializable;


/**
 *
 * @author Jose Miguel
 */
@Entity
@Table(name = "clientes")
public class Clientes implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column (name="nombre_c", nullable = false)
    private String nombre_c;
    @Column (name="apellidos", nullable = false)
    private String apellidos_c;
    @Column (name="email", nullable = false)
    private String email;
    
    public Clientes() {}
    public Clientes(String nombre_c,String apellidos_c,String email) {
        this.nombre_c = nombre_c;
        this.apellidos_c = apellidos_c;
        this.email = email;
    }
        public Long getIdC() {
        return id;
    }

    public void setIdC(Long id) {
        this.id = id;
    }
    public String getNombreC() {
        return nombre_c;
    }
    public void setNombreC(String nombre_c) {
        this.nombre_c = nombre_c;
    }
    
    public String getApellidosC() {
        return apellidos_c;
    }
    public void setApellidosC(String apellidos_c) {
        this.apellidos_c = apellidos_c;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String toString() {
        return "  Cliente "+id + ":\n\t" + nombre_c + " " + apellidos_c + "\n\t"+ email;
    }
}
