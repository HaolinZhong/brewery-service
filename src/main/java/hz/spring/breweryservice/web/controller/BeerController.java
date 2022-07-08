package hz.spring.breweryservice.web.controller;

import hz.spring.breweryservice.service.BeerService;
import hz.spring.breweryservice.web.model.BeerDTO;
import hz.spring.breweryservice.web.model.BeerPagedList;
import hz.spring.breweryservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final BeerService beerService;

    @GetMapping(produces = { "application/json" })
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "beerName", required = false) String beerName,
                                                   @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle,
                                                   @RequestParam(value = "showInventory", required = false) Boolean showInventory){

        if (showInventory == null) showInventory = false;

        if (pageNumber == null || pageNumber < 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        BeerPagedList beerList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize), showInventory);

        return new ResponseEntity<>(beerList, HttpStatus.OK);
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDTO> getBeerById(@PathVariable("beerId")UUID beerId,
                                               @RequestParam(value = "showInventory", required = false) Boolean showInventory) {

        if (showInventory == null) showInventory = false;

        return new ResponseEntity<>(beerService.getById(beerId, false), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveNewBeer(@RequestBody @Valid BeerDTO beerDTO) {

        return new ResponseEntity<>(beerService.saveNewBeer(beerDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity updateBeerById(@PathVariable("beerId") UUID beerId, @RequestBody @Valid BeerDTO beerDTO) {

        return new ResponseEntity<>(beerService.updateBeer(beerId, beerDTO), HttpStatus.NO_CONTENT);
    }
}
