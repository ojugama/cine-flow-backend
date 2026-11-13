package co.edu.cineflow.common.mongo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "counters")
public class SequenceEntity {
    @Id
    private String id;

    private Long value;
}
