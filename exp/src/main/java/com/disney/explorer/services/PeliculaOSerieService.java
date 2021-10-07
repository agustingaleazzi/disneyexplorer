package com.disney.explorer.services;

import java.util.ArrayList;
import java.util.Date;
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
import com.disney.explorer.repositories.PeliculaOSerieRepository;

@Service
public class PeliculaOSerieService {
	
	@Autowired
	private PeliculaOSerieRepository peliculaOSerieRepository;
	@Autowired
	private ImagenService imagenService;
	
	//add movieorseries
	@Transactional
	public void agregarPeliculaOSerie(String titulo, Date fecha, Integer clasificacion, MultipartFile imagen, List<Personaje> personajes) throws ErrorService {
		validar(titulo, clasificacion);
		
		PeliculaOSerie peliculaOSerie = new PeliculaOSerie();
		peliculaOSerie.setTitulo(titulo);
		peliculaOSerie.setFecha(fecha);
		peliculaOSerie.setClasificacion(clasificacion);
		Imagen img = imagenService.guardar(imagen);
		peliculaOSerie.setImagen(img);
		peliculaOSerie.setPersonajes(personajes);
		
		peliculaOSerieRepository.save(peliculaOSerie);				
	}
	
	@Transactional
	public Optional<PeliculaOSerie> buscarPeliculaOSeriePorTitulo(String titulo) {
		return peliculaOSerieRepository.buscarPorTitulo(titulo);
	}
	
	@Transactional
	public List<PeliculaOSerie> verificarSiExisteOCrear(String titulo){
		PeliculaOSerie peliculaOSerie = new PeliculaOSerie();
		Optional<PeliculaOSerie> respuesta = peliculaOSerieRepository.buscarPorTitulo(titulo);
		if(respuesta.isPresent()) {			
			peliculaOSerie = respuesta.get();
		} else {
			peliculaOSerie.setTitulo(titulo);
		}
			List<PeliculaOSerie> lista =new ArrayList<>();
			lista.add(peliculaOSerieRepository.save(peliculaOSerie));
			return lista;

	}
	
	@Transactional
	public Optional<PeliculaOSerie> buscarPorId(String id) {
		return peliculaOSerieRepository.findById(id);
	}
	
	//modify movieorseries
	@Transactional
	public void modificarPeliculaOSerie(String idPeliculaOSerie, String titulo, Date fecha, Integer clasificacion, MultipartFile imagen, List<Personaje> personajes) throws ErrorService{
		validar(titulo, clasificacion);
		
		Optional<PeliculaOSerie> respuesta = peliculaOSerieRepository.findById(idPeliculaOSerie);	
		if(respuesta.isPresent()) {
			PeliculaOSerie peliculaOSerie = respuesta.get();
			peliculaOSerie.setTitulo(titulo);
			peliculaOSerie.setFecha(fecha);
			peliculaOSerie.setClasificacion(clasificacion);
			
			String idImagen = null;
			if(peliculaOSerie.getImagen() != null) {
				idImagen = peliculaOSerie.getImagen().getId();
			}
			Imagen img = imagenService.actualizar(idImagen, imagen);
			peliculaOSerie.setImagen(img);
			
			peliculaOSerieRepository.save(peliculaOSerie);	
		} else {
			throw new ErrorService("La pelicula a modificar no ha sido encontrado.");
		}	
	}
	
	//delete movieorseries
	@Transactional
	public void eliminarPeliculaOSerie(String idPelicula) throws ErrorService{
		Optional<PeliculaOSerie> respuesta = peliculaOSerieRepository.findById(idPelicula);	
		if(respuesta.isPresent()) {
			PeliculaOSerie peliculaOSerie = respuesta.get();
			peliculaOSerieRepository.delete(peliculaOSerie);
		} else {
			throw new ErrorService("La pelicula o serie a eliminar no ha sido encontrada.");
		}
	}	
	
	//search all characters
	@Transactional
	public List<PeliculaOSerie> findAll() {
		return peliculaOSerieRepository.findAll();
	}
		
	//validating input data
	public void validar(String titulo, Integer clasificacion) throws ErrorService{
		if(titulo==null || titulo.isEmpty()) {
			throw new ErrorService("El titulo de la pelicula o serie no puede estar vacío.");
		}
		if(clasificacion<0 || clasificacion>5) {
			throw new ErrorService("La clasificación no puede ser menor a 0 o mayor a 5");
		}
	}

	
	
}
