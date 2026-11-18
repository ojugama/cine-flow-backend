package co.edu.cineflow.compras;

import co.edu.cineflow.common.api.ApiResponse;
import co.edu.cineflow.common.api.ResponseBuilder;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/compras")
public class CompraController {
    private final CompraHandler compraHandler;

    public CompraController(CompraHandler compraHandler) {
        this.compraHandler = compraHandler;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CompraDTO>> create(@Valid @RequestBody CompraCreateDTO in,
                                                         Principal principal) {
        String email = principal.getName();
        return ResponseBuilder.created("Se ha registrado correctamente la compra.",
                compraHandler.create(email, in));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<CompraDTO>>> findMyCompras(Principal principal) {
        String email = principal.getName();
        return ResponseBuilder.ok("OK", compraHandler.findMyCompras(email));
    }

    @GetMapping("/funcion/{idFuncion}/sillas-ocupadas")
    public ResponseEntity<ApiResponse<List<String>>> findSillasOcupadas(@PathVariable Long idFuncion) {
        return ResponseBuilder.ok("OK", compraHandler.findSillasOcupadas(idFuncion));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CompraDTO>> findById(@PathVariable Long id,
                                                           Authentication authentication) {
        String email = authentication.getName();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));
        return ResponseBuilder.ok("OK", compraHandler.findById(id, email, isAdmin));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<CompraDTO>>> findAll(@ParameterObject Pageable pageable) {
        return ResponseBuilder.ok("OK", compraHandler.findAll(pageable));
    }
}
