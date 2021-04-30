package com.mitocode.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitocode.model.DetalleVenta;
import com.mitocode.model.Venta;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IVentaRepo;
import com.mitocode.service.IVentaService;

@Service
public class VentaServiceImpl extends CRUDImpl<Venta, Integer> implements IVentaService {
	
	@Autowired
	private IVentaRepo repo;


	@Override
	protected IGenericRepo<Venta, Integer> getRepo() {
		return repo;
	}


	@Override
	public Venta registrarTransaccional(Venta venta) {
		// insertar venta para obtener la llave primaria
		// insertar detalleVenta , usando la PK previa
		
		venta.getDetalleVenta().forEach(det -> det.setVentaLink(venta));
		
		return repo.save(venta);
//		List<DetalleVenta> listaDetalle =  venta.getDetalleVenta();
//		for(DetalleVenta det : listaDetalle) {
//			det.setVentaLink(venta);
//		}
	
	}



}
