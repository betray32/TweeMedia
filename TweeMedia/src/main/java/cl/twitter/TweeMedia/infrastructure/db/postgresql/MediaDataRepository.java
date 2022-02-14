package cl.twitter.tweemedia.infrastructure.db.postgresql;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MediaDataRepository extends MongoRepository<MediaDataEntity, String> {
}
