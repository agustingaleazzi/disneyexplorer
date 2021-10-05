package com.disney.explorer.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.disney.explorer.entities.Usuario;
import com.disney.explorer.errors.ErrorService;
import com.disney.explorer.repositories.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private NotificacionService notificacionService;
	

	//register user

	@Transactional
	public void registrarUsuario(String nombre, String eMail, String clave, String dni) throws ErrorService {
		
		validar(nombre, eMail, clave);
		
		Usuario usuario = new Usuario();
		usuario.setNombre(nombre);
		usuario.seteMail(eMail);
		
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
	public void modificarUsuario(String id, String nombre, String eMail, String clave) throws ErrorService {	

		validar(nombre, eMail, clave);
		
		Optional<Usuario> respuesta = usuarioRepository.findById(id);	
		if(respuesta.isPresent()) {
			Usuario usuario = respuesta.get();
			usuario.setNombre(nombre);
			usuario.seteMail(eMail);
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
	private void validar(String nombre, String eMail, String clave) throws ErrorService{
		if(nombre==null || nombre.isEmpty()) {
			throw new ErrorService("Error en el nombre: esta vacio");
		}
		
		if(eMail==null || eMail.isEmpty()) {
			throw new ErrorService("Error en el eMail: esta vacio");
		}
		
		if(clave.isEmpty() || clave.length()<6) {
			throw new ErrorService("Error la clave: esta vacia");
		}
	}

	//authentication function with security
	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.buscarPorMail(mail);
		if(usuario !=null) {
			List<GrantedAuthority> permisos = new ArrayList<>();
			
			GrantedAuthority p1 = new SimpleGrantedAuthority("MODULO_GENERO");
			permisos.add(p1);
			GrantedAuthority p2 = new SimpleGrantedAuthority("MODULO_IMAGEN");
			permisos.add(p2);
			GrantedAuthority p3 = new SimpleGrantedAuthority("MODULO_PELICULAOSERIE");
			permisos.add(p3);
			GrantedAuthority p4 = new SimpleGrantedAuthority("MODULO_PERSONAJE");
			permisos.add(p4);
			
			
			User user = new User(mail, usuario.getClave(), permisos);
			
			return user;
		} else {
			return null;
		}
		
	}

}
