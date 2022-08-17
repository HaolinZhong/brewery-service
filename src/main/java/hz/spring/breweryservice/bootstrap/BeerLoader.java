package hz.spring.breweryservice.bootstrap;

import hz.spring.breweryservice.domain.Beer;
import hz.spring.breweryservice.repository.BeerRepository;
import hz.spring.common.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class BeerLoader implements CommandLineRunner {

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";

    public static final UUID BEER_1_UUID = UUID.fromString("095c83ee-c1bf-4933-8389-21bb6cedcd3f");
    public static final UUID BEER_2_UUID = UUID.fromString("259a01d9-d16e-403a-98a3-00df8983f731");
    public static final UUID BEER_3_UUID = UUID.fromString("9ad82b04-9660-4683-ab21-e134cd400565");

    private final BeerRepository beerRepository;

    @Override
    public void run(String... args) throws Exception {

        if (beerRepository.count() == 0) {
            loadBeerObjects();
        }

    }

    private void loadBeerObjects() {

        beerRepository.save(
                Beer.builder()
                        .id(BEER_1_UUID)
                        .beerName("Mango Bobs")
                        .beerStyle(BeerStyleEnum.IPA.name())
                        .quantityToBrew(200)
                        .minOnHand(12)
                        .upc(BEER_1_UPC)
                        .price(new BigDecimal("12.95"))
                        .build()
        );

        beerRepository.save(
                Beer.builder()
                        .id(BEER_2_UUID)
                        .beerName("Galaxy Cat")
                        .beerStyle(BeerStyleEnum.PALE_ALE.name())
                        .quantityToBrew(200)
                        .minOnHand(12)
                        .upc(BEER_2_UPC)
                        .price(new BigDecimal("11.95"))
                        .build()
        );

        beerRepository.save(
                Beer.builder()
                        .id(BEER_3_UUID)
                        .beerName("Pinball Porter")
                        .beerStyle(BeerStyleEnum.PALE_ALE.name())
                        .minOnHand(12)
                        .quantityToBrew(200)
                        .price(new BigDecimal("12.95"))
                        .upc(BEER_3_UPC)
                        .build()
        );

        System.out.println("Loaded Beers: " + beerRepository.count());
    }
}
