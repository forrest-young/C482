package control;

import helper.FXUtil;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
public class ModProductController implements Initializable{
    /////////////////////
    // Local Variables //
    /////////////////////
    private ObservableList<Part> ap = FXCollections.observableArrayList();
    @FXML
    private TextField modProductId;
    @FXML
    private TextField modProductName;
    @FXML
    private TextField modProductCost;
    @FXML
    private TextField modProductStock;
    @FXML
    private TextField modProductMin;
    @FXML
    private TextField modProductMax;
    @FXML
    private TextField modProductSearchText;
    @FXML
    private TableView<Part> modProductTopTV;
    @FXML
    private TableColumn<Part, Integer> modProductTopId;
    @FXML
    private TableColumn<Part, Integer> modProductTopName;
    @FXML
    private TableColumn<Part, Integer> modProductTopStock;
    @FXML
    private TableColumn<Part, Integer> modProductTopCost;
    @FXML
    private Button modProductAdd;
    @FXML
    private TableView<Part> modProductBotTV;
    @FXML
    private TableColumn<Part, Integer> modProductBotId;
    @FXML
    private TableColumn<Part, Integer> modProductBotName;
    @FXML
    private TableColumn<Part, Integer> modProductBotStock;
    @FXML
    private TableColumn<Part, Integer> modProductBotCost;
    @FXML
    private Button modProductDel;
    @FXML
    private Button modProductSave;
    @FXML
    private Button modProductCancel;
    @FXML
    private Button modProductSearch;

    ////////////////////////////
    // JavaFX Action Handlers //
    ////////////////////////////

    /**
     *
     * @param event the event to be handled
     */
    @FXML
    public void modProductSearchTextHandler(KeyEvent event){
        if (modProductSearchText.getText().isBlank()){
            modProductTopTV.setItems(Inventory.getAllParts());
        }
    }

    /**
     *
     * @param event the event to be handled
     */
    @FXML
    public void modProductSearchHandler(ActionEvent event){
        if (modProductSearchText.getText().isBlank()){
            modProductTopTV.setItems(Inventory.getAllParts());
        }else{
            ObservableList<Part> allParts = Inventory.getAllParts();
            ObservableList<Part> ol = FXCollections.observableArrayList();
            String s = modProductSearchText.getText();

            for (Part p : allParts) {
                if (String.valueOf(p.getId()).contains(s) ||
                        p.getName().contains(s)) {
                    ol.add(p);
                }
            }

            modProductTopTV.setItems(ol);
        }
    }

    /**
     *
     * @param event the event to be handled
     */
    @FXML
    public void modProductAddHandler(ActionEvent event){
        Part selectedPart = modProductTopTV.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            alert(0);
        } else {
            ap.add(selectedPart);
            modProductBotTV.setItems(ap);
        }
    }

    /**
     *
     * @param event the event to be handled
     */
    @FXML
    public void modProductDelHandler(ActionEvent event){
        Part selectedPart = modProductBotTV.getSelectionModel().getSelectedItem();

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
    public void modProductSaveHandler(ActionEvent event){
        int stock;
        int min;
        int max;
        double cost;
        String name;

        try{
            stock = Integer.parseInt(modProductStock.getText());
            min = Integer.parseInt(modProductMin.getText());
            max = Integer.parseInt(modProductMax.getText());
            cost = Double.parseDouble(modProductCost.getText());
            name = modProductName.getText();

            if (name.isBlank()){
                alert(3);
            }else if (!(min <= max)){
                alert(4);
            }else if ((stock < min) || (stock > max)){
                alert(5);
            }else if (ap.isEmpty()){
                alert(6);
            }else{
                Product sp = MainController.getProduct();
                int id = sp.getId();
                Inventory.deleteProduct(sp);

                Product p = new Product(id, name, cost, stock, min, max);

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
    public void modProductCancelHandler(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Cancel changes and return to the main screen?");
        Optional<ButtonType> o = alert.showAndWait();

        if (o.isPresent() && o.get() == ButtonType.OK) {
            returnTMS(event);
        }
    }

    ////////////////////////////////////
    // ModProductController Functions //
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
        modProductId.setFocusTraversable(false);

        //** Extract associated parts
        Product selectedProduct = MainController.getProduct();
        ap = selectedProduct.getAllAssociatedParts();

        //** Populate top table view
        modProductTopId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        modProductTopName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        modProductTopStock.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        modProductTopCost.setCellValueFactory(new PropertyValueFactory<>("Cost"));
        modProductTopTV.setItems(Inventory.getAllParts());

        //** Populate bottom table view
        modProductBotId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        modProductBotName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        modProductBotStock.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        modProductBotCost.setCellValueFactory(new PropertyValueFactory<>("Cost"));
        modProductBotTV.setItems(ap);

        modProductId.setText(String.valueOf(selectedProduct.getId()));
        modProductName.setText(String.valueOf(selectedProduct.getName()));
        modProductStock.setText(String.valueOf(selectedProduct.getStock()));
        modProductCost.setText(String.valueOf(selectedProduct.getCost()));
        modProductMin.setText(String.valueOf(selectedProduct.getMin()));
        modProductMax.setText(String.valueOf(selectedProduct.getMax()));
    }

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
                Part selectedPart = modProductBotTV.getSelectionModel().getSelectedItem();
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("Are you sure you want to disassociate the selected part?");
                Optional<ButtonType> o = alert.showAndWait();

                if (o.isPresent() && o.get() == ButtonType.OK) {
                    ap.remove(selectedPart);
                    modProductBotTV.setItems(ap);
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
