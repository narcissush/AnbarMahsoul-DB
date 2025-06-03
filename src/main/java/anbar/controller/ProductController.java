package anbar.controller;

import anbar.model.entity.Product;
import anbar.model.entity.enums.Brand;
import anbar.model.entity.enums.Os;
import anbar.model.repository.ProductDA;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;



@Log4j2
public class ProductController implements Initializable {

    @FXML private TextField productIdTxt, modelTxt, countTxt, priceTxt, searchBrandTxt, searchPriceTxt;
    @FXML private RadioButton iosRdo, androidRdo;
    @FXML private CheckBox hasChargerChk, hasHeadset;
    @FXML private ComboBox<Brand> brandCmb;
    @FXML private DatePicker manufactureDate;
    @FXML private Button saveBtn, editBtn, deleteBtn, SearchBtn, showAllBtn;
    @FXML private ToggleGroup osToggleGroup;

    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, Integer> productIdCol;
    @FXML private TableColumn<Product, String> modelCol;
    @FXML private TableColumn<Product, Integer> countCol, priceCol;
    @FXML private TableColumn<Product, Brand> brandCol;
    @FXML private TableColumn<Product, Os> osCol;
    @FXML private TableColumn<Product, LocalDate> manufactureDateCol;
    @FXML private TableColumn<Product, Boolean> hasChargerCol, hasHeadsetCol;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Fill ComboBox
        brandCmb.getItems().addAll(Brand.values());
        resetForm();

        saveBtn.setOnAction(event -> {
            try (ProductDA productDA = new ProductDA()) {
                RadioButton selectOsRadio = (RadioButton) osToggleGroup.getSelectedToggle();
                Product product = Product.builder()
                        .brand(brandCmb.getSelectionModel().getSelectedItem())
                        .model(modelTxt.getText())
                        .hasCharger(hasChargerChk.isSelected())
                        .hasHeadset(hasHeadset.isSelected())
                        .os(Os.valueOf(selectOsRadio.getText()))
                        .price(Integer.parseInt(priceTxt.getText()))
                        .count(Integer.parseInt(countTxt.getText()))
                        .manufactureDate(manufactureDate.getValue())
                        .build();

                productDA.save(product);
                log.info("Product Created: " + product);
                new Alert(Alert.AlertType.INFORMATION, "Product Saved", ButtonType.OK).show();
                resetForm();
            } catch (Exception e) {
                log.error("Error Saving Product: " + e.getMessage());
            }
        });

        editBtn.setOnAction(event -> {
            try (ProductDA productDA = new ProductDA()) {
                Os os = iosRdo.isSelected() ? Os.IOS : Os.ANDROID;

                Product product = Product.builder()
                        .id(Integer.parseInt(productIdTxt.getText()))
                        .model(modelTxt.getText())
                        .count(Integer.parseInt(countTxt.getText()))
                        .price(Integer.parseInt(priceTxt.getText()))
                        .hasCharger(hasChargerChk.isSelected())
                        .hasHeadset(hasHeadset.isSelected())
                        .brand(brandCmb.getSelectionModel().getSelectedItem())
                        .os(os)
                        .manufactureDate(manufactureDate.getValue())
                        .build();

                productDA.editProduct(product);
                log.info("Product Edited: " + product);
                new Alert(Alert.AlertType.INFORMATION, "Product Updated", ButtonType.OK).show();
                resetForm();
            } catch (Exception e) {
                log.error("Error Editing Product: " + e.getMessage());
            }
        });

        deleteBtn.setOnAction(event -> {
            try (ProductDA productDA = new ProductDA()) {
                int id = Integer.parseInt(productIdTxt.getText());
                productDA.removeProduct(id);
                new Alert(Alert.AlertType.INFORMATION, "Product Deleted", ButtonType.OK).show();
                log.info("Product Deleted: " + id);
                resetForm();
            } catch (Exception e) {
                log.error("Error Deleting Product: " + e.getMessage());
            }
        });

        SearchBtn.setOnAction(event -> {
//            try {
//                showProductsOnTable(productDA.getProductsByBrand(searchBrandTxt.getText(), Integer.parseInt(searchPriceTxt.getText())));
//            } catch (Exception e) {
//                log.error(e.getMessage());
//            }
        });

//        showAllBtn.setOnAction(event -> {
//
//            try {
//                showProductsOnTable(productDA.getAllProducts());
//            } catch (Exception e) {
//                log.error(e.getMessage());
//            }
//        });

        EventHandler<Event> tableChangeEvent = (mouseEvent) -> {
            Product selected = productTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                productIdTxt.setText(String.valueOf(selected.getId()));
                modelTxt.setText(selected.getModel());
                countTxt.setText(String.valueOf(selected.getCount()));
                priceTxt.setText(String.valueOf(selected.getPrice()));
                hasChargerChk.setSelected(selected.isHasCharger());
                hasHeadset.setSelected(selected.isHasHeadset());
                brandCmb.getSelectionModel().select(selected.getBrand());
                manufactureDate.setValue(selected.getManufactureDate());
                if (selected.getOs() == Os.IOS) iosRdo.setSelected(true);
                else androidRdo.setSelected(true);
            }
        };

        productTable.setOnMouseReleased(tableChangeEvent);
        productTable.setOnKeyReleased(tableChangeEvent);
    }

    private void resetForm() {
        try (ProductDA productDA = new ProductDA()) {

// todo : error            productIdTxt.setText(String.valueOf(productDA.getProductId()));
            modelTxt.clear();
            countTxt.clear();
            priceTxt.clear();
            searchBrandTxt.clear();
            searchPriceTxt.clear();
            brandCmb.getSelectionModel().selectFirst();
            iosRdo.setSelected(true);
            hasChargerChk.setSelected(false);
            hasHeadset.setSelected(false);
            manufactureDate.setValue(LocalDate.now());
            showProductsOnTable(productDA.getAllProducts());
        } catch (Exception e) {
            log.error("Error Resetting Form: " + e.getMessage());

        }
    }

    private void showProductsOnTable(List<Product> product) {

        ObservableList<Product> observableList = FXCollections.observableArrayList(product);

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        modelCol.setCellValueFactory(new PropertyValueFactory<>("model"));
        countCol.setCellValueFactory(new PropertyValueFactory<>("count"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
       brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
       osCol.setCellValueFactory(new PropertyValueFactory<>("os"));
        manufactureDateCol.setCellValueFactory(new PropertyValueFactory<>("datePick"));
        hasChargerCol.setCellValueFactory(new PropertyValueFactory<>("hasCharger"));
        hasHeadsetCol.setCellValueFactory(new PropertyValueFactory<>("hasHandsfree"));

        productTable.setItems(observableList);

    }
}
