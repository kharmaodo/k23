package sn.waqft.dft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import sn.waqft.dft.domain.PointOfBooks;
import sn.waqft.dft.domain.enumeration.StatusOfPOB;

/**
 * Spring Data MongoDB repository for the PointOfBooks entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PointOfBooksRepository extends MongoRepository<PointOfBooks, String> {
	 Page<PointOfBooks> findByDeleted(boolean deleted, Pageable pageable);
	 Page<PointOfBooks> findByStatus(String status, Pageable pageable);
	 Page<PointOfBooks> findByCategory(String category, Pageable pageable);
}
