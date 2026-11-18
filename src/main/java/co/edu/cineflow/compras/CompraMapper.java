package co.edu.cineflow.compras;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Component
public class CompraMapper {
    public CompraDTO toDTO(CompraEntity entity) {
        CompraDTO dto = new CompraDTO();

        dto.setId(entity.getId());
        dto.setCodigo(entity.getCodigo());
        dto.setSillas(entity.getSillas() != null ? entity.getSillas() : Collections.emptyList());
        dto.setPrecioBoleta(entity.getPrecioBoleta());
        dto.setTotal(entity.getTotal());
        dto.setFechaCompra(entity.getFechaCompra());
        dto.setIsActivo(entity.getIsActivo());

        if (entity.getUsuario() != null) {
            dto.setIdUsuario(entity.getUsuario().getId());
            dto.setEmailUsuario(entity.getUsuario().getEmail());
        }

        if (entity.getFuncion() != null) {
            dto.setIdFuncion(entity.getFuncion().getId());
            dto.setFechaHoraInicioFuncion(entity.getFuncion().getFechaHoraInicio());
            dto.setFechaHoraFinFuncion(entity.getFuncion().getFechaHoraFin());
            dto.setIsVigente(entity.getFuncion().getFechaHoraFin() != null
                    && entity.getFuncion().getFechaHoraFin().isAfter(LocalDateTime.now()));

            if (entity.getFuncion().getPelicula() != null) {
                dto.setIdPelicula(entity.getFuncion().getPelicula().getId());
                dto.setTituloPelicula(entity.getFuncion().getPelicula().getTitulo());
            }

            if (entity.getFuncion().getSala() != null) {
                dto.setIdSala(entity.getFuncion().getSala().getId());
                dto.setNombreSala(entity.getFuncion().getSala().getNombre());
                dto.setFormatoSala(entity.getFuncion().getSala().getFormato());

                if (entity.getFuncion().getSala().getSucursal() != null) {
                    dto.setNombreSucursal(entity.getFuncion().getSala().getSucursal().getNombre());
                    dto.setCiudadSucursal(entity.getFuncion().getSala().getSucursal().getCiudad());
                }
            }
        } else {
            dto.setIsVigente(false);
        }

        List<CompraComboDTO> combos = entity.getCombos() == null
                ? Collections.emptyList()
                : entity.getCombos().stream().map(this::toComboDTO).toList();
        dto.setCombos(combos);

        return dto;
    }

    private CompraComboDTO toComboDTO(CompraComboItem item) {
        CompraComboDTO dto = new CompraComboDTO();

        dto.setIdCombo(item.getIdCombo());
        dto.setNombre(item.getNombre());
        dto.setPrecio(item.getPrecio());
        dto.setCantidad(item.getCantidad());

        return dto;
    }
}
