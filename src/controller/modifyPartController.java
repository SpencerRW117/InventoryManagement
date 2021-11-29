package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This class handles behaviors for ModifyPart.fxml. @author Spencer Watkins */
public class modifyPartController implements Initializable {
    public HBox modifyPartLabel;
    public RadioButton modifyPartInHouseButton;
    public RadioButton modifyPartOutSourcedButton;
    public Label modifyPartIDLabel;
    public Label modifyPartNameLabel;
    public Label modifyPartInventoryLabel;
    public Label modifyPartPriceLabel;
    public TextField modifyPartIDText;
    public TextField modifyPartNameText;
    public TextField modifyPartInventoryText;
    public TextField modifyPartPriceText;
    public Label modifyPartMaxLabel;
    public TextField modifyPartMaxText;
    public Label modifyPartMinLabel;
    public TextField modifyPartMinText;
    public Button modifyPartSaveButton;
    public Button modifyPartCancelButton;
    public Label modifyPartOptionalLabel;
    public TextField modifyPartOptionalText;


    private static Part passedPart = null;
    private Alert errorBox = new Alert(Alert.AlertType.INFORMATION);

   /** Allows a part to be brought in from the main screen. */
   public static void passThePart(Part p){
       passedPart = p;
   }


    /** Initializes the modify part screen with imported data. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyPartNameText.setText(passedPart.getName());
        modifyPartInventoryText.setText(Integer.toString(passedPart.getStock()));
        modifyPartPriceText.setText(Double.toString(passedPart.getPrice()));
        modifyPartMaxText.setText(Integer.toString(passedPart.getMax()));
        modifyPartMinText.setText(Integer.toString(passedPart.getMin()));

        if(passedPart instanceof InHouse){
            modifyPartInHouseButton.fire();
            modifyPartOptionalText.setText(Integer.toString(((InHouse) passedPart).getMachineID()));
        }
        else if(passedPart instanceof OutSourced){
            modifyPartOutSourcedButton.fire();
            modifyPartOptionalText.setText(((OutSourced) passedPart).getCompany());
        }


    }
    /** Saves the part modification and redirects to the main screen. */
    public void savePartModification(javafx.event.ActionEvent actionEvent) throws IOException {

        int holderID = passedPart.getId();
        String newName = modifyPartNameText.getText();
        int newStock = Integer.parseInt(modifyPartInventoryText.getText());
        double newPrice = Double.parseDouble(modifyPartPriceText.getText());
        int newMax = Integer.parseInt(modifyPartMaxText.getText());
        int newMin = Integer.parseInt(modifyPartMinText.getText());

        if(newName.isEmpty() || !(newName instanceof String)){
            errorBox.setContentText("No value provided for Name.");
            errorBox.show();
            return;
        }

        if(newMin > newMax || !((newMin < newStock) && ( newStock < newMax))){
            errorBox.setContentText(" Please set correct values for min, max, and inventory.\n " +
                    "Inventory must be between min and max.\n Min must be less than max.");
            errorBox.show();
            return;
        }

        Inventory.removePart(passedPart);

        if(modifyPartInHouseButton.isSelected()){
            int newMachID = Integer.parseInt(modifyPartOptionalText.getText());
            InHouse n = new InHouse(newName, newPrice, newStock, newMin, newMax, newMachID);
            n.setId(holderID);
            Inventory.addPart(n);
        }

        else if(modifyPartOutSourcedButton.isSelected()){
            String newCompany = modifyPartOptionalText.getText();
            OutSourced o = new OutSourced(newName, newPrice, newStock, newMin, newMax, newCompany);
            o.setId(holderID);
            Inventory.addPart(o);
        }

        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 929, 388);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
    /** Cancels the part modification by redirecting to the main screen. */
    public void cancelPartModification(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 929, 388);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
    /** Changes the label of MachineID/Company Name to Machine ID when InHouse is selected. */
    public void modifyOnInHouse(ActionEvent actionEvent) {
        modifyPartOptionalLabel.setText("Machine ID");
    }

    /** Changes the label of MachineID/Company Name to Company Name when Outsourced is selected. */
    public void modifyOnOutSourced(ActionEvent actionEvent) {
        modifyPartOptionalLabel.setText("Company Name");
    }
}
