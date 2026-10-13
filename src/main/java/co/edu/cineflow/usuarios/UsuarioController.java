package co.edu.cineflow.usuarios;

import co.edu.cineflow.common.api.ApiResponse;
import co.edu.cineflow.common.api.ResponseBuilder;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {
    private final UsuarioHandler usuarioHandler;

    public UsuarioController(UsuarioHandler usuarioHandler) {
        this.usuarioHandler = usuarioHandler;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UsuarioDTO>> create(@Valid @RequestBody UsuarioCreateDTO in) {
        return ResponseBuilder.created("Se ha creado correctamente el usuario.", usuarioHandler.create(in));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioDTO>> findById(@PathVariable Long id) {
        return ResponseBuilder.ok("OK", usuarioHandler.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<UsuarioDTO>>> findAll(@ParameterObject Pageable pageable) {
        return ResponseBuilder.ok("OK", usuarioHandler.findAll(pageable));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UsuarioDTO>> update(@PathVariable Long id,
                                                          @Valid @RequestBody UsuarioUpdateDTO in) {
        return ResponseBuilder.ok("Se ha editado correctamente el usuario.", usuarioHandler.update(id, in));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        usuarioHandler.delete(id);
        return ResponseBuilder.ok("Se ha desactivado correctamente el usuario.");
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UsuarioDTO>> findMe(Principal principal) {
        String email = principal.getName();

        return ResponseBuilder.ok("OK", usuarioHandler.findMe(email));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UsuarioDTO>> update(@Valid @RequestBody UsuarioUpdateDTO in,
                                                          Principal principal) {
        String email = principal.getName();

        return ResponseBuilder.ok("Se ha editado correctamente el usuario.",
                usuarioHandler.update(email, in));
    }

    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<Void>> delete(Principal principal) {
        String email = principal.getName();
        usuarioHandler.delete(email);
        return ResponseBuilder.ok("Se ha eliminado correctamente la cuenta.");
    }

    @PatchMapping("/me/password")
    public ResponseEntity<ApiResponse<Void>> updatePassword(@Valid @RequestBody UsuarioPasswordUpdateDTO in,
                                                            Principal principal) {
        String email = principal.getName();

        usuarioHandler.updatePassword(email, in);
        return ResponseBuilder.ok("Se ha actualizado correctamente la contraseña.");
    }
}
