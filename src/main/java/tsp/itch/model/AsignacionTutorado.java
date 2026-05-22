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
@Table(name = "asignacion_tutorado")
public class AsignacionTutorado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id  ;

    @ManyToOne
    @JoinColumn(name = "id_asignacion")
    private Asignacion asignacion;

    @ManyToOne
    @JoinColumn(name = "id_tutorado")
    private Tutorado tutorado;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Asignacion getAsignacion() {
		return asignacion;
	}

	public void setAsignacion(Asignacion asignacion) {
		this.asignacion = asignacion;
	}

	public Tutorado getTutorado() {
		return tutorado;
	}

	public void setTutorado(Tutorado tutorado) {
		this.tutorado = tutorado;
	}

	@Override
	public String toString() {
		return "AsignacionTutorado [id=" + id + ", asignacion=" + asignacion + ", tutorado=" + tutorado + "]";
	}
    
}