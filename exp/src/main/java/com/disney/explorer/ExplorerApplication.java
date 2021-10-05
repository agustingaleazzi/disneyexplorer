package com.disney.explorer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.disney.explorer.services.UsuarioService;

@SpringBootApplication
public class ExplorerApplication {	

	public static void main(String[] args) {
		SpringApplication.run(ExplorerApplication.class, args);
	}

}
