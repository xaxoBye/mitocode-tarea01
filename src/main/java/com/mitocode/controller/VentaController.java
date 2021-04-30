package com.mitocode.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mitocode.exception.ModelNotFoundException;
import com.mitocode.model.Venta;
import com.mitocode.service.IVentaService;

@RestController
@RequestMapping("/ventas")
public class VentaController {
	
	@Autowired
	private IVentaService service;
	
	@GetMapping
	public ResponseEntity<List<Venta>> listar() throws Exception{
		List<Venta> lista = service.listar();		
		return new ResponseEntity<List<Venta>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Venta> listarPorId(@PathVariable("id") Integer idper) throws Exception{
		Venta obj = service.listarPorId(idper);		
		
		if(obj == null) {
			throw new ModelNotFoundException("[Venta] ID NO ENCONTRADO: " + idper);
		}
		
		return new ResponseEntity<Venta>(obj, HttpStatus.OK);
	}
	
	@GetMapping("/hateoas/{id}")
	public EntityModel<Venta> listarPorIdHateoas(@PathVariable("id") Integer idper) throws Exception{
		Venta obj = service.listarPorId(idper);		
		
		if(obj == null) {
			throw new ModelNotFoundException("[Venta] ID NO ENCONTRADO: " + idper);
		}
		
		EntityModel<Venta> recurso = EntityModel.of(obj);
		
		WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).listarPorId(idper));
		
		recurso.add(link.withRel("paciente-recurso"));
		
		return recurso;
	}
	
//	@PostMapping
//	public ResponseEntity<Venta> registrar(@Valid @RequestBody Venta p) throws Exception{
//		Venta obj = service.registrar(p);
//		return new ResponseEntity<Venta>(obj, HttpStatus.CREATED);
//	}
	
	@PostMapping
	public ResponseEntity<Venta> registrar(@Valid @RequestBody Venta p) throws Exception{
		Venta obj = service.registrarTransaccional(p);
		
//		localhost:8080/personas/2
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdVenta()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<Venta> modificar(@Valid @RequestBody Venta p) throws Exception{
		Venta obj = service.modificar(p);	
		
		
		return new ResponseEntity<Venta>(obj, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{ID}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer idBorrar) throws Exception{
		Venta obj = service.listarPorId(idBorrar);
		
		if(obj == null) {
			throw new ModelNotFoundException("[Venta] ID NO ENCONTRADO: " + idBorrar);
		}
		
		service.eliminar(idBorrar);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	
}
