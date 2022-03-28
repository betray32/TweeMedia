package cl.twitter.tweemedia.infrastructure.db.h2;

import org.springframework.data.repository.CrudRepository;

public interface MediaDataRepository extends CrudRepository<MediaDataEntity, String> {
}
