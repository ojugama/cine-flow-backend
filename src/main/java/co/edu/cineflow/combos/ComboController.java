package co.edu.cineflow.combos;

import co.edu.cineflow.common.api.ApiResponse;
import co.edu.cineflow.common.api.ResponseBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/combos")
public class ComboController {
    private final ComboHandler comboHandler;

    public ComboController(ComboHandler comboHandler) {
        this.comboHandler = comboHandler;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ComboDTO>>> findAll() {
        return ResponseBuilder.ok("OK", comboHandler.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ComboDTO>> findById(@PathVariable Long id) {
        return ResponseBuilder.ok("OK", comboHandler.findById(id));
    }
}
