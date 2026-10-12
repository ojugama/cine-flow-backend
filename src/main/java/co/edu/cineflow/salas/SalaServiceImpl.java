package co.edu.cineflow.salas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SalaServiceImpl implements SalaService {
    private final SalaRepository salaRepository;

    public SalaServiceImpl(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    @Override
    public Page<SalaEntity> findAll(Pageable pageable) {
        return salaRepository.findAll(pageable);
    }
}
