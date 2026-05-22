package tsp.itch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tsp.itch.model.Actividad;
import tsp.itch.model.Asignacion;
import tsp.itch.model.Asistencia;
import tsp.itch.model.PatCarrera;
import tsp.itch.model.SesionTutoria;
import tsp.itch.service.IActividadService;
import tsp.itch.service.IAsignacionService;
import tsp.itch.service.IAsistenciaService;
import tsp.itch.service.ICarreraService;
import tsp.itch.service.IPatCarreraService;
import tsp.itch.service.ISesionTutoriaService;

@RequestMapping("/sesion")
@Controller
public class SesionTutoriaController {

    @Autowired
    private ISesionTutoriaService sesionService;

    @Autowired
    private IAsignacionService asignacionService;
    
    @Autowired
    private IAsistenciaService asistenciaService;  
    //nuevos agrgar
    @Autowired
    private IActividadService actividadService;

    @Autowired
    private IPatCarreraService patCarreraService;
    
    @Autowired
    private ICarreraService carreraService;

    @GetMapping("/sesiones")
    public String mostrarSesiones(Model model) {
        List<SesionTutoria> lista = sesionService.buscarTodos();
        model.addAttribute("sesiones", lista);
        return "sesion/datosSesion";
    }

    @GetMapping("/sesiones/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("sesion", new SesionTutoria());
      //  model.addAttribute("asignaciones", asignacionService.buscarTodas());
        model.addAttribute("asignaciones", asignacionService.buscarActivas());
        model.addAttribute("actividades", new java.util.ArrayList<>());
        return "sesion/formSesion";
    }

   /* @PostMapping("/sesiones/guardar")
    public String guardarSesion(SesionTutoria sesion,
            RedirectAttributes attributes) {
        sesionService.guardar(sesion);
        attributes.addFlashAttribute("msg", "Sesión guardada correctamente");
        return "redirect:/sesion/sesiones";
    }*/
    
   /* @PostMapping("/sesiones/guardar")
    public String guardarSesion(SesionTutoria sesion,
            RedirectAttributes attributes) {

        // Cargar la asignación completa desde la BD
        Asignacion asignacion = asignacionService.buscarPorId(
            sesion.getAsignacion().getIdAsignacion()
        );
        sesion.setAsignacion(asignacion);

        sesionService.guardar(sesion);
        attributes.addFlashAttribute("msg", "Sesión guardada correctamente");
        return "redirect:/sesion/sesiones";
    }*/
   /* @PostMapping("/sesiones/guardar")
    public String guardarSesion(SesionTutoria sesion,
            RedirectAttributes attributes, Model model) {*/
    
    @PostMapping("/sesiones/guardar")
    public String guardarSesion(SesionTutoria sesion,
            @RequestParam(required = false) Integer idActividad,
            RedirectAttributes attributes, Model model) {
        Asignacion asignacion = asignacionService.buscarPorId(
            sesion.getAsignacion().getIdAsignacion()
        );
       sesion.setAsignacion(asignacion);
        if (idActividad != null) {
            sesion.setActividad(actividadService.buscarPorId(idActividad));
        }

        // Verificar si la fecha ya existe
      /*  boolean fechaDuplicada;
        if (sesion.getIdSesion() == null) {
            // Es nuevo: verificar que no exista esa fecha
            fechaDuplicada = sesionService.existeFecha(sesion.getFecha());
        } else {
            // Es edición: verificar que no exista en OTRA sesión
            fechaDuplicada = sesionService.existeFechaEnOtra(sesion.getFecha(), sesion.getIdSesion());
        }*/
        
        boolean fechaDuplicada;
        if (sesion.getIdSesion() == null) {
            fechaDuplicada = sesionService.existeFecha(
                sesion.getFecha(), 
                sesion.getAsignacion().getIdAsignacion()
            );
        } else {
            fechaDuplicada = sesionService.existeFechaEnOtra(
                sesion.getFecha(), 
                sesion.getAsignacion().getIdAsignacion(),
                sesion.getIdSesion()
            );
        }
        

        if (fechaDuplicada) {
            model.addAttribute("errorFecha", "⚠ Ya existe una sesión registrada para esa fecha.");
            model.addAttribute("sesion", sesion);
         //   model.addAttribute("asignaciones", asignacionService.buscarTodas());
            model.addAttribute("asignaciones", asignacionService.buscarActivas());
            return "sesion/formSesion";
        }

        sesionService.guardar(sesion);
        attributes.addFlashAttribute("msg", "Sesión guardada correctamente");
        return "redirect:/sesion/sesiones";
    }

