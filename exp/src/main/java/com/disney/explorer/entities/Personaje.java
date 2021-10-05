package com.disney.explorer.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Personaje {

	@Id
	@GeneratedValue(generator="uuid")
	@GenericGenerator(name="uuid", strategy="uuid2")
	private String id;
	private String nombre;
	private Integer peso;
	private String historia;
	
	@OneToOne
	private Imagen imagen;	
	
	@ManyToMany
	private List<PeliculaOSerie> peliculasOSeries;
	
	public Personaje() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Imagen getImagen() {
		return imagen;
	}

	public void setImagen(Imagen imagen) {
		this.imagen = imagen;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	public String getHistoria() {
		return historia;
	}

	public void setHistoria(String historia) {
		this.historia = historia;
	}

	public List<PeliculaOSerie> getPeliculasOSeries() {
		return peliculasOSeries;
	}

	public void setPeliculasOSeries(List<PeliculaOSerie> peliculasOSeries) {
		this.peliculasOSeries = peliculasOSeries;
	}
	
	
	
}
