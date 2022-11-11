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
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *
 * @author Forrest Young
 */
public class ModPartController implements Initializable {
    /////////////////////
    // Local Variables //
    /////////////////////
    @FXML
    private RadioButton modPartInHouse;
    @FXML
    private ToggleGroup partSource;
    @FXML
    private RadioButton modPartOutsource;
    @FXML
    private Label modPartLabel;
    @FXML
    private TextField modPartId;
    @FXML
    private TextField modPartLabelText;
    @FXML
    private TextField modPartName;
    @FXML
    private TextField modPartCost;
    @FXML
    private TextField modPartStock;
    @FXML
    private TextField modPartMin;
    @FXML
    private TextField modPartMax;
    @FXML
    private Button modPartSave;
    @FXML
    private Button modPartCancel;

    ////////////////////////////
    // JavaFX Action Handlers //
    ////////////////////////////

    /**
     *
     * @param event the event to be handled
     */
    @FXML
    public void modPartInHouseHandler(ActionEvent event){ modPartLabel.setText("Machine ID"); }

    /**
     *
     * @param event the event to be handled
     */
    @FXML
    public void modPartOutsourceHandler(ActionEvent event){ modPartLabel.setText("Company"); }

    /**
     *
     * @param event the event to be handled
     */
    @FXML
    public void modPartSaveHandler(ActionEvent event){
        //** Input Variables
        int macid;
        int stock;
        int min;
        int max;
        double cost;
        String name;
        String company;

        //** Check radio selection
        if (modPartInHouse.isSelected()){
            name = modPartName.getText();

            //** Validate Input
            try{
                stock = Integer.parseInt(modPartStock.getText());
                min = Integer.parseInt(modPartMin.getText());
                max = Integer.parseInt(modPartMax.getText());
                cost = Double.parseDouble(modPartCost.getText());
                macid = Integer.parseInt(modPartLabelText.getText());

                if (!(min < max)){
                    alert(1);
                }else if ((stock < min) || (stock > max)){
                    alert(2);
                }else{
                    //** Extract id and delete
                    Part sp = MainController.getPart();
                    int id = sp.getId();
                    Inventory.deletePart(sp);

                    //** Create modified part and add
                    InHouse ih = new InHouse(id, name, cost, stock, min, max, macid);
                    Inventory.addPart(ih);
                    returnTMS(event);
                }
            }catch(Exception e){
                alert(0);
            }
        }else{
            name = modPartName.getText();
            company = modPartLabelText.getText();

            try{
                stock = Integer.parseInt(modPartStock.getText());
                min = Integer.parseInt(modPartMin.getText());
                max = Integer.parseInt(modPartMax.getText());
                cost = Double.parseDouble(modPartCost.getText());

                if (!(min < max)){
                    alert(1);
                }else if ((stock < min) || (stock > max)){
                    alert(2);
                }else{
                    Part sp = MainController.getPart();
                    int id = sp.getId();
                    Inventory.deletePart(sp);

                    Outsourced os = new Outsourced(id, name, cost, stock, min, max, company);
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
     */
    @FXML
    public void modPartCancelHandler(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Cancel changes and return to the main screen?");
        Optional<ButtonType> o = alert.showAndWait();

        if (o.isPresent() && o.get() == ButtonType.OK) {
            returnTMS(event);
        }
    }

    /////////////////////////////////
    // ModPartController Functions //
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
        Part selectedPart = control.MainController.getPart();

        if (selectedPart instanceof InHouse){
            modPartInHouse.setSelected(true);
            modPartLabel.setText("Machine ID");
            modPartLabelText.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        }else if (selectedPart instanceof Outsourced){
            modPartOutsource.setSelected(true);
            modPartLabel.setText("Company");
            modPartLabelText.setText(String.valueOf(((Outsourced) selectedPart).getCompanyName()));
        }else{
            alert(3);
        }

        modPartId.setText(String.valueOf(selectedPart.getId()));
        modPartName.setText(String.valueOf(selectedPart.getName()));
        modPartStock.setText(String.valueOf(selectedPart.getStock()));
        modPartCost.setText(String.valueOf(selectedPart.getCost()));
        modPartMin.setText(String.valueOf(selectedPart.getMin()));
        modPartMax.setText(String.valueOf(selectedPart.getMax()));
    }
    //////////////////////////////
    // ModPartController Alerts //
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
            case 3 -> {
                alert.setTitle("");
                alert.setHeaderText("** ??? **");
                alert.setContentText("Something is broken");
                alert.showAndWait();
            }
        }
    }
}
