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
@Table(name = "tutorado")
public class Tutorado {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "id_tutorado")
	 private Integer idTutorado;
	 
	 @Column(name = "numero_control")
	 private String numeroControl;
	 
	 @Column(name = "nombre")
	 private String nombre;
	 
	 @Column(name = "apellido")
	 private String apellido;
	 
	 @Column(name = "correo")
	 private String correo;
	 
	 @Column(name = "foto")
	 private String foto;
	 
	 @Column(name = "estatus")
	 private Integer estatus;
	 
	 @ManyToOne
	 @JoinColumn(name = "id_carrera")
	 private Carrera carrera;

	 public Integer getIdTutorado() {
		 return idTutorado;
	 }

	 public void setIdTutorado(Integer idTutorado) {
		 this.idTutorado = idTutorado;
	 }

	 public String getNumeroControl() {
		 return numeroControl;
	 }

	 public void setNumeroControl(String numeroControl) {
		 this.numeroControl = numeroControl;
	 }

	 public String getNombre() {
		 return nombre;
	 }

	 public void setNombre(String nombre) {
		 this.nombre = nombre;
	 }

	 public String getApellido() {
		 return apellido;
	 }

	 public void setApellido(String apellido) {
		 this.apellido = apellido;
	 }

	 public String getCorreo() {
		 return correo;
	 }

	 public void setCorreo(String correo) {
		 this.correo = correo;
	 }

	 public String getFoto() {
		 return foto;
	 }

	 public void setFoto(String foto) {
		 this.foto = foto;
	 }

	 public Integer getEstatus() {
		 return estatus;
	 }

	 public void setEstatus(Integer estatus) {
		 this.estatus = estatus;
	 }

	 public Carrera getCarrera() {
		 return carrera;
	 }

	 public void setCarrera(Carrera carrera) {
		 this.carrera = carrera;
	 }

	 @Override
	 public String toString() {
		return "Tutorado [idTutorado=" + idTutorado + ", numeroControl=" + numeroControl + ", nombre=" + nombre
				+ ", apellido=" + apellido + ", correo=" + correo + ", foto=" + foto + ", estatus=" + estatus
				+ ", carrera=" + carrera + "]";
	 }

	 
	 

}
