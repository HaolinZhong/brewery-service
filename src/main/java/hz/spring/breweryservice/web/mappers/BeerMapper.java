package hz.spring.breweryservice.web.mappers;

import hz.spring.breweryservice.domain.Beer;
import hz.spring.breweryservice.web.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {
    BeerDTO BeerToBeerDTO(Beer beer);
    Beer BeerDTOToBeer(BeerDTO beerDTO);
}
