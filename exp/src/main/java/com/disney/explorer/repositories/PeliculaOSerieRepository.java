
package com.disney.explorer.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.disney.explorer.entities.PeliculaOSerie;
import com.disney.explorer.entities.Personaje;

public interface PeliculaOSerieRepository extends JpaRepository<PeliculaOSerie, String>{

	@Query("select e from PeliculaOSerie e where e.titulo like :titulo")
	public List<Personaje> buscarPorTitulo(@Param("titulo") String titulo);
	
}
