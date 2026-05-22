package tsp.itch.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "necesidad")
public class Necesidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_necesidad")
    private Integer idNecesidad;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "id_asignacion")
    private Asignacion asignacion;

	public Integer getIdNecesidad() {
		return idNecesidad;
	}

	public void setIdNecesidad(Integer idNecesidad) {
		this.idNecesidad = idNecesidad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Asignacion getAsignacion() {
		return asignacion;
	}

	public void setAsignacion(Asignacion asignacion) {
		this.asignacion = asignacion;
	}

	@Override
	public String toString() {
		return "Necesidad [idNecesidad=" + idNecesidad + ", descripcion=" + descripcion + ", asignacion=" + asignacion
				+ "]";
	}
    
    

}
