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
@Table(name = "reporte_tutor")
public class ReporteTutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reporte")
    private Integer idReporte;

    @Column(name = "fecha_reporte")
    private LocalDate fechaReporte;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "id_sesion")
    private SesionTutoria sesion;

	public Integer getIdReporte() {
		return idReporte;
	}

	public void setIdReporte(Integer idReporte) {
		this.idReporte = idReporte;
	}

	public LocalDate getFechaReporte() {
		return fechaReporte;
	}

	public void setFechaReporte(LocalDate fechaReporte) {
		this.fechaReporte = fechaReporte;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public SesionTutoria getSesion() {
		return sesion;
	}

	public void setSesion(SesionTutoria sesion) {
		this.sesion = sesion;
	}

	@Override
	public String toString() {
		return "ReporteTutor [idReporte=" + idReporte + ", fechaReporte=" + fechaReporte + ", observaciones="
				+ observaciones + ", sesion=" + sesion + "]";
	}
	
	

}
