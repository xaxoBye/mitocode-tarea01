package com.mitocode.controller;

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
import com.mitocode.model.Persona;
import com.mitocode.service.IPersonaService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/personas")
public class PersonaController {
	
	@Autowired
	private IPersonaService service;
	
	@GetMapping
	public ResponseEntity<List<Persona>> listar() throws Exception{
		List<Persona> lista = service.listar();		
		return new ResponseEntity<List<Persona>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Persona> listarPorId(@PathVariable("id") Integer idper) throws Exception{
		Persona obj = service.listarPorId(idper);		
		
		if(obj == null) {
			throw new ModelNotFoundException("[Persona] ID NO ENCONTRADO: " + idper);
		}
		
		return new ResponseEntity<Persona>(obj, HttpStatus.OK);
	}
	
	@GetMapping("/hateoas/{id}")
	public EntityModel<Persona> listarPorIdHateoas(@PathVariable("id") Integer idper) throws Exception{
		Persona obj = service.listarPorId(idper);		
		
		if(obj == null) {
			throw new ModelNotFoundException("[Persona] ID NO ENCONTRADO: " + idper);
		}
		
		EntityModel<Persona> recurso = EntityModel.of(obj);
		
		WebMvcLinkBuilder link =  linkTo(methodOn(this.getClass()).listarPorId(idper));
		
		recurso.add(link.withRel("paciente-recurso"));
		
		return recurso;
	}
	
//	@PostMapping
//	public ResponseEntity<Persona> registrar(@Valid @RequestBody Persona p) throws Exception{
//		Persona obj = service.registrar(p);
//		return new ResponseEntity<Persona>(obj, HttpStatus.CREATED);
//	}
	
	@PostMapping
	public ResponseEntity<Persona> registrar(@Valid @RequestBody Persona p) throws Exception{
		Persona obj = service.registrar(p);
		
//		localhost:8080/personas/2
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdPersona()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping
	public ResponseEntity<Persona> modificar(@Valid @RequestBody Persona p) throws Exception{
		Persona obj = service.modificar(p);	
		
		
		return new ResponseEntity<Persona>(obj, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{ID}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer idBorrar) throws Exception{
		Persona obj = service.listarPorId(idBorrar);
		
		if(obj == null) {
			throw new ModelNotFoundException("[Persona] ID NO ENCONTRADO: " + idBorrar);
		}
		
		service.eliminar(idBorrar);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	
}
