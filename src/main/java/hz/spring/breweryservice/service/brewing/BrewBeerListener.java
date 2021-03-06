package hz.spring.breweryservice.service.brewing;

import hz.spring.breweryservice.config.JmsConfig;
import hz.spring.breweryservice.domain.Beer;
import hz.spring.common.event.BrewBeerEvent;
import hz.spring.common.event.NewInventoryEvent;
import hz.spring.breweryservice.repository.BeerRepository;
import hz.spring.common.model.BeerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent event) {
        BeerDTO beerDTO = event.getBeerDTO();

        Beer beer = beerRepository.getReferenceById(beerDTO.getId());

        beerDTO.setQuantityOnHand(beer.getQuantityToBrew());

        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDTO);

        log.debug("Brewed beer " + beer.getMinOnHand() + " : QOH: " + beerDTO.getQuantityOnHand());

        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
    }

}
