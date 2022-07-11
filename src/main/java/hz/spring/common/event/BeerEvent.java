package hz.spring.common.event;

import hz.spring.breweryservice.web.model.BeerDTO;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerEvent implements Serializable {

    static final long serialVersionUID = 8563926148987727310L;

    private BeerDTO beerDTO;

}
