package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.*;

import java.util.Objects;

/** The Main Class, launches the application
 * @author Spencer Watkins*/
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 929, 388));
        primaryStage.show();
    }
    /** Adds a set of dummy test data used during development. */
    private static void addTestData(){
        InHouse p = new InHouse("Nuts", 0.50, 300, 0, 1000, 2);
        Inventory.addPart(p);
        InHouse q = new InHouse("Bolts", 0.75, 400, 0, 1000, 3);
        Inventory.addPart(q);
        OutSourced r = new OutSourced("Nails", 1.50, 250, 0, 1000, "Nails R Us");
        Inventory.addPart(r);

        Product  bike = new Product("Bike", 150.00, 25, 0, 60);
        Inventory.addProduct(bike);
        Product skateboard = new Product("Skateboard", 65.00, 56, 0, 75);
        Inventory.addProduct(skateboard);
        Product scooter = new Product("Scooter", 55.00, 6, 0, 85);
        Inventory.addProduct(scooter);

    }

    /** The main method.
     * Javadoc file with class and method descriptions found in the "Javadoc" file in this directory. */
    public static void main(String[] args) {
        //addTestData(); //This adds the test data, uncomment to start with some data in the tables.
        launch(args);
    }
}
