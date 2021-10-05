package com.disney.explorer.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/auth")
public class LoginController {
	
	@GetMapping("/login")
	public String login(@RequestParam(required=false) String error, @RequestParam(required=false) String logout, ModelMap model) {
		if(error != null) {
			model.put("error", "Nombre de usuario o clave incorrectos");
		}
		
		if(logout != null) {
			model.put("logout", "Usuario deslogueado correctamente");
		}
		return "login.html";
	}
			
}
