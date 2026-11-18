package co.edu.cineflow.combos;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComboHandler {
    private final ComboService comboService;
    private final ComboMapper comboMapper;

    public ComboHandler(ComboService comboService, ComboMapper comboMapper) {
        this.comboService = comboService;
        this.comboMapper = comboMapper;
    }

    public List<ComboDTO> findAll() {
        return comboService.findAll().stream()
                .map(comboMapper::toDTO)
                .toList();
    }

    public ComboDTO findById(Long id) {
        return comboMapper.toDTO(comboService.findById(id));
    }
}
