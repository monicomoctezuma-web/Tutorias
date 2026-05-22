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
import tsp.itch.model.Pat;
import tsp.itch.model.SesionTutoria;
import tsp.itch.service.IActividadService;
import tsp.itch.service.IPatCarreraService;
import tsp.itch.service.IPatService;
import tsp.itch.service.ISemestreService;
import tsp.itch.service.ISesionTutoriaService;

@RequestMapping("/pat")
@Controller
public class PatController {

    @Autowired
    private IPatService patService;
    @Autowired
    private ISemestreService semestreService;
    @Autowired
    private IActividadService actividadService;
    @Autowired
    private IPatCarreraService patCarreraService;
    
    @Autowired
    private ISesionTutoriaService sesionService;

    @GetMapping("/pats")
    public String mostrarPats(Model model) {
        List<Pat> lista = patService.buscarTodas();
        model.addAttribute("pats", lista);
        return "pat/datosPat";
    }

    @GetMapping("/pats/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("pat", new Pat());
        model.addAttribute("semestres", semestreService.buscarActivos());
        //  model.addAttribute("semestres", semestreService.buscarTodos());
        return "pat/formPat";
    }

    @PostMapping("/pats/guardar")
    public String guardarPat(
            Pat pat,
            @RequestParam(value = "actNombre[]", required = false) List<String> nombres,
            @RequestParam(value = "actDescripcion[]", required = false) List<String> descripciones,
            @RequestParam(value = "actSemana[]", required = false) List<String> semanas,
            @RequestParam(value = "actFecha[]", required = false) List<String> fechas,
            RedirectAttributes attributes) {

        if (pat.getIdPat() == null) {
            pat.setEstatus(1);
        }
        patService.guardar(pat);

        // Guardar actividades ligadas al PatCarrera
     /*   if (nombres != null) {
            for (int i = 0; i < nombres.size(); i++) {
                String nombre = nombres.get(i);
                if (nombre != null && !nombre.trim().isEmpty()) {
                    Actividad actividad = new Actividad();
                    actividad.setNombre(nombre);
                    actividad.setDescripcion(descripciones != null ? descripciones.get(i) : "");
                    actividad.setSemana(semanas != null ? semanas.get(i) : "");
                    actividad.setEstatus(1);
                    actividad.setPat(pat); // ← AGREGAR ESTA LÍNEA
                    actividadService.guardar(actividad);
                }
            }
        }*/
        // Borrar actividades anteriores para evitar duplicados
        /*if (pat.getIdPat() != null) {
            List<Actividad> anteriores = actividadService.buscarPorPat(pat.getIdPat());
            for (Actividad a : anteriores) {
                actividadService.eliminar(a.getIdActividad());
            }
        }*/
        if (pat.getIdPat() != null) {
            List<Actividad> anteriores = actividadService.buscarPorPat(pat.getIdPat());
            for (Actividad a : anteriores) {
                List<SesionTutoria> sesiones = sesionService.buscarPorActividad(a.getIdActividad());
                for (SesionTutoria s : sesiones) {
                    s.setActividad(null);
                    sesionService.guardar(s);
                }
                actividadService.eliminar(a.getIdActividad());
            }
        }

        // Guardar actividades nuevas
        if (nombres != null) {
            for (int i = 0; i < nombres.size(); i++) {
                String nombre = nombres.get(i);
                if (nombre != null && !nombre.trim().isEmpty()) {
                    Actividad actividad = new Actividad();
                    actividad.setNombre(nombre);
                    actividad.setDescripcion(descripciones != null ? descripciones.get(i) : "");
                    actividad.setSemana(semanas != null ? semanas.get(i) : "");
                    actividad.setFecha(fechas != null && fechas.get(i) != null && !fechas.get(i).isEmpty() 
                    	    ? java.time.LocalDate.parse(fechas.get(i)) : null);
                    actividad.setEstatus(1);
                    actividad.setPat(pat);
                    actividadService.guardar(actividad);
                }
            }
        }
        
        // ← AGREGAR AQUÍ: Sincronizar con PAT Carrera
       /* if (pat.getIdPat() != null) {
            List<tsp.itch.model.PatCarrera> patCarreras = patCarreraService.buscarPorPat(pat.getIdPat());
            for (tsp.itch.model.PatCarrera pc : patCarreras) {
                List<Actividad> anterioresPC = actividadService.buscarPorPatCarrera(pc.getIdPatCarrera());
                for (Actividad a : anterioresPC) {
                    actividadService.eliminar(a.getIdActividad());
                }*/
                 
        if (pat.getIdPat() != null) {
            List<tsp.itch.model.PatCarrera> patCarreras = patCarreraService.buscarPorPat(pat.getIdPat());
            for (tsp.itch.model.PatCarrera pc : patCarreras) {
                List<Actividad> anterioresPC = actividadService.buscarPorPatCarrera(pc.getIdPatCarrera());
                for (Actividad a : anterioresPC) {
                    List<SesionTutoria> sesionesPC = sesionService.buscarPorActividad(a.getIdActividad());
                    for (SesionTutoria s : sesionesPC) {
                        s.setActividad(null);
                        sesionService.guardar(s);
                    }
                    actividadService.eliminar(a.getIdActividad());
                }
        
                if (nombres != null) {
                    for (int i = 0; i < nombres.size(); i++) {
                        String nombre = nombres.get(i);
                        if (nombre != null && !nombre.trim().isEmpty()) {
                            Actividad actPC = new Actividad();
                            actPC.setNombre(nombre);
                            actPC.setDescripcion(descripciones != null ? descripciones.get(i) : "");
                            actPC.setSemana(semanas != null ? semanas.get(i) : "");
                            actPC.setFecha(fechas != null && fechas.get(i) != null && !fechas.get(i).isEmpty()
                                ? java.time.LocalDate.parse(fechas.get(i)) : null);
                            actPC.setPatCarrera(pc);
                            actPC.setEstatus(1);
                            actividadService.guardar(actPC);
                        }
                    }
                }
            }
        }
        attributes.addFlashAttribute("msg", "PAT guardado correctamente");
        return "redirect:/pat/pats";
    }

