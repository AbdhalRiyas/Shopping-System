import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.Assert.*;
public class WestminsterShoppingManagerTest {

    private WestminsterShoppingManager w;

    @Before
    public void setUp() {
        w = new WestminsterShoppingManager();
    }


    @Test
    public void testAddProduct() {
        Product product = new Electronics("123", "TestProduct", 10, 20.0, "TestBrand", 1);
        w.addProduct(product);

        ArrayList<Product> productList = WestminsterShoppingManager.getProductArrayList();
        assertTrue(productList.contains(product));
    }

    @Test
    public void testRemoveProduct(){
        Product product = new Electronics("123", "TestProduct", 10, 20.0, "TestBrand", 1);
        w.addProduct(product);

        String productId = product.getProductId();
        w.deleteProduct(productId);

        assertFalse(WestminsterShoppingManager.productArrayList.contains(product));
    }

    @Test
    public void testPrintProductList(){
        Product product = new Electronics("123", "TestProduct", 10, 20.0, "TestBrand", 1);
        w.addProduct(product);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        w.printProductList();

        String expectedOutput = "-------------------------------\n" +
                "Product Type = Electronics\n" +
                "Product Id = 123\n" +
                "Product Name = TestProduct\n" +
                "Number of Available Items = 10\n" +
                "Price = Rs.20.0\n" +
                "Brand Name = TestBrand\n" +
                "Warranty Period = 1 Weeks\n" +
                "-------------------------------\r\n";

        assertEquals(expectedOutput, outputStream.toString());

        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }

    @Test
    public void testSaveToFile(){
        Product product = new Electronics("123", "TestProduct", 10, 20.0, "TestBrand", 1);
        w.addProduct(product);

        w.saveToFile();
        assertTrue(Files.exists(Paths.get("Products.txt")));
    }

    @Test
    public void testLoadFromFile(){
        Product product = new Electronics("123", "TestProduct", 10, 20.0, "TestBrand", 1);
        w.addProduct(product);

        w.saveToFile();

        w.loadFromFile();

        assertNotNull(WestminsterShoppingManager.getProduct((product.getProductId())));
    }
}