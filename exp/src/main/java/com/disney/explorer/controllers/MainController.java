package com.disney.explorer.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class MainController {
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER')")
	@GetMapping("/inicio")
	public String inicio() {
		return "inicio.html";
	}
	

	@PreAuthorize("hasAnyRole('ROLE_REGISTERED_USER')")
	@GetMapping("/dashboard")
	public String dashboard() {
		return "dashboard.html";
	}
	
}
