package hz.spring.breweryservice.event;

import hz.spring.breweryservice.web.model.BeerDTO;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Builder
public class BeerEvent implements Serializable {

    static final long serialVersionUID = 8563926148987727310L;

    private final BeerDTO beerDTO;

}
