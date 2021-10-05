package com.disney.explorer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.disney.explorer.services.UsuarioService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SegConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	@Qualifier("usuarioService")
	public UsuarioService usuarioService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(usuarioService).
		passwordEncoder(new BCryptPasswordEncoder());
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/css/*", "/js/*", "/img/*").permitAll()
				.and().formLogin()
					.loginPage("/auth/login")
						.loginProcessingUrl("/logincheck")
						.usernameParameter("mail")
						.passwordParameter("clave")
						.defaultSuccessUrl("/inicio")
						.permitAll()
				.and().logout()
					.logoutUrl("/logout")
					.logoutSuccessUrl("/")
					.permitAll()
				.and().csrf()
					.disable();
	}
	

}