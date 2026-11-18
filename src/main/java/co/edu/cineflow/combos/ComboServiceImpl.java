package co.edu.cineflow.combos;

import co.edu.cineflow.common.exception.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComboServiceImpl implements ComboService {
    private final ComboRepository comboRepository;

    public ComboServiceImpl(ComboRepository comboRepository) {
        this.comboRepository = comboRepository;
    }

    @Override
    public List<ComboEntity> findAll() {
        return comboRepository.findAllByIsActivoTrue(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public ComboEntity findById(Long id) {
        return comboRepository.findByIdAndIsActivoTrue(id)
                .orElseThrow(() -> new NotFoundException("Combo no encontrado con ID " + id + "."));
    }
}
