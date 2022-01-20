package sn.waqft.dft.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;
import sn.waqft.dft.domain.enumeration.Category;
import sn.waqft.dft.domain.enumeration.StatusOfPOB;

/**
 * A DTO for the {@link sn.waqft.dft.domain.PointOfBooks} entity.
 */
public class PointOfBooksDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    private byte[] content;

    private String contentContentType;
    private String thumbnail;

    private String price;

    private StatusOfPOB status;

    private String volume;

    private String author;

    private Category category;

    private Instant createdAt;

    private Boolean deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getContentContentType() {
        return contentContentType;
    }

    public void setContentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public StatusOfPOB getStatus() {
        return status;
    }

    public void setStatus(StatusOfPOB status) {
        this.status = status;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PointOfBooksDTO)) {
            return false;
        }

        PointOfBooksDTO pointOfBooksDTO = (PointOfBooksDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pointOfBooksDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PointOfBooksDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", content='" + getContent() + "'" +
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
