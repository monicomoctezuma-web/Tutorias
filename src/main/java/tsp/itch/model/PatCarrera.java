package tsp.itch.model;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pat_carrera")
public class PatCarrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pat_carrera")
    private Integer idPatCarrera;

    @ManyToOne
    @JoinColumn(name = "id_pat_general")
    private Pat pat;

    @ManyToOne
    @JoinColumn(name = "id_carrera")
    private Carrera carrera;

    @ManyToOne
    @JoinColumn(name = "id_semestre")
    private Semestre semestre;

    @OneToMany(mappedBy = "patCarrera")
    private List<Actividad> actividades;

    @Column(name = "estatus")
    private Integer estatus;

	public Integer getIdPatCarrera() {
		return idPatCarrera;
	}

	public void setIdPatCarrera(Integer idPatCarrera) {
		this.idPatCarrera = idPatCarrera;
	}

	public Pat getPat() {
		return pat;
	}

	public void setPat(Pat pat) {
		this.pat = pat;
	}

	public Carrera getCarrera() {
		return carrera;
	}

	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}

	public Semestre getSemestre() {
		return semestre;
	}

	public void setSemestre(Semestre semestre) {
		this.semestre = semestre;
	}

	public List<Actividad> getActividades() {
		return actividades;
	}

	public void setActividades(List<Actividad> actividades) {
		this.actividades = actividades;
	}

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	@Override
	public String toString() {
		return "PatCarrera [idPatCarrera=" + idPatCarrera + ", pat=" + pat + ", carrera=" + carrera + ", semestre="
				+ semestre + ", actividades=" + actividades + ", estatus=" + estatus + "]";
	}

}
