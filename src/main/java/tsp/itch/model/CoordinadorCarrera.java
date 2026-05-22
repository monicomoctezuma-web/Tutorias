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
	@Table(name = "coordinador_carrera")
	public class CoordinadorCarrera {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id_coordinador")
	    private Integer idCoordinador;

	    @ManyToOne
	    @JoinColumn(name = "id_usuario")
	    private Usuario usuario;

	    @ManyToOne
	    @JoinColumn(name = "id_carrera")
	    private Carrera carrera;

		public Integer getIdCoordinador() {
			return idCoordinador;
		}

		public void setIdCoordinador(Integer idCoordinador) {
			this.idCoordinador = idCoordinador;
		}

		public Usuario getUsuario() {
			return usuario;
		}

		public void setUsuario(Usuario usuario) {
			this.usuario = usuario;
		}

		public Carrera getCarrera() {
			return carrera;
		}

		public void setCarrera(Carrera carrera) {
			this.carrera = carrera;
		}

		@Override
		public String toString() {
			return "CoordinadorCarrera [idCoordinador=" + idCoordinador + ", usuario=" + usuario + ", carrera="
					+ carrera + "]";
		}
	    
	    

}
