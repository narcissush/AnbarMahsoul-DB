import anbar.model.entity.Product;
import anbar.model.entity.enums.Brand;
import anbar.model.entity.enums.Os;

import java.time.LocalDate;

import anbar.model.repository.ProductDA;


public class ProductDATest {
    public static void main(String[] args) throws Exception {
        Product product =
                Product
                        .builder()
                        .id(1)
                        .brand(Brand.IPHONE)
                        .model("2020")
                        .os(Os.ANDROID)
                        .hasCharger(true)
                        .hasHeadset(false)
                        .price(1200)
                        .count(50)
                        .manufactureDate(LocalDate.now())
                        .build();

        try(ProductDA productDA = new ProductDA()){
            productDA.save(product);
        }

    }
}
