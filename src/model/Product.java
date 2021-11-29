package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/** The product class, used throughout the program. @author Spencer Watkins*/
public class Product {
    private ObservableList<Part> associatedParts= FXCollections.observableArrayList();
    private int prodID;
    private String prodName;
    private double prodPrice = 0.0;
    private int prodStock = 0;
    private int prodMin;
    private int prodMax;
    private double prodCost;
    /** Constructor. */
    public Product(String newName, double newPrice, int newStock, int newMin, int newMax) {
        this.prodID = Inventory.getProdTable().size() + 1;
        setProdName(newName);
        setProdPrice(newPrice);
        setProdStock(newStock);
        setProdMin(newMin);
        setProdMax(newMax);
    }

    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    /** Sets the Observablelist of associated parts. */
    public void setAssociatedParts(ObservableList<Part> newAssociatedParts) {
        this.associatedParts = newAssociatedParts;
    }
    /** Adds a new associated part. */
    public void addNewAssociatedPart(Part p){
        associatedParts.add(p);
    }
    /** Returns the product ID.
     * @return prodID*/
    public int getProdID() {
        return prodID;
    }
    /** Sets the product ID.
     * @param prodID */
    public void setProdID(int prodID) {
        this.prodID = prodID;
    }
    /** Returns the product name.
     * @return prodName. */
    public String getProdName() {
        return prodName;
    }
    /** Sets the product name.
     * @param prodName */
    public void setProdName(String prodName) {
        this.prodName = prodName;
    }
    /** Returns the product price.
     * @return  prodPrice */
    public double getProdPrice() {
        return prodPrice;
    }
    /** Sets the product price.
     * @param prodPrice */
    public void setProdPrice(double prodPrice) {
        this.prodPrice = prodPrice;
    }
    /** Returns the product stock.
     * @return prodStock */
    public int getProdStock() {
        return prodStock;
    }
    /** Sets the product stock.
     * @param prodStock */
    public void setProdStock(int prodStock) {
        this.prodStock = prodStock;
    }
    /** Returns the product minimum.
     * @return  prodMin */
    public int getProdMin() {
        return prodMin;
    }
    /** Sets the product minimum.
     * @param prodMin */
    public void setProdMin(int prodMin) {
        this.prodMin = prodMin;
    }
    /** Returns the product maximum.
     * @return  prodMax */
    public int getProdMax() {
        return prodMax;
    }
    /** Sets the product maximum.
     * @param prodMax */
    public void setProdMax(int prodMax) {
        this.prodMax = prodMax;
    }
    /** Returns the product cost.
     * @return prodCost */
    public double getProdCost() {
        return prodCost;
    }
    /** Sets the product cost.
     * @param prodCost*/
    public void setProdCost(double prodCost) {
        this.prodCost = prodCost;
    }
}
