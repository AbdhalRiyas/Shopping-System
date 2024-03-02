import javax.swing.*;
import java.awt.*;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
public class ShoppingCartGUI extends JFrame {
    private JLabel totalLabel, discount20Label, discount10Label, finalTotalLabel;
    private JTable cartProductsTable;
    private JScrollPane scrollPane;
    private JButton checkoutButton;
    private ShoppingCart cart;


    // Constructor to initialize the GUI
    public ShoppingCartGUI(ShoppingCart shoppingCart){

        this.cart = shoppingCart;
        setTitle("Shopping Cart");
        setLayout(new GridBagLayout());
        initializeComponents();
        layoutComponents();
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
    }

    public void initializeComponents(){

        double totalPrice = cart.calculateTotalCost();
        double discount20 = cart.categoryDiscount();
        double discount10 = cart.calculateTotalCost()* 0.1;

        // Create GUI components
        totalLabel = new JLabel("Total: " + totalPrice);
        discount10Label  = new JLabel("First Purchase Discount (10%): -" + discount10);
        discount20Label = new JLabel("Three Items in same Category Discount (20%):  - "+ discount20);
        finalTotalLabel = new JLabel("Final Total: "+ (totalPrice- discount10 - discount20));

        checkoutButton = new JButton("Checkout");
        checkoutButton.setBackground(Color.GRAY);

        // Table to display cart items
        String[] columnNames = { "Product", "Quantity", "Price" };
        DefaultTableModel model = new DefaultTableModel(convertListToData(cart.getProducts()), columnNames);
        cartProductsTable = new JTable(model);
        scrollPane = new JScrollPane(cartProductsTable);
        cartProductsTable.setModel(model);
    }

    public void layoutComponents(){
        // Add all components to frame
        // Set GridBagLayout constraints
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.gridwidth = 1;

        constraints.gridy = 0;
        constraints.gridx = 0;
        add(scrollPane, constraints);

        constraints.anchor = GridBagConstraints.CENTER;

        constraints.gridy++;
        add(totalLabel, constraints);

        constraints.gridy++;
        add(discount10Label, constraints);

        constraints.gridy++;
        add(discount20Label, constraints);

        constraints.gridy++;
        add(finalTotalLabel, constraints);

        constraints.gridy++;
        add(checkoutButton, constraints);

        checkoutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Checkout Successful");
            cart.getProducts().clear();
            this.dispose();
        });
    }

    // Converting shopping cart map to a 2D array for the table model
    private Object[][] convertListToData(Map<Product, Integer> map) {

        // Array to store table data
        Object[][] data = new Object[map.size()][3];
        int i = 0;

        // Iterate through cart map
        for (Product product : map.keySet()) {

            // Check product type
            if (product instanceof Electronics) {
                data[i][0] = product.getProductId() + " , " + product.getProductName() + " , "
                        + ((Electronics) product).getInfo();
            } else if (product instanceof Clothing) {
                data[i][0] = product.getProductId() + " , " + product.getProductName() + " , "
                        + ((Clothing) product).getInfo();
            }
            // Set quantity from map
            data[i][1] = map.get(product);

            // Set price
            data[i][2] = product.getPrice();
            i++;
        }
        return data;
    }

    public void updateCart() {

        // Get latest cart data
        Map<Product, Integer> ShoppingTableItems;
        ShoppingTableItems = cart.getProducts();

        // Update table model
        DefaultTableModel model = (DefaultTableModel) cartProductsTable.getModel();
        model.setDataVector(convertListToData(ShoppingTableItems), new String[] { "Product", "Quantity", "Price" });

        // Refresh table
        model.fireTableDataChanged();
        cartProductsTable.setModel(model);
    }
}


