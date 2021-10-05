package com.disney.explorer.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.disney.explorer.entities.Usuario;
import com.disney.explorer.errors.ErrorService;
import com.disney.explorer.repositories.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	/*
	@Autowired
	private NotificacionService notificacionService;
	*/
	

	//register user

	@Transactional
	public void registrarUsuario(String nombre, String mail, String clave, String dni) throws ErrorService {
		
		validar(nombre, mail, clave);
		
		Usuario usuario = new Usuario();
		usuario.setNombre(nombre);
		usuario.setMail(mail);
		
		//encrypting user password
		String encript = new BCryptPasswordEncoder().encode(clave);		
		usuario.setClave(encript);
		
		usuario.setDni(dni);
		usuario.setActivo(true);
		
		usuarioRepository.save(usuario);		
		
		/*notificacionService.enviar("¡Bienvenidx a Disney Explorer!", "Disney Explorer", usuario.geteMail());*/
	}

	//modify user
	@Transactional
	public void modificarUsuario(String id, String nombre, String mail, String clave) throws ErrorService {	

		validar(nombre, mail, clave);
		
		Optional<Usuario> respuesta = usuarioRepository.findById(id);	
		if(respuesta.isPresent()) {
			Usuario usuario = respuesta.get();
			usuario.setNombre(nombre);
			usuario.setMail(mail);
			usuario.setClave(clave);
			
			usuarioRepository.save(usuario);
		} else {
			throw new ErrorService("No se encontró el usuario solicitado");
		}		
	}
	
	//disable user
	@Transactional
	public void deshabilitarUsuario(String id) throws ErrorService {	
		
		Optional<Usuario> respuesta = usuarioRepository.findById(id);	
		if(respuesta.isPresent()) {
			Usuario usuario = respuesta.get();
			usuario.setActivo(false);
			
			usuarioRepository.save(usuario);
		} else {
			throw new ErrorService("No se encontró el usuario solicitado");
		}
		
	}
	
	//validating input data
	private void validar(String nombre, String mail, String clave) throws ErrorService{
		if(nombre==null || nombre.isEmpty()) {
			throw new ErrorService("Error en el nombre: esta vacio");
		}
		
		if(mail==null || mail.isEmpty()) {
			throw new ErrorService("Error en el mail: esta vacio");
		}
		
		if(clave.isEmpty() || clave.length()<6) {
			throw new ErrorService("Error la clave: esta vacia");
		}
	}

	//authentication function with security
	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.buscarPorMail(mail);
		if(usuario != null) {
			List<GrantedAuthority> permisos = new ArrayList<>();
			
			GrantedAuthority permiso1 = new SimpleGrantedAuthority("ROLE_REGISTERED_USER");
			permisos.add(permiso1);
			
			ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session = attr.getRequest().getSession(true);
			session.setAttribute("usuariosession", usuario);
						
			User user = new User(usuario.getMail(), usuario.getClave(), permisos);
			
			return user;			
		} else {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		
	}

}
