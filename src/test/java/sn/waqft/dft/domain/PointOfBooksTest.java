package sn.waqft.dft.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.waqft.dft.web.rest.TestUtil;

class PointOfBooksTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PointOfBooks.class);
        PointOfBooks pointOfBooks1 = new PointOfBooks();
        pointOfBooks1.setId("id1");
        PointOfBooks pointOfBooks2 = new PointOfBooks();
        pointOfBooks2.setId(pointOfBooks1.getId());
        assertThat(pointOfBooks1).isEqualTo(pointOfBooks2);
        pointOfBooks2.setId("id2");
        assertThat(pointOfBooks1).isNotEqualTo(pointOfBooks2);
        pointOfBooks1.setId(null);
        assertThat(pointOfBooks1).isNotEqualTo(pointOfBooks2);
    }
}
