package com.disney.explorer.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import com.disney.explorer.entities.Imagen;
import com.disney.explorer.repositories.ImagenRepository;

@Controller
@RequestMapping("/Imagen")
public class ImagenController {
	@Autowired
	private ImagenRepository imagenRepository;
	
	@GetMapping("/load/{id}")
	public ResponseEntity<byte[]> cargarFoto(@PathVariable String id) {
		Imagen imagen = imagenRepository.getOne(id);
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		return new ResponseEntity<>(imagen.getContenido(), headers, HttpStatus.OK);
	}

}
