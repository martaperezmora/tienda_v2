package com.mpm.springprojects.tienda.model;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

@Entity
public class Pedido {

    @Id
    @GeneratedValue
    private int codigo;
    @ManyToOne
    private Cliente cliente;
    @Transient
    private List<DetallePedido> detallePedidos;
    private double total;
    private Date fecha;

    public Pedido() {
        this.cliente = new Cliente();
        this.detallePedidos = new ArrayList<DetallePedido>();
    }

    // esto para el mapeo
    @PrePersist
    public void PrePersistFecha(){
        this.fecha = new Date();
    }
    
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public List<DetallePedido> getDetallePedidos() {
        return detallePedidos;
    }
    public void setDetallePedidos(List<DetallePedido> detallePedidos) {
        this.detallePedidos = detallePedidos;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getTotalCesta() {
        total = 0;
        for (DetallePedido detalle : this.detallePedidos) {
            total += detalle.getCantidad()*detalle.getProducto().getPrecio();
        }

        return total;
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
        Pedido other = (Pedido) obj;
        if (codigo != other.codigo)
            return false;
        return true;
    }

}
