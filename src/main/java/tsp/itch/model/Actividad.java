package tsp.itch.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "actividad")
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad")
    private Integer idActividad;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "semana")
    private String semana;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_pat_carrera")
    private PatCarrera patCarrera;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_pat")  // NUEVO
    private Pat pat;

    @Column(name = "estatus")
    private Integer estatus;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha")
    private LocalDate fecha;

	public Integer getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(Integer idActividad) {
		this.idActividad = idActividad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getSemana() {
		return semana;
	}

	public void setSemana(String semana) {
		this.semana = semana;
	}

	public PatCarrera getPatCarrera() {
		return patCarrera;
	}

	public void setPatCarrera(PatCarrera patCarrera) {
		this.patCarrera = patCarrera;
	}

	
	public Pat getPat() {
		return pat;
	}

	public void setPat(Pat pat) {
		this.pat = pat;
	}

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}
	

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Actividad [idActividad=" + idActividad + ", nombre=" + nombre + ", descripcion=" + descripcion
				+ ", semana=" + semana + ", patCarrera=" + patCarrera + ", pat=" + pat + ", estatus=" + estatus
				+ ", fecha=" + fecha + "]";
	}

	
	
    
}