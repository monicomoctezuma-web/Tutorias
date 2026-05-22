package tsp.itch.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tsp.itch.model.Asistencia;
import tsp.itch.model.CoordinadorCarrera;
import tsp.itch.model.JefeAcademico;
import tsp.itch.model.PatCarrera;
import tsp.itch.model.Asignacion;
import tsp.itch.model.AsignacionTutorado;
import tsp.itch.model.SesionTutoria;
import tsp.itch.service.IAsistenciaService;
import tsp.itch.service.ICoordinadorCarreraService;
import tsp.itch.service.IJefeAcademicoService;
import tsp.itch.service.IPatCarreraService;
import tsp.itch.service.IAsignacionTutoradoService;
import tsp.itch.service.IActividadService;
import tsp.itch.service.IAsignacionService;
import tsp.itch.service.IUsuarioService;
import tsp.itch.service.ISesionTutoriaService;
import tsp.itch.service.ITutorService;

@RequestMapping("/asistencia")
@Controller
public class AsistenciaController {

    @Autowired
    private IAsistenciaService asistenciaService;

    @Autowired
    private IAsignacionTutoradoService asignacionTutoradoService;

    @Autowired
    private IAsignacionService asignacionService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private ISesionTutoriaService sesionService;
    
    @Autowired
    private ITutorService tutorService;
    
    @Autowired
    private IPatCarreraService patCarreraService;

    @Autowired
    private IActividadService actividadService;
    
    @Autowired 
    private IJefeAcademicoService jefeService;

