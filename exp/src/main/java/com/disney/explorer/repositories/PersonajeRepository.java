package com.disney.explorer.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.disney.explorer.entities.Personaje;

public interface PersonajeRepository extends JpaRepository<Personaje, String>{
	
	@Query("select e from Personaje e where e.nombre like :nombre")
	public Personaje buscarPorNombre(@Param("nombre") String nombre);

}
