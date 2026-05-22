package tsp.itch.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tsp.itch.model.Actividad;
import tsp.itch.model.Asignacion;
import tsp.itch.model.PatCarrera;
import tsp.itch.service.IAsignacionService;
import tsp.itch.service.ICarreraService;
import tsp.itch.service.IActividadService;
import tsp.itch.service.IPatCarreraService;
import tsp.itch.service.IPatService;

@Controller
public class InicioController {

    @Autowired private ICarreraService carreraService;
    @Autowired private IAsignacionService asignacionService;
    @Autowired private IActividadService actividadService;
    @Autowired private IPatCarreraService patCarreraService;
    @Autowired private IPatService patService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/inicio")
    public String inicio(
            @RequestParam(required = false) Integer idCarrera,
            @RequestParam(required = false) Integer semana,
            // Filtros PAT general
            @RequestParam(required = false) String semanaPatGeneral,
            @RequestParam(required = false) String fechaPatGeneral,
            // Filtros PAT carrera
            @RequestParam(required = false) Integer idCarreraPatCarrera,
            @RequestParam(required = false) String semanaPatCarrera,
            @RequestParam(required = false) String fechaPatCarrera,
            Model model) {

        model.addAttribute("carreras", carreraService.buscarActivas());
        model.addAttribute("todasAsignaciones", asignacionService.buscarTodas()
            .stream().filter(a -> a.getEstatus() == 1)
            .collect(Collectors.toList()));

        // Stats
        List<Asignacion> todasAsig = asignacionService.buscarTodas();
        model.addAttribute("totalTutores", todasAsig.stream()
            .filter(a -> a.getEstatus() == 1)
            .map(a -> a.getTutor().getIdTutor()).distinct().count());
        model.addAttribute("totalAsignaciones", todasAsig.stream()
            .filter(a -> a.getEstatus() == 1).count());

        // ── BÚSQUEDA TUTORES POR CARRERA ──
        if (idCarrera != null) {
            List<Asignacion> asignaciones = asignacionService.buscarPorCarrera(idCarrera);
            model.addAttribute("asignaciones", asignaciones);
            model.addAttribute("idCarreraSeleccionada", idCarrera);

            if (semana != null) {
                List<Asignacion> lista = asignacionService.buscarPorCarrera(idCarrera);
                if (!lista.isEmpty()) {
                    Asignacion primera = lista.get(0);
                    Integer idSemestre = primera.getSemestre().getIdSemestre();
                    PatCarrera patCarrera = patCarreraService.buscarPorCarreraYSemestre(idCarrera, idSemestre);
                    if (patCarrera != null) {
                        List<Actividad> todasActividades = actividadService.buscarPorPatCarrera(patCarrera.getIdPatCarrera());
                        List<Actividad> filtradas = new java.util.ArrayList<>();
                        for (Actividad a : todasActividades) {
                            if (String.valueOf(semana).equals(a.getSemana())) filtradas.add(a);
                        }
                        model.addAttribute("actividades", filtradas);
                    }
                }
                model.addAttribute("semanaSeleccionada", semana);
            }
        }

        // ── PAT GENERAL: filtrar por semana y/o fecha ──
        boolean buscarPatGeneral = (semanaPatGeneral != null && !semanaPatGeneral.isEmpty())
                                || (fechaPatGeneral != null && !fechaPatGeneral.isEmpty());
        if (buscarPatGeneral) {
            List<Actividad> todasPat = actividadService.buscarTodas()
                .stream().filter(a -> a.getPat() != null).collect(Collectors.toList());

            if (semanaPatGeneral != null && !semanaPatGeneral.isEmpty()) {
                todasPat = todasPat.stream()
                    .filter(a -> semanaPatGeneral.equals(a.getSemana()))
                    .collect(Collectors.toList());
            }
            /*if (fechaPatGeneral != null && !fechaPatGeneral.isEmpty()) {
                LocalDate fecha = LocalDate.parse(fechaPatGeneral);
                todasPat = todasPat.stream()
                    .filter(a -> a.getFecha() != null && a.getFecha().equals(fecha))
                    .collect(Collectors.toList());
            }*/
            
            if (fechaPatGeneral != null && !fechaPatGeneral.isEmpty()) {
                LocalDate fecha = LocalDate.parse(fechaPatGeneral);
                if (fecha.getDayOfWeek() == java.time.DayOfWeek.FRIDAY) {
                    todasPat = todasPat.stream()
                        .filter(a -> a.getFecha() != null && a.getFecha().equals(fecha))
                        .collect(Collectors.toList());
                } else {
                    todasPat = new java.util.ArrayList<>();
                    model.addAttribute("errorFechaPatGeneral", "Solo se permiten fechas en viernes");
                }
            }
            model.addAttribute("actividadesPatGeneral", todasPat);
            model.addAttribute("semanaPatGeneral", semanaPatGeneral);
            model.addAttribute("fechaPatGeneral", fechaPatGeneral);
        }

        // ── PAT CARRERA: filtrar por carrera, semana y/o fecha ──
        boolean buscarPatCarrera = idCarreraPatCarrera != null
                                || (semanaPatCarrera != null && !semanaPatCarrera.isEmpty())
                                || (fechaPatCarrera != null && !fechaPatCarrera.isEmpty());
        if (buscarPatCarrera) {
            List<Actividad> todasPC = actividadService.buscarTodas()
                .stream().filter(a -> a.getPatCarrera() != null).collect(Collectors.toList());

            if (idCarreraPatCarrera != null) {
                todasPC = todasPC.stream()
                    .filter(a -> a.getPatCarrera().getCarrera().getIdCarrera().equals(idCarreraPatCarrera))
                    .collect(Collectors.toList());
                model.addAttribute("idCarreraPatCarreraSeleccionada", idCarreraPatCarrera);
            }
            if (semanaPatCarrera != null && !semanaPatCarrera.isEmpty()) {
                todasPC = todasPC.stream()
                    .filter(a -> semanaPatCarrera.equals(a.getSemana()))
                    .collect(Collectors.toList());
            }
         /*   if (fechaPatCarrera != null && !fechaPatCarrera.isEmpty()) {
                LocalDate fecha = LocalDate.parse(fechaPatCarrera);
                todasPC = todasPC.stream()
                    .filter(a -> a.getFecha() != null && a.getFecha().equals(fecha))
                    .collect(Collectors.toList());
            }*/
            if (fechaPatCarrera != null && !fechaPatCarrera.isEmpty()) {
                LocalDate fecha = LocalDate.parse(fechaPatCarrera);
                if (fecha.getDayOfWeek() == java.time.DayOfWeek.FRIDAY) {
                    todasPC = todasPC.stream()
                        .filter(a -> a.getFecha() != null && a.getFecha().equals(fecha))
                        .collect(Collectors.toList());
                } else {
                    todasPC = new java.util.ArrayList<>();
                    model.addAttribute("errorFechaPatCarrera", "Solo se permiten fechas en viernes");
                }
            }
            model.addAttribute("actividadesPatCarrera", todasPC);
            model.addAttribute("semanaPatCarrera", semanaPatCarrera);
            model.addAttribute("fechaPatCarrera", fechaPatCarrera);
        }

        return "inicio";
    }
}