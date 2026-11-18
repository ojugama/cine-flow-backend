package co.edu.cineflow.combos;

import org.springframework.stereotype.Component;

@Component
public class ComboMapper {
    public ComboDTO toDTO(ComboEntity entity) {
        ComboDTO dto = new ComboDTO();

        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setPrecio(entity.getPrecio());
        dto.setIcono(entity.getIcono());
        dto.setIsActivo(entity.getIsActivo());

        return dto;
    }
}
