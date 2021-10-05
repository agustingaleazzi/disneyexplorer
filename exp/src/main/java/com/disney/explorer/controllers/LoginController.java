package com.disney.explorer.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.disney.explorer.errors.ErrorService;
import com.disney.explorer.services.UsuarioService;

@Controller
@RequestMapping("/auth")
public class LoginController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/login")
	public String login() {
		return "login.html";
	}
	
	@PostMapping("")
	public String ingresarUsuario(Model model, @RequestParam String nombre, String eMail, String clave) throws ErrorService{
		
		/*usuarioService.ingresarUsuario(nombre, eMail, clave);*/
		
		return "redirect:/";
	}
	
}
