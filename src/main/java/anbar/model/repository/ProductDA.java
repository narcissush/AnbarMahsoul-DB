package anbar.model.repository;

import anbar.model.entity.enums.Brand;
import anbar.model.entity.enums.Os;
import lombok.extern.log4j.Log4j2;
import anbar.model.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ProductDA implements AutoCloseable {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public ProductDA() throws SQLException {
        log.info("Connection DB Connected");
        connection = ConnectionProvider.getConnectionProvider().getconnection();
    }
public int nextId() throws SQLException {
    preparedStatement = connection.prepareStatement("select product_seq.nextval from DUAL");
    ResultSet resultSet = preparedStatement.executeQuery();
    resultSet.next();
    return resultSet.getInt("nextval");
}

    public void save(Product product) throws SQLException {

        preparedStatement = connection.prepareStatement("insert into Products values (?,?,?,?,?,?,?,?,?)");

        preparedStatement.setInt(1,product.getId());
        preparedStatement.setString(2, product.getBrand().name());
        preparedStatement.setString(3, product.getModel());
        preparedStatement.setString(4, product.getOs().name());
        preparedStatement.setBoolean(5, product.isHasCharger());
        preparedStatement.setBoolean(6, product.isHasHeadset());
        preparedStatement.setInt(7, product.getPrice());
        preparedStatement.setInt(8, product.getCount());
        preparedStatement.setDate(9, Date.valueOf(product.getManufactureDate()));
        preparedStatement.execute();
        log.info("Product saved: " + product);
    }

    public void edit(Product product) throws SQLException {
        preparedStatement = connection.prepareStatement("update Products set brand=?,model=?,os=?,has_charger=?,has_headset=?,price=?,count=?,manufacture_date=? where id=?");

        preparedStatement.setString(1, product.getBrand().name());
        preparedStatement.setString(2, product.getModel());
        preparedStatement.setString(3, product.getOs().name());
        preparedStatement.setBoolean(4, product.isHasCharger());
        preparedStatement.setBoolean(5, product.isHasHeadset());
        preparedStatement.setInt(6, product.getPrice());
        preparedStatement.setInt(7, product.getCount());
        preparedStatement.setDate(8, Date.valueOf(product.getManufactureDate()));
        preparedStatement.setInt(9, product.getId());
        preparedStatement.execute();
        log.info("Product edited: " + product);
    }

    public void remove(int id) throws SQLException {
        preparedStatement = connection.prepareStatement("delete from products where id=?");
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        log.info("Product removed: " + id);
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> productsList = new ArrayList<>();
        preparedStatement = connection.prepareStatement("select * from products");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Product product = Product.builder()
                    .id(resultSet.getInt("id"))
                    .brand(Brand.valueOf(resultSet.getString("brand")))
                    .model(resultSet.getString("model"))
                    .os(Os.valueOf(resultSet.getString("os")))
                    .price(resultSet.getInt("price"))
                    .count(resultSet.getInt("count"))
                    .hasHeadset(resultSet.getBoolean("has_headset"))
                    .hasCharger(resultSet.getBoolean("has_charger"))
                    .manufactureDate(resultSet.getDate("manufacture_date").toLocalDate())
                    .build();
            productsList.add(product);
        }
        return productsList;
    }

    public List<Product> getProductsByBrand_Price(int price1,int price2) throws SQLException {
        List<Product> productsList = new ArrayList<>();
        preparedStatement = connection.prepareStatement("select * from Products where price between ? and ?");

        preparedStatement.setInt(1, price1);
        preparedStatement.setInt(2, price2);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Product product = Product.builder()
                    .id(resultSet.getInt("id"))
                    .brand(Brand.valueOf(resultSet.getString("brand")))
                    .model(resultSet.getString("model"))
                    .os(Os.valueOf(resultSet.getString("os")))
                    .price(resultSet.getInt("price"))
                    .count(resultSet.getInt("count"))
                    .hasHeadset(resultSet.getBoolean("has_headset"))
                    .hasCharger(resultSet.getBoolean("has_charger"))
                    .manufactureDate(resultSet.getDate("manufacture_date").toLocalDate())
                    .build();
            productsList.add(product);
        }
        return productsList;
    }



    @Override
    public void close() throws Exception {
        log.info("Connection DB Closed");
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        connection.close();
    }
}
