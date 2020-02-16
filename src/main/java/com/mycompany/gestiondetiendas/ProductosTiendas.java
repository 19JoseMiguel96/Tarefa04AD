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
@Table(name = "productos_tiendas")
public class ProductosTiendas implements Serializable {
    @EmbeddedId
    private ProductosTiendasID id;

    @ManyToOne
    @MapsId("producto_id")
    @JoinColumn(name = "producto_id")
    private Productos producto;

    @ManyToOne
    @MapsId("tienda_id")
    @JoinColumn(name = "tienda_id")
    private Tiendas tienda;
    
    @Column (name="stock", nullable = false)
    private Long stock;

    public ProductosTiendas() {}

    public ProductosTiendas(Productos producto, Tiendas tienda, Long stock) {
        this.id = new ProductosTiendasID(producto, tienda);
        this.producto = producto;
        this.tienda = tienda;
        this.stock = stock;
    }

    public ProductosTiendas(Productos producto, Tiendas tienda) {
        this(producto, tienda, 0L);
    }

    public ProductosTiendasID getId() {
        return id;
    }

    public void setId(ProductosTiendasID id) {
        this.id = id;
    }

    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }

    public Tiendas getTienda() {
        return tienda;
    }

    public void setTienda(Tiendas tienda) {
        this.tienda = tienda;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
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

        ProductosTiendas other = (ProductosTiendas) object;

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