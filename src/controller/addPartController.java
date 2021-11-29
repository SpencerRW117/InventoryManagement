package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This class controls the AddPart.fxml behaviors. @author Spencer Watkins*/
public class addPartController implements Initializable {
    public HBox addPartLabel;
    public RadioButton addPartInHouseRadio;
    public RadioButton addPartOutSourcedRadio;
    public Label addPartIdLabel;
    public Label addPartNameLabel;
    public Label addPartInventoryLabel;
    public Label addPartPriceLabel;
    public TextField addPartIDText;
    public TextField addPartNameText;
    public TextField addPartInventoryText;
    public TextField addPartPriceText;
    public Label addPartMaxLabel;
    public TextField addPartMaxText;
    public Label addPartMinLabel;
    public TextField addPartMinText;
    public Button addPartSaveButton;
    public Button addPartCancelButton;
    public Label addPartOptionalLabel;
    public TextField addPartOptionalText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private Alert errorBox = new Alert(Alert.AlertType.INFORMATION);

    /** Saves the part addition and redirects to the main screen. */
    public void savePartAddition(javafx.event.ActionEvent actionEvent) throws IOException {
        String newName = addPartNameText.getText();
        int newInv = Integer.parseInt(addPartInventoryText.getText());
        double newPrice = Double.parseDouble(addPartPriceText.getText());
        int newMin = Integer.parseInt(addPartMinText.getText());
        int newMax = Integer.parseInt(addPartMaxText.getText());


        if(newName.isEmpty() || !(newName instanceof String)){
            errorBox.setContentText("No value provided for Name.");
            errorBox.show();
            return;
        }

        if(newMin > newMax || !((newMin < newInv) && ( newInv < newMax))){
            errorBox.setContentText(" Please set correct values for min, max, and inventory.\n " +
                    "Inventory must be between min and max.\n Min must be less than max.");
            errorBox.show();
            return;
        }

        if(addPartInHouseRadio.isSelected()){
            int newMachineID = Integer.parseInt(addPartOptionalText.getText());
            InHouse n = new InHouse(newName, newPrice, newInv, newMin, newMax, newMachineID);
            Inventory.addPart(n);
        }

        else if(addPartOutSourcedRadio.isSelected()){
            String newCompany = addPartOptionalText.getText();
            OutSourced o = new OutSourced(newName, newPrice, newInv, newMin, newMax, newCompany);
            Inventory.addPart(o);
        }

        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 929, 388);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
    /** Cancels the part addition by redirecting to the main screen. */
    public void cancelPartAddition(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 929, 388);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /** Changes the label for the MachineID/Company name label to Machine ID when the InHouse radio is selected. */
    public void addOnInHouseButton(ActionEvent actionEvent) {
        addPartOptionalLabel.setText("Machine ID");
    }
    /** Changes the label for the MachineID/Company name label to Company Name when the Outsourced radio is selected. */
    public void addOnOutSourcedButton(ActionEvent actionEvent) {
        addPartOptionalLabel.setText("Company Name");
    }
}
