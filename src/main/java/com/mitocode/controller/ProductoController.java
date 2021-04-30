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
import com.mitocode.model.Producto;
import com.mitocode.service.IProductoService;

@RestController
@RequestMapping("/productos")
public class ProductoController {
	
	@Autowired
	private IProductoService service;
	
	@GetMapping
	public ResponseEntity<List<Producto>> listar() throws Exception{
		List<Producto> lista = service.listar();		
		return new ResponseEntity<List<Producto>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Producto> listarPorId(@PathVariable("id") Integer idper) throws Exception{
		Producto obj = service.listarPorId(idper);		
		
		if(obj == null) {
			throw new ModelNotFoundException("[Producto] ID NO ENCONTRADO: " + idper);
		}
		
		return new ResponseEntity<Producto>(obj, HttpStatus.OK);
	}
	
	@GetMapping("/hateoas/{id}")
	public EntityModel<Producto> listarPorIdHateoas(@PathVariable("id") Integer idper) throws Exception{
		Producto obj = service.listarPorId(idper);		
		
		if(obj == null) {
			throw new ModelNotFoundException("[Producto] ID NO ENCONTRADO: " + idper);
		}
		
		EntityModel<Producto> recurso = EntityModel.of(obj);
		
		WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).listarPorId(idper));
		
		recurso.add(link.withRel("paciente-recurso"));
		
		return recurso;
	}
	
//	@PostMapping
//	public ResponseEntity<Producto> registrar(@Valid @RequestBody Producto p) throws Exception{
//		Producto obj = service.registrar(p);
//		return new ResponseEntity<Producto>(obj, HttpStatus.CREATED);
//	}
	
	@PostMapping
	public ResponseEntity<Producto> registrar(@Valid @RequestBody Producto p) throws Exception{
		Producto obj = service.registrar(p);
		
//		localhost:8080/personas/2
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdProducto()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<Producto> modificar(@Valid @RequestBody Producto p) throws Exception{
		Producto obj = service.modificar(p);	
		
		
		return new ResponseEntity<Producto>(obj, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{ID}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer idBorrar) throws Exception{
		Producto obj = service.listarPorId(idBorrar);
		
		if(obj == null) {
			throw new ModelNotFoundException("[Producto] ID NO ENCONTRADO: " + idBorrar);
		}
		
		service.eliminar(idBorrar);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	
}
