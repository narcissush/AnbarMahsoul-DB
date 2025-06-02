package anbar.controller;

import anbar.model.entity.Product;
import anbar.model.entity.enums.Brand;
import anbar.model.entity.enums.Os;
import anbar.model.repository.ProductDataAccess;
import anbar.model.repository.ProductDataFileManager;
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
    @FXML private CheckBox hasChargerChk, hasHansfreeChk;
    @FXML private ComboBox<Brand> brandCmb;
    @FXML private DatePicker datePick;
    @FXML private Button saveBtn, editBtn, deleteBtn, SearchBtn, showAllBtn;
    @FXML private ToggleGroup osToggleGroup;

    @FXML private TableView<Product> productTable;
    @FXML private TableColumn<Product, Integer> productIdCol;
    @FXML private TableColumn<Product, String> modelCol;
    @FXML private TableColumn<Product, Integer> countCol, priceCol;
    @FXML private TableColumn<Product, Brand> brandCol;
    @FXML private TableColumn<Product, Os> osCol;
    @FXML private TableColumn<Product, LocalDate> dateCol;
    @FXML private TableColumn<Product, Boolean> hasChargerCol, hasHandsfreeCol;

    private ProductDataAccess productDataAccess = new ProductDataAccess();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Fill ComboBox
        brandCmb.getItems().addAll(Brand.values());
        resetForm();

        saveBtn.setOnAction(event -> {
            try {
                RadioButton selectOsRadio = (RadioButton) osToggleGroup.getSelectedToggle();
                Product product = Product.builder()
                        .productId(Integer.parseInt(productIdTxt.getText()))
                        .model(modelTxt.getText())
                        .count(Integer.parseInt(countTxt.getText()))
                        .price(Integer.parseInt(priceTxt.getText()))
                        .hasCharger(hasChargerChk.isSelected())
                        .hasHandsfree(hasHansfreeChk.isSelected())
                        .brand(brandCmb.getSelectionModel().getSelectedItem())
                        .os(Os.valueOf(selectOsRadio.getText()))
                        .datePick(datePick.getValue())
                        .build();

                productDataAccess.saveProduct(product);
                log.info("Product Created: " + product);
                new Alert(Alert.AlertType.INFORMATION, "Product Saved", ButtonType.OK).show();
                resetForm();
            } catch (Exception e) {
                log.error("Error Saving Product: " + e.getMessage());
                e.printStackTrace();
            }
        });

        editBtn.setOnAction(event -> {
            try {
                Os os = iosRdo.isSelected() ? Os.IOS : Os.ANDROID;

                Product product = Product.builder()
                        .productId(Integer.parseInt(productIdTxt.getText()))
                        .model(modelTxt.getText())
                        .count(Integer.parseInt(countTxt.getText()))
                        .price(Integer.parseInt(priceTxt.getText()))
                        .hasCharger(hasChargerChk.isSelected())
                        .hasHandsfree(hasHansfreeChk.isSelected())
                        .brand(brandCmb.getSelectionModel().getSelectedItem())
                        .os(os)
                        .datePick(datePick.getValue())
                        .build();

                productDataAccess.editProduct(product);
                log.info("Product Edited: " + product);
                new Alert(Alert.AlertType.INFORMATION, "Product Updated", ButtonType.OK).show();
                resetForm();
            } catch (Exception e) {
                log.error("Error Editing Product: " + e.getMessage());
                e.printStackTrace();
            }
        });

        deleteBtn.setOnAction(event -> {
            try {
                int id = Integer.parseInt(productIdTxt.getText());
                productDataAccess.removeProduct(id);
                new Alert(Alert.AlertType.INFORMATION, "Product Deleted", ButtonType.OK).show();
                log.info("Product Deleted: " + id);
                resetForm();
            } catch (Exception e) {
                log.error("Error Deleting Product: " + e.getMessage());
            }
        });

        SearchBtn.setOnAction(event -> {
            try {
                showProductsOnTable(productDataAccess.getProductsByBrand(searchBrandTxt.getText(), Integer.parseInt(searchPriceTxt.getText())));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });

        showAllBtn.setOnAction(event -> {

            try {
                showProductsOnTable(productDataAccess.getAllProducts());
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });

        EventHandler<Event> tableChangeEvent = (mouseEvent) -> {
            Product selected = productTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                productIdTxt.setText(String.valueOf(selected.getProductId()));
                modelTxt.setText(selected.getModel());
                countTxt.setText(String.valueOf(selected.getCount()));
                priceTxt.setText(String.valueOf(selected.getPrice()));
                hasChargerChk.setSelected(selected.isHasCharger());
                hasHansfreeChk.setSelected(selected.isHasHandsfree());
                brandCmb.getSelectionModel().select(selected.getBrand());
                datePick.setValue(selected.getDatePick());
                if (selected.getOs() == Os.IOS) iosRdo.setSelected(true);
                else androidRdo.setSelected(true);
            }
        };

        productTable.setOnMouseReleased(tableChangeEvent);
        productTable.setOnKeyReleased(tableChangeEvent);
    }

    private void resetForm() {


        productIdTxt.setText(String.valueOf(ProductDataFileManager.getManager().getNextId()));
        modelTxt.clear();
        countTxt.clear();
        priceTxt.clear();
        searchBrandTxt.clear();
        searchPriceTxt.clear();
        brandCmb.getSelectionModel().selectFirst();
        iosRdo.setSelected(true);
        hasChargerChk.setSelected(false);
        hasHansfreeChk.setSelected(false);
        datePick.setValue(LocalDate.now());

        try {
            showProductsOnTable(productDataAccess.getAllProducts());
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
        dateCol.setCellValueFactory(new PropertyValueFactory<>("datePick"));
        hasChargerCol.setCellValueFactory(new PropertyValueFactory<>("hasCharger"));
        hasHandsfreeCol.setCellValueFactory(new PropertyValueFactory<>("hasHandsfree"));

        productTable.setItems(observableList);

    }
}
