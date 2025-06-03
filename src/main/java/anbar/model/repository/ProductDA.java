package anbar.model.repository;

import anbar.model.entity.enums.Brand;
import anbar.model.entity.enums.Os;
import lombok.extern.log4j.Log4j2;
import anbar.model.entity.Product;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class ProductDA implements AutoCloseable {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public ProductDA() throws SQLException{
        log.info("Connected");
        connection =ConnectionProvider.getGetConnectionProvider().getconnection();
    }


    public void saveProduct(Product product) throws SQLException {
        preparedStatement= connection.prepareStatement("insert into Product values (?,?,?,?,?,?,?,?,?)");
        preparedStatement.setInt(1,product.getProductId());
        preparedStatement.setString(2,product.getBrand().name());
        preparedStatement.setString(3,product.getModel());
        preparedStatement.setString(4,product.getOs().name());
        preparedStatement.setBoolean(5, product.isHasCharger());
        preparedStatement.setBoolean(6, product.isHasHandsfree());
        preparedStatement.setInt(7,product.getPrice());
        preparedStatement.setInt(8,product.getCount());
        preparedStatement.setDate(9, Date.valueOf(product.getDatePick()));
        preparedStatement.execute();
        log.info("Product saved: " + product);
    }

    public void editProduct(Product product) throws SQLException {
        preparedStatement= connection.prepareStatement("update Product set brand=?,model=?,os=?,hascharger=?,hashansfree=?,price=?,count=?,birth_date=? where id=?");

        preparedStatement.setString(1,product.getBrand().name());
        preparedStatement.setString(2,product.getModel());
        preparedStatement.setString(3,product.getOs().name());
        preparedStatement.setBoolean(4, product.isHasCharger());
        preparedStatement.setBoolean(5, product.isHasHandsfree());
        preparedStatement.setInt(6,product.getPrice());
        preparedStatement.setInt(7,product.getCount());
        preparedStatement.setDate(8, Date.valueOf(product.getDatePick()));
        preparedStatement.setInt(9,product.getProductId());
        preparedStatement.execute();
        log.info("Product edited: " + product);
    }

    public void removeProduct(int id) throws SQLException {
        preparedStatement= connection.prepareStatement("delete from product where id=?");

        preparedStatement.setInt(1,id);
        preparedStatement.execute();
        log.info("Product removed: " + id);
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> productsList = new ArrayList<>();
        preparedStatement= connection.prepareStatement("select * from product");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            Product product = Product.builder()
                    .productId(resultSet.getInt("id"))
                    .brand(Brand.valueOf(resultSet.getString("brand")))
                    .model(resultSet.getString("model"))
                    .os(Os.valueOf(resultSet.getString("os")))
                    .price(resultSet.getInt("price"))
                    .count(resultSet.getInt("count"))
                    .hasHandsfree(resultSet.getBoolean("hashansfree"))
                    .hasCharger(resultSet.getBoolean("hascharger"))
                    .datePick(resultSet.getDate("birth_date").toLocalDate())
                    .build();
            productsList.add(product);
        }
        return productsList;

    }
    public int getProductId() throws SQLException {
        preparedStatement= connection.prepareStatement("SELECT COALESCE(MAX(id), 0) + 1 AS next_id FROM product");
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("next_id");
        } else {
            throw new SQLException("Cannot get next product ID.");
        }
    }


    public void getProductsByBrand(String model) throws SQLException {
        preparedStatement= connection.prepareStatement("select * from product where brand='ios'");

        preparedStatement.execute();
    }


    @Override
    public void close() throws Exception {
        System.out.printf("close");
connection.close();
preparedStatement.close();
    }
}
