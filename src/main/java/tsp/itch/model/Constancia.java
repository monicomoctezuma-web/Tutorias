package tsp.itch.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "constancia")
public class Constancia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_constancia")
    private Integer idConstancia;

    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;

    @ManyToOne
    @JoinColumn(name = "id_tutorado")
    private Tutorado tutorado;

    @ManyToOne
    @JoinColumn(name = "id_asignacion")
    private Asignacion asignacion;

	public Integer getIdConstancia() {
		return idConstancia;
	}

	public void setIdConstancia(Integer idConstancia) {
		this.idConstancia = idConstancia;
	}

	public LocalDate getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(LocalDate fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Tutorado getTutorado() {
		return tutorado;
	}

	public void setTutorado(Tutorado tutorado) {
		this.tutorado = tutorado;
	}

	public Asignacion getAsignacion() {
		return asignacion;
	}

	public void setAsignacion(Asignacion asignacion) {
		this.asignacion = asignacion;
	}

	@Override
	public String toString() {
		return "Constancia [idConstancia=" + idConstancia + ", fechaEmision=" + fechaEmision + ", tutorado=" + tutorado
				+ ", asignacion=" + asignacion + "]";
	}
    
    
	

}
