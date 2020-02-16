/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestiondetiendas;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Jose Miguel
 */
@Entity
@Table(name = "empleados")
public class Empleados implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name="nombre_e", nullable = false)
    private String nombre;

    @Column (name="apellidos", nullable = false)
    private String apellidos;

    @OneToMany(mappedBy = "empleado")
    private List<EmpleadosTiendas> tiendas = new ArrayList<>();

    public Empleados() {}

    public Empleados(String nombre, String apellidos) {
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellidos;
    }

    public List<EmpleadosTiendas> getTiendas() {
        return tiendas;
    }

    public void setTiendas(List<EmpleadosTiendas> tiendas) {
        this.tiendas = tiendas;
    }

    /**
     * Dada una tienda y una jornada, la añade a la lista de relaciones de
     * este empleado con las diferentes tiendas.
     *
     * @param tienda Tienda que debe figurar en la relación que vamos a añadir.
     * @param horas Jornada semanal del empleado.
     */
    public void addTienda(Tiendas tienda, Float horas) {
        EmpleadosTiendas relEmpleadoTienda = new EmpleadosTiendas(this, tienda, horas);
        tiendas.add(relEmpleadoTienda);
        tienda.getEmpleados().add(relEmpleadoTienda);
    }

    /**
     * Dada una tienda, elimina la relación que tiene este empleado con ella.
     *
     * @param tienda Tienda que figura en la relación que vamos a eliminar.
     */
    public void delTienda(Tiendas tienda) {
        EmpleadosTiendas relEmpleadoTienda = new EmpleadosTiendas(this, tienda);
        tiendas.remove(relEmpleadoTienda);
        tienda.getEmpleados().remove(relEmpleadoTienda);
    }

    public String toString() {
        return "  Empleado "+id + ":\n\t " + nombre + " " + apellidos;
    }
    
}
