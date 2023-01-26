package com.mpm.springprojects.tienda.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class DetallePedido {

    @EmbeddedId
    private DetallePedidoId id = new DetallePedidoId();

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
        
    public DetallePedidoId getId() {
        return id;
    }

    public void setId(DetallePedidoId id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
   
}
