package tsp.itch.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tsp.itch.model.CoordinadorCarrera;
import tsp.itch.model.ReporteTutor;
import tsp.itch.model.SesionTutoria;
import tsp.itch.service.IReporteTutorService;
import tsp.itch.service.ISesionTutoriaService;
import tsp.itch.service.ITutorService;
import tsp.itch.service.IAsignacionService;
import tsp.itch.service.ICoordinadorCarreraService;
import tsp.itch.service.IUsuarioService;

@RequestMapping("/reporte")
@Controller
public class ReporteTutorController {

    @Autowired
    private IReporteTutorService reporteService;

    @Autowired
    private ISesionTutoriaService sesionService;

    @Autowired
    private IAsignacionService asignacionService;

    @Autowired
    private IUsuarioService usuarioService;
    
    @Autowired
    private ITutorService tutorService;
    
    
    @Autowired
    private ICoordinadorCarreraService coordinadorService;
    

    // ===================== LISTA POR TUTOR Y SESIÓN =====================
   /* @GetMapping("/reportes")
    public String mostrarReportes(
            @RequestParam(required = false) Integer idTutor,
            @RequestParam(required = false) Integer idSesion,
            Model model) {

      //  model.addAttribute("tutores", usuarioService.buscarTodos());
    	model.addAttribute("tutores", tutorService.buscarTodos());

        if (idTutor != null) {
            model.addAttribute("idTutorSeleccionado", idTutor);
            // Cargar sesiones del tutor a través de sus asignaciones
            List<SesionTutoria> sesiones = new java.util.ArrayList<>();
            asignacionService.buscarPorTutor(idTutor).forEach(a ->
                sesiones.addAll(sesionService.buscarPorAsignacion(a.getIdAsignacion()))
            );
            model.addAttribute("sesiones", sesiones);
        }

        if (idSesion != null) {
            model.addAttribute("idSesionSeleccionada", idSesion);
            model.addAttribute("reportes", reporteService.buscarPorSesion(idSesion));
        }

        return "reporte/datosReporte";
    }*/
    
    @GetMapping("/reportes")
    public String mostrarReportes(
            @RequestParam(required = false) Integer idTutor,
            @RequestParam(required = false) Integer idSesion,
            Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        boolean esTutor = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_TUTOR"));
        boolean esCoordinador = auth.getAuthorities().stream()
            .anyMatch(a -> a.getAuthority().equals("ROLE_COORDINADOR_CARRERA"));

       /* if (esTutor) {
            // El tutor solo ve sus propios reportes
            model.addAttribute("reportes", reporteService.buscarPorTutorUsername(username));
            model.addAttribute("soloVista", true);
            return "reporte/datosReporte";
        }*/
        
       /* if (esTutor) {
            tsp.itch.model.Tutor tutor = tutorService.buscarPorUsername(username);
            List<SesionTutoria> sesiones = new java.util.ArrayList<>();
            asignacionService.buscarPorTutor(tutor.getIdTutor()).forEach(a ->
                sesiones.addAll(sesionService.buscarPorAsignacion(a.getIdAsignacion()))
            );
            model.addAttribute("soloVista", true);
            model.addAttribute("idTutorSeleccionado", tutor.getIdTutor());
            model.addAttribute("sesiones", sesiones);
            if (idSesion != null) {
                model.addAttribute("idSesionSeleccionada", idSesion);
                model.addAttribute("reportes", reporteService.buscarPorSesion(idSesion));
            }
            return "reporte/datosReporte";
        }*/
        
        if (esTutor) {
            tsp.itch.model.Tutor tutor = tutorService.buscarPorUsername(username);
            System.out.println("=== TUTOR: " + tutor);
            model.addAttribute("nombreTutor", tutor.getNombre() + " " + tutor.getApellido()); // agrega esto
            List<SesionTutoria> sesiones = new java.util.ArrayList<>();
            asignacionService.buscarPorTutor(tutor.getIdTutor()).forEach(a ->
                sesiones.addAll(sesionService.buscarPorAsignacion(a.getIdAsignacion()))
            );
            System.out.println("=== SESIONES: " + sesiones.size());
           /* model.addAttribute("soloVista", true);
            model.addAttribute("idTutorSeleccionado", tutor.getIdTutor());*/
            model.addAttribute("idTutorSeleccionado", tutor.getIdTutor());
            model.addAttribute("sesiones", sesiones);
            if (idSesion != null) {
                model.addAttribute("idSesionSeleccionada", idSesion);
                model.addAttribute("reportes", reporteService.buscarPorSesion(idSesion));
            }
            return "reporte/datosReporte";
        }

        /*if (esCoordinador) {
            CoordinadorCarrera coordinador = coordinadorService.buscarPorUsername(username);
            model.addAttribute("reportes", reporteService.buscarPorCarrera(
                coordinador.getCarrera().getIdCarrera()));
            model.addAttribute("soloVista", true);
            return "reporte/datosReporte";
        }*/
        
        if (esCoordinador) {
            CoordinadorCarrera coordinador = coordinadorService.buscarPorUsername(username);
            /*model.addAttribute("soloVista", true);*/
            model.addAttribute("esCoordinador", true);
           
            /*model.addAttribute("tutores", tutorService.buscarPorCarrera(
                coordinador.getCarrera().getIdCarrera()));*/
            
            model.addAttribute("tutores", tutorService.buscarActivosPorCarrera(
            	    coordinador.getCarrera().getIdCarrera()));
            if (idTutor != null) {
                model.addAttribute("idTutorSeleccionado", idTutor);
                List<SesionTutoria> sesiones = new java.util.ArrayList<>();
                asignacionService.buscarPorTutor(idTutor).forEach(a ->
                    sesiones.addAll(sesionService.buscarPorAsignacion(a.getIdAsignacion()))
                );
                model.addAttribute("sesiones", sesiones);
            }
            if (idSesion != null) {
                model.addAttribute("idSesionSeleccionada", idSesion);
                model.addAttribute("reportes", reporteService.buscarPorSesion(idSesion));
            }
            return "reporte/datosReporte";
        }

        // Admin ve todo con filtros
       // model.addAttribute("tutores", tutorService.buscarTodos());
        model.addAttribute("tutores", tutorService.buscarActivos());
        if (idTutor != null) {
            model.addAttribute("idTutorSeleccionado", idTutor);
            List<SesionTutoria> sesiones = new java.util.ArrayList<>();
            asignacionService.buscarPorTutor(idTutor).forEach(a ->
                sesiones.addAll(sesionService.buscarPorAsignacion(a.getIdAsignacion()))
            );
            model.addAttribute("sesiones", sesiones);
        }
        if (idSesion != null) {
            model.addAttribute("idSesionSeleccionada", idSesion);
            model.addAttribute("reportes", reporteService.buscarPorSesion(idSesion));
        }

        return "reporte/datosReporte";
    }
    

