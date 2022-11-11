package control;

import helper.FXUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Forrest Young
 */
public class AddPartController implements Initializable {
    /////////////////////
    // Local Variables //
    /////////////////////
    @FXML private Label addPartLabel;

    @FXML private RadioButton addPartInHouse;
    @FXML private ToggleGroup partSource;
    @FXML private RadioButton addPartOutsource;

    @FXML private TextField addPartId;
    @FXML private TextField addPartLabelText;
    @FXML private TextField addPartName;
    @FXML private TextField addPartCost;
    @FXML private TextField addPartStock;
    @FXML private TextField addPartMin;
    @FXML private TextField addPartMax;

    @FXML private Button addPartSave;
    @FXML private Button addPartCancel;

    ////////////////////////////
    // JavaFX Action Handlers //
    ////////////////////////////

    /**
     *
     * @param event the event to be handled
     */
    @FXML
    public void addPartInHouseHandler(ActionEvent event){ addPartLabel.setText("Machine ID"); }

    /**
     *
     * @param event the event to be handled
     */
    @FXML
    public void addPartOutsourceHandler(ActionEvent event){ addPartLabel.setText("Company"); }

    /**
     *
     * @param event the event to be handled
     */
    @FXML
    public void addPartSaveHandler(ActionEvent event){
        //** Input Variables
        int id;
        int stock;
        int min;
        int max;
        double cost;
        String name;
        String company;

        //** Check radio selection
        if (addPartInHouse.isSelected()){
            name = addPartName.getText();

            //** Validate Input
            try{
                stock = Integer.parseInt(addPartStock.getText());
                min = Integer.parseInt(addPartMin.getText());
                max = Integer.parseInt(addPartMax.getText());
                cost = Double.parseDouble(addPartCost.getText());
                id = Integer.parseInt(addPartLabelText.getText());

                if (!(min < max)){
                    alert(1);
                }else if ((stock < min) || (stock > max)){
                    alert(2);
                }else{
                    InHouse ih = new InHouse(Inventory.getNextId(), name, cost, stock, min, max, id);
                    Inventory.addPart(ih);
                    returnTMS(event);
                }
            }catch(Exception e){
                alert(0);
            }
        }else{
            name = addPartName.getText();
            company = addPartLabelText.getText();

            try{
                stock = Integer.parseInt(addPartStock.getText());
                min = Integer.parseInt(addPartMin.getText());
                max = Integer.parseInt(addPartMax.getText());
                cost = Double.parseDouble(addPartCost.getText());

                if (!(min < max)){
                    alert(1);
                }else if ((stock < min) || (stock > max)){
                    alert(2);
                }else{
                    Outsourced os = new Outsourced(Inventory.getNextId(), name, cost, stock, min, max, company);
                    Inventory.addPart(os);
                    returnTMS(event);
                }
            }catch(Exception e){
                alert(0);
            }
        }
    }

    /**
     *
     * @param event the event to be handled
     * @throws IOException standard
     */
    @FXML
    public void addPartCancelHandler(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Cancel changes and return to the main screen?");
        Optional<ButtonType> o = alert.showAndWait();

        if (o.isPresent() && o.get() == ButtonType.OK) {
            returnTMS(event);
        }
    }

    /////////////////////////////////
    // AddPartController Functions //
    /////////////////////////////////

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
        //** Insert initialization protocols
    }

    //////////////////////////////
    // AddPartController Alerts //
    //////////////////////////////

    /**
     *
     * @param alertType the alert to be sent
     */
    private void alert(int alertType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {
            case 0 -> {
                alert.setTitle("Error");
                alert.setHeaderText("** INVALID INPUT **");
                alert.setContentText("Please review the form for missing/incorrect values");
                alert.showAndWait();
            }
            case 1 -> {
                alert.setTitle("Error");
                alert.setHeaderText("** INVALID RANGE **");
                alert.setContentText("Minimum must be less than Maximum");
                alert.showAndWait();
            }
            case 2 -> {
                alert.setTitle("Error");
                alert.setHeaderText("** INVALID RANGE **");
                alert.setContentText("Stock must be between Minimum and Maximum");
                alert.showAndWait();
            }
        }
    }
}

