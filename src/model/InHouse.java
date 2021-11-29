package model;

/** Extends the Part superclass. @author Spencer Watkins*/
public class InHouse extends Part {
    private int machineID;
    /** Constructor .*/
    public InHouse(String name, double price, int stock, int min, int max, int newMachineID) {
        super(name, price, stock, min, max);
        setMachineID(newMachineID);
    }
    /** Returns the machine id. */
    public int getMachineID(){
        return  machineID;
    }
    /** Sets the machine id.
     * @param newMachineID int. */
    public void setMachineID(int newMachineID){ this.machineID = newMachineID;
    }

}