    // ===================== FORMULARIO NUEVO =====================
    @GetMapping("/reportes/nuevo")
    public String mostrarFormulario(
            @RequestParam(required = false) Integer idTutor,
            @RequestParam(required = false) Integer idSesion,
            Model model) {

        ReporteTutor reporte = new ReporteTutor();

        // Si viene idSesion preseleccionado desde el listado
        if (idSesion != null) {
            SesionTutoria sesion = sesionService.buscarPorId(idSesion);
            reporte.setSesion(sesion);
        }

        model.addAttribute("reporte", reporte);
        model.addAttribute("sesiones", sesionService.buscarTodos());
        model.addAttribute("idTutorSeleccionado", idTutor);
        model.addAttribute("idSesionSeleccionada", idSesion);
        return "reporte/formReporte";
    }

    // ===================== GUARDAR =====================
    @PostMapping("/reportes/guardar")
    public String guardarReporte(
            ReporteTutor reporte,
            @RequestParam(required = false) Integer idTutor,
            @RequestParam(required = false) Integer idSesion,
            RedirectAttributes attributes) {

        reporteService.guardar(reporte);
        attributes.addFlashAttribute("msg", "Reporte guardado correctamente");

        // Regresar al listado con el contexto del tutor y sesión
        String redirect = "/reporte/reportes";
        if (idTutor != null && idSesion != null) {
            redirect += "?idTutor=" + idTutor + "&idSesion=" + idSesion;
        }
        return "redirect:" + redirect;
    }

    // ===================== VER DETALLE =====================
    @GetMapping("/ver/{id}")
    public String verDetalle(@PathVariable("id") int id, Model model) {
        ReporteTutor reporte = reporteService.buscarPorId(id);
        model.addAttribute("reporte", reporte);
        return "reporte/detalleReporte";
    }

    // ===================== EDITAR =====================
    @GetMapping("/editar/{id}")
    public String editarReporte(@PathVariable("id") int id, Model model) {
        ReporteTutor reporte = reporteService.buscarPorId(id);
        model.addAttribute("reporte", reporte);
        model.addAttribute("sesiones", sesionService.buscarTodos());
        return "reporte/formReporte";
    }

    // ===================== ELIMINAR =====================
    @GetMapping("/eliminar")
    public String eliminarReporte(@RequestParam Integer id,
            RedirectAttributes attributes) {
        reporteService.eliminar(id);
        attributes.addFlashAttribute("msg", "Reporte eliminado correctamente");
        return "redirect:/reporte/reportes";
    }
}