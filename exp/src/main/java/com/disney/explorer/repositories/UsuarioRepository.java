package com.disney.explorer.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.disney.explorer.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

	@Query("SELECT a from Usuario a WHERE a.mail LIKE :mail")
	public Usuario buscarPorMail(@Param("mail") String mail);
	
}