    @Autowired private ICoordinadorCarreraService coordinadorService;
    // ===================== PANTALLA 1 - TUTOR REGISTRA ASISTENCIA =====================
  /*  @GetMapping("/registrar")
    public String mostrarRegistro(
            @RequestParam(required = false) Integer idTutor,
            @RequestParam(required = false) Integer idAsignacion,
            @RequestParam(required = false) Integer idSesion,
            Model model) {

      //  model.addAttribute("tutores", usuarioService.buscarTodos());
    	//model.addAttribute("tutores", usuarioService.buscarActivosPorRol("TUTOR"));
    	model.addAttribute("tutores", tutorService.buscarTodos());
        if (idTutor != null) {
            model.addAttribute("asignaciones", asignacionService.buscarPorTutor(idTutor));
            model.addAttribute("idTutorSeleccionado", idTutor);
        }

        if (idAsignacion != null) {
            model.addAttribute("asignaciones", asignacionService.buscarPorTutor(idTutor));
            model.addAttribute("idTutorSeleccionado", idTutor);
            model.addAttribute("idAsignacionSeleccionada", idAsignacion);

            // Cargar sesiones (viernes) de esa asignación
            List<SesionTutoria> sesiones = sesionService.buscarPorAsignacion(idAsignacion);
            model.addAttribute("sesiones", sesiones);

            // Cargar tutorados de esa asignación
            List<AsignacionTutorado> tutorados = asignacionTutoradoService.buscarPorAsignacion(idAsignacion);
            model.addAttribute("tutorados", tutorados);

            // Si hay sesión seleccionada cargar asistencias ya registradas
            if (idSesion != null) {
                model.addAttribute("idSesionSeleccionada", idSesion);
                List<Asistencia> asistenciasRegistradas = asistenciaService.buscarPorSesion(idSesion);
                Map<Integer, Integer> presencias = new HashMap<>();
                for (Asistencia a : asistenciasRegistradas) {
                    presencias.put(a.getTutorado().getIdTutorado(), a.getPresente());
                }
                model.addAttribute("presencias", presencias);
                //
                model.addAttribute("sesionActual", sesionService.buscarPorId(idSesion));
                
                //nuevo codigo 
                
             // Buscar actividades del PAT según carrera y semana de la sesión
                SesionTutoria sesionActual = sesionService.buscarPorId(idSesion);
                Asignacion asignacion = asignacionService.buscarPorId(idAsignacion);

                Integer idCarrera = asignacion.getCarrera().getIdCarrera();
                Integer idSemestre = asignacion.getSemestre().getIdSemestre();

                // Buscar el PatCarrera que corresponde a esa carrera y semestre
                PatCarrera patCarrera = patCarreraService.buscarPorCarreraYSemestre(idCarrera, idSemestre);

                if (patCarrera != null) {
                    // Filtrar actividades por la semana de la sesión
                    String semanaStr = String.valueOf(sesionActual.getSemana());
                    List<tsp.itch.model.Actividad> actividadesSemana = actividadService
                        .buscarPorPatCarrera(patCarrera.getIdPatCarrera())
                        .stream()
                        .filter(a -> semanaStr.equals(a.getSemana()))
                        .collect(java.util.stream.Collectors.toList());
                    model.addAttribute("actividadesSemana", actividadesSemana);
                }

                // Actividad actualmente asignada a la sesión
                model.addAttribute("idActividadActual",
                    sesionActual.getActividad() != null ? sesionActual.getActividad().getIdActividad() : null);
                
            
            }
        }

        return "asistencia/registrarAsistencia";
    }
    
    
    
    

    // Guardar asistencia
    @PostMapping("/guardarRegistro")
    public String guardarRegistro(
            @RequestParam Integer idAsignacion,
            @RequestParam Integer idTutor,
            @RequestParam Integer idSesion,
            @RequestParam(required = false) List<Integer> presentes,
            @RequestParam(required = false) Integer idActividad,  // NUEVO
            RedirectAttributes attributes) {

        SesionTutoria sesion = sesionService.buscarPorId(idSesion);
        
        if (idActividad != null) {
            sesion.setActividad(actividadService.buscarPorId(idActividad));
            sesionService.guardar(sesion);
        }
        
        List<AsignacionTutorado> tutorados = asignacionTutoradoService.buscarPorAsignacion(idAsignacion);

        // Eliminar asistencias previas de esa sesión para re-registrar
        List<Asistencia> previas = asistenciaService.buscarPorSesion(idSesion);
        for (Asistencia a : previas) {
            asistenciaService.eliminar(a.getIdAsistencia());
        }

        // Guardar asistencia por cada tutorado
        for (AsignacionTutorado at : tutorados) {
            Asistencia asistencia = new Asistencia();
            asistencia.setSesion(sesion);
            asistencia.setTutorado(at.getTutorado());
            int presente = (presentes != null && presentes.contains(at.getTutorado().getIdTutorado())) ? 1 : 0;
            asistencia.setPresente(presente);
            asistenciaService.guardar(asistencia);
        }

        attributes.addFlashAttribute("msg", "Asistencia registrada correctamente");
        return "redirect:/asistencia/registrar?idTutor=" + idTutor + "&idAsignacion=" + idAsignacion + "&idSesion=" + idSesion;
    }*/
    
