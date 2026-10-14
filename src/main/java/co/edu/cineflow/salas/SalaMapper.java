package co.edu.cineflow.salas;

import org.springframework.stereotype.Component;

@Component
public class SalaMapper {
    public SalaDTO toDto(SalaEntity salaEntity) {
        SalaDTO salaDTO = new SalaDTO();

        salaDTO.setId(salaEntity.getId());
        salaDTO.setNombre(salaEntity.getNombre());
        salaDTO.setFormato(salaEntity.getFormato());

        if (salaEntity.getSucursal() != null) {
            salaDTO.setIdSucursal(salaEntity.getSucursal().getId());
            salaDTO.setNombreSucursal(salaEntity.getSucursal().getNombre());
            salaDTO.setCiudadSucursal(salaEntity.getSucursal().getCiudad());
        }

        return salaDTO;
    }
}
