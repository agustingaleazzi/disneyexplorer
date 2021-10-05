package com.disney.explorer.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.disney.explorer.entities.Genero;

public interface GeneroRepository extends JpaRepository<Genero, String>{

	@Query("select e from Genero e where e.nombre like :nombre")
	public List<Genero> buscarPorNombre(@Param("nombre") String nombre);
}
