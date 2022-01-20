package sn.waqft.dft.service.mapper;

import org.mapstruct.*;
import sn.waqft.dft.domain.PointOfBooks;
import sn.waqft.dft.service.dto.PointOfBooksDTO;

/**
 * Mapper for the entity {@link PointOfBooks} and its DTO {@link PointOfBooksDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PointOfBooksMapper extends EntityMapper<PointOfBooksDTO, PointOfBooks> {}
