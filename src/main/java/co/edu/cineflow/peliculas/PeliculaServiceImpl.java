package co.edu.cineflow.peliculas;

import co.edu.cineflow.common.exception.BusinessException;
import co.edu.cineflow.common.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PeliculaServiceImpl implements PeliculaService {
    private final PeliculaRepository peliculaRepository;

    public PeliculaServiceImpl(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    @Override
    @Transactional
    public PeliculaEntity create(PeliculaEntity in) {
        if (peliculaRepository.existsByTituloAndIsActivoTrue(in.getTitulo())) {
            throw new BusinessException("Ya existe una película registrada con ese título.");
        }
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
    @Transactional
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
    @Transactional
    public void delete(Long id) {
        PeliculaEntity existingPelicula = findById(id);
        existingPelicula.setIsActivo(false);
        peliculaRepository.save(existingPelicula);
    }
}