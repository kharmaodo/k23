package sn.waqft.dft.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.waqft.dft.domain.PointOfBooks;
import sn.waqft.dft.repository.PointOfBooksRepository;
import sn.waqft.dft.service.dto.PointOfBooksDTO;
import sn.waqft.dft.service.mapper.PointOfBooksMapper;

/**
 * Service Implementation for managing {@link PointOfBooks}.
 */
@Service
public class PointOfBooksService {

    private final Logger log = LoggerFactory.getLogger(PointOfBooksService.class);

    private final PointOfBooksRepository pointOfBooksRepository;

    private final PointOfBooksMapper pointOfBooksMapper;

    public PointOfBooksService(PointOfBooksRepository pointOfBooksRepository, PointOfBooksMapper pointOfBooksMapper) {
        this.pointOfBooksRepository = pointOfBooksRepository;
        this.pointOfBooksMapper = pointOfBooksMapper;
    }

    /**
     * Save a pointOfBooks.
     *
     * @param pointOfBooksDTO the entity to save.
     * @return the persisted entity.
     */
    public PointOfBooksDTO save(PointOfBooksDTO pointOfBooksDTO) {
        log.debug("Request to save PointOfBooks : {}", pointOfBooksDTO);
        PointOfBooks pointOfBooks = pointOfBooksMapper.toEntity(pointOfBooksDTO);
        pointOfBooks = pointOfBooksRepository.save(pointOfBooks);
        return pointOfBooksMapper.toDto(pointOfBooks);
    }

    /**
     * Partially update a pointOfBooks.
     *
     * @param pointOfBooksDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PointOfBooksDTO> partialUpdate(PointOfBooksDTO pointOfBooksDTO) {
        log.debug("Request to partially update PointOfBooks : {}", pointOfBooksDTO);

        return pointOfBooksRepository
            .findById(pointOfBooksDTO.getId())
            .map(existingPointOfBooks -> {
                pointOfBooksMapper.partialUpdate(existingPointOfBooks, pointOfBooksDTO);

                return existingPointOfBooks;
            })
            .map(pointOfBooksRepository::save)
            .map(pointOfBooksMapper::toDto);
    }

    /**
     * Get all the pointOfBooks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<PointOfBooksDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PointOfBooks");
        return pointOfBooksRepository.findAll(pageable).map(pointOfBooksMapper::toDto);
    }

    /**
     * Get one pointOfBooks by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<PointOfBooksDTO> findOne(String id) {
        log.debug("Request to get PointOfBooks : {}", id);
        return pointOfBooksRepository.findById(id).map(pointOfBooksMapper::toDto);
    }

    /**
     * Delete the pointOfBooks by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete PointOfBooks : {}", id);
        pointOfBooksRepository.deleteById(id);
    }
}
