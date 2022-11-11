package control;

import helper.FXUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.*;
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
public class AddProductController implements Initializable{
    /////////////////////
    // Local Variables //
    /////////////////////
    private final ObservableList<Part> ap = FXCollections.observableArrayList();
    @FXML
    private TextField addProductId;
    @FXML
    private TextField addProductName;
    @FXML
    private TextField addProductCost;
    @FXML
    private TextField addProductStock;
    @FXML
    private TextField addProductMin;
    @FXML
    private TextField addProductMax;
    @FXML
    private TextField addProductSearchText;
    @FXML
    private TableView<Part> addProductTopTV;
    @FXML
    private TableColumn<Part, Integer> addProductTopId;
    @FXML
    private TableColumn<Part, Integer> addProductTopName;
    @FXML
    private TableColumn<Part, Integer> addProductTopStock;
    @FXML
    private TableColumn<Part, Integer> addProductTopCost;
    @FXML
    private Button addProductAdd;
    @FXML
    private TableView<Part> addProductBotTV;
    @FXML
    private TableColumn<Part, Integer> addProductBotId;
    @FXML
    private TableColumn<Part, Integer> addProductBotName;
    @FXML
    private TableColumn<Part, Integer> addProductBotStock;
    @FXML
    private TableColumn<Product, Double> addProductBotCost;
    @FXML
    private Button addProductDel;
    @FXML
    private Button addProductSave;
    @FXML
    private Button addProductCancel;
    @FXML
    private Button addProductSearch;

    ////////////////////////////
    // JavaFX Action Handlers //
    ////////////////////////////

    /**
     *
     * @param event the event to be handled
     */
    @FXML
    public void addProductSearchTextHandler(KeyEvent event){
        if (addProductSearchText.getText().isBlank()){
            addProductTopTV.setItems(Inventory.getAllParts());
        }
    }

    /**
     *
     * @param event the event to be handled
     */
    @FXML
    public void addProductSearchHandler(ActionEvent event){
        if (addProductSearchText.getText().isBlank()){
            addProductTopTV.setItems(Inventory.getAllParts());
        }else{
            ObservableList<Part> allParts = Inventory.getAllParts();
            ObservableList<Part> ol = FXCollections.observableArrayList();
            String s = addProductSearchText.getText();

            for (Part p : allParts) {
                if (String.valueOf(p.getId()).contains(s) ||
                        p.getName().contains(s)) {
                    ol.add(p);
                }
            }

            addProductTopTV.setItems(ol);
        }
    }

    /**
     *
     * @param event the event to be handled
     */
    @FXML
    public void addProductAddHandler(ActionEvent event){
        Part selectedPart = addProductTopTV.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            alert(0);
        } else {
            ap.add(selectedPart);
            addProductBotTV.setItems(ap);
        }
    }

    /**
     *
     * @param event the event to be handled
     */
    @FXML
    public void addProductDelHandler(ActionEvent event){
        Part selectedPart = addProductBotTV.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            alert(1);
        } else {
            alert(2);
        }
    }

    /**
     *
     * @param event the event to be handled
     */
    @FXML
    public void addProductSaveHandler(ActionEvent event){
        int stock;
        int min;
        int max;
        double cost;
        String name;

        try{
            stock = Integer.parseInt(addProductStock.getText());
            min = Integer.parseInt(addProductMin.getText());
            max = Integer.parseInt(addProductMax.getText());
            cost = Double.parseDouble(addProductCost.getText());
            name = addProductName.getText();

            if (name.isBlank()){
                alert(3);
            }else if (!(min <= max)){
                alert(4);
            }else if ((stock < min) || (stock > max)){
                alert(5);
            }else if(ap.isEmpty()){
                alert(6);
            }else{
                Product p = new Product(Inventory.getNextId(), name, cost, stock, min, max);

                for (Part part : ap) {
                    p.addAssociatedPart(part);
                }

                Inventory.addProduct(p);
                returnTMS(event);
            }
        }catch(Exception e){
            alert(7);
        }
    }

    /**
     *
     * @param event the event to be handled
     */
    @FXML
    public void addProductCancelHandler(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Cancel changes and return to the main screen?");
        Optional<ButtonType> o = alert.showAndWait();

        if (o.isPresent() && o.get() == ButtonType.OK) {
            returnTMS(event);
        }
    }

    ////////////////////////////////////
    // AddProductController Functions //
    ////////////////////////////////////

    /**
     *
     * @param event the event to be handled
     * @throws IOException standard
     */
    private void returnTMS(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXUtil.loadView(stage, "mainView.fxml");
    }

    /**
     *
     * @param url standard
     * @param rb standard
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        //** Send cursor to name textfield
        addProductId.setFocusTraversable(false);

        //** Populate top table view
        addProductTopTV.setItems(Inventory.getAllParts());
        addProductTopId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        addProductTopName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        addProductTopStock.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        addProductTopCost.setCellValueFactory(new PropertyValueFactory<>("Cost"));

        //** Prepare bottom table view
        addProductBotId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        addProductBotName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        addProductBotStock.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        addProductBotCost.setCellValueFactory(new PropertyValueFactory<>("Cost"));
    }

    /////////////////////////////////
    // AddProductController Alerts //
    /////////////////////////////////

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
                alert.setContentText("Please select a part to add");
                alert.showAndWait();
            }
            case 1 -> {
                alert.setTitle("Error");
                alert.setHeaderText("** NO PART SELECTED **");
                alert.setContentText("Please select a part to delete");
                alert.showAndWait();
            }
            case 2 -> {
                Part selectedPart = addProductTopTV.getSelectionModel().getSelectedItem();
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Are you sure you want to disassociate the selected part?");
                Optional<ButtonType> o = alert.showAndWait();

                if (o.isPresent() && o.get() == ButtonType.OK) {
                    ap.remove(selectedPart);
                    addProductBotTV.setItems(ap);
                }
            }
            case 3 -> {
                alert.setTitle("Error");
                alert.setHeaderText("** NO NAME GIVEN **");
                alert.setContentText("Please give the product a name");
                alert.showAndWait();
            }
            case 4 -> {
                alert.setTitle("Error");
                alert.setHeaderText("** INVALID RANGE **");
                alert.setContentText("Minimum must be less than or equal to Maximum");
                alert.showAndWait();
            }
            case 5 -> {
                alert.setTitle("Error");
                alert.setHeaderText("** INVALID RANGE **");
                alert.setContentText("Stock must be between Minimum and Maximum");
                alert.showAndWait();
            }
            case 6 -> {
                alert.setTitle("Error");
                alert.setHeaderText("** EMPTY PRODUCT **");
                alert.setContentText("Please add a part to the product");
                alert.showAndWait();
            }
            case 7 -> {
                alert.setTitle("Error");
                alert.setHeaderText("** INVALID INPUT **");
                alert.setContentText("Please review the form for missing/incorrect values");
                alert.showAndWait();
            }
        }
    }
}

