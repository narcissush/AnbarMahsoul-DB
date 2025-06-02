package anbar.model.repository;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import anbar.model.entity.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ProductDataFileManager {
    private final String FILE_NAME = "src/main/java/anbar/model/repository/datafile/product.dat";
    private final File file = new File(FILE_NAME);
    private static int nextId;

    private static List<Product> productList;

    @Getter
    private static final ProductDataFileManager manager = new ProductDataFileManager();

    private ProductDataFileManager() {}

    public List<Product> getProductList() {
        return productList;
    }

    public void addProduct(Product product) {
        if (productList == null) {
            productList = new ArrayList<>();
        }
        productList.add(product);
    }

    public int getNextId() {
        try {
            productList = readFromFile();
            return productList.size() + 1;
        } catch (Exception e) {
            log.error(e);
        }
        return 0;
    }



    public void saveToFile() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(productList);

        objectOutputStream.close();
        fileOutputStream.close();
        log.info("Data Saved To File");
    }

    public List<Product> readFromFile() throws IOException, ClassNotFoundException {
       // file.getParentFile().mkdirs();

            FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            productList = (ArrayList<Product>) objectInputStream.readObject();

        objectInputStream.close();
            fileInputStream.close();
            log.info("Data Read From File");

        return productList;


    }
}
