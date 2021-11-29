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
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/** This class controls Main.fxml. @author Spencer Watkins*/
public class mainController implements Initializable {
    public Label partsLabel;
    public TableView partsTable;
    public TableColumn partIdCol;
    public TableColumn partNameCol;
    public TableColumn partInvCol;
    public TableColumn partPriceCol;
    public Button partAddButton;
    public Button partModifyButton;
    public Button partDeleteButton;
    public Label prodLabel;
    public TableView prodTable;
    public TableColumn prodIdCol;
    public TableColumn prodNameCol;
    public TableColumn prodInvCol;
    public TableColumn prodPriceCol;
    public Button prodAddButton;
    public Button prodModifyButton;
    public Button prodDeleteButton;
    public Label appLabel;
    public TextField partSearchText;
    public TextField prodSearchText;
    public Button exitButton;

    Alert alertBox = new Alert(Alert.AlertType.INFORMATION);

    @Override
    /** This is the Initializer method. */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTable.setItems(Inventory.getPartTable());
        prodTable.setItems(Inventory.getProdTable());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        prodIdCol.setCellValueFactory(new PropertyValueFactory<>("prodID"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("prodName"));
        prodInvCol.setCellValueFactory(new PropertyValueFactory<>("prodStock"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("prodPrice"));
    }

    /** This method handles the search bar in the parts pane.
     * @param actionEvent triggers upon pressing the enter key.*/
    public void searchPartsHandler(ActionEvent actionEvent) {
        String s = partSearchText.getText();
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


        partsTable.setItems(returnParts);
        partSearchText.setText("");
    }
    /** This is a helper function that triggers when the user searched by an INT.
     * @param searchID the ID for the part being searched.
     * @return a single part with matching ID. */
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
    /** This method returns a list of parts containing the searched substring.
     * @param s the substring typed into the parts search bar.
     * @return a list of all parts containing the search string. */
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
    /** This method handles the search bar in the products pane.
     * @param actionEvent triggers upon pressing the Enter key. */
    public void searchProdHandler(ActionEvent actionEvent) {
        String s = prodSearchText.getText();
        ObservableList<Product> returnProducts = searchProductByName_List(s);
        if(returnProducts.size() == 0){
            try{
                int searchID = Integer.parseInt(s);
                Product p = searchProductByID(searchID);
                if (p != null){
                    returnProducts.add(p);
                }
            }
            catch (NumberFormatException e){
                //ignore
            }
        }
        prodTable.setItems(returnProducts);
        prodSearchText.setText("");
    }
    /** This is a helper function that triggers from the product search method.
     * @param searchID integer value to search for.
     * @return a single product with matching id. */
    private Product searchProductByID(int searchID){
        ObservableList<Product> allProducts = Inventory.getProdTable();
        Product retProd = null;
        for(Product p : allProducts) {
            if (p.getProdID() == searchID) {
                retProd =  p;
                break;
            }
        }
        return retProd;
    }
    /** Returns the list of products containing the searched substring.
     * @param s the substring entered into the text field.
     * @return a list of products with the matching search string. */
    private  ObservableList<Product> searchProductByName_List(String s){
        ObservableList<Product> namedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getProdTable();

        for(Product p : allProducts){
            if(p.getProdName().toLowerCase().contains(s.toLowerCase())){
                namedProducts.add(p);
            }
        }
        return namedProducts;
    }
    /** Directs users to the Add Part Screen.
     * @param actionEvent triggers upon clicking the add button under the part pane.  */
    public void toAddPartScreen(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 451, 588);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }
    /** Directs users to the Modify Part Screen.
     * @param actionEvent triggers upon clicking the modify button under the part pane. */
    public void toModifyPartScreen(javafx.event.ActionEvent actionEvent) throws IOException {

        Part p = (Part) partsTable.getSelectionModel().getSelectedItem();
        modifyPartController.passThePart(p);

        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 451, 588);
        stage.setTitle("Modify Part");
        stage.setScene(scene);
        stage.show();
    }
    /** Directs users to the Add Product Screen.
     * @param actionEvent triggers upon clicking the add button under the product pane.  */
    public void toAddProductScreen(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 913, 664);
        stage.setTitle("Modify Product");
        stage.setScene(scene);
        stage.show();
    }

    /** Directs users to the Modify Product Screen.
     * @param actionEvent triggers upon clicking the modify button under the product pane. */
    public void toModifyProductScreen(javafx.event.ActionEvent actionEvent) throws IOException {
        Product p = (Product) prodTable.getSelectionModel().getSelectedItem();
        modifyProductController.passTheProduct(p);


        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 913, 664);
        stage.setTitle("Modify Product");
        stage.setScene(scene);
        stage.show();
    }

    /** Kills the program upon clicking the exit button. */
    public void exitProgram(ActionEvent actionEvent) {
        System.exit(1);
    }

    /** Deletes parts from the main part pane. */
    public void partDeleteHandler(ActionEvent actionEvent) {
        Part p = (Part) partsTable.getSelectionModel().getSelectedItem();
        Inventory.removePart(p);
        alertBox.setContentText("Part deleted from inventory.");
        alertBox.show();
        partsTable.setItems(Inventory.getPartTable());
    }

    /** Deletes products from the main product pane. */
    public void prodDeleteHandler(ActionEvent actionEvent) {
        Product p = (Product) prodTable.getSelectionModel().getSelectedItem();
        if(!(p.getAssociatedParts().isEmpty())){
            alertBox.setContentText("Cannot delete a product with associated parts.");
            alertBox.show();
            return;
        }
        Inventory.removeProduct(p);
        alertBox.setContentText("Product deleted from inventory.");
        alertBox.show();
        prodTable.setItems(Inventory.getProdTable());
    }
}
