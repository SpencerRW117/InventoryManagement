package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This class controls the AddProduct.fxml behavior. @author Spencer Watkins*/
public class addProductController implements Initializable {
    public TextField addProductIDText;
    public TextField addProductNameText;
    public TextField addProductInvText;
    public TextField addProductPriceText;
    public TextField addProductMaxText;
    public TextField addProductMinText;
    public TextField addProductSearchText;
    public Button addProductAddButton;
    public Button addProductRemoveButton;
    public Button addProductSaveButton;
    public Button addProductCancelButton;
    public TableView addProductAssociatedPartsTable;
    public TableView addProductAllPartsTable;
    public TableColumn addProdAllIdCol;
    public TableColumn addProdAllNameCol;
    public TableColumn addProdAllInvCol;
    public TableColumn addProdAllPriceCol;
    public TableColumn addProdAssociatedIdCol;
    public TableColumn addProdAssociatedNameCol;
    public TableColumn addProdAssociatedInvCol;
    public TableColumn addProdAssociatedPriceCol;

    public ObservableList<Part> newAssociated = FXCollections.observableArrayList();
    private Alert errorBox = new Alert(Alert.AlertType.INFORMATION);

    @Override
    /** The Initializer for the scene. */
    public void initialize(URL url, ResourceBundle resourceBundle) {

       addProductAllPartsTable.setItems(Inventory.getPartTable());


       addProdAllIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
       addProdAllNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
       addProdAllInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
       addProdAllPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

       addProdAssociatedIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
       addProdAssociatedNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
       addProdAssociatedInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
       addProdAssociatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
    /** Saves the product addition and redirects to the main screen. */
    public void saveProductAddition(javafx.event.ActionEvent actionEvent) throws IOException {

        String newName = addProductNameText.getText();
        int newInv = Integer.parseInt(addProductInvText.getText());
        double newPrice = Double.parseDouble(addProductPriceText.getText());
        int newMin = Integer.parseInt(addProductMinText.getText());
        int newMax = Integer.parseInt(addProductMaxText.getText());

        Product p = new Product(newName, newPrice, newInv, newMin, newMax);
        p.setAssociatedParts(newAssociated);
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

        Inventory.addProduct(p);


        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 929, 388);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
    /** Cancels addition by redirecting to the main screen. */
    public void cancelProductAddition(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 929, 388);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /** Handles searching of the part inventory. */
    public void searchPartsHandler(ActionEvent actionEvent) {
        String s = addProductSearchText.getText();
        ObservableList<Part> returnParts = searchPartByName_List(s);

        if(returnParts.size() == 0){
            try{
                int searchID = Integer.parseInt(s);
                Part p = searchPartByID(searchID);
                if (p != null){
                    returnParts.add(p);
                }
            }
            catch (NumberFormatException e){
                //ignore
            }
        }


        addProductAllPartsTable.setItems(returnParts);
        addProductSearchText.setText("");
    }
    /** Returns a single part with a matching ID.
     * @param searchID int value to search. */
    private Part searchPartByID(int searchID){
        ObservableList<Part> allParts = Inventory.getPartTable();
        Part retPart = null;
        for(Part p : allParts) {
            if (p.getId() == searchID) {
                retPart =  p;
                break;
            }
        }
        return retPart;
    }
    /** Returns a list of parts with matching substrings in their name.
     * @param s the substring being searched. */
    private ObservableList<Part> searchPartByName_List(String s){
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getPartTable();

        for(Part p : allParts){
            if(p.getName().toLowerCase().contains(s.toLowerCase())){
                namedParts.add(p);
            }
        }
        return namedParts;
    }

    /** Associates a part with the current product. */
    public void addProdAddToAssociated(ActionEvent actionEvent) {
        Part p = (Part) addProductAllPartsTable.getSelectionModel().getSelectedItem();
        newAssociated.add(p);
        addProductAssociatedPartsTable.setItems(newAssociated);
    }

    /** Removes a part from association with the current product. */
    public void addProdRemoveFromAssociated(ActionEvent actionEvent) {
        Part p = (Part) addProductAssociatedPartsTable.getSelectionModel().getSelectedItem();
        newAssociated.remove(p);
        addProductAssociatedPartsTable.setItems(newAssociated);
        errorBox.setContentText("Part removed from association.");
        errorBox.show();
    }
}
