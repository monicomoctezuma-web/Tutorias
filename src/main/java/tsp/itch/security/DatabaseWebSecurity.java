package tsp.itch.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import tsp.itch.model.Tutor;
import tsp.itch.model.Usuario;
import tsp.itch.repository.TutorRepository;
import tsp.itch.repository.UsuarioRepository;
import java.util.List;

@Configuration
@EnableWebSecurity
public class DatabaseWebSecurity implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario != null) {
            return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombre())))
                .build();
        }
        Tutor tutor = tutorRepository.findByUsername(username);
        if (tutor != null) {
            return User.builder()
                .username(tutor.getUsername())
                .password(tutor.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority("ROLE_TUTOR")))
                .build();
        }
        throw new UsernameNotFoundException("Usuario no encontrado: " + username);
    }

   /* @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }*/
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder =
            http.getSharedObject(AuthenticationManagerBuilder.class);
        builder
            .userDetailsService(this)
            .passwordEncoder(passwordEncoder());
        return builder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/login").permitAll()
               // .requestMatchers("/inicio/**").permitAll()
                .requestMatchers("/inicio/**").permitAll()
                .requestMatchers("/inicio").permitAll()
                .requestMatchers("/").permitAll()
                
                .requestMatchers("/fotos/**").permitAll()
                .requestMatchers("/tutor/inicio").hasRole("TUTOR")
               // .requestMatchers("/tutor/inicio").hasAnyRole("TUTOR", "ADMINISTRADOR")
                .requestMatchers("/coordinador/inicioCoordinadorInstitucional").hasRole("COORDINADOR_INSTITUCIONAL")
                .requestMatchers("/coordinador/inicioCoordinadorCarrera").hasRole("COORDINADOR_CARRERA")
              //  .requestMatchers("/asistencia/**").hasAnyRole("TUTOR", "ADMINISTRADOR")
               // .requestMatchers("/asistencia/**").hasAnyRole("TUTOR", "ADMINISTRADOR", "COORDINADOR_CARRERA", "JEFE_ACADEMICO")
                
               // .requestMatchers("/asistencia/**").hasAnyRole("TUTOR", "ADMINISTRADOR", "COORDINADOR_CARRERA", "JEFE_ACADEMICO", "DESARROLLO_ACADEMICO")
                .requestMatchers("/asistencia/**").hasAnyRole("TUTOR", "ADMINISTRADOR", "COORDINADOR_CARRERA", "DESARROLLO_ACADEMICO")
                .requestMatchers("/sesion/tutor").hasAnyRole("TUTOR", "ADMINISTRADOR")
                //.requestMatchers("/usuario/**").hasRole("ADMINISTRADOR")
                .requestMatchers("/usuario/**").hasAnyRole("ADMINISTRADOR", "DESARROLLO_ACADEMICO")
               // .requestMatchers("/rol/**").hasRole("ADMINISTRADOR")
                //.requestMatchers("/carrera/**").hasRole("ADMINISTRADOR")
                
                .requestMatchers("/rol/**").hasAnyRole("ADMINISTRADOR", "DESARROLLO_ACADEMICO")
                .requestMatchers("/carrera/**").hasAnyRole("ADMINISTRADOR", "DESARROLLO_ACADEMICO")
                
                .requestMatchers("/semestre/**").hasAnyRole("ADMINISTRADOR", "DESARROLLO_ACADEMICO")
               // .requestMatchers("/semestre/**").hasRole("ADMINISTRADOR")
                //.requestMatchers("/coordinador/**").hasRole("ADMINISTRADOR")
               // .requestMatchers("/coordinador/**").hasAnyRole("ADMINISTRADOR", "COORDINADOR_INSTITUCIONAL")
               // .requestMatchers("/coordinador/**").hasAnyRole("ADMINISTRADOR", "COORDINADOR_INSTITUCIONAL", "COORDINADOR_CARRERA")
                
                //.requestMatchers("/coordinador/**").hasAnyRole("ADMINISTRADOR", "COORDINADOR_INSTITUCIONAL", "COORDINADOR_CARRERA", "JEFE_ACADEMICO")
                .requestMatchers("/coordinador/**").hasAnyRole("ADMINISTRADOR", "COORDINADOR_INSTITUCIONAL", "COORDINADOR_CARRERA", "JEFE_ACADEMICO", "DESARROLLO_ACADEMICO")
                //.requestMatchers("/jefe/**").hasAnyRole("ADMINISTRADOR", "JEFE_ACADEMICO")
                .requestMatchers("/jefe/**").hasAnyRole("ADMINISTRADOR", "JEFE_ACADEMICO", "COORDINADOR_INSTITUCIONAL")
                /*.requestMatchers("/tutor/**").hasRole("ADMINISTRADOR")
                .requestMatchers("/tutorado/**").hasRole("ADMINISTRADOR")*/
                /*.requestMatchers("/asignacion/**").hasRole("ADMINISTRADOR")
                .requestMatchers("/reporte/**").hasRole("ADMINISTRADOR")*/
                .requestMatchers("/tutor/**").hasAnyRole("ADMINISTRADOR", "DESARROLLO_ACADEMICO")
                .requestMatchers("/tutorado/**").hasAnyRole("ADMINISTRADOR", "DESARROLLO_ACADEMICO")
                
                .requestMatchers("/porcentaje/**").hasAnyRole("ADMINISTRADOR", "COORDINADOR_CARRERA")
                .requestMatchers("/asignacion/**").hasAnyRole("ADMINISTRADOR", "COORDINADOR_CARRERA", "JEFE_ACADEMICO")
                .requestMatchers("/reporte/**").hasAnyRole("ADMINISTRADOR", "COORDINADOR_CARRERA", "TUTOR")
                //.requestMatchers("/pdf/**").hasAnyRole("ADMINISTRADOR", "COORDINADOR_CARRERA", "TUTOR")
               // .requestMatchers("/pdf/**").hasAnyRole("ADMINISTRADOR", "COORDINADOR_CARRERA", "TUTOR", "DESARROLLO_ACADEMICO")
                .requestMatchers("/pdf/**").hasAnyRole("ADMINISTRADOR", "COORDINADOR_CARRERA", "TUTOR", "DESARROLLO_ACADEMICO", "JEFE_ACADEMICO")
                .requestMatchers("/necesidad/**").hasAnyRole("ADMINISTRADOR", "TUTOR", "COORDINADOR_CARRERA")
                //.requestMatchers("/necesidad/**").hasRole("ADMINISTRADOR")
               // .requestMatchers("/constancia/**").hasRole("ADMINISTRADOR")
               // .requestMatchers("/constancia/**").hasAnyRole("ADMINISTRADOR", "DESARROLLO_ACADEMICO")
              //  .requestMatchers("/constancia/**").hasAnyRole("ADMINISTRADOR", "DESARROLLO_ACADEMICO", "JEFE_ACADEMICO")
               // .requestMatchers("/pat/**").hasRole("ADMINISTRADOR")
               // .requestMatchers("/patcarrera/**").hasRole("ADMINISTRADOR")
                .requestMatchers("/constancia/**").hasAnyRole("ADMINISTRADOR", "DESARROLLO_ACADEMICO", "JEFE_ACADEMICO", "COORDINADOR_CARRERA")
                .requestMatchers("/pat/**").hasAnyRole("ADMINISTRADOR", "DESARROLLO_ACADEMICO")
                .requestMatchers("/patcarrera/**").hasAnyRole("ADMINISTRADOR", "COORDINADOR_CARRERA", "JEFE_ACADEMICO")
                
                .requestMatchers("/desarrollo/**").hasAnyRole("ADMINISTRADOR", "DESARROLLO_ACADEMICO")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler((request, response, authentication) -> {
                    String rol = authentication.getAuthorities().iterator().next().getAuthority();
                 /*   if (rol.equals("ROLE_TUTOR")) {
                        response.sendRedirect("/tutor/inicio");
                    } else {
                        response.sendRedirect("/");
                    }*/
                    
                    if (rol.equals("ROLE_TUTOR")) {
                        response.sendRedirect("/tutor/inicio");
                    } else if (rol.equals("ROLE_COORDINADOR_INSTITUCIONAL")) {
                        response.sendRedirect("/coordinador/inicioCoordinadorInstitucional");
                    } else if (rol.equals("ROLE_COORDINADOR_CARRERA")) {
                        response.sendRedirect("/coordinador/inicioCoordinadorCarrera");
                        
                    } else if (rol.equals("ROLE_JEFE_ACADEMICO")) {
                        response.sendRedirect("/coordinador/inicioJefeAcademico");
                        
                    } else if (rol.equals("ROLE_DESARROLLO_ACADEMICO")) {
                        response.sendRedirect("/coordinador/inicioDesarrolloAcademico");
                    } else {
                       // response.sendRedirect("/");
                    	  response.sendRedirect("/index");
                    }
                })
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }
}