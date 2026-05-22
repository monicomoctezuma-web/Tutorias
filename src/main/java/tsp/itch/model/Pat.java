package tsp.itch.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "pat")
public class Pat {
	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pat")
	private Integer idPat;
    
    @Column(name = "titulo")
	private String titulo;
    
    @Column(name = "descripcion", columnDefinition = "TEXT") 
    private String descripcion;
	
	@ManyToOne
    @JoinColumn(name = "id_semestre")
	private Semestre semestre;
	
	@JsonIgnore
	@OneToMany(mappedBy = "pat")
	 private List<PatCarrera> patCarreras;

	
	 @Column(name = "estatus")
	private Integer estatus;
	
	public Integer getIdPat() {
		return idPat;
	}
	public void setIdPat(Integer idPat) {
		this.idPat = idPat;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Semestre getSemestre() {
		return semestre;
	}
	public void setSemestre(Semestre semestre) {
		this.semestre = semestre;
	}
	
	
	public List<PatCarrera> getPatCarreras() {
		return patCarreras;
	}
	public void setPatCarreras(List<PatCarrera> patCarreras) {
		this.patCarreras = patCarreras;
	}
	public Integer getEstatus() {
		return estatus;
	}
	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}
	@Override
	public String toString() {
		return "Pat [idPat=" + idPat + ", titulo=" + titulo + ", descripcion=" + descripcion + ", semestre=" + semestre
				+ ", patCarreras=" + patCarreras + ", estatus=" + estatus + "]";
	}
	
	

	
	

	
	

}
