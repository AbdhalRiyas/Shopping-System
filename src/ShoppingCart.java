import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

    // Map to store added products and quantities
    private Map<Product, Integer> cartProducts;
    public ShoppingCart() {
        this.cartProducts = new HashMap<>();
    }

    // Getter for cart products map
    public Map<Product, Integer> getProducts() {
        return cartProducts;
    }

    public void addToCart(Product product, int quantity) {
        if (cartProducts.containsKey(product)) {
            int currentQuantity = cartProducts.get(product);

            // Adds new or increments quantity
            cartProducts.put(product, currentQuantity + quantity);
        } else {
            cartProducts.put(product, quantity);
        }
    }

    public double calculateTotalCost() {
        double total = 0.0;

        // Loops through map to calculate total cost
        for (Map.Entry<Product, Integer> entry : cartProducts.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public double categoryDiscount() {

        // Counts number of clothing and electronic items
        int clothingCount = 0;
        int electronicCount = 0;

        for(Map.Entry<Product, Integer> entry: this.cartProducts.entrySet()){
            Product product = entry.getKey();

            // Check product type to update count
            if (product instanceof Clothing){
                clothingCount += entry.getValue();
            } else if(product instanceof Electronics){
                electronicCount += entry.getValue();
            }
        }

        // Checks count to see if discount threshold met
        if (clothingCount >= 3 || electronicCount >= 3){
            return (calculateTotalCost()* 0.2);
        } else {
            return 0.0;
        }
    }
}