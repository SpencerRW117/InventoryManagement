package model;

/** Extends the part superclass to include Company name. @author Spencer Watkins*/
public class OutSourced extends Part {
    private String company;
    /** Constructor. */
    public OutSourced(String name, double price, int stock, int min, int max, String newCompany) {
        super(name, price, stock, min, max);
        setCompany(newCompany);
    }
    /** Returns the company name string. */
    public String getCompany(){
        return company;
    }
    /** Sets a value for the company name.
     * @param newCompany */
    public void setCompany(String newCompany){
        this.company = newCompany;
    }

}