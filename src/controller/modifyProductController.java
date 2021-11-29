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

/** This class handles behavior for ModifyProduct.fxml. @author Spencer Watkins*/
public class modifyProductController implements Initializable {
    public TextField modifyProductIDText;
    public TextField modifyProductNameText;
    public TextField modifyProductInvText;
    public TextField modifyProductPriceText;
    public TextField modifyProductMaxText;
    public TextField modifyProductMinText;
    public TextField modifyProductSearchText;
    public Button modifyProductAddButton;
    public Button modifyProductRemoveButton;
    public Button modifyProductSaveButton;
    public Button modifyProductCancelButton;

    public TableView modifyProductAllPartsTable;
    public TableColumn modifyProdAllIdCol;
    public TableColumn modifyProdAllNameCol;
    public TableColumn modifyProdAllInvCol;
    public TableColumn modifyProdAllPriceCol;
    public TableView modifyProductAssociatedPartsTable;
    public TableColumn modifyProdAssociatedIdCol;
    public TableColumn modifyProdAssociatedNameCol;
    public TableColumn modifyProdAssociatedInvCol;
    public TableColumn modifyProdAssociatedPriceCol;

    private static Product passedProduct = null;
    public  ObservableList<Part> newAssociated = FXCollections.observableArrayList();
    private Alert errorBox = new Alert(Alert.AlertType.INFORMATION);

    /** Brings in a product from the main screen. */
    public static void passTheProduct(Product p) { passedProduct = p;}

    /** Initializes the modify product form with imported data. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        modifyProductAllPartsTable.setItems(Inventory.getPartTable());
        modifyProductAssociatedPartsTable.setItems(passedProduct.getAssociatedParts());
        modifyProdAllIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProdAllNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProdAllInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProdAllPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        modifyProdAssociatedIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProdAssociatedNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProdAssociatedInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProdAssociatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        modifyProductNameText.setText(passedProduct.getProdName());
        modifyProductInvText.setText(Integer.toString(passedProduct.getProdStock()));
        modifyProductPriceText.setText(Double.toString(passedProduct.getProdPrice()));
        modifyProductMaxText.setText(Integer.toString(passedProduct.getProdMax()));
        modifyProductMinText.setText(Integer.toString(passedProduct.getProdMin()));

    }
    /** Saves the product modification and redirects to the main screen. */
    public void saveProductModification(javafx.event.ActionEvent actionEvent) throws IOException {

        int holderID = passedProduct.getProdID();
        String newName = modifyProductNameText.getText();
        int newInv = Integer.parseInt(modifyProductInvText.getText());
        Double newPrice = Double.parseDouble(modifyProductPriceText.getText());
        int newMax = Integer.parseInt(modifyProductMaxText.getText());
        int newMin = Integer.parseInt(modifyProductMinText.getText());
        ObservableList<Part> lastParts = modifyProductAssociatedPartsTable.getItems();

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

        Inventory.removeProduct(passedProduct);

        Product p = new Product(newName, newPrice, newInv, newMin, newMax);
        p.setProdID(holderID);
        p.setAssociatedParts(lastParts);
        Inventory.addProduct(p);

        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 929, 388);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
    /** Cancels the product modification and redirects to the main screen. */
    public void cancelProductModification(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 929, 388);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
    /** Handles searching the part inventory on the modify product page. */
    public void searchPartsHandler(ActionEvent actionEvent) {
        String s = modifyProductSearchText.getText();
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


        modifyProductAllPartsTable.setItems(returnParts);
        modifyProductSearchText.setText("");
    }
    /** Returns a single part with matching ID. */
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
    /** Returns a list of parts that match the specified substring. */
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

    /** Adds a part from inventory to associated parts. */
    public void modifyProdAddToAssociated(ActionEvent actionEvent) {
        Part p = (Part) modifyProductAllPartsTable.getSelectionModel().getSelectedItem();
        newAssociated.add(p);
        modifyProductAssociatedPartsTable.setItems(newAssociated);
    }

    /** Removes a part from associated parts. */
    public void modifyProdRemoveFromAssociated(ActionEvent actionEvent) {
        Part p = (Part) modifyProductAssociatedPartsTable.getSelectionModel().getSelectedItem();
        newAssociated.remove(p);
        modifyProductAssociatedPartsTable.setItems(newAssociated);
        errorBox.setContentText("Part removed from association.");
        errorBox.show();
    }

}
