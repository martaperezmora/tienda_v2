package com.mpm.springprojects.tienda.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class DetallePedido {
    @Id
    @GeneratedValue
    private int codigo;
    private int cantidad;
    private float total;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @MapsId("producto_codigo")
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @MapsId("pedido_codigo")
    private Pedido pedido;

    public DetallePedido() {
    }
    
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public float getTotal() {
        return total;
    }
    public void setTotal(float total) {
        this.total = total;
    }
    public Producto getProducto() {
        return producto;
    }
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + codigo;
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DetallePedido other = (DetallePedido) obj;
        if (codigo != other.codigo)
            return false;
        return true;
    }

   
}
