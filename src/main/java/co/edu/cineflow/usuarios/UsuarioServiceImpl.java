package co.edu.cineflow.usuarios;

import co.edu.cineflow.common.exception.BusinessException;
import co.edu.cineflow.common.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UsuarioEntity create(UsuarioEntity in) {
        if (usuarioRepository.existsByEmail(in.getEmail())) {
            throw new BusinessException("El email ya se encuentra registrado.");
        }

        in.setPassword(passwordEncoder.encode(in.getPassword()));

        in.setRol("CLIENTE");

        return usuarioRepository.save(in);
    }

    @Override
    public UsuarioEntity findById(Long id) {
        return usuarioRepository.findByIdAndIsActivoTrue(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID " + id + "."));
    }

    @Override
    public Page<UsuarioEntity> findAll(Pageable pageable) {
        return usuarioRepository.findAllByIsActivoTrue(pageable);
    }

    @Override
    @Transactional
    public UsuarioEntity update(Long id, UsuarioEntity in) {
        UsuarioEntity existingUsuario = findById(id);

        if (usuarioRepository.existsByEmailAndIdNot(in.getEmail(), id)) {
            throw new BusinessException("El email ya se encuentra registrado.");
        }

        existingUsuario.setEmail(in.getEmail());
        existingUsuario.setNombres(in.getNombres());
        existingUsuario.setApellidos(in.getApellidos());

        return usuarioRepository.save(existingUsuario);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        UsuarioEntity existingUsuario = findById(id);

        if ("ADMIN".equals(existingUsuario.getRol())) {
            throw new BusinessException("No se puede desactivar una cuenta de otro administrador.");
        }

        existingUsuario.setIsActivo(false);

        usuarioRepository.save(existingUsuario);
    }

    @Override
    public UsuarioEntity findByEmail(String email) {
        return usuarioRepository.findByEmailAndIsActivoTrue(email)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con email " + email + "."));
    }

    @Override
    @Transactional
    public UsuarioEntity update(String email, UsuarioEntity in) {
        UsuarioEntity usuario = findByEmail(email);

        if (usuarioRepository.existsByEmailAndIdNot(in.getEmail(), usuario.getId())) {
            throw new BusinessException("El email ya se encuentra registrado.");
        }

        usuario.setEmail(in.getEmail());
        usuario.setNombres(in.getNombres());
        usuario.setApellidos(in.getApellidos());

        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void delete(String email) {
        UsuarioEntity usuario = findByEmail(email);
        usuario.setIsActivo(false);
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void updatePassword(String email, UsuarioPasswordUpdateDTO in) {
        UsuarioEntity usuario = findByEmail(email);

        if (!passwordEncoder.matches(in.getCurrentPassword(), usuario.getPassword())) {
            throw new BusinessException("La contraseña actual es incorrecta.");
        }

        usuario.setPassword(passwordEncoder.encode(in.getNewPassword()));

        usuarioRepository.save(usuario);
    }
}
