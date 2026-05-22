package tsp.itch.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import tsp.itch.model.Usuario;
import tsp.itch.service.IRolService;
import tsp.itch.service.ITutorService;
import tsp.itch.service.IUsuarioService;

@RequestMapping("/usuario")
@Controller
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IRolService rolService;
    
    @Autowired
    private ITutorService tutorService;

    @Value("${app.upload.dir.usuario}")
    private String dirUsuario;
    
    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @GetMapping("/usuarios")
    public String mostrarUsuarios(Model model) {
        List<Usuario> lista = usuarioService.buscarTodos();
        model.addAttribute("usuarios", lista);
        //..
        model.addAttribute("tutores", tutorService.buscarTodos());
        return "usuario/datosUsuario";
    }

    @GetMapping("/usuarios/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolService.buscarTodos());
        return "usuario/formUsuario";
    }

   /* @PostMapping("/usuarios/guardar")
    public String guardarUsuario(Usuario usuario,
            @RequestParam("fotoArchivo") MultipartFile fotoArchivo,
            RedirectAttributes attributes) throws Exception {

        if (!fotoArchivo.isEmpty()) {
            String nombreFoto = fotoArchivo.getOriginalFilename();
            Path rutaFoto = Paths.get(dirUsuario + nombreFoto);
            Files.copy(fotoArchivo.getInputStream(), rutaFoto);
            usuario.setFoto(nombreFoto);
        }

        if (usuario.getIdUsuario() == null) {
            usuario.setEstatus(1);
        }

        usuarioService.guardar(usuario);
        attributes.addFlashAttribute("msg", "Usuario guardado correctamente");
        return "redirect:/usuario/usuarios";
    }*/
    
    @PostMapping("/usuarios/guardar")
    public String guardarUsuario(Usuario usuario,
            @RequestParam("fotoArchivo") MultipartFile fotoArchivo,
            RedirectAttributes attributes) throws Exception {

        // Validar número de trabajador duplicado
        Usuario existente = usuarioService.buscarPorNumeroTrabajador(usuario.getNumeroTrabajador());
        if (existente != null && !existente.getIdUsuario().equals(usuario.getIdUsuario())) {
            attributes.addFlashAttribute("error", "⚠ Ya existe un usuario con ese número de trabajador.");
            if (usuario.getIdUsuario() != null) {
                return "redirect:/usuario/editar/" + usuario.getIdUsuario();
            }
            return "redirect:/usuario/usuarios/nuevo";
        }

        if (!fotoArchivo.isEmpty()) {
            String nombreFoto = fotoArchivo.getOriginalFilename();
            Path rutaFoto = Paths.get(dirUsuario + nombreFoto);
            //Files.copy(fotoArchivo.getInputStream(), rutaFoto);
            Files.copy(fotoArchivo.getInputStream(), rutaFoto, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            usuario.setFoto(nombreFoto);
        }

       /* if (usuario.getIdUsuario() == null) {
            usuario.setEstatus(1);
        }

        usuarioService.guardar(usuario);*/
        
        if (usuario.getIdUsuario() == null) {
            usuario.setEstatus(1);
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        } else {
            Usuario actual = usuarioService.buscarPorId(usuario.getIdUsuario());
            if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
                usuario.setPassword(actual.getPassword());
            } else {
                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }
        }

        usuarioService.guardar(usuario);
        
        
        attributes.addFlashAttribute("msg", "Usuario guardado correctamente");
        return "redirect:/usuario/usuarios";
    }

    @GetMapping("/ver/{id}")
    public String verDetalle(@PathVariable("id") int id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id);
        model.addAttribute("usuario", usuario);
        return "usuario/detalleUsuario";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable("id") int id, Model model) {
        Usuario usuario = usuarioService.buscarPorId(id);
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolService.buscarTodos());
        return "usuario/formUsuario";
    }

    @GetMapping("/eliminar")
    public String eliminarUsuario(@RequestParam Integer id, RedirectAttributes attributes) {
        Usuario usuario = usuarioService.buscarPorId(id);
        usuario.setEstatus(0);
        usuarioService.guardar(usuario);
        attributes.addFlashAttribute("msg", "Usuario eliminado correctamente");
        return "redirect:/usuario/usuarios";
    }
    
    @GetMapping("/activar")
    public String activarUsuario(@RequestParam Integer id, RedirectAttributes attributes) {
        Usuario usuario = usuarioService.buscarPorId(id);
        usuario.setEstatus(1);
        usuarioService.guardar(usuario);
        attributes.addFlashAttribute("msg", "Usuario activado correctamente");
        return "redirect:/usuario/usuarios";
    }
}