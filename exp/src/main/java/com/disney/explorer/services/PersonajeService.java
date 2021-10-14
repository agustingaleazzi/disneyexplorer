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
	public void agregarPersonaje(String nombre, Integer edad, Integer peso, String historia, MultipartFile imagen, String pelicula) throws ErrorService{
		validar(nombre);
		
		Personaje personaje = new Personaje();
		personaje.setNombre(nombre);
		personaje.setEdad(edad);
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
		public List<Personaje> buscarPersonajePorNombre(String nombre)throws ErrorService {
			List<Personaje> match = personajeRepository.buscarPorNombre(nombre);
			if(match != null) {
				return match;
			}else {
				throw new ErrorService("No se ha encontrado personajes con ese nombre.");
			}
		}
		
	
	//search all characters
	@Transactional
	public List<Personaje> buscarTodos() {
		return personajeRepository.findAll();
	}
	

	@Transactional
	public Optional<Personaje> buscarPorId(String id) throws ErrorService {
		Optional<Personaje> match = personajeRepository.findById(id);
		if(match.isPresent()) {
			return personajeRepository.findById(id);
		} else {
			throw new ErrorService("No se ha encontrado personaje.");
		}
	}
	
	//addMovieToCharacter
	@Transactional
	public void addMovieToCharacter(String characterID, String movieID) throws ErrorService {
		Optional<Personaje> matchCharacter = buscarPorId(characterID);
		Optional<PeliculaOSerie> matchPeliculaOSerie = peliculaOSerieService.buscarPorId(movieID);
		if(matchCharacter.isPresent() && matchPeliculaOSerie.isPresent()){
			Personaje personaje = personajeRepository.findById(characterID).get();
			PeliculaOSerie peliculaOSerie = peliculaOSerieService.buscarPorId(movieID).get();
			if(personaje.getPeliculasOSeries() != null) {
				List<PeliculaOSerie> peliculas = personaje.getPeliculasOSeries();
				peliculas.add(peliculaOSerie);
				personaje.setPeliculasOSeries(peliculas);
				personajeRepository.save(personaje);	
			}
		} else {
			throw new ErrorService("No se ha podido añadir la película al personaje.");
		}
		
	}

	
	//modify character
	@Transactional
	public void modificarPersonaje(String idPersonaje, String nombre, Integer edad, Integer peso, String historia) throws ErrorService{
		validar(nombre);
		
		Optional<Personaje> respuesta = personajeRepository.findById(idPersonaje);	
		if(respuesta.isPresent()) {
			Personaje personaje = respuesta.get();
			personaje.setNombre(nombre);
			personaje.setEdad(edad);
			personaje.setPeso(peso);
			personaje.setHistoria(historia);
			
			/*String idImagen = null;
			if(personaje.getImagen() != null) {
				idImagen = personaje.getImagen().getId();
			}
			Imagen img = imagenService.actualizar(idImagen, imagen);
			personaje.setImagen(img);
			
			List<PeliculaOSerie> peliculasOSeries = peliculaOSerieService.verificarSiExisteOCrear(pelicula);
			peliculasOSeries.addAll(personaje.getPeliculasOSeries());
			personaje.setPeliculasOSeries(peliculasOSeries);*/
			
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
			System.out.print("aaaaa");
			Personaje personaje = personajeRepository.findById(idPersonaje).get();
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

	public List<Personaje> buscarPersonajePorEdad(Integer edad) throws ErrorService {
		// TODO Auto-generated method stub
		if(edad==null || edad < 0) {
			throw new ErrorService("No hay personajes con esta edad.");
		}
		List<Personaje> matches = personajeRepository.buscarPorEdad(edad);
		if(matches.size() == 0) {
			throw new ErrorService("No hay personajes con esta edad.");			
		}
		return matches;
	}
	
}
