package co.edu.cineflow.compras;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class CompraRepositoryImpl implements CompraRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    public CompraRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public boolean existeSillaOcupada(Long idFuncion, List<String> sillas) {
        Criteria criteria = Criteria.where("funcion.$id").is(idFuncion)
                .and("isActivo").is(true)
                .and("sillas").in(sillas);

        return mongoTemplate.exists(new Query(criteria), CompraEntity.class);
    }
}