    @GetMapping("/registrar")
    public String mostrarRegistro(
            @RequestParam(required = false) Integer idTutor,
            @RequestParam(required = false) Integer idAsignacion,
            @RequestParam(required = false) String fecha,
            Model model) {

        //model.addAttribute("tutores", tutorService.buscarTodos());
    	model.addAttribute("tutores", tutorService.buscarActivos());
        if (idTutor != null) {
            model.addAttribute("asignaciones", asignacionService.buscarPorTutor(idTutor));
            model.addAttribute("idTutorSeleccionado", idTutor);
        }

        if (idAsignacion != null) {
            model.addAttribute("asignaciones", asignacionService.buscarPorTutor(idTutor));
            model.addAttribute("idTutorSeleccionado", idTutor);
            model.addAttribute("idAsignacionSeleccionada", idAsignacion);
            model.addAttribute("fechaSeleccionada", fecha);

            if (fecha != null && !fecha.isEmpty()) {
                LocalDate fechaLocal = LocalDate.parse(fecha);
                Asignacion asignacion = asignacionService.buscarPorId(idAsignacion);

                Integer idCarrera  = asignacion.getCarrera().getIdCarrera();
                Integer idSemestre = asignacion.getSemestre().getIdSemestre();

                // Buscar PatCarrera según carrera y semestre
                PatCarrera patCarrera = patCarreraService.buscarPorCarreraYSemestre(idCarrera, idSemestre);

                if (patCarrera != null) {
                    // Buscar actividades de esa fecha en ese PatCarrera
                    List<tsp.itch.model.Actividad> actividadesFecha = actividadService
                        .buscarPorPatCarrera(patCarrera.getIdPatCarrera())
                        .stream()
                        .filter(a -> fechaLocal.equals(a.getFecha()))
                        .collect(java.util.stream.Collectors.toList());

                    model.addAttribute("actividadesFecha", actividadesFecha);

                    // Si hay actividad, tomar la semana de la primera
                    if (!actividadesFecha.isEmpty()) {
                        model.addAttribute("semanaActividad", actividadesFecha.get(0).getSemana());
                    }
                }

                // Cargar tutorados de esa asignación
                List<AsignacionTutorado> tutorados =
                    asignacionTutoradoService.buscarPorAsignacion(idAsignacion);
                model.addAttribute("tutorados", tutorados);

                // Verificar si ya existe sesión para esa fecha y asignación
                boolean yaExiste = sesionService.existeFecha(fechaLocal, idAsignacion);
                model.addAttribute("sesionYaExiste", yaExiste);

                if (yaExiste) {
                    // Buscar la sesión existente para mostrar asistencias ya guardadas
                    SesionTutoria sesionExistente = sesionService.buscarPorAsignacion(idAsignacion)
                        .stream()
                        .filter(s -> fechaLocal.equals(s.getFecha()))
                        .findFirst().orElse(null);

                    if (sesionExistente != null) {
                        model.addAttribute("idSesionExistente", sesionExistente.getIdSesion());
                        List<Asistencia> asistenciasRegistradas =
                            asistenciaService.buscarPorSesion(sesionExistente.getIdSesion());
                        Map<Integer, Integer> presencias = new HashMap<>();
                        for (Asistencia a : asistenciasRegistradas) {
                            presencias.put(a.getTutorado().getIdTutorado(), a.getPresente());
                        }
                        model.addAttribute("presencias", presencias);
                    }
                }
            }
        }

        return "asistencia/registrarAsistencia";
    }

    @PostMapping("/guardarRegistro")
    public String guardarRegistro(
            @RequestParam Integer idAsignacion,
            @RequestParam Integer idTutor,
            @RequestParam String fecha,
            @RequestParam(required = false) String semana,
            @RequestParam(required = false) Integer idActividad,
            @RequestParam(required = false) List<Integer> presentes,
            @RequestParam(required = false) Integer idSesionExistente,
            RedirectAttributes attributes) {

        LocalDate fechaLocal = LocalDate.parse(fecha);
        Asignacion asignacion = asignacionService.buscarPorId(idAsignacion);

        SesionTutoria sesion;

        // Si ya existe la sesión, reutizarla; si no, crearla
        if (idSesionExistente != null) {
            sesion = sesionService.buscarPorId(idSesionExistente);
        } else {
            sesion = new SesionTutoria();
            sesion.setFecha(fechaLocal);
            sesion.setAsignacion(asignacion);
            if (semana != null && !semana.isEmpty()) {
                sesion.setSemana(Integer.parseInt(semana));
            }
        }

        // Asignar actividad a la sesión
        if (idActividad != null) {
            sesion.setActividad(actividadService.buscarPorId(idActividad));
        }
        sesionService.guardar(sesion);

        // Eliminar asistencias previas y re-registrar
        List<Asistencia> previas = asistenciaService.buscarPorSesion(sesion.getIdSesion());
        for (Asistencia a : previas) {
            asistenciaService.eliminar(a.getIdAsistencia());
        }

        List<AsignacionTutorado> tutorados =
            asignacionTutoradoService.buscarPorAsignacion(idAsignacion);
        for (AsignacionTutorado at : tutorados) {
            Asistencia asistencia = new Asistencia();
            asistencia.setSesion(sesion);
            asistencia.setTutorado(at.getTutorado());
            int presente = (presentes != null &&
                presentes.contains(at.getTutorado().getIdTutorado())) ? 1 : 0;
            asistencia.setPresente(presente);
            asistenciaService.guardar(asistencia);
        }

        attributes.addFlashAttribute("msg", "✅ Asistencia registrada correctamente.");
        return "redirect:/asistencia/registrar?idTutor=" + idTutor
             + "&idAsignacion=" + idAsignacion
             + "&fecha=" + fecha;
    }

