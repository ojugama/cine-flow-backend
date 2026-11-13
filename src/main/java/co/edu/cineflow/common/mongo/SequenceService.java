package co.edu.cineflow.common.mongo;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class SequenceService {
    private final MongoTemplate mongoTemplate;

    public SequenceService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Long next(String sequenceName) {
        Query query = Query.query(Criteria.where("_id").is(sequenceName));
        Update update = new Update().inc("value", 1L);
        FindAndModifyOptions options = FindAndModifyOptions.options().upsert(true).returnNew(true);

        SequenceEntity sequence = mongoTemplate.findAndModify(query, update, options, SequenceEntity.class);

        return sequence != null && sequence.getValue() != null ? sequence.getValue() : 1L;
    }
}
