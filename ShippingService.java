
import java.util.List;

public class ShippingService {

    public void shipItems(List<ShippableItem> items) {
        System.out.println("Shipping the following items:");
        for (ShippableItem item : items) {
            System.out.printf("- %s (Weight: %.2f kg)%n", item.getName(), item.getWeight());
        }
        System.out.println("Items shipped successfully!");
    }
}
