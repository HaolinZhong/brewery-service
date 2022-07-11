package hz.spring.breweryservice.event;

import hz.spring.breweryservice.web.model.BeerDTO;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent {

    public BrewBeerEvent(BeerDTO beerDTO) {
        super(beerDTO);
    }

}
