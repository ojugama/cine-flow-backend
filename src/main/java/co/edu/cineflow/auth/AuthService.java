package co.edu.cineflow.auth;

import co.edu.cineflow.common.exception.BusinessException;
import co.edu.cineflow.security.JwtUtil;
import co.edu.cineflow.usuarios.UsuarioEntity;
import co.edu.cineflow.usuarios.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponseDTO login(AuthLoginDTO in) {
        UsuarioEntity usuario = usuarioRepository.findByEmailAndIsActivoTrue(in.getEmail())
                .orElseThrow(() -> new BusinessException("Las credenciales son inválidas."));

        if (!passwordEncoder.matches(in.getPassword(), usuario.getPassword())) {
            throw new BusinessException("Las credenciales son inválidas.");
        }

        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol());

        return new AuthResponseDTO(token);
    }
}
