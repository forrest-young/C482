package control;

import helper.FXUtil;
import javafx.fxml.FXML;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Forrest Young
 */
public class MainController implements Initializable{
    /////////////////////
    // Local Variables //
    /////////////////////
    private static Part selectedPart;
    private static Product selectedProduct;

    @FXML private TextField partSearchBox;
    @FXML private TableView<Part> partsTV;
    @FXML private TableColumn<Part, Integer> partIdCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, Integer> partStockCol;
    @FXML private TableColumn<Part, Double> partCostCol;

    @FXML private TextField productSearchBox;
    @FXML private TableView<Product> productsTV;
    @FXML private TableColumn<Product, Integer> productIdCol;
    @FXML private TableColumn<Product, String> productNameCol;
    @FXML private TableColumn<Product, Integer> productStockCol;
    @FXML private TableColumn<Product, Double> productCostCol;

    @FXML private Button mainPartAdd;
    @FXML private Button mainPartMod;
    @FXML private Button mainPartDel;
    @FXML private Button mainPartSearch;
    @FXML private Button mainProductAdd;
    @FXML private Button mainProductMod;
    @FXML private Button mainProductDel;
    @FXML private Button mainProductSearch;
    @FXML private Button mainExit;

    ////////////////////////////
    // JavaFX Action Handlers //
    ////////////////////////////

    /**
     *
     * @param event the event to be handled
     */
    @FXML
    public void mainPartSearchBoxHandler(KeyEvent event){
        if (partSearchBox.getText().isBlank()){
            partsTV.setItems(Inventory.getAllParts());
        }
    }

    /**
     *
     * @param event the event to be handled
     */
    @FXML
    public void mainPartSearchHandler(ActionEvent event){
        if (partSearchBox.getText().isBlank()){
            partsTV.setItems(Inventory.getAllParts());
        }else{
            ObservableList<Part> allParts = Inventory.getAllParts();
            ObservableList<Part> ol = FXCollections.observableArrayList();
            String s = partSearchBox.getText();

            for (Part p : allParts) {
                if (String.valueOf(p.getId()).contains(s) ||
                        p.getName().contains(s)) {
                    ol.add(p);
                }
            }

            partsTV.setItems(ol);
        }
    }

    /**
     *
     * @param event the event to be handled
     */
    @FXML
    public void mainPartAddHandler(ActionEvent event) throws IOException{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXUtil.loadView(stage, "addPartView.fxml");
    }

    /**
     *
     * @param event the event to be handled
     */
    @FXML
    public void mainPartModHandler(ActionEvent event) throws IOException{
        // Runtime error occurs if no part is selected
        selectedPart = partsTV.getSelectionModel().getSelectedItem();

        // Solution to runtime error: Validate selection prior to variable assignment
        if (selectedPart == null){
            alert(0);
        }else{
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXUtil.loadView(stage, "modPartView.fxml");
        }
    }

    /**
     *
     * @param event the event to be handled
     */
    @FXML
    public void mainPartDelHandler(ActionEvent event){
        selectedPart = partsTV.getSelectionModel().getSelectedItem();

        if (selectedPart == null){
            alert(1);
        }else{
            alert(2);
        }
    }

    /**
     *
     * @param event the event to be handled
     */
    @FXML
    public void mainProductSearchBoxHandler(KeyEvent event){
        if (productSearchBox.getText().isBlank()){
            productsTV.setItems(Inventory.getAllProducts());
        }
    }

    /**
     *
     * @param event the event to be handled
     */
    @FXML
    public void mainProductSearchHandler(ActionEvent event){
        if (productSearchBox.getText().isBlank()){
            productsTV.setItems(Inventory.getAllProducts());
        }else{
            ObservableList<Product> allProducts = Inventory.getAllProducts();
            ObservableList<Product> ol = FXCollections.observableArrayList();
            String s = partSearchBox.getText();

            for (Product p : allProducts) {
                if (String.valueOf(p.getId()).contains(s) ||
                        p.getName().contains(s)) {
                    ol.add(p);
                }
            }

            productsTV.setItems(ol);
        }
    }

    /**
     *
     * @param event the event to be handled
     */
    @FXML
    public void mainProductAddHandler(ActionEvent event) throws IOException{
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXUtil.loadView(stage, "addProductView.fxml");
    }

    /**
     *
     * @param event the event to be handled
     */
    @FXML
    public void mainProductModHandler(ActionEvent event) throws IOException{
        // Runtime error occurs if no product is selected
        selectedProduct = productsTV.getSelectionModel().getSelectedItem();

        // Solution to runtime error: Validate selection prior to variable assignment
        if (selectedProduct == null){
            alert(3);
        }else{
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXUtil.loadView(stage, "modProductView.fxml");
        }
    }

    /**
     *
     * @param event the event to be handled
     */
    @FXML
    public void mainProductDelHandler(ActionEvent event){
        // Runtime error occurs if no product is selected
        selectedProduct = productsTV.getSelectionModel().getSelectedItem();

        // Solution to runtime error: Validate selection prior to variable assignment
        if (selectedProduct == null){
            alert(4);
        }else{
            alert(5);
        }
    }

    /**
     *
     * @param event the event to be handled
     */
    @FXML
    public void mainExitHandler(ActionEvent event){ System.exit(0); }

    //////////////////////////////
    // MainController Functions //
    //////////////////////////////

    /**
     * @return the selected part
     */
    public static Part getPart(){ return selectedPart; }

    /**
     * @return the selected product
     */
    public static Product getProduct(){ return selectedProduct; }

    /**
     *
     * @param url standard
     * @param rb standard
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        //** Populate parts table view
        partsTV.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        partStockCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        partCostCol.setCellValueFactory(new PropertyValueFactory<>("Cost"));

        //** Populate products table view
        productsTV.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        productStockCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        productCostCol.setCellValueFactory(new PropertyValueFactory<>("Cost"));
    }

    ///////////////////////////
    // MainController Alerts //
    ///////////////////////////

    /**
     *
     * @param alertType the alert to be sent
     */
    private void alert(int alertType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 0 -> {
                alert.setTitle("Error");
                alert.setHeaderText("** NO PART SELECTED **");
                alert.setContentText("Please select a part to modify");
                alert.showAndWait();
            }
            case 1 -> {
                alert.setTitle("Error");
                alert.setHeaderText("** NO PART SELECTED **");
                alert.setContentText("Please select a part to delete");
                alert.showAndWait();
            }
            case 2 -> {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Are you sure you want to delete the selected part?");
                Optional<ButtonType> o = alert.showAndWait();

                if (o.isPresent() && o.get() == ButtonType.OK) {
                    Inventory.deletePart(selectedPart);
                }
            }
            case 3 -> {
                alert.setTitle("Error");
                alert.setHeaderText("** NO PRODUCT SELECTED **");
                alert.setContentText("Please select a product to modify");
                alert.showAndWait();
            }
            case 4 -> {
                alert.setTitle("Error");
                alert.setHeaderText("** NO PRODUCT SELECTED **");
                alert.setContentText("Please select a product to delete");
                alert.showAndWait();
            }
            case 5 -> {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Are you sure you want to delete the selected product?");
                Optional<ButtonType> o = alert.showAndWait();

                if (o.isPresent() && o.get() == ButtonType.OK) {
                    ObservableList<Part> ol = selectedProduct.getAllAssociatedParts();

                    if (ol.size() >= 1) {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("** NON-EMPTY PRODUCT **");
                        alert.setContentText("Please remove all associated parts from selected product");
                        alert.showAndWait();
                    } else {
                        Inventory.deleteProduct(selectedProduct);
                    }
                }
            }
        }
    }
}