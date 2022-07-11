package hz.spring.breweryservice.service.brewing;

import hz.spring.breweryservice.config.JmsConfig;
import hz.spring.breweryservice.domain.Beer;
import hz.spring.breweryservice.event.BrewBeerEvent;
import hz.spring.breweryservice.repository.BeerRepository;
import hz.spring.breweryservice.service.inventory.BeerInventoryService;
import hz.spring.breweryservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = 5000)
    public void checkForLowInventory() {
        List<Beer> beers = beerRepository.findAll();

        beers.forEach(beer -> {
            Integer QOH = beerInventoryService.getOnhandInventory(beer.getId());

            log.debug("Min on hand is " + beer.getMinOnHand());
            log.debug("Inventory QOH is " + QOH);

            if (beer.getMinOnHand() > QOH) {
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE, new BrewBeerEvent(beerMapper.BeerToBeerDTO(beer)));
            }
        });
    }

}
