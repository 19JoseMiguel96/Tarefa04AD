/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gestiondetiendas;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 *
 * @author JoseM
 */@Embeddable
public class EmpleadosTiendasID implements Serializable{
    private Long empleado_id;
    private Long tienda_id;

    public EmpleadosTiendasID() {}

    public EmpleadosTiendasID(Empleados empleados, Tiendas tiendas) {
        this.empleado_id = empleados.getId();
        this.tienda_id = tiendas.getId();
    }

    public Long getEmpleado_id() {
        return empleado_id;
    }

    public void setEmpleado_id(Long empleado_id) {
        this.empleado_id = empleado_id;
    }

    public Long getTienda_id() {
        return tienda_id;
    }

    public void setTienda_id(Long tienda_id) {
        this.tienda_id = tienda_id;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tienda_id == null) ? 0 : tienda_id.hashCode());
        result = prime * result + ((empleado_id == null) ? 0 : empleado_id.hashCode());
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

        EmpleadosTiendasID other = (EmpleadosTiendasID) object;

        if (tienda_id == null) {
            if (other.tienda_id != null) {
                return false;
            }
        } else if (!tienda_id.equals(other.tienda_id)) {
            return false;
        }

        if (empleado_id == null) {
            if (other.empleado_id != null) {
                return false;
            }
        } else if (!empleado_id.equals(other.empleado_id)) {
            return false;
        }

        return true;
    }    
    
}
