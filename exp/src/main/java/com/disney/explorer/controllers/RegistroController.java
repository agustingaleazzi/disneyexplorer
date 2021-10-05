package com.disney.explorer.controllers;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.disney.explorer.errors.ErrorService;
import com.disney.explorer.services.UsuarioService;

@Controller
@RequestMapping("/auth")
public class RegistroController {
	
	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/register")
	public String register() {
		return "registro";
	}
	
	@PostMapping("/registrarUsuario")
	public String registrarUsuario(ModelMap modelo, @RequestParam String nombre, @RequestParam String eMail, @RequestParam String clave, @RequestParam String dni){		
			try {
				usuarioService.registrarUsuario(nombre, eMail, clave, dni);
			} catch (ErrorService e) {
				modelo.put("error", e.getMessage());
				
				e.printStackTrace();

				return "registro";
			}
		modelo.put("mensaje", "Sesión iniciada");
		return "exito";
	}
	
}
