import java.io.Serializable;

public class Electronics extends Product implements Serializable {
    private String brand;
    private int warrantyPeriod;

    // Constructor
    public Electronics(String productID, String productName, int numAvailableItems, double price, String brand, int warrantyPeriod) {
        super(productID, productName, numAvailableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public Electronics() {

    }

    // Getters and Setters
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getInfo(){
      return brand + " , " + warrantyPeriod + " months";
    }
    public String getType(){
        return "Electronics";
    }

    @Override
    public String toString() {
        return  "-------------------------------" + "\n" +
                "Product Type = " + getType() + "\n" +
                "Product Id = " + productId +"\n" +
                "Product Name = " + productName  +"\n" +
                "Number of Available Items = " + numAvailableItems +"\n" +
                "Price = Rs." + price +"\n" +
                "Brand Name = " + brand  + "\n" +
                "Warranty Period = " + warrantyPeriod +" Weeks" +"\n" +
                "-------------------------------";
    }
}
