package co.edu.cineflow.peliculas;

import co.edu.cineflow.common.exception.BusinessException;
import co.edu.cineflow.common.exception.NotFoundException;
import co.edu.cineflow.common.mongo.SequenceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PeliculaServiceImpl implements PeliculaService {
    private final PeliculaRepository peliculaRepository;
    private final SequenceService sequenceService;

    public PeliculaServiceImpl(PeliculaRepository peliculaRepository, SequenceService sequenceService) {
        this.peliculaRepository = peliculaRepository;
        this.sequenceService = sequenceService;
    }

    @Override
    public PeliculaEntity create(PeliculaEntity in) {
        if (peliculaRepository.existsByTituloAndIsActivoTrue(in.getTitulo())) {
            throw new BusinessException("Ya existe una película registrada con ese título.");
        }
        in.setId(sequenceService.next("peliculas"));
        return peliculaRepository.save(in);
    }

    @Override
    public PeliculaEntity findById(Long id) {
        return peliculaRepository.findByIdAndIsActivoTrue(id)
                .orElseThrow(() -> new NotFoundException("Película no encontrada con ID " + id + "."));
    }

    @Override
    public Page<PeliculaEntity> findAll(Pageable pageable) {
        return peliculaRepository.findAllByIsActivoTrue(pageable);
    }

    @Override
    public PeliculaEntity update(Long id, PeliculaEntity in) {
        PeliculaEntity existingPelicula = findById(id);

        if (peliculaRepository.existsByTituloAndIdNotAndIsActivoTrue(in.getTitulo(), id)) {
            throw new BusinessException("Ya existe una película registrada con ese título.");
        }

        existingPelicula.setTitulo(in.getTitulo());
        existingPelicula.setSinopsis(in.getSinopsis());
        existingPelicula.setDuracionMinutos(in.getDuracionMinutos());
        existingPelicula.setGenero(in.getGenero());

        return peliculaRepository.save(existingPelicula);
    }

    @Override
    public void delete(Long id) {
        PeliculaEntity existingPelicula = findById(id);
        existingPelicula.setIsActivo(false);
        peliculaRepository.save(existingPelicula);
    }
}