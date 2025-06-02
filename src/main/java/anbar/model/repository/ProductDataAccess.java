package anbar.model.repository;

import lombok.extern.log4j.Log4j2;
import anbar.model.entity.Product;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class ProductDataAccess {

    public void saveProduct(Product product) throws IOException {
        ProductDataFileManager.getManager().addProduct(product);
        ProductDataFileManager.getManager().saveToFile();
        log.info("Product saved: " + product);
    }

    public void editProduct(Product product) throws IOException {
        List<Product> list = ProductDataFileManager.getManager().getProductList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getProductId() == product.getProductId()) {
                list.set(i, product);
                break;
            }
        }
        ProductDataFileManager.getManager().saveToFile();
        log.info("Product edited: " + product);
    }

    public void removeProduct(int id) throws IOException {
        List<Product> list = ProductDataFileManager.getManager().getProductList();
        list.removeIf(p -> p.getProductId() == id);
        ProductDataFileManager.getManager().saveToFile();
        log.info("Product removed: " + id);
    }

    public List<Product> getAllProducts() throws IOException, ClassNotFoundException {

        return ProductDataFileManager.getManager().readFromFile();
    }


    public List<Product> getProductsByModel(String model) throws IOException, ClassNotFoundException {
        return getAllProducts().stream()
                .filter(p -> p.getModel().toLowerCase().contains(model.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsByBrand(String brandName, int price) throws IOException, ClassNotFoundException {
        return getAllProducts().stream()
                .filter(p -> p.getBrand().toString().toLowerCase().contains(brandName.toLowerCase()) && p.getPrice() == price)
                .collect(Collectors.toList());
    }

}
