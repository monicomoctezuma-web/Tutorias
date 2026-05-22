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
@Table(name = "asistencia")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asistencia")
    private Integer idAsistencia;

    @Column(name = "presente")
    private Integer presente;

    @ManyToOne
    @JoinColumn(name = "id_sesion")
    private SesionTutoria sesion;

    @ManyToOne
    @JoinColumn(name = "id_tutorado")
    private Tutorado tutorado;

	public Integer getIdAsistencia() {
		return idAsistencia;
	}

	public void setIdAsistencia(Integer idAsistencia) {
		this.idAsistencia = idAsistencia;
	}

	public Integer getPresente() {
		return presente;
	}

	public void setPresente(Integer presente) {
		this.presente = presente;
	}

	public SesionTutoria getSesion() {
		return sesion;
	}

	public void setSesion(SesionTutoria sesion) {
		this.sesion = sesion;
	}

	public Tutorado getTutorado() {
		return tutorado;
	}

	public void setTutorado(Tutorado tutorado) {
		this.tutorado = tutorado;
	}

	@Override
	public String toString() {
		return "Asistencia [idAsistencia=" + idAsistencia + ", presente=" + presente + ", sesion=" + sesion
				+ ", tutorado=" + tutorado + "]";
	}
    
    

}
