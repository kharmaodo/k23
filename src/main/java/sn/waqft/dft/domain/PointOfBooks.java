package sn.waqft.dft.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import sn.waqft.dft.domain.enumeration.Category;
import sn.waqft.dft.domain.enumeration.StatusOfPOB;

/**
 * A PointOfBooks.
 */
@Document(collection = "point_of_books")
public class PointOfBooks implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @Field("content")
    private byte[] content;

    @NotNull
    @Field("content_content_type")
    private String contentContentType;

    @Field("thumbnail")
    private String thumbnail;

    @Field("price")
    private String price;

    @Field("status")
    private StatusOfPOB status;

    @Field("volume")
    private String volume;

    @Field("author")
    private String author;

    @Field("category")
    private Category category;

    @Field("created_at")
    private Instant createdAt;

    @Field("deleted")
    private Boolean deleted;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public PointOfBooks id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public PointOfBooks name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return this.content;
    }

    public PointOfBooks content(byte[] content) {
        this.setContent(content);
        return this;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentContentType() {
        return this.contentContentType;
    }

    public PointOfBooks contentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
        return this;
    }

    public void setContentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public PointOfBooks thumbnail(String thumbnail) {
        this.setThumbnail(thumbnail);
        return this;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPrice() {
        return this.price;
    }

    public PointOfBooks price(String price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public StatusOfPOB getStatus() {
        return this.status;
    }

    public PointOfBooks status(StatusOfPOB status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(StatusOfPOB status) {
        this.status = status;
    }

    public String getVolume() {
        return this.volume;
    }

    public PointOfBooks volume(String volume) {
        this.setVolume(volume);
        return this;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getAuthor() {
        return this.author;
    }

    public PointOfBooks author(String author) {
        this.setAuthor(author);
        return this;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Category getCategory() {
        return this.category;
    }

    public PointOfBooks category(Category category) {
        this.setCategory(category);
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public PointOfBooks createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getDeleted() {
        return this.deleted;
    }

    public PointOfBooks deleted(Boolean deleted) {
        this.setDeleted(deleted);
        return this;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PointOfBooks)) {
            return false;
        }
        return id != null && id.equals(((PointOfBooks) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PointOfBooks{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", content='" + getContent() + "'" +
            ", contentContentType='" + getContentContentType() + "'" +
            ", thumbnail='" + getThumbnail() + "'" +
            ", price='" + getPrice() + "'" +
            ", status='" + getStatus() + "'" +
            ", volume='" + getVolume() + "'" +
            ", author='" + getAuthor() + "'" +
            ", category='" + getCategory() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", deleted='" + getDeleted() + "'" +
            "}";
    }
}
