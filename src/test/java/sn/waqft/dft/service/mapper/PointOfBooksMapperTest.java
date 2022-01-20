package sn.waqft.dft.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PointOfBooksMapperTest {

    private PointOfBooksMapper pointOfBooksMapper;

    @BeforeEach
    public void setUp() {
        pointOfBooksMapper = new PointOfBooksMapperImpl();
    }
}
