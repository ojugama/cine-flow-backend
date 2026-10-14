package co.edu.cineflow.funciones;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FuncionHandler {
    private final FuncionService funcionService;
    private final FuncionMapper funcionMapper;

    public FuncionHandler(FuncionService funcionService, FuncionMapper funcionMapper) {
        this.funcionService = funcionService;
        this.funcionMapper = funcionMapper;
    }

    public FuncionDTO create(FuncionCreateDTO dto) {
        FuncionEntity entity = funcionMapper.toEntity(dto);
        FuncionEntity saved = funcionService.create(entity, dto.getIdPelicula(), dto.getIdSala());

        return funcionMapper.toDTO(saved);
    }

    public FuncionDTO findById(Long id) {
        return funcionMapper.toDTO(funcionService.findById(id));
    }

    public Page<FuncionDTO> findAll(Pageable pageable) {
        return funcionService.findAll(pageable).map(funcionMapper::toDTO);
    }

    public List<FuncionDTO> findByPelicula(Long idPelicula) {
        return funcionService.findByPelicula(idPelicula).stream()
                .map(funcionMapper::toDTO)
                .toList();
    }

    public FuncionDTO update(Long id, FuncionUpdateDTO dto) {
        FuncionEntity entity = funcionMapper.toEntity(dto);
        FuncionEntity updated = funcionService.update(id, entity, dto.getIdPelicula(), dto.getIdSala());

        return funcionMapper.toDTO(updated);
    }

    public void delete(Long id) {
        funcionService.delete(id);
    }
}