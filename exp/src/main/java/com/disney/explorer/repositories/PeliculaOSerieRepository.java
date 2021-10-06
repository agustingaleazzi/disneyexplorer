
package com.disney.explorer.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.disney.explorer.entities.PeliculaOSerie;

public interface PeliculaOSerieRepository extends JpaRepository<PeliculaOSerie, String>{

	@Query("select e from PeliculaOSerie e where e.titulo like :titulo")
	public Optional<PeliculaOSerie> buscarPorTitulo(@Param("titulo") String titulo);
	
}
