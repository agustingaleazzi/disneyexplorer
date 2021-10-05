package com.disney.explorer.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Genero {
	
	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid", strategy="uuid2")
	private String id;
	private String nombre;
	
	@OneToOne
	private Imagen imagen;	

	@OneToMany
	private List<PeliculaOSerie> peliculasOSeries; 
	
	public Genero() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}

	public List<PeliculaOSerie> getPeliculasOSeries() {
		return peliculasOSeries;
	}

	public void setPeliculasOSeries(List<PeliculaOSerie> peliculasOSeries) {
		this.peliculasOSeries = peliculasOSeries;
	}
	
	

}
