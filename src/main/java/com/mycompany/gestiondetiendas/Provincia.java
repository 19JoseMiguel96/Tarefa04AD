/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestiondetiendas;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author JoseM
 */
@Entity
@Table(name = "provincias")
public class Provincia implements Serializable {
    private static final long serialVersionUID = 8330729771273082960L;

    @Id
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    public Provincia() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String toString() {
        return "  Provincia "+id + ":\n\t" + nome;
    }
}
