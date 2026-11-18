package co.edu.cineflow.config;

import co.edu.cineflow.common.mongo.SequenceService;
import co.edu.cineflow.usuarios.UsuarioEntity;
import co.edu.cineflow.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("!test")
public class AdminSeeder {
    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    @Bean
    public CommandLineRunner initAdmin(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                                       SequenceService sequenceService) {
        return args -> {
            if (!usuarioRepository.existsByEmail("admin@cineflow.co")) {
                UsuarioEntity admin = new UsuarioEntity();
                admin.setId(sequenceService.next("usuarios"));
                admin.setEmail("admin@cineflow.co");
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setRol("ADMIN");
                admin.setNombres("Administrador");
                admin.setApellidos("Principal");

                usuarioRepository.save(admin);
            } else {
                UsuarioEntity admin = usuarioRepository.findByEmail("admin@cineflow.co");
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setIsActivo(true);
                admin.setRol("ADMIN");

                usuarioRepository.save(admin);
            }
        };
    }
}
