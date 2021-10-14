package com.disney.explorer.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.disney.explorer.entities.PeliculaOSerie;
import com.disney.explorer.entities.Personaje;
import com.disney.explorer.errors.ErrorService;
import com.disney.explorer.services.PeliculaOSerieService;
import com.disney.explorer.services.PersonajeService;

@Controller
@RequestMapping("/characters")
public class CharactersController {
	
	@Autowired
	private PersonajeService personajeService;
	@Autowired
	private PeliculaOSerieService peliculasOSeriesService;
	
	

	@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER')")
	@GetMapping("")
	public String simpleListCharacters(@RequestParam(required=false) String error, ModelMap model) {
		List<Personaje> listaPersonajes = personajeService.buscarTodos();
		model.addAttribute("listaPersonajes", listaPersonajes);
		return "characters.html";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER')")
	@GetMapping("/createCharacter")
	public ModelAndView createCharacter(@RequestParam(required=false) String error, ModelMap model) {
		return new ModelAndView("createCharacter.html");
	}
	
	@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER')")
	@GetMapping("/listCharacter")
	public String listCharacter(@RequestParam(required=false) String error, ModelMap model) {
		List<Personaje> listaPersonajes = personajeService.buscarTodos();
		model.addAttribute("listaPersonajes", listaPersonajes);
		return "listCharacter.html";
	}
	
	
	
	@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER')")
	@PostMapping("/addMovieToCharacter")
	public RedirectView addMovieToCharacter(@RequestParam(required=false) String error, @RequestParam String idPersonaje, @RequestParam String idPelicula, ModelMap model) {
		try {				
			personajeService.addMovieToCharacter(idPersonaje, idPelicula);

		} catch (ErrorService e) {
			model.put("error", e.getMessage());
			e.printStackTrace();
		}
	model.addAttribute("mensaje", "película añadida correctamente");
	return new RedirectView("/characters/editCharacter");
}
	
	
	@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER')")
	@GetMapping("/editCharacter")
	public ModelAndView editCharacter(@RequestParam(required=false) String error) {
		return new ModelAndView("editCharacter.html");
	}
	
	@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER')")
	@GetMapping("/editCharacter/{ID}")
	public ModelAndView editCharacter(@PathVariable String ID, ModelMap model) {
		try {
			Personaje personajeAEditar = personajeService.buscarPorId(ID).get();
			List<PeliculaOSerie> peliculasOSeries = peliculasOSeriesService.findAll();

			model.addAttribute("peliculasOSeries", peliculasOSeries);
			model.addAttribute("personaje", personajeAEditar);
		} catch (ErrorService e) {
			e.printStackTrace();		
			}

		return new ModelAndView("editCharacterPanel.html");
	}
	
	@PostMapping("/editCharacter")
	public RedirectView editarPersonaje(ModelMap modelo, @RequestParam String idPersonaje, @RequestParam String nombre, @RequestParam Integer edad, @RequestParam Integer peso, @RequestParam String historia) {
		
		try {			
			personajeService.modificarPersonaje(idPersonaje, nombre, edad, peso, historia);
		} catch (ErrorService e) {
			modelo.put("error", e.getMessage());
			e.printStackTrace();
		}
		
		modelo.put("mensaje", "Personaje modificado satisfactoriamente.");
		
		return new RedirectView("/characters");
	}
	

	@PostMapping("/agregarPersonaje")
	public RedirectView agregarPersonaje(ModelMap modelo, @RequestParam String nombre, @RequestParam Integer edad, @RequestParam Integer peso, @RequestParam String historia, @RequestParam MultipartFile imagen, @RequestParam String pelicula) {
		try {
			personajeService.agregarPersonaje(nombre, edad, peso, historia, imagen, pelicula);
		} catch (ErrorService e) {
			modelo.put("error", e.getMessage());
			e.printStackTrace();
		}
		
		modelo.put("mensaje", "Personaje creado satisfactoriamente.");
		
		return new RedirectView("/characters");
	}
	
	/*@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER')")
	@GetMapping("/buscar")
	public ModelAndView buscar(@RequestParam(required=false) String error, @RequestParam("nombre") String nombre, ModelMap model) {
		if(nombre != null) {
			try {
				List<Personaje> personajes = personajeService.buscarPersonajePorNombre(nombre);

				model.addAttribute("personajes", personajes);
			} catch (ErrorService e) {
				e.printStackTrace();
			}
		}
		return new ModelAndView("listCharacter.html");
	}*/
	
	@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER')")
	@GetMapping("/buscarParaEditar")
	public ModelAndView buscarParaEditar(@RequestParam(required=false) String error,@RequestParam String nombre, ModelMap model) {
		if(nombre != null) {
			try {
				List<Personaje> personajes = personajeService.buscarPersonajePorNombre(nombre);
				model.addAttribute("personajes", personajes);
			} catch (ErrorService e) {
				e.printStackTrace();
			}
		}
		return new ModelAndView("editCharacter.html");
	}

	@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER')")
	@GetMapping("/buscar")
	public ModelAndView buscarPersonajePorEdad(@RequestParam(required=false) String error, @RequestParam(required = false) Integer edad, @RequestParam(required = false) String nombre, ModelMap model) {
		if(nombre != null) {
			try {
				List<Personaje> personajes = personajeService.buscarPersonajePorNombre(nombre);
				model.addAttribute("personajesPorNombre", personajes);
			} catch (ErrorService e) {
				model.put("mensaje", e.toString());
				e.printStackTrace();
			}
		}
		if(edad != null) {
			try {
				List<Personaje> personajes = personajeService.buscarPersonajePorEdad(edad);
				model.addAttribute("personajesPorEdad", personajes);
			} catch (ErrorService e) {
				model.put("mensaje", e.toString());
				e.printStackTrace();
			}
		}
		return new ModelAndView("listCharacter.html");
	}
	
	
	@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER')")
	@GetMapping("/delete/{ID}")
	public RedirectView deleteCharacter(@PathVariable String ID, ModelMap model) {
		try {
			personajeService.eliminarPersonaje(ID);

			model.addAttribute("mensaje", "personaje eliminado correctamente");
		} catch (ErrorService e) {
			e.printStackTrace();		
			}
			
			return new RedirectView("/characters");

	}
	/*@GetMapping("/buscarPersonaje")
	public String buscarPersonaje(@RequestParam(required=false) String match, ModelMap model) {	
	if(match != null) {
		model.put("match", match);
	}
	return ""*/
}
