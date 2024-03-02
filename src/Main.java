public class Main {
    public static void main(String[] args) {
        WestminsterShoppingManager sys = new WestminsterShoppingManager();
        boolean exit = false;

        while (!exit) {
            exit = sys.runMenu();
        }
    }
}
