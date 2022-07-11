package hz.spring.breweryservice.event;

import hz.spring.breweryservice.web.model.BeerDTO;

public class NewInventoryEvent extends BeerEvent {

    public NewInventoryEvent(BeerDTO beerDTO) {
        super(beerDTO);
    }
}
