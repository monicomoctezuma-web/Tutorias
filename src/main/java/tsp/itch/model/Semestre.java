package tsp.itch.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "semestre")
public class Semestre {
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_semestre")
	private Integer idSemestre;
    
    @Column(name = "nombre") 
	private String nombre;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_inicio")
	private Date fechaInicio;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_fin")
	private Date fechaFin;
    
    @Column(name = "activo")
	private Integer activo;
	
	
	
	public Integer getIdSemestre() {
		return idSemestre;
	}
	public void setIdSemestre(Integer idSemestre) {
		this.idSemestre = idSemestre;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public Integer getActivo() {
		return activo;
	}
	public void setActivo(Integer activo) {
		this.activo = activo;
	}
	@Override
	public String toString() {
		return "Semestre [idSemestre=" + idSemestre + ", nombre=" + nombre + ", fechaInicio=" + fechaInicio
				+ ", fechaFin=" + fechaFin + ", activo=" + activo + "]";
	}

	
	

}
