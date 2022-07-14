package hz.spring.breweryservice.web.mappers;

import hz.spring.breweryservice.domain.Beer;
import hz.spring.breweryservice.service.inventory.BeerInventoryService;
import hz.spring.common.model.BeerDTO;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BeerMapperDecorator implements BeerMapper {
    private BeerInventoryService beerInventoryService;
    private BeerMapper mapper;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setMapper(BeerMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public BeerDTO BeerToBeerDTO(Beer beer) {
        return mapper.BeerToBeerDTO(beer);
    }

    @Override
    public BeerDTO BeerToBeerDTOWithQuantity(Beer beer) {
        BeerDTO dto = mapper.BeerToBeerDTO(beer);
        dto.setQuantityOnHand(beerInventoryService.getOnhandInventory(beer.getId()));
        return dto;
    }

    @Override
    public Beer BeerDTOToBeer(BeerDTO beerDTO) {
        return mapper.BeerDTOToBeer(beerDTO);
    }
}
