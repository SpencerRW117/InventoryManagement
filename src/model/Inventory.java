package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class acts as the primary container for both parts and products. @author Spencer Watkins*/
public class Inventory {

    private static ObservableList<Part> partTable = FXCollections.observableArrayList();
    private static ObservableList<Product> prodTable = FXCollections.observableArrayList();

    /** Adds a part to the inventory. */
    public static void addPart(Part p){
        partTable.add(p);
    }
    /** Returns the table of parts. */
    public static ObservableList<Part> getPartTable(){
        return partTable;
    }
    /** Removes a part from inventory. */
    public static void removePart(Part p){
        for(Part q: partTable){
            if(p.getId() == q.getId()){
                partTable.remove(q);
                break;
            }
        }
    }
    /** Adds a product to inventory. */
    public static void addProduct(Product q){
        prodTable.add(q);
    }
    /** Returns the table of parts. */
    public static ObservableList<Product> getProdTable(){
        return prodTable;
    }
    /** Removes a product from inventory. */
    public static void removeProduct (Product p){
        for (Product q : prodTable){
            if(p.getProdID() == q.getProdID()){
                prodTable.remove(q);
                break;
            }
        }
    }

}
