package hz.spring.breweryservice.service;

import hz.spring.breweryservice.domain.Beer;
import hz.spring.breweryservice.repository.BeerRepository;
import hz.spring.breweryservice.web.controller.NotFoundException;
import hz.spring.breweryservice.web.mappers.BeerMapper;
import hz.spring.breweryservice.web.model.BeerDTO;
import hz.spring.breweryservice.web.model.BeerPagedList;
import hz.spring.breweryservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Cacheable(cacheNames = "beerListCache", condition = "#showInventory == false")
    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventory) {

        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search both
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
            //search beer_service name
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search beer_service style
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if (showInventory) {
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::BeerToBeerDTOWithQuantity)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());

        } else {
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::BeerToBeerDTO)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        }


        return beerPagedList;
    }


    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventory == false ")
    @Override
    public BeerDTO getById(UUID beerId, Boolean showInventory) {

        if (showInventory) {
            return beerMapper.BeerToBeerDTOWithQuantity(
                    beerRepository.findById(beerId).orElseThrow(NotFoundException::new)
            );
        }

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
