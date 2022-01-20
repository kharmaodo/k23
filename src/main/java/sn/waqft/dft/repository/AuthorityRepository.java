package sn.waqft.dft.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import sn.waqft.dft.domain.Authority;

/**
 * Spring Data MongoDB repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends MongoRepository<Authority, String> {}
