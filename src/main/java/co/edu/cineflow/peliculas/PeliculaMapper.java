package co.edu.cineflow.peliculas;

import org.springframework.stereotype.Component;

@Component
public class PeliculaMapper {
    public PeliculaDTO toDTO(PeliculaEntity peliculaEntity) {
        PeliculaDTO peliculaDTO = new PeliculaDTO();

        peliculaDTO.setId(peliculaEntity.getId());
        peliculaDTO.setTitulo(peliculaEntity.getTitulo());
        peliculaDTO.setSinopsis(peliculaEntity.getSinopsis());
        peliculaDTO.setDuracionMinutos(peliculaEntity.getDuracionMinutos());
        peliculaDTO.setGenero(peliculaEntity.getGenero());
        peliculaDTO.setImagenUrl(peliculaEntity.getImagenUrl());
        peliculaDTO.setIsActivo(peliculaEntity.getIsActivo());

        return peliculaDTO;
    }

    public PeliculaEntity toEntity(PeliculaCreateDTO peliculaDTO) {
        PeliculaEntity peliculaEntity = new PeliculaEntity();

        peliculaEntity.setTitulo(peliculaDTO.getTitulo());
        peliculaEntity.setSinopsis(peliculaDTO.getSinopsis());
        peliculaEntity.setDuracionMinutos(peliculaDTO.getDuracionMinutos());
        peliculaEntity.setGenero(peliculaDTO.getGenero());
        peliculaEntity.setImagenUrl(peliculaDTO.getImagenUrl());

        return peliculaEntity;
    }

    public PeliculaEntity toEntity(PeliculaUpdateDTO peliculaDto) {
        PeliculaEntity peliculaEntity = new PeliculaEntity();

        peliculaEntity.setTitulo(peliculaDto.getTitulo());
        peliculaEntity.setSinopsis(peliculaDto.getSinopsis());
        peliculaEntity.setDuracionMinutos(peliculaDto.getDuracionMinutos());
        peliculaEntity.setGenero(peliculaDto.getGenero());
        peliculaEntity.setImagenUrl(peliculaDto.getImagenUrl());

        return peliculaEntity;
    }
}
