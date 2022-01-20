package sn.waqft.dft.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import sn.waqft.dft.domain.enumeration.Category;
import sn.waqft.dft.repository.PointOfBooksRepository;
import sn.waqft.dft.service.PointOfBooksService;
import sn.waqft.dft.service.dto.PointOfBooksDTO;
import sn.waqft.dft.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.waqft.dft.domain.PointOfBooks}.
 */
@RestController
@RequestMapping("/api")
public class PointOfBooksResource {

    private final Logger log = LoggerFactory.getLogger(PointOfBooksResource.class);

    private static final String ENTITY_NAME = "pointOfBooks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PointOfBooksService pointOfBooksService;

    private final PointOfBooksRepository pointOfBooksRepository;

    public PointOfBooksResource(PointOfBooksService pointOfBooksService, PointOfBooksRepository pointOfBooksRepository) {
        this.pointOfBooksService = pointOfBooksService;
        this.pointOfBooksRepository = pointOfBooksRepository;
    }

    /**
     * {@code POST  /point-of-books} : Create a new pointOfBooks.
     *
     * @param pointOfBooksDTO the pointOfBooksDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pointOfBooksDTO, or with status {@code 400 (Bad Request)} if the pointOfBooks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/point-of-books")
    public ResponseEntity<PointOfBooksDTO> createPointOfBooks(@Valid @RequestBody PointOfBooksDTO pointOfBooksDTO)
        throws URISyntaxException {
        log.debug("REST request to save PointOfBooks : {}", pointOfBooksDTO);
        if (pointOfBooksDTO.getId() != null) {
            throw new BadRequestAlertException("A new pointOfBooks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PointOfBooksDTO result = pointOfBooksService.save(pointOfBooksDTO);
        return ResponseEntity
            .created(new URI("/api/point-of-books/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /point-of-books/:id} : Updates an existing pointOfBooks.
     *
     * @param id the id of the pointOfBooksDTO to save.
     * @param pointOfBooksDTO the pointOfBooksDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointOfBooksDTO,
     * or with status {@code 400 (Bad Request)} if the pointOfBooksDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pointOfBooksDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/point-of-books/{id}")
    public ResponseEntity<PointOfBooksDTO> updatePointOfBooks(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody PointOfBooksDTO pointOfBooksDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PointOfBooks : {}, {}", id, pointOfBooksDTO);
        if (pointOfBooksDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pointOfBooksDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pointOfBooksRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PointOfBooksDTO result = pointOfBooksService.save(pointOfBooksDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pointOfBooksDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /point-of-books/:id} : Partial updates given fields of an existing pointOfBooks, field will ignore if it is null
     *
     * @param id the id of the pointOfBooksDTO to save.
     * @param pointOfBooksDTO the pointOfBooksDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pointOfBooksDTO,
     * or with status {@code 400 (Bad Request)} if the pointOfBooksDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pointOfBooksDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pointOfBooksDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/point-of-books/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PointOfBooksDTO> partialUpdatePointOfBooks(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody PointOfBooksDTO pointOfBooksDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PointOfBooks partially : {}, {}", id, pointOfBooksDTO);
        if (pointOfBooksDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pointOfBooksDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pointOfBooksRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PointOfBooksDTO> result = pointOfBooksService.partialUpdate(pointOfBooksDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pointOfBooksDTO.getId())
        );
    }

    /**
     * {@code GET  /point-of-books} : get all the pointOfBooks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pointOfBooks in body.
     */
    @GetMapping("/point-of-books")
    public ResponseEntity<List<PointOfBooksDTO>> getAllPointOfBooks(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PointOfBooks");
        Page<PointOfBooksDTO> page = pointOfBooksService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    
    /**
     * {@code GET  /point-of-books} : get all the pointOfBooks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pointOfBooks in body.
     */
    @GetMapping("/point-of-books/notdeleted")
    public ResponseEntity<List<PointOfBooksDTO>> getAllNotDeletedPointOfBooks(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of not deleted PointOfBooks");
        Page<PointOfBooksDTO> page = pointOfBooksService.findByDeleted(false,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    /**
     * {@code GET  /point-of-books} : get all the pointOfBooks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pointOfBooks in body.
     */
    @GetMapping("/point-of-books/deleted")
    public ResponseEntity<List<PointOfBooksDTO>> getAllDeletedPointOfBooks(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of not deleted PointOfBooks");
        Page<PointOfBooksDTO> page = pointOfBooksService.findByDeleted(true,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    
    /**
     * {@code GET  /point-of-books/:id} : get the "id" pointOfBooks.
     *
     * @param id the id of the pointOfBooksDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pointOfBooksDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/point-of-books/{id}")
    public ResponseEntity<PointOfBooksDTO> getPointOfBooks(@PathVariable String id) {
        log.debug("REST request to get PointOfBooks : {}", id);
        Optional<PointOfBooksDTO> pointOfBooksDTO = pointOfBooksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pointOfBooksDTO);
    }

    /**
     * {@code DELETE  /point-of-books/:id} : delete the "id" pointOfBooks.
     *
     * @param id the id of the pointOfBooksDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/point-of-books/{id}")
    public ResponseEntity<Void> deletePointOfBooks(@PathVariable String id) {
        log.debug("REST request to delete PointOfBooks : {}", id);
        pointOfBooksService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

        /**
     * {@code GET  /point-of-books/byPurchasable} :Get all books purchasabeles
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pointOfBooks in body.
     */
    @GetMapping("/point-of-books/byPurchasable")
    public ResponseEntity<List<PointOfBooksDTO>> getAllPointOfBooksPurchasable(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PointOfBooks by category");
        Page<PointOfBooksDTO> page = pointOfBooksService.findByPurchasable(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


     /**
     * {@code GET  /point-of-books/byPurchasable} :Get all books purchasabeles
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pointOfBooks in body.
     */
    @GetMapping("/point-of-books/categories/{byCategory}")
    public ResponseEntity<List<PointOfBooksDTO>> getAllPointOfBooksByCategory(@PathVariable Category byCategory,@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PointOfBooks by category {} ", byCategory);
        Page<PointOfBooksDTO> page = pointOfBooksService.findByCategory(byCategory.name().toString(),pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
