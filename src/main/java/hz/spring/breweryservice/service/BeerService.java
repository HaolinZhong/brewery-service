package hz.spring.breweryservice.service;

import hz.spring.breweryservice.web.model.BeerDTO;
import hz.spring.breweryservice.web.model.BeerPagedList;
import hz.spring.breweryservice.web.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {

    BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventory);

    BeerDTO getById(UUID beerId, Boolean showInventory);

    BeerDTO getByUpc(String upc, Boolean showInventory);

    BeerDTO saveNewBeer(BeerDTO beerDTO);

    BeerDTO updateBeer(UUID beerId, BeerDTO beerDTO);
}
