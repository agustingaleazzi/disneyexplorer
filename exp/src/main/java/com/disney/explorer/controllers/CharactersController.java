package com.disney.explorer.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.disney.explorer.entities.Personaje;
import com.disney.explorer.errors.ErrorService;
import com.disney.explorer.services.PersonajeService;

@Controller
@RequestMapping("/charactersPanel")
public class CharactersController {
	
	@Autowired
	private PersonajeService personajeService;
	

	@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER')")
	@GetMapping("")
	public String charactersPanel(@RequestParam(required=false) String error, ModelMap model) {
		List<Personaje> listaPersonajes = personajeService.buscarTodos();
		model.addAttribute("listaPersonajes", listaPersonajes);
		return "charactersPanel.html";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER')")
	@GetMapping("/createCharacter")
	public ModelAndView createCharacter(@RequestParam(required=false) String error, ModelMap model) {
		return new ModelAndView("createCharacter.html");
	}
	
	@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER')")
	@GetMapping("/editCharacter")
	public ModelAndView editCharacter(@RequestParam(required=false) String error, @RequestParam(required=false) String nombre, ModelMap model) {
		Personaje listaPersonajes = personajeService.buscarPersonajePorNombre(nombre);
		model.addAttribute("listaPersonajes", listaPersonajes);
		return new ModelAndView("editCharacter.html");
	}
	

	@PostMapping("/agregarPersonaje")
	public RedirectView agregarPersonaje(ModelMap modelo, @RequestParam String nombre, @RequestParam Integer edad, @RequestParam Integer peso, @RequestParam String historia, @RequestParam MultipartFile imagen, @RequestParam String pelicula) {

		try {
			personajeService.agregarPersonaje(nombre, peso, historia, imagen, pelicula);
		} catch (ErrorService e) {
			modelo.put("error", e.getMessage());
			e.printStackTrace();
		}
		
		modelo.put("mensaje", "Personaje creado satisfactoriamente.");
		
		return new RedirectView("/charactersPanel");
	}
	
	@PostMapping("/editarPersonaje")
	public RedirectView editarPersonaje(ModelMap modelo, @RequestParam String nombre, @RequestParam Integer edad, @RequestParam Integer peso, @RequestParam String historia, @RequestParam MultipartFile imagen, @RequestParam String pelicula) {
		
		try {
			Personaje respuesta = personajeService.buscarPersonajePorNombre(nombre);
			
			personajeService.modificarPersonaje(respuesta.getId(), nombre, peso, historia, imagen, pelicula);
		} catch (ErrorService e) {
			modelo.put("error", e.getMessage());
			e.printStackTrace();
		}
		
		modelo.put("mensaje", "Personaje modificado satisfactoriamente.");
		
		return new RedirectView("/charactersPanel");
	}
	
	@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER')")
	@GetMapping("/buscarPersonaje")
	public ModelAndView buscarPersonaje(@RequestParam(required=false) String error, @RequestParam(required=false) String nombre, ModelMap model) {
		if(nombre != null) {
			Personaje match = personajeService.buscarPersonajePorNombre(nombre);
			model.addAttribute("match", match);			
		}

		return new ModelAndView("editCharacter.html");
	}
	
	/*@GetMapping("/buscarPersonaje")
	public String buscarPersonaje(@RequestParam(required=false) String match, ModelMap model) {	
	if(match != null) {
		model.put("match", match);
	}
	return ""*/
}
