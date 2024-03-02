import java.io.Serializable;

public abstract class Product implements Serializable {
    protected String productId;
    protected String productName;
    protected int numAvailableItems;
    protected double price;

    public Product(){
    }
    public Product(String productId, String productName, int numAvailableItems, double price) {
        this.productId = productId;
        this.productName = productName;
        this.numAvailableItems = numAvailableItems;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getNumAvailableItems() {
        return numAvailableItems;
    }

    public void setNumAvailableItems(int numAvailableItems) {
        this.numAvailableItems = numAvailableItems;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public abstract String getType();
}
