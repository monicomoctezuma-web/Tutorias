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
@Table(name = "tutor")
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tutor")
    private Integer idTutor;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "foto")
    private String foto;

    @Column(name = "estatus")
    private Integer estatus;

    @ManyToOne
    @JoinColumn(name = "id_carrera")
    private Carrera carrera;
    
    @Column(name = "numero_trabajador", unique = true)
    private String numeroTrabajador;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "username", unique = true)
    private String username;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getNumeroTrabajador() { return numeroTrabajador; }
    public void setNumeroTrabajador(String numeroTrabajador)
    { this.numeroTrabajador = numeroTrabajador; }

    public Integer getIdTutor() { return idTutor; }
    public void setIdTutor(Integer idTutor) { this.idTutor = idTutor; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    public Integer getEstatus() { return estatus; }
    public void setEstatus(Integer estatus) { this.estatus = estatus; }

    public Carrera getCarrera() { return carrera; }
    public void setCarrera(Carrera carrera) { this.carrera = carrera; }
	@Override
	public String toString() {
		return "Tutor [idTutor=" + idTutor + ", nombre=" + nombre + ", apellido=" + apellido + ", telefono=" + telefono
				+ ", foto=" + foto + ", estatus=" + estatus + ", carrera=" + carrera + ", numeroTrabajador="
				+ numeroTrabajador + ", password=" + password + ", username=" + username + "]";
	}
	
    
    
	
  
   
}