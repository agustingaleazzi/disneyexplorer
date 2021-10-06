package com.disney.explorer.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.disney.explorer.entities.Imagen;
import com.disney.explorer.entities.PeliculaOSerie;
import com.disney.explorer.entities.Personaje;
import com.disney.explorer.errors.ErrorService;
import com.disney.explorer.repositories.PersonajeRepository;

@Service
public class PersonajeService {

	@Autowired
	private PersonajeRepository personajeRepository;
	
	@Autowired
	private ImagenService imagenService;
	@Autowired
	private PeliculaOSerieService peliculaOSerieService;
	
	//add character
	@Transactional
	public void agregarPersonaje(String nombre, Integer peso, String historia, MultipartFile imagen, String pelicula) throws ErrorService{
		validar(nombre);
		
		Personaje personaje = new Personaje();
		personaje.setNombre(nombre);
		personaje.setPeso(peso);
		personaje.setHistoria(historia);
		
		Imagen img = imagenService.guardar(imagen);
		personaje.setImagen(img);
		
		List<PeliculaOSerie> peliculaOSerie = peliculaOSerieService.verificarSiExisteOCrear(pelicula);		
		personaje.setPeliculasOSeries(peliculaOSerie);
		
		personajeRepository.save(personaje);		
	}
	
	//search character by name
	@Transactional
	public Personaje buscarPersonajePorNombre(String nombre) {
		return personajeRepository.buscarPorNombre(nombre);
	}
	
	//search all characters
	@Transactional
	public List<Personaje> buscarTodos() {
		return personajeRepository.findAll();
	}
	
	//modify character
	@Transactional
	public void modificarPersonaje(String idPersonaje, String nombre, Integer peso, String historia, MultipartFile imagen, String pelicula) throws ErrorService{
		validar(nombre);
		
		Optional<Personaje> respuesta = personajeRepository.findById(idPersonaje);	
		if(respuesta.isPresent()) {
			Personaje personaje = respuesta.get();
			personaje.setNombre(nombre);
			personaje.setPeso(peso);
			personaje.setHistoria(historia);
			
			String idImagen = null;
			if(personaje.getImagen() != null) {
				idImagen = personaje.getImagen().getId();
			}
			Imagen img = imagenService.actualizar(idImagen, imagen);
			personaje.setImagen(img);
			
			List<PeliculaOSerie> peliculasOSeries = peliculaOSerieService.verificarSiExisteOCrear(pelicula);
			peliculasOSeries.addAll(personaje.getPeliculasOSeries());
			personaje.setPeliculasOSeries(peliculasOSeries);
			
			personajeRepository.save(personaje);	
		} else {
			throw new ErrorService("El personaje a modificar no ha sido encontrado.");
		}	
	}
	
	//delete character
	@Transactional
	public void eliminarPersonaje(String idPersonaje) throws ErrorService{
		
		Optional<Personaje> respuesta = personajeRepository.findById(idPersonaje);	
		if(respuesta.isPresent()) {
			Personaje personaje = respuesta.get();
			personajeRepository.delete(personaje);
		} else {
			throw new ErrorService("El personaje a eliminar no ha sido encontrado.");
		}
	}
	
	//validate character name
	public void validar(String nombre) throws ErrorService{
		if(nombre==null || nombre.isEmpty()) {
			throw new ErrorService("El nombre del personaje no puede estar vacío.");
		}
	}
	
}
