package com.disney.explorer.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.disney.explorer.entities.Imagen;
import com.disney.explorer.errors.ErrorService;
import com.disney.explorer.repositories.ImagenRepository;

@Service
public class ImagenService {
	
	@Autowired
	private ImagenRepository imagenRepository;
	
	@Transactional
	public Imagen guardar(MultipartFile archivo) throws ErrorService{
		if(archivo != null) {
			try{
				Imagen imagen = new Imagen();
				imagen.setMime(archivo.getContentType());
				imagen.setNombre(archivo.getName());
				imagen.setContenido(archivo.getBytes());
			
				return imagenRepository.save(imagen);
			} catch(Exception e) {
				System.out.print(e.getMessage());
			}			
		}
		return null;		
	}
	

	@Transactional
	public Imagen actualizar(String idImagen, MultipartFile archivo) throws ErrorService{
		if(archivo != null) {
			try{
				Imagen imagen = new Imagen();
				if(idImagen != null) {
					Optional<Imagen> respuesta = imagenRepository.findById(idImagen);
					if(respuesta.isPresent()) {
						imagen = respuesta.get();
					}					
				}
				imagen.setMime(archivo.getContentType());
				imagen.setNombre(archivo.getName());
				imagen.setContenido(archivo.getBytes());
			
				return imagenRepository.save(imagen);
			} catch(Exception e) {
				System.out.print(e.getMessage());
			}			
		}
		return null;		
	}
}
