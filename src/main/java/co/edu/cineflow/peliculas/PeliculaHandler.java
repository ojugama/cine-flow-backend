package co.edu.cineflow.peliculas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PeliculaHandler {
    private final PeliculaService peliculaService;
    private final PeliculaMapper peliculaMapper;
    private final PeliculaImageStorage peliculaImageStorage;

    public PeliculaHandler(PeliculaService peliculaService, PeliculaMapper peliculaMapper,
                           PeliculaImageStorage peliculaImageStorage) {
        this.peliculaService = peliculaService;
        this.peliculaMapper = peliculaMapper;
        this.peliculaImageStorage = peliculaImageStorage;
    }

    public PeliculaDTO create(PeliculaCreateDTO in) {
        PeliculaEntity entity = peliculaMapper.toEntity(in);

        return peliculaMapper.toDTO(peliculaService.create(entity));
    }

    public PeliculaDTO findById(Long id) {
        return peliculaMapper.toDTO(peliculaService.findById(id));
    }

    public Page<PeliculaDTO> findAll(Pageable pageable) {
        return peliculaService.findAll(pageable).map(peliculaMapper::toDTO);
    }

    public PeliculaDTO update(Long id, PeliculaUpdateDTO in) {
        PeliculaEntity entity = peliculaMapper.toEntity(in);

        return peliculaMapper.toDTO(peliculaService.update(id, entity));
    }

    public PeliculaDTO actualizarImagen(Long id, MultipartFile imagen) {
        PeliculaEntity pelicula = peliculaService.findById(id);
        String imagenUrl = peliculaImageStorage.guardar(imagen, id);
        PeliculaEntity actualizada = peliculaService.actualizarImagen(id, imagenUrl);
        peliculaImageStorage.eliminar(pelicula.getImagenUrl());
        return peliculaMapper.toDTO(actualizada);
    }

    public void delete(Long id) {
        peliculaService.delete(id);
    }
}