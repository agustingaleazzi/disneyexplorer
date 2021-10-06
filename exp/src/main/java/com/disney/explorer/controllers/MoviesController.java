package com.disney.explorer.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.disney.explorer.entities.PeliculaOSerie;
import com.disney.explorer.services.PeliculaOSerieService;

@Controller
@RequestMapping("/moviesPanel")
public class MoviesController {

	@Autowired
	private PeliculaOSerieService peliculaService;
	
	@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER')")
	@GetMapping("")
	public String charactersPanel(@RequestParam(required=false) String error, ModelMap model) {
		List<PeliculaOSerie> listaPeliculas = peliculaService.buscarTodos();
		model.addAttribute("listaPeliculas", listaPeliculas);
		return "moviesPanel.html";
	}
}