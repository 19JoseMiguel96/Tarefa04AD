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
 */
@Embeddable
public class ProductosTiendasID implements Serializable {
    private static final long serialVersionUID = -6663566021438795925L;

    private Long producto_id;
    private Long tienda_id;

    public ProductosTiendasID() {}

    public ProductosTiendasID(Productos producto, Tiendas tienda) {
        this.producto_id = producto.getId();
        this.tienda_id = tienda.getId();
    }

    public Long getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(Long productoId) {
        this.producto_id = productoId;
    }

    public Long getTienda_id() {
        return tienda_id;
    }

    public void setTienda_id(Long tiendaId) {
        this.tienda_id = tiendaId;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tienda_id == null) ? 0 : tienda_id.hashCode());
        result = prime * result + ((producto_id == null) ? 0 : producto_id.hashCode());
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

        ProductosTiendasID other = (ProductosTiendasID) object;

        if (tienda_id == null) {
            if (other.tienda_id != null) {
                return false;
            }
        } else if (!tienda_id.equals(other.tienda_id)) {
            return false;
        }

        if (producto_id == null) {
            if (other.producto_id != null) {
                return false;
            }
        } else if (!producto_id.equals(other.producto_id)) {
            return false;
        }

        return true;
    }
}
