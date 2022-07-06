package hz.spring.breweryservice.service;

import hz.spring.breweryservice.domain.Beer;
import hz.spring.breweryservice.repository.BeerRepository;
import hz.spring.breweryservice.web.controller.NotFoundException;
import hz.spring.breweryservice.web.mappers.BeerMapper;
import hz.spring.breweryservice.web.model.BeerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public BeerDTO getById(UUID beerId) {
        return beerMapper.BeerToBeerDTO(
                    beerRepository.findById(beerId).orElseThrow(NotFoundException::new)
        );
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        return beerMapper.BeerToBeerDTO(
                beerRepository.save(
                        beerMapper.BeerDTOToBeer(beerDTO)
                )
        );
    }

    @Override
    public BeerDTO updateBeer(UUID beerId, BeerDTO beerDTO) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

        beer.setBeerName(beerDTO.getBeerName());
        beer.setBeerStyle(beerDTO.getBeerStyle().name());
        beer.setPrice(beerDTO.getPrice());
        beer.setUpc(beerDTO.getUpc());

        return beerMapper.BeerToBeerDTO(beerRepository.save(beer));
    }
}
