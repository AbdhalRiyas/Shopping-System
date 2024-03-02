import java.io.Serializable;

public class Clothing extends Product implements Serializable {
    private String size;
    private String color;

    // Constructor
    public Clothing(String productID, String productName, int numAvailableItems, double price, String size, String color) {
        super(productID, productName, numAvailableItems, price);
        this.size = size;
        this.color = color;
    }

    public Clothing() {

    }

    // Getters and Setters
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getInfo(){
        return size + " , " + color;
    }

    public String getType(){
        return "Clothing";
    }

    @Override
    public String toString() {
        return  "-------------------------------"+"\n" +
                "Product Type = " + getType() + "\n" +
                "Product Id = " + productId +"\n" +
                "Product Name = " + productName  +"\n" +
                "Number of Available Items = " + numAvailableItems +"\n" +
                "Price = Rs." + price +"\n" +
                "Product Size = " + size  + "\n" +
                "Product Color = " + color + "\n" +
                "-------------------------------" ;
    }
}
