package co.edu.cineflow.funciones;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDateTime;

public class FuncionRepositoryImpl implements FuncionRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    public FuncionRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public boolean existeCruceHorario(Long idSala, LocalDateTime inicio, LocalDateTime fin, Long idFuncion) {
        Criteria criteria = Criteria.where("sala.$id").is(idSala)
                .and("isActivo").is(true)
                .and("fechaHoraInicio").lt(fin)
                .and("fechaHoraFin").gt(inicio);

        if (idFuncion != null) {
            criteria = criteria.and("_id").ne(idFuncion);
        }

        return mongoTemplate.exists(new Query(criteria), FuncionEntity.class);
    }
}
