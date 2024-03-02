import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.awt.*;


public class ShoppingCentreGUI extends JFrame {
    private JLabel selectProductLabel, headingLabel, productIdLabel, nameLabel, categoryLabel, electronicLabel, clothingLabel, quantityLabel;
    private JButton shoppingCartButton, addShoppingCartButton, sortProductsButton;
    private JTable productsTable;
    private JComboBox productCategoryComboBox;
    private JScrollPane productsScrollPane;
    private JPanel detailsPanel;
    private ShoppingCart cart;

    private static final String[] columnNames = { "Product Id", "Name", "Category", "Price(Rs)", "Info" };
    public ShoppingCentreGUI(){
        setTitle("Westminster Shopping Centre");

        // Set layout manager
        setLayout(new GridBagLayout());

        // Initializing components
        initializeComponents();

        layoutGUI(new ShoppingCart());
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initializeComponents() {
        selectProductLabel = new JLabel("Select Product Category:");
        headingLabel = new JLabel("Selected Product - Details");
        productIdLabel = new JLabel("Product ID");
        nameLabel = new JLabel("Name");
        categoryLabel = new JLabel("Category");
        electronicLabel = new JLabel("Electronic");
        clothingLabel = new JLabel("Clothing");
        quantityLabel = new JLabel("Quantity");

        shoppingCartButton = new JButton("Shopping Cart");
        shoppingCartButton.setBackground(Color.GRAY);
        shoppingCartButton.setFocusable(false);

        addShoppingCartButton = new JButton("Add to Shopping Cart");
        addShoppingCartButton.setBackground(Color.GRAY);
        addShoppingCartButton.setFocusable(false);

        sortProductsButton = new JButton("Sort");
        sortProductsButton.setBackground(Color.GRAY);
        sortProductsButton.setFocusable(false);

        productsTable = new JTable();
        productCategoryComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});

