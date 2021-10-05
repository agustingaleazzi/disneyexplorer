package com.disney.explorer.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.disney.explorer.entities.Genero;
import com.disney.explorer.entities.Imagen;
import com.disney.explorer.entities.PeliculaOSerie;
import com.disney.explorer.errors.ErrorService;
import com.disney.explorer.repositories.GeneroRepository;

public class GeneroService {
	
	@Autowired
	private GeneroRepository generoRepository;

	@Autowired
	private ImagenService imagenService;
		
	@Transactional
	public void agregarGenero(String idUsuario, String nombre, MultipartFile imagen, List<PeliculaOSerie> peliculasOSeries) throws ErrorService {
		validar(nombre);
			
		Genero genero = new Genero();
		genero.setNombre(nombre);
		Imagen img = imagenService.guardar(imagen);
		genero.setImagen(img);
		genero.setPeliculasOSeries(peliculasOSeries);
			
		generoRepository.save(genero);				
	}
		
	public List<Genero> buscarGeneroPorNombre(String nombre) {
		return generoRepository.buscarPorNombre(nombre);
	}
		
	@Transactional
	public void modificarGenero(String idUsuario, String idGenero, String nombre, MultipartFile imagen, List<PeliculaOSerie> peliculasOSeries) throws ErrorService{
		validar(nombre);
		
		Optional<Genero> respuesta = generoRepository.findById(idGenero);	
		if(respuesta.isPresent()) {
			Genero genero = respuesta.get();
			genero.setNombre(nombre);
			genero.setPeliculasOSeries(peliculasOSeries);
			
			String idImagen = null;
			if(genero.getImagen() != null) {
				idImagen = genero.getImagen().getId();
			}
			Imagen img = imagenService.actualizar(idImagen, imagen);
			genero.setImagen(img);
				
			generoRepository.save(genero);	
		} else {
			throw new ErrorService("El genero a modificar no ha sido encontrado.");
		}	
	}
		
	@Transactional
	public void eliminarGenero(String idUsuario, String idGenero) throws ErrorService{
		Optional<Genero> respuesta = generoRepository.findById(idGenero);	
		if(respuesta.isPresent()) {
			Genero genero = respuesta.get();
			generoRepository.delete(genero);
		} else {
			throw new ErrorService("El genero a eliminar no ha sido encontrado.");
		}
	}
			
		
	public void validar(String nombre) throws ErrorService{
		if(nombre==null || nombre.isEmpty()) {
			throw new ErrorService("El nombre del genero no puede estar vacío.");
		}
	}
		
}
