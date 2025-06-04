import anbar.model.entity.Product;
import anbar.model.entity.enums.Brand;
import anbar.model.entity.enums.Os;

import java.time.LocalDate;

import anbar.model.repository.ProductDA;


public class ProductDATest {
    public static void main(String[] args) throws Exception {
        try(ProductDA productDA = new ProductDA()){

//        Product product =
//                Product
//                        .builder()
//                        .id(productDA.nextId())
//                        .brand(Brand.IPHONE)
//                        .model("2020")
//                        .os(Os.ANDROID)
//                        .hasCharger(true)
//                        .hasHeadset(false)
//                        .price(1200)
//                        .count(50)
//                        .manufactureDate(LocalDate.now())
//                        .build();


           // productDA.save(product);
            productDA.remove(14);
          //  System.out.println(product);
        }

    }
}
