package com.mitocode.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="detalle_venta")
public class DetalleVenta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idDetalleVenta;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="id_venta", nullable=false, foreignKey = @ForeignKey(name= "FK_venta_detalle"))
	private Venta ventaLink;
	
	@ManyToOne
	@JoinColumn(name="id_producto", nullable=false, foreignKey = @ForeignKey(name= "FK_producto_detalle"))
	private Producto producto;
	
	@Column(name="cantidad", nullable=false)
	private Integer cantidad;

	public Integer getIdDetalleVenta() {
		return idDetalleVenta;
	}

	public void setIdDetalleVenta(Integer idDetalleVenta) {
		this.idDetalleVenta = idDetalleVenta;
	}

	public Venta getVentaLink() {
		return ventaLink;
	}

	public void setVentaLink(Venta ventaLink) {
		this.ventaLink = ventaLink;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	

}
