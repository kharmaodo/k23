package sn.waqft.dft.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;
import sn.waqft.dft.IntegrationTest;
import sn.waqft.dft.domain.PointOfBooks;
import sn.waqft.dft.domain.enumeration.Category;
import sn.waqft.dft.domain.enumeration.StatusOfPOB;
import sn.waqft.dft.repository.PointOfBooksRepository;
import sn.waqft.dft.service.dto.PointOfBooksDTO;
import sn.waqft.dft.service.mapper.PointOfBooksMapper;

/**
 * Integration tests for the {@link PointOfBooksResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PointOfBooksResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CONTENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONTENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTENT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_THUMBNAIL = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_PRICE = "BBBBBBBBBB";

    private static final StatusOfPOB DEFAULT_STATUS = StatusOfPOB.COLLECTION;
    private static final StatusOfPOB UPDATED_STATUS = StatusOfPOB.MANUSCRIPT;

    private static final String DEFAULT_VOLUME = "AAAAAAAAAA";
    private static final String UPDATED_VOLUME = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final Category DEFAULT_CATEGORY = Category.FIQH;
    private static final Category UPDATED_CATEGORY = Category.LUGHA;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_DELETED = false;
    private static final Boolean UPDATED_DELETED = true;

    private static final String ENTITY_API_URL = "/api/point-of-books";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PointOfBooksRepository pointOfBooksRepository;

    @Autowired
    private PointOfBooksMapper pointOfBooksMapper;

    @Autowired
    private MockMvc restPointOfBooksMockMvc;

    private PointOfBooks pointOfBooks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointOfBooks createEntity() {
        PointOfBooks pointOfBooks = new PointOfBooks()
            .name(DEFAULT_NAME)
            .content(DEFAULT_CONTENT)
            .contentContentType(DEFAULT_CONTENT_CONTENT_TYPE)
            .thumbnail(DEFAULT_THUMBNAIL)
            .price(DEFAULT_PRICE)
            .status(DEFAULT_STATUS)
            .volume(DEFAULT_VOLUME)
            .author(DEFAULT_AUTHOR)
            .category(DEFAULT_CATEGORY)
            .createdAt(DEFAULT_CREATED_AT)
            .deleted(DEFAULT_DELETED);
        return pointOfBooks;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PointOfBooks createUpdatedEntity() {
        PointOfBooks pointOfBooks = new PointOfBooks()
            .name(UPDATED_NAME)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .thumbnail(UPDATED_THUMBNAIL)
            .price(UPDATED_PRICE)
            .status(UPDATED_STATUS)
            .volume(UPDATED_VOLUME)
            .author(UPDATED_AUTHOR)
            .category(UPDATED_CATEGORY)
            .createdAt(UPDATED_CREATED_AT)
            .deleted(UPDATED_DELETED);
        return pointOfBooks;
    }

    @BeforeEach
    public void initTest() {
        pointOfBooksRepository.deleteAll();
        pointOfBooks = createEntity();
    }

    @Test
    void createPointOfBooks() throws Exception {
        int databaseSizeBeforeCreate = pointOfBooksRepository.findAll().size();
        // Create the PointOfBooks
        PointOfBooksDTO pointOfBooksDTO = pointOfBooksMapper.toDto(pointOfBooks);
        restPointOfBooksMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pointOfBooksDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PointOfBooks in the database
        List<PointOfBooks> pointOfBooksList = pointOfBooksRepository.findAll();
        assertThat(pointOfBooksList).hasSize(databaseSizeBeforeCreate + 1);
        PointOfBooks testPointOfBooks = pointOfBooksList.get(pointOfBooksList.size() - 1);
        assertThat(testPointOfBooks.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPointOfBooks.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testPointOfBooks.getContentContentType()).isEqualTo(DEFAULT_CONTENT_CONTENT_TYPE);
        assertThat(testPointOfBooks.getThumbnail()).isEqualTo(DEFAULT_THUMBNAIL);
        assertThat(testPointOfBooks.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPointOfBooks.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPointOfBooks.getVolume()).isEqualTo(DEFAULT_VOLUME);
        assertThat(testPointOfBooks.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testPointOfBooks.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testPointOfBooks.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPointOfBooks.getDeleted()).isEqualTo(DEFAULT_DELETED);
    }

    @Test
    void createPointOfBooksWithExistingId() throws Exception {
        // Create the PointOfBooks with an existing ID
        pointOfBooks.setId("existing_id");
        PointOfBooksDTO pointOfBooksDTO = pointOfBooksMapper.toDto(pointOfBooks);

        int databaseSizeBeforeCreate = pointOfBooksRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPointOfBooksMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pointOfBooksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointOfBooks in the database
        List<PointOfBooks> pointOfBooksList = pointOfBooksRepository.findAll();
        assertThat(pointOfBooksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pointOfBooksRepository.findAll().size();
        // set the field null
        pointOfBooks.setName(null);

        // Create the PointOfBooks, which fails.
        PointOfBooksDTO pointOfBooksDTO = pointOfBooksMapper.toDto(pointOfBooks);

        restPointOfBooksMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pointOfBooksDTO))
            )
            .andExpect(status().isBadRequest());

        List<PointOfBooks> pointOfBooksList = pointOfBooksRepository.findAll();
        assertThat(pointOfBooksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllPointOfBooks() throws Exception {
        // Initialize the database
        pointOfBooksRepository.save(pointOfBooks);

        // Get all the pointOfBooksList
        restPointOfBooksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pointOfBooks.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].contentContentType").value(hasItem(DEFAULT_CONTENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT))))
            .andExpect(jsonPath("$.[*].thumbnail").value(hasItem(DEFAULT_THUMBNAIL)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].volume").value(hasItem(DEFAULT_VOLUME)))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED.booleanValue())));
    }

    @Test
    void getPointOfBooks() throws Exception {
        // Initialize the database
        pointOfBooksRepository.save(pointOfBooks);

        // Get the pointOfBooks
        restPointOfBooksMockMvc
            .perform(get(ENTITY_API_URL_ID, pointOfBooks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pointOfBooks.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.contentContentType").value(DEFAULT_CONTENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.content").value(Base64Utils.encodeToString(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.thumbnail").value(DEFAULT_THUMBNAIL))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.volume").value(DEFAULT_VOLUME))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED.booleanValue()));
    }

    @Test
    void getNonExistingPointOfBooks() throws Exception {
        // Get the pointOfBooks
        restPointOfBooksMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putNewPointOfBooks() throws Exception {
        // Initialize the database
        pointOfBooksRepository.save(pointOfBooks);

        int databaseSizeBeforeUpdate = pointOfBooksRepository.findAll().size();

        // Update the pointOfBooks
        PointOfBooks updatedPointOfBooks = pointOfBooksRepository.findById(pointOfBooks.getId()).get();
        updatedPointOfBooks
            .name(UPDATED_NAME)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .thumbnail(UPDATED_THUMBNAIL)
            .price(UPDATED_PRICE)
            .status(UPDATED_STATUS)
            .volume(UPDATED_VOLUME)
            .author(UPDATED_AUTHOR)
            .category(UPDATED_CATEGORY)
            .createdAt(UPDATED_CREATED_AT)
            .deleted(UPDATED_DELETED);
        PointOfBooksDTO pointOfBooksDTO = pointOfBooksMapper.toDto(updatedPointOfBooks);

        restPointOfBooksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pointOfBooksDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointOfBooksDTO))
            )
            .andExpect(status().isOk());

        // Validate the PointOfBooks in the database
        List<PointOfBooks> pointOfBooksList = pointOfBooksRepository.findAll();
        assertThat(pointOfBooksList).hasSize(databaseSizeBeforeUpdate);
        PointOfBooks testPointOfBooks = pointOfBooksList.get(pointOfBooksList.size() - 1);
        assertThat(testPointOfBooks.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPointOfBooks.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testPointOfBooks.getContentContentType()).isEqualTo(UPDATED_CONTENT_CONTENT_TYPE);
        assertThat(testPointOfBooks.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testPointOfBooks.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPointOfBooks.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPointOfBooks.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testPointOfBooks.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testPointOfBooks.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testPointOfBooks.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPointOfBooks.getDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    void putNonExistingPointOfBooks() throws Exception {
        int databaseSizeBeforeUpdate = pointOfBooksRepository.findAll().size();
        pointOfBooks.setId(UUID.randomUUID().toString());

        // Create the PointOfBooks
        PointOfBooksDTO pointOfBooksDTO = pointOfBooksMapper.toDto(pointOfBooks);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointOfBooksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pointOfBooksDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointOfBooksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointOfBooks in the database
        List<PointOfBooks> pointOfBooksList = pointOfBooksRepository.findAll();
        assertThat(pointOfBooksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPointOfBooks() throws Exception {
        int databaseSizeBeforeUpdate = pointOfBooksRepository.findAll().size();
        pointOfBooks.setId(UUID.randomUUID().toString());

        // Create the PointOfBooks
        PointOfBooksDTO pointOfBooksDTO = pointOfBooksMapper.toDto(pointOfBooks);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointOfBooksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pointOfBooksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointOfBooks in the database
        List<PointOfBooks> pointOfBooksList = pointOfBooksRepository.findAll();
        assertThat(pointOfBooksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPointOfBooks() throws Exception {
        int databaseSizeBeforeUpdate = pointOfBooksRepository.findAll().size();
        pointOfBooks.setId(UUID.randomUUID().toString());

        // Create the PointOfBooks
        PointOfBooksDTO pointOfBooksDTO = pointOfBooksMapper.toDto(pointOfBooks);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointOfBooksMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pointOfBooksDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PointOfBooks in the database
        List<PointOfBooks> pointOfBooksList = pointOfBooksRepository.findAll();
        assertThat(pointOfBooksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePointOfBooksWithPatch() throws Exception {
        // Initialize the database
        pointOfBooksRepository.save(pointOfBooks);

        int databaseSizeBeforeUpdate = pointOfBooksRepository.findAll().size();

        // Update the pointOfBooks using partial update
        PointOfBooks partialUpdatedPointOfBooks = new PointOfBooks();
        partialUpdatedPointOfBooks.setId(pointOfBooks.getId());

        partialUpdatedPointOfBooks
            .name(UPDATED_NAME)
            .thumbnail(UPDATED_THUMBNAIL)
            .price(UPDATED_PRICE)
            .volume(UPDATED_VOLUME)
            .category(UPDATED_CATEGORY)
            .createdAt(UPDATED_CREATED_AT)
            .deleted(UPDATED_DELETED);

        restPointOfBooksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPointOfBooks.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPointOfBooks))
            )
            .andExpect(status().isOk());

        // Validate the PointOfBooks in the database
        List<PointOfBooks> pointOfBooksList = pointOfBooksRepository.findAll();
        assertThat(pointOfBooksList).hasSize(databaseSizeBeforeUpdate);
        PointOfBooks testPointOfBooks = pointOfBooksList.get(pointOfBooksList.size() - 1);
        assertThat(testPointOfBooks.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPointOfBooks.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testPointOfBooks.getContentContentType()).isEqualTo(DEFAULT_CONTENT_CONTENT_TYPE);
        assertThat(testPointOfBooks.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testPointOfBooks.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPointOfBooks.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPointOfBooks.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testPointOfBooks.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testPointOfBooks.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testPointOfBooks.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPointOfBooks.getDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    void fullUpdatePointOfBooksWithPatch() throws Exception {
        // Initialize the database
        pointOfBooksRepository.save(pointOfBooks);

        int databaseSizeBeforeUpdate = pointOfBooksRepository.findAll().size();

        // Update the pointOfBooks using partial update
        PointOfBooks partialUpdatedPointOfBooks = new PointOfBooks();
        partialUpdatedPointOfBooks.setId(pointOfBooks.getId());

        partialUpdatedPointOfBooks
            .name(UPDATED_NAME)
            .content(UPDATED_CONTENT)
            .contentContentType(UPDATED_CONTENT_CONTENT_TYPE)
            .thumbnail(UPDATED_THUMBNAIL)
            .price(UPDATED_PRICE)
            .status(UPDATED_STATUS)
            .volume(UPDATED_VOLUME)
            .author(UPDATED_AUTHOR)
            .category(UPDATED_CATEGORY)
            .createdAt(UPDATED_CREATED_AT)
            .deleted(UPDATED_DELETED);

        restPointOfBooksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPointOfBooks.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPointOfBooks))
            )
            .andExpect(status().isOk());

        // Validate the PointOfBooks in the database
        List<PointOfBooks> pointOfBooksList = pointOfBooksRepository.findAll();
        assertThat(pointOfBooksList).hasSize(databaseSizeBeforeUpdate);
        PointOfBooks testPointOfBooks = pointOfBooksList.get(pointOfBooksList.size() - 1);
        assertThat(testPointOfBooks.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPointOfBooks.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testPointOfBooks.getContentContentType()).isEqualTo(UPDATED_CONTENT_CONTENT_TYPE);
        assertThat(testPointOfBooks.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testPointOfBooks.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPointOfBooks.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPointOfBooks.getVolume()).isEqualTo(UPDATED_VOLUME);
        assertThat(testPointOfBooks.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testPointOfBooks.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testPointOfBooks.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPointOfBooks.getDeleted()).isEqualTo(UPDATED_DELETED);
    }

    @Test
    void patchNonExistingPointOfBooks() throws Exception {
        int databaseSizeBeforeUpdate = pointOfBooksRepository.findAll().size();
        pointOfBooks.setId(UUID.randomUUID().toString());

        // Create the PointOfBooks
        PointOfBooksDTO pointOfBooksDTO = pointOfBooksMapper.toDto(pointOfBooks);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPointOfBooksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pointOfBooksDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pointOfBooksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointOfBooks in the database
        List<PointOfBooks> pointOfBooksList = pointOfBooksRepository.findAll();
        assertThat(pointOfBooksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPointOfBooks() throws Exception {
        int databaseSizeBeforeUpdate = pointOfBooksRepository.findAll().size();
        pointOfBooks.setId(UUID.randomUUID().toString());

        // Create the PointOfBooks
        PointOfBooksDTO pointOfBooksDTO = pointOfBooksMapper.toDto(pointOfBooks);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointOfBooksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pointOfBooksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PointOfBooks in the database
        List<PointOfBooks> pointOfBooksList = pointOfBooksRepository.findAll();
        assertThat(pointOfBooksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPointOfBooks() throws Exception {
        int databaseSizeBeforeUpdate = pointOfBooksRepository.findAll().size();
        pointOfBooks.setId(UUID.randomUUID().toString());

        // Create the PointOfBooks
        PointOfBooksDTO pointOfBooksDTO = pointOfBooksMapper.toDto(pointOfBooks);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPointOfBooksMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pointOfBooksDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PointOfBooks in the database
        List<PointOfBooks> pointOfBooksList = pointOfBooksRepository.findAll();
        assertThat(pointOfBooksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePointOfBooks() throws Exception {
        // Initialize the database
        pointOfBooksRepository.save(pointOfBooks);

        int databaseSizeBeforeDelete = pointOfBooksRepository.findAll().size();

        // Delete the pointOfBooks
        restPointOfBooksMockMvc
            .perform(delete(ENTITY_API_URL_ID, pointOfBooks.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PointOfBooks> pointOfBooksList = pointOfBooksRepository.findAll();
        assertThat(pointOfBooksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
