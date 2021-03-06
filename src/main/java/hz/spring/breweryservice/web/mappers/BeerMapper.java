package hz.spring.breweryservice.web.mappers;

import hz.spring.breweryservice.domain.Beer;
import hz.spring.common.model.BeerDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {
    BeerDTO BeerToBeerDTO(Beer beer);
    Beer BeerDTOToBeer(BeerDTO beerDTO);
    BeerDTO BeerToBeerDTOWithQuantity(Beer beer);
}