    // ===================== PANTALLA 2 - PORCENTAJES =====================
    @GetMapping("/porcentajes")
    public String mostrarPorcentajes(
            @RequestParam(required = false) Integer idTutor,
            @RequestParam(required = false) Integer idAsignacion,
            Model model) {

       // model.addAttribute("tutores", usuarioService.buscarTodos());
    	//model.addAttribute("tutores", usuarioService.buscarActivosPorRol("TUTOR"));
    	//model.addAttribute("tutores", tutorService.buscarTodos());
       
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String username = auth.getName();
    	boolean esCoordinadorCarrera = auth.getAuthorities().stream()
    	    .anyMatch(a -> a.getAuthority().equals("ROLE_COORDINADOR_CARRERA"));

    	if (esCoordinadorCarrera) {
    	    CoordinadorCarrera coordinador = coordinadorService.buscarPorUsername(username);
    	    //model.addAttribute("tutores", tutorService.buscarPorCarrera(coordinador.getCarrera().getIdCarrera()));
    	    model.addAttribute("tutores", tutorService.buscarActivosPorCarrera(coordinador.getCarrera().getIdCarrera()));
    	} else {
    	  //  model.addAttribute("tutores", tutorService.buscarTodos());
    		model.addAttribute("tutores", tutorService.buscarActivos());
    	}
    	
    	
    	
        if (idTutor != null) {
            model.addAttribute("asignaciones", asignacionService.buscarPorTutor(idTutor));
            model.addAttribute("idTutorSeleccionado", idTutor);
        }

        if (idAsignacion != null) {
            model.addAttribute("idAsignacionSeleccionada", idAsignacion);
            model.addAttribute("asignaciones", asignacionService.buscarPorTutor(idTutor));
            model.addAttribute("idTutorSeleccionado", idTutor);

            List<AsignacionTutorado> tutorados = asignacionTutoradoService.buscarPorAsignacion(idAsignacion);
            model.addAttribute("tutorados", tutorados);

           /* Map<Integer, Double> porcentajes = new HashMap<>();
            Map<Integer, Long> presencias = new HashMap<>();
            for (AsignacionTutorado at : tutorados) {
                Integer idTutorado = at.getTutorado().getIdTutorado();
                porcentajes.put(idTutorado, asistenciaService.calcularPorcentaje(idTutorado));
                presencias.put(idTutorado, asistenciaService.contarPresencias(idTutorado));
            }*/
            Map<Integer, Double> porcentajes = new HashMap<>();
            Map<Integer, Long> presencias = new HashMap<>();
            for (AsignacionTutorado at : tutorados) {
                Integer idTutorado = at.getTutorado().getIdTutorado();
                porcentajes.put(idTutorado, asistenciaService.calcularPorcentajePorAsignacion(idTutorado, idAsignacion));
                presencias.put(idTutorado, asistenciaService.contarPresenciasPorAsignacion(idTutorado, idAsignacion));
            }
            
            model.addAttribute("porcentajes", porcentajes);
            model.addAttribute("presencias", presencias);
            model.addAttribute("totalSesiones", 10);
        }

        return "asistencia/porcentajesAsistencia";
    }

    @GetMapping("/eliminar")
    public String eliminarAsistencia(@RequestParam Integer id, RedirectAttributes attributes) {
        asistenciaService.eliminar(id);
        attributes.addFlashAttribute("msg", "Asistencia eliminada correctamente");
        return "redirect:/asistencia/registrar";
    }
}