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
@Table(name = "asignacion")
public class Asignacion {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asignacion")
    private Integer idAsignacion;

    @Column(name = "aula")
    private String aula;

    @Column(name = "horario")
    private String horario;

    @Column(name = "estatus")
    private Integer estatus;

    @ManyToOne
    @JoinColumn(name = "id_tutor")
    private Tutor tutor;

    @ManyToOne
    @JoinColumn(name = "id_carrera")
    private Carrera carrera;

    @ManyToOne
    @JoinColumn(name = "id_semestre")
    private Semestre semestre;

    @OneToMany(mappedBy = "asignacion")
    private List<AsignacionTutorado> tutorados;

	public Integer getIdAsignacion() {
		return idAsignacion;
	}

	public void setIdAsignacion(Integer idAsignacion) {
		this.idAsignacion = idAsignacion;
	}

	public String getAula() {
		return aula;
	}

	public void setAula(String aula) {
		this.aula = aula;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	public Tutor getTutor() {
		return tutor;
	}

	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
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

	public List<AsignacionTutorado> getTutorados() {
		return tutorados;
	}

	public void setTutorados(List<AsignacionTutorado> tutorados) {
		this.tutorados = tutorados;
	}

	@Override
	public String toString() {
		return "Asignacion [idAsignacion=" + idAsignacion + ", aula=" + aula + ", horario=" + horario + ", estatus="
				+ estatus + ", tutor=" + tutor + ", carrera=" + carrera + ", semestre=" + semestre + ", tutorados="
				+ tutorados + "]";
	}


	
    
}