    @GetMapping("/ver/{id}")
    public String verDetalle(@PathVariable("id") int id, Model model) {
        SesionTutoria sesion = sesionService.buscarPorId(id);
        model.addAttribute("sesion", sesion);
        return "sesion/detalleSesion";
    }

    @GetMapping("/editar/{id}")
    public String editarSesion(@PathVariable("id") int id, Model model) {
        SesionTutoria sesion = sesionService.buscarPorId(id);
        model.addAttribute("sesion", sesion);
      //  model.addAttribute("asignaciones", asignacionService.buscarTodas());
        model.addAttribute("asignaciones", asignacionService.buscarActivas());
        return "sesion/formSesion";
    }

  /* @GetMapping("/eliminar")
    public String eliminarSesion(@RequestParam Integer id,
            RedirectAttributes attributes) {
        sesionService.eliminar(id);
        attributes.addFlashAttribute("msg", "Sesión eliminada correctamente");
        return "redirect:/sesion/sesiones";
    }
}*/
    
    @GetMapping("/eliminar")
    public String eliminarSesion(@RequestParam Integer id,
            RedirectAttributes attributes) {

        // Primero eliminar todas las asistencias relacionadas a esta sesión
        List<Asistencia> asistencias = asistenciaService.buscarPorSesion(id);
        for (Asistencia a : asistencias) {
            asistenciaService.eliminar(a.getIdAsistencia());
        }

        // Luego eliminar la sesión
        sesionService.eliminar(id);
        attributes.addFlashAttribute("msg", "Sesión eliminada correctamente");
        return "redirect:/sesion/sesiones";
    }
    
    @GetMapping("/actividades/{idAsignacion}")
    @ResponseBody
    public List<Actividad> obtenerActividades(@PathVariable Integer idAsignacion) {
        Asignacion asignacion = asignacionService.buscarPorId(idAsignacion);
        Integer idCarrera = asignacion.getCarrera().getIdCarrera();
        Integer idSemestre = asignacion.getSemestre().getIdSemestre();
        
        PatCarrera patCarrera = patCarreraService.buscarPorCarreraYSemestre(idCarrera, idSemestre);
        if (patCarrera == null) return new java.util.ArrayList<>();
        
        return actividadService.buscarPorPatCarrera(patCarrera.getIdPatCarrera());
    }
    
    
    @GetMapping("/sesiones/masivo")
    public String mostrarFormularioMasivo(Model model) {
        model.addAttribute("carreras", carreraService.buscarActivas());
        return "sesion/formSesionMasiva";
    }

    @PostMapping("/sesiones/masivo/guardar")
    public String guardarSesionMasiva(
            @RequestParam Integer idCarrera,
            @RequestParam Integer semana,
            @RequestParam String fecha,
            @RequestParam(required = false) Integer idActividad,
            RedirectAttributes attributes) {

        List<Asignacion> asignaciones = asignacionService.buscarPorCarrera(idCarrera);

        for (Asignacion asignacion : asignaciones) {
            SesionTutoria sesion = new SesionTutoria();
            sesion.setSemana(semana);
            sesion.setFecha(java.time.LocalDate.parse(fecha));
            sesion.setAsignacion(asignacion);
            if (idActividad != null) {
                sesion.setActividad(actividadService.buscarPorId(idActividad));
            }
            sesionService.guardar(sesion);
        }

        attributes.addFlashAttribute("msg", "Sesiones registradas para todos los tutores de esa carrera");
        return "redirect:/sesion/sesiones";
    }
    
    @GetMapping("/actividadesPorCarrera/{idCarrera}")
    @ResponseBody
    public List<Actividad> obtenerActividadesPorCarrera(@PathVariable Integer idCarrera) {
        List<Asignacion> asignaciones = asignacionService.buscarPorCarrera(idCarrera);
        if (asignaciones.isEmpty()) return new java.util.ArrayList<>();
        Asignacion primera = asignaciones.get(0);
        Integer idSemestre = primera.getSemestre().getIdSemestre();
        PatCarrera patCarrera = patCarreraService.buscarPorCarreraYSemestre(idCarrera, idSemestre);
        if (patCarrera == null) return new java.util.ArrayList<>();
        return actividadService.buscarPorPatCarrera(patCarrera.getIdPatCarrera());
    }
    
