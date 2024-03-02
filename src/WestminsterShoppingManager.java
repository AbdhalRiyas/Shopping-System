import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {
    static ArrayList<Product> productArrayList = new ArrayList<>();
    private static final int maxProducts = 50;
    Scanner scanner = new Scanner(System.in);

    // Returning the list of products
    public static ArrayList<Product> getProductArrayList ( ) {
        return productArrayList;
    }

    // Returning product by product id
    public static Product getProduct(String productId) {
        Product product = null;
        for (Product p : productArrayList) {
            if (p.getProductId().equals(productId)) {
                product = p;
                break;
            }
        }
        return product;
    }

    // Adding new product to the list
    @Override
    public void addProduct(Product product){
        productArrayList.add(product);
        System.out.println("Product: "+product.getProductName()+ " added successfully!");
    }

    // Adding new product by taking user input
    public void addNewProduct(){
        // Check product limit
        if (productArrayList.size() < maxProducts) {
            System.out.println("=====================\n" +
                    "Add a Product\n" +
                    "=====================");
            System.out.println("Choose the product type:");
            System.out.println("1. Electronics");
            System.out.println("2. Clothing");
            System.out.print("Enter your choice: ");

            // Input validation
            while (!scanner.hasNextInt()) {
                scanner.next();
                System.out.print("Invalid option. Try again: ");
            }
            int productTypeChoice = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter product ID: ");
            String productId = scanner.next();

            System.out.println("Enter product name: ");
            String productName = scanner.next();

            System.out.println("Enter the number of available items: ");
            while (!scanner.hasNextInt()) {
                scanner.next();
                System.out.print("Invalid input. Enter again: ");
            }
            int numAvailableItems = scanner.nextInt();

            System.out.println("Enter price of the product: ");
            while (!scanner.hasNextDouble()) {
                scanner.next();
                System.out.print("Invalid input. Enter again: ");
            }
            double price = scanner.nextDouble();

            // Create new product object based on user choice
            switch (productTypeChoice) {
                case 1:

                    System.out.println("Enter brand name: ");
                    String brand = scanner.next();

                    System.out.println("Enter warranty period in weeks: ");
                    while (!scanner.hasNextInt()) {
                        scanner.next();
                        System.out.print("Invalid input. Enter again: ");
                    }
                    int warrantyPeriod = scanner.nextInt();

                    Product electronic = new Electronics(productId, productName, numAvailableItems, price, brand, warrantyPeriod);

                    addProduct(electronic);
                    break;
                case 2:
                    System.out.println("Enter product size: ");
                    String size = scanner.next();

                    System.out.println("Enter product color: ");
                    String color = scanner.next();

                    Product clothing = new Clothing(productId, productName, numAvailableItems, price, size, color);

                    addProduct(clothing);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1 or 2.");
            }
        } else { // Handle exceeding product limit
            System.out.println("Maximum limit of product reached, Cannot add more products");
        }
    }

    // Removing product by product id
    public void removeProduct(String productId) {

        for(int i = 0; i < productArrayList.size(); i++) {
            Product p = productArrayList.get(i);
            if(p.getProductId().equals(productId)) {
                productArrayList.remove(p);
                return;
            }
        }
    }

    // Deleting product by taking input from user
    public void deleteProduct(String productId) {

        Product removedProduct = null;
        for(Product p : productArrayList) {
            if(p.getProductId().equals(productId)) {
                removedProduct = p;
                break;
            }
        }

        removeProduct(productId);

        if(removedProduct != null) {
            System.out.println("Product deleted: " + removedProduct.getType() + " - " + removedProduct.getProductName());
        } else {
            System.out.println("Product with ID " + productId + " not found.");
        }
        System.out.println("Total number of products left in the system: " + productArrayList.size());
    }

    // Printing details of the product
    public void printProductList(){
        if (productArrayList.isEmpty()){
            System.out.println("No products to show!");
        }
        productArrayList.sort(Comparator.comparing(Product::getProductId));
        for (Product product : productArrayList) {
            System.out.println(product);
        }
    }

    // Saving product list to a file
    public void saveToFile(){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("Products.txt");
            ObjectOutputStream o = new ObjectOutputStream(fileOutputStream);

            for (Product value: productArrayList){
                o.writeObject(value);
            }
            o.close();
            fileOutputStream.close();
            System.out.println("Products saved to file successfully!");
        }catch (FileNotFoundException e){
                System.out.println("File not found!");
        }catch (IOException e ){
            e.printStackTrace();
        }
    }

    // Loading product list from file
    public void loadFromFile (){

        try {
            FileInputStream fileInputStream = new FileInputStream("Products.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            while (true){
                Product savedProduct = (Product) objectInputStream.readObject();
                productArrayList.add(savedProduct);
                System.out.println("Product: "+savedProduct.getProductName() +" loaded successfully");
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e){
            System.out.println();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void openGUI(){
        new ShoppingCentreGUI();
    }

    public boolean runMenu() {
        boolean exit = false;
        // Menu printing
        System.out.println("*********** Westminster Shopping System ***********");
        System.out.println("1. Add a new product");
        System.out.println("2. Delete a product");
        System.out.println("3. Print the list of products");
        System.out.println("4. Save products to a file");
        System.out.println("5. Load products from a file");
        System.out.println("6. Open GUI");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");

        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Invalid option. Try again: ");
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        switch (choice){
            case 1:
                addNewProduct();
                break;
            case 2:
                System.out.println("=====================\n" +
                        "Delete a Product\n" +
                        "=====================");
                System.out.println("Enter the Product Id: ");
                String productId = scanner.next();

                deleteProduct(productId);
                break;
            case 3:
                printProductList();
                break;
            case 4:
                saveToFile();
                break;
            case 5:
                loadFromFile();
                break;
            case 6:
                openGUI();
                break;
            case 0:
                exit = true;
                break;
            default:
                System.out.println("Invalid choice!");
        }
        return exit;
    }
}