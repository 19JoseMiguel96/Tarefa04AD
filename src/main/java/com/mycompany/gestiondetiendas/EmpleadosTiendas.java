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
 * @author JoseM
 */
@Entity
@Table(name = "empleados_tiendas")
public class EmpleadosTiendas implements Serializable{
    @EmbeddedId
    private EmpleadosTiendasID id;

    @ManyToOne
    @MapsId("empleado_id")
    @JoinColumn(name = "empleado_id")
    private Empleados empleado;

    @ManyToOne
    @MapsId("tienda_id")
    @JoinColumn(name = "tienda_id")
    private Tiendas tienda;

    private Float jornada;

    public EmpleadosTiendas() {}

    public EmpleadosTiendas(Empleados empleado, Tiendas tienda, Float jornada) {
        this.id = new EmpleadosTiendasID(empleado, tienda);
        this.empleado = empleado;
        this.tienda = tienda;
        this.jornada = jornada;
    }

    public EmpleadosTiendas(Empleados empleado, Tiendas tienda) {
        this(empleado, tienda, 0f);
    }

    public EmpleadosTiendasID getId() {
        return id;
    }

    public void setId(EmpleadosTiendasID id) {
        this.id = id;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public Tiendas getTienda() {
        return tienda;
    }

    public void setTienda(Tiendas tienda) {
        this.tienda = tienda;
    }

    public Float getJornada() {
        return jornada;
    }

    public void setJornada(Float horas) {
        this.jornada = horas;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null) {
            return false;
        }

        if (getClass() != object.getClass()) {
            return false;
        }

        EmpleadosTiendas other = (EmpleadosTiendas) object;

        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }

        return true;
    }
    
}