        // Set table model using products list
        ArrayList<Product> products = WestminsterShoppingManager.getProductArrayList();
        DefaultTableModel model = new DefaultTableModel(convertListToData(products), columnNames);
        productsTable = new JTable(model);
        productsScrollPane = new JScrollPane(productsTable);
    }

    public void layoutGUI(ShoppingCart shoppingCart){
        this.cart = shoppingCart;
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(3,3,3,3);
        gridBagConstraints.gridwidth = 1;


        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridx = 1;
        add(shoppingCartButton,gridBagConstraints);


        gridBagConstraints.gridy++;
        gridBagConstraints.gridx = 0;
        add(selectProductLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        add(productCategoryComboBox,gridBagConstraints);

        gridBagConstraints.gridy++;
        gridBagConstraints.gridx = 1;
        add(sortProductsButton, gridBagConstraints);

        gridBagConstraints.gridwidth = 2;

        gridBagConstraints.gridy++;
        gridBagConstraints.gridx = 0;
        add(productsScrollPane, gridBagConstraints);

        gridBagConstraints.gridy++;

        detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
        gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints1.weightx = 1;
        gridBagConstraints1.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints1.insets = new Insets(3, 10, 3, 10);
        gridBagConstraints1.gridwidth = 2;

        gridBagConstraints1.gridy = 0;
        gridBagConstraints1.gridx = 0;
        detailsPanel.add(headingLabel,gridBagConstraints1);
        gridBagConstraints1.gridy++;
        detailsPanel.add(productIdLabel , gridBagConstraints1);
        gridBagConstraints1.gridy++;
        detailsPanel.add(categoryLabel , gridBagConstraints1);
        gridBagConstraints1.gridy++;
        detailsPanel.add(nameLabel , gridBagConstraints1);
        gridBagConstraints1.gridy++;
        detailsPanel.add(electronicLabel , gridBagConstraints1);
        gridBagConstraints1.gridy++;
        detailsPanel.add(clothingLabel , gridBagConstraints1);
        gridBagConstraints1.gridy++;
        detailsPanel.add(quantityLabel , gridBagConstraints1);
        gridBagConstraints1.gridy++;
        add(addShoppingCartButton, gridBagConstraints1);
        detailsPanel.setVisible(false);

        gridBagConstraints.gridy++;
        add(detailsPanel , gridBagConstraints);

        gridBagConstraints.anchor = GridBagConstraints.LAST_LINE_START;


        shoppingCartButton.addActionListener(e -> new ShoppingCartGUI(shoppingCart));

        productCategoryComboBox.addActionListener(e -> {

            // Get selected category
            String selectedCategory = (String) productCategoryComboBox.getSelectedItem();

            // Get all products
            ArrayList<Product> products = WestminsterShoppingManager.getProductArrayList();
            switch (Objects.requireNonNull(selectedCategory)) {
                case "All" ->
                    // Show all products "All" selected
                        productsTable.setModel(new DefaultTableModel(convertListToData(products) , columnNames));

                case "Electronics" -> {
                    ArrayList<Product> electronics = new ArrayList<>();
                    for (Product product : products) {
                        if (product instanceof Electronics) {
                            electronics.add(product);
                        }
                    }
                    // Set model as electronics list
                    productsTable.setModel(new DefaultTableModel(convertListToData(electronics) , columnNames));
                }

                case "Clothing" -> {
                    ArrayList<Product> clothing = new ArrayList<>();
                    for (Product product : products) {
                        if (product instanceof Clothing) {
                            clothing.add(product);
                        }
                    }
                    // Set table model as clothing List
                    productsTable.setModel(new DefaultTableModel(convertListToData(clothing) , columnNames));
                }
            }
        });

        sortProductsButton.addActionListener(e -> {
            ArrayList<Product> products = WestminsterShoppingManager.getProductArrayList();
            Collections.sort(products, Comparator.comparing(Product::getProductId));
            productsTable.setModel(new DefaultTableModel(convertListToData(products), columnNames));
        });

        // Add mouse listener for table selection
        productsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                // Get selected product
                int row = productsTable.getSelectedRow();
                String productId = (String) productsTable.getValueAt(row, 0);
                Product product = WestminsterShoppingManager.getProduct(productId);

                detailsPanel.setVisible(true);
                pack();

                // Disable add button if no quantity
                addShoppingCartButton.setEnabled(product.getNumAvailableItems() != 0);

                productIdLabel.setText("Product Id: " + product.getProductId());
                categoryLabel.setText("Category: " + product.getClass().getName());
                nameLabel.setText("Name: " + product.getProductName());
                if (product instanceof Electronics) {
                    electronicLabel.setText("Brand: " + ((Electronics) product).getBrand());
                    clothingLabel.setText("Warranty Period: " + ((Electronics) product).getWarrantyPeriod());
                } else if (product instanceof Clothing) {
                    electronicLabel.setText("Size: " + ((Clothing) product).getSize());
                    clothingLabel.setText("Colour: " + ((Clothing) product).getColor());
                }
                quantityLabel.setText("Quantity: " + product.getNumAvailableItems());
            }
        });

        productsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String productId = (String) table.getValueAt(row, 0);
                Product product = WestminsterShoppingManager.getProduct(productId);
                if (product != null && product.getNumAvailableItems() <= 3) {
                    component.setForeground(Color.RED);
                } else {
                    component.setForeground(Color.BLACK);
                }
                return component;
            }
        });

        addShoppingCartButton.addActionListener(e -> {

            // Get selected product
            int row = productsTable.getSelectedRow();
            if (row >= 0){
                String productID = (String) productsTable.getValueAt(row,0);
                Product product = WestminsterShoppingManager.getProduct(productID);

                // Validate before adding
                if(product != null && product.getNumAvailableItems() > 0) {

                    // Add product to cart
                    shoppingCart.addToCart(product, 1);
                    productsTable.clearSelection();
                    JOptionPane.showMessageDialog(null, product.getProductName() + " added to the cart.");

                    // Update quantity
                    product.setNumAvailableItems(product.getNumAvailableItems() - 1);
                    detailsPanel.setVisible(false);

                }
                // Refresh cart GUI
                ShoppingCartGUI cartGUI = new ShoppingCartGUI(cart);
                cartGUI.setVisible(false);
                cartGUI.updateCart();
                pack();
            }
        });
    }


    private Object[][] convertListToData (ArrayList<Product> productArrayList) {

        // Convert product list to data array
        Object[][] data = new Object[productArrayList.size()][5];
        for (int i = 0; i < productArrayList.size(); i++) {
            Product product = productArrayList.get(i);

            // Map product fields to array cell
            data[i][0] = product.getProductId();
            data[i][1] = product.getProductName();
            data[i][2] = product.getClass().getName();
            data[i][3] = product.getPrice();
            if (product instanceof Electronics) {
                data[i][4] = ((Electronics) product).getInfo();
            } else if (product instanceof Clothing) {
                data[i][4] = ((Clothing) product).getInfo();
            }
        }
        return data;
    }
}


