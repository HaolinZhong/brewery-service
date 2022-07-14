package hz.spring.breweryservice.service.order;

import hz.spring.breweryservice.repository.BeerRepository;
import hz.spring.common.model.BeerOrderDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderValidator {

    private final BeerRepository beerRepository;
    public Boolean validateOrder(BeerOrderDTO beerOrderDTO) {
        AtomicReference<Boolean> isValid = new AtomicReference<>(true);

        beerOrderDTO.getBeerOrderLines().forEach(line -> {
            if (!beerRepository.findByUpc(line.getUpc()).isPresent()) isValid.set(false);
        });

        return isValid.get();
    }
}
