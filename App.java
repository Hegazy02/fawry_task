
import java.util.Calendar;
import java.util.Date;

public class App {

    public static void main(String[] args) {
        Cart cart = new Cart();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7); // Set expiry date to 7 days ago
        Date expiredDate = calendar.getTime();
        Product laptop = new NormalProduct("Laptop", 1000, 10);
        Product milk = new PerishableProduct("Milk", 2, 10, expiredDate, 50);
        Product shoes = new PhysicalProduct("Shoes", 50, 10, 100);
        cart.addProduct(laptop, 5);
        cart.addProduct(milk, 5);
        cart.addProduct(shoes, 5);
        Checkout checkout = new Checkout(new Customer("Abdelrhman Hegazy", 10000), cart);
        System.out.println(checkout.generateReceipt());
    }
}
