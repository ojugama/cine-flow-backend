package co.edu.cineflow.funciones;

import co.edu.cineflow.common.exception.BusinessException;
import co.edu.cineflow.common.exception.NotFoundException;
import co.edu.cineflow.peliculas.PeliculaEntity;
import co.edu.cineflow.peliculas.PeliculaService;
import co.edu.cineflow.salas.SalaEntity;
import co.edu.cineflow.salas.SalaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class FuncionServiceImpl implements FuncionService {
    private final FuncionRepository funcionRepository;
    private final PeliculaService peliculaService;
    private final SalaService salaService;

    public FuncionServiceImpl(FuncionRepository funcionRepository, PeliculaService peliculaService, SalaService salaService) {
        this.funcionRepository = funcionRepository;
        this.peliculaService = peliculaService;
        this.salaService = salaService;
    }

    @Override
    @Transactional
    public FuncionEntity create(FuncionEntity in, Long idPelicula, Long idSala) {
        if (!in.getFechaHoraFin().isAfter(in.getFechaHoraInicio())) {
            throw new BusinessException("La fecha y hora de fin debe ser posterior a la de inicio.");
        }

        PeliculaEntity pelicula = peliculaService.findById(idPelicula);
        SalaEntity sala = salaService.findById(idSala);

        if (funcionRepository.existeCruceHorario(idSala, in.getFechaHoraInicio(), in.getFechaHoraFin(), null)) {
            throw new BusinessException("La sala ya tiene asignada una función en ese rango de horario.");
        }

        in.setPelicula(pelicula);
        in.setSala(sala);

        return funcionRepository.save(in);
    }

    @Override
    public FuncionEntity findById(Long id) {
        return funcionRepository.findByIdAndIsActivoTrue(id)
                .orElseThrow(() -> new NotFoundException("Función no encontrada con ID " + id + "."));
    }

    @Override
    public Page<FuncionEntity> findAll(Pageable pageable) {
        return funcionRepository.findAllByIsActivoTrue(pageable);
    }

    @Override
    public List<FuncionEntity> findByPelicula(Long idPelicula) {
        return funcionRepository.findByPeliculaIdAndIsActivoTrue(idPelicula);
    }

    @Override
    @Transactional
    public FuncionEntity update(Long id, FuncionEntity in, Long idPelicula, Long idSala) {
        FuncionEntity existing = findById(id);

        if (!in.getFechaHoraFin().isAfter(in.getFechaHoraInicio())) {
            throw new BusinessException("La fecha y hora de fin debe ser posterior a la de inicio.");
        }

        PeliculaEntity pelicula = peliculaService.findById(idPelicula);
        SalaEntity sala = salaService.findById(idSala);

        if (funcionRepository.existeCruceHorario(idSala, in.getFechaHoraInicio(), in.getFechaHoraFin(), id)) {
            throw new BusinessException("La sala ya tiene asignada una función en ese rango de horario.");
        }

        existing.setPelicula(pelicula);
        existing.setSala(sala);
        existing.setFechaHoraInicio(in.getFechaHoraInicio());
        existing.setFechaHoraFin(in.getFechaHoraFin());

        return funcionRepository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        FuncionEntity existing = findById(id);
        existing.setIsActivo(false);
        
        funcionRepository.save(existing);
    }
}