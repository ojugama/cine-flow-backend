package co.edu.cineflow.combos;

import java.util.List;

public interface ComboService {
    List<ComboEntity> findAll();

    ComboEntity findById(Long id);
}