    @GetMapping("/ver/{id}")
    public String verDetalle(@PathVariable("id") int id, Model model) {
        Pat pat = patService.buscarPorId(id);
        List<Actividad> actividades = actividadService.buscarPorPat(id); // NUEVO
        model.addAttribute("pat", pat);
        model.addAttribute("actividades", actividades); // NUEVO
        return "pat/detallePat";
    }
    
    @GetMapping("/actividadesPat/{idPat}")
    @ResponseBody
    public List<Actividad> obtenerActividadesPat(@PathVariable Integer idPat) {
        return actividadService.buscarPorPat(idPat);
    }

    @GetMapping("/editar/{id}")
    public String editarPat(@PathVariable("id") int id, Model model) {
        Pat pat = patService.buscarPorId(id);
        model.addAttribute("pat", pat);
        model.addAttribute("semestres", semestreService.buscarActivos());
        // model.addAttribute("semestres", semestreService.buscarTodos());
        return "pat/formPat";
    }

  /*  @GetMapping("/eliminar")
    public String eliminarPat(@RequestParam Integer id, RedirectAttributes attributes) {
        Pat pat = patService.buscarPorId(id);
        pat.setEstatus(0);
        patService.guardar(pat);
        attributes.addFlashAttribute("msg", "PAT eliminado correctamente");
        return "redirect:/pat/pats";
    }*/
    
    @GetMapping("/eliminar")
    public String eliminarPat(@RequestParam Integer id, RedirectAttributes attributes) {
        Pat pat = patService.buscarPorId(id);
        pat.setEstatus(0);
        patService.guardar(pat);

        // Desactivar también todos los PAT carrera relacionados
        List<tsp.itch.model.PatCarrera> patCarreras = patCarreraService.buscarPorPat(id);
        for (tsp.itch.model.PatCarrera pc : patCarreras) {
            pc.setEstatus(0);
            patCarreraService.guardar(pc);
        }

        attributes.addFlashAttribute("msg", "PAT eliminado correctamente");
        return "redirect:/pat/pats";
    }
    @GetMapping("/buscarPorFecha")
    public String buscarPorFecha(
            @RequestParam(required = false) String fecha,
            Model model) {
        List<Pat> lista = patService.buscarTodas();
        model.addAttribute("pats", lista);
        if (fecha != null && !fecha.isEmpty()) {
            java.time.LocalDate localDate = java.time.LocalDate.parse(fecha);
            model.addAttribute("actividades", actividadService.buscarPorFecha(localDate));
            model.addAttribute("fechaBuscada", fecha);
        }
        return "pat/datosPat";
    }
    
    @GetMapping("/activar")
    public String activarPat(@RequestParam Integer id, RedirectAttributes attributes) {
        Pat pat = patService.buscarPorId(id);
        pat.setEstatus(1);
        patService.guardar(pat);

        // Reactivar también todos los PAT carrera relacionados
        List<tsp.itch.model.PatCarrera> patCarreras = patCarreraService.buscarPorPat(id);
        for (tsp.itch.model.PatCarrera pc : patCarreras) {
            pc.setEstatus(1);
            patCarreraService.guardar(pc);
        }

        attributes.addFlashAttribute("msg", "PAT activado correctamente");
        return "redirect:/pat/pats";
    }
}