
import java.util.HashMap;
import java.util.Map;

public class Cart {

    private Map<Product, CartItem> items = new HashMap<>();

    public void addProduct(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity cannot be negative or zero");
        }
        if (quantity > product.getQuantity()) {
            throw new IllegalArgumentException("Quantity cannot be greater than product quantity");
        }
        if (!items.containsKey(product)) {
            items.put(product, new CartItem(product, quantity));
            return;

        }
        CartItem item = items.get(product);

        if (quantity + item.getQuantity() > product.getQuantity()) {
            throw new IllegalArgumentException("Quantity cannot be greater than product quantity");

        }
        item.setQuantity(item.getQuantity() + quantity);
    }

    public void removeProduct(Product product) {
        items.remove(product);
    }

    public Map<Product, CartItem> getItems() {
        return items;
    }

}
