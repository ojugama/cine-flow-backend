package co.edu.cineflow.peliculas;

import co.edu.cineflow.common.exception.BusinessException;
import co.edu.cineflow.common.exception.NotFoundException;
import co.edu.cineflow.common.mongo.SequenceService;
import co.edu.cineflow.funciones.FuncionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PeliculaServiceImpl implements PeliculaService {
    private final PeliculaRepository peliculaRepository;
    private final FuncionRepository funcionRepository;
    private final SequenceService sequenceService;

    public PeliculaServiceImpl(PeliculaRepository peliculaRepository, FuncionRepository funcionRepository,
                               SequenceService sequenceService) {
        this.peliculaRepository = peliculaRepository;
        this.funcionRepository = funcionRepository;
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

        boolean tieneFuncionesActivas = funcionRepository.findByPeliculaIdAndIsActivoTrue(id).stream()
                .anyMatch(f -> f.getFechaHoraFin() != null && f.getFechaHoraFin().isAfter(LocalDateTime.now()));

        if (tieneFuncionesActivas) {
            throw new BusinessException("No se puede eliminar la película porque tiene funciones activas programadas o en curso.");
        }

        existingPelicula.setIsActivo(false);
        peliculaRepository.save(existingPelicula);
    }
}