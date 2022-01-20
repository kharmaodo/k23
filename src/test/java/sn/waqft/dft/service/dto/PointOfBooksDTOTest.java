package sn.waqft.dft.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.waqft.dft.web.rest.TestUtil;

class PointOfBooksDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PointOfBooksDTO.class);
        PointOfBooksDTO pointOfBooksDTO1 = new PointOfBooksDTO();
        pointOfBooksDTO1.setId("id1");
        PointOfBooksDTO pointOfBooksDTO2 = new PointOfBooksDTO();
        assertThat(pointOfBooksDTO1).isNotEqualTo(pointOfBooksDTO2);
        pointOfBooksDTO2.setId(pointOfBooksDTO1.getId());
        assertThat(pointOfBooksDTO1).isEqualTo(pointOfBooksDTO2);
        pointOfBooksDTO2.setId("id2");
        assertThat(pointOfBooksDTO1).isNotEqualTo(pointOfBooksDTO2);
        pointOfBooksDTO1.setId(null);
        assertThat(pointOfBooksDTO1).isNotEqualTo(pointOfBooksDTO2);
    }
}
