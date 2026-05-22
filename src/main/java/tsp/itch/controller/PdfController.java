package tsp.itch.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import tsp.itch.GeneradorPDF;
import tsp.itch.model.*;
import tsp.itch.service.*;

import java.util.List;

@Controller
@RequestMapping("/pdf")
public class PdfController {

    @Autowired private IPatService patService;
    @Autowired private IPatCarreraService patCarreraService;
    @Autowired private IActividadService actividadService;
    @Autowired private IAsignacionService asignacionService;
    
    @Autowired private IAsignacionTutoradoService asignacionTutoradoService;
    @Autowired private ISesionTutoriaService sesionService;
    @Autowired private IAsistenciaService asistenciaService;

    @GetMapping("/pat/{id}")
    public void imprimirPat(@PathVariable Integer id,
                            HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "inline; filename=PAT_" + id + ".pdf");

        Pat pat = patService.buscarPorId(id);
        List<Actividad> actividades = actividadService.buscarPorPat(id);

        new GeneradorPDF().generarPat(pat, actividades, response);
    }

    @GetMapping("/patcarrera/{id}")
    public void imprimirPatCarrera(@PathVariable Integer id,
                                   HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "inline; filename=PAT_Carrera_" + id + ".pdf");

        PatCarrera pc = patCarreraService.buscarPorId(id);
        List<Actividad> actividades = actividadService.buscarPorPatCarrera(id);

        new GeneradorPDF().generarPatCarrera(pc, actividades, response);
    }

    @GetMapping("/tutorados")
    public void imprimirTutorados(HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "inline; filename=Lista_Tutorados.pdf");

        List<Asignacion> asignaciones = asignacionService.buscarTodas();

        new GeneradorPDF().generarListaTutorados(asignaciones, response);
    }
    
    @GetMapping("/tutorados/{id}")
    public void imprimirTutoradosPorAsignacion(@PathVariable Integer id,
                                                HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "inline; filename=Tutorados_" + id + ".pdf");

        Asignacion asignacion = asignacionService.buscarPorId(id);

        new GeneradorPDF().generarListaTutorados(
                java.util.List.of(asignacion), response);
    }
    
    @GetMapping("/formatoAsistencia/{idAsignacion}")
    public void imprimirFormatoAsistencia(@PathVariable Integer idAsignacion,
                                           HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "inline; filename=Formato_Asistencia_" + idAsignacion + ".pdf");

        Asignacion asignacion = asignacionService.buscarPorId(idAsignacion);
        List<SesionTutoria> sesiones = sesionService.buscarPorAsignacion(idAsignacion);
        List<AsignacionTutorado> tutorados = asignacionTutoradoService.buscarPorAsignacion(idAsignacion);
        List<Asistencia> todasAsistencias = asistenciaService.buscarTodos();

        new GeneradorPDF().generarFormatoAsistencia(
                asignacion, sesiones, tutorados, todasAsistencias, response);
    }
    
    @GetMapping("/carnets/{idAsignacion}")
    public void imprimirCarnets(@PathVariable Integer idAsignacion,
                                 HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "inline; filename=Carnets_" + idAsignacion + ".pdf");

        Asignacion asignacion = asignacionService.buscarPorId(idAsignacion);
        List<SesionTutoria> sesiones = sesionService.buscarPorAsignacion(idAsignacion);
        List<AsignacionTutorado> tutorados = asignacionTutoradoService.buscarPorAsignacion(idAsignacion);

        new GeneradorPDF().generarCarnets(asignacion, sesiones, tutorados, response);
    }
    
    @Autowired private IConstanciaService constanciaService;

    @GetMapping("/constanciaAlumno/{idConstancia}")
    public void imprimirConstanciaAlumno(@PathVariable Integer idConstancia,
                                          HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "inline; filename=Constancia_Alumno_" + idConstancia + ".pdf");

        Constancia constancia = constanciaService.buscarPorId(idConstancia);
        new GeneradorPDF().generarConstanciaAlumno(constancia, response);
    }
    
    @GetMapping("/constanciaTutor/{idAsignacion}")
    public void imprimirConstanciaTutor(@PathVariable Integer idAsignacion,
                                         HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "inline; filename=Constancia_Tutor_" + idAsignacion + ".pdf");

        Asignacion asignacion = asignacionService.buscarPorId(idAsignacion);
        List<AsignacionTutorado> tutorados = asignacionTutoradoService.buscarPorAsignacion(idAsignacion);

        // Contar cuántos acreditaron el 80%
        int totalTutorados = tutorados.size();
        int acreditados = 0;
        for (AsignacionTutorado at : tutorados) {
            double pct = asistenciaService.calcularPorcentaje(
                    at.getTutorado().getIdTutorado());
            if (pct >= 80) acreditados++;
        }

        new GeneradorPDF().generarConstanciaTutor(
                asignacion, totalTutorados, acreditados, response);
    }
    
    @GetMapping("/paseLista/{idSesion}")
    public void imprimirPaseLista(@PathVariable Integer idSesion,
                                   HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "inline; filename=Pase_Lista_Sesion_" + idSesion + ".pdf");

        SesionTutoria sesion = sesionService.buscarPorId(idSesion);
        Asignacion asignacion = sesion.getAsignacion();
        List<AsignacionTutorado> tutorados = asignacionTutoradoService
                .buscarPorAsignacion(asignacion.getIdAsignacion());
        List<Asistencia> asistencias = asistenciaService.buscarPorSesion(idSesion);

        new GeneradorPDF().generarPaseLista(asignacion, sesion, tutorados, asistencias, response);
    }
    
    @GetMapping("/carnetIndividual/{idAsignacion}/{idTutorado}")
    public void imprimirCarnetIndividual(@PathVariable Integer idAsignacion,
                                          @PathVariable Integer idTutorado,
                                          HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition",
                "inline; filename=Carnet_" + idTutorado + ".pdf");

        Asignacion asignacion = asignacionService.buscarPorId(idAsignacion);
        List<SesionTutoria> sesiones = sesionService.buscarPorAsignacion(idAsignacion);
        List<AsignacionTutorado> tutorados = asignacionTutoradoService.buscarPorAsignacion(idAsignacion);

        AsignacionTutorado at = tutorados.stream()
            .filter(a -> a.getTutorado().getIdTutorado().equals(idTutorado))
            .findFirst().orElse(null);

        if (at == null) { response.sendError(404); return; }

        new GeneradorPDF().generarCarnetIndividual(asignacion, sesiones, at, response);
    }
}