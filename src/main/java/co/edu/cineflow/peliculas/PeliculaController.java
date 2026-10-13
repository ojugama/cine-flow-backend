package co.edu.cineflow.peliculas;

import co.edu.cineflow.common.api.ApiResponse;
import co.edu.cineflow.common.api.ResponseBuilder;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/peliculas")
public class PeliculaController {
    private final PeliculaHandler peliculaHandler;

    public PeliculaController(PeliculaHandler peliculaHandler) {
        this.peliculaHandler = peliculaHandler;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<PeliculaDTO>> create(@Valid @RequestBody PeliculaCreateDTO in) {
        return ResponseBuilder.created("Se ha creado correctamente la película.", peliculaHandler.create(in));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PeliculaDTO>> findById(@PathVariable Long id) {
        return ResponseBuilder.ok("OK", peliculaHandler.findById(id));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PeliculaDTO>>> findAll(@ParameterObject Pageable pageable) {
        return ResponseBuilder.ok("OK", peliculaHandler.findAll(pageable));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PeliculaDTO>> update(@PathVariable Long id,
                                                           @Valid @RequestBody PeliculaUpdateDTO in) {
        return ResponseBuilder.ok("Se ha editado correctamente la película.", peliculaHandler.update(id, in));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        peliculaHandler.delete(id);
        return ResponseBuilder.ok("Se ha eliminado correctamente la película.");
    }
}