  /*  @GetMapping("/tutor")
    public String vistaTutor(
            @RequestParam(required = false) Integer idAsignacion,
            Model model) {

        // Todas las asignaciones activas para el dropdown de tutores
        model.addAttribute("asignaciones", asignacionService.buscarActivas());

        if (idAsignacion != null) {
            List<SesionTutoria> misSesiones = sesionService.buscarTodos()
                .stream()
                .filter(s -> s.getAsignacion().getIdAsignacion().equals(idAsignacion))
                .collect(java.util.stream.Collectors.toList());

            model.addAttribute("misSesiones", misSesiones);
            model.addAttribute("idAsignacionSeleccionada", idAsignacion);

            // Asignación seleccionada para mostrar datos del tutor
            Asignacion asignacion = asignacionService.buscarPorId(idAsignacion);
            model.addAttribute("asignacionSeleccionada", asignacion);

            // Actividades disponibles para esa asignación
            Integer idCarrera = asignacion.getCarrera().getIdCarrera();
            Integer idSemestre = asignacion.getSemestre().getIdSemestre();
            PatCarrera patCarrera = patCarreraService.buscarPorCarreraYSemestre(idCarrera, idSemestre);
            if (patCarrera != null) {
                model.addAttribute("actividades",
                    actividadService.buscarPorPatCarrera(patCarrera.getIdPatCarrera()));
            } else {
                model.addAttribute("actividades", new java.util.ArrayList<>());
            }
        }

        return "sesion/misSesiones";
    }*/
    
    @GetMapping("/tutor")
    public String vistaTutor(
            @RequestParam(required = false) Integer idTutor,
            Model model) {

        // Todas las asignaciones activas
        List<Asignacion> todasAsignaciones = asignacionService.buscarActivas();
        
        // Tutores únicos para el dropdown
        java.util.Map<Integer, Asignacion> tutoresUnicos = new java.util.LinkedHashMap<>();
        for (Asignacion a : todasAsignaciones) {
            tutoresUnicos.putIfAbsent(a.getTutor().getIdTutor(), a);
        }
        model.addAttribute("tutoresUnicos", tutoresUnicos.values());

        if (idTutor != null) {
            // Todas las asignaciones de ese tutor (uno por semestre)
            List<Asignacion> asignacionesTutor = todasAsignaciones.stream()
                .filter(a -> a.getTutor().getIdTutor().equals(idTutor))
                .collect(java.util.stream.Collectors.toList());

            model.addAttribute("idTutorSeleccionado", idTutor);
            model.addAttribute("asignacionesTutor", asignacionesTutor);

            // Sesiones agrupadas por asignacion (semestre)
            java.util.Map<Asignacion, List<SesionTutoria>> sesionesPorSemestre = new java.util.LinkedHashMap<>();
            for (Asignacion a : asignacionesTutor) {
                List<SesionTutoria> sesiones = sesionService.buscarTodos()
                    .stream()
                    .filter(s -> s.getAsignacion().getIdAsignacion().equals(a.getIdAsignacion()))
                    .collect(java.util.stream.Collectors.toList());
                sesionesPorSemestre.put(a, sesiones);
            }
            model.addAttribute("sesionesPorSemestre", sesionesPorSemestre);

            // Info del tutor
            if (!asignacionesTutor.isEmpty()) {
                model.addAttribute("tutorSeleccionado", asignacionesTutor.get(0).getTutor());
            }
        }

        return "sesion/misSesiones";
    }

    @PostMapping("/tutor/guardar")
    public String guardarSesionTutor(
            @RequestParam Integer idAsignacion,
            @RequestParam Integer semana,
            @RequestParam String fecha,
            @RequestParam(required = false) Integer idActividad,
            RedirectAttributes attributes) {

        Asignacion asignacion = asignacionService.buscarPorId(idAsignacion);

        boolean fechaDuplicada = sesionService.existeFecha(
            java.time.LocalDate.parse(fecha), idAsignacion);

        if (fechaDuplicada) {
            attributes.addFlashAttribute("error", "⚠ Ya existe una sesión registrada para esa fecha.");
            return "redirect:/sesion/tutor?idAsignacion=" + idAsignacion;
        }

        SesionTutoria sesion = new SesionTutoria();
        sesion.setSemana(semana);
        sesion.setFecha(java.time.LocalDate.parse(fecha));
        sesion.setAsignacion(asignacion);
        if (idActividad != null) {
            sesion.setActividad(actividadService.buscarPorId(idActividad));
        }

        sesionService.guardar(sesion);
        attributes.addFlashAttribute("msg", "✅ Sesión registrada correctamente.");
        return "redirect:/sesion/tutor?idAsignacion=" + idAsignacion;
    }
    
}
    